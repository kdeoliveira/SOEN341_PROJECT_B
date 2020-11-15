package instruction;

import util.BinaryAddress;

public class InheritInstruction extends Instruction {
   
    //Inherent Addressing
    public InheritInstruction(Opcode opcode){
        this.opcode = opcode;
        this.operand = null;
        this.format = 8;
        this.instructionBinary = parseInstruction();
    }
    //Immediate Addressing
    public InheritInstruction(Opcode opcode, Operand operand){
        this.opcode = opcode;
        this.operand = operand;
        this.format = 8;
        this.instructionBinary = parseInstruction();
    }

    protected BinaryAddress parseInstruction(){
        return this.opcode.getBinary()
                               .add(this.operand.getBinary());
    }

}
