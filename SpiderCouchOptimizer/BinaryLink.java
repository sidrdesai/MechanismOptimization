import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 */

/**
 * Links two position vectors as a binary link. Provides a method for drawing.
 * @author siddharth
 *
 */
public class BinaryLink {
	
	private Color color;
	private Vect v1;
	private Vect v2;
	
	/**
	 * Constructor for a binary link with a default color.
	 * @param a first joint
	 * @param b second joint
	 */
	public BinaryLink(Vect a, Vect b){
		v1 = a;
		v2 = b;
		color = Color.BLUE;
		
	}
	
	/**
	 * Constructor for a binary link with a specified color.
	 * @param a first joint
	 * @param b second joint
	 * @param c color
	 */
	public BinaryLink(Vect a, Vect b, Color c){
		v1 = a;
		v2 = b;
		color = c;
	}
	
	/**
	 * Draws a circle at both joints and connects it with a line.
	 * This draws it with reference to the graphics parameter provided.
	 * It is drawn with the color of the link.
	 * @param g The graphics object to draw onto
	 * @see Graphics
	 */
	public void draw(Graphics g){
		g.setColor(color);
		g.drawOval((int)v1.getX()-3,(int)v1.getY()-3,5,5);
		g.drawOval((int)v2.getX()-3,(int)v2.getY()-3,5,5);
		g.drawLine((int)v1.getX(),(int)v1.getY(),(int)v2.getX(),(int)v2.getY());
		g.drawString(v1.toString(),(int)v1.getX(),(int)v1.getY());
		g.drawString(v2.toString(),(int)v2.getX(),(int)v2.getY());
	}
	
	/**
	 * Sets the color of the link.
	 * @param c color
	 */
	public void setColor(Color c){
		color = c;
	}
	
	/**
	 * Gets the color of the link.
	 * @return color
	 */
	public Color getColor(){
		return color;
	}
}