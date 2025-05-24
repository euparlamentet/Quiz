package johbed4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.Serializable;
import javax.swing.*;

import johbed4.QuestionBank.Question;
import johbed4.QuestionBank.Question.Category;

/**
 * Classen GUI är en klass för att skapa det grafiska användargränssnittet av applikationen.
 * Classen förlänger JFrame, har flera metoder och en inre class.
 * 
 * @author johbed4 - Johannes Bergius
 * @version 1.0
 */

public class GUI extends JFrame implements Serializable {
	
	//
	private QuizLogic logic;
	
	//Konstanter
	private static final int FRAME_WIDTH = 350;
    private static final int FRAME_HEIGHT = 400;
    private static final int TEXTFIELD_SIZE_20 = 20;
    private static final int TEXTFIELD_SIZE_10 = 10;
    private static final String APP_TITLE_STRING = "FredagsQuizen";
    private static final String NAME = "Name: ";
    private static final String ADD_QUESTION = "Add question";
    private static final String CATEGORY = "Category: ";
    private static final String QUESTION_TEXT = "Question: ";
    private static final String ANSWER = "Answer: ";
    private static final String SAVE = "Save";
    private static final String AMOUNT = "Amount";
    private static final String CLOSE_ACCOUNT = "Close account";
    private static final String GET_ACCOUNT = "Get account";
    private static final String DELETE_CUSTOMER = "Delete customer";
    private static final String GET_CUSTOMER_DETAILS = "Get customer details";
    private static final String OK = "OK";
    
    
    
    //Constructor
    public GUI(QuizLogic logic) {
    	this.logic = logic;
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(APP_TITLE_STRING);
        //getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
        setLocation(550, 250);
        createComponents();
    }
    
    /**
	 * Metod för att uppdatera bilden när komponenter lags till.
	 */
	private void refresh() {
		revalidate();
		repaint();
	}
	
	private void createComponents() {
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu gameMenu = new JMenu("Quiz");
		JMenu playerMenu = new JMenu("Player");
		JMenu questionMenu = new JMenu("Question");
		JMenu highScoreMenu = new JMenu("High Score");
		
		JMenuItem loadFile = new JMenuItem("Load Quiz");
		JMenuItem saveFile = new JMenuItem("Save Quiz");
		JMenuItem startGame = new JMenuItem("Start Game");
		JMenuItem addPlayers = new JMenuItem("Add Players");
		JMenuItem getPlayer = new JMenuItem("Get player info");
		JMenuItem getAllPlayers = new JMenuItem("All players info");
		JMenuItem addQuestion = new JMenuItem(ADD_QUESTION);
		JMenuItem getQuestion = new JMenuItem("Get Question details");
		JMenuItem deleteQuestion = new JMenuItem("Close account");
		JMenuItem deposit = new JMenuItem("Deposit");
		JMenuItem withdraw = new JMenuItem("Withdraw");
		JMenuItem transactionList = new JMenuItem("Transaction list");
		JMenuItem exportTransactions = new JMenuItem("Export transactions to file");
		
		gameMenu.add(startGame);
		startGame.addActionListener(e -> startGameGUI());
		gameMenu.add(loadFile);
		loadFile.addActionListener(e -> System.out.println("Not yet"));
		gameMenu.add(saveFile);
		saveFile.addActionListener(e -> System.out.println("Not yet"));
		playerMenu.add(addPlayers);
		addPlayers.addActionListener(e -> addPlayersGUI());
		playerMenu.add(getPlayer);
		getPlayer.addActionListener(e -> System.out.println("Not yet"));
		playerMenu.add(getAllPlayers);
		getAllPlayers.addActionListener(e -> System.out.println("Not yet"));
		questionMenu.add(addQuestion);
		addQuestion.addActionListener(e -> addQuestionGUI());
		questionMenu.add(getQuestion);
		getQuestion.addActionListener(e -> System.out.println("Not yet"));
		questionMenu.add(deleteQuestion);
		deleteQuestion.addActionListener(e -> System.out.println("Not yet"));
		highScoreMenu.add(deposit);
		deposit.addActionListener(e -> System.out.println("Not yet"));
		highScoreMenu.add(withdraw);
		withdraw.addActionListener(e -> System.out.println("Not yet"));
		highScoreMenu.add(transactionList);
		transactionList.addActionListener(e -> System.out.println("Not yet"));
		highScoreMenu.add(exportTransactions);
		exportTransactions.addActionListener(e -> System.out.println("Not yet"));
		menuBar.add(gameMenu);
		menuBar.add(playerMenu);
		menuBar.add(questionMenu);
		menuBar.add(highScoreMenu);
		setJMenuBar(menuBar);

	}
	
