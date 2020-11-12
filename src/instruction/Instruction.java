package instruction;

import binaryutil.BinaryAddress;

public abstract class Instruction {
    protected Opcode opcode;
    protected Operand operand;
    protected int format;
    protected BinaryAddress instructionBinary;

    protected abstract BinaryAddress parseInstruction();

    public int getFormat(){
        return this.format;
    }

    public String getHexCode(){
        return instructionBinary.getHexCode();
    }

    @Override
    public String toString(){
        return instructionBinary.toString();
    }

    public static void main(String[] args){
        Opcode opcode = new Opcode(0xA0);
        Operand operand = new Operand(0x1);

        System.out.println(opcode.getBinary());
        System.out.println(operand.getBinary());

        Instruction inst = new InheritInstruction(opcode, operand);

        System.out.println(inst.getHexCode());
    }
}
