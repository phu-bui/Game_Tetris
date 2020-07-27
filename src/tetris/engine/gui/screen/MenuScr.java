package tetris.engine.gui.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.sound.sampled.Clip;

import tetris.engine.FileLoader;
import tetris.engine.Game;
import tetris.engine.gui.MyButton;

public class MenuScr extends Screen{
	
	private BufferedImage backGround;
	private BufferedImage speak;
	private BufferedImage guide;
	private BufferedImage sllv;
	
	private ArrayList<MyButton> buttons = new ArrayList<MyButton>();
	private MyButton btnNewGame;
	private MyButton btnContinue;
	private MyButton btnHowToPlay;
	private MyButton btnQuit;
	private MyButton btnSpeak;
	private MyButton btnDone;
	private MyButton btnPlay;
	private MyButton btnCancel;
	private MyButton btnLeft;
	private MyButton btnRight;
	
	private int level = 1;
	
	private boolean isGuide = false;
	private boolean isSelectLv = false;
	
	public MenuScr(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		backGround = FileLoader.loadImage("/bgm.png");
		speak = FileLoader.loadImage("/speaker.png");
		guide = FileLoader.loadImage("/guide.png");
		sllv = FileLoader.loadImage("/selectlevel.png");
		
		
		btnContinue = new MyButton(game, FileLoader.loadImage("/continue.png"), 240, 300, 180, 53);
		btnNewGame = new MyButton(game, FileLoader.loadImage("/newgame.png"), 240, 370, 180, 53);
		btnHowToPlay = new MyButton(game, FileLoader.loadImage("/howtoplay.png"), 240, 440, 180, 53);
		btnQuit = new MyButton(game, FileLoader.loadImage("/quit.png"), 240, 510, 180, 53);
		btnSpeak = new MyButton(game, speak.getSubimage(0, 0, 512, 512), 350, 194, 40, 40);
		btnDone = new MyButton(game, FileLoader.loadImage("/done.png"), 161, 355, 128, 53);
		btnPlay = new MyButton(game, FileLoader.loadImage("/play.png"), 85, 355, 128, 53);
		btnCancel = new MyButton(game, FileLoader.loadImage("/cancel.png"), 235, 355, 128, 53);
		btnLeft = new MyButton(game, FileLoader.loadImage("/left.png"), 100, 240, 50, 50);
		btnRight = new MyButton(game, FileLoader.loadImage("/right.png"), 300, 240, 50, 50);
		
		buttons.add(btnContinue);
		buttons.add(btnNewGame);
		buttons.add(btnHowToPlay);
		buttons.add(btnQuit);
		buttons.add(btnSpeak);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (isSelectLv) {
			if(btnPlay.isMouseUp()) {
				game.getWindow().getGameScr().setNewGame(level);
				game.getWindow().setScreen(tetris.engine.gui.Window.Screen.Game);
				isSelectLv = false;
			}
			if(btnCancel.isMouseUp()) {
				isSelectLv = false;
			}
			if(btnLeft.isMouseUp()&&level>1) {
				level--;
			}
			if(btnRight.isMouseUp()&&level<9) {
				level++;
			}
		} else if (isGuide) {
			btnDone.update();
			if(btnDone.isMouseUp()) {
				isGuide = false;
			}
		} else {
			if(buttons != null) {
				for (MyButton button : buttons) {
					if (button == btnContinue) {
						if (game.getWindow().getGameScr().isPause()) {
							button.update();
						}
					} else {
						button.update();
					}				
				}
			}
			if(btnQuit.isMouseUp()) {
				System.exit(0);
			}
			if(btnHowToPlay.isMouseUp()) {
				isGuide = true;
			}
			if (btnNewGame.isMouseUp()) {
				isSelectLv = true;
			}
			if (btnContinue.isMouseUp()&&game.getWindow().getGameScr().isPause()) {
				game.getWindow().getGameScr().setPause(false);
				game.getWindow().setScreen(tetris.engine.gui.Window.Screen.Game);
			}
			if (btnSpeak.isMouseUp()) {
				game.getWindow().setMute(!game.getWindow().isMute());
				btnSpeak.setImage(speak.getSubimage(512*(game.getWindow().isMute()?1:0), 0, 512, 512));
				if(game.getWindow().isMute()) {
					game.getWindow().getMusic().stop();
				}
				else {
					game.getWindow().getMusic().start();
					game.getWindow().getMusic().loop(Clip.LOOP_CONTINUOUSLY);
				}
			}			
		} 
		
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(backGround, 0, 0, game.getWindow().getCanvas().getWidth(), game.getWindow().getCanvas().getHeight(), null);	
		
		if (isSelectLv) {
			g.drawImage(sllv, 60, 150, 330, 236, null);
			btnPlay.paint(g);
			btnCancel.paint(g);
			btnLeft.paint(g);
			btnRight.paint(g);
			g.setColor(Color.white);
			g.setFont(new Font("Comic Sans MS", Font.BOLD, 55));
			g.drawString("" + level, 210, 285);
		} else if (isGuide) {
			g.drawImage(guide, 60, 150, 330, 236, null);
			btnDone.paint(g);
		} else {
			btnSpeak.setImage(speak.getSubimage(512*(game.getWindow().isMute()?1:0), 0, 512, 512));
			if(buttons != null) {
				for (MyButton button : buttons) {
					if (button == btnContinue) {
						if (game.getWindow().getGameScr().isPause()) {
							button.paint(g);
						}
					} else {
						button.paint(g);
					}
				}
			}
		}
	}

}
