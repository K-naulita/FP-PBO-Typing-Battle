import javax.swing.*;
import java.awt.*;

/**
 * GameWindow.java
 * GUI utama versi lebih "gaming" (900x600), merah-hitam, with nicer layout and effects.
 */
public class GameWindow extends JFrame {
    private JLabel titleLabel;
    private JLabel lblWord;
    private JTextField txtInput;
    private JButton btnStart;
    private JLabel lblTimer;
    private JProgressBar hpPlayerBar;
    private JProgressBar hpBotBar;
    private JLabel lblPlayerHP;
    private JLabel lblBotHP;
    private JLabel lblMessage;
    private JLabel lblScore;
    private JLabel lblHighScore;

    private BattleManager battleManager;

    public GameWindow() {
        super("Typing Battle");

        battleManager = new BattleManager(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                Color c1 = new Color(20, 20, 20);
                Color c2 = new Color(40, 0, 0);
                GradientPaint gp = new GradientPaint(0, 0, c1, 0, getHeight(), c2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        root.setLayout(new BorderLayout());
        setContentPane(root);

        titleLabel = new JLabel("TYPING BATTLE", SwingConstants.CENTER);
        titleLabel.setForeground(new Color(255, 90, 90));
        titleLabel.setFont(new Font("Impact", Font.BOLD, 36));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(18, 0, 8, 0));
        root.add(titleLabel, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        root.add(center, BorderLayout.CENTER);

        JPanel hpPanel = new JPanel(new GridLayout(2, 1, 8, 8));
        hpPanel.setOpaque(false);
        hpPanel.setBorder(BorderFactory.createEmptyBorder(12, 60, 12, 60));

        JPanel row1 = new JPanel(new BorderLayout(8, 8));
        row1.setOpaque(false);
        lblPlayerHP = new JLabel("Player : 100");
        lblPlayerHP.setForeground(Color.WHITE);
        lblPlayerHP.setFont(new Font("Arial", Font.BOLD, 16));
        hpPlayerBar = new JProgressBar(0, 100);
        hpPlayerBar.setValue(100);
        hpPlayerBar.setStringPainted(true);
        hpPlayerBar.setPreferredSize(new Dimension(600, 22));

        row1.add(lblPlayerHP, BorderLayout.WEST);
        row1.add(hpPlayerBar, BorderLayout.CENTER);

        JPanel row2 = new JPanel(new BorderLayout(8, 8));
        row2.setOpaque(false);
        lblBotHP = new JLabel("Bot     : 100");
        lblBotHP.setForeground(Color.WHITE);
        lblBotHP.setFont(new Font("Arial", Font.BOLD, 16));
        hpBotBar = new JProgressBar(0, 100);
        hpBotBar.setValue(100);
        hpBotBar.setStringPainted(true);
        hpBotBar.setPreferredSize(new Dimension(600, 22));

        row2.add(lblBotHP, BorderLayout.WEST);
        row2.add(hpBotBar, BorderLayout.CENTER);

        hpPanel.add(row1);
        hpPanel.add(row2);

        center.add(hpPanel, BorderLayout.NORTH);

        JPanel wordPanel = new RoundedPanel(20, new Color(30, 0, 0, 150));
        wordPanel.setLayout(new BorderLayout());
        wordPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        wordPanel.setPreferredSize(new Dimension(800, 220));

        lblWord = new JLabel("PRESS START TO BEGIN", SwingConstants.CENTER);
        lblWord.setFont(new Font("Monospaced", Font.BOLD, 34));
        lblWord.setForeground(new Color(255, 140, 140));
        wordPanel.add(lblWord, BorderLayout.CENTER);

        center.add(wordPanel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(12, 60, 12, 60));

        txtInput = new JTextField();
        txtInput.setFont(new Font("Monospaced", Font.PLAIN, 20));
        txtInput.setEnabled(false);
        txtInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtInput.setBackground(new Color(30, 30, 30));
        txtInput.setForeground(Color.WHITE);
        txtInput.setCaretColor(Color.WHITE);

        JPanel rightControls = new JPanel();
        rightControls.setOpaque(false);
        rightControls.setLayout(new BoxLayout(rightControls, BoxLayout.Y_AXIS));

        lblTimer = new JLabel("Time: 0.0s");
        lblTimer.setForeground(Color.WHITE);
        lblTimer.setFont(new Font("Arial", Font.BOLD, 18));
        lblTimer.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnStart = new JButton("START");
        btnStart.setBackground(new Color(200, 30, 30));
        btnStart.setForeground(Color.WHITE);
        btnStart.setFont(new Font("Arial", Font.BOLD, 16));
        btnStart.setFocusPainted(false);
        btnStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnStart.setMaximumSize(new Dimension(140, 40));

        rightControls.add(lblTimer);
        rightControls.add(Box.createRigidArea(new Dimension(0,8)));
        rightControls.add(btnStart);

        inputPanel.add(txtInput);
        inputPanel.add(Box.createRigidArea(new Dimension(12,0)));
        inputPanel.add(rightControls);

        center.add(inputPanel, BorderLayout.SOUTH);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.setBorder(BorderFactory.createEmptyBorder(8, 60, 16, 60));

        lblMessage = new JLabel("Get ready to duel the bot!", SwingConstants.CENTER);
        lblMessage.setForeground(Color.LIGHT_GRAY);
        lblMessage.setFont(new Font("Arial", Font.ITALIC, 14));
        bottom.add(lblMessage, BorderLayout.NORTH);

        JPanel scoreRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 6));
        scoreRow.setOpaque(false);
        lblScore = new JLabel("Score: 0");
        lblScore.setForeground(Color.WHITE);
        lblScore.setFont(new Font("Arial", Font.BOLD, 16));
        lblHighScore = new JLabel("Highscore: 0");
        lblHighScore.setForeground(Color.YELLOW);
        lblHighScore.setFont(new Font("Arial", Font.BOLD, 16));
        scoreRow.add(lblScore);
        scoreRow.add(lblHighScore);
        bottom.add(scoreRow, BorderLayout.CENTER);

        root.add(bottom, BorderLayout.SOUTH);

        btnStart.addActionListener(e -> {
            if (btnStart.getText().equals("START")) {
                battleManager.startNewGame();
            } else {
                battleManager.resetGame();
            }
        });

        txtInput.addActionListener(e -> {
            String input = txtInput.getText().trim();
            battleManager.submitGuess(input);
        });

        int hs = battleManager.getScoreManager().loadHighScore();
        lblHighScore.setText("Highscore: " + hs);

        setVisible(true);
    }

