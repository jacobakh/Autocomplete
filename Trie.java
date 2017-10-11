import java.util.*;

/**
 * Prefix-Trie. Supports linear time find() and insert().
 * Should support determining whether a word is a full word in the
 * Trie or a prefix.
 *
 * @author
 */
public class Trie {
    TrieNode root = new TrieNode(false);

    public boolean find(String s, boolean isFullWord) {
        /* YOUR CODE HERE. */
        /// If string is empty
        if (s.length() == 0 || s.equals("")) {
            return true;
        }
        /// Check if first char of s exists in root's links
        TrieNode curr = root;
        int lastIndex = s.length() - 1;
        if (!root.links.containsKey(s.charAt(0))) {
            return false;
        }
        /// Iterate through link's path
        for (int i = 0; i < lastIndex; i++) {
            if (curr.links.isEmpty()) {
                return false;
            } else {
                /// Check if element is in curr treeMap
                if (!curr.links.containsKey(s.charAt(i))) {
                    return false;
                } else { /// Go to next character
                    curr = curr.links.get(s.charAt(i));
                }
            }
        }
        /// Check if last char is in current treeMap and is marked as existing end word
        if (isFullWord && curr.links.containsKey(s.charAt(lastIndex))
                && curr.links.get(s.charAt(lastIndex)).exists) {
            return true;
        } else if (!isFullWord && curr.links.containsKey(s.charAt(lastIndex))) {
            return true;
        }
        return false;
    }

    public void insert(String s) {
        /* YOUR CODE HERE. */
        //System.out.println("Insert " + s);
        if (s.isEmpty() || s == null) {
            throw new IllegalArgumentException();
        }
        TrieNode curr = root;
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
                    curr.links.put(s.charAt(i), new TrieNode(true));

                } else {
                    curr.links.put(s.charAt(i), new TrieNode(false));
                    curr = curr.links.get(s.charAt(i));
                }

            }
        }
    }

    public ArrayList<String> keysPrefix(String prefix) {
        if (!find(prefix, false)) {
            return new ArrayList<>();
        }

        TrieNode prefixPtr = root;
        ArrayList<String> prefixTerms = new ArrayList<>();
        String word = "";
        int index = 0;

        /// Iterate through link's path
        /// prefixPtr will point to the last letter in prefix
        for (int i = 0; i < prefix.length(); i++) {
            prefixPtr = prefixPtr.links.get(prefix.charAt(i));
        }
        /// Put list of all words with "prefix" in prefixTerms
        getWords(prefixPtr, "", prefixTerms, prefix);
        //System.out.println(prefixTerms);
        return prefixTerms;
    }


    public void getWords(TrieNode node, String word,
                         ArrayList<String> wordList, String prefix) {
        if (node.links.isEmpty()) {
            wordList.add(prefix + word);
        }
        for (Character c : node.links.keySet()) {
            if (node.links.get(c).exists && !node.links.get(c).links.isEmpty()) {
                wordList.add(prefix + word + c.toString());
            }
            getWords(node.links.get(c), word + c.toString(), wordList, prefix);
        }
    }

    public void getHigherWords(TrieNode node, String word,
                         ArrayList<String> wordList, String prefix) {
        if (node.links.isEmpty()) {
            wordList.add(prefix + word);
        }
        for (Character c : node.links.keySet()) {
            if (node.links.get(c).exists && !node.links.get(c).links.isEmpty()) {
                wordList.add(prefix + word + c.toString());
            }
            getWords(node.links.get(c), word + c.toString(), wordList, prefix);
        }
    }


    public void printWords(TrieNode node, String word) {
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


    //// Encapsulation of TrieNode Class
    public class TrieNode {
        double endWeight;
        boolean exists;
        Map<Character, Double> weightMap;
        Map<Character, TrieNode> links;

        public TrieNode(boolean isWord) {
            this.exists = isWord;
            this.links = new TreeMap<Character, TrieNode>();
        }

        public void setExists(boolean exists) {
            this.exists = exists;
        }
    }

}

