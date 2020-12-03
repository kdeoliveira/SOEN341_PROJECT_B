// cma.java - (c) 2020 by Team B6: Cm assembler

import java.io.*;
import assembler.*;
import assembler.tokenization.*;
import java.util.*;

import admin.*;
import util.*;

public class cma {

    public static void printLines(Engine eng){
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("#  |\tMemory Address\tMachine Code\tLabel\tMnemonic\tOperand |");
        System.out.println("-------------------------------------------------------------------------");
        for(int i = 0; i < eng.getAssemblerUnit().getNumberOfLines() ; i++){
            System.out.println(eng.getAssemblerUnit().getLineStatements(i).getLineNumber()+(eng.getAssemblerUnit().getLineStatements(i).getLineNumber() > 9 ? " |": "  |")+
            "\t"+new BinaryAddress(i, false, 16).getHexCode()+"\t\t"+
            eng.getAssemblerUnit().getLineStatements(i)
            );
        }
        System.out.println("-------------------------------------------------------------------------");
    }
    public static void printSymbols(Engine eng){
        System.out.println("Label List #"+eng.getAssemblerUnit().sizeLabel());
        System.out.println("#\tMemory Address\tMachine Code\tLabel");
        for(int i = 0; i < eng.getAssemblerUnit().sizeLabel() ; i++){
            System.out.println(eng.getAssemblerUnit().getLabelNumber(i)+"\t"+
            eng.getAssemblerUnit().getLabel(i));
        }
    }
    public static void printErrors(Engine eng){
        System.out.println("Error "+eng.getAssemblerUnit().sizeError());
        for(CharSequence x : eng.getAssemblerUnit().getListofErrors()){
            System.out.println(x.toString());
        }
    }
    public static void printBinaryCode(Engine eng, PrintStream file){
        for(int i = 0; i < eng.getAssemblerUnit().sizeLineStatement() ; i++){
            System.out.print(eng.getAssemblerUnit().getLineStatements(i).getMachineCode().getHexCode());
            file.write((int)eng.getAssemblerUnit().getLineStatements(i).getMachineCode().getBinaryAddress());
        }
        System.out.println();
    }    
    public static void main(String[] args) throws IOException {
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
        	
        	SourceFile srcFile = new SourceFile(filename);
        	String srcListingFile = srcFile.getName()+".lst";
        	String executableFile = srcFile.getName()+".exe";
        	
            File file = new File(filename);
            if ( !file.canRead() ) {
                admin.outputln("cma: Can't open file '" + filename + "'");
                continue;
            }
            if (verbose) admin.outputln("cma: Opening '" + filename + "'");
            
            // admin.output(filename + ": ");

            try(ReadLine dictFile = new ReadLine("dictionary",3);
            ReadLine src = new ReadLine(file,4);
            PrintStream stream = new PrintStream(new File(srcListingFile));
            PrintStream symbols = new PrintStream(new File("symbols.lst"));
            PrintStream errors = new PrintStream(new File("errors.lst"));
            PrintStream executable = new PrintStream(new File(executableFile))
            )
            {
                for(String[] x : dictFile){
                    dic.put(x[0], new BinaryAddress(x[1], false));
                }
    
                Engine eng = new Engine(dic, new Lexer(dic), new Parser());

                

                
                for(String[] x : src){
                    if(!eng.assemble(x))
                        break;
                }
                eng.pass2();

                

                printBinaryCode(eng, executable);
                
                
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

    
