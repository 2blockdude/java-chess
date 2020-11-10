package Chess;

import static java.lang.Math.abs;

public class Rook extends Piece
{
    public Rook(boolean white, int value)
    {
        super(white, value);
    }

    @Override
    public char getId()
    {
        return 'R';
    }

    @Override
    public int isMoveLegal(Board board, Tile moveFrom, Tile moveTo)
    {
        return isMoveLegal(board, moveFrom, moveTo, true);
    }

    @Override
    public int isMoveLegal(Board board, Tile moveFrom, Tile moveTo, boolean checkKing)
    {
        int numSpacesMovingX = (moveTo.getX() - moveFrom.getX());
        int numSpacesMovingY = (moveTo.getY() - moveFrom.getY());

        // illegal if move to space with same color piece
        if (moveTo.getPiece() != null)
            if (moveTo.getPiece().isWhite() == isWhite())
                return 0;

        // illegal if not moving in a straight line
        if (numSpacesMovingX != 0 && numSpacesMovingY != 0)
            return 0;

        // illegal if there is a piece in between the piece and the destination
        if (isPieceBlocking(board, moveFrom, moveTo))
            return 0;

        // check if king is in check and if move will bring it out of check
        if (checkKing)
            if (isKingInCheck(board))
                if (isDestinationCheck(board, moveFrom, moveTo))
                    return 0;

        return 1;

    }
}
