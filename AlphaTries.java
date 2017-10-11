/**
 * Created by juanrodriguezquintanilla on 8/3/17.
 */
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by juanrodriguezquintanilla on 8/2/17.
 */
public class AlphaTries {
    //private static final int R = 26;
    AlphaNode root = new AlphaNode(false);
    String order;

    public AlphaTries(String order) {
        this.order = order;
    }

    public void insert(String s) {
        /* YOUR CODE HERE. */
        //System.out.println("Insert " + s);
        if (s.isEmpty() || s == null) {
            throw new IllegalArgumentException();
        }
        AlphaNode curr = root;
        /// Iterate through every char in string
        for (int i = 0; i < s.length(); i++) {
            // If the first char in s is already present
            // in one of the Trie links, move to next char
            if (curr.links.containsKey(s.charAt(i))) {
                if (i == s.length() - 1) {
                    curr.links.get(s.charAt(i)).setExists(true);
                    curr = curr.links.get(s.charAt(i));
                } else {
                    curr = curr.links.get(s.charAt(i));
                }
            } else { // Add a new link to curr TrieNode
                if (i == s.length() - 1) {
                    curr.links.put(s.charAt(i), new AlphaNode(true));
                } else {
                    curr.links.put(s.charAt(i), new AlphaNode(false));
                    curr = curr.links.get(s.charAt(i));
                }

            }
        }
    }

    //// Encapsulation of TrieNode Class
    class AlphaNode {
        boolean exists;
        Map<Character, AlphaNode> links;

        AlphaNode(boolean isWord) {
            this.exists = isWord;
            this.links = new TreeMap<Character, AlphaNode>(new AlphaComp());
        }

        public void setExists(boolean exists) {
            this.exists = exists;
        }
    }

    class AlphaComp implements Comparator<Character> {
        @Override
        public int compare(Character o1, Character o2) {
            int pos1 = 0;
            int pos2 = 0;
            pos1 = order.indexOf(o1);
            pos2 = order.indexOf(o2);
            return pos1 - pos2;
        }
    }

    public void printWords(AlphaNode node, String word) {
        if (node.links.isEmpty()) {
            System.out.println(word);
        }
        for (Character c : node.links.keySet()) {
            //System.out.println(c + ": exists:  " + node.links.get(c).exists);
            if (node.links.get(c).exists && !node.links.get(c).links.isEmpty()) {
                //System.out.println("HERREEEE");
                System.out.println(word + c.toString());
            }
            printWords(node.links.get(c), word + c.toString());
        }
    }
}

