// cma.java - (c) 2020 by Team B6: Cm assembler

import java.io.*;
import assembler.*;
import assembler.tokenization.*;
import java.util.*;

import admin.*;
import util.*;

public class cma {

    public static void printLines(Engine eng){
        System.out.println("#\tMemory Address\tMachine Code\tHex\tMnemonic");
        for(int i = 0; i < eng.getNumberOfLine() ; i++){
            System.out.println(eng.getLines().get(i).getLineNumber()+"\t"+new BinaryAddress(i)+"\t"+
            eng.getLines().get(i));
        }
    }
    public static void printSymbols(Engine eng){
        System.out.println("Label List #"+eng.getLabels().size());
        System.out.println("#\tMemory Address\tMachine Code\tHex\tLabel");
        for(int i = 0; i < eng.getLabels().size() ; i++){
            System.out.println(i+1+"\t"+eng.getLabels().get(i).getValue()+"\t"+
            eng.getLabels().get(i));
        }
    }
    public static void printErrors(Engine eng){
        System.out.println("Error "+eng.getErrorList().size());
        for(CharSequence x : eng.getErrorList()){
            System.out.println(x.toString());
        }
    }
    public static void printBinaryCode(Engine eng){
        System.out.println("Binary Code");
        for(int i = 0; i < eng.getNumberOfLine() ; i++){
            System.out.print(eng.getLines().get(i).getMachineCode());
        }
        System.out.println();
    }

    public static void main(String args[]) throws IOException {
        IListing       cmaListing  = cmaFactory.makeListing();
        IAdministrator admin       = cmaFactory.makeAdmin(args);

        // Parsing options and arguments.
        admin.administer();

        Map<String,BinaryAddress> dic = new BinarySearchTree<>();

        IOptionTable options = admin.getOptions();

        // Checking valid options have been enabled.
        if (options.get("help").isEnabled()) {
            admin.usage();
        } else if (options.get("listing").isEnabled()) {
            admin.outputln(cmaListing.toString());
        }
        boolean verbose = options.get("verbose").isEnabled();

        // Executing.
        ArrayList<String> files = admin.getArguments();
        for (String filename : files) {

            File file = new File(filename);
            if ( !file.canRead() ) {
                admin.outputln("cma: Can't open file '" + filename + "'");
                continue;
            }
            if (verbose) admin.outputln("cma: Opening '" + filename + "'");
            
            // admin.output(filename + ": ");

            try(ReadLine dictFile = new ReadLine("dictionary",3);
            ReadLine src = new ReadLine(file,4);
            PrintStream stream = new PrintStream(new File("output.lst"));
            PrintStream symbols = new PrintStream(new File("symbols.lst"));
            PrintStream errors = new PrintStream(new File("errors.lst"))
            )
            {
                for(String[] x : dictFile){
                    dic.put(x[0], new BinaryAddress(x[1], false));
                }
    
                Engine eng = new Engine(dic, new Lexer(), new Parser());


                
                for(String[] x : src){
                    if(!eng.assemble(x))
                        break;
                }

                printBinaryCode(eng);
                System.out.println();
                if (verbose){
                    System.setOut(System.out);
                    printLines(eng);
                    printSymbols(eng);
                    printErrors(eng);
                    admin.outputln("cma: Closing '" + filename + "'");  
                }
                
                System.setOut(stream);
                printLines(eng);
                
                System.setOut(symbols);
                printSymbols(eng);

                System.setOut(errors);            
                printErrors(eng);
                
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            
            
        }            
        admin.outputln("");
    }
}

    
