/**
 * Implements autocomplete on prefixes for a given dictionary of terms and weights.
 *
 * @author
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.PriorityQueue;
public class Autocomplete {
    /**
     * Initializes required data structures from parallel arrays.
     *
     * @param terms Array of terms.
     * @param weights Array of weights.
     */
    private Trie autoTrie;
    private String[] terms;
    private double[] weights;
    private HashMap<String, Double> termMap = new HashMap<String, Double>();

    public Autocomplete(String[] terms, double[] weights) {
        /// Error checking
        autoTrie = new Trie();
        if (terms.length != weights.length) {
            throw new IllegalArgumentException(
                    "The length of the terms and weights arrays are different");
        }
        for (double w : weights) {
            if (w < 0) {
                throw new IllegalArgumentException("There are negative weights");
            }
        }
        Set<String> repeats = new HashSet<String>();
        for (String s : terms) {
            if (repeats.contains(s)) {
                throw new IllegalArgumentException("Error: Repeated terms");
            }
            repeats.add(s);
        }
        /// Populate map
        for (int i = 0; i < terms.length; i++) {
            termMap.put(terms[i], weights[i]);
        }

        /* YOUR CODE HERE. */
        this.terms = terms;
        this.weights = weights;

        //autoTrie.insert(terms[0]);
        for (int i = 0; i < terms.length; i++) {
            autoTrie.insert(terms[i]);
        }
    }

    /**
     * Find the weight of a given term. If it is not in the dictionary, return 0.0
     *
     * @param term
     * @return
     */
    public double weightOf(String term) {
        /* YOUR CODE HERE. */
        for (int i = 0; i < terms.length; i++) {
            if (terms[i].equals(term)) {
                return weights[i];
            }
        }
        return 0.0;
    }

    /**
     * Return the top match for given prefix, or null if there is no matching term.
     * @param prefix Input prefix to match against.
     * @return Best (highest weight) matching string in the dictionary.
     */
    public String topMatch(String prefix) {
        /* YOUR CODE HERE. */
//        if (!autoTrie.find(prefix, false) || !autoTrie.find(prefix, true)){
//            return null;
//        }
        ArrayList<String> wordsList = autoTrie.keysPrefix(prefix);

        //System.out.println("List of words that match " + prefix + ": " + wordsList);

        double maxWeight = 0;
        String maxWord = "";

        if (!prefix.equals("") && autoTrie.find(prefix, true)) {
            maxWord = prefix;

            maxWeight = termMap.get(prefix);
        }

        for (String word : wordsList) {
            if (termMap.get(word) > maxWeight) {
                maxWeight = termMap.get(word);
                maxWord = word;
            }
        }
        return maxWord;
    }


    /**
     * Returns the top k matching terms (in descending order of weight) as an iterable.
     * If there are less than k matches, return all the matching terms.
     *
     * @param prefix
     * @param k
     * @return
     */
    public Iterable<String> topMatches(String prefix, int k) {
        /* YOUR CODE HERE. */
        if (prefix == null) {
            throw new NullPointerException("null prefix.");
        }
        if (k < 0) {
            throw new IllegalArgumentException(
                    "Trying to find the k top matches for non-positive k.");
        }
        ArrayList<String> wordsList = autoTrie.keysPrefix(prefix);
        /// Declare Iterable string obj
        ArrayList<String> topWords = new ArrayList<String>();
        // / Declare a PQ ordered by weight
        PriorityQueue<String> queue =
                new PriorityQueue<String>(k,
                    (t1, t2) -> (int) weightOf(t2) - (int) weightOf(t1));

        for (String word : wordsList) {
            /// Put words in PQ ordered by weight
            queue.add(word);
        }

        if (queue.size() < k) {
            while (!queue.isEmpty()) {
                topWords.add(queue.poll());
            }
        } else {
            for (int i = 0; i < k; i++) {
                topWords.add(queue.poll());
            }
        }
        return topWords;
    }

    /**
     * Test client. Reads the data from the file, then repeatedly reads autocomplete
     * queries from standard input and prints out the top k matching terms.
     *
     * @param args takes the name of an input file and an integer k as
     *             command-line arguments
     */
    public static void main(String[] args) {
        // initialize autocomplete data structure
        In in = new In(args[0]);
        int N = in.readInt();
        String[] terms = new String[N];
        double[] weights = new double[N];
        for (int i = 0; i < N; i++) {
            weights[i] = in.readDouble();   // read the next weight
            in.readChar();                  // scan past the tab
            terms[i] = in.readLine();       // read the next term
        }

        Autocomplete autocomplete = new Autocomplete(terms, weights);

        // process queries from standard input
        int k = Integer.parseInt(args[1]);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            for (String term : autocomplete.topMatches(prefix, k)) {
                StdOut.printf("%14.1f  %s\n", autocomplete.weightOf(term), term);
            }
        }
    }
}
