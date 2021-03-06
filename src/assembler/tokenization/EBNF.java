package assembler.tokenization;

public enum EBNF {
    COMMENT(FORMAT.COMMENT),
    LABEL(FORMAT.LABEL),
    INHERENT(FORMAT.OPCODEINHERENT),
    RELATIVE3(FORMAT.LABEL, FORMAT.OPCODERELATIVE, FORMAT.LABEL),
    RELATIVE2(FORMAT.OPCODERELATIVE, FORMAT.OPERAND),
    INHERENT2(FORMAT.LABEL, FORMAT.OPCODEINHERENT),
    
    IMMEDIATE(FORMAT.OPCODEIMMEDIATE, FORMAT.OPERAND),
    RELATIVE(FORMAT.LABEL, FORMAT.OPCODERELATIVE, FORMAT.OPERAND),
    RELATIVE1(FORMAT.OPCODERELATIVE, FORMAT.LABEL);


    private final FORMAT[] type;

    private EBNF(FORMAT...types){
        this.type = types;
    }
    
    public boolean contains(FORMAT type){
        return this.contains(type);
    }

    public FORMAT[] getType() {
        return type;
    }
}
