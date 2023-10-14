package chess;
import chess.Piece.moveType;
import chess.pieces.*;
import chess.ReturnPlay.Message;
import java.util.ArrayList;

public class moveParser {
    
    private moveParser(){
    }

    public static Message parseMove(String moveToParse, boolean curPlayer){
        
        int nextSpace;
        char pieceToBecome = '\0';
        Square[] move = new Square[2];

        int counter = 0;

        while(!moveToParse.equals("")){

            //Find where the next space is in the string
            nextSpace = moveToParse.indexOf(" ");
            //If the next char is a space, slice it off then continue
            if(nextSpace == 0){ moveToParse = moveToParse.substring(1); continue; }

            //Otherwise parse the characters. First check for resign, draw or promotion, then try to parse a position
            else{
                if(moveToParse.indexOf("resign") != -1){
                    return curPlayer ? Message.RESIGN_BLACK_WINS : Message.RESIGN_WHITE_WINS;
                }else if(moveToParse.indexOf("draw?") == 0){
                    executeMove(move);
                    return Message.DRAW;
                }else if( "BNRQ".indexOf(moveToParse.charAt(0)) != -1){ 
                    pieceToBecome = moveToParse.charAt(0);
                    moveToParse = moveToParse.substring(1);
                }else{
                    move[counter] = new Square((int)(moveToParse.charAt(0) - 'a'), (int)(moveToParse.charAt(1) - '1'));
                    moveToParse = moveToParse.substring(2);
                }
                counter++;
            }
        }


        switch(checkMoveValidity(move, curPlayer)){
            case INVALID:
                //Create message for invalid move
                return Message.ILLEGAL_MOVE;
            case VALID:
                executeMove(move); //Execute move normally
                System.out.println("valid");
                break;
            case CASTLE: 
                executeMove(move);
                if(move[1].getFile() > move[0].getFile()){
                    Square[] rookMove = {new Square(7, move[1].getRank()), new Square(5, move[1].getRank())};
                    executeMove(rookMove);
                }else{
                    Square[] rookMove = {new Square(0, move[1].getRank()), new Square(3, move[1].getRank())};
                    executeMove(rookMove);
                }
                break;
            case ENPASSANT:
                executeMove(move);
                Piece.DefaultBoard.setPosition(move[1].getFile(), move[0].getRank(), null); //Delete the Pawn that was En Passanted
                break;
            case PROMOTE:
                executeMove(move);
                promotePawn(move[1], pieceToBecome); //Change pawn after movement
                break;
        }

        ArrayList<Board<moveType>> validMoveLists = new ArrayList<Board<moveType>>(); 
        for (Piece piece : Piece.DefaultBoard) {
            if (piece != null && piece.isWhite() != curPlayer)
                validMoveLists.add(piece.validMoves);
        }
        if (!Board.anyValidMoves(validMoveLists))
            return curPlayer ? Message.CHECKMATE_WHITE_WINS : Message.CHECKMATE_BLACK_WINS;
        else if (Board.isInCheck(Piece.DefaultBoard, true) || Board.isInCheck(Piece.DefaultBoard, false)) {
            return Message.CHECK;
        } else {return null;}
    }


    public static void executeMove(Square[] move){
        Piece pieceToMove = Piece.DefaultBoard.getPosition(move[0]);
        pieceToMove.move(move[1]);
        updateLoop();
    }

    public static void promotePawn(Square pawnPos, char pieceToBecome){
        Pawn pawn = (Pawn)Piece.DefaultBoard.getPosition(pawnPos);  
        pawn.promotePawn(pieceToBecome);
    }

    public static moveType checkMoveValidity(Square[] move, boolean curPlayer){
        if(Piece.DefaultBoard.getPosition(move[0]) == null) return moveType.INVALID;
        if(Piece.DefaultBoard.getPosition(move[0]).isWhite() != curPlayer) return moveType.INVALID;
        return Piece.DefaultBoard.getPosition(move[0]).isValidMove(move[1]);
    }

    public static void updateLoop(){
        for(Piece piece : Piece.DefaultBoard){
			if(piece == null) { continue; }
			piece.updateValidMoves();;
		}
    }
}
