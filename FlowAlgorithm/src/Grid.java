//MOTHERFUCKING FUCKER FUCK FUCK
//SECOND REVISION

import java.awt.Point;


public class Grid {
	
	Pipe[] pipes;
	
	int numPipes;
	int length;
	int area;
	 
	public Grid(int length, int numPipes) {
		this.length = length;
		this.numPipes = numPipes;
		this.area = length * length;
		
		pipes = new Pipe[numPipes];
	}
	
	public void addPipe(Point p1, Point p2, int index) {
		if (index < numPipes) {
			Pipe newPipe = new Pipe(p1, p2);
			pipes[index] = newPipe;
		}
		else {
			System.out.println("Cameron fucked up");
		}
	}
	
	public boolean checkSolved() {
		
		int totalPathPoints = 0;
		for (int i = 0; i < pipes.length; i++) {
			if (!pipes[i].isConnected) return false;
			totalPathPoints += pipes[i].numPoints; 
		}
		
		if (totalPathPoints != this.area) return false;
		
		return true;
	}
}
