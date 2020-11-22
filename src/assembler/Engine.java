package assembler;

import java.util.*;

import assembler.tokenization.*;
import util.*;

public class Engine {
    private Map<String,BinaryAddress> dictMap;
    private List<LineStatement> lines;
    private List<Node> labels;
    private List<CharSequence> errorList;
    private int numberOfLine;
    private BinaryAddress memoryAddress;
    private Lexical lex;
    private Parsable parser;

    public Engine(Map<String, BinaryAddress> dict, Lexical lexer, Parsable parser) {
        this.dictMap = dict;

        this.memoryAddress = new BinaryAddress(0x0);
        this.lines = new ArrayList<>();
        this.labels = new ArrayList<>();
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
        boolean flag = false;
        Instruction inst = null;
        Node label = null;
        Comment comment = null;

        for(int i = 0; i < code.length ; i++){
            cnt+=code[i].length();
            if(Token.class.cast(code[i]).getKey().equals(SYNTAX.LABEL)){
                label = new Node(0, Token.class.cast(code[i]).getValue());
                if(i == 0){
                    flag = true;
                    label.setValue(new BinaryAddress(this.numberOfLine));
                    this.labels.add(label);
                }
            }   
            if(Token.class.cast(code[i]).getKey().equals(SYNTAX.COMMENT)){
                comment = new Comment(Token.class.cast(code[i]).getValue());
                flag =true;
            }
            if(Token.class.cast(code[i]).getKey().equals(SYNTAX.OPCODE) && this.dictMap.containsKey(Token.class.cast(code[i]).getValue()) )
            {
                flag = true;
                inst = new Instruction(this.dictMap.get(Token.class.cast(code[i]).getValue()), Token.class.cast(code[i]).getValue(), this.parser.getTypeEBNF());
            }
            if(Token.class.cast(code[i]).getKey().equals(SYNTAX.OPERAND) && inst != null){
                flag = true;
                inst.setOperand(new Operand(new BinaryAddress(Token.class.cast(code[i]).getValue(), false), Token.class.cast(code[i]).getValue()));
            }
        }

        if(flag){
            this.lines.add(new LineStatement(this.numberOfLine+1, label, inst, comment, parser.getTypeEBNF()));
            return true;
        }
        else{
            this.errorList.add(new Error<>(cnt, Arrays.toString(code)));
            return false;
        }
    }

    public List<LineStatement> getLines() {
        return lines;
    }

    public int getNumberOfLine() {
        return numberOfLine;
    }

    public List<Node> getLabels() {
        return labels;
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
