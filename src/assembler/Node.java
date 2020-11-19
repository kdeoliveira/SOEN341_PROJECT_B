package assembler;

import util.BinaryAddress;

public class Node implements Vertex<String>{
    private static final long serialVersionUID = 240542041950251807L;
    private String key;
    private transient BinaryAddress value;

    public Node(int hex, String name){
        this.value = new BinaryAddress(hex);
        this.key = name;
    }
    public Node(BinaryAddress hex, String name){
        this.key = name;
        this.value = hex;
    }

    public String getKey() {
        return this.key;
    }

    public BinaryAddress getValue() {
        return this.value;
    }

    @Override
    public String toString(){
        return String.format("%s\t%s\t%s", value, value.getHexCode(), key);
    }

    @Override
    public char charAt(int arg0) {
        return this.key.charAt(arg0);
    }

    @Override
    public int length() {
        return this.key.length();
    }

    @Override
    public CharSequence subSequence(int arg0, int arg1) {
        return this.key.subSequence(arg0, arg1);
    }
}
