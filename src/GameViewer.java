// Bryan Liu for CS2

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameViewer extends JFrame
{
    private static final int PLAY_TILE_X = 173;
    private static final int PLAY_TILE_Y = 150;
    private static final int GAME_OVER_X = 161+25;
    private static final int GAME_OVER_Y = 150;
    private static final int POSS_H_X = 50;
    private static final int POSS_H_Y = 150;
    private static final int POSS_C_X = 475;
    private static final int POSS_C_Y = 150;
    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 800;
    public static final int HAND_TILE_GAP = 25;
    public static final int HAND_Y_COORD = 685;
    public static final int TITLE_BAR_HEIGHT = 70;
    public static final int POINTS_FONT_SIZE = 30;
    private static final int PLAY_TILE_FONT_SIZE = 25;
    private static final int PLAY_AGAIN_FONT_SIZE = 50;
    public static final int H_PLAYER_PTS_START = 20;
    public static final int C_PLAYER_PTS_START_SUB = 275;
    private static final int TIE_X = 225;
    private static final int TIE_Y = 300;
    private static final int WIN_X = 115;
    private static final int WIN_Y = 350;
    private static final int LOSE_X = 95;
    private static final double END_COMMENT_TIME = 2000;
    private static final int END_PTS_FONT_SIZE = 100;
    private static final int END_H_X = 160;
    private static final int END_H_Y = 475;
    private static final int END_C_X = 345;
    private static final int END_C_Y = 475;
    private static final double PLAY_AGAIN_DELAY = 2000;
    public static final int PLAY_AGAIN_X = 200;
    public static final int PLAY_AGAIN_Y = 505;
    public static final int PLAY_AGAIN_W = 205;
    public static final int PLAY_AGAIN_H = 150;
    private ArrayList<Image> playerMoonTileImages;
    private ArrayList<Image> computerMoonTileImages;
    private Image backgroundImage;
    private Image topMoonImage;
    private Game game;

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

        // Draw win/lose screen and don't draw it again with timers once drawn
        if (game.gameState.equals("win/lose"))
        {
            // If both players have their iswinner == false, it was a tie
            if (!game.getHumanPlayer().isWinner() && !game.getComputerPlayer().isWinner())
            {
                // Draw tie statement
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial Black",Font.BOLD,PLAY_TILE_FONT_SIZE));
                g.drawString("It's a Tie...",TIE_X,TIE_Y);
                g.drawString("The Half Moon wants rematch",WIN_X+2,WIN_Y);
            }
            // If human wins, draw that
            else if (game.getHumanPlayer().isWinner())
            {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial Black",Font.BOLD,PLAY_TILE_FONT_SIZE));
                g.drawString("You Win!",TIE_X+10,TIE_Y);
                game.timer(END_COMMENT_TIME);
                g.drawString("The Half Moon will return...",WIN_X,WIN_Y);
            }
            // If computer wins, draw it
            else if (game.getComputerPlayer().isWinner())
            {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial Black",Font.BOLD,PLAY_TILE_FONT_SIZE));
                g.drawString("You Lose!",TIE_X+5,TIE_Y);
                game.timer(END_COMMENT_TIME);
                g.drawString("The Half Moon defeated you...",LOSE_X,WIN_Y);
            }

            // Draw player points after a delay
            game.timer(END_COMMENT_TIME);
            int humanPts = game.getHumanPlayer().getPoints();
            int computerPts = game.getComputerPlayer().getPoints();
            // Draw the points
            g.setFont(new Font("Arial Black",Font.BOLD,END_PTS_FONT_SIZE));
            g.setColor(game.getHumanPlayer().getPlayerColorTheme());
            g.drawString(""+humanPts,END_H_X,END_H_Y);
            g.setColor(game.getComputerPlayer().getPlayerColorTheme());
            g.drawString(""+computerPts,END_C_X,END_C_Y);


            // Draw play again button after a pause
            game.timer(PLAY_AGAIN_DELAY);
            // Draw play again button
            g.setColor(BoardTile.BOARD_THEME_COLOR);
            g.fill3DRect(PLAY_AGAIN_X,PLAY_AGAIN_Y,PLAY_AGAIN_W,PLAY_AGAIN_H,true);
            // Draw play again text
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial Black",Font.BOLD,PLAY_AGAIN_FONT_SIZE));
            g.drawString("Play",PLAY_AGAIN_X+40,PLAY_AGAIN_Y+60);
            g.drawString("Again",PLAY_AGAIN_X+20,PLAY_AGAIN_Y+120);

            // Don't draw the rest of the stuff
            return;
        }

        // Draw prompt for user
        if (game.gameState.equals("human"))
        {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial Black",Font.BOLD,PLAY_TILE_FONT_SIZE));
            g.drawString("Play a Moon Tile",PLAY_TILE_X,PLAY_TILE_Y);
        }

        // Draw game over notification
        else if (game.gameState.equals("game over"))
        {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial Black",Font.BOLD,POINTS_FONT_SIZE));
            g.drawString("Game Over!",GAME_OVER_X,GAME_OVER_Y);
        }

        // Draw possession
        if (game.gameState.contains("possession"))
        {
            // Draw prompt on screen
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial Black",Font.BOLD,POINTS_FONT_SIZE));
            g.drawString("Tile Owner Pts",GAME_OVER_X-25,GAME_OVER_Y);

            // Draw human player possession points
            g.setColor(game.getHumanPlayer().getPlayerColorTheme());
            g.setFont(new Font("Arial Black",Font.BOLD,POINTS_FONT_SIZE));
            g.drawString("+"+game.gameState.charAt(0),POSS_H_X,POSS_H_Y);

            // Draw computer player possession points
            g.setColor(game.getComputerPlayer().getPlayerColorTheme());
            g.setFont(new Font("Arial Black",Font.BOLD,POINTS_FONT_SIZE));
            g.drawString("+"+game.gameState.charAt(1),POSS_C_X,POSS_C_Y);
        }

        // Draw human player, computer player, and board
        game.getHumanPlayer().draw(g);
        game.getComputerPlayer().draw(g);
        game.getBoard().draw(g);
    }

    public ArrayList<Image> getComputerMoonTileImages() {
        return computerMoonTileImages;
    }

    public ArrayList<Image> getPlayerMoonTileImages() {
        return playerMoonTileImages;
    }
}
