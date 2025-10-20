import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.text.AttributeSet.ColorAttribute;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.geom.RoundRectangle2D;

public class ChessPanel extends JPanel implements ActionListener{
	static final int SCREEN_WIDTH = 750;
	static final int SCREEN_HEIGHT = 750;
	static final int MARGIN_WIDTH = 75; 
	static final int SQUARE_SIZE = 75;
	static final Color grey = new Color(43, 41, 39);
	static final Color grey2 = new Color(85, 84, 82);
	static final Color green = new Color(122, 146, 89);
	static final Color white = new Color(235, 235, 208);
	static final Color white2 = new Color(255, 255, 255);
	
	private final int DELAY = 25;
	
	private BufferedImage myImage;
	private Graphics myBuffer;
	private Timer timer;
	private Point pointerLocation; 
	//private Point clickedLocation = new Point(0, 0);
	private Point firstClick = new Point(-1, -1);
	private Point secondClick = new Point(-1, -1);
	private Point processedClick = new Point(-1, -1);
	private Point processedClick2 = new Point(-1, -1);
	private Point origin = new Point(-1, -1); //where a piece is coming from (r, c)
	private Point destination = new Point(-1, -1); //where a piece is going to (r, c)
	private ChessBoard board = new ChessBoard();
	private int gameStatus = 0; // 0 = pre, 1 = running, 2 = end
	private int runningStatus = 0; // 0 - moving pieces, 1 - promoting
	private int endType = 0; // 0 = whiteWins, 1 blackWins, 2 drawByStalemate, 3 drawByInsufficientMaterial, 4 drawByThreefoldRep, 5 drawBy50MoveRule
	private boolean showPossibleMoves = false;
	private Timer timer2;
	
	ChessPanel(){
		myImage = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
		myBuffer = myImage.getGraphics();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setFocusable(true);
		handler h = new handler();
		this.addMouseListener(h);
		startGame();
	}
	
	
	public void startGame() {

//		System.out.println("Attempting to make tree");
//		long startTime = System.nanoTime();
//		System.out.println("startTime = " + startTime);
//		Tree tree = new Tree(board);
//		long endTime = System.nanoTime();
//		long timeElapsed = endTime - startTime;
//		
//		System.out.println("CPU Random Move: ");
//		for(int i = 0; i < 10; i++) {
//		Move move = tree.getRanMove(1);
//		System.out.println("(" + move.getR1() + ", " + move.getC1() + ") - (" + move.getR2() + ", " + move.getC2() + ")");
//		}
//		System.out.println("Tree was made successfully");
//		System.out.println("Nodes @ level 0 : " + tree.countNodes(0));
//		System.out.println("Nodes @ level 1: " + tree.countNodes(1));
//		System.out.println("Nodes @ level 2: " + tree.countNodes(2));
//		System.out.println();
//
//		System.out.println("Time elapsed in nano: " + timeElapsed);
//		System.out.println("Time elapsed in milli: " + timeElapsed / 1000000);
//		System.out.println("Time to make tree (s): " + timeElapsed / (1000000000));
//		
		
		//2674, 2808, 2671, 2704, 2691
		
		//optimized1
		//2739, 2705, 2680, 2710, 2710

//		ChessBoard alteration = board;
//		alteration.setTurn(alteration.getTurn() + 20);
//		System.out.println(board.getTurn());
//		System.out.println(alteration.getTurn());
//		ChessBoard alteration = new ChessBoard();
//		alteration.getTurn() = board.getTurn());
//		alteration.setTurn(alteration.getTurn() + 20);
//		System.out.println(alteration.getTurn());
//		System.out.println(board.getTurn());
//		board.updateAllPossibleMoves();
//		int r1;
//		int c1;
//		int r2;
//		int c2;
//		System.out.println("# of possible moves: " + board.getAllPossibleMoves().size());
//		System.out.println("possible moves:");
//		for(int i = 0; i < board.getAllPossibleMoves().size(); i++) {
//			r1 = board.getAllPossibleMoves().get(i).getR1();
//			c1 = board.getAllPossibleMoves().get(i).getC1();
//			r2 = board.getAllPossibleMoves().get(i).getR2();
//			c2 = board.getAllPossibleMoves().get(i).getC2();
//
//			System.out.println("(" + r1 + ", " + c1 + ") - (" + r2 + ", " + c2 + ")");
//		}
		drawBackground();
		board.updateInfluence(board);
		timer = new Timer(DELAY, this);
		timer.start();
	}


