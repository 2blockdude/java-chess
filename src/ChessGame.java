package Chess;

import java.util.ArrayList;

public class ChessGame extends GameBoard
{
    private final ArrayList<Piece> captured = new ArrayList<>();
    private int turns = 0;

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

    // converts chess coordinates like "c4" to x = 2, y = 3 note: minus one because index is base 0
    // no input validation because lazy
    public Tile getTile(String chessCoordinates)
    {
        int x = chessCoordinates.charAt(0) - 97;
        int y = Integer.parseInt(String.valueOf(chessCoordinates.charAt(1))) - 1;

        return getTile(x, y);
    }

    public boolean isMoveLegal(int pieceX, int pieceY, int destinationX, int destinationY)
    {
        Tile moveFrom = getTile(pieceX, pieceY);
        Tile moveTo = getTile(destinationX, destinationY);

        return isMoveLegal(moveFrom, moveTo);
    }

    public boolean isMoveLegal(String piece, String destination)
    {
        Tile moveFrom = getTile(piece);
        Tile moveTo = getTile(destination);

        return isMoveLegal(moveFrom, moveTo);
    }

    public void movePiece(int pieceX, int pieceY, int destinationX, int destinationY)
    {
        Tile moveFrom = getTile(pieceX, pieceY);
        Tile moveTo = getTile(destinationX, destinationY);

        movePiece(moveFrom, moveTo);
    }

    public void movePiece(String pieceToMove, String destination)
    {
        Tile moveFrom = getTile(pieceToMove);
        Tile moveTo = getTile(destination);

        movePiece(moveFrom, moveTo);
    }

    @Override
    public void movePiece(Tile moveFrom, Tile moveTo)
    {
        // don't forget at the end of moving increase all pawns, who have moved once and is on the en passant row, moves by one
                if (moveFrom.getPiece().isWhite() == (turns % 2 == 0))
                {
                    if (isMoveLegal(moveFrom, moveTo))
                    {
                        turns++;
                        if (moveTo.getPiece() != null)
                        {
                            captured.add(moveTo.getPiece());
                        }
                    }
                    super.movePiece(moveFrom, moveTo);
                }
    }

    public boolean isKingInCheck()
    {
        return super.isKingInCheck(isTurnWhite());
    }

    public boolean isKingInCheckmate()
    {
        return super.isKingInCheckmate(isTurnWhite());
    }
}
