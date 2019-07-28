package tests;

import org.junit.jupiter.api.Test;
import src.*;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {

    private void testDistance(Board b, String startSting, String endString, int realDist) {
        Cell start = b.getCell(startSting);
        Cell end = b.getCell(endString);

        assertTrue(PathFinder.checkValidPath(start, end, realDist));
        assertTrue(PathFinder.checkValidPath(end, start, realDist));

        assertTrue(PathFinder.checkValidPath(start, end, realDist + 1));
        assertTrue(PathFinder.checkValidPath(end, start, realDist + 1));

        assertFalse(PathFinder.checkValidPath(start, end, realDist - 1));
        assertFalse(PathFinder.checkValidPath(end, start, realDist - 1));

        assertTrue(PathFinder.checkValidPathFromString(startSting, endString, realDist));
        assertTrue(PathFinder.checkValidPathFromString(endString, startSting, realDist));

        assertTrue(PathFinder.checkValidPathFromString(startSting, endString, realDist + 1));
        assertTrue(PathFinder.checkValidPathFromString(endString, startSting, realDist + 1));

        assertFalse(PathFinder.checkValidPathFromString(startSting, endString, realDist - 1));
        assertFalse(PathFinder.checkValidPathFromString(endString, startSting, realDist - 1));
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
    }


}
