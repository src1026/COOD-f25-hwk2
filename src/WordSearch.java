package src;

import java.io.*;
import java.util.*;

/*
 * Implements a text search engine for a collection of documents in the same directory.
 */

public class WordSearch {
	
	public static Map<String, Set<String>> buildMap(String dirName) {
		File dir = new File(dirName);	// create a File object for this directory
		
		// make sure it exists and is actually a directory
		if (dir.exists() == false || dir.isDirectory() == false) {
            // this tells the caller "you gave me bad input"
			throw new IllegalArgumentException(dirName + " does not exist or is not a directory");
		}
		
		File[] files = dir.listFiles();		// get the Files in the specified directory
		
		// Implement the rest of this method starting from here!
        // creates and returns a mapping of the distinct terms to the files in which each is found
        Map<String, Set<String>> word_file_map = new HashMap<>();
        for (File file : files) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    System.out.println(line);
                    String[] words = line.split(" ");
                    System.out.println(Arrays.toString(words));
                    for (int i = 0; i < words.length; i++) {
                        String word = words[i].toLowerCase();
                        System.out.println(word);
                        if (!word_file_map.containsKey(word)) {
                            word_file_map.put(word, new HashSet<>());
                        }
                        word_file_map.get(word).add(file.getName());
                    }
                }

            } catch (FileNotFoundException e) {
                System.err.println("File " + file.getName() + " not found");
            }

        }
        return word_file_map;

		// this is for debugging, just to make sure it's reading the right files
//        for (File file : files) {
//            System.out.println(file.getName());
//        }


		
//		return Collections.EMPTY_MAP; // change this as necessary
		
	}
	
	public static List<String> search(String[] terms, Map<String, Set<String>> map) {
		// Implement this method starting from here!
        Map<String, Integer> file_freq_map = new HashMap<>();
        Map<Integer, List<String>> freq_file_map = new HashMap<>();

        // populate the file_freq_map
        for (String t : terms) {
            Set<String> associated_files = map.get(t);
            for (String file : associated_files) {
                if (!file_freq_map.containsKey(file)) {
                    file_freq_map.put(file, 1);
                } else {
                    file_freq_map.put(file, file_freq_map.get(file) + 1);
                }
            }
        }
        int max_freq = Integer.MIN_VALUE;;
        int min_freq = Integer.MAX_VALUE;
        for (Map.Entry<String, Integer> entry : file_freq_map.entrySet()) {
            String file_name = entry.getKey();
            int freq = entry.getValue();
            max_freq = Math.max(max_freq, freq);
            min_freq = Math.min(min_freq, freq);
            if (!freq_file_map.containsKey(freq)) {
                freq_file_map.put(freq, new ArrayList<>());
            }
            freq_file_map.get(freq).add(file_name);
        }

        List<String> result = new ArrayList<>();

        for (int f = max_freq; f >= min_freq; f--) {
            if (freq_file_map.containsKey(f)) {
                List<String> file_list = freq_file_map.get(f);
                Collections.sort(file_list);
                for (String file : file_list) {
                    result.add(file);
                }
            }
        }
		return result; // change this as necessary
	}
	
	public static void main(String[] args) {
		Map<String, Set<String>> map = buildMap(args[0]);
		System.out.println(map); 					// for debugging purposes
		
		System.out.print("Enter a term to search for: ");
		
		try (Scanner in = new Scanner(System.in)) { // create a Scanner to read from stdin
			String input = in.nextLine();			// read the entire line that was entered
			String[] terms = input.split(" ");		// separate tokens based on a single whitespace
			List<String> list = search(terms, map);	// search for the tokens in the Map
			for (String file : list) {				// print the results
				System.out.println(file);
			}
		}
		catch (Exception e) {
			// oops! something went wrong
			e.printStackTrace();
		}
	}

}
