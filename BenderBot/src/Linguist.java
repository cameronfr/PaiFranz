import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;



public class Linguist {

	HashMap<String,ArrayList<String>> table;
	Parser parser;
	int keyLength;
	
	public Linguist() {
		keyLength = 2;
		parser = new Parser(keyLength);
		try {
			parser.parseFile("/resources/testInput.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table = parser.getTable();
		System.out.println(table);
		System.out.println(createSentenceFromKey("name is"));
	}
	
	public String createSentenceFromKey(String startKey) {
		String sentence = startKey;
		String key = startKey;
		int randomIndex;
		while(table.containsKey(key)) {
			randomIndex = (int)(Math.random() * table.get(key).size());
			sentence = sentence.concat(" ").concat(table.get(key).get(randomIndex));
			key = getKey(sentence);
		}
		return sentence;
	}
	
	//returns key at very end of string
	public String getKey(String text) {
		String key = "";
		String words[] = text.split(" ");
		for(int i = words.length-keyLength; i<words.length;i++){
			key = key.concat(words[i]).concat(" ");
		}
		return key.trim();
	}
	
	public static void main(String[] args) {
		Linguist linguist = new Linguist();
	}


}
