package src;
import javax.swing.*;
import java.awt.*;

public class PenduGamePanel extends JPanel {
    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 50;
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);

    public PenduGamePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTitle(g);
    }

    private void drawTitle(Graphics g) {
        g.setFont(TITLE_FONT);
        FontMetrics fm = g.getFontMetrics();
        int titleWidth = fm.stringWidth("PENDUGAME");
        int x = (getWidth() - titleWidth) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString("PENDUGAME", x, y);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("PenduGame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new PenduGamePanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
