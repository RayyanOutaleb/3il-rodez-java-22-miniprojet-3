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
    private JButton nouvellePartieButton;
    private JTextField lettreField;
    private JButton proposerLettreButton;
    private boolean partieTerminee;

    public PenduGame() {
        initialiserInterface();
        chargerMotAleatoire();
        initialiserPartie();
    }

    private void initialiserPartie() {
        lettresProposees = new HashSet<>();
        tentativesRestantes = 6;
        partieTerminee = false;
        mettreAJourInterface();
    }

    private void chargerMotAleatoire() {
        try (Scanner scanner = new Scanner(new File("mots.txt"))) {
            ArrayList<String> mots = new ArrayList<>();
            while (scanner.hasNextLine()) {
                mots.add(scanner.nextLine());
            }
            Random random = new Random();
            String motDef = mots.get(random.nextInt(mots.size()));
            String[] parts = motDef.split(" ");
            motADeviner = parts[0].toUpperCase();
            definition = parts[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initialiserInterface() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        motCacheLabel = new JLabel("");
        lettresProposeesLabel = new JLabel("");
        penduLabel = new JLabel("");
        lettreField = new JTextField(1);
        proposerLettreButton = new JButton("Proposer");
        proposerLettreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                proposerLettre();
            }
        });
        contentPane.add(motCacheLabel, BorderLayout.NORTH);
        contentPane.add(lettresProposeesLabel, BorderLayout.CENTER);
        contentPane.add(penduLabel, BorderLayout.SOUTH);
        contentPane.add(lettreField, BorderLayout.WEST);
        contentPane.add(proposerLettreButton, BorderLayout.EAST);
        setContentPane(contentPane);
        setTitle("Jeu du Pendu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void proposerLettre() {
        String lettre = lettreField.getText().toUpperCase();
        if (lettre.length() == 1 && Character.isLetter(lettre.charAt(0))) {
            lettresProposees.add(lettre.charAt(0));
            mettreAJourInterface();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une lettre valide.");
        }
        lettreField.setText("");
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

    private void mettreAJourInterface() {
        motCacheLabel.setText(getMotCache());
        lettresProposeesLabel.setText("Lettres proposées : " + lettresProposees.toString());
        penduLabel.setText("Tentatives restantes : " + tentativesRestantes);
        if (partieTerminee) {
            if (motADeviner.equals(getMotCache())) {
                JOptionPane.showMessageDialog(this, "Bravo ! Vous avez deviné le mot !");
            } else {
                JOptionPane.showMessageDialog(this, "Désolé, vous avez épuisé toutes vos tentatives. Le mot était : " + motADeviner);
            }
            nouvellePartieButton.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PenduGame();
        });
    }
}
