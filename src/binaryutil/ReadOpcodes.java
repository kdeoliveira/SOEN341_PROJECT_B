package binaryutil;

import java.io.FileInputStream;

public class ReadOpcodes {
    public static void main(String[] args){
        TripleSearchTree inst = new TripleSearchTree();
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
                        }
                        else if(counter == 1)
                        {
                            value = new BinaryAddress(word.toString(), false);
                        }
                        else if(counter == 2)
                        {
                            comment = word.toString();
                        }
                        counter++;
                        word.delete(0, word.length());
                    }
                    else{
                        word.append(ch);
                    }
                }
                else
                {
                    counter = 0;
                    inst.put(key, value, comment);
                    word.delete(0, word.length());
                }

                if(src.available() == 0)
                {
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
