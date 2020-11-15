import java.math.BigInteger;
import java.util.Arrays;

public class Test {
    public static void main(String[] args){


        int y = 0x44;
        String hex = "0xAA";

        System.out.println(Integer.toHexString(y).length());


        


        System.out.println(Arrays.toString(hex.getBytes()));

        System.out.println(0xAA);

        System.out.println(Integer.toHexString(0xAA));

        System.out.println(Integer.toBinaryString(0xAA));

        // System.out.println(Integer.toBinaryString(y & 0x7));

    }
}
