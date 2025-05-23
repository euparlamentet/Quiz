/**
 * Klass för person som tävlar i quizen.
 */
package johbed4;

public class Player {
	
	//Instansvariablar
	private String name;
	private int score;
	private int tries;
	private double hitRate;
	//Konstruktor
	public Player(String name) {
		super();
		this.name = name;
		this.score = 0;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public void increaseScore() {
	    this.score++;
	}
	
	public void addPoint() {
	    this.score++;
	}
	public int getTries() {
		return tries;
	}
	public void setTries(int tries) {
		this.tries = tries;
	}
	
	public void increaseTries() {
		this.tries++;
	}
	
	public double getHitRate() {
		return hitRate;
	}
	public void setHitRate(double hitRate) {
		this.hitRate = hitRate;
	}
	
	public String hiScoreToString(int pos) {
	    return "Pos: " + pos + " Name: " + name + "Hit Rate: " + hitRate*100 + "%";
	}
	
}
