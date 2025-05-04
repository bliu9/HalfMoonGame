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
        // Draw each tile in the board
        for (ArrayList<BoardTile> b : board)
        {
            for (BoardTile bt : b)
            {
                printBoardHelper(bt,g,bt.getX(),bt.getY());
            }
        }

        // After board is fully drawn, go through each tile and reset the isDrawn variables back to false for the next
        //time the board is drawn
        for (ArrayList<BoardTile> b : board)
        {
            for (BoardTile bt : b)
            {
                bt.setDrawn(false);
            }
        }
    }

    public void printBoardHelper(BoardTile current, Graphics g, int prevX, int prevY)
    {
        // If the current tile has not been drawn and isn't a wall, draw it with the adjacent connector
        if (!current.isDrawn() && !current.isWall())
        {
            // Default board tile color
            g.setColor(Color.darkGray);

            // Call draw method for board tile and update isDrawn
            current.draw(g);
            current.setDrawn(true);

            // Draw connector line
            g.drawLine(prevX,prevY, current.getX(), current.getY());
        }

        // for each thing in the neighbors of the board tile, call the print board helper on it
        for (BoardTile neighbor : current.getNeighbors())
        {
            printBoardHelper(neighbor,g,current.getX(),current.getY());
        }
    }
}
