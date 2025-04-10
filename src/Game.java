import java.util.ArrayList;

public class Game
{
    ArrayList<ArrayList<BoardTile>> board;
    private final int NUMBER_OF_BOARDS = 3;

    public void generateBoard()
    {
        // Picking a random board
        int boardType = (int)(Math.random()*NUMBER_OF_BOARDS);

        // Generating each board
        if (boardType == 1)
        {
            //code for generating one arrangement of the board
        }
        else if (boardType == 2)
        {
            //code for generating second arrangement of the board
        }
        else if (boardType == 3)
        {
            //code for generating third arrangement of the board
        }

        // Debugging
        System.out.println("error making board");
    }
}
