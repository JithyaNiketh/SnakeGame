import javax.swing.JFrame;

public class GameFrameS extends JFrame{

	GameFrameS(){
			
		this.add(new GamePanelS());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}
}