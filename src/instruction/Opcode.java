package instruction;

import binaryutil.BinaryAddress;

public class Opcode{
    private BinaryAddress binary;
    private String mnemonic;

    public Opcode(String mnemonic){
        this.mnemonic = mnemonic;
    }
    public Opcode(int hex){
        this.binary = new BinaryAddress(hex, false);
    }
    public Opcode(String mnemonic, int hex){
        this.mnemonic = mnemonic;
        this.binary = new BinaryAddress(hex, false);
    }

    public BinaryAddress getBinary(){
        return this.binary;
    }


    public static void main(String[] args){
        Opcode op = new Opcode(0x4F);

        System.out.println(op.getBinary());
    }

    
}