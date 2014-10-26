
public class QBezCurve {
	
	private Vect A,B,C;
	
	/**
	 * Constructor for a quadratic bezier curve.
	 * @param a First endpoint position vector
	 * @param b Handle position vector
	 * @param c Second endpoint postion vector
	 */
	public QBezCurve(Vect a, Vect b, Vect c){
		A=a;
		B=b;
		C=c;
	}
	
	public Vect[] getPoints(int num){
		Vect [] result = new Vect[num];
		Vect ba = B.subtract(A);
		Vect cb = C.subtract(B);
		for(int i = 0; i < num; i++){
			double ratio = 1.0*i/num;
			Vect x = A.add(ba.multiply(ratio));
			Vect y = B.add(cb.multiply(ratio));
			result[i] = x.add(y.subtract(x).multiply(ratio));
		}
		return result;
	}
}