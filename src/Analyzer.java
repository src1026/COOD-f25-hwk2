package src; /**
 * @author [YOUR NAME HERE!]
 *
 * This class contains the methods used for conducting a simple sentiment analysis.
 */

import java.util.*;

public class Analyzer {

	/**
	 * This method calculates the weighted average for each word in all the Sentences.
	 * This method is case-insensitive and all words should be stored in the Map using
	 * only lowercase letters.
	 * 
	 * @param sentences Set containing Sentence objects with words to score
	 * @return Map of each word to its weighted average; null if input is null
	 */
	public static Map<String, Double> calculateWordScores(Set<Sentence> sentences) {
		/*
		 * Implement this method in Step 2
		 */
		
		if (sentences == null) {
			return null;
		}
		
		if (sentences.isEmpty()) {
			return new HashMap<>();
		}
		
		Map<String, List<Integer>> wordScores = new HashMap<>();
		
		for (Sentence sentence : sentences) {
			if (sentence == null || 
				sentence.getScore() < -2 || 
				sentence.getScore() > 2 || 
				sentence.getText() == null || 
				sentence.getText().isEmpty()) {
				continue;
			}
			
			String[] parts = sentence.getText().split("\\s+");

			for (String part : parts) {
				if (part.isEmpty()) {
					continue;
				}
				
				if (!Character.isLetter(part.charAt(0))) {
					continue;
				}
				String word = part.toLowerCase();
				if (!wordScores.containsKey(word)) {
					wordScores.put(word, new ArrayList<>());
				}
				wordScores.get(word).add(sentence.getScore());
			}
		}
		
		Map<String, Double> result = new HashMap<>();
		for (Map.Entry<String, List<Integer>> entry : wordScores.entrySet()) {
			String word = entry.getKey();
			List<Integer> scores = entry.getValue();
			
			double sum = 0.0;
			for (int score : scores) {
				sum += score;
			}
			double average = sum / scores.size();
			
			result.put(word, average);
		}
		
		return result;
	}
	
	/**
	 * This method determines the sentiment of the input sentence using the average of the
	 * scores of the individual words, as stored in the Map.
	 * This method is case-insensitive and all words in the input sentence should be
	 * converted to lowercase before searching for them in the Map.
	 * 
	 * @param wordScores Map of words to their weighted averages
	 * @param sentence Text for which the method calculates the sentiment
	 * @return Weighted average scores of all words in input sentence; null if either input is null
	 */
	public static double calculateSentenceScore(Map<String, Double> wordScores, String sentence) {
		/*
		 * Implement this method in Step 3
		 */
		return 0;
	}

    /**
     * Use this main() method for testing your calculateWordScores and
     * calculateSentenceScore methods with different inputs.
     * Note that this is _NOT_ the main() method for the whole sentiment analysis program!
     * Just use it for testing this class. It is not considered for grading.
     */
    public static void main(String[] args) {
        System.out.println("Testing Analyzer.calculateWordScores method:");
        
        System.out.println("Basic functionality");
        Set<Sentence> sentences1 = new HashSet<>();
        sentences1.add(new Sentence(2, "I like cake and could eat cake all day ."));
        sentences1.add(new Sentence(1, "I hope the dog does not eat my cake ."));
        
        Map<String, Double> scores1 = calculateWordScores(sentences1);

        
        System.out.println("Invalid sentences");
        Set<Sentence> sentences6 = new HashSet<>();
        sentences6.add(new Sentence(2, "Valid sentence"));
        sentences6.add(new Sentence(5, "Invalid score"));
        sentences6.add(new Sentence(1, ""));
        sentences6.add(new Sentence(1, null));
        
        Map<String, Double> scores6 = calculateWordScores(sentences6);
        if (scores6.size() == 2 && scores6.containsKey("valid") && scores6.containsKey("sentence")) {
            System.out.println("Invalid sentences ignored correctly");
        } else {
            System.out.println("Invalid sentences not handled correctly");
        }
    }

}
