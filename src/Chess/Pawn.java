package Chess;

import javax.swing.*;

import static java.lang.Math.abs;

public class Pawn extends Piece
{
    public Pawn(boolean white)
    {
        super(white);
    }

    @Override
    public char getId()
    {
        return 'P';
    }

    @Override
    public int getValue()
    {
        return 1;
    }

    @Override
    public int isMoveLegal(Board board, Tile moveFrom, Tile moveTo)
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

        // illegal if not within one space or two in the y axis if not moved
        if (numSpacesX + numSpacesY > 2 || numSpacesX > 1)
        {
            return 0;
        }

        // can't move is piece if blocking
        if (isPieceBlocking(board, moveFrom, moveTo))
        {
            return 0;
        }
        else
        {
            // check if king is in check and if move will bring it out of check
            // to be honest I don't know how this does not cause a stack overflow error
            if (isKingInCheck(board))
                return 0;
            if (isDestinationCheck(board, moveFrom, moveTo))
                return 0;

                // old method that did not word properly
//            if (checkKing)
//                if (isKingInCheck(board))
//                    if (isDestinationCheck(board, moveFrom, moveTo))
//                        return 0;

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

                // legal move if the enemy pawn is two spaces from the home row, within bounds, the destination has a piece behind it, the piece is a pawn, and the enemy pawn has moved once
                if (((moveFrom.getY() == 3) && !isWhite()) || ((moveFrom.getY() == board.getBoardSizeY() - 4) && isWhite()))
                {
                    if ((moveTo.getY() - 1) >= 0 && (moveTo.getY() - 1) < board.getBoardSizeY())
                        if (board.getTile(moveTo.getX(), moveTo.getY() - 1).getPiece() != null)
                            if (board.getTile(moveTo.getX(), moveTo.getY() - 1).getPiece().getId() == 'P')
                                if (board.getTile(moveTo.getX(), moveTo.getY() - 1).getPiece().getMoves() == 1)
                                    return 2;

                    if ((moveTo.getY() + 1) >= 0 && (moveTo.getY() + 1) < board.getBoardSizeY())
                        if (board.getTile(moveTo.getX(), moveTo.getY() + 1).getPiece() != null)
                            if (board.getTile(moveTo.getX(), moveTo.getY() + 1).getPiece().getId() == 'P')
                                if (board.getTile(moveTo.getX(), moveTo.getY() + 1).getPiece().getMoves() == 1)
                                    return 2;
                }
            }
            // legal if a piece is directly diagonal to the pawn
            else
            {
                if (numSpacesX == 1 && numSpacesY == 1)
                {
                    // gets promoted if at the end of board
                    if (moveTo.getY() == board.getBoardSizeY() - 1 || moveTo.getY() == 0)
                        return 3;
                    return 1;
                }
            }
        }
        return 0;
    }
}
