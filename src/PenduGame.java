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
    private JLabel definitionLabel; // Ajout du JLabel pour afficher la définition
    private JButton proposerLettreButton;
    private JButton nouvellePartieButton;
    private JButton toggleValidationButton; // Bouton pour activer/désactiver la validation par Entrée
    private JTextField lettreField;
    private boolean partieTerminee;
    private boolean validationParEntree; // Variable pour suivre l'état du mode de validation par Entrée

    private static final int FRAME_WIDTH = 600; // Largeur de la fenêtre
    private static final int FRAME_HEIGHT = 300; // Hauteur de la fenêtre

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
        validationParEntree = true; // Par défaut, la validation par Entrée est activée
    }

    private void chargerMotAleatoire() {
        try (Scanner scanner = new Scanner(new File("mots.txt"))) {
            ArrayList<String> mots = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\s+", 2); // Sépare le mot à deviner et la définition
                mots.add(parts[0].toUpperCase()); // Ajoute le mot à deviner en majuscules
            }
            // Choix aléatoire d'un mot dans la liste
            Random random = new Random();
            int index = random.nextInt(mots.size());
            motADeviner = mots.get(index);
            // Recherche de la définition correspondante
            definition = ""; // Réinitialisation de la définition
            try (Scanner scannerDef = new Scanner(new File("mots.txt"))) {
                while (scannerDef.hasNextLine()) {
                    String line = scannerDef.nextLine();
                    if (line.toUpperCase().startsWith(motADeviner)) {
                        String[] parts = line.split("\\s+", 2);
                        definition = parts.length > 1 ? parts[1] : ""; // Récupère la définition s'il y en a une
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
        lettreField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validationParEntree) {
                    proposerLettre();
                }
            }
        });
        proposerLettreButton = new JButton("Proposer");
        proposerLettreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                proposerLettre();
            }
        });

        nouvellePartieButton = new JButton("Relancer une Partie");
        nouvellePartieButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                relancerPartie();
            }
        });

        toggleValidationButton = new JButton("Activer/Désactiver Validation Entrée");
        toggleValidationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleValidationParEntree();
            }
        });

        definitionLabel = new JLabel("Définition : "); // Initialisation du JLabel pour la définition

        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new FlowLayout());
        panelNorth.add(motCacheLabel);

        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new FlowLayout());
        panelCenter.add(lettresProposeesLabel);

        JPanel panelSouth = new JPanel();
        panelSouth.setLayout(new FlowLayout());
        panelSouth.add(penduLabel);
        panelSouth.add(lettreField);
        panelSouth.add(proposerLettreButton);
        panelSouth.add(nouvellePartieButton);
        panelSouth.add(toggleValidationButton); // Ajout du bouton de contrôle pour la validation par Entrée

        contentPane.add(panelNorth, BorderLayout.NORTH);
        contentPane.add(panelCenter, BorderLayout.CENTER);
        contentPane.add(panelSouth, BorderLayout.SOUTH);
        contentPane.add(definitionLabel, BorderLayout.WEST); // Ajout du JLabel à gauche

        setContentPane(contentPane);
        setTitle("Jeu du Pendu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT); // Définit la taille de la fenêtre
        setLocationRelativeTo(null); // Centre la fenêtre sur l'écran
        setVisible(true);
    }

    private void proposerLettre() {
        String lettre = lettreField.getText().toUpperCase();
        if (lettre.length() == 1 && Character.isLetter(lettre.charAt(0))) {
            if (!lettresProposees.contains(lettre.charAt(0))) {
                lettresProposees.add(lettre.charAt(0));
                if (!motADeviner.contains(lettre)) {
                    tentativesRestantes--;
                }
                mettreAJourInterface();
            } else {
                JOptionPane.showMessageDialog(this, "Cette lettre a déjà été proposée.");
            }
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

    private boolean estMotDevine() {
        for (char c : motADeviner.toCharArray()) {
            if (!lettresProposees.contains(c)) {
                return false; // S'il manque au moins une lettre, le mot n'est pas deviné
            }
        }
        return true; // Toutes les lettres ont été trouvées, le mot est deviné
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
        chargerMotAleatoire(); // Appeler à nouveau pour choisir un nouveau mot
        initialiserPartie();
        nouvellePartieButton.setEnabled(false);
    }

    private void toggleValidationParEntree() {
        validationParEntree = !validationParEntree; // Inverse l'état du mode de validation par Entrée
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PenduGame();
        });
    }
}
