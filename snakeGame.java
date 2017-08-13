import java.util.*;
public class snakeGame {
	static snakeGame game;
	static snakeGUI gui;
	static snake snake;
	public static void main(String args[]){
			System.out.println("Snake!");
			game = new snakeGame();
			snake = new snake();
			gui = new snakeGUI(game);
	}


	
}

class snake {
	int headX, headY;
	ArrayList<Integer> body; //size(),add(),get(i), remove()

	public snake(){
		System.out.println("snake constructed");
		body = new ArrayList<Integer>();
		body.add(10);
		body.add(11);
		body.add(12);
		body.add(13);
		body.add(14);
	}
	public void snakeHi(){
		System.out.println("snake says hi!");

		System.out.println(body);
		System.out.println(body.size());
	}

}