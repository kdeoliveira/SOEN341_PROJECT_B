package assembler.tokenization;

public class Token {
    private final SYNTAX type;
    private String data;

    public Token(SYNTAX type, String data){
        this.type = type;
        this.data = data;
    }

    public SYNTAX getType() {
        return this.type;
    }

    public String getData() {
        return this.data;
    }

    @Override
    public String toString(){
        return String.format("%s (%s)",this.data, this.type.name());
    }
}
