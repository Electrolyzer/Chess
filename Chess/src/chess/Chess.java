package chess;

import java.util.ArrayList;

import chess.pieces.*;

class ReturnPiece {
	static enum PieceType {WP, WR, WN, WB, WQ, WK, 
		            BP, BR, BN, BB, BK, BQ};
	static enum PieceFile {a, b, c, d, e, f, g, h};
	
	PieceType pieceType;
	PieceFile pieceFile;
	int pieceRank;  // 1..8
	public String toString() {
		return ""+pieceFile+pieceRank+":"+pieceType;
	}
	public boolean equals(Object other) {
		if (other == null || !(other instanceof ReturnPiece)) {
			return false;
		}
		ReturnPiece otherPiece = (ReturnPiece)other;
		return pieceType == otherPiece.pieceType &&
				pieceFile == otherPiece.pieceFile &&
				pieceRank == otherPiece.pieceRank;
	}
}

class ReturnPlay {
	enum Message {ILLEGAL_MOVE, DRAW, 
				  RESIGN_BLACK_WINS, RESIGN_WHITE_WINS, 
				  CHECK, CHECKMATE_BLACK_WINS,	CHECKMATE_WHITE_WINS, 
				  STALEMATE};
	
	ArrayList<ReturnPiece> piecesOnBoard;
	Message message;
}

public class Chess {
	
	enum Player { white, black }
	
	/**
	 * Plays the next move for whichever player has the turn.
	 * 
	 * @param move String for next move, e.g. "a2 a3"
	 * 
	 * @return A ReturnPlay instance that contains the result of the move.
	 *         See the section "The Chess class" in the assignment description for details of
	 *         the contents of the returned ReturnPlay instance.
	 */
	public static ReturnPlay play(String move) {

		/* FILL IN THIS METHOD */
		
		/* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
		/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
		return null;
	}
	
	
	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
		/* FILL IN THIS METHOD */
		Piece.Board = new Board<Piece>();
		Square curPos;
		for(int i=0;i<8;i++){
			curPos = new Square(i, 0);
			if(i==0 || i==7) Piece.Board.setPosition(curPos, new Rook(curPos, true));
			if(i==1 || i==6) Piece.Board.setPosition(curPos, new Knight(curPos, true));
			if(i==2 || i==5) Piece.Board.setPosition(curPos, new Bishop(curPos, true));
			if(i==4) Piece.Board.setPosition(curPos, new King(curPos, true));
			if(i==5) Piece.Board.setPosition(curPos, new Queen(curPos, true));
		}
		for(int i=0;i<8;i++){
			curPos = new Square(i, 1);
			Piece.Board.setPosition(curPos, new Pawn(curPos, true));
		}
		for(int i=0;i<8;i++){
			curPos = new Square(i, 6);
			Piece.Board.setPosition(curPos, new Pawn(curPos, false));
		}
		for(int i=0;i<8;i++){
			curPos = new Square(i, 7);
			if(i==0 || i==7) Piece.Board.setPosition(curPos, new Rook(curPos, false));
			if(i==1 || i==6) Piece.Board.setPosition(curPos, new Knight(curPos, false));
			if(i==2 || i==5) Piece.Board.setPosition(curPos, new Bishop(curPos, false));
			if(i==4) Piece.Board.setPosition(curPos, new King(curPos, false));
			if(i==5) Piece.Board.setPosition(curPos, new Queen(curPos, false));
		}
	}
}
