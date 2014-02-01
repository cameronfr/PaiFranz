import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import javax.sound.sampled.*;

public class audioGenerator {

	AudioFormat			audioFormat;
	AudioInputStream	audioInput;
	// feeds data to speakers
	SourceDataLine		sourceLine;
	// 8000,11025,16000,22050,44100 allowed
	float				sampleRate			= 16000.0F;
	// 8 ,16 allowed
	int					sampleSizeInBits	= 16;
	int					channels			= 2;
	boolean				signed				= true;
	boolean				bigEndian			= true;

	// for audio gen
	ByteBuffer			byteBuffer;
	ShortBuffer			shortBuffer;
	int					byteLength;
	byte				audioData[];

	public static void main(String[] args) throws IOException {
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(System.in));
		// System.out.print("Sound Panel: ");
		// String s = br.readLine();
		// if (s.equals("play")) new audioGenerator();
		audioGenerator soundGen = new audioGenerator();

		soundGen.generateSample();
		soundGen.playOrFileData();

	}

	private void playOrFileData() {

		InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
		audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, channels,
				signed, bigEndian);
		audioInput = new AudioInputStream(byteArrayInputStream, audioFormat,
				audioData.length / audioFormat.getFrameSize());
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,
				audioFormat);

		// Geta SourceDataLine object
		try {
			sourceLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// play the audio
		new ListenThread(this).start();

	}

	public audioGenerator() {

	}

	// from
	// http://www.developer.com/java/other/article.php/2226701/Java-Sound-Creating-Playing-and-Saving-Synthetic-Sounds.ht
	public void generateSample() {

		// 2 seconds of mono or 1 sec stereo
		audioData = new byte[(int) (sampleRate * 16)];

		getSyntheticData(audioData);
		// synGen creates the audio data to be played
	}

	void getSyntheticData(byte[] synDataBuffer) {

		byteBuffer = ByteBuffer.wrap(synDataBuffer);
		shortBuffer = byteBuffer.asShortBuffer();
		byteLength = synDataBuffer.length;
		tones();
		//stereoPanning();

	}

	// this actually creates the audio data
	void tones() {

		channels = 1;
		int bytesPerSamp = 2;
		sampleRate = 16000.0F;
		int sampleLength = byteLength / bytesPerSamp;
		double freq = 900.0;

		for (int cnt = 0; cnt < sampleLength; cnt++) {
			double time = cnt / sampleRate;
			
			freq += 0.001;
			System.out.println(cnt);

			// I have no idea how this works it makes an actual sound wave
			// !1!1111!
			double sinValue = Math.sin(2 * Math.PI * freq * time)+ Math.sin(2 * Math.PI * (freq / 1.8) * time)+ Math.sin(2 * Math.PI * (freq / 1.5) * time) / 3.0;
			shortBuffer.put((short) (1600 * sinValue));
		}

	}

	void stereoPanning() {
		channels = 2;
		int bytesPerSamp = 4;
		sampleRate = 16000.0F;
		double freq = 600;

		int sampLength = byteLength / bytesPerSamp;
		for (int cnt = 0; cnt < sampLength; cnt++) {
			
			double rightGain = 16000.0 * cnt / sampLength;
			double leftGain = 16000.0 - rightGain;
			double time = cnt / sampleRate;

			double sinValue = Math.sin(2 * Math.PI * (freq) * time);
			shortBuffer.put((short) (leftGain * sinValue));
			sinValue = Math.sin(2 * Math.PI * (freq * 0.8) * time);
			shortBuffer.put((short) (rightGain * sinValue));
		}
	}
}

// this class is all playback
class ListenThread extends Thread {
	// This is a working buffer used to transfer
	// the data between the AudioInputStream and
	// the SourceDataLine. The size is rather
	// arbitrary.
	byte			playBuffer[]	= new byte[16384];
	audioGenerator	audioGen;

	public ListenThread(audioGenerator audioGenerator) {
		audioGen = audioGenerator;

	}

	@Override
	public void run() {
		try {

			// Open and start the SourceDataLine
			audioGen.sourceLine.open(audioGen.audioFormat);
			audioGen.sourceLine.start();

			int cnt;
			// Transfer the audio data to the speakers
			while ((cnt = audioGen.audioInput.read(playBuffer, 0,
					playBuffer.length)) != -1) {
				// Keep looping until the input read
				// method returns -1 for empty stream.
				if (cnt > 0) {

					audioGen.sourceLine.write(playBuffer, 0, cnt);
				}
			}

			audioGen.sourceLine.drain();
			audioGen.sourceLine.stop();
			audioGen.sourceLine.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

	}
}
