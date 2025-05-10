// Bryan Liu for CS2

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
        // Draw all board tiles
        for (ArrayList<BoardTile> row : board)
        {
            for (BoardTile bt : row)
            {
                if (!bt.isWall())
                {
                    bt.draw(g);
                }
            }
        }
    }

}
