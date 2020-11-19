package assembler.tokenization;


public enum SYNTAX{
    OPCODE("([a-z])+(\\.[ui](3|5|8)$)?"),
    LABEL("\\p{Upper}\\p{Lower}+"),
    OPERAND("\\d+"),
    COMMENT("[;].+");

    public final String pattern;

    private SYNTAX(String pattern){
        this.pattern = pattern;
    }

    public String getPattern(){
        return pattern;
    }

    public static void main(String[] args){
        for(SYNTAX x : SYNTAX.values()){
            System.out.println(x+" "+x.getPattern());
        }
    }
}
