package Chess;

import java.util.ArrayList;
import java.util.Scanner;

public class ChessGame
{
    public Board board = new Board(8, 8);
    private static Scanner s = new Scanner(System.in);
    private Tile movedPiece;
    private ArrayList<Piece> captured = new ArrayList<>();
    private int turns = 0;

    public int getTurns()
    {
        return turns;
    }

    public boolean isTurnWhite()
    {
        return turns % 2 == 0;
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
        int legalValue = moveFrom.getPiece().isMoveLegal(board, moveFrom, moveTo);

        if (legalValue > 0)
            return true;
        return false;
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
        if (moveFrom.getPiece() != null)
        {
            if (moveFrom.getPiece().isWhite() == (turns % 2 == 0))
            {
                int legalValue = moveFrom.getPiece().isMoveLegal(board, moveFrom, moveTo);

                increasePawnsMovementCounterByOneBTWThisIsAWorkAroundForEnPassantBecauseICouldNotThinkOfABetterWayToDoThisIGuessIAlsoWantToGetSomeIdeasIWishIHadMoreExperience(board, moveTo);

                switch (legalValue)
                {
                    case 1:
                        move(moveFrom, moveTo);
                        turns++;
                        break;
                    case 2:
                        enPassant(moveFrom, moveTo);
                        turns++;
                        break;
                    case 3:
                        promotePawn(moveFrom, moveTo);
                        turns++;
                        break;
                    case 4:
                        castleKingSide(moveFrom, moveTo);
                        turns++;
                        break;
                    case 5:
                        castleQueenSide(moveFrom, moveTo);
                        turns++;
                        break;
                    default:
                        break;
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

    // only checks the 4th and 5th row assuming this is an 8 x 8 board
    private void increasePawnsMovementCounterByOneBTWThisIsAWorkAroundForEnPassantBecauseICouldNotThinkOfABetterWayToDoThisIGuessIAlsoWantToGetSomeIdeasIWishIHadMoreExperience(Board board, Tile moveTo)
    {
        if (movedPiece != null)
        {
            if (movedPiece.getPiece().getId() == 'P')
            {
                if (movedPiece.getY() == 3 || movedPiece.getY() == board.getBoardSizeY() - 4)
                {
                    if (movedPiece.getPiece().getMoves() == 1)
                    {
                        movedPiece.getPiece().increaseMoves();
                        movedPiece.getPiece().movesMinusOne = true;
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

    private void promotePawn(Tile moveFrom, Tile moveTo)
    {
        boolean needInput = true;
        while (needInput)
        {
            System.out.println("Promote Pawn");
            System.out.println("Q:Queen, R:Rook, B:Bishop, H:Horse.");
            System.out.print("Piece: ");
//            char pieceSelected = s.nextLine().charAt(0);
            char pieceSelected = 'Q';
            move(moveFrom, moveTo);

            Piece pawn = moveTo.getPiece();
            switch (pieceSelected)
            {
                case 'Q':
                    moveTo.setPiece(new Queen(pawn.isWhite()));
                    moveTo.getPiece().setMoves(pawn.getMoves());
                    moveTo.getPiece().movesMinusOne = pawn.movesMinusOne;
                    needInput = false;
                    break;
                case 'R':
                    moveTo.setPiece(new Rook(pawn.isWhite()));
                    moveTo.getPiece().setMoves(pawn.getMoves());
                    moveTo.getPiece().movesMinusOne = pawn.movesMinusOne;
                    needInput = false;
                    break;
                case 'B':
                    moveTo.setPiece(new Bishop(pawn.isWhite()));
                    moveTo.getPiece().setMoves(pawn.getMoves());
                    moveTo.getPiece().movesMinusOne = pawn.movesMinusOne;
                    needInput = false;
                    break;
                case 'H':
                    moveTo.setPiece(new Horse(pawn.isWhite()));
                    moveTo.getPiece().setMoves(pawn.getMoves());
                    moveTo.getPiece().movesMinusOne = pawn.movesMinusOne;
                    needInput = false;
                    break;
                default:
                    break;
            }
        }
    }

    private void enPassant(Tile moveFrom, Tile moveTo)
    {
        if (moveFrom.getPiece().isWhite())
        {
            captured.add(board.getTile(moveTo.getX(), moveTo.getY() - 1).getPiece());
            board.getTile(moveTo.getX(), moveTo.getY() - 1).setPiece(null);
        }
        else
        {
            captured.add(board.getTile(moveTo.getX(), moveTo.getY() + 1).getPiece());
            board.getTile(moveTo.getX(), moveTo.getY() + 1).setPiece(null);
        }
        move(moveFrom, moveTo);

        moveTo.getPiece().movesMinusOne = true;
        moveTo.getPiece().increaseMoves();
    }

    private void move(Tile moveFrom, Tile moveTo)
    {
        if (moveTo.getPiece() != null)
        {
            captured.add(moveTo.getPiece());
        }

        board.getTile(moveTo.getX(), moveTo.getY()).setPiece(moveFrom.getPiece());
        board.getTile(moveFrom.getX(), moveFrom.getY()).setPiece(null);

        moveTo.getPiece().increaseMoves();

        movedPiece = moveTo;
    }
}
