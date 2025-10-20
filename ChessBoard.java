import java.util.ArrayList;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;

public class ChessBoard {
	static final Color grey = new Color(43, 41, 39);
	static final Color green = new Color(122, 146, 89);
	static final Color greenDot = new Color(109, 132, 81);
	static final Color greenH = new Color(189, 199, 60);
	static final Color greenHDot = new Color(169, 178, 54);
	static final Color white = new Color(235, 235, 208);
	static final Color whiteDot = new Color (213, 212, 188);
	static final Color whiteH = new Color(248, 243, 112);
	static final Color whiteHDot = new Color(222, 218, 101);
	private Piece[][] board = new Piece[8][8];
	private int[][] squares = new int[8][8]; 
	private Square[][] squareColor = new Square[8][8];
	private int turn;
	private int doubleR = 0;
	private int doubleC = 0;
	private boolean promoting = false;
	private int promotingColor = 0;
	private boolean promotingWhite = false;
	private boolean promotingBlack = false;
	private int promotingSquare = 0;
	private int fiftyCount = 0;
	private boolean aiOn = false;
	private ArrayList<Square> whiteInfluence = new ArrayList<Square>(0);
	private ArrayList<Square> blackInfluence = new ArrayList<Square>(0);
	private ArrayList<Square> possibleMoves = new ArrayList<Square>(0);
	private ArrayList<Piece[][]> positions = new ArrayList<Piece[][]>(0);
	private ArrayList<Move> allPossibleMoves = new ArrayList<Move>(0);
	
	
	
	
	ChessBoard(){
		//set pieces
		//updateInfluence();
		this.turn = 1;
		for(int i = 0; i < 8; i++) {
			board[6][i] = new Piece("white", "Pawn", 6, i);
			board[1][i] = new Piece("black", "Pawn", 1, i);

		}
		
		board[0][0] = new Piece("black", "Rook", 0, 0);
		board[0][1] = new Piece("black", "Knight", 0, 1);
		board[0][2] = new Piece("black", "Bishop", 0, 2);
		board[0][3] = new Piece("black", "Queen", 0, 3);
		board[0][4] = new Piece("black", "King", 0, 4);
		board[0][5] = new Piece("black", "Bishop", 0, 5);
		board[0][6] = new Piece("black", "Knight", 0, 6);
		board[0][7] = new Piece("black", "Rook", 0, 7);
		
		board[7][0] = new Piece("white", "Rook", 7, 0);
		board[7][1] = new Piece("white", "Knight", 7, 1);
		board[7][2] = new Piece("white", "Bishop", 7, 2);
		board[7][3] = new Piece("white", "Queen", 7, 3);
		board[7][4] = new Piece("white", "King", 7, 4);
		board[7][5] = new Piece("white", "Bishop", 7, 5);
		board[7][6] = new Piece("white", "Knight", 7, 6);
		board[7][7] = new Piece("white", "Rook", 7, 7);
		
		for(int i2 = 0; i2 < 8; i2++) {
			board[2][i2] = new Piece("empty", "empty", 2, i2);
			board[3][i2] = new Piece("empty", "empty", 3, i2);
			board[4][i2] = new Piece("empty", "empty", 4, i2);
			board[5][i2] = new Piece("empty", "empty", 5, i2);
		}
		
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				if(r % 2 == 0) {
					if(c % 2 == 0) {
						squareColor[r][c] = new Square(r, c, white);
					} else {
						squareColor[r][c] = new Square(r, c, green);
					}
				} else {
					if(c % 2 == 0) {
						squareColor[r][c] = new Square(r, c, green);
					} else {
						squareColor[r][c] = new Square(r, c, white);
					}
				}
			}
		}

