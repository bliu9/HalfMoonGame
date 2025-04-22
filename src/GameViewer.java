import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameViewer extends JFrame
{
    private Game game;
    private final int WINDOW_WIDTH = 600;
    private final int WINDOW_HEIGHT = 800;
    private final int TITLE_BAR_HEIGHT = 30;
    private ArrayList<Image> moonTileImages;
    private Image backgroundImage;
    private Image topMoonImage;

    public GameViewer(Game game)
    {
        // Create backend reference
        this.game = game;

        // Set up image resources
        this.topMoonImage = new ImageIcon("Resources/fullmoon.png").getImage();
        this.backgroundImage = new ImageIcon("Resources/background.jpg").getImage();
        moonTileImages = new ArrayList<Image>();
        for (int i=0; i<52; i++)
        {
            moonTileImages.add(new ImageIcon("Resources/"+i+".png").getImage());
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

        game.getCurrentPlayer().draw(g);
        game.getBoard().draw(g);
    }
}
