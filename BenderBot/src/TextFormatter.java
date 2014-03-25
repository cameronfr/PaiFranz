import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class TextFormatter {

	public static void main(String[] args) {
		try {
			formatFile("/resources/BenderQuotes.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void formatFile(String path) throws FileNotFoundException {
		String currentDirectory = new File("").getAbsolutePath();
		Scanner text = new Scanner(new File(currentDirectory+ path));
		printBenderLines(text);
	}
	private static void printBenderLines(Scanner text) {
		while(text.hasNextLine()) {
			String line = text.nextLine();
			if(line.contains("Bender:")) {
				String benderLine = line.substring(line.indexOf(" ")+1);
				System.out.println(benderLine);
			}
		}
	}
}
