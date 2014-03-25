import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {

	HashMap<String,ArrayList<String>> table;

	public Parser() {
		table = new HashMap<String,ArrayList<String>>();
	}

	public void parseFile(String path) throws FileNotFoundException {
		String currentDirectory = new File("").getAbsolutePath();
		Scanner words = new Scanner(new File(currentDirectory+ path));
		simpleReadFile(words);
	}
	
	//reads from a file formatted with each line as one sentence
	private void simpleReadFile(Scanner text) {
		while(text.hasNextLine()) {
			Scanner line = new Scanner(text.nextLine());
			String word1,word2,word3;
			word1 = line.next();
			word2 = line.next();
			while(line.hasNext()) {
				word3 = line.next();
				String key = word1.concat(" ").concat(word2);
				String value = word3;
				addEntry(key, value);
				word1 = word2;
				word2 = word3;
			}
			
		}
	}
	private void addEntry(String key, String value) {
		//System.out.println("Key: " + key + " Value: " + value);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Parser fileParser = new Parser();
		try {
			fileParser.parseFile("/resources/testInput.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
