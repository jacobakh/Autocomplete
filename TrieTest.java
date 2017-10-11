import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by juanrodriguezquintanilla on 8/2/17.
 */
public class TrieTest {
    @Test
    public void find() throws Exception {
        Trie t = new Trie();
        t.insert("a");
        t.insert("b");
        t.insert("c");
        t.insert("a");
        System.out.println(t.root.links);

        System.out.println(t.root.links.get('b').links);
        t.insert("bel");
        t.insert("bil");
        t.insert("bol");
        System.out.println("b Links: " + t.root.links.get('b').links.keySet());
        System.out.println("b: " + t.root.links.get('b').exists);
        System.out.println("e: " + t.root.links.get('b').links.get('e').exists);
        System.out.println("l: " + t.root.links.get('b').links.get('e').links.get('l').exists);

        System.out.println("Find 'bel: '" + t.find("bel", true));
        System.out.println("Find 'bOl: '" + t.find("bOl", true));
        System.out.println("Find 'bela: '" + t.find("bela", true));
        System.out.println("Find 'z: '" + t.find("z", true));


    }

    @Test
    public void insert() throws Exception {
        Trie t = new Trie();
        t.insert("a");
        t.insert("b");
        t.insert("c");
        t.insert("a");
        System.out.println(t.root.links);

        System.out.println(t.root.links.get('b').links);
        t.insert("bel");
        t.insert("bil");
        t.insert("bol");
        System.out.println("b Links: " + t.root.links.get('b').links.keySet());
        System.out.println("b: " + t.root.links.get('b').exists);
        System.out.println("e: " + t.root.links.get('b').links.get('e').exists);
        System.out.println("l: " + t.root.links.get('b').links.get('e').links.get('l').exists);

        t = new Trie();
        t.insert("hello");
        t.insert("hey");
        t.insert("goodbye");
        System.out.println(t.find("hell", false));
        System.out.println(t.find("hello", true));
        System.out.println(t.find("good", false));
        System.out.println(t.find("bye", false));
        System.out.println(t.find("heyy", false));
        System.out.println(t.find("hell", true));
    }

    @Test
    public void keysPrefix() throws Exception {
        Trie t = new Trie();
        t.insert("holaaaa");
        t.insert("hole");
        t.insert("holi");
        t.insert("holo");
        t.insert("geyo");
        t.insert("goodbye");
        t.insert("goodb");
        t.insert("goodbuuu");

        ArrayList<String> words = t.keysPrefix("go");
        System.out.println("words -> " + words);

    }
}

