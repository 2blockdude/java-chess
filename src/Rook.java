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
    public int isLegalMove(Board board, Tile moveFrom, Tile moveTo)
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
        if (isPieceBlockingV2(board, moveFrom, moveTo))
            return 0;

        return 1;

    }

    private boolean isPieceBlockingV2(Board board, Tile moveFrom, Tile moveTo)
    {
        int numSpacesMovingX = (moveTo.getX() - moveFrom.getX());
        int numSpacesMovingY = (moveTo.getY() - moveFrom.getY());
        int movingX = (numSpacesMovingX) > 0 ? 1 : -1;
        int movingY = (numSpacesMovingY) > 0 ? 1 : -1;

        // checks every tile up to the destination only on the x axis
        if (numSpacesMovingX != 0 && numSpacesMovingY == 0)
        {
            for (int i = movingX; i != (numSpacesMovingX) + movingX; i += movingX)
            {
                // legal if piece is the destination, is not null, and is not the same color
                if (i == numSpacesMovingX && moveTo.getPiece() != null && isWhite() != moveTo.getPiece().isWhite())
                    return false;

                // checks if position is null
                if (board.getTile(moveFrom.getX() + i, moveFrom.getY()).getPiece() != null)
                    return true;
            }
        }

        // checks every tile up to the destination only on the y axis
        if (numSpacesMovingY != 0 && numSpacesMovingX == 0)
        {
            for (int i = movingY; i != (numSpacesMovingY) + movingY; i += movingY)
            {
                if (i == numSpacesMovingY && moveTo.getPiece() != null && isWhite() != moveTo.getPiece().isWhite())
                    return false;
                if (board.getTile(moveFrom.getX(), moveFrom.getY() + i).getPiece() != null)
                    return true;
            }
        }

        return false;
    }

    private boolean isPieceBlocking(Board board, Tile moveFrom, Tile moveTo)
    {
        int numSpacesMovingX = abs(moveFrom.getX() - moveTo.getX());
        int numSpacesMovingY = abs(moveFrom.getY() - moveTo.getY());
        boolean isMovingPositiveX = (moveTo.getX() - moveFrom.getX()) > 0;
        boolean isMovingPositiveY = (moveTo.getY() - moveFrom.getY()) > 0;

        if (isMovingPositiveX)
        {
            for (int i = 1; i <= numSpacesMovingX; i++)
            {
                if (i == numSpacesMovingX && moveTo.getPiece() != null && isWhite() != moveTo.getPiece().isWhite())
                    return false;
                if (board.getTile(moveFrom.getX() + i, moveFrom.getY()).getPiece() != null)
                    return true;
            }
        }
        else
        {
            for (int i = 1; i <= numSpacesMovingX; i++)
            {
                if (i == numSpacesMovingX && moveTo.getPiece() != null && isWhite() != moveTo.getPiece().isWhite())
                    return false;
                if (board.getTile(moveFrom.getX() - i, moveFrom.getY()).getPiece() != null)
                    return true;
            }
        }
        if (isMovingPositiveY)
        {
            for (int i = 1; i <= numSpacesMovingY; i ++)
            {
                if (i != 0)
                {
                if (i == numSpacesMovingY && moveTo.getPiece() != null && isWhite() != moveTo.getPiece().isWhite())
                    return false;
                if (board.getTile(moveFrom.getX(), moveFrom.getY() + i).getPiece() != null)
                    return true;
                }
            }
        }
        else
        {
            for (int i = 1; i <= numSpacesMovingY; i++)
            {
                if (i == numSpacesMovingY && moveTo.getPiece() != null && isWhite() != moveTo.getPiece().isWhite())
                    return false;
                if (board.getTile(moveFrom.getX(), moveFrom.getY() - i).getPiece() != null)
                    return true;
            }
        }
        return false;
    }
}
