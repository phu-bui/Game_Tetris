package tetris.engine.algorithm;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tetris.engine.FileLoader;

public class Block {
	
	private int color;
	private BufferedImage image;
	private int blockSize = 30;
	
	private int x, y;
	
	public Block() {
		color = 0;
		x = 0;
		y = 0;
		image = FileLoader.loadImage("/tiles.png");
	}	
	public Block(int color) {
		this();
		this.color = color;
	}
	public Block(int color, int x, int y) {
		this(color);
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		
	}
	
	public void paint(Graphics g) {
		g.drawImage(image.getSubimage(color * blockSize, 0, blockSize, blockSize), x * blockSize, y * blockSize, blockSize, blockSize, null);		
	}
	
	public void drop() {
		y++;
	}
	public void moveLeft() {
		x--;
	}
	public void moveRight() {
		x++;
	}
	public void setLocal(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getColor() {
		return color;
	}

}
