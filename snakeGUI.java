import javax.swing.*; //JPanel/containers/scrollpane
import java.awt.*; //Dimension/colour/graphics + Point
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class snakeGUI extends JPanel
						implements KeyListener
{
	snakeGame game;
	snake snake;
	int gameWidth, gameHeight;
	int gridXNum, gridYNum;
	int cellWidth, cellHeight;
	int	bannerHeight = 20;

	Color background = Color.BLACK;
	Color foreground = Color.WHITE;
	Color snakeColour = Color.RED;
	Frame f;
	Font guiFont = new Font("Comic Sans MS", Font.PLAIN, 25);


	public snakeGUI(snakeGame game){
		this.game = game;
		this.snake = game.snake;

		f = new Frame(bannerHeight);
		//f.setPreferredSize(new Dimension(250, 250 + bannerHeight) );
		initialiseGUI(f);
	}
	public void initialiseGUI (Frame f){
		//some visual constants
		setOpaque(true);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(f.frameWidth, f.gameHeight) );

		gameWidth = f.frameWidth;
		gameHeight = f.gameHeight - bannerHeight;
		gridXNum = 10;
		gridYNum = 10;
		cellWidth = gameWidth / gridXNum;
		cellHeight = gameHeight / gridYNum;
		
		//get wet for input
		addKeyListener(this);
		setFocusable(true);
		
		f.initialise(this);
	}

	public void paint(Graphics g){
		//g.setColor(foreground);
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 15)); //for king hell
		paintEnvironment(g);
		paintSnake(g);
		paintBanner(g);
	}

	public void paintEnvironment(Graphics g){
		g.setColor(Color.WHITE);
		//draw vertical lines
		for(int i = 0; i<=gridXNum+1; i++){
			g.drawLine(i*cellWidth, bannerHeight, i*cellWidth, gameHeight + bannerHeight);
		}
		//draw right-mostvertical line, cant explain why
		g.drawLine(gameWidth-1, bannerHeight, gameWidth-1, gameHeight + bannerHeight);

		//draw horizotnal lines
		for(int j=0; j<=gridYNum; j++){
			g.drawLine(0,j*cellHeight + bannerHeight, gameWidth, j*cellHeight + bannerHeight); //x1,y1,x2,y2
		}
		//the bottom line, compensating for banner
		g.drawLine(0,gameHeight + bannerHeight -3, gameWidth,gameHeight + bannerHeight -3 );

		//paint food
		if(game.food.available){
			g.setColor(Color.GREEN);
			g.fillRect(game.food.location.x*cellWidth, game.food.location.y*cellHeight + bannerHeight, cellWidth, cellHeight);
		}
	}

	public void paintSnake(Graphics g){
		g.setColor(snakeColour);

		for (Point bodyPart : snake.body){
			g.fillRect(bodyPart.x*cellWidth, bodyPart.y * cellHeight + bannerHeight, cellWidth, cellHeight);//x,y,width,height
		}
		//draw head
		g.setColor(Color.CYAN);
		g.fillRect(snake.body.get(0).x*cellWidth, snake.body.get(0).y * cellHeight + bannerHeight, cellWidth, cellHeight);//x,y,width,height

	}
	public void paintBanner(Graphics g){
		g.setColor(Color.WHITE);
		g.drawString("SCORE:", gameWidth - 150,15);
		g.drawString("" + game.score, gameWidth - 50, 15);

		if(game.paused == true){
			g.drawString("PAUSED!", 50,15);
		}
	}
	// INPUT
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
			case KeyEvent.VK_UP: //0
				//System.out.println("UP!");
				if(snake.direction != 2) {
					snake.direction = 0;
				}
			break;
			case KeyEvent.VK_DOWN: //2
				//System.out.println("DOWN!");
				if(snake.direction != 0) {
					snake.direction = 2;
				}
			break;
			case KeyEvent.VK_LEFT: //3
				//System.out.println("LEFT!");
				if(snake.direction != 1) {
					snake.direction = 3;
				}
			break;
			case KeyEvent.VK_RIGHT: //1
				//System.out.println("RIGHT!");
				if(snake.direction != 3) {
					snake.direction = 1;
				}
			break;
			case KeyEvent.VK_SPACE:
				System.out.println("SPACE!");
				game.resetGame();
			break;
			//pause?
			case KeyEvent.VK_P:
				System.out.println("P for pause!");
				game.pauseGame();
			break;
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
			break;
		}
	}

	//UNUSED listener functions
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
}



class Frame extends JFrame {
	static int frameWidth = 300; //windowWidth = 1000; player 1/100 of this, ball 1/50
	static int gameHeight = 300; //windowHeight = 600;  //player is 1/6 of this

	public Frame(int bannerHeight){
		setTitle("Snake");
		setSize(frameWidth,gameHeight + bannerHeight);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void initialise(snakeGUI guiPanel)
	{
		setLayout(new GridLayout(1,1));
		add(guiPanel);
		//pack();
		//guiPanel.northLimit = getInsets().top;
		setLocationRelativeTo(null);
		setVisible(true);
		System.out.println("gui initialised");
	}
}