    public void setWord(String w) {
        lblWord.setText(w);
        lblWord.setForeground(new Color(255, 120, 120));
    }

    public void setTimerLabel(String s) {
        lblTimer.setText(s);
    }

    public void enableInput(boolean en) {
        txtInput.setEnabled(en);
        if (en) {
            txtInput.setText("");
            txtInput.requestFocusInWindow();
        }
    }

    public void setPlayerHP(int hp) {
        hpPlayerBar.setValue(hp);
        lblPlayerHP.setText("Player : " + hp);
    }

    public void setBotHP(int hp) {
        hpBotBar.setValue(hp);
        lblBotHP.setText("Bot     : " + hp);
    }

    public void showMessage(String msg) {
        lblMessage.setText(msg);
    }

    public void setScoreLabel(int s) {
        lblScore.setText("Score: " + s);
    }

    public void setHighScoreLabel(int hs) {
        lblHighScore.setText("Highscore: " + hs);
    }

    public void setStartButtonToRunning() {
        btnStart.setText("RESTART");
    }

    public void setStartButtonToStart() {
        btnStart.setText("START");
    }

    public void showWinDialog(int score, int highscore) {
        String msg = "YOU WIN!\nScore: " + score + "\nHighscore: " + highscore;
        JOptionPane.showMessageDialog(this, msg, "Victory", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showLoseDialog() {
        JOptionPane.showMessageDialog(this, "YOU LOSE! Try again.", "Defeat", JOptionPane.WARNING_MESSAGE);
    }

    static class RoundedPanel extends JPanel {
        private int radius;
        private Color bg;
        public RoundedPanel(int radius, Color bg) {
            this.radius = radius;
            this.bg = bg;
            setOpaque(false);
        }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillRoundRect(0,0,getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
        }
    }
}