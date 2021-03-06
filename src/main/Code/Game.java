package main.Code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * The Rat Bingo program is a bingo game with a rat theme.
 *
 * @author  Giana (Github: G-i-a-n-a - Website: Giana.dev)
 * @version 1.0.1
 */
public class Game
{
    private Board playerBoard;                                 // Board for the Player
    private Board npcBoard;                                    // Board for the NPC
    private String mode;                                       // Game mode
    private ArrayList<Integer> toCall = new ArrayList<>();     // List of random numbers to call
    private ArrayList<String> called = new ArrayList<>();      // List of numbers already called
    private NPC npcPlayer;                                     // NPC playing against Player
    private Player player;                                     // The Player
    private ArrayList<Integer> gameData = new ArrayList<>();   // List for game stats
    private boolean soundStatus = true;                        // If sound is on/muted

    /**
     * This is the default constructor for Game.
     * It creates new instances of a Player and
     * an NPC, and generates a new Board for the
     * Player, and a new Board for the NPC. It
     * resets the Player and NPC Boards back to
     * default, as well as the call lists. It
     * creates a new shuffled list of numbers to
     * call, and adds the free space to the list
     * of numbers already called.
     */
    public Game()
    {
        playerBoard = new Board();
        player = new Player();
        npcBoard = new Board();
        npcPlayer = new NPC();
        reset();
        callsGenerator();
        called.add("FREE");
    }

    /**
     * This is the parameterized constructor
     * for Game. It creates new instances of a
     * Player and an NPC, and generates a new
     * Board for the Player, and a new Board for
     * the NPC. It resets the Player and NPC
     * Boards back to default, as well as the call
     * lists. It creates a new shuffled list of
     * numbers to call, and adds the free space to
     * the list of numbers already called. It also
     * assigns a game mode to the Game.
     * @param mode Game mode to assign to Game
     */
    public Game(String mode)
    {
        playerBoard = new Board();
        player = new Player();
        npcBoard = new Board();
        npcPlayer = new NPC();
        this.mode = mode;
        reset();
        callsGenerator();
        called.add("FREE");
    }

    /**
     * @return Board Player's Board
     */
    public Board getPlayerBoard() { return playerBoard; }

    /**
     * @return Board NPC's Board
     */
    public Board getNpcBoard() { return npcBoard; }

    /**
     * @param mode Mode to assign to Game
     */
    public void setMode(String mode) { this.mode = mode; }

    /**
     * @return ArrayList<Integer> List of numbers to call
     */
    public ArrayList<Integer> getToCall() { return toCall; }

    /**
     * @return ArrayList<Integer> List of numbers called
     */
    public ArrayList<String> getCalled() { return called; }

    /**
     * @return Player Game's Player
     */
    public Player getPlayer() { return player; }

    /**
     * @return NPC Game's NPC player
     */
    public NPC getNpcPlayer() { return npcPlayer; }

    /**
     * @return boolean Status of sound (toggled on/off)
     */
    public boolean getSoundStatus() { return soundStatus; }

    /**
     * @param soundStatus Sound status to assign to Game (toggle on/off)
     */
    public void setSoundStatus(boolean soundStatus) { this.soundStatus = soundStatus; }

