import java.util.ArrayList;
import java.awt.*;
import javax.swing.*; // timer
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom; //for use for food placement

public class snakeGame implements ActionListener{
/* ToDo GIT THIS MOTHERFUCKER
current: 
	food spawns within snake body... ouch
	points
	food (class)
	game edge kill
	snake growth
	game over / restart
	randomise start direction - or start with direction press
	improve gui, remove grid, add border for points
	make the grid smaller (perhaps also function of diffiuclty)?
	food time adapt for snakelength
	potentially stop snake from going back in on itself
bugs:
	main class food check doesnt work
	things are printing twice?
developments:
	multiple foods
	special foods
	difficulties
tidying:
	print function
*/
	static snakeGame game;
	static snakeGUI gui;
	static snake snake;
	static int score;
	static food food;
	static boolean paused = false;
	Timer t;

	public static void main(String args[]){
		System.out.println("Snake!");
		game = new snakeGame();
		gui = new snakeGUI(game);
		food = new food(game,gui);
		snake = new snake(gui,food);

		newGame();
	}
	//semi redundant, organise with reset...
	public static void newGame(){
		game.t = new Timer(250,game); //difficulty == timer...
		resetGame();
		paused = false;
	}

	public static void resetGame(){
		snake.reset();
		food.reset();
		score = 0;
		game.t.start();
	}

	//called per timestep
	public void actionPerformed(ActionEvent e) {
		if(!paused){
			//increment food time count
			food.countUp();

			//are we alive
			if(snake.snakeDead()){
				gameOver();
			}
			else {
				//move snake
				snake.move();

				gui.repaint();
			}
		}
	}
	public void pauseGame(){
		paused = !paused;
		gui.repaint();
	}
	public void addPoints(int points){
		score += points;
		System.out.println("score: " + score);
	}

	public void gameOver(){
		System.out.println("Game Over! Score: " + score);
		game.t.stop();
	}
}

class food {
	Point location;
	boolean available;
	int count, countLimit; //used for timing food
	int xLim,yLim;
	snakeGame game;

	public food(snakeGame game, snakeGUI gui){
		this.game = game;
		xLim = gui.gridXNum;
		yLim = gui.gridYNum-1; //must it be -1?
		countLimit = ((xLim + yLim) / 2);//25; //turn into a factor of gui/difficulty
		System.out.println("food count limit " + countLimit);
		count = countLimit - 5; //makes food appear sooner, when i sort new/resetgame
		//reset();
	}
	//used when food is eaten 
	public void reset(){
		available = false;
		count = 0;
	}
	public void countUp(){
		//System.out.println("counting up " + count);
		count++;

		if(count % countLimit == 0){
			placeFood();
		}
	}

	public void eaten(){
		reset();
		//increment points
		game.addPoints(500);
	}
	public void placeFood(){
		count = 0;
		int foodX = ThreadLocalRandom.current().nextInt(0, xLim);
		int foodY = ThreadLocalRandom.current().nextInt(0, yLim);

		location = new Point(foodX,foodY); //should it be new point?
		available = true;
	}

	public Point getLoc(){
		return location;
	}
}

class snake {
	int headX, headY;
	static ArrayList<Point> body; //size(),add(),get(i), remove()
	static int direction; //0 north, 1 east, 2 south, 3 west //for now? is there better?
	snakeGUI gui;
	food food;

	public snake(snakeGUI gui, food f){
		this.gui = gui;
		this.food = f;
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
	//update head position
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
		//Point head = snake.getHead();
		//snake.print("got head: " + head);

	//now actually move the snake

		//IF EATEN FOOD, DONT BOTHER WITH THE REST, 'just' add new head!
		boolean foodEaten = false;
		Point tail = null;
		if(head.equals(food.getLoc()) && food.available == true){
			print("snake reckons its eaten the food");
			//body.add(food.getLoc());
			food.eaten();
			foodEaten = true;
			tail = body.get(body.size()-1);
		}

		//body follows itself
		for (int i=body.size()-1; i>0; i--){
			Point next = body.get(i-1);
			body.set(i, next);
		}	
		//move head having used it for first body part
		body.set(0,head);
		if(foodEaten) {
			body.add(tail);
			food.countLimit++;
		}

		//print(body + "\n");
	}
	public boolean snakeDead(){
		//if snake head on another part of its body you DIE!
		Point head = body.get(0);
		for(int i = 1; i < body.size(); i++){
			if(body.get(i).equals(head)){
				print("HEAD OVERLAP DEAD!");
				return true; //dead
			}
		}

		//if snake head out of bounds you DIE!
		if(head.x < 0 || head.x >= gui.gridXNum){
			print("out of the horizontal bounds!");
			return true; //dead
		}
		else if(head.y < 0 || head.y >= gui.gridYNum-1){ //wish I could tell you why -1
			print("out of the vertical bounds!");
			return true; //dead
		}

		return false; //alive
	}

	public void reset(){
		body = new ArrayList<Point>();
		body.add(new Point(6,1));
		body.add(new Point(5,1));
		body.add(new Point(4,1));
		body.add(new Point(3,1));
		body.add(new Point(2,1));
		
		direction = 2;
	}
	public Point getHead(){
		return body.get(0);
	}
	public void print(Object o){
		System.out.println(o);
	}
}