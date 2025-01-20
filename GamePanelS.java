import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class GamePanelS extends JPanel implements ActionListener{


	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 450;
	static final int UNIT_SIZE = 20;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
	static final int DELAY = 100;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	
    
    int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	
    GamePanelS(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
    
    public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	
    
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
    
    public void draw(Graphics g) {
		
		if(running) {
			
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		
			for(int i = 0; i< bodyParts;i++) {
				if(i == 0) {
					g.setColor(Color.blue);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(Color.blue);
					//g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255))); ---> for changing colors
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}			
			}
			g.setColor(Color.white);
			g.setFont( new Font("Times New Roman",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			
			g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		}
		else {
			gameOver(g);
		}
		
	}
	
    
    public void newApple(){
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
    
    public void move(){
		for(int i = bodyParts;i>0;i--) {
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
	
    
    public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	
    
    public void checkCollisions() {
		//head n body
		for(int i = bodyParts;i>0;i--) {
			if((x[0] == x[i])&& (y[0] == y[i])) {
				running = false;
			}
		}
		//head n left border
		if(x[0] < 0) {
			running = false;
		}
		//head n right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		//head n top border
		if(y[0] < 0) {
			running = false;
		}
		//head n bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	
    
    public void gameOver(Graphics g) {
		
		@SuppressWarnings("unused")
		JFrame f = new JFrame();
		//Score
		g.setColor(Color.white);
		g.setFont( new Font("Times New Roman",Font.BOLD, 30));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		
		g.setColor(Color.white);	
		g.setFont( new Font("Times New Roman",Font.BOLD, 100));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		g.setFont(new Font("Times New Roman", Font.BOLD,50));

		

		//Buttons
		
		JButton restartButton = new JButton("RESTART");
		JButton q = new JButton("QUIT");
    
		int buttonWidth = 150;
		int buttonHeight = 50;
		int buttonX = (SCREEN_WIDTH - buttonWidth) / 2;
		int buttonY = (SCREEN_HEIGHT / 2) + 100; 
	
		restartButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
		q.setBounds(buttonX, buttonY+50, buttonWidth, buttonHeight);
	
		//ActionListeners
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				SnakeGame.main(null);
				Window w = SwingUtilities.getWindowAncestor(GamePanelS.this);
				w.dispose();
				
			}
		});

		q.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){

				Window w1 =SwingUtilities.getWindowAncestor(GamePanelS.this);
				w1.dispose();
			}
		});
	
		this.setLayout(null); 
		this.add(restartButton);
		this.add(q);
	
		this.revalidate();
		this.repaint();
		
	}
	
    
    @Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkApple();
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