//		board[2][0] = new Piece("empty", "empty", 0, 2);
//		System.out.println("testAttempt:");
//		System.out.println(board[3][3].getType());
//		System.out.println("testPassed");
	}
	public Piece[][] getBoard(){
		return board;
	}
	public int getTurn() {
		return turn;
	}
	public void setTurn(int newTurn) {
		this.turn = newTurn;
	}
	public void setDoubleR(int newDoubleR) {
		this.doubleR = newDoubleR;
	}
	public int getDoubleR() {
		return doubleR;
	}
	public void setDoubleC(int newDoubleC) {
		this.doubleC = newDoubleC;
	}
	public int getDoubleC() {
		return doubleC;
	}
	public void setPromoting(boolean a) {
		this.promoting = a;
	}
	public void setPromotingWhite(boolean a) {
		this.promotingWhite = a;
	}
	public void setPromotingBlack(boolean a) {
		this.promotingBlack = a;
	}
	public void setPromotingSquare(int a) {
		this.promotingSquare = a;
	}
	public boolean getPromoting() {
		return promoting;
	}
	public boolean getPromotingWhite() {
		return promotingWhite;
	}
	public boolean getPromotingBlack() {
		return promotingBlack;
	}
	public int getPromotingSquare() {
		return promotingSquare;
	}
	public int getPromotingColor() {
		return promotingColor;
	}
	public int get50Count() {
		return fiftyCount;
	}
	public void set50Count(int new50Count) {
		this.fiftyCount = new50Count;
	}
	public void setSquareColor(Color color, int r, int c) {
		squareColor[r][c].setColor(color);
	}
	public boolean getAIOn() {
		return aiOn;
	}
	public void setAIOn(boolean b) {
		this.aiOn = b;
	}
	public ArrayList<Move> getAllPossibleMoves(){
		return allPossibleMoves;
	}
	public void resetSquareColor() {
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				if(r % 2 == 0) {
					if(c % 2 == 0) {
						squareColor[r][c].setColor(white);// = new Square(r, c, white);
					} else {
						squareColor[r][c].setColor(green);
					}
				} else {
					if(c % 2 == 0) {
						squareColor[r][c].setColor(green);
					} else {
						squareColor[r][c].setColor(white);
					}
				}
			}
		}
	}
	public void resetSquareColor(int r, int c) {
		if(squareColor[r][c].getColor().equals(whiteH)) {
			squareColor[r][c].setColor(white);
		}
		if(squareColor[r][c].getColor().equals(greenH)) {
			squareColor[r][c].setColor(green);
		}
	}
	public void highlightSquare(int r, int c) {
		if(squareColor[r][c].getColor().equals(green)) {
			squareColor[r][c].setColor(greenH);
		}
		if(squareColor[r][c].getColor().equals(white)) {
			squareColor[r][c].setColor(whiteH);
		}
	}
	
	public void printBoardInfo() {
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				board[r][c].printPieceInfo();
			}
		}
	}
	public void promote(int color, int c) {
		System.out.println("Piece is promoting");
		if(turn % 2 == 0 && aiOn) {
			board[7][c] = new Piece("black", "Queen", 7, c);
			this.promoting = false;
		} else {
		this.promoting = true;
		if(color == 1) {
			this.promotingColor = 1;
		}
		if(color == 2) {
			this.promotingColor = 2;
		}
		this.promotingSquare = c;
		}
	}
	
	public boolean hasPiece(int r, int c) {
		if(board[r][c].getType() > 0) {
			return true;
		}
		return false;
	}
	
	public Piece getPiece(int r, int c) {
		return board[r][c];
	}
	
	public void setSquare(Piece piece, int r, int c) {
		this.board[r][c] = piece;
	}
	
	public ArrayList<Square> getWhiteInfluence() {
		return whiteInfluence;
	}
	public ArrayList<Square> getBlackInfluence() {
		return blackInfluence;
	}
	
	public void addPosition() {
		Piece[][] array = new Piece[8][8];
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				array[r][c] = new Piece(board[r][c].getColorSet()[board[r][c].getColor()], board[r][c].getTypeSet()[board[r][c].getType()], r, c);
			}
		}
		positions.add(array);
	}
	
	public void updatePossibleMoves(int r, int c) {
		ChessBoard alternate = new ChessBoard();
		for(int r1 = 0; r1 < 8; r1++) {
			for(int c1 = 0; c1 < 8; c1++) {
				alternate.getBoard()[r1][c1] = new Piece(board[r1][c1].getColorSet()[board[r1][c1].getColor()], board[r1][c1].getTypeSet()[board[r1][c1].getType()], r1, c1);
			}
		}
//		int possibleMovesSize = possibleMoves.size();
//		for(int i = 0; i < possibleMovesSize; i++) {
//			possibleMoves.remove(i);
//			i--;
//		}
		possibleMoves.clear();
		for(int r1 = 0; r1 < 8; r1++) {
			for(int c1 = 0; c1 < 8; c1++) {
				if(alternate.movePiece(alternate, alternate.getBoard()[r][c], turn, r, c, r1, c1)) {
					possibleMoves.add(new Square(r1, c1));
				}
				for(int r2 = 0; r2 < 8; r2++) {
					for(int c2 = 0; c2 < 8; c2++) {
						alternate.getBoard()[r2][c2] = new Piece(board[r2][c2].getColorSet()[board[r2][c2].getColor()], board[r2][c2].getTypeSet()[board[r2][c2].getType()], r2, c2);
					}
				}
			}
		}
	}
	
	public void updateAllPossibleMoves() {
		//int allPossibleMovesSize = allPossibleMoves.size();
		int possibleMovesSize;
//		for(int i = 0; i < allPossibleMovesSize; i++) {
//			allPossibleMoves.remove(i);
//			i--;
//		}
		allPossibleMoves.clear();
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				updatePossibleMoves(r, c);
				possibleMovesSize = possibleMoves.size();
				for(int i = 0; i < possibleMovesSize; i++) {
					allPossibleMoves.add(new Move(r, c, possibleMoves.get(i).getR(), possibleMoves.get(i).getC()));
				}
			}
		}
	}
	
	public void updateInfluence(ChessBoard boardObj) {
		System.out.println("****updateInfluence method*******");
		ArrayList<Integer> types = new ArrayList<Integer>();
		Piece piece = new Piece("empty", "empty", 0, 0);
//		for(int i = 0; i < whiteInfluence.size(); i++) {
//			whiteInfluence.remove(i);
//			i--;
//		}
		whiteInfluence.clear();
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				if(board[r][c].getColor() == 1 && board[r][c].getType() != 0) {
					piece = board[r][c];
					types.add(piece.getType());
					//System.out.println("Type: " + piece.getType());
					for(int r2 = 0; r2 < 8; r2++) {
						for(int c2 = 0; c2 < 8; c2++) {
							if(piece.whiteMoveIsLegal(boardObj, board, turn, r2, c2, true)) {
								this.whiteInfluence.add(new Square(r2, c2));
								//System.out.println("     (" + r2 + ", " + c2 + ")");
							}
						}
					}
				}
				
			}
		}
		
