package src;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class PenduGame extends JFrame {
    private String motADeviner;
    private String definition;
    private Set<Character> lettresProposees;
    private int tentativesRestantes;
    private JLabel motCacheLabel;
    private JLabel lettresProposeesLabel;
    private JLabel penduLabel;
    private JLabel definitionLabel;
    private JButton nouvellePartieButton;
    private JTextField lettreField;
    private boolean partieTerminee;

    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 300;

    public PenduGame() {
        initialiserInterface();
        initialiserPartie();
    }

    private void initialiserPartie() {
        lettresProposees = new HashSet<>();
        tentativesRestantes = 10;
        partieTerminee = false;
        chargerMotAleatoire();
        motCacheLabel.setText(getMotCache());
        penduLabel.setText("Tentatives restantes : " + tentativesRestantes);
        lettresProposeesLabel.setText("Lettres proposées : " + lettresProposees.toString());
        if (definition != null && !definition.isEmpty()) {
            definitionLabel.setText("Définition : " + definition);
        } else {
            definitionLabel.setText("Définition : Aucune");
        }
    }

    private void chargerMotAleatoire() {
        try (Scanner scanner = new Scanner(new File("mots.txt"))) {
            ArrayList<String> mots = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\s+", 2);
                mots.add(parts[0].toUpperCase());
            }
            Random random = new Random();
            int index = random.nextInt(mots.size());
            motADeviner = mots.get(index);
            definition = "";
            try (Scanner scannerDef = new Scanner(new File("mots.txt"))) {
                while (scannerDef.hasNextLine()) {
                    String line = scannerDef.nextLine();
                    if (line.toUpperCase().startsWith(motADeviner)) {
                        String[] parts = line.split("\\s+", 2);
                        definition = parts.length > 1 ? parts[1] : "";
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initialiserInterface() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        motCacheLabel = new JLabel();
        lettresProposeesLabel = new JLabel();
        penduLabel = new JLabel();
        lettreField = new JTextField(1);
        lettreField.setEditable(false); // Désactiver la zone de saisie
        nouvellePartieButton = new JButton("Relancer une Partie");
        nouvellePartieButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                relancerPartie();
            }
        });

        definitionLabel = new JLabel("Définition : ");

        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new FlowLayout());
        panelNorth.add(motCacheLabel);

        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new FlowLayout());
        panelCenter.add(lettresProposeesLabel);

        JPanel panelSouth = new JPanel();
        panelSouth.setLayout(new FlowLayout());
        panelSouth.add(penduLabel);
        panelSouth.add(nouvellePartieButton);

        contentPane.add(panelNorth, BorderLayout.NORTH);
        contentPane.add(panelCenter, BorderLayout.CENTER);
        contentPane.add(panelSouth, BorderLayout.SOUTH);
        contentPane.add(definitionLabel, BorderLayout.WEST);

        setContentPane(contentPane);
        setTitle("Jeu du Pendu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);

        // Ajout de l'écouteur d'événements clavier à la fenêtre
        addKeyListener(new LettreKeyListener());
        setFocusable(true);
    }

    private void proposerLettre(String lettre) {
        if (lettre.length() == 1 && Character.isLetter(lettre.charAt(0))) {
            char lettreProposee = lettre.toUpperCase().charAt(0);
            if (!lettresProposees.contains(lettreProposee)) {
                lettresProposees.add(lettreProposee);
                if (!motADeviner.contains(String.valueOf(lettreProposee))) {
                    tentativesRestantes--;
                }
                mettreAJourInterface();
            } else {
                JOptionPane.showMessageDialog(this, "Cette lettre a déjà été proposée.");
            }
        }
    }

    private String getMotCache() {
        StringBuilder motCache = new StringBuilder();
        for (char c : motADeviner.toCharArray()) {
            if (lettresProposees.contains(c)) {
                motCache.append(c);
            } else {
                motCache.append('_');
            }
            motCache.append(' ');
        }
        return motCache.toString();
    }

    private boolean estMotDevine() {
        for (char c : motADeviner.toCharArray()) {
            if (!lettresProposees.contains(c)) {
                return false;
            }
        }
        return true;
    }

    private void mettreAJourInterface() {
        motCacheLabel.setText(getMotCache());
        lettresProposeesLabel.setText("Lettres proposées : " + lettresProposees.toString());
        penduLabel.setText("Tentatives restantes : " + tentativesRestantes);

        if (tentativesRestantes <= 0 || estMotDevine()) {
            partieTerminee = true;
            if (estMotDevine()) {
                JOptionPane.showMessageDialog(this, "Bravo ! Vous avez deviné le mot !");
            } else {
                JOptionPane.showMessageDialog(this, "Désolé, vous avez épuisé toutes vos tentatives. Le mot était : " + motADeviner);
            }
            nouvellePartieButton.setEnabled(true);
        }
    }

    private void relancerPartie() {
        partieTerminee = false;
        lettresProposees.clear();
        tentativesRestantes = 10;
        chargerMotAleatoire();
        initialiserPartie();
        nouvellePartieButton.setEnabled(false);
    }

    // Classe interne pour écouter les événements clavier
    private class LettreKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            char lettreTapee = Character.toUpperCase(e.getKeyChar());
            if (Character.isLetter(lettreTapee)) {
                proposerLettre(String.valueOf(lettreTapee)); // Appel de proposerLettre avec la lettre tapée
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PenduGame();
        });
    }
}
