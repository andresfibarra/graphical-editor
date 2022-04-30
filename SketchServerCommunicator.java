import java.io.*;
import java.net.Socket;

/**
 * Handles communication between the server and one client, for SketchServer
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate SketchServerCommunicator
 * @author Andres Ibarra, Spring 2021, CS10 PSet 6
 */
public class SketchServerCommunicator extends Thread {
	private Socket sock;					// to talk with client
	private BufferedReader in;				// from client
	private PrintWriter out;				// to client
	private SketchServer server;			// handling communication for

	public SketchServerCommunicator(Socket sock, SketchServer server) {
		this.sock = sock;
		this.server = server;
	}

	/**
	 * Sends a message to the client
	 * @param msg
	 */
	public void send(String msg) {
		out.println(msg);
	}
	
	/**
	 * Keeps listening for and handling (your code) messages from the client
	 */
	public void run() {
		try {
			System.out.println("someone connected");
			
			// Communication channel
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);

			// Tell the client the current state of the world
			// TODO: YOUR CODE HERE
			for (Shape shape: server.getSketch().getIdMap().values()){
				out.println("draw " + shape);
			}

			// Keep getting and handling messages from the client
			// TODO: YOUR CODE HERE
			String line;
			while ((line = in.readLine()) != null ) {
				String[] split = line.split(" ");
				String request = split[0];
				//get line with command to draw, move, recolor, delete, the given shape whose ID is given
				//call the needed method from messageHandler
				if (request.equals("draw")) {
					MessageHandler.handleDraw(server.getSketch().getIdMap(), split);
				}
				else if (request.equals("move")) {
					MessageHandler.handleMove(server.getSketch().getIdMap(), Integer.parseInt(split[1]),
							Integer.parseInt(split[2]), Integer.parseInt(split[3]));
				}
				else if (request.equals("recolor")) {
					MessageHandler.handleRecolor(server.getSketch().getIdMap(), Integer.parseInt(split[1]),
							Integer.parseInt(split[2]));
				}
				else if (request.equals("delete")) {
					MessageHandler.handleDelete(server.getSketch().getIdMap(), Integer.parseInt(split[1]));
				}
				server.broadcast(line);	//pass on request to all the editors
			}

			// Clean up -- note that also remove self from server's list so it doesn't broadcast here
			server.removeCommunicator(this);
			out.close();
			in.close();
			sock.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
