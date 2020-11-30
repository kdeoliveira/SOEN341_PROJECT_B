package assembler.tokenization;

public enum FORMAT {
    OPCODERELATIVE("([a-z])+(\\.[ui](8|16|32)$){1}"),
    OPCODEIMMEDIATE("([a-z])+(\\.[ui](3|5)$){1}"),
    OPCODEINHERENT("([a-z]){2,}"),
    // OPCODE("([a-z])+(\\.[ui](3|5|8)$)?"),
    DIRECTIVE("[.][a-z]{2,}"),
    LABEL("\\p{Upper}\\p{Lower}+"),
    OPERAND("-?\\d+"),
    COMMENT("[;].+"),
    STRINGOPERAND("\\p{ASCII}");

    public final String pattern;

    private FORMAT(String pattern){
        this.pattern = pattern;
    }

    public String getPattern(){
        return pattern;
    }

}
