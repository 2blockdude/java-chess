import static java.lang.Math.abs;

public class King extends Piece
{
    public King(boolean white, int value)
    {
        super(white, value);
    }

    @Override
    public char getId()
    {
        return 'K';
    }

    @Override
    public int isLegalMove(Board board, Tile moveFrom, Tile moveTo)
    {
        int numSpacesMovingX = (moveTo.getX() - moveFrom.getX());
        int numSpacesMovingY = (moveTo.getY() - moveFrom.getY());
        boolean movingPositiveX = (numSpacesMovingX) > 0;

        // illegal if move to space with same color piece
        if (moveTo.getPiece() != null)
            if (moveTo.getPiece().isWhite() == isWhite())
                return 0;

        // illegal if destination puts self in check
        // also first checks if move is at all possible to make  it a bit faster... I suppose
        if ((abs(numSpacesMovingX) <= 1 && abs(numSpacesMovingY) <= 1) || (abs(numSpacesMovingX) == 2 && abs(numSpacesMovingY) == 0 && getMoves() == 0))
        {
            if (isDestinationCheck(board, moveFrom, moveTo))
                return 0;

            if ((abs(numSpacesMovingX) == 2 && abs(numSpacesMovingY) == 0 && getMoves() == 0))
            {
                if (canCastle(board, moveFrom, moveTo))
                {
                    if (movingPositiveX)
                        return 4;
                    else
                        return 5;
                }
                else
                {
                    return 0;
                }
            }
        }
        else
        {
            return 0;
        }

        return 1;
    }

    private boolean canCastle(Board board, Tile moveFrom, Tile moveTo)
    {
        int numSpacesMovingX = (moveTo.getX() - moveFrom.getX());
        boolean movingPositiveX = (numSpacesMovingX) > 0;

        // if king has moved it can't castle
        if (getMoves() != 0)
            return false;

        // if king is in check it can't castle
        if (isInCheck(board, moveFrom))
            return false;

        // if the rook it is trying to get to has moved then it can't castle
        if (!movingPositiveX)
        {
            if (board.getTile(0, moveFrom.getY()).getPiece() == null)
            {
                return false;
            }
            else
            {
                if (board.getTile(0, moveFrom.getY()).getPiece().isWhite() != isWhite())
                {
                    return false;
                }
                else
                {
                    if (board.getTile(0, moveFrom.getY()).getPiece().getId() != 'R')
                    {
                        return false;
                    }
                    else
                    {
                        if (board.getTile(0, moveFrom.getY()).getPiece().getMoves() != 0)
                            return false;
                    }
                }
            }
        }

        // if the rook it is trying to get to has moved then it can't castle
        if (movingPositiveX)
        {
            if (board.getTile(board.getBoardSizeX() - 1, moveFrom.getY()).getPiece() == null)
            {
                return false;
            }
            else
            {
                if (board.getTile(board.getBoardSizeX() - 1, moveFrom.getY()).getPiece().isWhite() != isWhite())
                {
                    return false;
                }
                else
                {
                    if (board.getTile(board.getBoardSizeX() - 1, moveFrom.getY()).getPiece().getId() != 'R')
                    {
                        return false;
                    }
                    else
                    {
                        if (board.getTile(board.getBoardSizeX() - 1, moveFrom.getY()).getPiece().getMoves() != 0)
                            return false;
                    }
                }
            }
        }

        // if there is a piece in between the king and the rook it can't castle
        if (isPieceBlocking(board, moveFrom, moveTo))
            return false;

        return true;
    }

    private boolean isInCheck(Board board, Tile moveFrom)
    {
        // loops at tile looking for enemy piece
        for (int x = 0; x < board.getBoardSizeX(); x++)
            for (int y = 0; y < board.getBoardSizeY(); y++)
                if (board.getTile(x, y).getPiece() != null)
                    if (board.getTile(x, y).getPiece().isWhite() != isWhite())
                        if (board.getTile(x, y).getPiece().isLegalMove(board, board.getTile(x, y), moveFrom) > 0)
                            return true;
        return false;
    }

    private boolean isPieceBlocking(Board board, Tile moveFrom, Tile moveTo)
    {
        int numSpacesMovingX = (moveTo.getX() - moveFrom.getX());
        int movingPositiveX = (numSpacesMovingX) > 0 ? 1 : -1;
        int numSpacesToEnd = 0;

        // calculate number of spaces to rook
        if (movingPositiveX == 1)
            numSpacesToEnd = (board.getBoardSizeX() - 2) - moveFrom.getX();
        else
            numSpacesToEnd = moveFrom.getX() - 1;

        // checks if there is a piece blocking
        for (int i = 0; abs(i) <= abs(numSpacesToEnd); i += movingPositiveX)
            if (i != 0)
                if (board.getTile(moveFrom.getX() + i, moveFrom.getY()).getPiece() != null)
                    return true;

        return false;
    }

    private boolean isDestinationCheck(Board board, Tile moveFrom, Tile moveTo)
    {
        // moves king to destination to see if any enemy piece can move to the destination
        Piece piece = board.getTile(moveTo.getX(), moveTo.getY()).getPiece();
        Piece king = board.getTile(moveFrom.getX(), moveFrom.getY()).getPiece();
        board.getTile(moveFrom.getX(), moveFrom.getY()).setPiece(null);
        board.getTile(moveTo.getX(), moveTo.getY()).setPiece(king);

        for (int x = 0; x < board.getBoardSizeX(); x++)
        {
            for (int y = 0; y < board.getBoardSizeY(); y++)
            {
                if (board.getTile(x, y).getPiece() != null)
                {
                    if (board.getTile(x, y).getPiece().isWhite() != isWhite())
                    {
                        if (board.getTile(x, y).getPiece().isLegalMove(board, board.getTile(x, y), moveTo) > 0)
                        {
                            board.getTile(moveFrom.getX(), moveFrom.getY()).setPiece(king);
                            board.getTile(moveTo.getX(), moveTo.getY()).setPiece(piece);
                            return true;
                        }
                    }
                }
            }
        }
        board.getTile(moveFrom.getX(), moveFrom.getY()).setPiece(king);
        board.getTile(moveTo.getX(), moveTo.getY()).setPiece(piece);
        return false;
    }
}
