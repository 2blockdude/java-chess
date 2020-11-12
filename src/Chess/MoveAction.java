package Chess;

public class MoveAction
{
    Board board;

    void move(Tile moveFrom, Tile moveTo)
    {
        board.getTile(moveTo.getX(), moveTo.getY()).setPiece(moveFrom.getPiece());
        board.getTile(moveFrom.getX(), moveFrom.getY()).setPiece(null);

        moveTo.getPiece().increaseMoves();
    }

    void castleQueenSide(Tile moveFrom, Tile moveTo)
    {
        // place rook at other side of king
        Piece rook = board.getTile(0, moveFrom.getY()).getPiece();
        board.getTile(moveTo.getX() + 1, moveTo.getY()).setPiece(rook);
        board.getTile(0, moveFrom.getY()).setPiece(null);

        move(moveFrom, moveTo);
    }

    void castleKingSide(Tile moveFrom, Tile moveTo)
    {
        // place rook at other side of king
        Piece rook = board.getTile(board.getBoardSizeX() - 1, moveFrom.getY()).getPiece();
        board.getTile(moveTo.getX() - 1, moveTo.getY()).setPiece(rook);
        board.getTile(board.getBoardSizeX() - 1, moveFrom.getY()).setPiece(null);

        move(moveFrom, moveTo);
    }

    void enPassant(Tile moveFrom, Tile moveTo)
    {
        if (moveFrom.getPiece().isWhite())
            board.getTile(moveTo.getX(), moveTo.getY() - 1).setPiece(null);
        else
            board.getTile(moveTo.getX(), moveTo.getY() + 1).setPiece(null);

        move(moveFrom, moveTo);

        moveTo.getPiece().movesMinusOne = true;
        moveTo.getPiece().increaseMoves();
    }
}
