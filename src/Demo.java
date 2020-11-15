import util.*;

import java.io.IOException;

import field.*;

public class Demo{

    public static void main(String[] args){
        TrinarySearchTree tst = new TrinarySearchTree();

        try(ReadLine file = new ReadLine("file.txt"))
        {
            for(String[] x : file){
                tst.put(x[0], new BinaryAddress(x[1], false) , x.length > 2 ? x[2] : null);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        System.out.println(tst.getAddress("halt").getHexCode());
        System.out.println(tst.getAddress("add").getHexCode());
        System.out.println(tst.getAddress("trap").getHexCode());
    }
}