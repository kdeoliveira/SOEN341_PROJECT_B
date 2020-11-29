package assembler;

import util.BinaryAddress;

public class Operand extends Node{

    /**
     *
     */
    private static final long serialVersionUID = -8355252271095410258L;
    public Operand(BinaryAddress bin, String name){
        super(bin,name);
    }
	public Operand(Integer valueOf, String strip) {
        super(valueOf,strip);
	}
}
