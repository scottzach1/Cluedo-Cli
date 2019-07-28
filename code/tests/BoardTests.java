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

    @Test void testPathFinding() {
        Board b = new Board();

        testDistance(b, "H1", "H3", 2);
        testDistance(b, "H1", "G6", 6);
        testDistance(b, "B2","H2", 9);
        testDistance(b, "Q21","B24", 11);
    }

    @Test void testBoard() {
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

        assertNull(b.getCell("H3").getSprite());
        assertEquals(user.getSprite(), b.getCell("H1").getSprite());

        b.moveCharacter(user, b.getCell("H1"), b.getCell("H3"));

        assertNull(b.getCell("H1").getSprite());
        assertEquals(user.getSprite(), b.getCell("H3").getSprite());

        try {
            b.moveCharacter(user, b.getCell("H-1"), b.getCell("H3"));
            fail("Invalid Move didn't throw Exception");
        } catch (Exception e) {}

        try {
            b.moveCharacter(null, b.getCell("H1"), b.getCell("H3"));
            fail("Invalid Move didn't throw Exception");
        } catch (Exception e) {}


    }

    @Test void testCellToString() {
        Cell cell = new Cell(5, 5, Cell.Type.WALL);
        assertEquals(cell.printCoordinates(), "F6");
    }

    @Test void testRoom() {
        Board b = new Board();

        assertEquals(1, b.getRooms().get(Room.RoomAlias.KITCHEN).getDoors().size());
        assertEquals(4, b.getRooms().get(Room.RoomAlias.BALLROOM).getDoors().size());
        assertEquals(1, b.getRooms().get(Room.RoomAlias.CONSERVATORY).getDoors().size());
        assertEquals(2, b.getRooms().get(Room.RoomAlias.LIBRARY).getDoors().size());
        assertEquals(1, b.getRooms().get(Room.RoomAlias.STUDY).getDoors().size());
        assertEquals(4, b.getRooms().get(Room.RoomAlias.HALL).getDoors().size());
        assertEquals(1, b.getRooms().get(Room.RoomAlias.LOUNGE).getDoors().size());
        assertEquals(2, b.getRooms().get(Room.RoomAlias.DINING_ROOM).getDoors().size());
    }

    @Test void testSpriteAndUser() {
        Board b = new Board();
        User user = new User();

        for (Sprite sprite : b.getSprites().values()) {
            sprite.setUser(user);
            assertEquals(sprite, user.getSprite());
            assertEquals(user, sprite.getUser());
        }

        for (Sprite sprite : b.getSprites().values()) {
            user.setSprite(sprite);
            assertEquals(sprite, user.getSprite());
            assertEquals(user, sprite.getUser());
        }


    }


}
