import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest
{
    @Test // Test checkRow()
    public void checkRowTest()
    {
        Game testGame = new Game();
        Board testBoard = testGame.getPlayerBoard();
        int size = testBoard.getSize();

        // Check default
        for(int i = 0; i < size; i++)
        {
            assertFalse(testGame.checkRow(testBoard, i));
        }

        // Fill a row, check for horizontal win, repeat
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                testBoard.getMap()[i][j].setCalled(true);
                testBoard.getMap()[i][j].setSelected(true);
            }

            assertTrue(testGame.checkRow(testBoard, i));
        }
    }

    @Test // Test checkHorizontal()
    public void checkHorizontalTest()
    {
        Game testGame = new Game();
        Board testBoard = testGame.getPlayerBoard();
        int size = testBoard.getSize();

        // Check default
        assertEquals("N/A", testGame.checkHorizontal(testBoard));

        // Fill a row, check for horizontal win, reset, repeat
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                testBoard.getMap()[i][j].setCalled(true);
                testBoard.getMap()[i][j].setSelected(true);
            }

            assertEquals("horizontal" + i, testGame.checkHorizontal(testBoard));
            testGame.reset();
        }
    }

    @Test // Test checkColumn()
    public void checkColumnTest()
    {
        Game testGame = new Game();
        Board testBoard = testGame.getPlayerBoard();
        int size = testBoard.getSize();

        // Check default
        for(int i = 0; i < size; i++)
        {
            assertFalse(testGame.checkColumn(testBoard, i));
        }

        // Fill a row, check for horizontal win, repeat
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                testBoard.getMap()[j][i].setCalled(true);
                testBoard.getMap()[j][i].setSelected(true);
            }

            assertTrue(testGame.checkColumn(testBoard, i));
        }
    }

    @Test // Test checkVerticle()
    public void checkVerticleTest()
    {
        Game testGame = new Game();
        Board testBoard = testGame.getPlayerBoard();
        int size = testBoard.getSize();

        // Check default
        assertEquals("N/A", testGame.checkVerticle(testBoard));

        // Fill a column, check for verticle win, reset, repeat
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                testBoard.getMap()[j][i].setCalled(true);
                testBoard.getMap()[j][i].setSelected(true);
            }

            assertEquals("verticle" + i, testGame.checkVerticle(testBoard));
            testGame.reset();
        }
    }

    @Test // Test checkDiagonal()
    public void checkDiagonalTest()
    {
        Game testGame = new Game();
        Board testBoard = testGame.getPlayerBoard();
        int size = testBoard.getSize();
        int j = 4;

        // Check default
        assertEquals("N/A", testGame.checkDiagonal(testBoard));

        // Fill left diagonal, check, reset
        for(int i = 0; i < size; i++)
        {
            testBoard.getMap()[i][i].setCalled(true);
            testBoard.getMap()[i][i].setSelected(true);
        }

        assertEquals("diagonalL", testGame.checkDiagonal(testBoard));
        testGame.reset();

        // Fill right diagonal, check, reset
        for(int i = 0; i < size; i++)
        {
            testBoard.getMap()[i][j].setCalled(true);
            testBoard.getMap()[i][j].setSelected(true);
            j--;
        }

        assertEquals("diagonalR", testGame.checkDiagonal(testBoard));
        testGame.reset();

        j = 4;

        // Fill both diagonals, check for left
        for(int i = 0; i < size; i++)
        {
            // Left
            testBoard.getMap()[i][i].setCalled(true);
            testBoard.getMap()[i][i].setSelected(true);

            // Right
            testBoard.getMap()[i][j].setCalled(true);
            testBoard.getMap()[i][j].setSelected(true);
            j--;
        }

        assertEquals("diagonalL", testGame.checkDiagonal(testBoard));
    }
}