	public void paintComponent(Graphics g) {
		

		
		
		super.paintComponent(g);
		board.paintBoard(myBuffer, MARGIN_WIDTH, SQUARE_SIZE);
		if(showPossibleMoves) {
			board.paintDots(myBuffer, MARGIN_WIDTH, SQUARE_SIZE);
		}
		board.paintCoordinates(myBuffer, MARGIN_WIDTH, SQUARE_SIZE);
		board.paintPieces(myBuffer, MARGIN_WIDTH, SQUARE_SIZE);
		if(board.getPromoting()) {
			runningStatus = 1;
			paintPromotionPanel(myBuffer, board.getPromotingColor(), board.getPromotingSquare());
		}
		if(gameStatus == 0) {
			paintStartScreen(myBuffer);
		}
		if(gameStatus == 2) {
			paintEndScreen(myBuffer, endType);
		}
		
		g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
		
		
		
		
		

		
		
		
		//System.out.println("repeating?");
		//g.drawImage(img.getImage(), 100, 100, null);
		}
	
	public void drawBackground() {
		myBuffer.setColor(grey);
		myBuffer.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
	
	public void paintPromotionPanel(Graphics myBuffer, int color, int c) {
		myBuffer.setColor(white2);
		if(color == 1) { //promoting white
			myBuffer.fillRect(MARGIN_WIDTH + SQUARE_SIZE*c, MARGIN_WIDTH, SQUARE_SIZE, SQUARE_SIZE*4);
			myBuffer.drawImage(new Piece("white", "Queen", 0, 0).getImageIcon().getImage(), MARGIN_WIDTH + c*SQUARE_SIZE, MARGIN_WIDTH, SQUARE_SIZE, SQUARE_SIZE, null);
			myBuffer.drawImage(new Piece("white", "Rook", 0, 0).getImageIcon().getImage(), MARGIN_WIDTH + c*SQUARE_SIZE, MARGIN_WIDTH + SQUARE_SIZE * 1, SQUARE_SIZE, SQUARE_SIZE, null);
			myBuffer.drawImage(new Piece("white", "Bishop", 0, 0).getImageIcon().getImage(), MARGIN_WIDTH + c*SQUARE_SIZE, MARGIN_WIDTH + SQUARE_SIZE * 2, SQUARE_SIZE, SQUARE_SIZE, null);
			myBuffer.drawImage(new Piece("white", "Knight", 0, 0).getImageIcon().getImage(), MARGIN_WIDTH + c*SQUARE_SIZE, MARGIN_WIDTH + SQUARE_SIZE * 3, SQUARE_SIZE, SQUARE_SIZE, null);

		}
		if(color == 2) { //promoting white
			myBuffer.fillRect(MARGIN_WIDTH + SQUARE_SIZE*c, SCREEN_WIDTH - MARGIN_WIDTH - SQUARE_SIZE * 4, SQUARE_SIZE, SQUARE_SIZE*4);
			myBuffer.drawImage(new Piece("black", "Queen", 0, 0).getImageIcon().getImage(), MARGIN_WIDTH + c*SQUARE_SIZE, SCREEN_WIDTH - MARGIN_WIDTH - SQUARE_SIZE * 1, SQUARE_SIZE, SQUARE_SIZE, null);
			myBuffer.drawImage(new Piece("black", "Rook", 0, 0).getImageIcon().getImage(), MARGIN_WIDTH + c*SQUARE_SIZE, SCREEN_WIDTH - MARGIN_WIDTH - SQUARE_SIZE * 2, SQUARE_SIZE, SQUARE_SIZE, null);
			myBuffer.drawImage(new Piece("black", "Bishop", 0, 0).getImageIcon().getImage(), MARGIN_WIDTH + c*SQUARE_SIZE, SCREEN_WIDTH - MARGIN_WIDTH - SQUARE_SIZE * 3, SQUARE_SIZE, SQUARE_SIZE, null);
			myBuffer.drawImage(new Piece("black", "Knight", 0, 0).getImageIcon().getImage(), MARGIN_WIDTH + c*SQUARE_SIZE, SCREEN_WIDTH - MARGIN_WIDTH - SQUARE_SIZE * 4, SQUARE_SIZE, SQUARE_SIZE, null);

		}
	}
	
	public void paintStartScreen(Graphics myBuffer) {
		myBuffer.setColor(grey);
		myBuffer.fillRoundRect(MARGIN_WIDTH + SQUARE_SIZE*2 - 8, MARGIN_WIDTH + SQUARE_SIZE*2 - 8, SQUARE_SIZE*4 + 16, SQUARE_SIZE*4 + 16, 10, 10);
		myBuffer.setColor(grey2);
		myBuffer.fillRoundRect(MARGIN_WIDTH + SQUARE_SIZE*2 + 10, MARGIN_WIDTH + SQUARE_SIZE*2 - 8 + 18, SQUARE_SIZE*4 - 20, SQUARE_SIZE*1, 10, 10);
		myBuffer.setColor(white);
		String l1 = "Java Chess";
		String l2 = "single player";
		String l3 = "two player";
		Font font1 = new Font("Monospaced", Font.BOLD, 40);
		Font font2 = new Font("Monospaced", Font.BOLD, 25);
		FontMetrics metrics = getFontMetrics(font1);
		FontMetrics metrics2 = getFontMetrics(font2);
		myBuffer.setFont(font1);
		myBuffer.drawString(l1, SCREEN_WIDTH/2 - metrics.stringWidth(l1)/2, MARGIN_WIDTH + SQUARE_SIZE*2 - 8 + 15 + 15 + font1.getSize());
		myBuffer.setColor(green);
		myBuffer.fillRoundRect(MARGIN_WIDTH + SQUARE_SIZE*2 + 10, MARGIN_WIDTH + SQUARE_SIZE*6 + 8 - 18 - (int)(SQUARE_SIZE*.75) - 18 - (int)(SQUARE_SIZE*.75), SQUARE_SIZE*4 - 20, (int)(SQUARE_SIZE*.75), 10, 10);
		myBuffer.fillRoundRect(MARGIN_WIDTH + SQUARE_SIZE*2 + 10, MARGIN_WIDTH + SQUARE_SIZE*6 + 8 - 18 - (int)(SQUARE_SIZE*.75), SQUARE_SIZE*4 - 20, (int)(SQUARE_SIZE*.75), 10, 10);
		myBuffer.setFont(font2);
		myBuffer.setColor(white);
		myBuffer.drawString(l2, SCREEN_WIDTH/2 - metrics2.stringWidth(l2)/2, MARGIN_WIDTH + SQUARE_SIZE*6 + 8 - 18 - (int)(SQUARE_SIZE*.75) - 18 - (int)(SQUARE_SIZE*.75) + 12 + font2.getSize());
		myBuffer.drawString(l3, SCREEN_WIDTH/2 - metrics2.stringWidth(l3)/2, MARGIN_WIDTH + SQUARE_SIZE*6 + 8 - 18 - (int)(SQUARE_SIZE*.75) + 12 + font2.getSize());


	}
	
	public void paintEndScreen(Graphics myBuffer, int endType){
		String l1 = "";
		String l2 = "";
		Font font1 = new Font("Monospaced", Font.BOLD, 40);
		Font font2 = new Font("Monospaced", Font.BOLD, 25);
		myBuffer.setColor(grey);
		myBuffer.fillRoundRect(MARGIN_WIDTH + SQUARE_SIZE*2 - 8, MARGIN_WIDTH + SQUARE_SIZE*2 - 8, SQUARE_SIZE*4 + 16, SQUARE_SIZE*4 + 16, 10, 10);
		myBuffer.setColor(grey2);
		myBuffer.fillRoundRect(MARGIN_WIDTH + SQUARE_SIZE*2 + 10, MARGIN_WIDTH + SQUARE_SIZE*3, SQUARE_SIZE*4 - 20, SQUARE_SIZE*2, 10, 10);
		myBuffer.setColor(white);
		//myBuffer.setFont(new Font("Monospaced", Font.BOLD, 25));
		FontMetrics metrics = getFontMetrics(font1);
		FontMetrics metrics2 = getFontMetrics(font2);
		if(endType == 0) {
			l1 = "White Won";
			l2 = "by checkmate";
		}
		if(endType == 1) {
			l1 = "Black Won";
			l2 = "by checkmate";
		}
		if(endType == 2) {
			l1 = "Draw";
			l2 = "by stalemate";
		}
		if(endType == 3) {
			l1 = "Draw";
			l2 = "by insufficient material";
		}
		if(endType == 4) {
			l1 = "Draw";
			l2 = "by repetion";
		}
		if(endType == 5) {
			l1 = "Draw";
			l2 = "by 50 move rule";
		}
		myBuffer.setFont(font1);
		myBuffer.drawString(l1, SCREEN_WIDTH/2 - metrics.stringWidth(l1)/2, MARGIN_WIDTH + SQUARE_SIZE*3 + 20 + font1.getSize());
		myBuffer.setFont(font2);
		myBuffer.drawString(l2, SCREEN_WIDTH/2 - metrics2.stringWidth(l2)/2, MARGIN_WIDTH + SQUARE_SIZE*3 + 20 + font2.getSize() + 40 + font2.getSize());


	}
	
	public void aiMove() {
		if(board.getAIOn() && board.getTurn() % 2 == 0) {
			Tree tree = new Tree(board);
			Move move = tree.getRanMove(1);
			board.movePiece(board, board.getPiece(move.getR1(), move.getC1()), board.getTurn(), move.getR1(), move.getC1(), move.getR2(), move.getC2());
		} 
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("repeating?");
		pointerLocation = MouseInfo.getPointerInfo().getLocation();
		repaint();
		aiMove();
		
	}
	
	
	//top left corner of board: x: 467  y: 142
	public class handler implements MouseListener{
		boolean originAttained = false;
		boolean destinationAttained = false;
		boolean second = false;
		boolean onBoard = false;
		int originX;
		int originY;
		Piece pieceMoving;
		int xPoint;
		int yPoint;
		int x;
		int y;
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			x = e.getX();
			y = e.getY();
			//clickedLocationRaw = pointerLocation;
			//System.out.println(clickedLocationRaw.getX());
			//clickedLocation.setLocation(pointerLocation.getX(), pointerLocation.getY());
			System.out.println("MOUSE CLICKED: ( " + pointerLocation.getX() + " , " + pointerLocation.getY() + " )");
			System.out.println("rel x,y: ( " + x + " , " + y + " )");
			if(gameStatus == 0) {
				int buttonX = MARGIN_WIDTH + SQUARE_SIZE*2 + 10;
				int buttonWidth = SQUARE_SIZE*4 - 20;
				int buttonY = MARGIN_WIDTH + SQUARE_SIZE*6 + 8 - 18 - (int)(SQUARE_SIZE*.75) - 18 - (int)(SQUARE_SIZE*.75);
				int buttonY2 =  MARGIN_WIDTH + SQUARE_SIZE*6 + 8 - 18 - (int)(SQUARE_SIZE*.75);
				int buttonHeight = (int)(SQUARE_SIZE*.75);
				if(x > buttonX && x < buttonX + buttonWidth) {
					if(y > buttonY && y < buttonY + buttonHeight) {
						System.out.println("GAME STATUS EQUALS 1");
						gameStatus = 1;
						board.setAIOn(true);
					}
					if(y > buttonY2 && y < buttonY2 + buttonHeight) {
						System.out.println("GAME STATUS EQUALS 1");
						gameStatus = 1;
					}
				}
			}
			if(gameStatus == 1) {
				if(runningStatus == 0) { //moving pieces
					System.out.println("RUNNING STATUS IS 0");
			if(!originAttained) {
				if(x >= MARGIN_WIDTH && x <= MARGIN_WIDTH + 8*SQUARE_SIZE && y > MARGIN_WIDTH && y < MARGIN_WIDTH + 8*SQUARE_SIZE) {
					onBoard = true;
				} else {
					onBoard = false;
				}
				if(onBoard) {
					firstClick.setLocation(x, y);
					processedClick.setLocation(((int)(firstClick.getX()-MARGIN_WIDTH))/SQUARE_SIZE, ((int)(firstClick.getY()-MARGIN_WIDTH))/SQUARE_SIZE);
					if(board.getBoard()[(int)processedClick.getY()][(int)processedClick.getX()].getColor() == 1 && board.getTurn() % 2 != 0 || board.getBoard()[(int)processedClick.getY()][(int)processedClick.getX()].getColor() == 2 && board.getTurn() % 2 == 0){//((int)processedClick.getY(), (int)processedClick.getX())) {
						origin.setLocation(processedClick.getX(), processedClick.getY());
						originAttained = true;
						
						if(board.getBoard()[(int)origin.getY()][(int)origin.getX()].getColor() == 1 && board.getTurn() % 2 != 0) {
							board.highlightSquare((int)origin.getY(), (int)origin.getX());
							showPossibleMoves = true;
							board.updatePossibleMoves((int)origin.getY(), (int)origin.getX());
						}
						if(board.getBoard()[(int)origin.getY()][(int)origin.getX()].getColor() == 2 && board.getTurn() % 2 == 0) {
							board.highlightSquare((int)origin.getY(), (int)origin.getX());
							showPossibleMoves = true;
							board.updatePossibleMoves((int)origin.getY(), (int)origin.getX());
						}
						
						//board.highlightSquare((int)origin.getY(), (int)origin.getX());
						System.out.println("has valid origin");
					}
				}
			} else {
				if(x > MARGIN_WIDTH && x < MARGIN_WIDTH + 8*SQUARE_SIZE && y > MARGIN_WIDTH && y < MARGIN_WIDTH + 8*SQUARE_SIZE) {
					onBoard = true;
				} else {
					onBoard = false;
				}
				if(onBoard) {
					secondClick.setLocation(x, y);
					processedClick2.setLocation(((int)(secondClick.getX()-MARGIN_WIDTH))/SQUARE_SIZE, ((int)(secondClick.getY()-MARGIN_WIDTH))/SQUARE_SIZE);
					if(board.getBoard()[(int)processedClick2.getY()][(int)processedClick2.getX()].getColor() == 1 && board.getTurn() % 2 != 0 || board.getBoard()[(int)processedClick2.getY()][(int)processedClick2.getX()].getColor() == 2 && board.getTurn() % 2 == 0) {
						board.resetSquareColor((int)processedClick.getY(), (int)processedClick.getX());
						origin.setLocation(processedClick2.getX(), processedClick2.getY());
						originAttained = true;
						//showPossibleMoves = true;
						
						if(board.getBoard()[(int)origin.getY()][(int)origin.getX()].getColor() == 1 && board.getTurn() % 2 != 0) {
							board.highlightSquare((int)origin.getY(), (int)origin.getX());
							showPossibleMoves = true;
							board.updatePossibleMoves((int)origin.getY(), (int)origin.getX());
						}
						if(board.getBoard()[(int)origin.getY()][(int)origin.getX()].getColor() == 2 && board.getTurn() % 2 == 0) {
							board.highlightSquare((int)origin.getY(), (int)origin.getX());
							showPossibleMoves = true;
							board.updatePossibleMoves((int)origin.getY(), (int)origin.getX());
						}
					} else {
						destination.setLocation(processedClick2.getX(), processedClick2.getY());
						destinationAttained = true;
						showPossibleMoves = false;
						System.out.println("has valid destination");
					
					}
					processedClick.setLocation((int)processedClick2.getX(), (int)processedClick2.getY());
				}else {
					//originAttained = false;
					System.out.println("destination wasn't valid, originAttained is false");
				}
			}
			if(originAttained && destinationAttained) {
				System.out.println("origin and destination attained");
				originX = (int)origin.getX();
				System.out.println(originX);
				originY = (int)origin.getY(); 
				System.out.println("Moving info:  (" + origin.getX() + ", " + origin.getY() + ") to (" + destination.getX() + ", " + destination.getY() + ")");
				
//				for(int i = 0; i < 8; i++) {
//					for(int i2 = 0; i2 < 8; i2++) {
//						System.out.print(board.getBoard()[i][i2].getType() + " ");
//					}
//					System.out.println();
//				}
				
				if(board.movePiece(board, board.getPiece((int)origin.getY(), (int)origin.getX()), board.getTurn(), (int)origin.getY(), (int)origin.getX(), (int)destination.getY(), (int)destination.getX())) {
					board.resetSquareColor();
					board.highlightSquare((int)origin.getY(), (int)origin.getX());
					board.highlightSquare((int)destination.getY(), (int)destination.getX());
				} else {
					board.resetSquareColor((int)origin.getY(), (int)origin.getX());
				}
				System.out.println("<><><><><>50 Move Rule Count: " + board.get50Count());
				if(board.getTurn() % 2 != 0) {
						if(board.inCheck(1)) {
							if(board.inMate(board, 1)) {
								System.out.println("!!!!!!white is checkmate!!!!!!");
								gameStatus = 2;
								endType = 1;
							}
						} else {
							if(board.inMate(board, 1)) {
								System.out.println("!!!!!!white is stalemate!!!!!!");
								gameStatus = 2;
								endType = 2;
							}
						}
						
				}
				if(board.getTurn() % 2 == 0) {
						if(board.inCheck(2)) {
							if(board.inMate(board, 2)) {
								System.out.println("!!!!!!black is checkmate!!!!!!");
								gameStatus = 2; 
								endType = 0;
							}
						} else {
							if(board.inMate(board, 2)) {
								System.out.println("!!!!!!black in stalemate!!!!!!");
								gameStatus = 2; 
								endType = 2;
							}
							
						}
				}
				if(board.checkInsufficientMaterial()) {
					gameStatus = 2;
					endType = 3;
				}
				if(board.check3FoldRep()) {
					gameStatus = 2;
					endType = 4;
				}
				if(board.check50MoveRule()) {
					gameStatus = 2;
					endType = 5;
				}
				originAttained = false;
				destinationAttained = false;
				
				
				

			}
			}
			if(runningStatus == 1) { //promoting
				if(x > MARGIN_WIDTH && x < MARGIN_WIDTH + 8*SQUARE_SIZE && y > MARGIN_WIDTH && y < MARGIN_WIDTH + 8*SQUARE_SIZE) {
					onBoard = true;
				} else {
					onBoard = false;
				}
				
				if(onBoard) {
					processedClick.setLocation(((int)(x-MARGIN_WIDTH))/SQUARE_SIZE, ((int)(y-MARGIN_WIDTH))/SQUARE_SIZE);
					
					if(board.getPromotingColor() == 1) { //promoting white
						if(processedClick.getX() == board.getPromotingSquare()) {
							if(processedClick.getY() == 0) {
								//board.setSquare(), originY, originX);
								board.getBoard()[0][board.getPromotingSquare()] = new Piece("white", "Queen", 0, board.getPromotingSquare());
							}
							
							if(processedClick.getY() == 1) {
								board.getBoard()[0][board.getPromotingSquare()] = new Piece("white", "Rook", 0, board.getPromotingSquare());
							}
							if(processedClick.getY() == 2) {
								board.getBoard()[0][board.getPromotingSquare()] = new Piece("white", "Bishop", 0, board.getPromotingSquare());
							}
							if(processedClick.getY() == 3) {
								board.getBoard()[0][board.getPromotingSquare()] = new Piece("white", "Knight", 0, board.getPromotingSquare());
							}
							runningStatus = 0;
							board.setPromoting(false);
							board.setPromotingWhite(false);
							board.setPromotingBlack(false);
						}
					}
					if(board.getPromotingColor() == 2) {
						if(processedClick.getX() == board.getPromotingSquare()) {
							if(processedClick.getY() == 7) {
								board.getBoard()[7][board.getPromotingSquare()] = new Piece("black", "Queen", 7, board.getPromotingSquare());
							}
							if(processedClick.getY() == 6) {
								board.getBoard()[7][board.getPromotingSquare()] = new Piece("black", "Rook", 7, board.getPromotingSquare());
							}
							if(processedClick.getY() == 5) {
								board.getBoard()[7][board.getPromotingSquare()] = new Piece("black", "Bishop", 7, board.getPromotingSquare());
							}
							if(processedClick.getY() == 3) {
								board.getBoard()[7][board.getPromotingSquare()] = new Piece("black", "Knight", 7, board.getPromotingSquare());
							}
							runningStatus = 0;
							board.setPromoting(false);
							board.setPromotingWhite(false);
							board.setPromotingBlack(false);
						}
					}
				
				}
			}

		}
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		
		
	}


}
