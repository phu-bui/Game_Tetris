package tetris.engine.gui.screen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.sound.sampled.Clip;

import tetris.engine.FileLoader;
import tetris.engine.Game;
import tetris.engine.algorithm.InGame;
import tetris.engine.gui.MyButton;

public class GameScr extends Screen{
	
	private BufferedImage backGround;
	private BufferedImage gameOver;
	private BufferedImage backToMenu;
	private BufferedImage speak;
	private BufferedImage pause;
	private BufferedImage playAgainst;

	private InGame inGame;
	private ArrayList<MyButton> buttons = new ArrayList<MyButton>();
	private MyButton btnBackToMenu;
	private MyButton btnSpeak;
	private MyButton btnPause;
	
	private boolean isPause = false;	
	
	private int level = 1;

	public GameScr(Game game) {
		super(game);
		inGame = new InGame(this, level);
		backGround = FileLoader.loadImage("/backGround.png");
		gameOver = FileLoader.loadImage("/gameover.png");
		backToMenu = FileLoader.loadImage("/backToMenu.png");
		pause = FileLoader.loadImage("/pauseAndPlay.png");
		speak = FileLoader.loadImage("/speaker.png");
		playAgainst = FileLoader.loadImage("/playagainst.png");
		
		btnBackToMenu = new MyButton(game, backToMenu, 355, 520, 40, 40);
		btnSpeak = new MyButton(game, speak.getSubimage(0, 0, 512, 512), 355, 440, 40, 40);
		btnPause = new MyButton(game, pause.getSubimage(0, 0, 512, 512), 355, 360, 40, 40);
		
		buttons.add(btnBackToMenu);
		buttons.add(btnPause);
		buttons.add(btnSpeak);
	}

	@Override
	public void update() {
		if(!inGame.isGameOver() && !isPause) {
			inGame.update();
		}		
		if(buttons != null) {
			for (MyButton button : buttons) {
				button.update();
			}
		}
		if (btnSpeak.isMouseUp()) {
			game.getWindow().setMute(!game.getWindow().isMute());
			//System.out.println(isMute);
			btnSpeak.setImage(speak.getSubimage(512*(game.getWindow().isMute()?1:0), 0, 512, 512));
			if(game.getWindow().isMute()) {
				game.getWindow().getMusic().stop();
			}
			else {
				game.getWindow().getMusic().start();
				game.getWindow().getMusic().loop(Clip.LOOP_CONTINUOUSLY);
			}
		}
		if(btnPause.isMouseUp()) {
			if(!inGame.isGameOver()) {
				isPause = !isPause;
				btnPause.setImage(pause.getSubimage(512*(isPause?1:0), 0, 512, 512));
			} else {
				setNewGame(level);
			}
		}
		if(btnBackToMenu.isMouseUp()) {
			if(!inGame.isGameOver()) {
				isPause = true;
			}
			game.getWindow().setScreen(tetris.engine.gui.Window.Screen.Menu);
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(backGround, 0, 0, game.getWindow().getCanvas().getWidth(), game.getWindow().getCanvas().getHeight(), null);	
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(new Color(0, 0, 0, 100));		
		for(int i = 0; i <= inGame.getBoard().getHeigth(); i++)
		{
			g2d.drawLine(0, i*inGame.getBlockSize(), inGame.getBoard().getWidth()*inGame.getBlockSize(), i*inGame.getBlockSize());
		}
		for(int j = 0; j <= inGame.getBoard().getWidth(); j++)
		{
			g2d.drawLine(j*inGame.getBlockSize(), 0, j*inGame.getBlockSize(), inGame.getBoard().getHeigth()*inGame.getBlockSize());
		}
		//TODO anything paint
		
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		g.setColor(Color.WHITE);
		g.drawString("Level  " + level, game.getWidth() - 125, 200);
		
		g.drawString("Score", game.getWidth() - 125, 250);
		g.drawString(inGame.getScore() + "", game.getWidth() - 125, 280);
		
		//g.drawImage(backToMenu, 325, 300 ,100 ,100 , null);
		inGame.paint(g);
		
		if(isPause) {
			//System.out.println("game pause");
		}
		
		if(inGame.isGameOver()) {
			btnPause.setImage(playAgainst);
		} else {
			btnPause.setImage(pause.getSubimage(512*(isPause?1:0), 0, 512, 512));
		}
		btnSpeak.setImage(speak.getSubimage(512*(game.getWindow().isMute()?1:0), 0, 512, 512));
		if(buttons != null) {
			for (MyButton button : buttons) {
				button.paint(g);
			}
		}
		
		if(inGame.isGameOver()) {
			g.drawImage(gameOver, 20, 150 ,267 ,200 , null);
		}
		
	}
	
	public void setNewGame(int level) {
		this.level = level;
		this.isPause = false;
		inGame = new InGame(this, level);
	}

	public boolean isPause() {
		return isPause;
	}

	public void setPause(boolean isPause) {
		this.isPause = isPause;
	}

}