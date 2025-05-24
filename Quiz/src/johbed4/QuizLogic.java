/**
 * Logik-klass för Quiz.
 */

package johbed4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import johbed4.QuestionBank.Question;
import johbed4.QuestionBank.Question.Category;

public class QuizLogic implements Serializable {
	
	private QuestionBank qb = QuestionBank.loadQuestionBank();
	private List<Player> players = new ArrayList<>();
	List<Player> highscoreList = new ArrayList<>();
	private int currentPlayerIndex = 0;
	
	private static final String FILE = "hiscore.txt";
	
    	public void startGame() {
    		loadHiScore();
    		JFrame frame = new GUI(this);
    		frame.setVisible(true);
    		
    	}
    	
    	public void loadHiScore() {
			List<String> hiScoreList = TextFileHandling.read(FILE);
			for (String s : hiScoreList) {
				String[] parts = s.split(",");
				if (parts.length == 3) {
					String pos = parts[0];
			        String name = parts[1];
			        double hitRate = Double.parseDouble(parts[2]);
			        Player p = new Player(name);
			        p.setHitRate(hitRate);
			        highscoreList.add(p);
			        }
				}	
    	}
    	
    	private void saveHighScore() {
    	boolean append = false;	
    	int pos = 1;
    		for (Player p : highscoreList) {
    			String text = (pos + ", " + p.getName() + ", " + p.getHitRate());
    	        pos++;
    	        append = true;
    		}
    			
    	}
    	
    	
    	public int randomIndex(int length) {
			int index = -1;
			Random rand = new Random();
			index = rand.nextInt(length);
			
			return index;    		
    	}
    	
    	public Category randomCategory() {
    		Category[] randomCat = Category.values(); // Få alla värden i enumen
    		
    		return randomCat[randomIndex(randomCat.length)];
    	}
    	
    	public Question randomQuestion() {
			Question q = null;
    		Category cat = randomCategory();
			List<Question> questions = new ArrayList<Question>();
			questions = qb.getQuestionsByCategory(cat);
			q = questions.get(randomIndex(questions.size()));
			
    		return q;	
    	}
    	
    	/**
    	 * Lägger till en spelare i listan över spelare om spelaren är giltig.
    	 *
    	 * @param p Spelaren som ska läggas till. Måste inte vara null och måste ha ett icke-tomt namn.
    	 * @return true om spelaren lades till framgångsrikt, annars false.
    	 */
        public boolean addPlayer(Player p) {
            boolean success = false;
        
            if (p != null && !p.getName().isEmpty()) {
            	players.add(p);
            	success = true;
            } 
         
            return success;
        }
        
        public int nextPlayer() {
        	if (getCurrentPlayer().getScore() >= 10) {
        		highScore();
        		saveHighScore();
        		return -1;
        	}
        	currentPlayerIndex++;
                    	
        	if (currentPlayerIndex >= players.size()) {
                currentPlayerIndex = 0; // eller visa slutresultat
            }
            return currentPlayerIndex;
        }
        
        public Player getCurrentPlayer() {
            return players.get(currentPlayerIndex);
        }
        
        public void sortHighscore() {
            for (int i = 0; i < highscoreList.size() - 1; i++) {
                for (int j = 0; j < highscoreList.size() - i - 1; j++) {
                    if (highscoreList.get(j).getHitRate() < highscoreList.get(j + 1).getHitRate()) {
                        // Byt plats
                        Player temp = highscoreList.get(j);
                        highscoreList.set(j, highscoreList.get(j + 1));
                        highscoreList.set(j + 1, temp);
                    }
                }
            }
        }
        
        public int highScore() {
        	if (players.isEmpty() ) {
        		return -1;
        	}
        	Player p = getCurrentPlayer();
        	p.setHitRate(p.getScore() / (double) p.getTries());
        	double hitRate = 0.0;
        	hitRate = p.getHitRate();
        	
        	if (highscoreList.size() < 10) {
        		highscoreList.add(p);
        		
        	} else {
        		Player lastPlayer = highscoreList.get(highscoreList.size()-1);
        		if (hitRate >= lastPlayer.getHitRate()) {
        			lastPlayer.setName(p.getName());
        			lastPlayer.setHitRate(hitRate);
        			}		
        	}
        	sortHighscore();
        	return 0;
        }
    	
    	public QuestionBank getQuestionBank() {
    	    return qb;
    	}

		public List<Player> getPlayers() {
			return players;
		}

		public void setPlayers(List<Player> players) {
			this.players = players;
		}   	
}
