import static java.lang.Math.abs;

public class Pawn extends Piece
{
    public Pawn(boolean white, int value)
    {
        super(white, value);
    }

    @Override
    public char getId()
    {
        return 'P';
    }

    @Override
    public int isLegalMove(Board board, Tile moveFrom, Tile moveTo)
    {
        int numSpacesX = abs(moveFrom.getX() - moveTo.getX());
        int numSpacesY = abs(moveFrom.getY() - moveTo.getY());
        boolean canMoveForward = (moveTo.getY() - moveFrom.getY()) > 0;

        // illegal if move to space with same color piece
        // note: needs to check null or else it will get an error
        // does not affect the outcome
        if (moveTo.getPiece() != null)
            if (moveTo.getPiece().isWhite() == isWhite())
                return 0;

        // illegal if trying to move opposite direction
        if ((isWhite() && !canMoveForward) || (!isWhite() && canMoveForward))
            return 0;

        // can't move is piece if blocking
        if (isPieceBlocking(board, moveFrom, numSpacesX, numSpacesY))
        {
            return 0;
        }
        else
        {
            // legal if nothing in the space
            if (moveTo.getPiece() == null)
            {
                // legal if moving one space forward
                if (numSpacesY == 1 && numSpacesX == 0)
                {
                    if (moveTo.getY() == board.getBoardSizeY() - 1 || moveTo.getY() == 0)
                        return 3;
                    return 1;
                }

                // can move two spots forward if had not moved
                if (numSpacesY == 2 && numSpacesX == 0)
                    if (getMoves() == 0)
                        return 1;

                // legal move if the enemy pawn is two spaces from the home row, within bounds, there is a piece to the left side, the piece is a pawn, and the enemy pawn has moved once
                if (((moveFrom.getY() == 3) && !isWhite()) || ((moveFrom.getY() == board.getBoardSizeY() - 4) && isWhite()))
                    if ((moveTo.getY() - 1) >= 0 && (moveTo.getY() - 1) < board.getBoardSizeY())
                        if (board.getTile(moveTo.getX(), moveTo.getY() - 1).getPiece() != null)
                            if (board.getTile(moveTo.getX(), moveTo.getY() - 1).getPiece().getId() == 'P')
                                if (board.getTile(moveTo.getX(), moveTo.getY() - 1).getPiece().getMoves() == 1)
                                    return 2;

                // legal move if the enemy pawn is two spaces from the home row, within bounds, there is a piece to the right side, the piece is a pawn, and the enemy pawn has moved once
                if (((moveFrom.getY() == 3) && !isWhite()) || ((moveFrom.getY() == board.getBoardSizeY() - 4) && isWhite()))
                    if ((moveTo.getY() + 1) >= 0 && (moveTo.getY() + 1) < board.getBoardSizeY())
                        if (board.getTile(moveTo.getX(), moveTo.getY() + 1).getPiece() != null)
                            if (board.getTile(moveTo.getX(), moveTo.getY() + 1).getPiece().getId() == 'P')
                                if (board.getTile(moveTo.getX(), moveTo.getY() + 1).getPiece().getMoves() == 1)
                                    return 2;

            }
            // legal if a piece is directly diagonal to the pawn
            else
            {
                if (numSpacesX == 1 && numSpacesY == 1)
                {
                    if (moveTo.getY() == board.getBoardSizeY() - 1 || moveTo.getY() == 0)
                        return 3;
                    return 1;
                }
            }
        }
        return 0;
    }

    private boolean isPieceBlocking(Board board, Tile moveFrom, int numSpacesX, int numSpacesY)
    {
        if (isWhite())
            for (int i = 1; i <= numSpacesY; i++)
                if (board.getTile(moveFrom.getX(), moveFrom.getY() + i).getPiece() != null && numSpacesX == 0)
                    return true;

        if (!isWhite())
            for (int i = 1; i <= numSpacesY; i++)
                if (board.getTile(moveFrom.getX(), moveFrom.getY() - i).getPiece() != null && numSpacesX == 0)
                    return true;

        return false;
    }
}
