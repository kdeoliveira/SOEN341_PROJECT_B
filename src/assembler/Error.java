package assembler;

import java.util.ArrayList;

public class Error<S extends Comparable<Integer>, V, T> {
    int lineNumber;
    ArrayList<S> position;
    ArrayList<V> value;
    ArrayList<T> type;

    /**
     * Incomplete
     * @param startLine
     */
    public Error(int startLine){
        this.lineNumber = startLine;
    }

    public void add(S position, V value, T type){
        this.position.add(position);
        this.value.add(value);
        this.type.add(type);
    }







}
