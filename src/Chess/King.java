package Chess;

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
    public int isMoveLegal(Board board, Tile moveFrom, Tile moveTo)
    {
        return isMoveLegal(board, moveFrom, moveTo, true);
    }

    @Override
    public int isMoveLegal(Board board, Tile moveFrom, Tile moveTo, boolean checkKing)
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
        if (isKingInCheck(board))
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

        int numSpacesToEnd = 0;
        // get number of spaces to rook
        // did this because it cannot tell which rook to look at unless I tell it which one by the direction the king is moving
        if (movingPositiveX)
            numSpacesToEnd = (board.getBoardSizeX() - 2);
        else
            numSpacesToEnd = 1;
        // if there is a piece in between the king and the rook it can't castle
        if (isPieceBlocking(board, moveFrom, board.getTile(numSpacesToEnd, moveFrom.getY())))
            return false;

        return true;
    }
}
