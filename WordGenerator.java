import java.util.Random;

/**
 * WordGenerator.java
 * Word bank with many harder words and varied tokens.
 */
public class WordGenerator {
    private String[] words = {
        "asymptotic", "nebulous", "xenophobia", "cryptogram", "juxtaposition",
        "synchronization", "juxtapose", "antidisestablishmentarianism", "ephemeral",
        "serendipity", "quintessential", "obfuscation", "idiosyncratic", "inconsequential",
        "metamorphosis", "labyrinthine", "anachronistic", "circuitous", "ubiquitous",
        "pellucid", "diaphanous", "perspicacious", "grandiloquent", "magnanimous",
        "vicissitude", "sesquipedalian", "hyperbolic", "transmogrify", "phosphorescent",
        "ransomware", "pseudocode", "hypervelocity", "numbermix", "xenogram",
        "characterization", "institutionalization", "misinterpretation", "photosynthesis",
        "electroencephalogram", "counterintuitive", "computationally", "neuroplasticity",
        "sphinx", "rhythms", "mnemonic", "cwm", "lynx", "gypsum",
        "cooperate", "enter", "stateoftheart", "rocknroll"
    };

    private Random rand = new Random();

    public String randomWord() {
        int idx = rand.nextInt(words.length);
        return words[idx];
    }
}
