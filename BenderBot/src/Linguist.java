import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;



public class Linguist {

	HashMap<String,ArrayList<String>> table;
	Parser parser;
	int markovOrder;
	
	public Linguist() {
		markovOrder = 2;
		parser = new Parser(markovOrder);
		try {
			parser.parseFile("/resources/CameronMessages.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table = parser.getTable();
		//System.out.println(table);
		System.out.println(createTextFromKey(randomKey(), 1000));
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
	private String getKey(String text) {
		String key = "";
		String words[] = text.split(" ");
		for(int i = words.length-markovOrder; i<words.length;i++){
			key = key.concat(words[i]).concat(" ");
		}
		return key.trim();
	}
	public String randomKey() {
		int rand = (int)(Math.random() * table.keySet().size());
		return (String) table.keySet().toArray()[rand];
	}
	
	public static void main(String[] args) {
		Linguist linguist = new Linguist();
	}


}
