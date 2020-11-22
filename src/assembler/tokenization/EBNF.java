package assembler.tokenization;

public enum EBNF {
    COMMENT(SYNTAX.COMMENT),
    LABEL(SYNTAX.LABEL),
    INHERENT(SYNTAX.OPCODE),
    INHERENT2(SYNTAX.LABEL, SYNTAX.OPCODE),
    IMMEDIATE(SYNTAX.OPCODE, SYNTAX.OPERAND),
    BYTE(SYNTAX.LABEL, SYNTAX.OPCODE, SYNTAX.OPERAND),
    RELATIVE(SYNTAX.OPCODE, SYNTAX.LABEL);


    private final SYNTAX[] type;

    private EBNF(SYNTAX...types){
        this.type = types;
    }
    
    public boolean contains(SYNTAX type){
        return this.contains(type);
    }

    public SYNTAX[] getType() {
        return type;
    }

    public static void main(String[] args){
        for(EBNF x : EBNF.values())
            for(SYNTAX y : x.getType())
                System.out.println(x+" "+y.getPattern());
    }
    
}
