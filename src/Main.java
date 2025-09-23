package src;

import java.util.*;

/**
 * @author [your name here]
 *
 * This class holds the main() method for the sentiment analysis program.
 */

public class Main {

    public static void main(String[] args) {
        // implement this method in Step 4
        
        // Check if input file argument is provided
        if (args.length == 0) {
            System.out.println("no input file");
            return;
        }
        
        String inputFile = args[0];
        
        // Read the input file and get Set of Sentence objects
        Set<Sentence> sentences = Reader.readFile(inputFile);
        
        // Check if file reading was successful
        if (sentences == null) {
            System.out.println("bad input file");
            return;
        }
        
        // Calculate word scores from the sentences
        Map<String, Double> wordScores = Analyzer.calculateWordScores(sentences);
        
        // Create scanner for user input
        Scanner scanner = new Scanner(System.in);
        
        // Main loop for user input
        while (true) {
            // Prompt user to enter a sentence
            System.out.print("Enter a sentence: ");
            String userSentence = scanner.nextLine();
            
            // Check if user wants to quit
            if (userSentence.equals("quit")) {
                break;
            }
            
            // Calculate sentence score and print result
            double sentenceScore = Analyzer.calculateSentenceScore(wordScores, userSentence);
            System.out.println("Sentiment score: " + sentenceScore);
        }
        
        // Close scanner
        scanner.close();
    }
}
