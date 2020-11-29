package assembler.tokenization;

import assembler.Node;
import assembler.Vertex;

/**
 * Eventually change CharSequence implementation to another more preferrable
 */
public class Token implements Vertex<FORMAT>{
    private final FORMAT key;
    private static final long serialVersionUID = 760703066967345L;
    private Node value;

    public Token(FORMAT type, Node data){
        this.key = type;
        this.value = data;
    }

    public Token(FORMAT type, String data){
        this.key = type;
        this.value = new Node(0, data);
    }

    public FORMAT getKey() {
        return this.key;
    }


    public Node getValue() {
        return this.value;
    }

    @Override
    public String toString(){
        if(this.key != null)
            return String.format("%s (%s)",this.value.getKey(), this.key.name());
        else
            return this.value.getKey();
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
