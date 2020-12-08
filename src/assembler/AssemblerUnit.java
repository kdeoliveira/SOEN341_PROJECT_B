package assembler;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

import assembler.tokenization.EBNF;
import util.BinaryAddress;

public class AssemblerUnit {
    private List<LineStatement> lines;
    private int numberOfLine;
    private List<Node> labels;
    private List<Integer> labelsNumberOfLine;
    private List<CharSequence> errorList;
    private static final int MAXBITS = 32;

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

    public void printLineStatements(){
        System.out.println("#  |\tMemory Address\tMachine Code\tLabel\tMnemonic\tOperand |");
        System.out.println("-------------------------------------------------------------------------");
        for (int i = 0; i < this.numberOfLine; i++) {
            System.out.println(
                this.getLineStatements(i).getLineNumber()
                    + (this.getLineStatements(i).getLineNumber() > 9 ? " |" : "  |") + "\t"
                    + new BinaryAddress((long) i * 2, false, 16).getHexCode() + "\t\t"
                    + this.getLineStatements(i));
        }
    }

    public void printSymbolicLabels(){
        System.out.println("#\tHex\tLabel");
        System.out.println("----------------------");
        for (int i = 0; i < this.sizeLabel(); i++) {
            System.out.println(this.getLabelNumber(i) + "\t" + this.getLabel(i));
        }
    }

    public void printErrors(){
        for (CharSequence x : this.getListofErrors()) {
            System.out.println(x.toString());
        }
    }

    public void exportLineStatements(PrintStream file){
        System.setOut(file);
        this.printLineStatements();
        System.setOut(System.out);
    }

    public void exportSymbolicLabels(PrintStream file){
        System.setOut(file);
        this.printSymbolicLabels();
        System.setOut(System.out);
    }
    public void exportErrors(PrintStream file){
        System.setOut(file);
        this.printErrors();
        System.setOut(System.out);
    }
    public void exportBinaryCode(PrintStream file){
        StringBuilder str = new StringBuilder();

            for (int i = 0; i < this.sizeLineStatement(); i++) {

                for( int j = 0; j < this.getLineStatements(i).getMachineCode().hexCodeToArray().length ; j++){
                    str.append(
                        this.getLineStatements(i).getMachineCode().hexCodeToArray()[j]
                    );

                    if(str.length() == 2){

                        file.write(
                        Integer.parseInt( str.toString(), 16 )
                        );
                        str.delete(0, str.length());
                    }
                    else if( str.length() < 2 && j == this.getLineStatements(i).getMachineCode().hexCodeToArray().length - 1 ){
                        str.append('0');


                        file.write(
                            Integer.parseInt( str.toString(), 16 )
                            );
                            str.delete(0, str.length());
                    }
                }
            }
    }
}