package Chess;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

public class Horse extends Piece
{
    public Horse(boolean white)
    {
        super(white);
    }

    @Override
    public char getId()
    {
        return 'H';
    }

    @Override
    public int getValue()
    {
        return 3;
    }

    @Override
    public int isMoveLegal(Board board, Tile moveFrom, Tile moveTo)
    {
        int numSpacesMovingX = (moveTo.getX() - moveFrom.getX());
        int numSpacesMovingY = (moveTo.getY() - moveFrom.getY());

        // illegal if move to space with same color piece
        if (moveTo.getPiece() != null)
            if (moveTo.getPiece().isWhite() == isWhite())
                return 0;

        // illegal if not moving two spaces and to the left or right
        // note: used hypotenuse for some reason
        if (sqrt(pow(numSpacesMovingX, 2) + pow(numSpacesMovingY, 2)) != sqrt(5))
            return 0;

        // check if king is in check and if move will bring it out of check
        if (isKingInCheck(board))
            return 0;
        if (isDestinationCheck(board, moveFrom, moveTo))
            return 0;

        return 1;
    }
}
