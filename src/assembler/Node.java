package assembler;

import util.BinaryAddress;

public class Node implements Vertex<String>{
    private static final long serialVersionUID = 240542041950251807L;
    protected String key;
    protected transient BinaryAddress value;

    public Node(){}
    public Node(Node node){
        this.key = node.key;
        this.value = node.value;
    }
    public Node(BinaryAddress hex){
        this.key = "";
        this.value = hex != null ? hex : new BinaryAddress(0x0);
    }

    public Node(int hex, String name){
        this.key = name;
        this.value = new BinaryAddress(hex);
    }
    public Node(BinaryAddress hex, String name){
        this.key = name;
        this.value = hex != null ? hex : new BinaryAddress(0x0);
    }

    public String getKey() {
        return this.key;
    }

    public BinaryAddress getValue() {
        return this.value;
    }
    
    public void setValue(BinaryAddress value) {
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString(){
        return String.format("%s\t%s\t%s", this.value, value.getHexCode(), key);
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
