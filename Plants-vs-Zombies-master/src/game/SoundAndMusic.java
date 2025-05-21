package game;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * A simple audio class for playing background music (BGM) and sound effects.
 * Suitable for beginners.
 */
public class SoundAndMusic {
    private String filePath; // Path to the BGM audio file
    private volatile int state; // 1: Not started, 2: Playing, 3: Stopped
    private Thread bgmThread;

    /**
     * Constructor, sets the BGM file path
     * @param filePath path to the BGM audio file
     */
    public SoundAndMusic(String filePath) {
        this.filePath = filePath;
        this.state = 1;
    }

    /**
     * Set a new BGM file path (for changing music)
     * @param newPath new BGM audio file path
     */
    public void setFilePath(String newPath) {
        this.filePath = newPath;
        this.state = 1; // Allow replay from new file
    }

    /**
     * Start looping BGM playback in the background.
     * Will repeat the music until StopPlay_BGM() is called.
     */
    public void StartPlay_BGM() {
        if (state != 1) return; // Only start if not already playing
        state = 2;
        bgmThread = new Thread(() -> {
            while (state == 2) {
                try (AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filePath))) {
                    AudioFormat format = stream.getFormat();
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                    try (SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
                        line.open();
                        line.start();
                        byte[] buf = new byte[512 * 1024];
                        int nbytes;
                        // Read and play audio data while not stopped
                        while (state == 2 && (nbytes = stream.read(buf, 0, buf.length)) != -1) {
                            line.write(buf, 0, nbytes);
                        }
                        line.drain();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    break; // Exit if there is an error
                }
                // If not stopped, loop and play again
            }
        });
        bgmThread.start();
    }

    /**
     * Stop the background music playback.
     */
    public void StopPlay_BGM() {
        state = 3; // Signal the thread to stop
        if (bgmThread != null) {
            try {
                bgmThread.join(500); // Wait for the thread to finish (max 0.5s)
            } catch (InterruptedException ignored) {}
        }
        state = 1; // Reset state so music can be played again
    }

    /**
     * Play a sound effect once (for events like zombie being hit).
     * @param soundPath the path to the sound effect file
     */
    public void playSound(String soundPath) {
        new Thread(() -> {
            try (AudioInputStream stream = AudioSystem.getAudioInputStream(new File(soundPath))) {
                AudioFormat format = stream.getFormat();
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                try (SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
                    line.open();
                    line.start();
                    byte[] buf = new byte[512 * 1024];
                    int nbytes;
                    // Play the sound data once
                    while ((nbytes = stream.read(buf, 0, buf.length)) != -1) {
                        line.write(buf, 0, nbytes);
                    }
                    line.drain();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}