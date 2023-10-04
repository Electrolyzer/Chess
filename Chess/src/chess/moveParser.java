package chess;
import chess.Piece.moveType;
import chess.pieces.*;
import chess.ReturnPlay.Message;;

public class moveParser {
    
    private moveParser(){
    }

    public static Message parseMove(String moveToParse, boolean curPlayer){
        
        int nextSpace;
        char pieceToBecome = '\0';
        Square[] move = new Square[2];

        int counter = 0;

        while(moveToParse != ""){

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
                    Square[] rookMove = {new Square(8, move[1].getRank()), new Square(6, move[1].getRank())};
                    executeMove(rookMove);
                }else{
                    Square[] rookMove = {new Square(1, move[1].getRank()), new Square(4, move[1].getRank())};
                    executeMove(rookMove);
                }
                break;
            case ENPASSANT:
                executeMove(move);
                Piece.Board.setPosition(move[1].getFile(), move[0].getRank(), null); //Delete the Pawn that was En Passanted
                break;
            case PROMOTE:
                executeMove(move);
                promotePawn(move[1], pieceToBecome); //Change pawn after movement
                break;
        }        
        return null;
    }


    public static void executeMove(Square[] move){
        Piece pieceToMove = Piece.Board.getPosition(move[0]);
        pieceToMove.move(move[1]);
        Piece.Board.setPosition(move[1], pieceToMove);
        Piece.Board.setPosition(move[0], null);
        updateLoop();
    }

    public static void promotePawn(Square pawnPos, char pieceToBecome){
        Pawn pawn = (Pawn)Piece.Board.getPosition(pawnPos);  
        pawn.promotePawn(pieceToBecome);
    }

    public static moveType checkMoveValidity(Square[] move, boolean curPlayer){
        if(Piece.Board.getPosition(move[0]) == null) return moveType.INVALID;
        if(Piece.Board.getPosition(move[0]).isWhite() != curPlayer) return moveType.INVALID;
        return Piece.Board.getPosition(move[0]).isValidMove(move[1]);
    }

    public static void updateLoop(){
        for(Piece piece : Piece.Board){
			if(piece == null) { continue; }
			piece.updateValidMoves();;
		}
    }
}
