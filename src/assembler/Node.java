package assembler;

import util.BinaryAddress;

public class Node {
    private BinaryAddress machineCode;
    private String name;

    public Node(int hex, String name){
        this.machineCode = new BinaryAddress(hex);
        this.name = name;
    }
    public Node(BinaryAddress hex, String name){
        this.machineCode = hex;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BinaryAddress getMachineCode() {
        return machineCode;
    }

    @Override
    public String toString(){
        return String.format("%s\t%s\t%s", machineCode, machineCode.getHexCode(), name);
    }
}
