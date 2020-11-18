import util.*;
import assembler.*;
import java.io.IOException;

public class Demo{

    public static void main(String[] args) {
        TrinarySearchTree<String, String> tst = new TrinarySearchTree<>();

        try(ReadLine file = new ReadLine("file.txt",3);
        ReadLine src = new ReadLine("input.asm",4))
        {
            for(String[] x : file){
                tst.put(x[0], new BinaryAddress(x[1], false) , x.length > 2 ? x[2] : null);
            }

            Engine eng = new Engine(tst, src);
            eng.line();

            for(Node x : eng.getLinestatement())
                System.out.println(x);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}