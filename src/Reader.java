package src; /**
 * @author [YOUR NAME HERE!]
 *
 * This class contains a method for reading from a file and creating Sentence objects
 * for a sentiment analysis program.
 */

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Reader {
	/**
	 * This method reads sentences from the input file, creates a Sentence object
	 * for each, and returns a Set of the Sentences.
	 * 
	 * @param filename Name of the input file to be read
	 * @return Set containing one Sentence object per sentence in the input file; null if filename is null
	 */
	public static Set<Sentence> readFile(String filename) {
		/*
		 * Implement this method in Step 1
		 */
		
		// Return null if filename is null
		if (filename == null) {
			return null;
		}
		
		Set<Sentence> sentenceSet = new HashSet<>();
		
		try (Scanner scanner = new Scanner(new File(filename))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				
				if (line.isEmpty()) {
                    System.out.println("Empty line");
					continue;
				}
				
				String[] parts = line.split("\\s+");
				if (parts.length < 2) {
                    System.out.println("Invalid line: " + line);
					continue;
				}
				
				try {
					int score = Integer.parseInt(parts[0]);
					
					if (score < -2 || score > 2) {
                        System.out.println("Invalid score: " + score);
						continue;
					}
					
					StringBuilder textBuilder = new StringBuilder();
					for (int i = 1; i < parts.length; i++) {
						if (i > 1) {
							textBuilder.append(" ");
						}
						textBuilder.append(parts[i]);
					}
					String text = textBuilder.toString();
					
					Sentence sentence = new Sentence(score, text);
					sentenceSet.add(sentence);
					
				} catch (NumberFormatException e) {
                    System.out.println("Invalid score: " + parts[0]);
					continue;
				}
			}
			
		} catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
			return null;
		}
		
		return sentenceSet;
	}

    /**
     * Use this main() method for testing your Reader.readFile method with different inputs.
     * Note that this is _NOT_ the main() method for the whole sentiment analysis program!
     * Just use it for testing this class. It is not considered for grading.
     */
    public static void main(String[] args) {
        // Test the readFile method
        System.out.println("Testing Reader.readFile method:");
        
        // Test with valid file
        Set<Sentence> sentences = readFile("reviews.txt");
        if (sentences != null) {
            System.out.println("Successfully read " + sentences.size() + " sentences from reviews.txt");
            
            // Print first few sentences as examples
            int count = 0;
            for (Sentence sentence : sentences) {
                if (count < 3) {
                    System.out.println("Score: " + sentence.getScore() + ", Text: " + sentence.getText());
                    count++;
                } else {
                    break;
                }
            }
        } else {
            System.out.println("Failed to read reviews.txt");
        }
        
        // Test with null filename
        Set<Sentence> nullTest = readFile(null);
        System.out.println("Test with null filename: " + (nullTest == null ? "PASS" : "FAIL"));
        
        // Test with non-existent file
        Set<Sentence> nonExistentTest = readFile("nonexistent.txt");
        System.out.println("Test with non-existent file: " + (nonExistentTest == null ? "PASS" : "FAIL"));
    }
}
