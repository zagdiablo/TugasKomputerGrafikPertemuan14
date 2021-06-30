package twod.snake.game;

import javax.swing.JFrame;

public class GameFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	GameFrame(String title){
		this.add(new GamePanel());
		this.setTitle(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}
}
