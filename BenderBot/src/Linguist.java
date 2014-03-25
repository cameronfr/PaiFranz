import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;



public class Linguist {

	HashMap<String,ArrayList<String>> table;
	Parser parser;
	
	public Linguist() {
		parser = new Parser();
		try {
			parser.parseFile("/resources/testInput.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table = parser.getTable();
		System.out.println(table);
	}
	
	public static void main(String[] args) {
		Linguist linguist = new Linguist();
	}


}
