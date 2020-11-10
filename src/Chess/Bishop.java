package Chess;

import static java.lang.Math.abs;

public class Bishop extends Piece
{
    public Bishop(boolean white)
    {
        super(white);
    }

    @Override
    public char getId()
    {
        return 'B';
    }

    @Override
    public int getValue()
    {
        return 3;
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

        // illegal if not moving diagonally
        if (abs(numSpacesMovingX) != abs(numSpacesMovingY))
            return 0;

        // illegal if there is a piece in between the piece and the destination
        if (isPieceBlocking(board, moveFrom, moveTo))
            return 0;

        // check if king is in check and if move will bring it out of check
        if (checkKing)
            if (isKingInCheck(board))
                if (isDestinationCheck(board, moveFrom, moveTo))
                    return 0;

        if (isDestinationCheck(board, moveFrom, moveTo))
            return 0;

        return 1;
    }

    private boolean isPieceBlockingV2(Board board, Tile moveFrom, Tile moveTo)
    {
        int numSpacesMovingX = (moveTo.getX() - moveFrom.getX());
        int numSpacesMovingY = (moveTo.getY() - moveFrom.getY());
        int movingX = numSpacesMovingX > 0 ? 1 : -1;
        int movingY = numSpacesMovingY > 0 ? 1 : -1;

        // checks every diagonal till the destination
        for (int i = 1; i <= abs(numSpacesMovingX); i++)
        {
            // legal if piece is the destination, is not null, and is not the same color
            if (i == abs(numSpacesMovingX) && moveTo.getPiece() != null && isWhite() != moveTo.getPiece().isWhite())
                return false;

            if (board.getTile(moveFrom.getX() + (i * movingX), moveFrom.getY() + (i * movingY)).getPiece() != null)
                return true;
        }

        return false;
    }
}
