package tetris.engine;

import tetris.engine.gui.Window;

public class Game implements Runnable {
	
	private Thread thread;
	
	private Window window;
	private Input input;
	
	private boolean running = false;
	private final double UPDATE_CAP = 1.0 / 60.0;
	
	private int width = 450, height = 600;
	private float scale = 1f;
	private String title = "Tetris";

	public Game() {
		
	}
	
	public void start() {
		window = new Window(this);
		input = new Input(this);
		
		thread = new Thread(this);
		thread.run();
	}
	
	public void stop() {
		
	}
	
	public void run() {
		running = true;
		
		boolean render = false;
		double now = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;
		
		double frameTime = 0;
		int frames = 0;
		int fps = 0;
		
		while(running) {
			render = false;
			
			now = System.nanoTime() / 1000000000.0;
			passedTime = now - lastTime;
			lastTime = now;
			
			unprocessedTime += passedTime;
			frameTime += passedTime;
			
			while(unprocessedTime >= UPDATE_CAP){
				unprocessedTime -= UPDATE_CAP;
				render = true;
				//TODO update game 
				
				window.update();
				//if(input.isMouseMove()) System.out.println(input.getMouseX() + " " + input.getMouseY());
				//if(input.isButtonDown(MouseEvent.BUTTON1, 200, 100, 50, 50)) System.out.println("ok");
				input.update();
				if(frameTime >= 1.0) {
					frameTime = 0;
					fps = frames;
					frames = 0;
					//System.out.println("Fps: " + fps);
				}
			}
			if(render) {
				//TODO render game
				
				window.paint();
				
				frames++;
			}
			else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		dispose();
	}
	
	private void dispose() {
		
	}

	public Window getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float getScale() {
		return scale;
	}

	public String getTitle() {
		return title;
	}

}
