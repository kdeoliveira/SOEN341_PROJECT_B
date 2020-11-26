package assembler;

import java.util.*;

public class AssemblerUnit {
    private List<LineStatement> lines;
    private int numberOfLine;
    private List<Node> labels;
    private List<CharSequence> errorList;

    /**
     * Composite pattern for lineStatements and Errors
     */
    public AssemblerUnit(){
        this.lines = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.errorList = new ArrayList<>();
        this.numberOfLine = 0;
    }

    /**
     * Adds either a LineStatement, an Error or a Symbolic Label
     * @param object
     */
    public void add(Object object){
        if(object instanceof LineStatement){
            lines.add((LineStatement) object);
            numberOfLine++;
        }
        else if(object instanceof Node)
            labels.add((Node) object);
        else if(object instanceof CharSequence)
            errorList.add((CharSequence) object);
    }

    @SuppressWarnings("unchecked")
    public void addAll(List<?> object) {
        if(object instanceof LineStatement){
            lines.addAll((Collection<LineStatement>) object);
            numberOfLine+=object.size();
        }
        else if(object instanceof Node)
            labels.addAll((Collection<Node>) object);
        else if(object instanceof CharSequence)
            errorList.addAll((Collection<CharSequence>) object);
    }

    public LineStatement getLineStatements(int i){
        return lines.get(i);
    }

    public Node getLabel(int i){
        return labels.get(i);
    }

    public CharSequence getError(int i){
        return errorList.get(i);
    }

    public List<LineStatement> getListofLines(){
        return this.lines;
    }

    public List<Node> getListofLabels(){
        return this.labels;
    }

    public List<CharSequence> getListofErrors(){
        return this.errorList;
    }

    public int sizeLineStatement(){
        return lines.size();
    }

    public int sizeLabel(){
        return labels.size();
    }

    public int sizeError(){
        return errorList.size();
    }

    public int getNumberOfLines(){
        return this.numberOfLine;
    }
}
