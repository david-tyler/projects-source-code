import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener
{
	private int BOARD_WIDTH = 600;
	private int BOARD_HEIGHT = 600;
	private static final int UNIT_SIZE = 10;
	private int GAME_UNITS = (BOARD_WIDTH*BOARD_HEIGHT)/UNIT_SIZE;
	private final int Delay = 50;
	private final int x[] = new int[GAME_UNITS];
	private final int y[] = new int[GAME_UNITS];
	
	private int bparts = 6;
	private int ate;
	private int apple_x;
	private int apple_y;
	private char direction = 'D'; // I want to modify this so intial direction is determined by key pressed.
	private boolean inGame = false;
	
	private Timer timer;
	private Random random;
	private Image apple;
	private Image head;
	private Image body;
	
	
	GamePanel()
	{
		random = new Random();
		this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new KAdapter());
		loadImages();
		start();
	}
	
	public void start()
	{
		CreateApple();
		inGame = true;
		timer = new Timer(Delay,this);
		timer.start();
	}
	
	private void loadImages()
	{
		ImageIcon one = new ImageIcon("dot.png");
		body = one.getImage();
		
		ImageIcon two = new ImageIcon("apple.png");
		apple = two.getImage();
		
		ImageIcon three = new ImageIcon("head.png");
		head = three.getImage();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		doDrawing(g);
	}
	private void doDrawing(Graphics g)
	{
		/*for(int i=0;i<BOARD_HEIGHT/UNIT_SIZE;i++)
		{
			g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,BOARD_HEIGHT);
			g.drawLine(0,i*UNIT_SIZE,BOARD_HEIGHT,i*UNIT_SIZE);
		}*/
		if(inGame==true)
		{
			g.drawImage(apple, apple_x, apple_y, this);
			
			for(int i=0; i<bparts; i++)
			{
				if(i==0)
					g.drawImage(head, x[i], y[i], this);
				else
					g.drawImage(body, x[i], y[i], this);
			}
			
			Toolkit.getDefaultToolkit().sync();
			
			g.setColor(Color.green);
			g.setFont(new Font("Ink Free", Font.BOLD, 30));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("SCORE: " + ate, (BOARD_WIDTH - metrics.stringWidth("SCORE: " + ate ))/2, g.getFont().getSize());
		}
		else
			endGame(g);
	}
	
	public void CreateApple()
	{
		apple_x = random.nextInt((int)(BOARD_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		apple_y = random.nextInt((int)(BOARD_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void move()
	{
		for(int i = bparts; i>0; i--)
		{
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction)
		{
			case 'W': 
				y[0]-=UNIT_SIZE; 
				break;
			case 'S': 
				y[0]+=UNIT_SIZE;
				break;
			case 'A': 
				x[0]-=UNIT_SIZE;
				break;
			case 'D': 
				x[0]+=UNIT_SIZE; 
				break;
		}
	}
	
	
	public void CheckApple()
	{
		if(x[0]==apple_x && y[0] == apple_y)
		{
			bparts++;
			ate++;
			CreateApple();
		}
	}
	
	public void CheckCollision()
	{
		
		for(int i = bparts; i>0; i--) //checks if head collides with body
		{
			if((x[0]==x[i]&&y[0]==y[i]))
			{
				inGame=false;
			}
		}
		if(x[0]<0)
		{
			inGame = false;
		}
		if(x[0]>BOARD_WIDTH)
		{
			inGame =false;
		}
		if(y[0]<0)
		{
			inGame = false;
		}
		if(y[0]>BOARD_HEIGHT)
		{
			inGame =false;
		}
		if(inGame==false)
			timer.stop();
	}
	
	public void endGame(Graphics g)
	{
		//Text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (BOARD_WIDTH - metrics.stringWidth("GAME OVER"))/2, BOARD_HEIGHT/2);
		
		g.setColor(Color.blue);
		g.setFont(new Font("Ink Free", Font.BOLD, 30));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("SCORE: " + ate, (BOARD_WIDTH - metrics2.stringWidth("SCORE: " + ate ))/2, g.getFont().getSize());
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(inGame==true)
		{
			move();
			CheckApple();
			CheckCollision();
		}
		repaint();
	}
	
	private class KAdapter extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_A:
					if(direction!='D')
					{
						direction = 'A';
					}
					break;
				case KeyEvent.VK_D:
					if(direction!='A')
					{
						direction = 'D';
					}
					break;
				case KeyEvent.VK_W:
					if(direction!='S')
					{
						direction = 'W';
					}
					break;
				case KeyEvent.VK_S:
					if(direction!='W')
					{
						direction = 'S';
					}
					break;
				case KeyEvent.VK_UP:
					if(direction!='S')
					{
						direction = 'W';
					}
					break;
				case KeyEvent.VK_LEFT:
					if(direction!='D')
					{
						direction = 'A';
					}
					break;
				case KeyEvent.VK_RIGHT:
					if(direction!='A')
					{
						direction = 'D';
					}
					break;
				case KeyEvent.VK_DOWN:
					if(direction!='W')
					{
						direction = 'S';
					}
					break;
			}
		}
	}
}

