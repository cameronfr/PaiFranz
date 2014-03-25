import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class TextFormatter {

	public static void main(String[] args) {
		try {
			formatFile("/resources/CameronMessages.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void formatFile(String path) throws FileNotFoundException {
		String currentDirectory = new File("").getAbsolutePath();
		Scanner text = new Scanner(new File(currentDirectory+ path));
		writeSkypeLines(text);
	}
	private static void printBenderLines(Scanner text) {
		while(text.hasNextLine()) {
			String line = text.nextLine();
			if(line.contains("Bender:")) {
				String benderLine = line.substring(line.indexOf(" ")+1);
				benderLine = removeBrackets(benderLine);
			}
		}
	}
	private static void writeSkypeLines(Scanner text) {
		PrintWriter f0 = null;
		try {
			f0 = new PrintWriter(new FileWriter("output.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(text.hasNextLine()) {
			String line = text.nextLine();
			line = removeSkypeFormatting(line);
			if(line!=null)f0.println(line);
		}
	}
	
	private static String removeBrackets(String line) {
		String fixedLine = line;
		String bracketSubstring;
		//if has brackets, fix
		if(line.indexOf("[")>0 && line.indexOf("]") > 0) {
			bracketSubstring = line.substring(line.indexOf("["), line.indexOf("]")+1);
			fixedLine = line.replace(bracketSubstring,"");
		}
		return fixedLine;

	}
	private static String removeSkypeFormatting(String line){
		if(line.contains("&apos;"))line=line.replace("&apos;", ""); 
		if(line.contains("&quot;"))line=line.replace("&quot;", "");
		if(line.contains(">") || line.contains("<")) line = null;
		return line;
	}
}
