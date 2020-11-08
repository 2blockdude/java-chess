import static java.lang.Math.abs;

public class Queen extends Piece
{
    public Queen(boolean white, int value)
    {
        super(white, value);
    }

    @Override
    public char getId()
    {
        return 'Q';
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

        // illegal if not moving diagonally or horizontally
        if ((abs(numSpacesMovingX) != abs(numSpacesMovingY)) && (numSpacesMovingX != 0 && numSpacesMovingY != 0))
            return 0;

        // illegal if there is a piece in between the piece and the destination
        if (isPieceBlocking(board, moveFrom, moveTo))
            return 0;

        return 1;
    }

    private boolean isPieceBlocking(Board board, Tile moveFrom, Tile moveTo)
    {
        int numSpacesMovingX = (moveTo.getX() - moveFrom.getX());
        int numSpacesMovingY = (moveTo.getY() - moveFrom.getY());
        int movingX = numSpacesMovingX > 0 ? 1 : -1;
        int movingY = numSpacesMovingY > 0 ? 1 : -1;

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

        // checks every diagonal till the destination
        if (abs(numSpacesMovingX) == abs(numSpacesMovingY))
        {
            for (int i = 1; i <= abs(numSpacesMovingX); i++)
            {
                // legal if piece is the destination, is not null, and is not the same color
                if (i == abs(numSpacesMovingX) && moveTo.getPiece() != null && isWhite() != moveTo.getPiece().isWhite())
                    return false;

                if (board.getTile(moveFrom.getX() + (i * movingX), moveFrom.getY() + (i * movingY)).getPiece() != null)
                    return true;
            }
        }

        return false;
    }
}
