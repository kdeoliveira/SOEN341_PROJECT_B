package util;
import java.util.Arrays;

public class BinaryAddress {
    
    private int binaryAddress;
    private boolean signed = false;
    private char[] hexCode;

    /**
     * CONSTRUCTORS
     */

    public BinaryAddress(String hex, boolean signed){
        this.binaryAddress = Integer.decode(hex);
        this.signed = signed;
        this.hexCode = new char[Integer.toHexString(this.binaryAddress).length()];
        this.hexCode = this.convertHex(this.binaryAddress);
    }
    public BinaryAddress(int hex, boolean signed){
        this.binaryAddress = hex;        
        this.signed = signed;
        this.hexCode = new char[Integer.toHexString(this.binaryAddress).length()];
        this.hexCode = this.convertHex(this.binaryAddress);
    }
    public BinaryAddress(int hex, boolean signed, int range){
        if(hex > (int) Math.pow(2, range) - 1)     throw new OutOfMemoryError();
        this.binaryAddress = hex;        
        this.signed = signed;
        this.hexCode = new char[Integer.toHexString(this.binaryAddress).length()];
        this.hexCode = this.convertHex(this.binaryAddress);
    }
    public BinaryAddress(char[] hex, boolean signed){
        char[] tempHex = Arrays.copyOf(hex, hex.length);
        this.signed = signed;
        this.binaryAddress = (byte) ((Character.digit(tempHex[0],16) << 4) + (Character.digit(tempHex[1], 16)));
        this.hexCode = this.convertHex(this.binaryAddress);
    }

    

    /**
     * GETTERS AND SETTERS
     */
    public String getHexCode(){
        return hexCode != null ? Arrays.toString(hexCode) : null;
    }

    public int getBinaryAddress(){
        return this.binaryAddress;
    }

    public boolean isSigned(){
        return this.signed;
    }

    private char[] convertHex(int binary){
        return Integer.toHexString(binary).toCharArray();
    }
    

    @Override
    public String toString(){
        return Integer.toBinaryString(this.binaryAddress);
    }


    /**
     * Returns unsigned byte
     * @param x
     * @return
     */
    public BinaryAddress add(BinaryAddress x){
        if(x == null)   return this;

        int sum;
        if(!x.isSigned()){
            
            sum = this.binaryAddress + x.binaryAddress;
            return new BinaryAddress(sum, this.signed);
        }
        else{
            if(x.binaryAddress >> 3 == 0){
                sum = this.binaryAddress + (x.binaryAddress & 0x7);
            }
            else{
                sum = this.binaryAddress - (x.binaryAddress & 0x7);
            }
        }

        return new BinaryAddress(sum, this.signed);
        
    }

    /**
     * Returns unisgned byte
     * @param x
     */
    public void plus(BinaryAddress x){
        if(x == null)   return;

        if(!x.isSigned()){
            this.binaryAddress += x.binaryAddress;
        }
        else{
            if(x.binaryAddress >> 3 == 0){
                this.binaryAddress += (x.binaryAddress & 0x7);
            }
            else{
                this.binaryAddress -= (x.binaryAddress & 0x7);
            }
        }
        this.hexCode = this.convertHex(this.binaryAddress);
    }

    public BinaryAddress concat(BinaryAddress x){
        return new BinaryAddress(((this.binaryAddress << 4) + (x.binaryAddress & 0xff)), false);
    }



    public static void main(String[] args){
        BinaryAddress mem = new BinaryAddress("0xBF", false);
        BinaryAddress mem1 = new BinaryAddress(0x01, false);

        System.out.println(mem +"\t" + mem.getHexCode());
        System.out.println(mem1 +"\t" + mem1.getHexCode());
        System.out.println();
        mem.plus(mem1);
        System.out.println("After sum: "+mem +"\t" + mem.getHexCode());

        System.out.println();

        System.out.println("After add" + mem.add(mem1) +"\n" + mem.add(mem1).getHexCode());
        
        System.out.println();

        // -------------
        System.out.println("Concat:");
        System.out.println(mem.concat(mem1)+"\t"+mem1.concat(mem1).getHexCode());

    }

}
