package twod.snake.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int foodEaten;
	int foodX;
	int foodY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		spawnFood();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		if(running) {
			/*
			for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			*/
			g.setColor(Color.red);
			g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
			
			for(int i=0; i<bodyParts; ++i) {
				if(i==0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}else {
					g.setColor(new Color(45, 180, 0));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.white);
			g.setFont(new Font("Ink Free", Font.BOLD, 35));
			FontMetrics metrics = getFontMetrics(g.getFont()); // center gameover text
			g.drawString("Score: "+foodEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+foodEaten))/2, g.getFont().getSize());
		}else {
			gameOver(g);
		}
	}
	
	public void spawnFood() {
		foodX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		foodY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void move() {
		for(int i=bodyParts; i>0; --i) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
		
	public void checkFood() {
		if((x[0] == foodX) && (y[0] == foodY)) {
			bodyParts++;
			foodEaten++;
			spawnFood();
		}
	}
	
	public void checkCollisions() {
		// Check jika badan menabrak bagian tubuh
		for(int i=bodyParts; i>0; --i) {
			if((x[0]==x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		// Check jika head menabrak border kiri
		if(x[0] < 0) {
			running = false;
		}
		// Check jika head menabrak border kanan
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		// Check jika head menabrak border atas
		if(y[0] < 0) {
			running = false;
		}
		// Check jika head menabrak border bawah
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		// score
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 35));
		FontMetrics metrics1 = getFontMetrics(g.getFont()); // center gameover text
		g.drawString("Score: "+foodEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+foodEaten))/2, SCREEN_HEIGHT/2+50);
		// adit
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free", Font.BOLD, 20));
		FontMetrics metrics2 = getFontMetrics(g.getFont()); // center gameover text
		g.drawString("Adhitia Nugraha", (SCREEN_WIDTH - metrics2.stringWidth("Adhitia Nugraha"))/2, SCREEN_HEIGHT/2+100);
		// game over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics5 = getFontMetrics(g.getFont()); // center gameover text
		g.drawString("Game Over", (SCREEN_WIDTH - metrics5.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkFood();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
}
