import java.awt.Point;


public class Pipe {
	Point p1;
	Point p2;
	
	Point[] path;
	
	int numPoints;
	boolean isConnected;
	
	public Pipe(Point first, Point second) {
		this.p1	= first;
		this.p2 = second;
		numPoints = 2;
		isConnected = false;
	}
	
	public void setPath(Point[] p) {
		//Conditions to follow
		boolean c1, c2;
		
		c1 = true;
		if (p[0] == p1) c1 = false; 
		if (p[0] == p2) c1 = false;  
		
		int lastIndex = p.length - 1;
		
		if (p[lastIndex] == p1) c1 = false; 
		if (p[lastIndex] == p2) c1 = false;  
		
		c2 = false;
		
		//Everything checks out
		numPoints += p.length;
		this.path = p;
		isConnected = true;
	}
	
}
