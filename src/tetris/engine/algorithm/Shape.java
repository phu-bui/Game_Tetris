package tetris.engine.algorithm;

import java.awt.Graphics;
import java.util.ArrayList;


public class Shape {
	
	protected InGame inGame;
	
	protected ArrayList<Block> blocks = new ArrayList<Block>();
	
	protected int color;
	protected int x, y;
	protected int maxSize;
	
	protected int normal = 600, fast = 50;
	protected int delay;
	
	protected long now, lastTime;
	
	protected boolean collision = false;
	
	public Shape(InGame inGame, int color) {
		this.color = color;
		this.inGame = inGame;	
		for(int i = 0; i < 4; i++) {
			blocks.add(new Block(color));			
		}
		
		normal = 600 - (int)(600 * 0.1 * (inGame.getLevel() - 1));
		
		delay = normal;
		now = System.currentTimeMillis();
		lastTime = now;
	}
	public Shape(InGame inGame, int color, ArrayList<Block> blocks) {
		this(inGame, color);
		this.blocks = blocks;
	}
	
	public void update() {
		now = System.currentTimeMillis();
		for (Block block : blocks) {
			if((block.getY() >= inGame.getBoard().getHeigth() - 1)) {
				if(now - lastTime > delay/2) {
					collision = true;
					break;
				}
			}
			if(color != 7) {
				for (Block block2 : inGame.getBoard().getBlocks()) {
					if(block.getX() == block2.getX() && block.getY() + 1 == block2.getY()) {
						if(now - lastTime > delay/2) {
							collision = true;
							break;
						}
					}
				}
			}			
		}
		if(now - lastTime > delay && !collision) {
			lastTime = now;
			y++;
			for (Block block : blocks) {
				block.setLocal(block.getX(), block.getY() + 1);
			}
		}
		if(color == 7) {
			for (Block block : blocks) {
				block.setColor((int)(Math.random()*7));
			}
		}
	}
	
	public void paint(Graphics g) {
		for (Block block : blocks) {
			block.paint(g);
		}
	}	
	
	public void rotateShape() {
		ArrayList<Block> tmpBlocks = new ArrayList<Block>();
		for (Block block : blocks) {
			tmpBlocks.add(new Block(block.getColor(), block.getX() - x, block.getY() - y));
		}		
		for (Block block : tmpBlocks) {
			block.setLocal(block.getY(), block.getX());			
			block.setLocal(block.getX(), maxSize - block.getY());
			block.setLocal(block.getX() + x, block.getY() + y);
		}
		boolean flag = true;
		int tmp = 0;
		for (Block block : tmpBlocks) {
			int d = 0;
			if(block.getY() >= inGame.getBoard().getHeigth()) {
				flag = false;
				break;
			}
			if (block.getX() < 0) {
				d = 0 - block.getX();
			}				
			if(block.getX() >= inGame.getBoard().getWidth()) {
				d = inGame.getBoard().getWidth() - block.getX() - 1;
			}
			if(Math.abs(d) > Math.abs(tmp)) {
				tmp = d;
			}
		}
		for (Block block : tmpBlocks) {
			block.setLocal(block.getX() + tmp, block.getY());
		}
		x += tmp;
		if(color != 7) {
			for (Block block : tmpBlocks) {
				for (Block block2 : inGame.getBoard().getBlocks()) {
					if(block.getX() == block2.getX() && block.getY() == block2.getY()) {
						flag = false;
						break;
					}
				}
			}
		}		
		if (flag) {
			blocks = tmpBlocks;
		}
	}
	public void moveLeft() {
		moveX(-1);
	}
	public void moveRight() {
		moveX(1);
	}
	private void moveX(int delta) {
		ArrayList<Block> tmpBlocks = new ArrayList<Block>();
		for (Block block : blocks) {
			tmpBlocks.add(new Block(block.getColor(), block.getX(), block.getY()));
		}
		for (Block block : tmpBlocks) {
			block.setLocal(block.getX() + delta, block.getY());
		}
		boolean flag = true;
		for (Block block : tmpBlocks) {
			if (block.getX() < 0 || block.getX() >= inGame.getBoard().getWidth()) {
				flag = false;
				break;
			}
			if(color != 7) {
				for (Block block2 : inGame.getBoard().getBlocks()) {
					if(block.getX() == block2.getX() && block.getY() == block2.getY()) {
						flag = false;
						break;
					}
				}
			}			
		}
		if (flag) {
			blocks = tmpBlocks;
			x+=delta;
		}
	}

	
	public void speedUp() {
		delay = fast;
	}
	
	public void speedDown() {
		delay = normal;
	}

	public int getColor() {
		return color;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isCollision() {
		return collision;
	}

	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}
	public void setColor(int color) {
		this.color = color;
	}

}