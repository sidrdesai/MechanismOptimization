
public class Vect {
	private double X;
	private double Y;
	private double R;
	private double theta;
	
	/**
	 * Creates a 2D vector (x,y)
	 * @param x the x component
	 * @param y the y component
	 */
	public Vect(double x, double y){
		setPos(x,y);
	}
	
	/**
	 * Gets the value of the x component of the vector
	 * @return the x component
	 */
	public double getX() {
		return X;
	}
	
	/**
	 * Sets the value for the x component of the vector preserving the y component
 	 * @param x the value of the x component
	 * @return the vector itself
	 */
	public Vect setX(double x) {
		X = x;
		R = Math.sqrt(X*X+Y*Y);
		theta = Math.atan2(Y, X);
		return this;
	}

	/**
	 * Gets the value of the Y component of the vector
	 * @return the y component
	 */
	public double getY() {
		return Y;
	}

	/**
	 * Sets the value for the y component of the vector preserving the x component
	 * @param y the value of the y component
	 * @return the vector itself
	 */
	public Vect setY(double y) {
		Y = y;
		R = Math.sqrt(X*X+Y*Y);
		theta = Math.atan2(Y, X);
		return this;
	}
	
	/**
	 * Gets the magnitude of the vector
	 * @return the magnitude of the vector
	 */
	public double getR() {
		return R;
	}
	
	/**
	 * Sets the magnitude of the vector preserving the angle
	 * @param r
	 * @return the vector itself
	 */
	public Vect setR(double r) {
		if(r < 0){
			rotate(Math.PI);
			return setR(-1*r);
		}
		R = r;
		X = R*Math.cos(theta);
		Y = R*Math.sin(theta);
		return this;
	}

	/**
	 * Gets the angle of the vector with respect to the positive X-axis
	 * @return the angle in radians
	 */
	public double getTheta() {
		return theta;
	}
	
	/**
	 * Sets the angle of the vector preserving the magnitude
	 * @param theta the angle in radians
	 * @return the vector itself
	 */
	public Vect setTheta(double theta) {
		this.theta = theta%(2*Math.PI);
		X = R*Math.cos(theta);
		Y = R*Math.sin(theta);
		return this;
	}
	
	/**
	 * Rotates the vector counter-clockwise
	 * @param angle the angle to rotate in radians
	 * @return the vector itself
	 */
	public Vect rotate(double angle) {
		setTheta(getTheta()+angle);
		return this;
	}
	
	/**
	 * Scales the magnitude of the vector
	 * @param s the scaling factor
	 * @return the vector itself
	 */
	public Vect scale(double s){
		setR(getR()*s);
		return this;
	}
	
	/**
	 * Sets the position of the vector by specifying an x component and y component
	 * @param x the x component
	 * @param y the y component
	 * @return the vector itself
	 */
	public Vect setPos(double x, double y){
		X = x;
		Y = y;
		R = Math.sqrt(x*x+y*y);
		theta = Math.atan2(y, x);
		return this;
	}
	
	/**
	 * Returns the dot product of two vectors
	 * @param that the other vector
	 * @return the dot product
	 */
	public double dotProd(Vect that){
		return this.getX()*that.getX()+this.getY()*that.getY();
	}
	
	/**
	 * Returns the cross product of two vectors axb as a double.
	 * This is the coefficient of the unit vector in the z direction.
	 * Since both inputs are on the X,Y plane, the resultant cross product
	 * is in the z direction with no x or y component.
	 * @param that the second vector
	 * @return the cross product
	 */
	public double crossProd(Vect that){
		return this.getX()*that.getY()-this.getY()*that.getX();
	}
	
	/**
	 * Returns a copy of the vector.
	 */
	public Vect clone()
	{
		return new Vect(getX(),getY());
	}
	
	/**
	 * Returns a unit vector in the same direction.
	 * @return a unit vector
	 */
	public Vect unit()
	{
		return clone().setR(1.0);
	}
	
	/**
	 * Returns the sum of two vectors. The original vector is unchanged.
	 * @param that the other vector
	 * @return a new vector that is the sum
	 */
	public Vect add(Vect that){
		return new Vect(this.getX()+that.getX(),this.getY()+that.getY());
	}
	
	/**
	 * Returns the difference of two vectors. The original vector is unchanged.
	 * @param that the vector to be subtracted
	 * @return a new vector that is the difference
	 */
	public Vect subtract(Vect that){
		return new Vect(this.getX()-that.getX(),this.getY()-that.getY());
	}
	
	/**
	 * Returns the product of the vector with a scalar.
	 * This is different from the scale method in that the original
	 * vector is unchanged.
	 * @param s the scalar factor.
	 * @return a new vector that is the product
	 */
	public Vect multiply(double s){
		return clone().scale(s);
	}
	
	/**
	 * Returns the projection of this vector onto the vector provided as an argument.
	 * The original vector is unchanged.
	 * @param that the vector to be projected onto
	 * @return a new vector that is the projection of this vector
	 */
	public Vect project(Vect that){
		return that.unit().multiply(this.dotProd(that));
	}
	
	/**
	 * Compares this vector to another vector
	 * @param that
	 * @return true if they are equal
	 */
	public boolean equals(Vect that){
		return (this.getX()==that.getX())&&(this.getY()==that.getY());
	}
	
	/**
	 * Returns a string representation of the vector in
	 * the format X,Y surrounded by angle brackets.
	 * @return a string representation of the vector
	 */
	public String toString(){
		return "<"+getX()+","+getY()+">";
	}
	
	public static Vect midpoint(Vect a, Vect b){
		return new Vect((a.getX()+b.getX())/2, (a.getY()+b.getY())/2);
	}
	
	public static double distance(Vect a, Vect b){
		return Math.sqrt(Math.pow(a.getX()-b.getX(),2)+Math.pow(a.getY()-b.getY(),2));
	}
}