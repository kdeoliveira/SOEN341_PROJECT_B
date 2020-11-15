package field;

public class Address extends Operand{
    
    public Address(){
        
        //Unsigned value
        this.machineCode = (byte) (this.machineCode & 0xFF);
    }
}
