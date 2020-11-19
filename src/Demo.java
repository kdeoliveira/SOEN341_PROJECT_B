import util.*;
import assembler.*;
import assembler.tokenization.*;

import java.io.IOException;
import java.util.Map;

public class Demo{

    public static void main(String[] args) {
        Map<String,BinaryAddress> dic = new BinarySearchTree<>();

        try(ReadLine file = new ReadLine("dictionary",3);
        ReadLine src = new ReadLine("input.asm",4))
        {

            src.setRegex("(?<=\\s\\S+)\\s");

            for(String[] x : file){
                dic.put(x[0], new BinaryAddress(x[1], false));
            }

            Engine eng = new Engine(dic, new Lexer(), new Parser());
            
            for(String[] x : src){
                if(!eng.assemble(x))
                    break;
            }

            System.out.println("#\tMemory Address\tMachine Code\tHex\tMnemonic");

            for(int i = 0; i < eng.getNumberOfLine() ; i++){
                System.out.println((i+1)+"\t"+new BinaryAddress(i)+"\t"+
                eng.getLinestatement().get(i));
            }
                        
            System.out.println("Error "+eng.getErrorList().size());
            for(CharSequence x : eng.getErrorList()){
                System.out.println(x.toString());
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}