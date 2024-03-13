package src;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PenduGame penduGame = new PenduGame();
            penduGame.initialiserJeu();
        });
    }
}
