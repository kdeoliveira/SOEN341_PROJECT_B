package assembler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import assembler.tokenization.*;
import util.*;

public class Engine {
    private TrinarySearchTree<String, String> dictionary;
    private ReadLine src;
    private ArrayList<Node> linestatement;

    public Engine(TrinarySearchTree<String, String> dict, ReadLine file) {
        this.dictionary = dict;
        this.src = file;
        this.linestatement = new ArrayList<>();
    }

    public void line() throws IOException {

        if(this.src.iterator().hasNext())
            check(this.src.oneline());
    }

    private void check(String[] str) throws IOException {
        Lexer lex = new Lexer();
        Parser parser = new Parser();
        Token[] tok;
        if(!lex.tokenization(str))  return;

        if(dictionary.contains(str[0])){
            tok = (Token[]) parser.parse(lex.getTokens());
            if(tok.length > 0){
                for(Token x : tok){
                    linestatement.add(new Node(dictionary.getAddress(x.getData()), x.getData()));
                }
            }
        }
        

        line();
    }

    public Node[] getLinestatement() {
        return linestatement.toArray(new Node[0]);
    }

}
