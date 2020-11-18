package assembler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

import assembler.tokenization.*;
import util.*;

public class Engine {
    private TrinarySearchTree<String, String> dictionary;
    private ReadLine src;
    private ArrayList<Node> linestatement;
    private ArrayList<Token> errors;

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
        errors = new ArrayList<>();
        Token[] tok;
        //If line has error, then stop (return)
        if(!lex.tokenization(str)){
            System.out.println("Syntax Error: "+Arrays.toString(lex.getErrors()));
            return;
        }
        else if(lex.getTokens()[0].getType().equals(SYNTAX.OPCODE) && dictionary.contains(str[0])){
            tok = (Token[]) parser.parse(lex.getTokens());
            if(tok.length > 0){
                for(Token x : tok){
                    linestatement.add(new Node(dictionary.getAddress(x.getData()), x.getData()));
                }
            }
            errors.addAll(Arrays.asList(parser.getErrors()));
        }
        line();
    }

    public Node[] getLinestatement() {
        return linestatement.toArray(new Node[0]);
    }

    public Token[] getErrors() {
        return errors.toArray(new Token[0]);
    }

}
