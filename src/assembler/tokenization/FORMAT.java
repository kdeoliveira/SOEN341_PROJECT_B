package assembler.tokenization;

public enum FORMAT {
    OPCODERELATIVE("([a-z])+((\\.(u|i)(8|16|32))|(\\*))$"),
    OPCODEIMMEDIATE("([a-z])+(\\.[ui](3|5)$){1}"),
    OPCODEINHERENT("([a-z])+"),
    // OPCODE("([a-z])+(\\.[ui](3|5|8)$)?"),
    LABEL("\\p{Upper}\\p{Lower}+"),
    OPERAND("-?\\d+"),
    COMMENT("[;].+");

    public final String pattern;

    private FORMAT(String pattern){
        this.pattern = pattern;
    }

    public String getPattern(){
        return pattern;
    }

}

