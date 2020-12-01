package util;

public class BinaryAddress {
    
    private long binaryAddress;
    private boolean signed = true;
    private int format;
    private char[] hexCode;

    /**
     * CONSTRUCTORS
     */

    public BinaryAddress(BinaryAddress bin){
        this.binaryAddress = bin.binaryAddress;
        this.signed = bin.signed;
        this.format = bin.format;
        this.hexCode = bin.hexCode;
    }

    public BinaryAddress(String hex, boolean signed){
        this.binaryAddress = Integer.decode(hex);
        this.signed = signed;
        this.hexCode = this.convertHex(this.binaryAddress);
        this.format = 8;
        this.checkSignedBinary();
    }
    public BinaryAddress(long hex){
        this.binaryAddress = hex;        
        this.signed = true;
        this.hexCode = this.convertHex(this.binaryAddress);
        //max bits a node can have
        this.format = 32;
        this.checkSignedBinary();
    }
    public BinaryAddress(long hex, boolean signed){
        this.binaryAddress = hex;        
        this.signed = signed;
        this.hexCode = this.convertHex(this.binaryAddress);
        this.format = 8;
        this.checkSignedBinary();
    }
    public BinaryAddress(long hex, int range){
        this.binaryAddress = hex;        
        this.signed = false;
        this.hexCode = this.convertHex(this.binaryAddress);
        this.format = range;
        this.checkSignedBinary();
    }
    public BinaryAddress(long hex, boolean signed, int range){
        //CHeck for negatives as well
        this.binaryAddress = hex;        
        this.signed = signed;
        this.hexCode = this.convertHex(this.binaryAddress);
        this.format = range;
        this.checkSignedBinary();
    }

    

    /**
     * GETTERS AND SETTERS
     */
    public String getHexCode(){
        return hexCode != null ? this.hexFormat(hexCode) : null;
    }

    public long getBinaryAddress(){
        return this.binaryAddress;
    }

    public String getBinaryAddressString(){
        return toBinaryFormat(this.binaryAddress);
    }

    public int getFormat() {
        return format;
    }

    public boolean isSigned(){
        return this.signed;
    }

    private char[] convertHex(long binary){
        return Long.toHexString(binary).toCharArray();
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
        this.checkSignedBinary();
    }
    public void setSigned(String signed) {
        if(signed.equals("u"))
            this.signed = false;
        else if(signed.equals("i"))
            this.signed = true;
        this.checkSignedBinary();
    }

    public void setFormat(int format){
        this.format = format;
        this.checkSignedBinary();
    }

    /**
     * Verifies if binary number is in range
     * Depends on boolean signed and integer format
     */
    private void checkSignedBinary(){
        if(this.signed){
            int lowBound = (int) (Math.pow(2, this.format)/2 ) * -1;
            int upperBound = (int) (Math.pow(2, this.format)/2 ) - 1;
            if(!(this.binaryAddress <= upperBound && this.binaryAddress >= lowBound))
                throw new UnsupportedOperationException();
        }else
            if(this.binaryAddress < 0 || this.binaryAddress > (long) Math.pow(2, this.format) - 1){
                System.out.println("BinaryAddress.java: "+binaryAddress);
                throw new UnsupportedOperationException();
            }
            
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

    private String toBinaryFormat(long binary){
        StringBuilder str = new StringBuilder(Long.toBinaryString(binary));


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
        
        if(x.signed){
            return new BinaryAddress( (binaryAddress) + (x.binaryAddress & (int) Math.pow(2, x.format) - 1) , false, this.format);
        }
        return new BinaryAddress(this.binaryAddress + x.binaryAddress, false, Integer.max(format, x.format));
        
    }

    /**
     * Returns unisgned byte
     * @param x
     */
    public void plus(BinaryAddress x){
        if(x == null)   return;

        if(x.signed){
            this.binaryAddress = (binaryAddress) + (x.binaryAddress & (int) Math.pow(2, x.format) - 1);
            signed = false;
            this.hexCode = this.convertHex(this.binaryAddress);
        }
        else{
            this.binaryAddress += x.binaryAddress;
            format = Integer.max(format, x.format);
            this.hexCode = this.convertHex(this.binaryAddress);
        }
    }

    /**
 * Returns unsigned byte
 * @param x
 */
    public void plus(int x){

        if(this.binaryAddress + x > (int) Math.pow(2, this.format) - 1) return;

        this.binaryAddress+=x;
        this.hexCode = this.convertHex(this.binaryAddress);
    }

    public BinaryAddress concat(BinaryAddress x){
        // System.out.println("--->"+Integer.toHexString(binaryAddress)+"+"+x.binaryAddress+"\t"+format+"+"+x.format);
        // System.out.println("Result--->"+Integer.toHexString((this.binaryAddress << x.format) + (x.binaryAddress & 0xff)));
        return new BinaryAddress(((this.binaryAddress << x.format) + (x.binaryAddress & (int) Math.pow(2, x.format) - 1)), false, (this.format+x.format));
    }

    public static BinaryAddress toBinary(String str){
        long result = 0;
        int cnt = 1;
        for(char x : str.toCharArray()){
            if(x != '"'){
                
                result += (int) x;
                
                result = result << 8;
                cnt++;
            }
        }
        return new BinaryAddress(result,false, cnt*8);
    }



    public static void main(String[] args){
        BinaryAddress mem = new BinaryAddress("0xBF", false);
        BinaryAddress mem1 = new BinaryAddress(-4, true, 3);

        System.out.println(BinaryAddress.toBinary("Kevin").getHexCode());



        // System.out.println(mem +"\t" + mem.getHexCode());
        // System.out.println(mem1 +"\t" + mem1.getHexCode());
        // System.out.println(mem.concat(mem1).getHexCode());
        // System.out.println();
        // mem.plus(mem1);
        // System.out.println("After plus: "+mem +"\t" + mem.getHexCode());

        // System.out.println();

        // System.out.println("After add" + mem.add(mem1) +"\n" + mem.add(mem1).getHexCode());
        
        // System.out.println();


    }

}
