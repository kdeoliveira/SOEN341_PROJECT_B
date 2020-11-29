package assembler;

public interface Vertex<K> extends CharSequence, java.io.Serializable {
    public K getKey();
}
