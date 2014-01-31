//after we get a simple tone to work we should try to make shephards scale look it up it sounds cool liek something aliens would make
//also should we use the java api or some other one like "JFugue"
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.midi.*;

 
public class main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Sound Panel: ");
        String s = br.readLine();
        
        if (s.equals("play")) invokeSound();
        invokeSound();
		
	}
	
	public static void invokeSound() {
		
	}
	
	//this method creates a sample that can be played back
	//idk how to play back
	//sampling code from https://stackoverflow.com/questions/297070/how-to-generate-sound-effects-in-java
	public static void generateSample() {
		double[] rawOutput;
		double sampleRate;
		double samplePause;
		double duration;
		int durationInSamples;
		int time;
		
		//in hz, 44100 default playback
		sampleRate = 44100;
		
		//time btween samples
		samplePause = 1.0 / sampleRate;
		
		//this is = 2ms
		duration = 0.002;
		//math.ceil just rounds
		durationInSamples = (int) Math.ceil(duration * sampleRate);
		time = 0;
		for(int i = 0; i < durationInSamples; i++) 
		{
			//sample a sine wave at 440hz
			//i dont really know how this code works but ok
			 time += samplePause;
			 
			rawOutput[i] = Math.sin(2 * Math.PI * 440 * time);
		}
				
		
	}

}
