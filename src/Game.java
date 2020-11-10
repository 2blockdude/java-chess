import Chess.Tile;
import Chess.ChessGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.Scanner;

public class Game extends JPanel
{
    private ChessGame chess = new ChessGame();
    private Scanner s = new Scanner(System.in);
    private int previousMouseX;
    private int previousMouseY;
    private int mouseX;
    private int mouseY;

    public Game()
    {
        getMouseInput();
    }

    // note: I could also implement mouse inputs
    private void getMouseInput()
    {
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                super.mousePressed(e);

                previousMouseX = mouseX;
                previousMouseY = mouseY;

                mouseX = e.getX() / 100;
                mouseY = e.getY() / 100;

                System.out.printf("%d, %d\n", mouseX, mouseY);
                movePiece();
                if (chess.isNeedPromotion())
                    chess.promoteLastMovedPiece('Q');
                System.out.println(chess.isKingInCheck());
                System.out.println(chess.isKingInCheckmate());
                repaint();
            }
        });
    }

    private void movePiece()
    {
        if (chess.getTile(previousMouseX, previousMouseY).getPiece() != null)
        {
            if (chess.isTurnWhite() == chess.getTile(previousMouseX, previousMouseY).getPiece().isWhite())
            {
                chess.movePiece(previousMouseX, previousMouseY, mouseX, mouseY);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        // removes old graphics
        super.paintComponent(g);

        // draws chess game
        drawBoard(g);
        fillValidMoves(g);
    }

    private void drawBoard(Graphics g)
    {
        for (int x = 0; x < chess.getBoardSizeX(); x++)
        {
            for (int y = 0; y < chess.getBoardSizeY(); y++)
            {
                g.drawRect(x * 100, y * 100, 100, 100);

                if (chess.getTile(x, y).getPiece() != null)
                {
                    if (chess.getTile(x, y).getPiece().isWhite())
                    {
                        g.setColor(Color.gray);
                        g.fillOval((x * 100) + 25, (y * 100) + 25, 50, 50);
                        g.setColor(Color.black);
                        g.drawString(String.valueOf(chess.getTile(x, y).getPiece().getId()), (x * 100) + 5, (y * 100) + 15);
                    }
                    else
                    {
                        g.fillOval((x * 100) + 25, (y * 100) + 25, 50, 50);
                        g.drawString(String.valueOf(chess.getTile(x, y).getPiece().getId()), (x * 100) + 5, (y * 100) + 15);
                    }
                }
            }
        }
    }

    private void fillValidMoves(Graphics g)
    {
        if (chess.getTile(mouseX, mouseY).getPiece() != null)
        {
            if (chess.isTurnWhite() == chess.getTile(mouseX, mouseY).getPiece().isWhite())
            {
                for (int x = 0; x < chess.getBoardSizeX(); x++)
                {
                    for (int y = 0; y < chess.getBoardSizeY(); y++)
                    {
                        if (chess.isMoveLegal(mouseX, mouseY, x, y))
                        {
                            g.setColor(Color.red);
                            g.drawOval((x * 100) + 25, (y * 100) + 25, 50, 50);
                            g.setColor(Color.black);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args)
    {
        JFrame jf = new JFrame();
        jf.setSize(800, 800);
        jf.setResizable(false);
        jf.setTitle("Chess");
        // sets location on screen when it opens
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
        // adds all components and listeners to the object
        jf.getContentPane().add(new Game());
        // note: this needs to be called last for everything to load properly
        jf.setVisible(true);
    }
}