    /**
     * This method is used to check a given row
     * on a Board to see if each Tile is selected
     * and called, indicating a horizontal win.
     * @param board Board to be checked
     * @param row Row to be checked
     * @return boolean If row is a win
     */
    public boolean checkRow(Board board, int row)
    {
        int size = board.getSize();

        for(int i = 0; i < size; i++)
        {
            if(!(board.getMap()[row][i].getSelected() &&
                    called.contains(board.getMap()[row][i].toString())))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * This method is used to check each row on
     * a Board for a horizontal win.
     * @param board Board to be checked
     * @return String "N/A" if no win, "horizontal"
     *         + row if win
     */
    public String checkHorizontal(Board board)
    {
        int size = board.getSize();

        for(int i = 0; i < size; i++)
        {
            if(checkRow(board, i))
            {
                return "horizontal" + i;
            }
        }

        return "N/A";
    }

    /**
     * This method is used to check a given column
     * on a Board to see if each Tile is selected
     * and called, indicating a vertical win.
     * @param board Board to be checked
     * @param column Column to be checked
     * @return boolean If column is a win
     */
    public boolean checkColumn(Board board, int column)
    {
        int size = board.getSize();

        for(int i = 0; i < size; i++)
        {
            if(!(board.getMap()[i][column].getSelected() &&
                    called.contains(board.getMap()[i][column].toString())))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * This method is used to check each column on
     * a Board for a vertical win.
     * @param board Board to be checked
     * @return String "N/A" if no win, "vertical"
     *         + column if win
     */
    public String checkVertical(Board board)
    {
        int size = board.getSize();

        for(int i = 0; i < size; i++)
        {
            if(checkColumn(board, i))
            {
                return "vertical" + i;
            }
        }

        return "N/A";
    }

    /**
     * This method is used to check each diagonal
     * on a Board for a diagonal win.
     * @param board Board to be checked
     * @return String "N/A" if no win, "diagonal"
     *         + direction(s) if win
     */
    public String checkDiagonal(Board board)
    {
        int size = board.getSize();
        int j = 4;
        String rightState = "N/A";
        String leftState = "N/A";

        // Check from top left
        for(int i = 0; i < size; i++)
        {
            if(board.getMap()[i][i].getSelected() &&
                    called.contains(board.getMap()[i][i].toString()))
            {
                leftState = "diagonal";
            }
            else
            {
                leftState = "N/A";
                break;
            }
        }

        // Check from top right
        for(int i = 0; i < size; i++)
        {
            if(board.getMap()[i][j].getSelected() &&
                    called.contains(board.getMap()[i][j].toString()))
            {
                rightState = "diagonal";
                j--;
            }
            else
            {
                rightState = "N/A";
                break;
            }
        }

        // Only a left diagonal
        if(leftState.equals("diagonal") && rightState.equals("N/A"))
        {
            return "diagonalL";
        }
        // Only a right diagonal
        else if(leftState.equals("N/A") && rightState.equals("diagonal"))
        {
            return "diagonalR";
        }
        // Both diagonals
        else if(leftState.equals("diagonal") && rightState.equals("diagonal"))
        {
            return "diagonalLR";
        }
        // Nothing was found
        else
        {
            return "N/A";
        }
    }

    /**
     * This method is used to check both
     * diagonals on a Board for an X win.
     * @param board Board to be checked
     * @return String "N/A" if no win,
     *         "x" if win
     */
    public String checkX(Board board)
    {
        String state = checkDiagonal(board);

        // Two diagonals found (an X)
        if(state.equals("diagonalLR"))
        {
            return "x";
        }
        else
        {
            return "N/A";
        }
    }

    /**
     * This method is used to check all 4
     * corners on a Board for a corners win.
     * @param board Board to be checked
     * @return String "N/A" if no win,
     *         "corners" if win
     */
    public String checkCorners(Board board)
    {
        int lowerBound = 0;
        int upperBound = (board.getSize() - 1);
        String state = "N/A";

        if(board.getMap()[lowerBound][lowerBound].getSelected() &&
                called.contains(board.getMap()[lowerBound][lowerBound].toString()) &&
                board.getMap()[lowerBound][upperBound].getSelected() &&
                called.contains(board.getMap()[lowerBound][upperBound].toString()) &&
                board.getMap()[upperBound][lowerBound].getSelected() &&
                called.contains(board.getMap()[upperBound][lowerBound].toString()) &&
                board.getMap()[upperBound][upperBound].getSelected() &&
                called.contains(board.getMap()[upperBound][upperBound].toString()))
        {
            state = "corners";
        }

        return state;
    }

    /**
     * This method is used to check row
     * 0 and column 2 for a T win.
     * @param board Board to be checked
     * @return String "N/A" if no win,
     *         "t" if win
     */
    public String checkT(Board board)
    {
        int row = 0;
        int column = 2;

        // Check row and column that make a T
        if(checkRow(board, row) && checkColumn(board, column))
        {
            return "t";
        }

        // Nothing found
        return "N/A";
    }

    /**
     * This method is used to check row
     * 4 and column 0 for an L win.
     * @param board Board to be checked
     * @return String "N/A" if no win,
     *         "l" if win
     */
    public String checkL(Board board)
    {
        int row = 4;
        int column = 0;

        // Check row and column that make a L
        if(checkRow(board, row) && checkColumn(board, column))
        {
            return "l";
        }

        // Nothing found
        return "N/A";
    }

    /**
     * This method is used to check every
     * Tile on the Board for an all win.
     * @param board Board to be checked
     * @return String "N/A" if no win,
     *         "all" if win
     */
    public String checkAll(Board board)
    {
        int size = board.getSize();

        for(int i = 0; i < size; i++)
        {
            if(!(checkRow(board, i)))
            {
                return "N/A";
            }
        }

        return "all";
    }

    /**
     * This method is used to reset the Player
     * and NPC Boards to have no selections. It
     * also clears the toCall and called lists.
     */
    public void reset()
    {
        int size = playerBoard.getSize();

        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                playerBoard.getMap()[i][j].setSelected(false);
                npcBoard.getMap()[i][j].setSelected(false);
            }
        }

        toCall.clear();
        called.clear();
    }

    /**
     * This method is used to check a Board
     * for a win based on the game mode.
     * @param board Board to be checked
     * @return String "N/A" if no win,
     *         designated String for win
     *         variety if win
     */
    public String checkWin(Board board)
    {
        String state = "N/A";

        switch(mode)
        {
            case "horizontal":
                state = checkHorizontal(board);
                break;
            case "vertical":
                state = checkVertical(board);
                break;
            case "diagonal":
                state = checkDiagonal(board);
                break;
            case "x":
                state = checkX(board);
                break;
            case "corners":
                state = checkCorners(board);
                break;
            case "t":
                state = checkT(board);
                break;
            case "l":
                state = checkL(board);
                break;
            case "all":
                state = checkAll(board);
                break;
        }

        return state;
    }

    /**
     * This method is used to initialize
     * toCall to have numbers 1 through
     * 75, shuffled.
     */
    public void callsGenerator()
    {
        // Add possible numbers to calls list (1 - 75)
        for(int i = 1; i <= 75; i++)
        {
            toCall.add(i);
        }

        // Shuffle so it's not in order
        Collections.shuffle(toCall);
    }

    /**
     * This method is used to return the
     * first number in toCall, remove it
     * from toCall, and add it to called.
     * @return int Number called
     */
    public int runCaller()
    {
        int calling = toCall.get(0);

        // Add to list of numbers called
        called.add(Integer.toString(calling));

        toCall.remove(0);

        return calling;
    }

    /**
     * This method is used to save the total wins,
     * losses, dollars, and rats for Player into
     * the Save.txt file.
     * @throws IOException On file error
     */
    public void saveGame() throws IOException
    {
        FileWriter fileWriter = new FileWriter("Save.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.println(player.getTotalWins());
        printWriter.println(player.getTotalLosses());
        printWriter.println(player.getTotalDollars());
        printWriter.println(player.getTotalHooded());
        printWriter.println(player.getTotalAgouti());
        printWriter.println(player.getTotalBerkshire());
        printWriter.println(player.getTotalRoan());
        printWriter.println(player.getTotalAlbino());
        printWriter.println(player.getTotalSiamese());
        printWriter.println(player.getTotalHairless());
        printWriter.println(player.getTotalRussianBlue());
        printWriter.println(player.getTotalPatchwork());
        printWriter.println(player.getTotalManx());

        printWriter.close();
    }

    /**
     * This method is used to load the total wins,
     * losses, dollars, and rats for Player from
     * the Save.txt file, into Player variables.
     * @throws IOException On file error
     */
    public void loadGame() throws IOException
    {
        File saveFile = new File("Save.txt");
        Scanner input = new Scanner(saveFile);

        while(input.hasNextLine())
        {
            gameData.add(Integer.parseInt(input.nextLine()));
        }

        if(!gameData.isEmpty())
        {
            player.setTotalWins(gameData.get(0));
            player.setTotalLosses(gameData.get(1));
            player.setTotalDollars(gameData.get(2));
            player.setTotalHooded(gameData.get(3));
            player.setTotalAgouti(gameData.get(4));
            player.setTotalBerkshire(gameData.get(5));
            player.setTotalRoan(gameData.get(6));
            player.setTotalAlbino(gameData.get(7));
            player.setTotalSiamese(gameData.get(8));
            player.setTotalHairless(gameData.get(9));
            player.setTotalRussianBlue(gameData.get(10));
            player.setTotalPatchwork(gameData.get(11));
            player.setTotalManx(gameData.get(12));
        }
    }

    /**
     * This method is used to clear the Save.txt
     * file by replacing every value with a 0.
     * @throws IOException On file error
     */
    public void deleteSave() throws IOException
    {
        FileWriter fileWriter = new FileWriter("Save.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for(int i = 0; i < 13; i++)
        {
            printWriter.println("0");
        }

        printWriter.close();
    }
}