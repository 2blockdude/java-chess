import java.util.ArrayList;

public class ChessGame
{
    public Board board = new Board(8, 8);
    private ArrayList<Piece> captured = new ArrayList<>();
    private Piece promotionPiece;
    private int turns = 0;

    public int getTurns()
    {
        return turns;
    }

    public void movePiece(Tile moveFrom, Tile moveTo)
    {
        // don't forget at the end of moving increase all pawns, who have moved once and is on the en passant row, moves by one
        if (moveFrom.getPiece() != null)
        {
//            if (moveFrom.getPiece().isWhite() == (turns % 2 == 0))
//            {
                int legalValue = moveFrom.getPiece().isLegalMove(board, moveFrom, moveTo);

                if (legalValue == 1)
                {
                    normalMove(moveFrom, moveTo);
                    turns++;
                }
                else if (legalValue == 2)
                {
                    enPassant(moveFrom, moveTo);
                    turns++;
                }
                else if (legalValue == 3)
                {
                    normalMove(moveFrom, moveTo);
                    turns++;
                }
                else if (legalValue == 4)
                {
                    castleKingSide(moveFrom, moveTo);
                    turns++;
                }
                else if (legalValue == 5)
                {
                    castleQueenSide(moveFrom, moveTo);
                    turns++;
                }
//            }
        }
    }

    private void castleQueenSide(Tile moveFrom, Tile moveTo)
    {
        // place rook at other side of king
        Piece rook = board.getTile(0, moveFrom.getY()).getPiece();
        board.getTile(moveTo.getX() + 1, moveTo.getY()).setPiece(rook);
        board.getTile(0, moveFrom.getY()).setPiece(null);

        normalMove(moveFrom, moveTo);
    }

    private void castleKingSide(Tile moveFrom, Tile moveTo)
    {
        // place rook at other side of king
        Piece rook = board.getTile(board.getBoardSizeX() - 1, moveFrom.getY()).getPiece();
        board.getTile(moveTo.getX() - 1, moveTo.getY()).setPiece(rook);
        board.getTile(board.getBoardSizeX() - 1, moveFrom.getY()).setPiece(null);

        normalMove(moveFrom, moveTo);
    }

    private void pawnPromotion(Tile moveFrom, Tile moveTo)
    {

    }

    private void enPassant(Tile moveFrom, Tile moveTo)
    {
        if (moveFrom.getPiece().isWhite())
        {
            captured.add(board.getTile(moveTo.getX(), moveTo.getY() - 1).getPiece());
            board.getTile(moveTo.getX(), moveTo.getY() - 1).setPiece(null);
        }
        else
        {
            captured.add(board.getTile(moveTo.getX(), moveTo.getY() + 1).getPiece());
            board.getTile(moveTo.getX(), moveTo.getY() + 1).setPiece(null);
        }
        normalMove(moveFrom, moveTo);

        moveTo.getPiece().increaseMoves();
    }

    private void normalMove(Tile moveFrom, Tile moveTo)
    {
        if (moveTo.getPiece() != null)
        {
            captured.add(moveTo.getPiece());
        }
        board.getTile(moveTo.getX(), moveTo.getY()).setPiece(moveFrom.getPiece());
        board.getTile(moveFrom.getX(), moveFrom.getY()).setPiece(null);

        moveTo.getPiece().increaseMoves();
    }
}
