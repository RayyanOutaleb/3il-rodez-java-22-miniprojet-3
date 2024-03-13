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
    private JLabel imagePenduLabel; // JLabel pour afficher l'image du pendu

    private static final int FRAME_WIDTH = 2000;
    private static final int FRAME_HEIGHT = 1000;

    public PenduGame() {
        this.motCacheLabel = new JLabel();
        this.lettresProposeesLabel = new JLabel();
        this.penduLabel = new JLabel();
        this.lettreField = new JTextField(1);
        this.nouvellePartieButton = new JButton("Relancer une Partie");
        this.imagePenduLabel = new JLabel(); // Initialisation du JLabel pour l'image du pendu

        this.nouvellePartieButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                relancerPartie();
            }
        });

        this.definitionLabel = new JLabel("Définition : ");
        this.partieTerminee = false;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setLocationRelativeTo(null);
    }

    public void initialiserJeu() {
        this.initialiserInterface();
        this.initialiserPartie();
        this.setVisible(true);
    }

    void initialiserPartie() {
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

    String getMotCache() {
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

    private boolean estMotDevine() {
        for (char c : this.motADeviner.toCharArray()) {
            if (!this.lettresProposees.contains(c)) {
                return false;
            }
        }
        return true;
    }

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

    private void relancerPartie() {
        this.partieTerminee = false;
        this.lettresProposees.clear();
        this.tentativesRestantes = 10;
        this.chargerMotAleatoire();
        this.initialiserPartie();
        this.nouvellePartieButton.setEnabled(false);
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

    // Getters pour les tests unitaires
    public String getMotADeviner() {
        return motADeviner;
    }

    public String getDefinition() {
        return definition;
    }

    public Set<Character> getLettresProposees() {
        return lettresProposees;
    }

    public int getTentativesRestantes() {
        return tentativesRestantes;
    }

    public static void main(String[] args) {
        PenduGame jeu = new PenduGame();
        jeu.initialiserJeu();
    }
}
