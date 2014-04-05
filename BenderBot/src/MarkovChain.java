import java.util.ArrayList;
import java.util.HashMap;


public class MarkovChain {

	HashMap<String, ArrayList<String>> table;
	int keyLength;
	
	public MarkovChain(int keyLengthIn) {
		keyLength = keyLengthIn;
		table = new HashMap<String, ArrayList<String>>();
	}
	
	public String randomKey() {
		int rand = (int)(Math.random() * table.keySet().size());
		return (String) table.keySet().toArray()[rand];
	}
	
	public String keyFromText(String text) {
		String key = "";
		String words[] = text.split(" ");
		for(int i = words.length-keyLength; i<words.length;i++){
			key = key.concat(words[i]).concat(" ");
		}
		return key.trim();
	}
	
	
	public HashMap getTable() {
		return this.table;
	}

	public void addEntry(String key, String value) {
		ArrayList<String> values = new ArrayList<String>();
		if (table.containsKey(key)) {
			values = table.get(key);
		}
		values.add(value);
		table.put(key, values);
	}
	public int getKeyLength() {
		return keyLength;
	}

}

