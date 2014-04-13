import java.util.ArrayList;
import java.util.HashMap;

public class Linguist {

	private Parser parser;
	private MarkovChain chain;
	private int keyLength = 2;
	private static final int TEXT_LENGTH = 1000;

	public Linguist() {
		chain = new MarkovChain(keyLength);
		parser = new Parser(chain);
		parser.parseFile("/resources/CameronMessages.txt");
		//System.out.println(table);
		System.out.println(createTextFromKey(chain.randomKey(), TEXT_LENGTH).replaceAll("/n", "\n"));
	}

	public final String createTextFromKey(final String startKey, final int wordLengthLimit) {
		HashMap<String, ArrayList<String>> table = chain.getTable();
		String sentence = startKey;
		String key = startKey;
		int randomIndex;
		int iterNum = 0;
		while (table.containsKey(key) && iterNum < wordLengthLimit) {
			randomIndex = (int) (Math.random() * table.get(key).size());
			sentence = sentence.concat(" ").concat(table.get(key).get(randomIndex));
			key = chain.keyFromText(sentence);
			iterNum++;
		}
		return sentence;
	}

	public static void main(final String[] args) {
		Linguist linguist = new Linguist();
	}
}
