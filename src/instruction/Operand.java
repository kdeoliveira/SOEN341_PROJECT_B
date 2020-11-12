package instruction;

import binaryutil.BinaryAddress;

public class Operand {
    protected BinaryAddress binary;

    public Operand(int hex){
        this.binary = new BinaryAddress(hex, false);
    }

    public BinaryAddress getBinary(){
        return this.binary;
    }


}
