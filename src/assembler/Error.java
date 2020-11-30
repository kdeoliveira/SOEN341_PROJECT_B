package assembler;

import java.util.ArrayList;
import java.util.List;

import assembler.tokenization.Token;

public class Error<S extends Comparable<S>> implements Vertex<S> {
    private static final long serialVersionUID = 42041950251807L;
    private transient S position;
    private transient List<CharSequence> value;
    private final String message;

    /**
     * Incomplete
     * @param startLine
     */
    public Error(S position, List<CharSequence> value, String message){
        this.position = position;
        this.value = value;
        this.message = message;
    }
    public Error(S position, CharSequence value, String message){
        this.position = position;
        this.value = new ArrayList<>();
        this.value.add(new Token(null, value.toString()));
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public S getPosition() {
        return position;
    }
    public List<CharSequence> getValue() {
        return value;
    }

    // public boolean contain(FORMAT type){
    //     return this.value.getKey() == type;
    // }

    public boolean equalsTo(CharSequence err){
        return this.value == err;
    }


    @Override
    public String toString(){
        if(this.value instanceof List)
            return String.format("%s : %s --> %s", this.value == null ? "Unknown": this.value, this.position, this.message == null ? "" : this.message);
        else
            return String.format("%s:%s",this.position, this.message);
    }

    @Override
    public char charAt(int arg0) {
        return this.message.charAt(arg0);
    }

    @Override
    public int length() {
        return this.message.length();
    }

    @Override
    public CharSequence subSequence(int arg0, int arg1) {
        return this.message.subSequence(arg0, arg1);
    }

    @Override
    public S getKey() {
        return this.position;
    }

}
