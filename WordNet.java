
import java.util.ArrayList;
import java.util.*;


/**
 * Created by cheng305 on 4/8/17.
 */
public final class WordNet {
    private Digraph G;
    HashMap<String, Integer> hm;
    ArrayList<Node> list;
    private SAP S;

    private class Node {
        int id;
        String word_set;
        public Node(int id, String word_set){
            this.id = id;
            this.word_set = word_set;
        }
    }

    public int getID(String word){

        if(hm.get(word)!= null) {
            return (int) hm.get(word);
        }
        return -1;
    }


    public WordNet(String synsets, String hypernyms){
        this.list = new ArrayList<>();
        this.hm = new HashMap<String, Integer>();
        list.add(new Node(0, "not_a_word"));
        //hm.put("not_a_word", 0);

        int num_synset = 0;
        In in2 = new In(synsets);
        while (!in2.isEmpty()) {
            String line = in2.readLine();
            String[] arr = line.split(",");
            int id = Integer.parseInt(arr[0]);
            String word_set = arr[1];
            Node node = new Node(id, word_set);
            list.add(node);
            String[] words = word_set.split(" ");
            for (String word: words) {
                hm.put(word, id);
            }
            num_synset++;
        }

        G = new Digraph(++num_synset);

        In in_hypernym = new In(hypernyms);

        while(!in_hypernym.isEmpty()){
            String line = in_hypernym.readLine();
            String[] arr = line.split(",");
            int v = Integer.parseInt(arr[0]);
            for (int i = 1; i < arr.length; i++) {
                int w = Integer.parseInt(arr[i]);
                G.addEdge(v,w);
            }
        }
        S = new SAP(G);

    }

    public boolean isNoun(String word) {
        for (Node node : list){
            String[] words = node.word_set.split(" ");
            for (String str: words) {
                if (word.compareTo(str) == 0) {
                    return true;
                }
            }

        }
        return false;
    }

    public void printSap(String nounA, String nounB) {
        int aID = getID(nounA);
        int bID = getID(nounB);
        if (aID != -1 && bID != -1) {
            int ant = S.ancestor(aID, bID);
            int len = S.length(aID, bID);

            if(ant != -1) {
                StdOut.print("sap = " + len + ", ancestor = " + list.get(ant).word_set + "\n");
            } else {
                StdOut.print("sap = -1" + ", ancestor = null\n");
            }

        } else
            StdOut.print("sap = -1" + ", ancestor = null\n");
    }

    public static void main(String[] args){
        String syn = args[0];
        String hyp = args[1];

        WordNet wn = new WordNet(syn, hyp);
       // StdOut.print(wn.getID("administrative_district") + "\n");
        //StdOut.print(wn.getID("") + "\n");
        boolean a = wn.isNoun("populated_area");
       StdOut.print(a + "\n");

        In in = new In(args[2]);

        while(!in.isEmpty()){
            String line = in.readLine();
            String[] words = line.split(" ");
            //int aID = wn.getID(words[0]);
            //int bID = wn.getID(words[1]);
            //StdOut.print(aID + " " + bID + "\n");
            wn.printSap(words[0], words[1]);
        }
    }
}