	/**
	 * Metod som skapar en panel med en rubrik. Metoden tar emot en textsträng och skriver den i en rubrik.
	 */
	private void title(String labelTitle) {

	    JPanel titlePanel = new JPanel();
	    titlePanel.setLayout(new BorderLayout());
	    titlePanel.setBackground(Color.BLACK);
	    JLabel titleLabel = new JLabel(labelTitle, SwingConstants.CENTER);
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
	    titleLabel.setForeground(Color.WHITE);
	    titlePanel.add(titleLabel, BorderLayout.CENTER);
	    titlePanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
	    titlePanel.setPreferredSize(new Dimension(400, 40));
	    getContentPane().add(titlePanel, BorderLayout.NORTH);
	}
	
	private void addQuestionGUI() {
		title(ADD_QUESTION);
		Panel addQuestionPanel = new Panel(CATEGORY, QUESTION_TEXT, ANSWER);
		add(addQuestionPanel.getPanel());
		addQuestionPanel.save.addActionListener(e -> {
			if(!addQuestionPanel.textField1.getText().trim().isEmpty() && !addQuestionPanel.textField2.getText().trim().isEmpty()) {
				String qtext = addQuestionPanel.textField1.getText();
				String answer = addQuestionPanel.textField2.getText();
				Category selected = (Category) addQuestionPanel.categoryBox.getSelectedItem();
				logic.getQuestionBank().addQuestion(qtext, answer, selected);
			} else {
				//Msg om tomma fält
			}
		});
		refresh();
	}
	
	
	/**
	 * Startar spelet genom att fråga hur många spelare som ska läggas till,
	 * låter användaren mata in namn för varje spelare och lägger till dem i QuizLogic.
	 * Visar meddelanderutor för bekräftelse och fel.
	 */
	private void addPlayersGUI() {
	    String input = JOptionPane.showInputDialog(null, "Hur många spelare vill du lägga till?", "Antal spelare", JOptionPane.QUESTION_MESSAGE);

	    if (input == null) {
	        JOptionPane.showMessageDialog(null, "Inga spelare tillagda.", "Information", JOptionPane.INFORMATION_MESSAGE);
	        return;
	    }

	    int antal;
	    try {
	        antal = Integer.parseInt(input);
	        if (antal <= 0) {
	            JOptionPane.showMessageDialog(null, "Ange ett positivt tal.", "Fel", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Ange ett giltigt heltal.", "Fel", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    for (int i = 1; i <= antal; i++) {
	        String name = null;
	        while (true) {
	            name = JOptionPane.showInputDialog(null, "Ange namn för spelare " + i + ":", "Lägg till spelare", JOptionPane.QUESTION_MESSAGE);
	            if (name == null) {
	                JOptionPane.showMessageDialog(null, "Avbröt inmatning. Avslutar.", "Information", JOptionPane.INFORMATION_MESSAGE);
	                return;
	            }
	            name = name.trim();
	            if (!name.isEmpty()) {
	                break;
	            }
	            JOptionPane.showMessageDialog(null, "Namnet får inte vara tomt.", "Fel", JOptionPane.ERROR_MESSAGE);
	        }

	        Player p = new Player(name);
	        boolean success = logic.addPlayer(p);
	        if (success) {
	            JOptionPane.showMessageDialog(null, "Spelare tillagd: " + name, "Information", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(null, "Kunde inte lägga till spelare: " + name, "Fel", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	    
	    
	}
	
	private void startGameGUI() {
		addPlayersGUI();
		questionGUI();
	}


     
    private void questionGUI() {
    	getContentPane().removeAll();
    	title("Quiz");
    	Player currentPlayer = logic.getCurrentPlayer();
		String playerName = currentPlayer.getName();
		Question q = logic.randomQuestion();
		String category = q.getCategory().toString();
		String question = q.getqText();
		Panel playerPanel = new Panel(playerName, category);
		JLabel questionLabel = new JLabel(question);
		playerPanel.getPanel().add(questionLabel);
		String answer = q.getAnswer();
		Panel buttonPanel = new Panel();
		
		buttonPanel.ok.setText("See answer");
		buttonPanel.ok.addActionListener(e -> {
			JOptionPane.showMessageDialog(null, answer, "Answer", JOptionPane.INFORMATION_MESSAGE);
		});
		buttonPanel.save.setText("Correct");
		buttonPanel.save.addActionListener(e -> {
			currentPlayer.increaseScore();
			currentPlayer.increaseTries();
			if (logic.nextPlayer() != -1) {
				//Spela på
				questionGUI();
			} else {
				//10p, avbryt spelet
				System.out.println("Spelet slutuu");
			}
		});
		
		JButton wrong = new JButton("False");
		wrong.addActionListener(e -> {
			currentPlayer.increaseTries();
			if (logic.nextPlayer() != -1) {
				questionGUI();
			}
			
		});
		buttonPanel.getPanel().add(wrong);
		
		add(playerPanel.getPanel());
		add(buttonPanel.getPanel(), BorderLayout.SOUTH);
		refresh();
		}


	/**
     * Classen Panel är en inre class för att skapa panel som används i applikationen.
     * Classen förlänger JPanel. Den har två olika constuctorer. Vilken som används beror
     * på vilka kompenenter som behövs i bilden.
     */
    public class Panel extends JPanel {
    	private JPanel panel;
        private JLabel label1;
        private JLabel label2;
        private JLabel label3;
        private JTextField textField1;
        private JTextField textField2;
        private JButton ok;
        private JButton save;
        private JTextArea textArea;
        private JComboBox<Integer> categoryBox;
        
        //Konstruktor
        public Panel() {
        	panel = new JPanel();
        	ok = new JButton();
        	save = new JButton();
        	panel.setLayout(new GridLayout());
        	panel.add(ok);
        	panel.add(save);
        }
        //Konstruktor
        public Panel (String labelText1, String labelText2) {
        	panel = new JPanel();
        	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        	label1 = new JLabel(labelText1);
            label1.setPreferredSize(new Dimension(135, 25));
            label2 = new JLabel(labelText2);
            label2.setPreferredSize(new Dimension(70, 25));
            
            panel.add(label1);
            panel.add(label2);
            
        }
        //Konstruktor
        public Panel(String labelText1, String labelText2, String labelText3) {
        	panel = new JPanel();
            label1 = new JLabel(labelText1);
            label1.setPreferredSize(new Dimension(135, 25)); 
            label2 = new JLabel(labelText2);
            label2.setPreferredSize(new Dimension(70, 25)); 
            label3 = new JLabel(labelText3);
            label3.setPreferredSize(new Dimension(70, 25)); 
            textField1 = new JTextField(TEXTFIELD_SIZE_20);
            textField2 = new JTextField(TEXTFIELD_SIZE_20);
            JComboBox<Category> categoryBox = new JComboBox<Category>(Category.values());
            categoryBox.setPreferredSize(new Dimension(160, 25));
            save = new JButton(SAVE);
            
            panel.add(label1);
            panel.add(categoryBox);
            panel.add(label2);
            panel.add(textField1);
            panel.add(label3);
            panel.add(textField2);
            panel.add(save);
            
        }
        
        /**
    	 * Hämtar panelen.
    	 * @return panel
    	 */
        public JPanel getPanel() {
        	return panel;
        } 
    }
        
}