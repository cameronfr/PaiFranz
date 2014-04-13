import java.util.ArrayList;
import java.util.HashMap;


public class MarkovChain {

	private HashMap<String, ArrayList<String>> table;
	private int keyLength;

	public MarkovChain(final int keyLengthIn) {
		keyLength = keyLengthIn;
		table = new HashMap<String, ArrayList<String>>();
	}

	public final String randomKey() {
		int rand = (int) (Math.random() * table.keySet().size());
		return (String) table.keySet().toArray()[rand];
	}

	public final String keyFromText(final String text) {
		String key = "";
		String[] words = text.split(" ");
		for (int i = words.length - keyLength; i < words.length; i++) {
			key = key.concat(words[i]).concat(" ");
		}
		return key.trim();
	}

	public final HashMap getTable() {
		return this.table;
	}

	public final void addEntry(final String key, final String value) {
		ArrayList<String> values = new ArrayList<String>();
		if (table.containsKey(key)) {
			values = table.get(key);
		}
		values.add(value);
		table.put(key, values);
	}
	
	public final int getKeyLength() {
		return keyLength;
	}

}

