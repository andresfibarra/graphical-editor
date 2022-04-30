import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * A multi-segment Shape, with straight lines connecting "joint" points -- (x1,y1) to (x2,y2) to (x3,y3) ...
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 * @author CBK, updated Fall 2016
 * @author Andres Ibarra, Spring 2021, CS10 PSet 6
 */
public class Polyline implements Shape {
	ArrayList<Segment> freehand;	//list of segments that compose the polyline
	private Color color;
	public final double MAX_DIST = 5.0;	//maximum click distance from the polyline that will be considered a hit

	// TODO: YOUR CODE HERE

	/**
	 * Create an "empty" Polyline with one one point
	 * @param x
	 * @param y
	 * @param color
	 */
	public Polyline (int x, int y, Color color) {
		freehand = new ArrayList<Segment>();
		freehand.add(new Segment(x, y, color));
		this.color = color;
	}

	/**
	 * @param x1 - x coord of start
	 * @param y1 - y coord of start
	 * @param x2 - x coord of end
	 * @param y2 - y coord of end
	 */
	public void addSeg(int x1, int y1, int x2, int y2) {
		freehand.add(new Segment(x1, y1, x2, y2, color));
	}

	@Override
	public void moveBy(int dx, int dy) {
		for (Segment seg: freehand) {
			seg.moveBy(dx, dy);
		}
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
		for(Segment seg: freehand) {
			seg.setColor(color);
		}
	}

	/**
	 * return true if the point clicked is within MAX_DIST of any of the segments
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public boolean contains(int x, int y) {
		for(Segment seg: freehand) {
			if (Segment.pointToSegmentDistance(x, y, seg.getX1(), seg.getY1(), seg.getX2(), seg.getY2()) <= MAX_DIST) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void draw(Graphics g) {
		for (Segment seg: freehand) {
			seg.draw(g);
		}
	}

	/**
	 * Return the rectangle's information in a way that can be easily parsed
	 * @return
	 */
	@Override
	public String toString() {
		String s = "polyline ";
		for (Segment seg: freehand) {
			s += seg.getX1() + " " + seg.getY1() + " " + seg.getX2() + " " + seg.getY2() + " ";
		}
		s += color.getRGB();
		return s;
	}
}
