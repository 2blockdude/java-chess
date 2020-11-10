package Chess;

import static java.lang.Math.abs;

public abstract class Piece
{
    private boolean white;
    protected boolean movesMinusOne = false;
    private int movesTaken = 0;

    public Piece(boolean white)
    {
        this.white = white;
    }

    public boolean isWhite()
    {
        return this.white;
    }

    public int getMoves()
    {
        return movesTaken;
    }

    public void setMoves(int moves)
    {
        this.movesTaken = moves;
    }

    public void increaseMoves()
    {
        movesTaken++;
    }

    // isMoveLegal notes
    // 0: illegal move
    // 1: legal move
    // 2: en passant
    // 3: promotion
    // 4: castle king side
    // 5: castle queen side
    public abstract int isMoveLegal(Board board, Tile moveFrom, Tile moveTo);
    public abstract char getId();
    public abstract int getValue();

    // not sure if I should put this here
    protected boolean isKingInCheck(Board board)
    {
        // loops at tile looking for enemy piece
        for (int i = 0; i < board.getBoardSizeX(); i++)
            for (int j = 0; j < board.getBoardSizeY(); j++)
                if (board.getTile(i, j).getPiece() != null)
                    if (board.getTile(i, j).getPiece().getId() == 'K')
                        if (board.getTile(i, j).getPiece().isWhite() == isWhite())
                            for (int x = 0; x < board.getBoardSizeX(); x++)
                                for (int y = 0; y < board.getBoardSizeY(); y++)
                                    if (board.getTile(x, y).getPiece() != null)
                                        if (board.getTile(x, y).getPiece().isWhite() != isWhite())
                                            if (board.getTile(x, y).getPiece().isMoveLegal(board, board.getTile(x, y), board.getTile(i, j)) > 0)
                                                return true;
        return false;
    }

    protected boolean isDestinationCheck(Board board, Tile moveFrom, Tile moveTo)
    {
        // moves king to destination to see if any enemy piece can move to the destination
        Piece enemyPiece = board.getTile(moveTo.getX(), moveTo.getY()).getPiece();
        Piece originalPiece = board.getTile(moveFrom.getX(), moveFrom.getY()).getPiece();
        Piece placeHolder;

        // is not in check if it is about to capture a king
        if (moveTo.getPiece() != null)
        {
            if (moveTo.getPiece().getId() == 'K')
            {
                return false;
            }
        }

        // just created new place holder objects because I am lazy :P... sorry.
        if (originalPiece.getId() == 'K')
        {
            board.getTile(moveFrom.getX(), moveFrom.getY()).setPiece(null);
            placeHolder = new PlaceHolderKing(originalPiece.isWhite());
            board.getTile(moveTo.getX(), moveTo.getY()).setPiece(placeHolder);
        }
        else
        {
            board.getTile(moveFrom.getX(), moveFrom.getY()).setPiece(null);
            placeHolder = new PlaceHolderPiece(originalPiece.isWhite());
            board.getTile(moveTo.getX(), moveTo.getY()).setPiece(placeHolder);
        }

        if (isKingInCheck(board))
        {
            board.getTile(moveFrom.getX(), moveFrom.getY()).setPiece(originalPiece);
            board.getTile(moveTo.getX(), moveTo.getY()).setPiece(enemyPiece);
            return true;
        }
        else
        {
            board.getTile(moveFrom.getX(), moveFrom.getY()).setPiece(originalPiece);
            board.getTile(moveTo.getX(), moveTo.getY()).setPiece(enemyPiece);
            return false;
        }
    }

    // not sure if I should put this method here but every piece uses this method except for the horse
    protected boolean isPieceBlocking(Board board, Tile moveFrom, Tile moveTo)
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
