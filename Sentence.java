/**
 * @author [your name here]
 *
 * This class represents a single sentence from the input file.
 * 
 */

public class Sentence {
	
	/**
	 * The sentiment score for the sentence. Should be in the range [-2, 2]
	 */
	private int score;
	
	/**
	 * The text contained in the sentence. 
	 */
	private String text;
	
	public Sentence(int score, String text) {
		this.score = score;
		this.text = text;
	}
	
	public int getScore() {
		return score;
	}
	
	public String getText() {
		return text;
	}

	
}
