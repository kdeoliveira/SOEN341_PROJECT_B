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
            for(String[] x : file){
                dic.put(x[0], new BinaryAddress(x[1], false));
            }

            Engine eng = new Engine(dic, new Lexer(), new Parser());
            
            for(String[] x : src)
                if(!eng.assemble(x))
                    break;
            
            cma.printLines(eng);	
            /*System.out.println("#\tMemory Address\tMachine Code\tHex\tLabel\tMnemonic");
            for(int i = 0; i < eng.getAssemblerUnit().getNumberOfLines() ; i++){
            	System.out.println(eng.getAssemblerUnit().getLineStatements(i).getLineNumber()+"\t"+new BinaryAddress(i, false, 16).getHexCode()+"\t\t"+
                        eng.getAssemblerUnit().getLineStatements(i));
            }*/

            cma.printSymbols(eng);
            /*System.out.println("Label List #"+eng.getAssemblerUnit().sizeLabel());
            System.out.println("#\tMemory Address\tMachine Code\tLabel");
            for(int i = 0; i < eng.getAssemblerUnit().sizeLabel() ; i++){
                System.out.println(eng.getAssemblerUnit().getLabelNumber(i)+"\t"+eng.getAssemblerUnit().getLabel(i).getValue()+"\t"+
                eng.getAssemblerUnit().getLabel(i));
            }*/
            
            cma.printErrors(eng);
            /*System.out.println("Error "+eng.getAssemblerUnit().sizeError());
            for(CharSequence x : eng.getAssemblerUnit().getListofErrors()){
                System.out.println(x.toString());
            }*/
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }


        /**
         * Expected result of Demo.java:
#       Memory Address  Machine Code    Hex     Mnemonic
1       00000000        00000000        00      halt
2       00000001        00000001        01      pop
3       00000010        00000010        02      dup
4       00000011        00000011        03      exit
5       00000100        00000100        04      ret
6       00000101        00001100        0C      not Label
7       00000110        00001101        0D      and
Error 1
(Unknown:1) [ Label]
         */
    }
}