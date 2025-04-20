import java.awt.*;
import java.util.ArrayList;

public class Board
{
    ArrayList<ArrayList<BoardTile>> board;

    public Board(ArrayList<ArrayList<BoardTile>> board)
    {
        this.board = new ArrayList<>();
    }

    public ArrayList<ArrayList<BoardTile>> getBoard()
    {
        return board;
    }

    public void printBoard(Graphics g)
    {
        //printBoardHelper();
        g.drawRect(300,300,100,100);
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
