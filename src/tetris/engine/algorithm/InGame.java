package tetris.engine.algorithm;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import tetris.engine.gui.screen.GameScr;

public class InGame {
	
	private GameScr gameScr;
	
	private Board board;	
	private Shape currentShape, nextShape;
	
	private ArrayList<Block> nextShapeBlocks = new ArrayList<Block>();
	
	private final int blockSize = 30;
	
	private boolean gameOver = false;
	
	private int level;
	
	private long count;
	
	private int score;
	
	public InGame(GameScr gameScr, int level) {
		
		this.gameScr = gameScr;
		this.level = level;
		
		//TODO
		count = 0;
		score = 0;
		board = new Board();
		
		setNextShape();
		setCurrentShape();
	}
	
	public void update() {
		//TODO
		currentShape.update();
		if(currentShape.isCollision()) {
			board.setShapeToBoard(currentShape);
			score += board.checkLine() * (8 + level * 2);		
			checkGameOver();
			if(!gameOver) setCurrentShape();
		}
		keyUpdate();
	}
	
	public void paint(Graphics g) {
		//TODO
		board.paint(g);
		
		currentShape.paint(g);
		
		if(nextShape.getColor() == 7) {
			for (Block block : nextShapeBlocks) {
				block.setColor((int)(Math.random()*7));
			}
		}		
		for (Block block : nextShapeBlocks) {
			block.paint(g);
		}
				
	}
	
	private void keyUpdate() {
		//TODO
		if(gameScr.getGame().getInput().isKeyDown(KeyEvent.VK_UP)) {
			currentShape.rotateShape();
			//System.out.println("roll");
		}
		if(gameScr.getGame().getInput().isKeyDown(KeyEvent.VK_DOWN)) {
			currentShape.speedUp();
		}
		if(gameScr.getGame().getInput().isKeyUp(KeyEvent.VK_DOWN)) {
			currentShape.speedDown();
		}
		if(gameScr.getGame().getInput().isKeyHold(KeyEvent.VK_RIGHT)) {
			currentShape.moveRight();
		}
		if(gameScr.getGame().getInput().isKeyHold(KeyEvent.VK_LEFT)) {
			currentShape.moveLeft();
		}
	}
	
	private void checkGameOver() {
		for (Block block : board.getBlocks()) {
			if(block.getY() == 0) {
				gameOver = true;
			}
		}
	}
	
	private void setCurrentShape() {
		currentShape = nextShape;
		//TODO
		currentShape.setLastTime(System.currentTimeMillis());
		setNextShape();
	}
	private void setNextShape() {
		//TODO
		int index = (int)(Math.random()*7);
		switch (index) {
		case 0:
			nextShape = new ShapeO(this, 0);			
			break;
		case 1:
			nextShape = new ShapeT(this, 1);
			break;
		case 2:
			nextShape = new ShapeL(this, 2);
			break;
		case 3:
			nextShape = new ShapeJ(this, 3);
			break;
		case 4:
			nextShape = new ShapeS(this, 4);
			break;
		case 5:
			nextShape = new ShapeZ(this, 5);
			break;
		case 6:
			nextShape = new ShapeI(this, 6);
			break;
		default:
			break;
		}
		count++;
		if(count % 10 == 0) {
			nextShape.setColor(7);
		}	
		nextShapeBlocks = new ArrayList<Block>();
		for (Block block : nextShape.getBlocks()) {
			nextShapeBlocks.add(new Block(block.getColor(), block.getX() - nextShape.getX() + 11, block.getY() - nextShape.getY() + 2));
		}
	}

	public Board getBoard() {
		return board;
	}

	public int getBlockSize() {
		return blockSize;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public int getScore() {
		return score;
	}

	public int getLevel() {
		return level;
	}

}
