package tetris.engine.gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;

import tetris.engine.FileLoader;
import tetris.engine.Game;
import tetris.engine.gui.screen.GameScr;
import tetris.engine.gui.screen.MenuScr;

public class Window {
	
	public static enum Screen {
		Menu,
		Game
	}
	private Screen screen = Screen.Menu;
	
	private JFrame frame;
	private Canvas canvas;
	private BufferStrategy bs;
	private Graphics g;
	
	private boolean isMute = false;
	
	private Clip music;
	
	//private Input input;
	
	private MenuScr menuScr;
	private GameScr gameScr;

	public Window(Game game) {
		canvas = new Canvas();
		Dimension s = new Dimension((int)(game.getWidth() * game.getScale()) , (int)(game.getHeight() * game.getScale()));
		canvas.setPreferredSize(s);
		canvas.setMaximumSize(s);
		canvas.setMinimumSize(s);
		
		frame = new JFrame(game.getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		
		//input = game.getInput();
		
		music = FileLoader.LoadSound("/music.wav");
		music.loop(Clip.LOOP_CONTINUOUSLY);
		
		menuScr = new MenuScr(game);
		gameScr = new GameScr(game);
	}
	
	public void update() {
		//TODO
		if(screen == Screen.Game) {
			gameScr.update();
		} else {
			menuScr.update();
		}
	}
	
	public void paint() {
		if(screen == Screen.Game) {
			gameScr.paint(g);
		} else {
			menuScr.paint(g);
		}
		bs.show();
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public GameScr getGameScr() {
		return gameScr;
	}

	public boolean isMute() {
		return isMute;
	}

	public void setMute(boolean isMute) {
		this.isMute = isMute;
	}

	public Clip getMusic() {
		return music;
	}

}