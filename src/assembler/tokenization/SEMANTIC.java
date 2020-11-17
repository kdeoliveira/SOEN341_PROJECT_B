package assembler.tokenization;

public enum SEMANTIC {
    INHERENT(SYNTAX.OPCODE), IMMEDIATE(SYNTAX.OPCODE), BYTE(SYNTAX.OPCODE, SYNTAX.OPERAND);

    private final SYNTAX[] type;

    private SEMANTIC(SYNTAX...types){
        this.type = types;
    }
    
    public boolean contains(SYNTAX type){
        return this.contains(type);
    }



    public SYNTAX[] getType() {
        return type;
    }

    public static void main(String[] args){
        for(SEMANTIC x : SEMANTIC.values())
            for(SYNTAX y : x.getType())
                System.out.println(x+" "+y.getPattern());
    }
    
}
