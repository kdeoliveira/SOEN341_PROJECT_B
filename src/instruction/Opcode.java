package instruction;

import binaryutil.BinaryAddress;

public class Opcode{
    private BinaryAddress binary;
    private String mnemonic;
    private String format;

    public Opcode(String mnemonic){
        this.mnemonic = mnemonic;
        String[] temp = this.mnemonic.split("\\.", 2);
        if(temp.length > 1)     this.format = temp[1];
        else                    this.format = null;
    }
    public Opcode(int hex){
        this.binary = new BinaryAddress(hex, false);
        this.format = null;
    }
    public Opcode(String mnemonic, int hex){
        this.mnemonic = mnemonic;
        this.binary = new BinaryAddress(hex, false);
        String[] temp = this.mnemonic.split("\\.");
        if(temp.length > 1)     this.format = temp[1];
        else                    this.format = null;
    }

    public BinaryAddress getBinary(){
        return this.binary;
    }


    public String getFormat(){
        return this.format;
    }


    public static void main(String[] args){
        Opcode op = new Opcode("add.i3");

        System.out.println(op.getFormat());
    }

    
}