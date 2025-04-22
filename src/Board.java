import java.awt.*;
import java.util.ArrayList;

public class Board
{
    ArrayList<ArrayList<BoardTile>> board;

    public Board(ArrayList<ArrayList<BoardTile>> board)
    {
        this.board = new ArrayList<>();
    }

    public Board()
    {
        this.board = new ArrayList<>();
    }

    public ArrayList<ArrayList<BoardTile>> getBoard()
    {
        return board;
    }

    public void setBoard(ArrayList<ArrayList<BoardTile>> newBoard)
    {
        this.board = newBoard;
    }

    public void draw(Graphics g)
    {
        //printBoardHelper();
        for (ArrayList<BoardTile> b : board)
        {
            for (BoardTile bt : b)
            {
                bt.draw(g);
            }
        }
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
