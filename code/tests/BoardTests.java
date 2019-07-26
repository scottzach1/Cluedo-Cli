package tests;

import org.junit.jupiter.api.Test;
import src.Board;
import src.Cell;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {

    public void testDistance(Board b, String startSting, String endString, int realDist) {
        Cell start = b.getCell(startSting);
        Cell end = b.getCell(endString);

        assertEquals(realDist, b.calcNumSteps(start, end));
        assertEquals(realDist, b.calcNumSteps(end, start));

        assertTrue(b.calcValidPath(start, end, realDist));
        assertTrue(b.calcValidPath(end, start, realDist));

        assertFalse(b.calcValidPath(start, end, realDist - 1));
        assertTrue(b.calcValidPath(end, start, realDist + 1));
    }

    @Test void test_pathFinding() {
        Board b = new Board();

        testDistance(b, "H1", "H3", 2);
        testDistance(b, "H1", "G6", 6);
        testDistance(b, "B2","H2", 9);
        testDistance(b, "Q21","B24", 12);

    }
}
