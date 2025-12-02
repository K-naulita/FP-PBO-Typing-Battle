import javax.swing.*;
import java.util.Random;

/**
 * BattleManager.java
 * Game logic with 5-second timer, per-round scoring,
 * correct -> +10 points, wrong/timeout -> -10 points.
 * Bot damage when player correct is 10 (updated).
 */
public class BattleManager {
    private static final int MAX_HP = 100;
    private static final int BOT_DAMAGE = 10;   
    private static final int PLAYER_DAMAGE = 10; 
    private static final int ROUND_TIME_SECONDS = 5;

    private GameWindow gui;
    private WordGenerator wordGen;
    private ScoreManager scoreManager;
    private SoundManager soundManager;

    private int playerHP;
    private int botHP;
    private int score;

    private javax.swing.Timer countdownTimer;
    private int timeLeftMs;
    private String currentWord;
    private long roundStartMillis;
    private boolean roundActive;

    private Random rand = new Random();

    public BattleManager(GameWindow gui) {
        this.gui = gui;
        this.wordGen = new WordGenerator();
        this.scoreManager = new ScoreManager();
        this.soundManager = new SoundManager(); 
        resetValues();
    }

    private void resetValues() {
        playerHP = MAX_HP;
        botHP = MAX_HP;
        score = 0;
        roundActive = false;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public void startNewGame() {
        soundManager.playBgmLoop();
        resetValues();
        gui.setPlayerHP(playerHP);
        gui.setBotHP(botHP);
        gui.setScoreLabel(score);
        gui.setHighScoreLabel(scoreManager.loadHighScore());
        gui.setStartButtonToRunning();
        gui.showMessage("Get ready!");

        startCountdownBeforeFirstRound();
    }

    public void resetGame() {
        if (countdownTimer != null && countdownTimer.isRunning()) countdownTimer.stop();
        soundManager.stopBgm();
        startNewGame();
    }

    private void startCountdownBeforeFirstRound() {
        gui.enableInput(false);
        gui.setWord("");
        final int[] count = {3};
        gui.showMessage("Game starts in " + count[0] + " ...");

        javax.swing.Timer t = new javax.swing.Timer(1000, null);
        t.addActionListener(e -> {
            count[0]--;
            if (count[0] > 0) {
                gui.showMessage("Game starts in " + count[0] + " ...");
            } else {
                ((javax.swing.Timer) e.getSource()).stop();
                gui.showMessage("GO!");
                new javax.swing.Timer(400, ev -> {
                    ((javax.swing.Timer) ev.getSource()).stop();
                    nextRound();
                }).start();
            }
        });
        t.setInitialDelay(1000);
        t.start();
    }

    public void nextRound() {
        if (checkEndCondition()) return;

        currentWord = wordGen.randomWord();
        gui.setWord(currentWord);

        timeLeftMs = ROUND_TIME_SECONDS * 1000;
        roundStartMillis = System.currentTimeMillis();
        roundActive = true;
        gui.enableInput(true);

        gui.setTimerLabel(String.format("Time: %.1f s", timeLeftMs / 1000.0));

        if (countdownTimer != null && countdownTimer.isRunning()) countdownTimer.stop();

        countdownTimer = new javax.swing.Timer(80, e -> {
            timeLeftMs -= 80;
            if (timeLeftMs < 0) timeLeftMs = 0;
            gui.setTimerLabel(String.format("Time: %.1f s", timeLeftMs / 1000.0));
            if (timeLeftMs <= 0) {
                ((javax.swing.Timer) e.getSource()).stop();
                timeExpired();
            }
        });
        countdownTimer.start();
    }

    public void submitGuess(String guess) {
        if (!roundActive) return;
        roundActive = false;
        if (countdownTimer != null && countdownTimer.isRunning()) countdownTimer.stop();
        gui.enableInput(false);

        long end = System.currentTimeMillis();
        double duration = (end - roundStartMillis) / 1000.0;
        boolean exact = guess.equals(currentWord);

        if (exact && duration <= ROUND_TIME_SECONDS) {
            botHP -= BOT_DAMAGE;
            score += 10;
            gui.showMessage(String.format("Perfect! %.2f s — Bot -%d HP", duration, BOT_DAMAGE));
        } else if (exact) {
            playerHP -= PLAYER_DAMAGE;
            score -= 10;
            gui.showMessage(String.format("Correct but too slow (%.2f s)! You -%d HP", duration, PLAYER_DAMAGE));
        } else {
            playerHP -= PLAYER_DAMAGE;
            score -= 10;
            gui.showMessage(String.format("Wrong! You -%d HP", PLAYER_DAMAGE));
        }

        if (score < 0) score = 0;

        gui.setPlayerHP(Math.max(0, playerHP));
        gui.setBotHP(Math.max(0, botHP));
        gui.setScoreLabel(score);

        int delay = 600 + rand.nextInt(700);
        new javax.swing.Timer(delay, e -> {
            ((javax.swing.Timer) e.getSource()).stop();
            if (!checkEndCondition()) nextRound();
        }).start();
    }

    private void timeExpired() {
        roundActive = false;
        gui.enableInput(false);
        playerHP -= PLAYER_DAMAGE;
        score -= 10;
        if (score < 0) score = 0;
        gui.setPlayerHP(Math.max(0, playerHP));
        gui.setScoreLabel(score);
        gui.showMessage("Time's up! You -" + PLAYER_DAMAGE + " HP");
        new javax.swing.Timer(700, e -> {
            ((javax.swing.Timer) e.getSource()).stop();
            if (!checkEndCondition()) nextRound();
        }).start();
    }

    private boolean checkEndCondition() {
        if (botHP <= 0) {
            soundManager.stopBgm();
            int oldHS = scoreManager.loadHighScore();
            if (score > oldHS) {
                scoreManager.saveHighScore(score);
            }
            gui.setScoreLabel(score);
            gui.setHighScoreLabel(scoreManager.loadHighScore());
            gui.showWinDialog(score, scoreManager.loadHighScore());
            gui.setStartButtonToStart();
            gui.showMessage("You won! Press START to play again.");
            if (countdownTimer != null && countdownTimer.isRunning()) countdownTimer.stop();
            return true;
        }
        if (playerHP <= 0) {
            soundManager.stopBgm();
            gui.showLoseDialog();
            gui.setStartButtonToStart();
            gui.showMessage("You lost! Press START to try again.");
            if (countdownTimer != null && countdownTimer.isRunning()) countdownTimer.stop();
            return true;
        }
        return false;
    }
}