//after we get a simple tone to work we should try to make shephards scale look it up it sounds cool liek something aliens would make
//also should we use the java api or some other one like "JFugue"
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

 
public class main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Sound Panel: ");
        String s = br.readLine();
        
        if (s.equals("a")) invokeSound();
		
	}
	
	public static void invokeSound() {
		System.out.println("Alieeeens");
	}
	
	

}
