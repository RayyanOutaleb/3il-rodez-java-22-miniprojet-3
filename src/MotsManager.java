package src;
import java.io.*;
import java.util.*;

public class MotsManager {
    private static final String FICHIER_MOTS = "mots.txt";

    public static String chargerMotAleatoire() {
        String motAleatoire = "";
        try (Scanner scanner = new Scanner(new File(FICHIER_MOTS))) {
            ArrayList<String> mots = new ArrayList<>();
            while (scanner.hasNextLine()) {
                mots.add(scanner.nextLine());
            }
            Random random = new Random();
            String motDef = mots.get(random.nextInt(mots.size()));
            String[] parts = motDef.split(" ");
            motAleatoire = parts[0].toUpperCase();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return motAleatoire;
    }
}
