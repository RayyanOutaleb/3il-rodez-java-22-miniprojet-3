package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
 * La classe PenduGame représente un jeu de pendu où le joueur doit deviner un mot en proposant des lettres.
 * Le jeu affiche le mot à deviner sous forme masquée avec des tirets pour chaque lettre non devinée,
 * ainsi qu'une image représentant un pendu qui évolue en fonction du nombre de tentatives restantes.
 * Le joueur peut proposer des lettres en utilisant le clavier.
 */
public class PenduGame extends JFrame {
    // Attributs de la classe

    /**
     * Le mot à deviner.
     */
    private String motADeviner;

    /**
     * La définition du mot à deviner.
     */
    private String definition;

    /**
     * Ensemble des lettres déjà proposées par le joueur.
     */
    private Set<Character> lettresProposees;

    /**
     * Nombre de tentatives restantes pour le joueur.
     */
    private int tentativesRestantes;

    /**
     * JLabel pour afficher le mot à deviner.
     */
    private JLabel motCacheLabel;

    /**
     * JLabel pour afficher les lettres déjà proposées.
     */
    private JLabel lettresProposeesLabel;

    /**
     * JLabel pour afficher le nombre de tentatives restantes.
     */
    private JLabel penduLabel;

    /**
     * JLabel pour afficher la définition du mot à deviner.
     */
    private JLabel definitionLabel;

    /**
     * Bouton pour démarrer une nouvelle partie.
     */
    private JButton nouvellePartieButton;

    /**
     * Champ de texte pour entrer une lettre proposée par le joueur.
     */
    private JTextField lettreField;

    /**
     * Indique si la partie est terminée.
     */
    private boolean partieTerminee;

    /**
     * JLabel pour afficher l'image du pendu.
     */
    private JLabel imagePenduLabel; // JLabel pour afficher l'image du pendu

    /**
     * Largeur de la fenêtre du jeu.
     */
    private static final int FRAME_WIDTH = 2000;

    /**
     * Hauteur de la fenêtre du jeu.
     */
    private static final int FRAME_HEIGHT = 1000;

    // Constructeur

