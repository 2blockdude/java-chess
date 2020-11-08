import static java.lang.Math.abs;

public class King extends Piece
{
    public King(boolean white, int value)
    {
        super(white, value);
    }

    public char getId()
    {
        return 'K';
    }

    public int isLegalMove(Board board, Tile moveFrom, Tile moveTo)
    {
        int numSpacesMovingX = (moveTo.getX() - moveFrom.getX());
        int numSpacesMovingY = (moveTo.getY() - moveFrom.getY());

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
        }
        else
        {
            return 0;
        }

        // illegal if moving more than one space at a time
//        if (abs(numSpacesMovingX) > 1 || abs(numSpacesMovingY) > 1)
//            return 0;

        return 1;
    }

    public boolean isDestinationCheck(Board board,Tile moveFrom, Tile moveTo)
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
