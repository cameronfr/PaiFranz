import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;



public class Linguist {

	HashMap<String,ArrayList<String>> table;
	Parser parser;
	int markovOrder;
	
	public Linguist() {
		markovOrder = 3;
		parser = new Parser(markovOrder);
		try {
			parser.parseFile("/resources/BenderQuotes.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table = parser.getTable();
		//System.out.println(table);
		System.out.println(createTextFromKey("Hey! I got a busted", 100));
	}
	
	public String createTextFromKey(String startKey, int wordLengthLimit) {
		String sentence = startKey;
		String key = startKey;
		int randomIndex;
		int iterNum = 0;
		while(table.containsKey(key) && iterNum < wordLengthLimit) {
			randomIndex = (int)(Math.random() * table.get(key).size());
			sentence = sentence.concat(" ").concat(table.get(key).get(randomIndex));
			key = getKey(sentence);
			iterNum++;
		}
		return sentence;
	}
	
	//returns key at very end of string
	public String getKey(String text) {
		String key = "";
		String words[] = text.split(" ");
		for(int i = words.length-markovOrder; i<words.length;i++){
			key = key.concat(words[i]).concat(" ");
		}
		return key.trim();
	}
	
	public static void main(String[] args) {
		Linguist linguist = new Linguist();
	}


}
