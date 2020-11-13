package binaryutil;

import java.io.FileInputStream;

public class ReadOpcodes {
    public static void main(String[] args){
        TrinarySearchTree inst = new TrinarySearchTree();
        if(args.length == 0)    return;
        try(FileInputStream src = new FileInputStream(args[0])){
            int c;
            int counter = 0;
            StringBuilder word = new StringBuilder();
            String key = "";
            String comment = "";
            BinaryAddress value = null;

            while(src.available() > 0)
            {
                char ch = (char) src.read();

                if(ch != '\n')
                {
                    if(ch == ' '){
                        
                        if(counter == 0)
                        {
                            key = word.toString();
                            counter++;
                            word.delete(0, word.length());
                        }
                        else if(counter == 1)
                        {
                            value = new BinaryAddress(word.toString(), false);
                            counter++;
                            word.delete(0, word.length());
                        }

                        if(counter == 2){
                            word.append(ch);
                        }
                        
                    }
                    else{
                        word.append(ch);
                    }
                }
                else
                {
                    if(counter == 2)
                        {
                            comment = word.toString().stripLeading();
                        }
                    counter = 0;
                    inst.put(key, value, comment);
                    word.delete(0, word.length());
                }

                if(src.available() == 0)
                {
                    comment = word.toString().stripLeading();

                    inst.put(key, value, comment);
                    word.delete(0, word.length());
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }

        System.out.println(
            inst.getComment("add")
        );
    }
}
