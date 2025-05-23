/**
 * Logik-klass för Quiz.
 */

package johbed4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import johbed4.*;
import johbed4.QuestionBank.Question;
import johbed4.QuestionBank.Question.Category;

public class QuizLogic implements Serializable {
	
	private QuestionBank qb = QuestionBank.loadQuestionBank();
	private List<Player> players = new ArrayList<>();
	List<Player> highscoreList = new ArrayList<>();
	private int currentPlayerIndex = 0;
	
	private static final String FILEPATH = "src/johbed4/files/hiscore.txt";
	
    	public void testMetod() {
    		loadHiScore();
    		
    	}
    	
    	public void loadHiScore() {
    		try {
				FileReader hiscore = new FileReader(FILEPATH);
				BufferedReader br = new BufferedReader(hiscore);
				String line;
				while ((line = br.readLine()) != null) {
					String[] parts = line.split(",");
					if (parts.length == 3) {
			            String pos = parts[0];
			            String name = parts[1];
			            double hitRate = Double.parseDouble(parts[2]);
			            Player p = new Player(name);
			            p.setHitRate(hitRate);
			            highscoreList.add(p);
			        }
				}
					
			} catch (IOException e) {
				System.out.println("Nå fel");
			}
    	}
    	
    	private void saveHighScore() {
    		
    		try {
    			FileWriter hiscore = new FileWriter(FILEPATH);
    			PrintWriter pw = new PrintWriter(hiscore);
    			int pos = 1;
    	        for (Player p : highscoreList) {
    	            pw.println(pos + ", " + p.getName() + ", " + p.getHitRate());
    	            pos++;
    	        }
    			pw.close();
    			
    		} catch (IOException e) {
    			System.out.println("Nå fel spara");
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
        	
        	for (Player p2 : highscoreList) {
        		if (hitRate >= p2.getHitRate()) {
        			highscoreList.get(9).setName(p.getName());
        			highscoreList.get(9).setHitRate(hitRate);
        			break;
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
