package assembler;


public class Error<S extends Comparable<Integer>, V, T> {
    private final int lineNumber;
    private S position;
    private V value;
    private T type;

    /**
     * Incomplete
     * @param startLine
     */
    public Error(int startLine, S position, V value, T type){
        this.lineNumber = startLine;
        this.position = position;
        this.value = value;
        this.type = type;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public V getValue() {
        return value;
    }

    public T getType() {
        return type;
    }

    public S getPosition() {
        return position;
    }

    public void set(S position, V value, T type){
        this.position = position;
        this.value = value;
        this.type = type;
    }

    public boolean contain(T type){
        return this.type == type;
    }

    public boolean equalsTo(Error<S, V, T> err){
        return (this.lineNumber == err.lineNumber) && (this.position == err.position);
    }







}
