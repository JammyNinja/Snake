import java.util.ArrayList;
import java.awt.*;
import javax.swing.*; // timer
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class snakeGame implements ActionListener{

	static snakeGame game;
	static snakeGUI gui;
	static snake snake;

	static Timer t;

	public static void main(String args[]){
		System.out.println("Snake!");
		game = new snakeGame();
		snake = new snake();
		gui = new snakeGUI(game);
		t = new Timer(50,game);
		startGame();

	}
	public static void startGame(){
		t.start();
	}

	//called per timestep
	public void actionPerformed(ActionEvent e) {
		gui.repaint();
	}
}

class snake {
	int headX, headY;
	ArrayList<Point> body; //size(),add(),get(i), remove()

	public snake(){
		System.out.println("snake constructed");
		body = new ArrayList<Point>();
		body.add(new Point(10,5));
		body.add(new Point(11,5));
		body.add(new Point(12,5));

	}
	public void snakeHi(){
		System.out.println("snake says hi!");

		System.out.println(body);
		System.out.println(body.size());
		print(body.get(1));


	}
	public void moveUp(){
		for (int i = 0; i<body.size(); i++){
			Point p = body.get(i);
			p.y++;
			body.set(i, p);
			System.out.println(body);

		}
	}

	public void print(Object o){
		System.out.println(o);
	}
}