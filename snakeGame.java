import java.util.ArrayList;
import java.awt.*;
import javax.swing.*; // timer
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class snakeGame implements ActionListener{
/* ToDo
current: paint a whole snake

	game edge kill
	food
	points
	game over / restart

*/
	static snakeGame game;
	static snakeGUI gui;
	static snake snake;

	static Timer t;

	public static void main(String args[]){
		System.out.println("Snake!");
		game = new snakeGame();
		snake = new snake();
		gui = new snakeGUI(game);
		t = new Timer(250,game);
		startGame();

	}
	public static void startGame(){
		t.start();
	}

	//called per timestep
	public void actionPerformed(ActionEvent e) {
		snake.move();
		gui.repaint();
	}
}

class snake {
	int headX, headY;
	static ArrayList<Point> body; //size(),add(),get(i), remove()
	int direction; //0 north, 1 east, 2 south, 3 west //for now? is there better?

	public snake(){
		System.out.println("snake constructed");
		body = new ArrayList<Point>();
		body.add(new Point(10,5));
		body.add(new Point(11,5));
		body.add(new Point(12,5));
		//body.add(new Point(13,5));
		//body.add(new Point(14,5));
		
		direction = 3;

	}
	public void snakeHi(){
		System.out.println("snake says hi!");

		System.out.println("snake: " + body);
		System.out.println("snakelength:" + body.size());
	}
	public void move(){	
		print("moving snake " + body);		//use direction to move head
		Point head = new Point();
		head.x = body.get(0).x;
		head.y = body.get(0).y;
		switch(direction){
			case 0: //north
				head.y--;
			break;
			case 1: //east
				head.x++;
			break;
			case 2: //south
				head.y++;
			break;
			case 3: //west
				head.x--;
			break;
		}

		//body follows itself
		for (int i=body.size()-1; i>0; i--){
			Point next = body.get(i-1);
			print("i = " +i+" next: " + next);
			Point test = new Point(next.x, next.y);
			body.set(i, test);
		}
		body.set(0,head);
		//move head having used it for first body part
		print(body + "\n");
	}
	public void moveUp(){
		for (int i = 0; i<body.size(); i++){
			Point p = body.get(i);
			p.y--;
			body.set(i, p);
			System.out.println(body);

		}
	}

	public void print(Object o){
		System.out.println(o);
	}
}