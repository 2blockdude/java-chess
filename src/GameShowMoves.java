import java.util.Scanner;

public class GameShowMoves
{
    private static Board board = new Board(8, 8);
    public static void main(String[] args)
    {
        printBoard(4, 7);
    }

    private static void printBoard(int checkx, int checky)
    {
        int pawnx = checkx;
        int pawny = checky;
        Tile t = board.getTile(pawnx, pawny);

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
                    int legalValue = t.getPiece().isLegalMove(board, board.getTile(pawnx, pawny), board.getTile(x, y));
                    if (legalValue > 0)
                        if (board.getTile(x, y).getPiece() != null)
                            System.out.printf("|%c%c|", board.getTile(x, y).getPiece().isWhite() ? 'W' : 'B', board.getTile(x, y).getPiece().getId());
                        else
                            System.out.print("|XX|");
                    else
                        if (board.getTile(x, y).getPiece() != null)
                            System.out.printf(" %c%c ", board.getTile(x, y).getPiece().isWhite() ? 'W' : 'B', board.getTile(x, y).getPiece().getId());
                        else
                            System.out.print(" XX ");
                }
            }
            System.out.println("");
        }
    }
}
