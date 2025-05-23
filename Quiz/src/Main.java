import javax.swing.JFrame;

import johbed4.*;


public class Main {

	public static void main(String[] args) {
		
		QuizLogic logic = new QuizLogic();
		
		Player Player2 = new Player("Si Fack");
		Player Player3 = new Player("Mi Tack");
		Player Player4 = new Player("Qi Smack");
		JFrame frame = new GUI(logic);
		frame.setVisible(true);
		logic.testMetod();
	}

}
