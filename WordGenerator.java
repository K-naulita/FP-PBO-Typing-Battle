import java.util.Random;

/**
 * WordGenerator.java
 * Word bank with many harder words and varied tokens.
 */
public class WordGenerator {
    private String[] words = {
        "hai", "neb", "xeno", "cryp", "juya"
    };

    private Random rand = new Random();

    public String randomWord() {
        int idx = rand.nextInt(words.length);
        return words[idx];
    }
}