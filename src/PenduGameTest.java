package src;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PenduGameTest {

    @Test
    public void testGetMotCache() {
        PenduGame penduGame = new PenduGame();
        penduGame.initialiserJeu();
        String motCache = penduGame.getMotCache();
        assertNotNull(motCache);
    }

    @Test
    public void testProposerLettre() {
        PenduGame penduGame = new PenduGame();
        penduGame.initialiserJeu();
        penduGame.proposerLettre("A");
        assertEquals(penduGame.getLettresProposees().size(), 1);
    }
}
