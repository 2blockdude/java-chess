package Chess;

public class PieceMovement
{
    private Board board;

    protected void setBoard(Board board)
    {
        this.board = board;
    }

    // increase pawn movement by one to disable en passant after moving two spaces
    protected void increasePawnsMovementCounterByOneBTWThisIsAWorkAroundForEnPassantBecauseICouldNotThinkOfABetterWayToDoThisIGuessIAlsoWantToGetSomeIdeasIWishIHadMoreExperience(Tile pawn)
    {
        if (pawn != null)
        {
            if (pawn.getPiece() != null)
            {
                if (pawn.getPiece().getId() == 'P')
                {
                    if (pawn.getY() == 3 || pawn.getY() == board.getBoardSizeY() - 4)
                    {
                        if (pawn.getPiece().getMoves() == 1)
                        {
                            pawn.getPiece().increaseMoves();
                            pawn.getPiece().movesMinusOne = true;
                        }
                    }
                }
            }
        }
    }

    protected void castleQueenSide(Tile moveFrom, Tile moveTo)
    {
        // place rook at other side of king
        Piece rook = board.getTile(0, moveFrom.getY()).getPiece();
        board.getTile(moveTo.getX() + 1, moveTo.getY()).setPiece(rook);
        board.getTile(0, moveFrom.getY()).setPiece(null);

        move(moveFrom, moveTo);
    }

    protected void castleKingSide(Tile moveFrom, Tile moveTo)
    {
        // place rook at other side of king
        Piece rook = board.getTile(board.getBoardSizeX() - 1, moveFrom.getY()).getPiece();
        board.getTile(moveTo.getX() - 1, moveTo.getY()).setPiece(rook);
        board.getTile(board.getBoardSizeX() - 1, moveFrom.getY()).setPiece(null);

        move(moveFrom, moveTo);
    }

    protected void promotePawn(Tile promotee, char promoteTo)
    {
        if (promotee.getPiece() != null)
        {
            if (promotee.getPiece().getId() == 'P')
            {
                Piece pawn = promotee.getPiece();

                switch (promoteTo)
                {
                    case 'Q':
                        promotee.setPiece(new Queen(pawn.isWhite()));
                        promotee.getPiece().setMoves(pawn.getMoves());
                        promotee.getPiece().movesMinusOne = pawn.movesMinusOne;
                        break;
                    case 'R':
                        promotee.setPiece(new Rook(pawn.isWhite()));
                        promotee.getPiece().setMoves(pawn.getMoves());
                        promotee.getPiece().movesMinusOne = pawn.movesMinusOne;
                        break;
                    case 'B':
                        promotee.setPiece(new Bishop(pawn.isWhite()));
                        promotee.getPiece().setMoves(pawn.getMoves());
                        promotee.getPiece().movesMinusOne = pawn.movesMinusOne;
                        break;
                    case 'H':
                        promotee.setPiece(new Horse(pawn.isWhite()));
                        promotee.getPiece().setMoves(pawn.getMoves());
                        promotee.getPiece().movesMinusOne = pawn.movesMinusOne;
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }
    }

    protected void enPassant(Tile moveFrom, Tile moveTo)
    {
        if (moveFrom.getPiece().isWhite())
            board.getTile(moveTo.getX(), moveTo.getY() - 1).setPiece(null);
        else
            board.getTile(moveTo.getX(), moveTo.getY() + 1).setPiece(null);

        move(moveFrom, moveTo);

        moveTo.getPiece().movesMinusOne = true;
        moveTo.getPiece().increaseMoves();
    }

    protected void move(Tile moveFrom, Tile moveTo)
    {
        board.getTile(moveTo.getX(), moveTo.getY()).setPiece(moveFrom.getPiece());
        board.getTile(moveFrom.getX(), moveFrom.getY()).setPiece(null);

        moveTo.getPiece().increaseMoves();
    }
}
