import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class LinkageVisualizer extends JPanel implements MouseListener,
		KeyListener,MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//variables
	private JFrame frame;
	private int WIDTH = 600;
	private int HEIGHT = 400;
	private Vect mousePos;
	private boolean mousePressed;
	private boolean moveAction;
	private boolean clickAction;
	
	private PointLoop curvePoints;
	private PointLoop targetCurve;
	private int selPoint;
	private double [] params;
	/*
	 * 0 - ground length
	 * 1 - ground angle
	 * 2 - input length
	 * 3 - coupler length
	 * 4 - output length
	 * 5 - coupler parallel
	 * 6 - coupler normal
	 */
	private PointLoop outputCurve;
	private BinaryLink[] links;
	/*
	 * 0 - ground
	 * 1 - input
	 * 2 - coupler
	 * 3 - output
	 * 4 - coupler a
	 * 5 - coupler b
	 */
	private Vect[] joints;
	private double time;
	private final Vect origin = new Vect(200,200);
	
	
	public static void main(String[] args) {
		new LinkageVisualizer();
	}
	
	public LinkageVisualizer(){
		frame = new JFrame("Linkages");
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.add(this);
		frame.addKeyListener(this);
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		init();
		frame.setVisible(true);
		while(true)
		{
			update();
			frame.repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {}
		}
	}
	
	private void init(){
		mousePos = new Vect(0,0);
		clickAction=true;
		moveAction =true;
		curvePoints = new PointLoop();
		curvePoints.setColor(Color.GREEN);
		outputCurve = new PointLoop();
		outputCurve.setColor(Color.YELLOW);
		time = 0;
		selPoint = -1;
		joints = new Vect[] {new Vect(200,200),
							new Vect(300,300),
							new Vect(400,300),
							new Vect(300,200),
							new Vect(350,350)};
		links = new BinaryLink[] {new BinaryLink(joints[0],joints[3]),
							new BinaryLink(joints[0],joints[1]),
							new BinaryLink(joints[1],joints[2]),
							new BinaryLink(joints[3],joints[2]),
							new BinaryLink(joints[1],joints[4]),
							new BinaryLink(joints[2],joints[4])};
		
		Vect asdf = new Vect(200,200);
		Vect jkl = new Vect(0,50);
		for(int blah = 0; blah < 4; blah++)
			curvePoints.add(asdf.add(jkl.rotate(Math.PI/2)));
		calcBezier();
	}
	
	private void update(){
/*		if(!clickAction){
			curvePoints.add(new Vect(mouseX,mouseY));
			if(curvePoints.size() > 2){
				curve = new PointLoop();
				for(int i = 0; i < curvePoints.size(); i++){
					Vect a = curvePoints.get(i);
					Vect b = curvePoints.get((i+1)%curvePoints.size());
					Vect c = curvePoints.get((i+2)%curvePoints.size());
					curve.add(new QBezCurve(Vect.midpoint(a, b),b,Vect.midpoint(b, c)).getPoints(10));
				}
			}
			clickAction=true;
		}*/
		if(mousePressed && selPoint == -1){
			int shortIndex = 0;
			double shortLength = Vect.distance(mousePos,curvePoints.get(0));
			for(int i = 1; i < curvePoints.size(); i++)
			{
				if(Vect.distance(mousePos, curvePoints.get(i))<shortLength){
					shortIndex = i;
					shortLength = Vect.distance(mousePos, curvePoints.get(i));
				}
			}
			if(shortLength < 5)
				selPoint = shortIndex;
			else
				selPoint = -2;
		}
		if(!moveAction && selPoint >= 0){
			curvePoints.get(selPoint).setPos(mousePos.getX(), mousePos.getY());
			calcBezier();
			moveAction = true;
		}
		time++;
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0,0,WIDTH,HEIGHT);
		if(targetCurve!=null){
			targetCurve.draw(g);
			curvePoints.draw(g);
//			outputCurve.draw(g);
		}
		for(int i = 0; i < links.length; i++)
			links[i].draw(g);
	}
	
	private void calcBezier(){
		targetCurve = new PointLoop();
		for(int i = 0; i < curvePoints.size(); i++){
			Vect a = curvePoints.get(i);
			Vect b = curvePoints.get((i+1)%curvePoints.size());
			Vect c = curvePoints.get((i+2)%curvePoints.size());
			targetCurve.add(new QBezCurve(Vect.midpoint(a, b),b,Vect.midpoint(b, c)).getPoints(10));
		}
	}
	
	private Vect[] calcJoints(double [] p, double inputAngle){
		Vect[] result = new Vect[5];
		result[0] = origin;
		result[3] = origin.add(new Vect(0,1).setR(p[0]).setTheta(p[1]));
		result[1] = origin.add(new Vect(0,1).setR(p[2]).setTheta(inputAngle));
		Vect temp = result[1].subtract(result[3]);
		
		return result;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
		clickAction = false;
		selPoint = -1;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mousePos.setPos(e.getX(),e.getY());
		moveAction = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePos.setPos(e.getX(),e.getY());
	}
}
