import java.util.ArrayList;
import java.awt.*;
import javax.swing.*; // timer
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom; //for use for food placement

public class snakeGame implements ActionListener{
/* ToDo
current: 
	food (class)
	snake moving into itself dead
	game edge kill
	snake growth
	points
	game over / restart
	difficulties
*/
	static snakeGame game;
	static snakeGUI gui;
	static snake snake;
	static Point food;
	static Boolean foodAvailable;
	static int foodCount, foodTimeLimit;
	static int points;
	Timer t;

	public static void main(String args[]){
		System.out.println("Snake!");
		game = new snakeGame();
		gui = new snakeGUI(game);
		snake = new snake(gui);
		newGame();

	}
	public static void newGame(){
		snake.reset();
		
		foodAvailable = false;
		foodCount = 0;
		foodTimeLimit = 150; //unhardcode

		points = 0;
		game.t = new Timer(250,game);
		game.t.start();
	}

	//called per timestep
	public void actionPerformed(ActionEvent e) {
		snake.move();
		if(snake.snakeDead()){
			gameOver();
		}

		foodCount++;
		if(foodCount / 25 >= 1){
			newFood();
		}
		snake.print(foodCount);

		snakeEat();
		gui.repaint();
	}

	public void newFood(){
		foodCount = 0;
		foodAvailable = true;
		int foodX = ThreadLocalRandom.current().nextInt(0, gui.gridXNum);
		int foodY = ThreadLocalRandom.current().nextInt(0, gui.gridYNum);

		food = new Point(foodX,foodY);
	}

	public void snakeEat(){
		Point head = snake.getHead();
		//snake.print("got head: " + head);

		if(head.equals(food)){
			snake.print("NOM!");
			//increment points
			points += 50;
			//destroy food
			foodAvailable = false;
			//reset food
			foodCount = 0;
		}
	}

	public void gameOver(){
		game.t.stop();
	}
}

class snake {
	int headX, headY;
	static ArrayList<Point> body; //size(),add(),get(i), remove()
	static int direction; //0 north, 1 east, 2 south, 3 west //for now? is there better?
	snakeGUI gui;

	public snake(snakeGUI gui){
		this.gui = gui;
		reset();

		System.out.println("snake constructed");
	}
	public void snakeHi(){
		System.out.println("snake says hi!");
		System.out.println("snake: " + body);
		System.out.println("snakelength:" + body.size());
	}
	public void move(){	
		//print("moving snake " + body);		//use direction to move head
		
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
			body.set(i, next);
		}
		
		//move head having used it for first body part
		body.set(0,head);

		//check is the game over
		snakeDead();

		//check if hit food


		//print(body + "\n");
	}
	public boolean snakeDead(){
		//if snake head on another part of its body you DIE!
		Point head = body.get(0);
		for(int i = 1; i < body.size(); i++){
			if(body.get(i).equals(head)){
				print("HEAD OVERLAP DEAD!");
				return true;
			}
		}
		//if snake head out of bounds you DIE!
		if(head.x < 0 || head.x >= gui.gridXNum){
			print("out of the horizontal bounds!");
			return true;
		}
		else if(head.y < 0 || head.y >= gui.gridYNum-1){ //wish I could tell you why -1
			print("out of the vertical bounds!");
			return true;
		}
		return false;
	}

	public void reset(){
		body = new ArrayList<Point>();
		body.add(new Point(10,5));
		body.add(new Point(11,5));
		body.add(new Point(12,5));
		body.add(new Point(13,5));
		body.add(new Point(14,5));
		
		direction = 3;
	}
	public Point getHead(){
		return body.get(0);
	}
	public void print(Object o){
		System.out.println(o);
	}
}