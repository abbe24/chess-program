import java.util.ArrayList;

public class Node {

	private Piece[][] position;
	private int level;
	private Move move; //move that led to position
	private Node parent;
	private ArrayList<Node> children = new ArrayList<Node>();
	private int eval;
	private ChessBoard boardObj;
	
	public Node(Node parent_i, ChessBoard boardObj_i,  Move move_i, int level_i) {
		this.level = level_i;
		this.parent = parent_i;
		this.move = move_i;
		this.boardObj = boardObj_i;
		this.position = boardObj.getBoard();
	}
	
	public Node(ChessBoard boardObj_i) {
		this.boardObj = boardObj_i;
		this.position = boardObj.getBoard();
		this.level = 0;
	}
	
	public ArrayList<Node> getChildren(){
		return children;
	}
	
	public Move getMove() {
		return move;
	}
	
	public void generateChildren() {
		boardObj.updateAllPossibleMoves();
		ChessBoard alteration = new ChessBoard();
		alteration.setAIOn(true);
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				alteration.getBoard()[r][c] = new Piece(boardObj.getBoard()[r][c].getColorSet()[boardObj.getBoard()[r][c].getColor()], boardObj.getBoard()[r][c].getTypeSet()[boardObj.getBoard()[r][c].getType()], r, c);
			}
		}
		int r1; 
		int c1; 
		int r2;  
		int c2; 
		int allPossibleMovesSize = boardObj.getAllPossibleMoves().size();
		for(int i = 0; i < allPossibleMovesSize; i++) {
			r1 = boardObj.getAllPossibleMoves().get(i).getR1();
			c1 = boardObj.getAllPossibleMoves().get(i).getC1();
			r2 = boardObj.getAllPossibleMoves().get(i).getR2();
			c2 = boardObj.getAllPossibleMoves().get(i).getC2();
			alteration.movePiece(alteration, alteration.getBoard()[r1][c1], boardObj.getTurn(), r1, c1, r2, c2);
			
			children.add(new Node(this, alteration, new Move(r1, c1, r2, c2), level + 1));
			
			for(int r = 0; r < 8; r++) {
				for(int c = 0; c < 8; c++) {
					alteration.getBoard()[r][c] = new Piece(boardObj.getBoard()[r][c].getColorSet()[boardObj.getBoard()[r][c].getColor()], boardObj.getBoard()[r][c].getTypeSet()[boardObj.getBoard()[r][c].getType()], r, c);
				}
			}
		}
		//children.add(new Node(this, newPosition, newMove, level + 1))
	}
}
