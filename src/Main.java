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
        
        if (args.length == 0) {
            System.out.println("no input file");
            return;
        }
        
        String inputFile = args[0];
        Set<Sentence> sentences = Reader.readFile(inputFile);
        
        if (sentences == null) {
            System.out.println("bad input file");
            return;
        }
        
        Map<String, Double> wordScores = Analyzer.calculateWordScores(sentences);
        
        Scanner scanner = new Scanner(System.in);
        

        while (true) {
            System.out.print("Enter a sentence: ");
            String userSentence = scanner.nextLine();

            if (userSentence.equals("quit")) {
                break;
            }
            
            double sentenceScore = Analyzer.calculateSentenceScore(wordScores, userSentence);
            System.out.println("Sentiment score: " + sentenceScore);
        }
        
        scanner.close();
    }
}