    /**
     * Construit un nouvel objet PenduGame.
     */
    public PenduGame() {
        // Initialisation des composants graphiques
        this.motCacheLabel = new JLabel();
        this.lettresProposeesLabel = new JLabel();
        this.penduLabel = new JLabel();
        this.lettreField = new JTextField(1);
        this.nouvellePartieButton = new JButton("Relancer une Partie");
        this.imagePenduLabel = new JLabel(); // Initialisation du JLabel pour l'image du pendu

        // Ajout d'un écouteur d'événement sur le bouton pour relancer une partie
        this.nouvellePartieButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                relancerPartie();
            }
        });

        // Initialisation du JLabel pour afficher la définition
        this.definitionLabel = new JLabel("Définition : ");
        this.partieTerminee = false;

        // Configuration de la fenêtre principale
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setLocationRelativeTo(null);
    }

    // Méthodes publiques

    /**
     * Initialise le jeu en initialisant l'interface graphique et en démarrant une nouvelle partie.
     */
    public void initialiserJeu() {
        this.initialiserInterface();
        this.initialiserPartie();
        this.setVisible(true);
    }

    // Méthodes privées

    /**
     * Initialise une nouvelle partie en réinitialisant les attributs et en chargeant un nouveau mot à deviner.
     */
    private void initialiserPartie() {
        this.lettresProposees = new HashSet<>();
        this.tentativesRestantes = 10;
        this.chargerMotAleatoire();
        this.motCacheLabel.setText(this.getMotCache());
        this.penduLabel.setText("Tentatives restantes : " + this.tentativesRestantes);
        this.lettresProposeesLabel.setText("Lettres proposées : " + this.lettresProposees.toString());
        if (this.definition != null && !this.definition.isEmpty()) {
            this.definitionLabel.setText("Définition : " + this.definition);
        } else {
            this.definitionLabel.setText("Définition : Aucune");
        }
    }

    /**
     * Charge un mot aléatoire depuis un fichier et définit sa définition.
     */
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
            this.motADeviner = mots.get(index);
            this.definition = "";
            try (Scanner scannerDef = new Scanner(new File("mots.txt"))) {
                while (scannerDef.hasNextLine()) {
                    String line = scannerDef.nextLine();
                    if (line.toUpperCase().startsWith(this.motADeviner)) {
                        String[] parts = line.split("\\s+", 2);
                        this.definition = parts.length > 1 ? parts[1] : "";
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

    /**
     * Initialise l'interface graphique du jeu.
     */
    private void initialiserInterface() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new FlowLayout());
        panelNorth.add(this.motCacheLabel);

        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new FlowLayout());
        panelCenter.add(this.lettresProposeesLabel);

        JPanel panelSouth = new JPanel();
        panelSouth.setLayout(new FlowLayout());
        panelSouth.add(this.penduLabel);
        panelSouth.add(this.nouvellePartieButton);

        contentPane.add(panelNorth, BorderLayout.NORTH);
        contentPane.add(panelCenter, BorderLayout.CENTER);
        contentPane.add(panelSouth, BorderLayout.SOUTH);
        contentPane.add(this.definitionLabel, BorderLayout.WEST);
        contentPane.add(this.imagePenduLabel, BorderLayout.EAST); // Ajout du JLabel pour l'image du pendu

        this.setContentPane(contentPane);

        // Ajout de l'écouteur d'événements clavier à la fenêtre
        this.addKeyListener(new LettreKeyListener());
        this.setFocusable(true);
    }

    /**
     * Propose une lettre pour le jeu et met à jour l'interface en conséquence.
     * @param lettre La lettre proposée par le joueur.
     */
    void proposerLettre(String lettre) {
        if (lettre.length() == 1 && Character.isLetter(lettre.charAt(0))) {
            char lettreProposee = lettre.toUpperCase().charAt(0);
            if (!this.lettresProposees.contains(lettreProposee)) {
                this.lettresProposees.add(lettreProposee);
                if (!this.motADeviner.contains(String.valueOf(lettreProposee))) {
                    this.tentativesRestantes--;
                }
                this.mettreAJourInterface();
            } else {
                JOptionPane.showMessageDialog(this, "Cette lettre a déjà été proposée.");
            }
        }
    }

    /**
     * Obtient la représentation masquée du mot à deviner, remplaçant les lettres non devinées par des tirets.
     * @return La représentation masquée du mot à deviner.
     */
    public String getMotCache() {
        StringBuilder motCache = new StringBuilder();
        for (char c : this.motADeviner.toCharArray()) {
            if (this.lettresProposees.contains(c)) {
                motCache.append(c);
            } else {
                motCache.append('_');
            }
            motCache.append(' ');
        }
        return motCache.toString();
    }

    /**
     * Vérifie si le mot à deviner a été entièrement deviné par le joueur.
     * @return true si le mot a été deviné, sinon false.
     */
    private boolean estMotDevine() {
        for (char c : this.motADeviner.toCharArray()) {
            if (!this.lettresProposees.contains(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Met à jour l'interface graphique en fonction de l'état actuel du jeu.
     */
    private void mettreAJourInterface() {
        this.motCacheLabel.setText(this.getMotCache());
        this.lettresProposeesLabel.setText("Lettres proposées : " + this.lettresProposees.toString());
        this.penduLabel.setText("Tentatives restantes : " + this.tentativesRestantes);

        // Chargement de l'image du pendu correspondant au nombre d'erreurs
        int nombreErreurs = 10 - this.tentativesRestantes;
        if (nombreErreurs >= 0 && nombreErreurs <= 10) {
            String cheminImage = "images/pendu" + nombreErreurs + ".png";
            ImageIcon imagePendu = new ImageIcon(cheminImage);
            this.imagePenduLabel.setIcon(imagePendu);
        }

        if (this.tentativesRestantes <= 0 || this.estMotDevine()) {
            this.partieTerminee = true;
            if (this.estMotDevine()) {
                JOptionPane.showMessageDialog(this, "Bravo ! Vous avez deviné le mot !");
            } else {
                JOptionPane.showMessageDialog(this, "Désolé, vous avez épuisé toutes vos tentatives. Le mot était : " + this.motADeviner);
            }
            this.nouvellePartieButton.setEnabled(true);
        }
    }

    /**
     * Relance une nouvelle partie en réinitialisant les attributs et en démarrant une nouvelle partie.
     */
    private void relancerPartie() {
        this.partieTerminee = false;
        this.lettresProposees.clear();
        this.tentativesRestantes = 10;
        this.chargerMotAleatoire();
        this.initialiserPartie();
        this.nouvellePartieButton.setEnabled(false);
    }

    public Collection<Object> getLettresProposees() {
        return null;
    }

    // Classe interne pour écouter les événements clavier

    /**
     * Classe interne pour écouter les événements clavier.
     */
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

    // Méthode main pour démarrer le jeu

    /**
     * Méthode principale pour démarrer le jeu.
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        PenduGame jeu = new PenduGame();
        jeu.initialiserJeu();
    }
}
