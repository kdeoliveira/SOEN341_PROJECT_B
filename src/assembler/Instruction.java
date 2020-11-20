package assembler;

import util.BinaryAddress;

public class Instruction extends Node{
    /**
     *
     */
    private static final long serialVersionUID = -4060152889359052216L;
    private Node opcode;
    private Node operand;
    private String typeEBNF;


    //Inherent Instructions only
    public Instruction(BinaryAddress bin, String name, String type){
        super(bin, name);
        this.typeEBNF = type;
    }

    public void setOpcode(Node opcode) {
        this.opcode = opcode;
    }
    public void setOperand(Node operand) {
        this.operand = operand;
    }

    public String getTypeEBNF() {
        return typeEBNF;
    }

    public void setInstruction(Node opcode){
        // String temp;
        // if(opcode.getKey().contains(".")){
        //     temp = opcode.getKey().substring(opcode.length()-2, opcode.length());

        // }else{

        // }


    }

}
