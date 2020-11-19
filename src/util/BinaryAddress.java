package util;

public class BinaryAddress {
    
    private int binaryAddress;
    private boolean signed = false;
    private int format;
    private char[] hexCode;

    /**
     * CONSTRUCTORS
     */

    public BinaryAddress(String hex, boolean signed){
        this.binaryAddress = Integer.decode(hex);
        this.signed = signed;
        this.hexCode = this.convertHex(this.binaryAddress);
        this.format = 8;
    }
    public BinaryAddress(int hex){
        this.binaryAddress = hex;        
        this.signed = false;
        this.hexCode = this.convertHex(this.binaryAddress);
        this.format = 8;
    }
    public BinaryAddress(int hex, boolean signed){
        this.binaryAddress = hex;        
        this.signed = signed;
        this.hexCode = this.convertHex(this.binaryAddress);
        this.format = 8;
    }
    public BinaryAddress(int hex, boolean signed, int range){
        if(hex > (int) Math.pow(2, range) - 1)     throw new OutOfMemoryError();
        this.binaryAddress = hex;        
        this.signed = signed;
        this.hexCode = this.convertHex(this.binaryAddress);
        this.format = range;
    }
    // public BinaryAddress(char[] hex, boolean signed){
    //     char[] tempHex = Arrays.copyOf(hex, hex.length);
    //     this.signed = signed;
    //     this.hexCode = this.convertHex(this.binaryAddress);
    // }

    

    /**
     * GETTERS AND SETTERS
     */
    public String getHexCode(){
        return hexCode != null ? this.hexFormat(hexCode) : null;
    }

    public int getBinaryAddress(){
        return this.binaryAddress;
    }

    public String getBinaryAddressString(){
        return toBinaryFormat(this.binaryAddress);
    }

    public boolean isSigned(){
        return this.signed;
    }

    private char[] convertHex(int binary){
        return Integer.toHexString(binary).toCharArray();
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    /**
     * Print hex format with leading 0 
     * @param character
     * @return
     */
    private String hexFormat(char...character){
        StringBuilder str = new StringBuilder();
        
        if(character.length == 0)   return str.toString();

        int bits = 0;
        if(character.length < this.format/4)
            bits = (this.format/4) - character.length;

        for(int i=0; i < bits; i++)
            str.insert(0,"0");

        for(char x : character){
            str.append(x);
        }
        return str.toString().toUpperCase();
    }

    private String toBinaryFormat(int binary){
        StringBuilder str = new StringBuilder(Integer.toBinaryString(binary));


        int bits = 0;
        if(str.length() < this.format)
            bits = this.format - str.length();
        for(int i=0; i < bits; i++)
            str.insert(0,"0");
        return str.toString();
    }

    

    @Override
    public String toString(){
        return toBinaryFormat(this.binaryAddress);
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

    /**
 * Returns unisgned byte
 * @param x
 */
    public void plus(int x){

        if(this.binaryAddress + x > (int) Math.pow(2, this.format) - 1) return;

        this.binaryAddress+=x;
        this.hexCode = this.convertHex(this.binaryAddress);
    }

    public BinaryAddress concat(BinaryAddress x){
        return new BinaryAddress(((this.binaryAddress << 4) + (x.binaryAddress & 0xff)), false, 16);
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
