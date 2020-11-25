package assembler;

import assembler.tokenization.EBNF;
import util.BinaryAddress;

public class Instruction extends Node{

    private static final long serialVersionUID = -4060152889359052216L;
    private Node opcode;
    private Node operand;
    private String typeEBNF;

    public Instruction(Instruction inst){
        this.opcode = inst.opcode;
        this.operand = inst.operand;
        this.typeEBNF = inst.typeEBNF;
    }

    //Inherent Instructions only
    public Instruction(BinaryAddress bin, String name, String type){
        super(bin, name);
        this.opcode = new Node(bin, name);
        this.typeEBNF = type;
    }

    public Instruction(Node opcode, Node operand, String type){

        this.opcode = opcode;

        this.setOperand(operand);

        this.typeEBNF = type;
        
    }

    public void setOpcode(Node opcode) {
        this.opcode = opcode;
        
    }
    /**
     * Sets an operand in a instruction line. If operand is immediate or relative, perform operation on binary value
     * Note: BinaryAddress.java allows binary operations such as concat and addition between binary numbers. Refer to BinaryAddress for more information.
     * @param operand
     */
    public void setOperand(Node operand) {
        this.operand = operand;
        if(opcode.getKey().contains(".")){
            String[] temp = opcode.getKey().split("\\.");
            this.operand.getValue().setFormat(Integer.parseInt( String.valueOf(temp[1].charAt(1)) ));
            this.operand.getValue().setSigned(String.valueOf(temp[1].charAt(0)));
        }
        if(this.typeEBNF.equals(EBNF.RELATIVE.name()) || this.typeEBNF.equals(EBNF.RELATIVE1.name()) || this.typeEBNF.equals(EBNF.RELATIVE2.name()))
            this.value = this.value.concat(operand.getValue());
    
        else
            this.value = opcode.getValue().add(operand.getValue());
    }

    public String getTypeEBNF() {
        return typeEBNF;
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.opcode != null ? this.opcode.getKey() : "", this.operand != null ? this.operand.getKey() : "");
    }

    public void setInstruction(Node opcode){
        // String temp;
        // if(opcode.getKey().contains(".")){
        //     temp = opcode.getKey().substring(opcode.length()-2, opcode.length());

        // }else{

        // }


    }

}
