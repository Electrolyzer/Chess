package chess;

import java.util.ArrayList;

import chess.ReturnPiece.PieceFile;
import chess.ReturnPiece.PieceType;
import chess.ReturnPlay.Message;
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

	private static Board<Piece> board;
	private static boolean playerIsWhite = true;
	
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
		Message message = moveParser.parseMove(move, playerIsWhite);
		ArrayList<ReturnPiece> returnPieces = new ArrayList<ReturnPiece>();
		ReturnPiece pieceToAdd;

		for(Piece piece : board){
			if(piece == null) { continue; }
			pieceToAdd = pieceToReturnPiece(piece);
			returnPieces.add(pieceToAdd);
		}

		if(message == null || message == Message.CHECK){
			playerIsWhite = !playerIsWhite;
		}

		ReturnPlay returnPlay = new ReturnPlay();
		returnPlay.message = message;
		returnPlay.piecesOnBoard = returnPieces;
		return returnPlay;
	}
	
	private static ReturnPiece pieceToReturnPiece(Piece pieceToConvert){
		ReturnPiece returnPiece = new ReturnPiece();
		returnPiece.pieceRank = pieceToConvert.getRank() + 1;

		switch(pieceToConvert.getFile()){
			case 0:
				returnPiece.pieceFile = PieceFile.a;
				break;
			case 1:
				returnPiece.pieceFile = PieceFile.b;
				break;
			case 2:
				returnPiece.pieceFile = PieceFile.c;
				break;
			case 3:
				returnPiece.pieceFile = PieceFile.d;
				break;
			case 4:
				returnPiece.pieceFile = PieceFile.e;
				break;
			case 5:
				returnPiece.pieceFile = PieceFile.f;
				break;
			case 6:
				returnPiece.pieceFile = PieceFile.g;
				break;
			case 7:
				returnPiece.pieceFile = PieceFile.h;
				break;
		}

		String pieceType = (pieceToConvert.isWhite() ? "W" : "B") + pieceToConvert.getType();

		switch(pieceType){
			case "WP":
				returnPiece.pieceType = PieceType.WP;
				break;
			case "WR":
				returnPiece.pieceType = PieceType.WR;
				break;
			case "WN":
				returnPiece.pieceType = PieceType.WN;
				break;
			case "WB":
				returnPiece.pieceType = PieceType.WB;
				break;
			case "WQ":
				returnPiece.pieceType = PieceType.WQ;
				break;
			case "WK":
				returnPiece.pieceType = PieceType.WK;
				break;
			case "BP":
				returnPiece.pieceType = PieceType.BP;
				break;
			case "BR":
				returnPiece.pieceType = PieceType.BR;
				break;
			case "BN":
				returnPiece.pieceType = PieceType.BN;
				break;
			case "BB":
				returnPiece.pieceType = PieceType.BB;
				break;
			case "BQ":
				returnPiece.pieceType = PieceType.BQ;
				break;
			case "BK":
				returnPiece.pieceType = PieceType.BK;
				break;
		}
	

		return returnPiece;
	}
	
	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
		/* FILL IN THIS METHOD */
		Piece.Board = new Board<Piece>();
		board = Piece.Board;
		Square curPos;
		for(int i=0;i<8;i++){
			curPos = new Square(i, 0);
			if(i==0 || i==7) Piece.Board.setPosition(curPos, new Rook(curPos, true));
			if(i==1 || i==6) Piece.Board.setPosition(curPos, new Knight(curPos, true));
			if(i==2 || i==5) Piece.Board.setPosition(curPos, new Bishop(curPos, true));
			if(i==3) Piece.Board.setPosition(curPos, new King(curPos, true));
			if(i==4) Piece.Board.setPosition(curPos, new Queen(curPos, true));
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
			if(i==3) Piece.Board.setPosition(curPos, new King(curPos, false));
			if(i==4) Piece.Board.setPosition(curPos, new Queen(curPos, false));
		}
		moveParser.updateLoop();
	}
}
