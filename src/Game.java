import Chess.Board;
import Chess.ChessGame;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Game
{
    private static Scanner s = new Scanner(System.in);

    public static void main(String[] args)
    {
        Board newGame = new Board(8, 8);
        printBoard(newGame);
    }

    private static void printBoard(Board board)
    {
        ChessGame c = new ChessGame();
        c.setScanner(s);
        c.board = board;
        while(true)
        {
            System.out.print("   ");
            for (int x = -1; x < 8; x++)
            {

                for (int y = 0; y < 8; y++)
                {
                    if (y == 0 && x != -1)
                    {
                        System.out.printf("|%c|", x + 97);
                    }
                    if (x == -1)
                    {
                        System.out.printf("|%2.0f|", (float)(y + 1));
                    }
                    else
                    {
                        if (board.getTile(x, y).getPiece() != null)
                        {
                            System.out.printf(" %c%c ", board.getTile(x, y).getPiece().isWhite() ? 'W' : 'B', board.getTile(x, y).getPiece().getId());
                        }
                        else
                        {
                            System.out.printf(" XX ");
                        }
                    }
                }
                System.out.println("");
            }

            while(true)
            {
                System.out.println(c.isKingInCheck());
                System.out.println(c.isKingInCheckmate());

                System.out.print("Select Piece: ");
                String pieceFrom = s.nextLine();

                System.out.print("Move Piece To: ");
                String pieceTo = s.nextLine();

                c.movePiece(pieceFrom, pieceTo);
                break;
            }
        }
    }
}
