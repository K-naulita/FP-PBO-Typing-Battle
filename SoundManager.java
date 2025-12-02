import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * SoundManager.java
 * Loads and plays only BGM (bgm.wav) in working directory.
 * Correct/wrong sound methods are intentionally no-ops.
 */
public class SoundManager {
    private Clip bgmClip;

    public SoundManager() {
        try {
            bgmClip = loadClip("bgm.wav");
        } catch (Exception e) {
            System.err.println("BGM file not found or failed to load: " + e.getMessage());
            bgmClip = null;
        }
    }

    private Clip loadClip(String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File f = new File(filename);
        if (!f.exists()) throw new IOException("Missing audio file: " + filename);
        AudioInputStream ais = AudioSystem.getAudioInputStream(f);
        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        return clip;
    }

    public void playCorrect() {}
    public void playWrong() {}

    public void playBgmLoop() {
        if (bgmClip == null) return;
        if (bgmClip.isRunning()) bgmClip.stop();
        bgmClip.setFramePosition(0);
        bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopBgm() {
        if (bgmClip == null) return;
        bgmClip.stop();
        bgmClip.setFramePosition(0);
    }
}
