package assembler;

import java.util.*;

import assembler.tokenization.EBNF;

public class AssemblerUnit {
    private List<LineStatement> lines;
    private int numberOfLine;
    private List<Node> labels;
    private List<Integer> labelsNumberOfLine;
    private List<CharSequence> errorList;

    /**
     * Composite pattern for lineStatements and Errors
     */
    public AssemblerUnit(){
        this.lines = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.errorList = new ArrayList<>();
        this.labelsNumberOfLine = new ArrayList<>();
        this.numberOfLine = 0;
    }

    /**
     * Adds either a LineStatement, an Error or a Symbolic Label
     * @param object
     */
    public void add(Object object){
        if(object instanceof LineStatement){
            if(!((LineStatement) object).getTypeEBNF().equals(EBNF.COMMENT.name())){
                //Fix number of line in LineStatement
                lines.add((LineStatement) object);
                numberOfLine++;
            }
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

    public void addLabelNumber(int i){
        this.labelsNumberOfLine.add(i);
    }
    public int getLabelNumber(int i){
        return this.labelsNumberOfLine.get(i);
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
