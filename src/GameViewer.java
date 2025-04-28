import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameViewer extends JFrame
{
    private Game game;
    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 800;
    public static final int HAND_TILE_GAP = 25;
    public static final int HAND_Y_COORD = 600;
    private final int TITLE_BAR_HEIGHT = 30;
    private ArrayList<Image> playerMoonTileImages;
    private ArrayList<Image> computerMoonTileImages;
    private Image backgroundImage;
    private Image topMoonImage;

    public GameViewer(Game game)
    {
        // Create backend reference
        this.game = game;

        // Set up image resources
        this.topMoonImage = new ImageIcon("Resources/fullmoon.png").getImage();
        this.backgroundImage = new ImageIcon("Resources/background.jpg").getImage();
        playerMoonTileImages = new ArrayList<>();
        for (int i=0; i<8; i++)
        {
            playerMoonTileImages.add(new ImageIcon("Resources/p"+i+".png").getImage());
        }
        computerMoonTileImages = new ArrayList<>();
        for (int i=0; i<8; i++)
        {
            computerMoonTileImages.add(new ImageIcon("Resources/c"+i+".png").getImage());
        }

        // Set up the window and set it to visible
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Half Moon Game");
        this.setLocationRelativeTo(null);
        this.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g)
    {
        //Draw background
        g.drawImage(backgroundImage,0,0,WINDOW_WIDTH,WINDOW_HEIGHT,this);
        g.drawImage(topMoonImage,40,-250,500,500,this);

        game.getCurrentPlayer().setHandCoordinates();

        game.getCurrentPlayer().draw(g);
        game.getBoard().draw(g);
    }

    public ArrayList<Image> getComputerMoonTileImages() {
        return computerMoonTileImages;
    }

    public ArrayList<Image> getPlayerMoonTileImages() {
        return playerMoonTileImages;
    }
}