//		for(int i3 = 0; i3 < blackInfluence.size(); i3++) {
//			blackInfluence.remove(i3);
//			i3--;
//		}
		blackInfluence.clear();
		for(int r3 = 0; r3 < 8; r3++) {
			for(int c3 = 0; c3 < 8; c3++) {
				if(board[r3][c3].getColor() == 2 && board[r3][c3].getType() != 0) {
					piece = board[r3][c3];
					types.add(piece.getType());
					//System.out.println("Type: " + piece.getType());
					for(int r4 = 0; r4 < 8; r4++) {
						for(int c4 = 0; c4 < 8; c4++) {
							if(piece.whiteMoveIsLegal(boardObj, board, turn, r4, c4, true)) {
								this.blackInfluence.add(new Square(r4, c4));
								//System.out.println("     (" + r4 + ", " + c4 + ")");
							}
						}
					}
				}
				
			}
		}
		
//		System.out.println("    summary:     ");
//		for(int i2 = 0; i2 < types.size(); i2++) {
//			System.out.println("Type: " + types.get(i2));
//		}

	}
	
	public boolean inCheck(int color) {
		if(color == 2) {
			for(int r = 0; r < 8; r++) {
				for(int c = 0; c < 8; c++) {
					if(board[r][c].getType() == 6 && board[r][c].getColor() == 2) {
						for(int i = 0; i < whiteInfluence.size(); i++) {
							if(whiteInfluence.get(i).getR() == r && whiteInfluence.get(i).getC() == c) {
								return true;
							}
						}
					}
				}
			}
		}
		if(color == 1) {
			for(int r = 0; r < 8; r++) {
				for(int c = 0; c < 8; c++) {
					if(board[r][c].getType() == 6 && board[r][c].getColor() == 1) {
						for(int i = 0; i < blackInfluence.size(); i++) {
							if(blackInfluence.get(i).getR() == r && blackInfluence.get(i).getC() == c) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean inMate(ChessBoard boardObj, int color) {
		ChessBoard alternateBoard = new ChessBoard();
		for(int ri = 0; ri < 8; ri++) {
			for(int ci = 0; ci < 8; ci++) {
				//alternateBoard.getBoard()[ri][ci] = new Piece("empty", "empty", ri, ci);
				alternateBoard.getBoard()[ri][ci] = new Piece(board[ri][ci].getColorSet()[board[ri][ci].getColor()], board[ri][ci].getTypeSet()[board[ri][ci].getType()], ri, ci);
			}
		}
		alternateBoard.setTurn(turn);
		alternateBoard.setDoubleR(doubleR);
		alternateBoard.setDoubleC(doubleC);
		if(color == 2) {
			for(int r = 0; r < 8; r++) {
				for(int c = 0; c < 8; c++) {
					if(board[r][c].getColor() == 2) {
						for(int r2 = 0; r2 < 8; r2++) {
							for(int c2 = 0; c2 < 8; c2++) {
								if(alternateBoard.movePiece(alternateBoard, alternateBoard.getBoard()[r][c], alternateBoard.getTurn(), r, c, r2, c2)) {
									return false;
								}
								for(int ri2 = 0; ri2 < 8; ri2++) {
									for(int ci2 = 0; ci2 < 8; ci2++) {
										alternateBoard.getBoard()[ri2][ci2] = new Piece(board[ri2][ci2].getColorSet()[board[ri2][ci2].getColor()], board[ri2][ci2].getTypeSet()[board[ri2][ci2].getType()], ri2, ci2);
									}
								}
								alternateBoard.setTurn(turn);
								alternateBoard.setDoubleR(doubleR);
								alternateBoard.setDoubleC(doubleC);
							}
						}
					}
				}
			}
		}
		if(color == 1) {
			for(int r = 0; r < 8; r++) {
				for(int c = 0; c < 8; c++) {
					if(board[r][c].getColor() == 1) {
						for(int r2 = 0; r2 < 8; r2++) {
							for(int c2 = 0; c2 < 8; c2++) {
//								if(alternateBoard.getBoard()[r][c].whiteMoveIsLegal(alternateBoard, alternateBoard.getBoard(), turn, r2, c2, false)) {
//									
//								}
								if(alternateBoard.movePiece(alternateBoard, alternateBoard.getBoard()[r][c], alternateBoard.getTurn(), r, c, r2, c2)) {
									return false;
								}
								for(int ri2 = 0; ri2 < 8; ri2++) {
									for(int ci2 = 0; ci2 < 8; ci2++) {
										alternateBoard.getBoard()[ri2][ci2] = new Piece(board[ri2][ci2].getColorSet()[board[ri2][ci2].getColor()], board[ri2][ci2].getTypeSet()[board[ri2][ci2].getType()], ri2, ci2);
									}
								}
								alternateBoard.setTurn(turn);
								alternateBoard.setDoubleR(doubleR);
								alternateBoard.setDoubleC(doubleC);
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	public boolean checkInsufficientMaterial() {
		int wQ = countPieces(1, 5);
		int bQ = countPieces(2, 5);
		int wR = countPieces(1, 4);
		int bR = countPieces(2, 4);
		int wB = countPieces(1, 3);
		int bB = countPieces(2, 3);
		int wK = countPieces(1, 2); 
		int bK = countPieces(2, 2);
		int wP = countPieces(1, 1); 
		int bP = countPieces(2, 1);
		if(wQ + bQ + wR + bR + wP + bP == 0) {
			if(wB + wK < 2 && bB + bK < 2) {
				return true;
			}
		}
		return false;
	}
	
	public boolean check3FoldRep() {
		Piece[][] array = new Piece[8][8];
		int count = 0;
		for(int i = 0; i < positions.size(); i++) {
			for(int r = 0; r < 8; r++) {
				for(int c = 0; c < 8; c++) {
					array[r][c] = positions.get(i)[r][c];
				}
			}
			for(int i2 = 0; i2 < positions.size(); i2++){
				if(equals(array, positions.get(i2))) {
					count++;
				}
			}
			if(count > 2) {
				return true;
			}
			count = 0;
		}
		return false;
	}
	
	public boolean check50MoveRule() {
		if(fiftyCount > 99) {
			return true;
		}
		return false;
	}
	
	public boolean equals(Piece[][] a, Piece[][] b) {
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				if(a[r][c].getColor() != b[r][c].getColor() || a[r][c].getType() != b[r][c].getType()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public int countPieces(int color, int type) {
		int count = 0;
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				if(board[r][c].getColor() == color && board[r][c].getType() == type) {
					count++;
				}
			}
		}
		return count;
	}
	

	public void adj50Count(int newR, int newC, boolean findingInfluence) {
		if(!findingInfluence) {
			if(board[newR][newC].getType() == 0) {
				this.fiftyCount++;
			} else {
				this.fiftyCount = 0;
			}
		}
	}
	
	
	
	public boolean movePiece(ChessBoard boardObj, Piece piece, int turn, int r, int c, int newR, int newC) {
		System.out.println("turn: " + turn);
		if(doubleR != 0 && doubleC != 0) {
			if(board[doubleR][doubleC].getMovedDouble()) {
				if(turn - board[doubleR][doubleC].getMovedDoubleTurn() == 2) {
					board[doubleR][doubleC].setMovedDouble(false);
					System.out.println("movedDouble is now false");
				}
			}
		}
		if(board[r][c].getColor() == 1 && turn % 2 == 0) {
			System.out.println("it's not white's turn");
			return false;
		}
		if(board[r][c].getColor() == 2 && turn % 2 != 0) {
			System.out.println("it's not black's turn");
			return false;
		}
		//check if capturing same color
		if(board[r][c].getColor() == board[newR][newC].getColor()) {
			System.out.println("can't capture same color");
			return false;
		}
		///////////////////////
		if(!board[r][c].whiteMoveIsLegal(boardObj, board, turn, newR, newC, false)) {
			System.out.println("the move isn't legal");
			return false;
		} 
		System.out.println("the move is legal");
		Piece pieceRemoved = board[newR][newC]; 
		board[newR][newC] = piece;
		piece.setR(newR);
		piece.setC(newC);
		board[r][c] = new Piece("empty", "empty", newR, newC);
		if(!piece.getHasMoved()) {
			piece.setHasMoved(true);
		}
		
		boardObj.updateInfluence(boardObj);
		if(turn % 2 != 0 && inCheck(1)) {
			System.out.println("white is still in check");
			board[r][c] = piece;
			piece.setR(r);
			piece.setC(c);
			board[newR][newC] = pieceRemoved;
			return false;
		}
		if(turn % 2 == 0 && inCheck(2)) {
			System.out.println("black is still in check");
			board[r][c] = piece;
			piece.setR(r);
			piece.setC(c);
			board[newR][newC] = pieceRemoved;
			return false;
		}
	
		
		System.out.println("piece moved!!!");
		System.out.println("Piece r: " + piece.getR() + " Piece c: " + piece.getC());
		addPosition();
		this.turn++;
		System.out.println();
		return true;

	}
	
	public void paintDots(Graphics myBuffer, int MARGIN_WIDTH, int SQUARE_SIZE) {
		//Color color;
		for(int i = 0; i < possibleMoves.size(); i++) {
			if(squareColor[possibleMoves.get(i).getR()][possibleMoves.get(i).getC()].getColor().equals(white)) {
				myBuffer.setColor(whiteDot);
			}
			if(squareColor[possibleMoves.get(i).getR()][possibleMoves.get(i).getC()].getColor().equals(whiteH)) {
				myBuffer.setColor(whiteHDot);
			}
			if(squareColor[possibleMoves.get(i).getR()][possibleMoves.get(i).getC()].getColor().equals(green)) {
				myBuffer.setColor(greenDot);
			}
			if(squareColor[possibleMoves.get(i).getR()][possibleMoves.get(i).getC()].getColor().equals(greenH)) {
				myBuffer.setColor(greenHDot);
			}
			if(board[possibleMoves.get(i).getR()][possibleMoves.get(i).getC()].getType() == 0) {
				myBuffer.fillOval(MARGIN_WIDTH + SQUARE_SIZE * possibleMoves.get(i).getC() + SQUARE_SIZE / 3, MARGIN_WIDTH + SQUARE_SIZE * possibleMoves.get(i).getR() + SQUARE_SIZE / 3, SQUARE_SIZE / 3, SQUARE_SIZE / 3);
			} else {
				myBuffer.fillOval(MARGIN_WIDTH + SQUARE_SIZE * possibleMoves.get(i).getC(), MARGIN_WIDTH + SQUARE_SIZE * possibleMoves.get(i).getR(), SQUARE_SIZE, SQUARE_SIZE);
				myBuffer.setColor(squareColor[possibleMoves.get(i).getR()][possibleMoves.get(i).getC()].getColor());
				myBuffer.fillOval(MARGIN_WIDTH + SQUARE_SIZE * possibleMoves.get(i).getC() + SQUARE_SIZE / 10, MARGIN_WIDTH + SQUARE_SIZE * possibleMoves.get(i).getR() + SQUARE_SIZE / 10, SQUARE_SIZE - 2 * (SQUARE_SIZE / 10), SQUARE_SIZE - 2 * (SQUARE_SIZE / 10));

			}
			
		}
	}
	
	public void paintBoard(Graphics myBuffer, int MARGIN_WIDTH, int SQUARE_SIZE) {
		for(int r = 0; r < 8; r++) { //draws board squares
			for(int c = 0; c < 8; c++) {
				myBuffer.setColor(squareColor[r][c].getColor());
				myBuffer.fillRect(squareColor[r][c].getC() * SQUARE_SIZE + MARGIN_WIDTH, squareColor[r][c].getR() * SQUARE_SIZE + MARGIN_WIDTH, SQUARE_SIZE, SQUARE_SIZE);
			}
		}
	}
	
	public void paintCoordinates(Graphics myBuffer, int MARGIN_WIDTH, int SQUARE_SIZE) {
		boolean greenSquare = true;
		String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};
		
		int fontSize = 18;
		myBuffer.setFont(new Font("Monospaced", Font.BOLD, fontSize));
		myBuffer.setColor(white);
		int i2 = 0;
		for(int i = MARGIN_WIDTH + SQUARE_SIZE - fontSize + 3; i < MARGIN_WIDTH + 8 * SQUARE_SIZE; i+= SQUARE_SIZE) {
			if(greenSquare) {
				myBuffer.setColor(white);
			} else {
				myBuffer.setColor(green);
			}
			myBuffer.drawString(letters[i2], i, MARGIN_WIDTH + SQUARE_SIZE * 8 - 5);
			i2++;
			if(greenSquare) {
				greenSquare = false;
			} else {
				greenSquare = true;
			}
			
		}
		int i3 = 8;
		greenSquare = false;
		for(int i = MARGIN_WIDTH + fontSize + 3; i < MARGIN_WIDTH + 8 * SQUARE_SIZE; i+= SQUARE_SIZE) {
			if(greenSquare) {
				myBuffer.setColor(white);
			} else {
				myBuffer.setColor(green);
			}
			myBuffer.drawString(Integer.toString(i3), MARGIN_WIDTH + 5, i);
			i3--;
			if(greenSquare) {
				greenSquare = false;
			} else {
				greenSquare = true;
			}
			
		}
	}
	
	public void paintPieces(Graphics myBuffer, int marginWidth, int squareSize) {
		//System.out.println("in board's paint method");
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				//System.out.println("r: " + r + "  c: " + c);
				if(board[r][c].getType() != 0) {
					myBuffer.drawImage(board[r][c].getImageIcon().getImage(), marginWidth + c*squareSize, marginWidth + r*squareSize, squareSize, squareSize, null);
				}
			}
		}
	}

}
