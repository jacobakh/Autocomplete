/**
 * AlphabetSort takes input from stdin and prints to stdout.
 * The first line of input is the alphabet permutation.
 * The the remaining lines are the words to be sorted.
 * 
 * The output should be the sorted words, each on its own line, 
 * printed to std out.
 */
import java.io.*;
import java.util.*;

public class AlphabetSort {
    /**
     * Reads input from standard input and prints out the input words in
     * alphabetical order.
     *
     * @param args ignored
     */

    public static void main(String[] args) {
        /* YOUR CODE HERE. */
        /// Reads input from standard input
        Scanner input = new Scanner(System.in);
        /// Check for errors
        if (!input.hasNextLine()) {
            throw new IllegalArgumentException("No alphabet given");
        }
        /// Get dictionary order
        String order = input.nextLine();
        Set<Character> orderCheck = new HashSet<>();
        /// Check for repeats in alphabet
        for (int i = 0; i < order.length(); i++) {
            if (orderCheck.contains(order.charAt(i))) {
                throw new IllegalArgumentException(
                        "Repeated characters in alphabet");
            }
            orderCheck.add(order.charAt(i));
        }
        /// Declare alphaTrie to be sorted by "order"
        AlphaTries alphabet = new AlphaTries(order);
        /// Check if there are any words
        if (!input.hasNextLine()) {
            throw new IllegalArgumentException("No words given");
        }

        // Put words in Trie: Reading every line in file
        while (input.hasNext()) {
            String word = input.nextLine();
            boolean isValidWord = true;

            // Determine if word is a validWord
            for (int i = 0; i < word.length(); i++) {
                //System.out.println(word.charAt(i));
                if (!orderCheck.contains(word.charAt(i))) {
                    isValidWord = false;
                }
            }

            // Only put words in Trie if all its chars
            // are valid chars in our new alphabet
            //System.out.println("ouo" + word);
            if (isValidWord) {
                alphabet.insert(word);
            }
        }

        /// Print words in "order"
        alphabet.printWords(alphabet.root, "");
    }
}
