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
		
		if (wordScores == null || sentence == null || sentence.isEmpty()) {
			return 0;
		}
		
		String[] words = sentence.split("\\s+");
		
		double totalScore = 0.0;
		int wordCount = 0;
		
		for (String word : words) {
			if (word.isEmpty()) {
				continue;
			}
			
			// Skip words that don't start with a letter
			if (!Character.isLetter(word.charAt(0))) {
				continue;
			}
			
			// Convert to lowercase
			String lowercaseWord = word.toLowerCase();
			
			// Get score from map, or 0 if word not found
			double score = wordScores.getOrDefault(lowercaseWord, 0.0);
			totalScore += score;
			wordCount++;
		}
		
		// Return average score, or 0 if no valid words found
		if (wordCount == 0) {
			return 0;
		}
		
		return totalScore / wordCount;
	}

    /**
     * Use this main() method for testing your calculateWordScores and
     * calculateSentenceScore methods with different inputs.
     * Note that this is _NOT_ the main() method for the whole sentiment analysis program!
     * Just use it for testing this class. It is not considered for grading.
     */
    public static void main(String[] args) {
        System.out.println("Testing Analyzer.calculateSentenceScore method:");
        
        Map<String, Double> scores1 = new HashMap<>();
        scores1.put("dogs", 1.5);
        scores1.put("are", 0.0);
        scores1.put("cute", 2.0);
        
        double result1 = calculateSentenceScore(scores1, "dogs are cute");
        double expected1 = (1.5 + 0.0 + 2.0) / 3; // 3.5 / 3 = 1.1667
        if (Math.abs(result1 - expected1) < 0.001) {
            System.out.println("Basic functionality: " + result1 + " (expected: " + expected1 + ")");
        } else {
            System.out.println("Basic functionality failed: " + result1 + " (expected: " + expected1 + ")");
        }
        
        Map<String, Double> scores2 = new HashMap<>();
        scores2.put("dogs", 1.5);
        scores2.put("are", 0.0);
        scores2.put("cute", 2.0);
        
        double result2 = calculateSentenceScore(scores2, "dogs are dogs");
        double expected2 = (1.5 + 0.0 + 1.5) / 3; // 3.0 / 3 = 1.0
        if (Math.abs(result2 - expected2) < 0.001) {
            System.out.println("Weighted scoring: " + result2 + " (expected: " + expected2 + ")");
        } else {
            System.out.println("Weighted scoring failed: " + result2 + " (expected: " + expected2 + ")");
        }
        
        Map<String, Double> scores3 = new HashMap<>();
        scores3.put("dogs", 1.5);
        scores3.put("are", 0.0);
        scores3.put("smart", 2.0);
        
        double result3 = calculateSentenceScore(scores3, "dogs are ?smart");
        double expected3 = (1.5 + 0.0) / 2; // 1.5 / 2 = 0.75
        if (Math.abs(result3 - expected3) < 0.001) {
            System.out.println("Punctuation filtering: " + result3 + " (expected: " + expected3 + ")");
        } else {
            System.out.println("Punctuation filtering failed: " + result3 + " (expected: " + expected3 + ")");
        }
        
        Map<String, Double> scores4 = new HashMap<>();
        scores4.put("dogs", 1.5);
        scores4.put("are", 0.0);
        
        double result4 = calculateSentenceScore(scores4, "dogs are funny");
        double expected4 = (1.5 + 0.0 + 0.0) / 3; // 1.5 / 3 = 0.5
        if (Math.abs(result4 - expected4) < 0.001) {
            System.out.println("Missing words: " + result4 + " (expected: " + expected4 + ")");
        } else {
            System.out.println("Missing words failed: " + result4 + " (expected: " + expected4 + ")");
        }
        
        Map<String, Double> scores5 = new HashMap<>();
        scores5.put("dogs", 1.5);
        scores5.put("are", 0.0);
        scores5.put("cute", 2.0);
        
        double result5 = calculateSentenceScore(scores5, "DOGS ARE CUTE");
        double expected5 = (1.5 + 0.0 + 2.0) / 3; // 3.5 / 3 = 1.1667
        if (Math.abs(result5 - expected5) < 0.001) {
            System.out.println("Case insensitivity: " + result5 + " (expected: " + expected5 + ")");
        } else {
            System.out.println("Case insensitivity failed: " + result5 + " (expected: " + expected5 + ")");
        }
        
        double result6a = calculateSentenceScore(null, "dogs are cute");
        double result6b = calculateSentenceScore(scores1, null);
        double result6c = calculateSentenceScore(scores1, "");
        
        if (result6a == 0 && result6b == 0 && result6c == 0) {
            System.out.println("Null inputs handled correctly");
        } else {
            System.out.println("Null inputs not handled correctly");
        }
        
        double result7 = calculateSentenceScore(scores1, "   ");
        if (result7 == 0) {
            System.out.println("Empty sentence handled correctly");
        } else {
            System.out.println("Empty sentence not handled correctly");
        }
        
        double result8 = calculateSentenceScore(scores1, "! ? .");
        if (result8 == 0) {
            System.out.println("Only punctuation handled correctly");
        } else {
            System.out.println("Only punctuation not handled correctly");
        }
        
    }

}
