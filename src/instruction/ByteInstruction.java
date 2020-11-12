package instruction;

import binaryutil.BinaryAddress;

public class ByteInstruction extends Instruction {
    
    public ByteInstruction(Opcode opcode, Operand operand){
        this.opcode = opcode;
        this.operand = operand;
        this.format = 16;
        this.instructionBinary = parseInstruction();
    }

    protected BinaryAddress parseInstruction(){
        return this.opcode.getBinary()
                               .concat(this.operand.getBinary());
    }
}
