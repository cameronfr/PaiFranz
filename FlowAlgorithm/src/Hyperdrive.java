import java.awt.Point;
import java.util.Scanner;

public class Hyperdrive {
    // aka engine

    Scanner input;

    // Grid grid;

    public Hyperdrive() {

	initScanner();
	initGrid();

    }

    public static void main(String[] args) {

	Hyperdrive longShot = new Hyperdrive();

    }

    public void initScanner() {

	input = new Scanner(System.in);

    }

    public int nextIntIn() {

	while (!input.hasNextInt()) {
	    
	    input.next();
	    System.out.println("INVALID NUMBER PROGRAM WILL NOW TERMINATE");
	    System.exit(1);
	}
	return input.nextInt();
    }

    public void initGrid() {
	int tmpSize;
	int tmpPipes;
	//
	System.out.println("Grid Size: ");
	while (!input.hasNextInt()) {
	    input.next();
	}
	tmpSize = input.nextInt();

	//
	System.out.println("Number of Pipes: ");
	while (!input.hasNextInt()) {
	    input.next();
	}
	tmpPipes = input.nextInt();
	// grid = new Grid(tmpSize, tmpPipes);

	for (int i = 1; i <= tmpPipes; i++) {

	    int tmpX;
	    int tmpY;
	    //
	    System.out.println("Pipe " + i);
	    System.out.println("Start X:");
	    tmpX = nextIntIn();
	    //
	    System.out.println("Start Y:");
	    tmpY = nextIntIn();
	    //
	    Point startPos = new Point(tmpX, tmpY);

	    System.out.println("End X:");
	    tmpX = nextIntIn();
	    //
	    System.out.println("End Y:");
	    tmpY = nextIntIn();
	    //
	    Point endPos = new Point(tmpX, tmpY);
	    // grid.addPipe(startPos, endPos, i-1);
	}
    }
}
