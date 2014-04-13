import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

	private MarkovChain chain;
	private int keyLength;

	public Parser(final MarkovChain chainIn) {
		chain = chainIn;
		keyLength = chain.getKeyLength();
	}

	public final void parseFile(final String path) {
		try {
			simpleReadTextByLine(readFile(path));
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
		int wordBufferLength = keyLength + 1;
		String[] wordBuffer = new String[keyLength + 1];
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
			wordBuffer[keyLength] = text.next();
		}
	}

	private void simpleReadTextByLine(final Scanner text) {
		int wordBufferLength = keyLength + 1;
		String[] wordBuffer = new String[keyLength + 1];
		for (int i = 0; i < wordBufferLength; i++) {
			if (text.hasNext()) {
					wordBuffer[i] = text.next();
				}
		}
		while (text.hasNextLine()) {
			String tmpLine = text.nextLine();
			tmpLine = tmpLine.concat(" /n");
			Scanner line = new Scanner(tmpLine);
			while (line.hasNext()) {
				String key = "";
				String value = wordBuffer[wordBufferLength - 1];
				
				// last word in buffer is value, not part of key
				for (int i = 0; i < keyLength; i++) {
					key = key.concat(" ").concat(wordBuffer[i]);
				}
				chain.addEntry(key.trim(), value.trim());
				for (int i = 0; i < wordBufferLength - 1; i++) {
					wordBuffer[i] = wordBuffer[i + 1];
					
				}
				wordBuffer[keyLength] = line.next();
			}
		}
	}

}
