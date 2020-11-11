package Chess;

import java.util.ArrayList;

public class ChessGame extends PieceMovement
{
    private Board board = new Board(8, 8);
    private Tile movedPiece = null;
    private final ArrayList<Piece> captured = new ArrayList<>();
    private int turns = 0;
    private boolean needPromotion = false;

    public ChessGame()
    {
        setBoard(board);
    }

    public ArrayList<Piece> getCaptured()
    {
        return captured;
    }

    public int getTurns()
    {
        return turns;
    }

    public boolean isTurnWhite()
    {
        return turns % 2 == 0;
    }

    public boolean isNeedPromotion()
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

    public Tile getTile(String chessCoordinates)
    {
        int x = chessCoordinates.charAt(0) - 97;
        int y = Integer.parseInt(String.valueOf(chessCoordinates.charAt(1))) - 1;

        return board.getTile(x, y);
    }

    public boolean isMoveLegal(int pieceX, int pieceY, int destinationX, int destinationY)
    {
        Tile moveFrom = board.getTile(pieceX, pieceY);
        Tile moveTo = board.getTile(destinationX, destinationY);

        return isMoveLegal(moveFrom, moveTo);
    }

    public boolean isMoveLegal(String piece, String destination)
    {
        Tile moveFrom = getTile(piece);
        Tile moveTo = getTile(destination);

        return isMoveLegal(moveFrom, moveTo);
    }

    public boolean isMoveLegal(Tile moveFrom, Tile moveTo)
    {
        if (needPromotion)
            return false;

        int legalValue = moveFrom.getPiece().isMoveLegal(board, moveFrom, moveTo);

        return legalValue > 0;
    }

    public void movePiece(int pieceX, int pieceY, int destinationX, int destinationY)
    {
        Tile moveFrom = board.getTile(pieceX, pieceY);
        Tile moveTo = board.getTile(destinationX, destinationY);

        movePiece(moveFrom, moveTo);
    }

    public void movePiece(String pieceToMove, String destination)
    {
        Tile moveFrom = getTile(pieceToMove);
        Tile moveTo = getTile(destination);

        movePiece(moveFrom, moveTo);
    }

    public void movePiece(Tile moveFrom, Tile moveTo)
    {
        // don't forget at the end of moving increase all pawns, who have moved once and is on the en passant row, moves by one
        if (!needPromotion)
        {
            if (moveFrom.getPiece() != null)
            {
                if (moveFrom.getPiece().isWhite() == (turns % 2 == 0))
                {
                    int legalValue = moveFrom.getPiece().isMoveLegal(board, moveFrom, moveTo);

                    if (legalValue > 0)
                    {
                        increasePawnsMovementCounterByOneBTWThisIsAWorkAroundForEnPassantBecauseICouldNotThinkOfABetterWayToDoThisIGuessIAlsoWantToGetSomeIdeasIWishIHadMoreExperience(movedPiece);
                        movedPiece = moveTo;
                        turns++;
                        if (moveTo.getPiece() != null)
                        {
                            captured.add(moveTo.getPiece());
                        }
                    }

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
        }
    }

    // checks the king based on turn i.e. if it is black's turn it will check the black king and visa versa.
    public boolean isKingInCheck()
    {
        // loops at tile looking for enemy piece and if it has a move that can take the king
        for (int i = 0; i < board.getBoardSizeX(); i++)
            for (int j = 0; j < board.getBoardSizeY(); j++)
                if (board.getTile(i, j).getPiece() != null)
                    if (board.getTile(i, j).getPiece().getId() == 'K')
                        if (board.getTile(i, j).getPiece().isWhite() == (turns % 2 == 0))
                            for (int x = 0; x < board.getBoardSizeX(); x++)
                                for (int y = 0; y < board.getBoardSizeY(); y++)
                                    if (board.getTile(x, y).getPiece() != null)
                                        if (board.getTile(x, y).getPiece().isWhite() != (turns % 2 == 0))
                                            if (board.getTile(x, y).getPiece().isMoveLegal(board, board.getTile(x, y), board.getTile(i, j)) > 0)
                                                return true;
        return false;
    }

    public boolean isKingInCheckmate()
    {
        // loops through add pieces that are on the same team and sees if there is a valid move. if it finds none then... you know the rest...
        for (int x = 0; x < board.getBoardSizeX(); x++)
            for (int y = 0; y < board.getBoardSizeY(); y++)
                if (board.getTile(x, y).getPiece() != null)
                    if (board.getTile(x, y).getPiece().isWhite() == (turns % 2 == 0))
                        for (int i = 0; i < board.getBoardSizeX(); i++)
                            for (int j = 0; j < board.getBoardSizeY(); j++)
                                if (isMoveLegal(board.getTile(x, y), board.getTile(i, j)))
                                    return false;
        return true;
    }

    public void promoteLastMovedPiece(char promoteTo)
    {
        promotePawn(movedPiece, promoteTo);
        needPromotion = false;
    }
}
