import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public class Piece {
	private int r;
	private int c;
	private String[] typeSet = {"empty", "Pawn", "Knight", "Bishop", "Rook", "Queen", "King"};
	private int type;
	private String[] colorSet = {"empty", "white", "black"};
	private int color;
	private int[] worthSet= {0, 1, 3, 3, 5, 9, 1000};
	private int worth;
	private String[] iconNamesWSet = {"empty.png", "whitePawn.png", "whiteKnight.png", "whiteBishop.png", "whiteRook.png", "whiteQueen.png", "whiteKing.png"};
	private String[] iconNamesBset = {"empty.png", "blackPawn.png", "blackKnight.png", "blackBishop.png", "blackRook.png", "blackQueen.png", "blackKing.png"};
	private ImageIcon icon;//= ImageIO.read(Piece.class.getResource("/images/whitePawn.png"));
	private boolean hasMoved = false;
	private boolean movedDouble = false;
	private int movedDoubleTurn = 0;
	
	//private ImageIcon pawnImage = new ImageIcon
	Piece(String color_i, String type_i, int r_i, int c_i){
		this.r = r_i;
		this.c = c_i;
		for(int i = 0; i < colorSet.length; i++){
			if(color_i.equals(colorSet[i])) {
				this.color = i;
			}
		}
		for(int i2 = 0; i2 < typeSet.length; i2++){
			if(type_i.equals(typeSet[i2])) {
				this.type = i2;
				this.worth = worthSet[i2];
			}
		}
		String test = new String("");
		if(type == 0) {
			icon = new ImageIcon("empty.png");
			//System.out.println("icon set as empty!!!");
		}
		else {
			icon = new ImageIcon(colorSet[color] + typeSet[type] + ".png");
			
		}
		// = (colorSet[color] + typeSet[type] + ".png");
		//System.out.println("x: " + x + " y: " + y + " type: " + type + " color: " + color + " fileName: " + (colorSet[color] + typeSet[type] + ".png");

	}
	
	//getters and setters
	public String[] getTypeSet() {
		return typeSet;
	}
	public String[] getColorSet() {
		return colorSet;
	}
	public int getType(){
		return type;
	}
	public void setType(String newType){
		for(int i = 0; i < typeSet.length; i++)
		{
			if(newType.equals(typeSet[i])) {
				this.type = i;
			}
		}
	}
	public int getColor() {
		return color;
	}
	public boolean getHasMoved() {
		return hasMoved;
	}
	public void setHasMoved(boolean newHasMoved) {
		this.hasMoved = newHasMoved;
	}
	public boolean getMovedDouble() {
		return movedDouble;
	}
	public void setMovedDouble(boolean newMovedDouble) {
		this.movedDouble = newMovedDouble;
	}
	public int getMovedDoubleTurn() {
		return movedDoubleTurn;
	}
	public void setR(int newR) {
		this.r = newR;
	}
	public void setC(int newC) {
		this.c = newC;
	}
	public int getR() {
		return r;
	}
	public int getC() {
		return c;
	}
	public int getWorth(){
		return worth;
	}
	
	public ImageIcon getImageIcon() {
		return icon;
	}
	
	public boolean whiteMoveIsLegal(ChessBoard boardObj, Piece[][] board, int turn, int newR, int newC, boolean findingInfluence) {
		//System.out.println("in whiteMoveIsLegal method");
		int countSE = 1;
		int countSW = 1;
		int countNW = 1;
		int countNE = 1;
		int countN = 1;
		int countE = 1;
		int countS = 1;
		int countW = 1;
		if(type == 1) {		//pawn
			if(!findingInfluence) {
//				System.out.println("||| in moveIsLegal method -- checking pawns's move ||||");
//				System.out.println("r = " + r);
//				System.out.println("c = " + c);
//				System.out.println("newR = " + newR);
//				System.out.println("newC = " + newC);



			}
			if(board[r][c].getColor() == 1) { //white
				if(newR == 0 && !findingInfluence) {
					boardObj.promote(1, newC);
					System.out.println("in legalMoveMethod, promoting");

				}
				if(newR == r - 1 && newC == c && board[newR][newC].getType() == 0 && !findingInfluence) { //white moving forward 1
					boardObj.set50Count(0);
					return true;
				}
				if(findingInfluence && newR == r - 1 && newC == c - 1 || findingInfluence && newR == r - 1 && newC == c + 1) {
					//System.out.println("1 : 1.5");
					return true;
				} else if(board[newR][newC].getType() != 0 && newR == r - 1 && newC == c - 1 || board[newR][newC].getType() != 0 && newR == r - 1 && newC == c + 1) { //white capture
					if(!findingInfluence) {
						boardObj.set50Count(0);
					}
					//System.out.println("1 : 3");
					return true;
				}
				if(r == 6) {
					if(newR == r - 2 && newC == c && board[newR][newC].getType() == 0 && board[newR + 1][newC].getType() == 0 && !findingInfluence) { //white moves double on first move
						//System.out.println("1 : 2");
						this.movedDouble = true;
						//System.out.println("movedDouble is now true");
						this.movedDoubleTurn = turn;
						boardObj.setDoubleR(newR);
						boardObj.setDoubleC(newC);
						boardObj.set50Count(0);
						return true;
					}
				}
				if(!findingInfluence && newR == r - 1 && newC == c - 1 && board[r][c - 1].getMovedDouble() || !findingInfluence && newR == r - 1 && newC == c + 1 && board[r][c + 1].getMovedDouble()) { //white enpassent
					//System.out.println("white moved enpassent");
					boardObj.setSquare(new Piece("empty", "empty", newR + 1, newC), newR + 1, newC);
					boardObj.set50Count(0);
					return true;
				}
			}
			if(board[r][c].getColor() == 2) {
				if(newR == 7 && !findingInfluence) {
					System.out.println("in legalMoveMethod, promoting");
					boardObj.promote(2, newC);
				}
				if(newR == r + 1 && newC == c && board[newR][newC].getType() == 0 && !findingInfluence) { //black moves forward one
					boardObj.set50Count(0);
					//System.out.println("1 : 4");
					return true;
				}
				if(findingInfluence && newR == r + 1 && newC == c - 1 || findingInfluence && newR == r + 1 && newC == c + 1) { //black capture
					//System.out.println("1 : 5");
					return true;
				} else if(board[newR][newC].getType() != 0 && newR == r + 1 && newC == c - 1 || board[newR][newC].getType() != 0 && newR == r + 1 && newC == c + 1) { //black capture
					if(!findingInfluence) {
						boardObj.set50Count(0);
					}
					//System.out.println("1 : 5");
					return true;
				}
				if(r == 1) {
					if(newR == r + 2 && newC == c && board[newR][newC].getType() == 0 && board[newR - 1][newC].getType() == 0 && !findingInfluence) { //black double move
						//System.out.println("1 : 6");
						this.movedDouble = true;
						//System.out.println("movedDouble is now true");
						this.movedDoubleTurn = turn;
						boardObj.setDoubleR(newR);
						boardObj.setDoubleC(newC);
						boardObj.set50Count(0);
						return true;
					}
				}
				if(!findingInfluence && newR == r + 1 && newC == c - 1 && board[r][c - 1].getMovedDouble() || !findingInfluence && newR == r + 1 && newC == c + 1 && board[r][c + 1].getMovedDouble()) { //black enpassent
					//System.out.println("black moved enpassent");
					boardObj.setSquare(new Piece("empty", "empty", newR - 1, newC), newR - 1, newC);
					boardObj.set50Count(0);
					return true;
				}
			}
		}
		if(type == 2) {		//knight
			if(!findingInfluence) {
//				System.out.println("||| in moveIsLegal method -- checking knight's move ||||");
//				System.out.println("r = " + r);
//				System.out.println("c = " + c);
//				System.out.println("newR = " + newR);
//				System.out.println("newC = " + newC);



			}
			if(newR == r + 2 && newC == c - 1 || newR == r + 2 && newC == c + 1) {
				//System.out.println("2 : 1");
				boardObj.adj50Count(newR, newC, findingInfluence);
				return true;
			}
			if(newR == r - 2 && newC == c - 1 || newR == r - 2 && newC == c + 1) {
				//System.out.println("2 : 2");
				boardObj.adj50Count(newR, newC, findingInfluence);
				return true;
			}
			if(newC == c + 2 && newR == r + 1 || newC == c + 2 && newR == r - 1) {
				//System.out.println("2 : 3");
				boardObj.adj50Count(newR, newC, findingInfluence);
				return true;
			}
			if(newC == c - 2 && newR == r + 1 || newC == c - 2 && newR == r - 1) {
				//System.out.println("2 : 4");
				boardObj.adj50Count(newR, newC, findingInfluence);
				return true;
			}
			
		}
		if(type == 3) {		//bishop
			countSE = 1;
			countSW = 1;
			countNW = 1;
			countNE = 1;
			for(int i = 1; i < 8; i++) {
				if(newR == r + 1 * i && newC == c + 1 * i) {
					//System.out.println("3 : 1");
					for(int i2 = 1; i2 < 8; i2++) {
						if(r + 1 * i2 < 8 && c + 1 * i2 < 8 && r + 1 * i2 < newR && c + 1 * i2 < newC) {
							if(board[r + 1 * i2][c + 1 * i2].getType() != 0) {
								countSE++;
								//System.out.println("countSE increased");
							}
						}
					}
					if(countSE < 2) {
						boardObj.adj50Count(newR, newC, findingInfluence);
						return true;
					}
				}
				if(newR == r - 1 * i && newC == c + 1 * i) {
					//System.out.println("3 : 2");
					for(int i3 = 1; i3 < 8; i3++) {
						if(r - 1 * i3 > -1 && c + 1 * i3 < 8 && r - 1 * i3 > newR && c + 1 * i3 < newC) {
							if(board[r - 1 * i3][c + 1 * i3].getType() != 0) {
								countNE++;
								//System.out.println("countNE increased");
							}
						}
					}
					if(countNE < 2) {
						boardObj.adj50Count(newR, newC, findingInfluence);
						return true;
					}	
				}
				if(newR == r + 1 * i && newC == c - 1 * i) {
					//System.out.println("3 : 3");
					for(int i4 = 1; i4 < 8; i4++) {
						if(r + 1 * i4 < 8 && c - 1 * i4 > -1 && r + 1 * i4 < newR && c - 1 * i4 > newC) {
							if(board[r + 1 * i4][c - 1 * i4].getType() != 0) {
								countSW++;
								//System.out.println("countSW increased");
							}
						}
					}
					if(countSW < 2) {
						boardObj.adj50Count(newR, newC, findingInfluence);
						return true;
					}
				}
				if(newR == r - 1 * i && newC == c - 1 * i) {
					//System.out.println("3 : 4");
					for(int i5 = 1; i5 < 8; i5++) {
						if(r - 1 * i5 > -1 && c - 1 * i5 > -1 && r - 1 * i5 > newR && c - 1 * i5 > newC) {
							if(board[r - 1 * i5][c - 1 * i5].getType() != 0) {
								countNW++;
								//System.out.println("countNW increased");
							}
						}
					}
					if(countNW < 2) {
						boardObj.adj50Count(newR, newC, findingInfluence);
						return true;
					}
				}
			}		
		}
		if(type == 4) {		//rook
			countN = 1;
			countE = 1;
			countS = 1;
			countW = 1;
			for(int i = 1; i < 8; i++) {
				if(newR == r + 1 * i && newC == c) {
					//System.out.println("4 : 1");
					for(int i2 = 1; i2 < 8; i2++) {
						if(r + 1 * i2 < 8 && r + 1 * i2 < newR) {
							if(board[r + 1 * i2][c].getType() != 0) {
								countS++;
								//System.out.println("countS increased");
							}
						}
					}
					if(countS < 2) {
						boardObj.adj50Count(newR, newC, findingInfluence);
						return true;
					}
				}
				if(newR == r && newC == c + 1 * i) {
					//System.out.println("4 : 2");
					for(int i3 = 1; i3 < 8; i3++) {
						if(c + 1 * i3 < 8 && c + 1 * i3 < newC) {
							if(board[r][c + 1 * i3].getType() != 0) {
								countE++;
								//System.out.println("countE increased");
							}
						}
					}
					if(countE < 2) {
						boardObj.adj50Count(newR, newC, findingInfluence);
						return true;
					}	
				}
				if(newR == r && newC == c - 1 * i) {
					//System.out.println("4 : 3");
					for(int i4 = 1; i4 < 8; i4++) {
						if(c - 1 * i4 > -1 && c - 1 * i4 > newC) {
							if(board[r][c - 1 * i4].getType() != 0) {
								countW++;
								//System.out.println("countW increased");
							}
						}
					}
					if(countW < 2) {
						boardObj.adj50Count(newR, newC, findingInfluence);
						return true;
					}
				}
				if(newR == r - 1 * i && newC == c) {
					//System.out.println("4 : 4");
					for(int i5 = 1; i5 < 8; i5++) {
						if(r - 1 * i5 > -1 && r - 1 * i5 > newR) {
							if(board[r - 1 * i5][c].getType() != 0) {
								countN++;
								//System.out.println("countN increased");
							}
						}
					}
					if(countN < 2) {
						boardObj.adj50Count(newR, newC, findingInfluence);
						return true;
					}
				}
			}	
		}
		if(type == 5) {		//queen
			countN = 1;
			countE = 1;
			countS = 1;
			countW = 1;
			countSE = 1;
			countSW = 1;
			countNW = 1;
			countNE = 1;
			for(int i = 1; i < 8; i++) {
				if(newR == r + 1 * i && newC == c) {
					//System.out.println("5 : 1");
					for(int i2 = 1; i2 < 8; i2++) {
						if(r + 1 * i2 < 8 && r + 1 * i2 < newR) {
							if(board[r + 1 * i2][c].getType() != 0) {
								countS++;
								//System.out.println("countS increased");
							}
						}
					}
					if(countS < 2) {
						boardObj.adj50Count(newR, newC, findingInfluence);
						return true;
					}
				}
				if(newR == r && newC == c + 1 * i) {
					//System.out.println("5 : 2");
					for(int i3 = 1; i3 < 8; i3++) {
						if(c + 1 * i3 < 8 && c + 1 * i3 < newC) {
							if(board[r][c + 1 * i3].getType() != 0) {
								countE++;
								//System.out.println("countE increased");
							}
						}
					}
					if(countE < 2) {
						boardObj.adj50Count(newR, newC, findingInfluence);
						return true;
					}	
				}
				if(newR == r && newC == c - 1 * i) {
					//System.out.println("5 : 3");
					for(int i4 = 1; i4 < 8; i4++) {
						if(c - 1 * i4 > -1 && c - 1 * i4 > newC) {
							if(board[r][c - 1 * i4].getType() != 0) {
								countW++;
								//System.out.println("countW increased");
							}
						}
					}
					if(countW < 2) {
						boardObj.adj50Count(newR, newC, findingInfluence);
						return true;
					}
				}
				if(newR == r - 1 * i && newC == c) {
					//System.out.println("5 : 4");
					for(int i5 = 1; i5 < 8; i5++) {
						if(r - 1 * i5 > -1 && r - 1 * i5 > newR) {
							if(board[r - 1 * i5][c].getType() != 0) {
								countN++;
								//System.out.println("countN increased");
							}
						}
					}
					if(countN < 2) {
						boardObj.adj50Count(newR, newC, findingInfluence);
						return true;
					}
				}
				if(newR == r + 1 * i && newC == c + 1 * i) {
					//System.out.println("5 : 5");
					for(int i6 = 1; i6 < 8; i6++) {
						if(r + 1 * i6 < 8 && c + 1 * i6 < 8 && r + 1 * i6 < newR && c + 1 * i6 < newC) {
							if(board[r + 1 * i6][c + 1 * i6].getType() != 0) {
								countSE++;
								//System.out.println("countSE increased");
							}
						}
					}
					if(countSE < 2) {
						boardObj.adj50Count(newR, newC, findingInfluence);
						return true;
					}
				}
				if(newR == r - 1 * i && newC == c + 1 * i) {
					//System.out.println("5 : 6");
					for(int i7 = 1; i7 < 8; i7++) {
						if(r - 1 * i7 > -1 && c + 1 * i7 < 8 && r - 1 * i7 > newR && c + 1 * i7 < newC) {
							if(board[r - 1 * i7][c + 1 * i7].getType() != 0) {
								countNE++;
								//System.out.println("countNE increased");
							}
						}
					}
					if(countNE < 2) {
						boardObj.adj50Count(newR, newC, findingInfluence);
						return true;
					}	
				}
				if(newR == r + 1 * i && newC == c - 1 * i) {
					//System.out.println("5 : 7");
					for(int i8 = 1; i8 < 8; i8++) {
						if(r + 1 * i8 < 8 && c - 1 * i8 > -1 && r + 1 * i8 < newR && c - 1 * i8 > newC) {
							if(board[r + 1 * i8][c - 1 * i8].getType() != 0) {
								countSW++;
								//System.out.println("countSW increased");
							}
						}
					}
					if(countSW < 2) {
						boardObj.adj50Count(newR, newC, findingInfluence);
						return true;
					}
				}
				if(newR == r - 1 * i && newC == c - 1 * i) {
					//System.out.println("5 : 8");
					for(int i9 = 1; i9 < 8; i9++) {
						if(r - 1 * i9 > -1 && c - 1 * i9 > -1 && r - 1 * i9 > newR && c - 1 * i9 > newC) {
							if(board[r - 1 * i9][c - 1 * i9].getType() != 0) {
								countNW++;
								//System.out.println("countNW increased");
							}
						}
					}
					if(countNW < 2) {
						boardObj.adj50Count(newR, newC, findingInfluence);
						return true;
					}
				}
			}	
		}
		if(type == 6) {		//king
			if(newR == r - 1 && newC == c) { //N
				//System.out.println("6 : 1");
				boardObj.adj50Count(newR, newC, findingInfluence);
				return true;
			}
			if(newR == r - 1 && newC == c + 1) { //NE
				//System.out.println("6 : 2");
				boardObj.adj50Count(newR, newC, findingInfluence);
				return true;
			}
			if(newR == r && newC == c + 1) { //E
				//System.out.println("6 : 3");
				boardObj.adj50Count(newR, newC, findingInfluence);
				return true;
			}
			if(newR == r + 1 && newC == c + 1) { //SE
				boardObj.adj50Count(newR, newC, findingInfluence);
				return true;
			}
			if(newR == r + 1 && newC == c) { //S
				boardObj.adj50Count(newR, newC, findingInfluence);
				return true;
			}
			if(newR == r + 1 && newC == c - 1) { //SW
				boardObj.adj50Count(newR, newC, findingInfluence);
				return true;
			}
			if(newR == r && newC == c - 1) { //W
				boardObj.adj50Count(newR, newC, findingInfluence);
				return true;
			}
			if(newR == r - 1 && newC == c - 1) { //NW
				boardObj.adj50Count(newR, newC, findingInfluence);
				return true;
			}
			if(!findingInfluence) {
			if(board[r][c].getColor() == 1 && !boardObj.inCheck(1)) {
				if(newR == r && newC == c + 2 && !board[r][c].getHasMoved() && board[r][c + 1].getType() == 0 && board[r][c + 2].getType() == 0 && board[r][c + 3].getColor() == 1 && !board[r][c + 3].getHasMoved()) {
					//System.out.println("6 : 9");
					boardObj.movePiece(boardObj, board[r][c + 3], turn, r, c + 3, r, c + 1);
					boardObj.setTurn(boardObj.getTurn() - 1);
					boardObj.set50Count(boardObj.get50Count()+1);
					//System.out.println("turn decreased while castling");
					return true;
				}
				if(newR == r && newC == c - 2 && !board[r][c].getHasMoved() && board[r][c - 1].getType() == 0 && board[r][c - 2].getType() == 0 && board[r][c - 3].getType() == 0 && board[r][c - 4].getColor() == 1 && !board[r][c + 3].getHasMoved()) {
					//System.out.println("6 : 10");
					boardObj.movePiece(boardObj, board[r][c - 4], turn, r, c - 4, r, c - 1);
					boardObj.setTurn(boardObj.getTurn() - 1);
					boardObj.set50Count(boardObj.get50Count()+1);
					return true;
				}
			}
			if(board[r][c].getColor() == 2 && !boardObj.inCheck(2)) {
				if(newR == r && newC == c + 2 && !board[r][c].getHasMoved() && board[r][c + 1].getType() == 0 && board[r][c + 2].getType() == 0 && board[r][c + 3].getColor() == 2 && !board[r][c + 3].getHasMoved()) {
					//System.out.println("6 : 11");
					boardObj.movePiece(boardObj, board[r][c + 3], turn, r, c + 3, r, c + 1);
					boardObj.setTurn(boardObj.getTurn() - 1);
					boardObj.set50Count(boardObj.get50Count()+1);
					return true;
				}
				if(newR == r && newC == c - 2 && !board[r][c].getHasMoved() && board[r][c - 1].getType() == 0 && board[r][c - 2].getType() == 0 && board[r][c - 3].getType() == 0 && board[r][c - 4].getColor() == 2 && !board[r][c + 3].getHasMoved()) {
					//System.out.println("6 : 12");
					boardObj.movePiece(boardObj, board[r][c - 4], turn, r, c - 4, r, c - 1);
					boardObj.setTurn(boardObj.getTurn() - 1);
					boardObj.set50Count(boardObj.get50Count()+1);
					return true;
				}
			}
			}
		}
		return false;
	}
	
	//public ArrayList<Square> get
	
	public void printPieceInfo() {
		System.out.println("r: " + r + " c: " + c + " type: " + type + " color: " + color + " fileName: " + (colorSet[color] + typeSet[type] + ".png"));
	}
	
	public void drawIcon(Graphics myBuffer, int r, int c, int newLength) {
		//BufferedImage img;
		//if(type == 1) {
			//img = loadIcon("whitePawn.png");
			myBuffer.drawImage(icon.getImage(), c, r, newLength, newLength, null);
			System.out.println("in drawIcon");
		//}
	}
	
	
}
