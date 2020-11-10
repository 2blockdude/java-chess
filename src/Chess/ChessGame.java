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

    public void setScanner(Scanner scanner)
    {
        this.s = scanner;
    }

    public void isLegalMove(Tile moveFrom, Tile moveTo)
    {
        
    }

    public void movePiece(Tile moveFrom, Tile moveTo)
    {
        // don't forget at the end of moving increase all pawns, who have moved once and is on the en passant row, moves by one
        if (moveFrom.getPiece() != null)
        {
//            if (moveFrom.getPiece().isWhite() == (turns % 2 == 0))
//            {
                int legalValue = moveFrom.getPiece().isLegalMove(board, moveFrom, moveTo);

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
//            }
        }
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
            char pieceSelected = s.nextLine().charAt(0);
            move(moveFrom, moveTo);

            Piece pawn = moveTo.getPiece();
            switch (pieceSelected)
            {
                case 'Q':
                    moveTo.setPiece(new Queen(pawn.isWhite(), 50));
                    moveTo.getPiece().setMoves(pawn.getMoves());
                    moveTo.getPiece().movesMinusOne = pawn.movesMinusOne;
                    needInput = false;
                    break;
                case 'R':
                    moveTo.setPiece(new Rook(pawn.isWhite(), 50));
                    moveTo.getPiece().setMoves(pawn.getMoves());
                    moveTo.getPiece().movesMinusOne = pawn.movesMinusOne;
                    needInput = false;
                    break;
                case 'B':
                    moveTo.setPiece(new Bishop(pawn.isWhite(), 50));
                    moveTo.getPiece().setMoves(pawn.getMoves());
                    moveTo.getPiece().movesMinusOne = pawn.movesMinusOne;
                    needInput = false;
                    break;
                case 'H':
                    moveTo.setPiece(new Horse(pawn.isWhite(), 50));
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
