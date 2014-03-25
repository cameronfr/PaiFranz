import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {

	HashMap<String,ArrayList<String>> table;
	int keyLength;
	
	public Parser(int keyLengthIn) {
		keyLength = keyLengthIn;
		table = new HashMap<String,ArrayList<String>>();
	}

	public void parseFile(String path) throws FileNotFoundException {
		String currentDirectory = new File("").getAbsolutePath();
		Scanner text = new Scanner(new File(currentDirectory+ path));
		simpleReadText(text);
	}
	
	//reads from a file with formatted sentences, 2 values 
	private void simpleReadText(Scanner text) {
			String word1,word2,word3;
			word1 = text.next();
			word2 = text.next();
			while(text.hasNext()) {
				word3 = text.next();
				String key = word1.concat(" ").concat(word2);
				String value = word3;
				addEntry(key.trim(), value.trim());
				word1 = word2;
				word2 = word3;
			}
			
		
	}
	public HashMap getTable(){
		return this.table;
	}
	private void addEntry(String key, String value) {
		ArrayList<String> values = new ArrayList<String>();
		if (table.containsKey(key)) {
			values = table.get(key);
		}
		values.add(value);
		table.put(key, values);
	}
	
}
