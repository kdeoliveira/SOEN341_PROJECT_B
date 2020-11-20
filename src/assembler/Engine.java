package assembler;

import java.util.*;

import assembler.tokenization.*;
import util.*;

public class Engine {
    private Map<String,BinaryAddress> dictMap;

    private Map<Integer,Node> linestatement;
    private List<LineStatement> lines;
    private List<CharSequence> errorList;
    private int numberOfLine;
    private BinaryAddress memoryAddress;
    private Lexical lex;
    private Parsable parser;

    public Engine(Map<String, BinaryAddress> dict, Lexical lexer, Parsable parser) {
        this.dictMap = dict;

        this.memoryAddress = new BinaryAddress(0x0);
        this.lines = new ArrayList<>();
        this.linestatement = new HashMap<>();
        this.errorList = new ArrayList<>();
        this.numberOfLine = 0;
        this.lex = lexer;
        this.parser = parser;
    }

    public boolean assemble(String...code){
        if(code.length == 0)    return false;
        
        if(!this.checkSyntax(code) || 
        !this.checkSemantic(lex.getTokens().toArray(new Vertex<?>[0])) || 
        !this.checkDictionary(lex.getTokens().toArray(new Vertex<?>[0]))) return false;
        
        this.numberOfLine++;
        this.memoryAddress.plus(0x1);
        return true;
    }

    private boolean checkSyntax(String...code){
        if(!lex.tokenization(code)){
            this.errorList.addAll(lex.getTokens());
            return false;
        }
        return true;
    }

    private boolean checkSemantic(CharSequence...code){
        if(!parser.parse((Vertex<?>[]) code)) {
            if(parser.getReturnValueObjects() == null)
                this.errorList.add(new Error<Integer>(0, "Incorrect format"));
            else
                this.errorList.addAll(parser.getReturnValueObjects());
            return false;
        }
        return true;
    }

    private boolean checkDictionary(CharSequence...code){
        int cnt = 0;
        // BinaryAddress temp = null;
        boolean flag = false;
        // StringBuilder str = new StringBuilder();
        Instruction inst = null;
        Node label = null;

        for(CharSequence x : code){
            cnt+=x.length();
            if(Token.class.cast(x).getKey().equals(SYNTAX.LABEL)){
                label = new Node(0, Token.class.cast(x).getValue());
                if(code.length == 1){
                    // temp = new BinaryAddress(this.numberOfLine);
                    flag = true;
                    label.setValue(new BinaryAddress(this.numberOfLine));
                    break;
                }
            }   
            if(Token.class.cast(x).getKey().equals(SYNTAX.OPCODE) && this.dictMap.containsKey(Token.class.cast(x).getValue()) )
            {
                flag = true;
                inst = new Instruction(this.dictMap.get(Token.class.cast(x).getValue()), Token.class.cast(x).getValue());
                // temp = this.dictMap.get(Token.class.cast(x).getValue());
                // str.append(Token.class.cast(x).getValue()+" ");
            }
        }

        if(flag){
            this.lines.add(new LineStatement(this.numberOfLine+1, label, inst, null));
            return true;
        }
        else{
            this.errorList.add(this.numberOfLine+1, new Error<>(cnt, Arrays.toString(code)));
            return false;
        }
    }

    public Map<Integer, Node> getLinestatement() {
        return linestatement;
    }

    public List<LineStatement> getLines() {
        return lines;
    }

    public int getNumberOfLine() {
        return numberOfLine;
    }

    public BinaryAddress getMemoryAddress() {
        return memoryAddress;
    }

    public List<CharSequence> getErrorList() {
        return errorList;
    }

    public boolean hasErrors(){
        return this.errorList.isEmpty();
    }

}
