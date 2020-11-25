package assembler.tokenization;

import assembler.Vertex;

/**
 * Eventually change CharSequence implementation to another more preferrable
 */
public class Token implements Vertex<FORMAT>{
    private final FORMAT key;
    private static final long serialVersionUID = 760703066967345L;
    private String value;

    public Token(FORMAT type, String data){
        this.key = type;
        this.value = data;
    }

    public FORMAT getKey() {
        return this.key;
    }


    public String getValue() {
        return this.value;
    }

    @Override
    public String toString(){
        if(this.key != null)
            return String.format("%s (%s)",this.value, this.key.name());
        else
            return this.value;
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
