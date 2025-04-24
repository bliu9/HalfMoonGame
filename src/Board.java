import java.awt.*;
import java.util.ArrayList;

public class Board
{
    private ArrayList<ArrayList<BoardTile>> board;
    private Game game;

    public Board(ArrayList<ArrayList<BoardTile>> board,Game game)
    {
        this.board = new ArrayList<>();
        this.game = game;
    }

    public Board(Game game)
    {
        this.board = new ArrayList<>();
        this.game = game;
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

    public void setNeighbors() {
    }
}
