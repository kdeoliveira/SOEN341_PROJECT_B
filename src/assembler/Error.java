package assembler;

import assembler.tokenization.FORMAT;
import assembler.tokenization.Token;

public class Error<S extends Comparable<S>> implements Vertex<S> {
    private static final long serialVersionUID = 42041950251807L;
    private transient S position;
    private Token value;

    /**
     * Incomplete
     * @param startLine
     */
    public Error(S position, Token value){
        this.position = position;
        this.value = value;
    }
    
    public Error(S position, CharSequence value){
        this.position = position;
        this.value = new Token(null, value.toString());
    }

    public CharSequence getValue() {
        return this.value.getValue();
    }

    public FORMAT getType() {
        return this.value.getKey();
    }

    public S getKey() {
        return position;
    }

    public boolean contain(FORMAT type){
        return this.value.getKey() == type;
    }

    public boolean equalsTo(CharSequence err){
        return this.value == err;
    }


    @Override
    public String toString(){
        if(this.value instanceof Token)
            return String.format("(%s:%s) %s", this.value.getKey() == null ? "Unknown": this.value.getKey(), this.position, this.value);
        else
            return String.format("%s:%s",this.position, this.value);
    }

    @Override
    public char charAt(int arg0) {
        return this.value.charAt(arg0);
    }

    @Override
    public int length() {
        return this.value.length();
    }

    @Override
    public CharSequence subSequence(int arg0, int arg1) {
        return this.value.subSequence(arg0, arg1);
    }

}
