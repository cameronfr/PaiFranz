import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import javax.sound.sampled.*;

//Resources:
//1 http://www.jsresources.org/faq_audio.html
//2 http://www.developer.com/java/other/article.php/2226701/Java-Sound-Creating-Playing-and-Saving-Synthetic-Sounds.htm
//3 http://www.developer.com/java/other/article.php/2173111/Java-Sound-Playing-Back-Audio-Files-using-Java.htm
//4 http://www.dickbaldwin.com/dsp/Dsp00100.htm
//
//We are using PCM_Signed encoding which is default for AudioFormat class
//Most of the instance variables are merely "default" parameters which will be changed by the "fx" methods

public class AudioGenerator {

	// Stores formatted audio
	AudioFormat audioFormat;
	// Input stream with an AudioFormat and length in frames
	AudioInputStream audioInputStream;
	// Feeds data to speakers
	SourceDataLine sourceDataLine;

	// Frequency (Hz) of rate wave is sampled at, standard: 8 11.025, 16, 22.05,
	// 44.1 (kHz)
	float sampleRate = 16000;
	// Size of each sample (or frame) in bytes, allowed: 1 or 2, 2 provides
	// greater dynamic range
	int sampleSizeInBytes = 2;
	// Size of each sample (or frame) in bits
	int sampleSizeInBits = sampleSizeInBytes * 8;
	// Mono(1) or stereo(2) sound
	int channels = 2;
	// Length of the audio data in samples. Set by the bufferAudioData() method
	int lengthInSamples;
	// default is signed for PCM encoding
	boolean signed = true;
	// indexing data storage parameter, java uses bigEndian
	boolean bigEndian = true;
	// File name is audio is filed
	String fileName = "AudioOutput";

	// A buffer for raw audio data. At 16 kHz sample rate and 2 byte sample = 2S. Controls length of sound
	byte audioData[] = new byte[(int) (sampleRate * sampleSizeInBytes * 10)];
	// 8 bit(byte) buffer
	ByteBuffer byteBuffer;
	// 16 bit(short) buffer
	ShortBuffer shortBuffer;
	// length of audioData in bytes
	int byteLength;

	public static void main(String[] args) {
		AudioGenerator audioGen = new AudioGenerator();
		audioGen.bufferAudioData();
		audioGen.fxTones();
		audioGen.playOrStoreAudioData(true, false);

	}

	private void playOrStoreAudioData(boolean play, boolean file) {
		// put our audioData into an inputStream, because inputStream has
		// internal buffer thingy
		InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
		// Instantiate the audioFormat object with out parameters
		audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
		// Instantiate AudioInputStream with ByteArrayInputStream and
		// audioFormat
		audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormat, audioData.length / audioFormat.getFrameSize());
		// Create an "info" class with info which will be used to request a
		// SourceDataLine
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);

		// Try to request a SourceDataLine from the AudioSystem which will
		// output to speakers
		try {	
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
		
		} catch (LineUnavailableException e) {
			e.printStackTrace();

		}
		// Instantiate a ListenThread to play the audio if the play parameter is
		// true
		if (play) {
			new PlayThread(sourceDataLine, audioInputStream).start();
		
		}
		// Attempt to write to file if file parameter is true
		if (file) {
			try {
				AudioSystem.write(audioInputStream, AudioFileFormat.Type.AIFF, new File(fileName + ".au"));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}

	public void bufferAudioData() {
		// puts raw audio data into ByteBuffer
		byteBuffer = ByteBuffer.wrap(audioData);
		// converts byteBuffer to shortBuffer
		shortBuffer = byteBuffer.asShortBuffer();
		// set length of data in bytes
		byteLength = audioData.length;
		// set length of data in samples
		lengthInSamples = byteLength / sampleSizeInBytes;
	}

	public void fxTones() {
		channels = 1;
		sampleSizeInBytes = 2;
		sampleRate = 16000;

		for (int i = 0; i < lengthInSamples; i++) {

			// calculate time in seconds this iteration is at in the audio
			double time = i / sampleRate;
			// frequency (hz) we want to attain
			double freq = 950.0;

			// The line below samples a sine wave at given (time) and the
			// wavelength of a sine wave is 2PI because math
			// We want to have (#freq) of wavelengths in one second
			// The equation for this would be y = sin( 2 * pi * freq * x), which
			// results in (#freq) wavelengths for every x
			// Within the sin() changes frequency, and outside the sin() changes
			// amplitude(volume)
			double sinValue = Math.sin(2 * Math.PI * freq * time);

			// store the data in sinValue and increase its amplitude
			shortBuffer.put((short) (1600 * sinValue));
		}
	}
}

class PlayThread extends Thread {
	SourceDataLine sourceDataLine;
	AudioInputStream audioInputStream;
	// Buffer for transferring audio from AudioInputstream to SourceDataLine
	byte tempBuffer[] = new byte[16384]; // 2^14

	public PlayThread(SourceDataLine sdl, AudioInputStream ais) {
		sourceDataLine = sdl;
		audioInputStream = ais;

	}

	@Override
	public void run() {
		try {
			// Give the SourceDataLine the audioFormat and then open for
			// playback
			sourceDataLine.open(audioInputStream.getFormat());
			sourceDataLine.start();

			int i;
			// Read writes to buffer and returns bytes that were read into
			// tempBuffer. -1 means EOF
			while ((i = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
				if (i > 0) {
					// Write the data from the buffer into the output line
					sourceDataLine.write(tempBuffer, 0, tempBuffer.length);
				}
			}
			// Empties internal buffer
			sourceDataLine.drain();
			// Closes the dataLine
			sourceDataLine.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
//                .
//                |
//             .-"^"-.
//            /_....._\
//        .-"`         `"-.
//       (  ooo  ooo  ooo  )
//        '-.,_________,.-'
//            /       \
//          _/         \_
//         `"`         `"`
//			ALIIIIENNZZZ