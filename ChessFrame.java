import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChessFrame extends JFrame{
	
		ChessFrame(){

		this.add(new ChessPanel());
		this.setTitle("Chess Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
