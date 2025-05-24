/**
 * Class for the quiz questions
 */

package johbed4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionBank implements Serializable {
	
	//konstant för sökväen 
	private static final String FILEPATH = "src/johbed4/files/QuestionBank.ser";
	
    // Listan av frågor som instansvariabel
    private List<Question> questionList;
    
    //Fråge ID
    private int nextId = 100;
    
	// Konstruktor för Questions
    public QuestionBank() {
        questionList = new ArrayList<>();
        
    	
    }
    
    // Lägg till en ny fråga
    public void addQuestion(String qText, String answer, Question.Category category) {

    	Question newQuestion = new Question(nextId, qText, answer, category);
        questionList.add(newQuestion);
        nextId++;
    }
    
    public static QuestionBank loadQuestionBank() {
    	try {
    		FileInputStream loadQuestionBank = new FileInputStream(FILEPATH);
    		ObjectInputStream objIn = new ObjectInputStream(loadQuestionBank);
              		
            QuestionBank qb = (QuestionBank) objIn.readObject(); // först läsa objektet

            objIn.close(); // sen stänga strömmar
            loadQuestionBank.close();

            return qb;
    		
    	} catch (IOException | ClassNotFoundException exc) {
    		System.out.println("Fel Fel, frågebank inte laddad");
    		return new QuestionBank();
    	}
    	
    }
    
    public boolean saveQuestionBank() {
    	boolean success = false;
    	
    	try {
    		FileOutputStream saveQuestionBank = new FileOutputStream(FILEPATH);
    		ObjectOutputStream objOut = new ObjectOutputStream(saveQuestionBank);
    		
    		objOut.writeObject(this);
    		objOut.close();
    		saveQuestionBank.close();
    		success = true;
    		
    	} catch (IOException IOe) {
    		
    	}
    	
    	return success;
    }

    /** 
     * Söker efter anvgivet frågeID och returnerar på vilket index fråge-id finns.
     * Returnerar -1 om frågan inte finns.
     * 
     * @param questionId - Fråge-ID.
     * 
     * @return index    
     */
    public int findQuestionIndex(int questionId) {
        int index = -1;
        for (int i = 0; i < questionList.size(); i++) {
            if (questionId == questionList.get(i).getQuestionId()) { 
                index = i;
                break;
            }
        }
        return index;
    }
    
    public List<Question> getQuestionsByCategory(johbed4.QuestionBank.Question.Category cat) {
        List<Question> result = new ArrayList<>();
        
		for (Question q : questionList) {
            if (q.getCategory() == cat) {
                result.add(q);
            }
        }
        return result;
    }

    
    public List<Question> getQuestionList() {
		return questionList;
	}
    
	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}
	
	public String getQuestionsAsString() {
	    StringBuilder sb = new StringBuilder();  // Använd StringBuilder för effektiv stränghantering
	    
	    for (Question question : questionList) {
	        sb.append("ID: ").append(question.getQuestionId()) // Lägg till frågeID
	          .append("\nFråga: ").append(question.getqText()) // Lägg till frågtext
	          .append("\nSvar: ").append(question.getAnswer()) // Lägg till svar
	          .append("\nKategori: ").append(question.getCategory()) // Lägg till kategori
	          .append("\n\n"); // Lägg till en extra radbrytning mellan varje fråga
	    }
	    
	    return sb.toString(); // Returnera den sammanställda strängen
	}
	
	public String getQuestionAsString(int questionId) {
	    int index = findQuestionIndex(questionId);
	    String q = questionList.get(index).toString();
	    
	    return q; // Returnera den sammanställda strängen
	}
	
	public class Question implements Serializable {
		
		//Instansvariabler
		private int questionId;
		private String qText;
		private String answer;
		private Category category;
		
		//Enum med kategorier
		public enum Category {
			GEOGRAPHY,HISTORY,SCIENCE,SPORTS
		}
	
		// Konstruktor
		public Question(int questionId, String qText, String answer, Category category) {
			this.questionId = questionId;
			this.qText = qText;
			this.answer = answer;
			this.category = category;
		}
				
		@Override
		public String toString() {
			String s = "Question-ID = " + questionId + "\nFråga: " + qText + "\nSvar: " + answer + "\nKategori: "
					+ category + "\n";
			return s;
		}

		public String getqText() {
			return qText;
		}

		public void setqText(String qText) {
			this.qText = qText;
		}

		public String getAnswer() {
			return answer;
		}

		public void setAnswer(String answer) {
			this.answer = answer;
		}

		public Category getCategory() {
			return category;
		}

		public void setCategory(Category category) {
			this.category = category;
		}
		
		public int getQuestionId() {
			return questionId;
		}

		public void setQuestionId(int questionId) {
			this.questionId = questionId;
		}
	}
	

	

    

}
