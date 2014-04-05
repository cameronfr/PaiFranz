import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

	private MarkovChain chain;
	private int markovOrder;

	public Parser(final MarkovChain chainIn) {
		chain = chainIn;
		markovOrder = chain.getKeyLength();
	}

	public final void parseFile(final String path) {
		try {
			simpleReadTextByWord(readFile(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Scanner readFile(final String path) throws FileNotFoundException {
		String currentDirectory = new File("").getAbsolutePath();
		Scanner text = new Scanner(new File(currentDirectory + path));
		return text;
	}

	// reads from a file with formatted sentences, 2 values
	private void simpleReadTextByWord(final Scanner text) {
		int wordBufferLength = markovOrder + 1;
		String[] wordBuffer = new String[markovOrder + 1];
		for (int i = 0; i < wordBufferLength; i++) {
			if (text.hasNext()) {
					wordBuffer[i] = text.next();
				}
		}
		while (text.hasNext()) {
			String key = "";
			String value = wordBuffer[wordBufferLength - 1];
			// last word in buffer is value, not part of key
			for (int i = 0; i < wordBufferLength - 1; i++) {
				key = key.concat(" ").concat(wordBuffer[i]);
			}
			chain.addEntry(key.trim(), value.trim());
			for (int i = 0; i < wordBufferLength - 1; i++) {
				wordBuffer[i] = wordBuffer[i + 1];
			}
			wordBuffer[markovOrder] = text.next();
		}
	}

}
