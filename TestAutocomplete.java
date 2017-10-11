import ucb.junit.textui;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/** The suite of all JUnit tests for the Autocomplete class.
 *  @author
 */
public class TestAutocomplete {

    /** A dummy test to avoid complaint. */
    @Test
    public void placeholderTest() {

    }

//    @Test
//    public void autocompleteTest() {
//        // Testing constructor
//        String[] terms = {"holo", "holi", "holi", "yakub", "hola", "physically", "physical"};
//        double[] weights = {2, 10000, 4333.0, 100, 30, 600, 900};
//
//        try {
//            Autocomplete autoT = new Autocomplete(terms, weights);
//        } catch (Exception e) {
//            System.out.println("Error: Repetated terms");
//        }
//    }

    @Test
    public void weightOfTest() {
        String[] terms = {"holo", "hole", "holi", "yakub", "hola", "physically", "physical"};
        double[] weights = {2, 10000, 4333.0, 100, 30, 600, 900};
        Autocomplete autoT = new Autocomplete(terms, weights);

        String term = terms[2];
        double weight = autoT.weightOf(term);
        System.out.println(weight + " " + weights[2]);
        assertEquals(weight, weights[2], 0);
    }

    @Test
    public void topMatchTest() {
        String[] terms = {"holo", "hole", "holi", "yacool", "hola", "yakub", "physical"};
        double[] weights = {2, 10, 43, 100, 30, 6000, 900};

        Autocomplete autoT = new Autocomplete(terms, weights);

        /// Check for global maximum
        String prefix = "";
        String topMatch = autoT.topMatch(prefix);
        if (topMatch == null) {
            System.out.println("No words watch prefix: " + prefix);
        }
        //System.out.println(topMatch + " -> " + autoT.weightOf(topMatch));
        assertEquals(topMatch, "yakub");

        /// Check for maximum prefix
        prefix = "hol";
        topMatch = autoT.topMatch(prefix);
        if (topMatch == null) {
            System.out.println("No words watch prefix: " + prefix);
        }
        //System.out.println(topMatch + " -> " + autoT.weightOf(topMatch));
        assertEquals(topMatch, "holi");

        /// Check when prefix doesn't exist
        prefix = "x";
        topMatch = autoT.topMatch(prefix);
        assertEquals(topMatch, "");
    }


    @Test
    public void topMatchesTest() {
        String[] terms = {"holo", "hole", "holi", " ", "hola", "physically", "physical"};
        double[] weights = {2, 10000, 4333, 100, 30, 600, 900};
        Autocomplete autoT = new Autocomplete(terms, weights);

        String prefix = "";
        int k = 3;
        Iterable<String> topMatches = autoT.topMatches(prefix, k);
        if (topMatches == null) {
            System.out.println("No words watch prefix: " + prefix);
        }

        String[] correct = {"hole", "holi", "physical"};
        Iterator iter = topMatches.iterator();

        for (int i = 0; i < k; i++) {
            assertEquals(correct[i], iter.next());
        }

    }

    /** Run the JUnit tests above. */
    public static void main(String[] ignored) {
        textui.runClasses(TestAutocomplete.class);
    }
}
