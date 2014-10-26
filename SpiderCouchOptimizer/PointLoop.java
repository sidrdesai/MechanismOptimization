import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 */

/**
 * @author siddharth
 *
 */
public class PointLoop {
	
	private Vect [] list;
	private Color color;
	public static final int REFPOINTS = 10;
	
	public PointLoop(Vect [] l){
		list = l;
		color = Color.RED;
	}
	
	public PointLoop(){
		list = null;
		color = Color.RED;
	}
	
	public Vect[] normalized(){
		Vect [] result = new Vect [REFPOINTS];
		int startPoint = 0;
		double totalLength = 0;
		for(int i = 0; i < list.length; i++){
			totalLength += list[i].subtract(list[(i+1)%list.length]).getR();
			if(list[i].getX()<list[startPoint].getX())
				startPoint = i;
		}
		double stepLength = totalLength/REFPOINTS;
		result[0] = list[startPoint];
		int listIndex = startPoint;
		Vect tempVect = new Vect(0,0);
		double tempDist = 0;
		for(int i = 0; i < REFPOINTS; i++){
			while(tempDist < stepLength){
				listIndex++;
				listIndex%=list.length;
				 tempVect = list[listIndex].subtract(list[(listIndex-1)%list.length]);
				 tempDist += tempVect.getR();
			}
			tempDist -= stepLength;
			result[i] = list[listIndex].subtract(tempVect.multiply(tempDist/stepLength));
		}
		return result;
	}
	
	public void draw(Graphics g){
		g.setColor(color);
		for(int i = 0; i < list.length-1; i++){
			g.drawLine((int)list[i].getX(), (int)list[i].getY(), (int)list[i+1].getX(), (int)list[i+1].getY());
		}
		g.drawLine((int)list[list.length-1].getX(), (int)list[list.length-1].getY(), (int)list[0].getX(), (int)list[0].getY());
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void add(Vect[] v){
		if(list == null){
			list = new Vect[v.length];
			System.arraycopy(v, 0, list, 0, v.length);
			return;
		}
		Vect[] v2 = new Vect[v.length + list.length];
		System.arraycopy(list, 0, v2, 0, list.length);
		System.arraycopy(v, 0, v2, list.length, v.length);
		list = v2;
	}
	
	public static double compare(PointLoop a, PointLoop b){
		if(a.size()!=b.size())
			return -1;
		double total = 0;
		for(int i = 0; i < a.size(); i++){
			total += Math.pow(Vect.distance(a.get(i), b.get(i)),2);
		}
		return Math.sqrt(total);
	}
	
	public void add(Vect v){
		add(new Vect[]{v});
	}
	
	public int size(){
		return list.length;
	}
	
	public Vect get(int index){
		return list[index];
	}
}
