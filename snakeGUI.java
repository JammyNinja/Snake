import javax.swing.*; //JPanel/containers/scrollpane
import java.awt.*; //Dimension/colour/graphics
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

	Color background = Color.BLACK;
	Color foreground = Color.WHITE;
	Color snakeColour = Color.RED;
	Frame f;

	public snakeGUI(snakeGame game){
		this.game = game;
		this.snake = game.snake;

		f = new Frame();
		initialiseGUI(f);
		snake.snakeHi();
	}
	public void initialiseGUI (Frame f){
		//some visual constants
		setOpaque(true);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(f.frameWidth, f.frameHeight) );

		gameWidth = f.frameWidth;
		gameHeight = f.frameHeight;
		gridXNum = 25;
		gridYNum = 25;
		cellWidth = gameWidth / gridXNum;
		cellHeight = gameWidth / gridYNum;
		
		//get wet for input
		addKeyListener(this);
		setFocusable(true);

		f.initialise(this);
	}

	public void paint(Graphics g){
		//g.setColor(foreground);
		//g.setFont(new Font("Monospaced", Font.PLAIN, sqSize/2) ); //for king hell
		paintEnvironment(g);
		paintSnake(g);
	}

	public void paintEnvironment(Graphics g){
		g.setColor(Color.WHITE);
		for(int i = 0; i<=gridXNum; i++){
			g.drawLine(i*cellWidth, 0, i*cellWidth, gameHeight);
		}
		for(int j=0; j<=gridYNum; j++){
			g.drawLine(0,j*cellHeight, gameWidth, j*cellHeight); //x1,y1,x2,y2
		}
	}

	public void paintSnake(Graphics g){
		g.setColor(snakeColour);
		int snakeHeight = 10 * cellHeight;
		for(int i = 10; i<15; i++){
				//g.fillRect(i*cellWidth, snakeHeight, cellWidth, cellHeight);//x,y,width,height
		}
		for (int bodyPart : snake.body){
			g.fillRect(bodyPart*cellWidth, snakeHeight, cellWidth, cellHeight);//x,y,width,height
		}
	}
	// INPUT
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
			case KeyEvent.VK_SPACE:
			//game.test();
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
	static int frameWidth = 500; //windowWidth = 1000; player 1/100 of this, ball 1/50
	static int frameHeight = 500; //windowHeight = 600;  //player is 1/6 of this

	public Frame(){
		setTitle("Snake");
		setSize(frameWidth,frameHeight);
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