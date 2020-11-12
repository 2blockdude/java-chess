package Chess;

public class GameBoard
{
    private Board board = new Board(8, 8);
    private Tile movedPiece = null;
    private boolean needPromotion = false;

    public void setBoard(Board board)
    {
        this.board = board;
    }

    // I know. I know. The method name is the same as a variable name...
    public boolean needPromotion()
    {
        return needPromotion;
    }

    public int getBoardSizeX()
    {
        return board.getBoardSizeX();
    }

    public int getBoardSizeY()
    {
        return board.getBoardSizeY();
    }

    public Tile getTile(int x, int y)
    {
        return board.getTile(x, y);
    }

    public boolean isMoveLegal(Tile moveFrom, Tile moveTo)
    {
        if (needPromotion)
            return false;

        int legalValue = moveFrom.getPiece().isMoveLegal(board, moveFrom, moveTo);

        return legalValue > 0;
    }

    // checks the king based on turn i.e. if it is black's turn it will check the black king and visa versa.
    public boolean isKingInCheck(boolean whiteSide)
    {
        // first loops through the board to find the king
        // then loops through board looking for enemy piece and if they have a move that can take the king
        for (int i = 0; i < board.getBoardSizeX(); i++)
            for (int j = 0; j < board.getBoardSizeY(); j++)
                if (board.getTile(i, j).getPiece() != null)
                    if (board.getTile(i, j).getPiece().getId() == 'K')
                        if (board.getTile(i, j).getPiece().isWhite() == whiteSide)
                            for (int x = 0; x < board.getBoardSizeX(); x++)
                                for (int y = 0; y < board.getBoardSizeY(); y++)
                                    if (board.getTile(x, y).getPiece() != null)
                                        if (board.getTile(x, y).getPiece().isWhite() != whiteSide)
                                            if (board.getTile(x, y).getPiece().isMoveLegal(board, board.getTile(x, y), board.getTile(i, j)) > 0)
                                                return true;
        return false;
    }

    public boolean isKingInCheckmate(boolean whiteSide)
    {
        // loops through add pieces that are on the same team and sees if there is a valid move. if it finds none then... you know the rest...
        for (int x = 0; x < board.getBoardSizeX(); x++)
            for (int y = 0; y < board.getBoardSizeY(); y++)
                if (board.getTile(x, y).getPiece() != null)
                    if (board.getTile(x, y).getPiece().isWhite() == whiteSide)
                        for (int i = 0; i < board.getBoardSizeX(); i++)
                            for (int j = 0; j < board.getBoardSizeY(); j++)
                                if (isMoveLegal(board.getTile(x, y), board.getTile(i, j)))
                                    return false;
        return true;
    }

    public void movePiece(Tile moveFrom, Tile moveTo)
    {
        // don't forget at the end of moving increase all pawns, who have moved once and is on the en passant row, moves by one
        if (!needPromotion)
        {
            if (moveFrom.getPiece() != null)
            {
                int legalValue = moveFrom.getPiece().isMoveLegal(board, moveFrom, moveTo);

                if (legalValue > 0)
                    disableEnPassant(movedPiece);

                switch (legalValue)
                {
                    case 1:
                        move(moveFrom, moveTo);
                        break;
                    case 2:
                        enPassant(moveFrom, moveTo);
                        break;
                    case 3:
                        move(moveFrom, moveTo);
                        needPromotion = true;
                        break;
                    case 4:
                        castleKingSide(moveFrom, moveTo);
                        break;
                    case 5:
                        castleQueenSide(moveFrom, moveTo);
                        break;
                    default:
                        break;
                }
            }
        }
        else
        {
            System.out.println("Pawn Needs Promotion");
        }
    }

    public void promotePawn(char promoteTo)
    {
        Tile promotee = movedPiece;

        if (promotee.getPiece() != null)
        {
            if (promotee.getPiece().getId() == 'P')
            {
                Piece pawn = promotee.getPiece();

                needPromotion = false;

                switch (promoteTo)
                {
                    case 'Q' -> {
                        promotee.setPiece(new Queen(pawn.isWhite()));
                        promotee.getPiece().setMoves(pawn.getMoves());
                        promotee.getPiece().movesMinusOne = pawn.movesMinusOne;
                    }
                    case 'R' -> {
                        promotee.setPiece(new Rook(pawn.isWhite()));
                        promotee.getPiece().setMoves(pawn.getMoves());
                        promotee.getPiece().movesMinusOne = pawn.movesMinusOne;
                    }
                    case 'B' -> {
                        promotee.setPiece(new Bishop(pawn.isWhite()));
                        promotee.getPiece().setMoves(pawn.getMoves());
                        promotee.getPiece().movesMinusOne = pawn.movesMinusOne;
                    }
                    case 'H' -> {
                        promotee.setPiece(new Horse(pawn.isWhite()));
                        promotee.getPiece().setMoves(pawn.getMoves());
                        promotee.getPiece().movesMinusOne = pawn.movesMinusOne;
                    }
                    default -> throw new IllegalArgumentException();
                }
            }
        }
    }

    // increase pawn movement by one to disable en passant after moving two spaces
    private void disableEnPassant(Tile pawn)
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

    private void castleQueenSide(Tile moveFrom, Tile moveTo)
    {
        // place rook at other side of king
        Piece rook = board.getTile(0, moveFrom.getY()).getPiece();
        board.getTile(moveTo.getX() + 1, moveTo.getY()).setPiece(rook);
        board.getTile(0, moveFrom.getY()).setPiece(null);

        move(moveFrom, moveTo);
    }

    private void castleKingSide(Tile moveFrom, Tile moveTo)
    {
        // place rook at other side of king
        Piece rook = board.getTile(board.getBoardSizeX() - 1, moveFrom.getY()).getPiece();
        board.getTile(moveTo.getX() - 1, moveTo.getY()).setPiece(rook);
        board.getTile(board.getBoardSizeX() - 1, moveFrom.getY()).setPiece(null);

        move(moveFrom, moveTo);
    }

    private void enPassant(Tile moveFrom, Tile moveTo)
    {
        if (moveFrom.getPiece().isWhite())
            board.getTile(moveTo.getX(), moveTo.getY() - 1).setPiece(null);
        else
            board.getTile(moveTo.getX(), moveTo.getY() + 1).setPiece(null);

        move(moveFrom, moveTo);

        moveTo.getPiece().movesMinusOne = true;
        moveTo.getPiece().increaseMoves();
    }

    private void move(Tile moveFrom, Tile moveTo)
    {
        board.getTile(moveTo.getX(), moveTo.getY()).setPiece(moveFrom.getPiece());
        board.getTile(moveFrom.getX(), moveFrom.getY()).setPiece(null);

        moveTo.getPiece().increaseMoves();
        movedPiece = moveTo;
    }
}
