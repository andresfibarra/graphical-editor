import java.io.*;
import java.net.Socket;

/**
 * Handles communication to/from the server for the editor
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author Chris Bailey-Kellogg; overall structure substantially revised Winter 2014
 * @author Travis Peters, Dartmouth CS 10, Winter 2015; remove EditorCommunicatorStandalone (use echo server for testing)
 * @author Andres Ibarra, Spring 2021, CS10 PSet 6
 */
public class EditorCommunicator extends Thread {
	private PrintWriter out;		// to server
	private BufferedReader in;		// from server
	protected Editor editor;		// handling communication for

	/**
	 * Establishes connection and in/out pair
	 */
	public EditorCommunicator(String serverIP, Editor editor) {
		this.editor = editor;
		System.out.println("connecting to " + serverIP + "...");
		try {
			Socket sock = new Socket(serverIP, 4242);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			System.out.println("...connected");
		}
		catch (IOException e) {
			System.err.println("couldn't connect");
			System.exit(-1);
		}
	}

	/**
	 * Sends message to the server
	 */
	public void send(String msg) {
		out.println(msg);
	}

	/**
	 * Keeps listening for and handling (your code) messages from the server
	 */
	public void run() {
		try {
			// Handle messages
			// TODO: YOUR CODE HERE
			String line;
			while ((line = in.readLine()) != null) {
				String[] split = line.split(" ");	//create array with each word/ number to facilitate parsing
				String request = split[0];
				//get line with command to draw, move, recolor, delete, the given shape whose ID is given
				//call the needed method from messageHandler
				if (request.equals("draw")) {
					MessageHandler.handleDraw(editor.getSketch().getIdMap(), split);
				}
				else if (request.equals("move")) {
					MessageHandler.handleMove(editor.getSketch().getIdMap(), Integer.parseInt(split[1]),
							Integer.parseInt(split[2]), Integer.parseInt(split[3]));
				}
				else if (request.equals("recolor")) {
					MessageHandler.handleRecolor(editor.getSketch().getIdMap(), Integer.parseInt(split[1]),
							Integer.parseInt(split[2]));
				}
				else if (request.equals("delete")) {
					MessageHandler.handleDelete(editor.getSketch().getIdMap(), Integer.parseInt(split[1]));
				}
				editor.repaint();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("server hung up");
		}
	}	

	// Send editor requests to the server
	// TODO: YOUR CODE HERE
	
}
