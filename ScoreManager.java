import java.io.*;

/**
 * ScoreManager.java
 * Handles read/write of highscore to "highscore.txt"
 */
public class ScoreManager {
    private final String FILE_NAME = "highscore.txt";

    public int loadHighScore() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return 0;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line = br.readLine();
            if (line == null) return 0;
            return Integer.parseInt(line.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public void saveHighScore(int score) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
            bw.write(String.valueOf(score));
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Failed to save highscore: " + e.getMessage());
        }
    }
}