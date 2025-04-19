import java.util.ArrayList;

public class Board
{
    ArrayList<ArrayList<BoardTile>> board;

    public Board(ArrayList<ArrayList<BoardTile>> board)
    {
        this.board = board;
    }

    public void printBoard()
    {
        //printBoardHelper();
    }

    public void printBoardHelper(BoardTile current)
    {
        //current.draw();
        // for each thing in the neighbors of the board tile, call the print board helper on it
        for (BoardTile neighbor : current.getNeighbors())
        {
            printBoardHelper(neighbor);
        }
    }
}
