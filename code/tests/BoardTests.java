package tests;

import org.junit.jupiter.api.Test;
import src.*;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {

    private void testDistance(Board b, String startSting, String endString, int realDist) {

        assertTrue(b.calcPathFromStrings(startSting, endString, realDist));
        assertTrue(b.calcPathFromStrings(startSting, endString, realDist + 1));
        assertFalse(b.calcPathFromStrings(startSting, endString, realDist - 1));

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
        testDistance(b, "Q21","B24", 11);
    }

    @Test void test_board() {
        Board b = new Board();

        for (Room.RoomAlias roomAlias : Room.RoomAlias.values()) {
            assertEquals(roomAlias, b.getRooms().get(roomAlias).getRoomAlias());
        }

        for (Sprite.SpriteAlias spriteAlias : Sprite.SpriteAlias.values()) {
            assertEquals(spriteAlias, b.getSprites().get(spriteAlias).getSpriteAlias());
        }

        for (Weapon.WeaponAlias weaponAlias : Weapon.WeaponAlias.values()) {
            assertEquals(weaponAlias, b.getWeapons().get(weaponAlias).getWeaponAlias());
        }

        User user = new User();
        assertNull(user.getSprite());

        user.setSprite(b.getSprites().get(Sprite.SpriteAlias.MRS_WHITE));
        assertEquals(b.getSprites().get(Sprite.SpriteAlias.MRS_WHITE),user.getSprite());

//        b.moveCharacter(user.getSprite(), );
    }


}
