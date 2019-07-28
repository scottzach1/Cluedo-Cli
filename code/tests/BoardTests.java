package tests;

import org.junit.jupiter.api.Test;
import src.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {

    private void testDistance(Board b, String startSting, String endString, int realDist) {
        Cell start = b.getCell(startSting);
        Cell end = b.getCell(endString);

        PathFinder pathFinder = new PathFinder(b);

        assertTrue(pathFinder.checkValidPath(start, end, realDist));
        assertTrue(pathFinder.checkValidPath(end, start, realDist));

        assertTrue(pathFinder.checkValidPath(start, end, realDist + 1));
        assertTrue(pathFinder.checkValidPath(end, start, realDist + 1));

        assertFalse(pathFinder.checkValidPath(start, end, realDist - 1));
        assertFalse(pathFinder.checkValidPath(end, start, realDist - 1));

        assertTrue(pathFinder.checkValidPathFromString(startSting, endString, realDist));
        assertTrue(pathFinder.checkValidPathFromString(endString, startSting, realDist));

        assertTrue(pathFinder.checkValidPathFromString(startSting, endString, realDist + 1));
        assertTrue(pathFinder.checkValidPathFromString(endString, startSting, realDist + 1));

        assertFalse(pathFinder.checkValidPathFromString(startSting, endString, realDist - 1));
        assertFalse(pathFinder.checkValidPathFromString(endString, startSting, realDist - 1));
    }

    @Test void testPathFinding() {
        Board b = new Board();
        PathFinder pathFinder = new PathFinder(b);

        testDistance(b, "H2", "H4", 2);
        testDistance(b, "H2", "G7", 6);
        testDistance(b, "B2","H2", 9);
        testDistance(b, "Q21","B24", 11);
        testDistance(b, "H2", "H7", 5);


        // End is a wall
        assertFalse(pathFinder.checkValidPathFromString("H1", "F2", 10));

        assertNotNull(b.getCell("H1").getSprite());
        assertNull(b.getCell("H5").getSprite());

        // End holds a character
        assertFalse(pathFinder.checkValidPath(b.getCell("H5"), b.getCell("H1"), 10));
        assertFalse(pathFinder.checkValidPathFromString("H5", "H1", 10));
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

        b.moveUser(user, b.getCell("H1"), b.getCell("H3"));

        assertNull(b.getCell("H1").getSprite());
        assertEquals(user.getSprite(), b.getCell("H3").getSprite());

        try {
            b.moveUser(user, b.getCell("H-1"), b.getCell("H3"));
            fail("Invalid Move didn't throw Exception");
        } catch (Exception e) {}

        try {
            b.moveUser(null, b.getCell("H1"), b.getCell("H3"));
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

        Queue<String> roomAliasStrings = new ArrayDeque<>(Arrays.asList("KITCHEN", "BALLROOM", "CONSERVATORY", "BILLARD_ROOM", "DINING_ROOM", "LIBRARY", "LOUNGE", "HALL", "STUDY"));
        Queue<Character> roomCharList = new ArrayDeque<>(Arrays.asList('K', 'B', 'C', 'A', 'D', 'L', 'E', 'H', 'S'));
        for (int i = 0; i < Room.RoomAlias.values().length; i++) {
            assertEquals(roomAliasStrings.peek(), Room.parseAliasFromOrdinalInt(i).toString());
            assertEquals(roomAliasStrings.poll(), Room.parseAliasFromChar(roomCharList.poll()).toString());
        }

        User user1 = new User(), user2 = new User();

        user1.setSprite(b.getSprites().get(Sprite.SpriteAlias.MRS_WHITE));
        user2.setSprite(b.getSprites().get(Sprite.SpriteAlias.COLONEL_MUSTARD));

        b.moveUser(user1, user1.getSprite().getPosition(), b.getCell("B24"));
        b.moveUser(user2, user2.getSprite().getPosition(), b.getCell("E20"));

        Set<User> expectedUsers = new HashSet<>(Arrays.asList(user1, user2));
        Set<User> recordedUsers = b.getRooms().get(Room.RoomAlias.LOUNGE).getInThisRoom();

        assertEquals(expectedUsers.size(), recordedUsers.size());
        assertTrue(recordedUsers.containsAll(expectedUsers));
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

        for (Sprite.SpriteAlias spriteAlias : Sprite.SpriteAlias.values()) {
            Sprite sprite = b.getSprites().get(spriteAlias);
            switch (sprite.getSpriteAlias()) {
                case MRS_WHITE:
                    assertEquals("W", sprite.toString());
                    break;
                case MR_GREEN:
                    assertEquals("G", sprite.toString());
                    break;
                case MRS_PEACOCK:
                    assertEquals("Q", sprite.toString());
                    break;
                case PROFESSOR_PLUM:
                    assertEquals("P", sprite.toString());
                    break;
                case MISS_SCARLETT:
                    assertEquals("S", sprite.toString());
                    break;
                case COLONEL_MUSTARD:
                    assertEquals("M", sprite.toString());
                    break;
            }
        }

        Queue<String> spriteAliasStrings = new ArrayDeque<>(Arrays.asList("MRS_WHITE", "MR_GREEN", "MRS_PEACOCK", "PROFESSOR_PLUM", "MISS_SCARLETT", "COLONEL_MUSTARD"));

        for (int i = 0; i < Sprite.SpriteAlias.values().length; i++) {
            assertEquals(spriteAliasStrings.peek(), Sprite.parseAliasFromOrdinalInt(i).toString());
            assertEquals(spriteAliasStrings.poll(), Sprite.parseAliasFromOrdinalChar((i + "").charAt(0)).toString());
        }
    }


}
