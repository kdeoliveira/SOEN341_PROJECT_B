// cma.java - (c) 2020 by Team B6: Cm assembler

import java.io.*;

import assembler.*;
import assembler.tokenization.*;
import java.util.*;

import admin.*;
import util.*;

public class cma {  
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
        }

        
        
        boolean verbose = options.get("verbose").isEnabled();


        if(verbose || options.get("listing").isEnabled())
            admin.logo();

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
                    if(!eng.assemble(x)){
                        if(verbose)
                            eng.getAssemblerUnit().printErrors();
                        break;
                    }else
                        if(verbose)
                            admin.output(".");
                }


                if(verbose)
                    admin.outputln("\nFirst pass completed");
                
                if(eng.pass2() && verbose)
                    admin.outputln("Second pass completed");
                

                if (options.get("listing").isEnabled()) {
                    admin.outputln("Line Statemnts: ");
                    eng.getAssemblerUnit().printLineStatements();
                    admin.outputln("Symbolic Labels: ");
                    eng.getAssemblerUnit().printSymbolicLabels();
                    admin.outputln("Errors: ");
                    eng.getAssemblerUnit().printErrors();
                }

                if(verbose){
                    admin.outputln("cma: Closing '" + filename + "'");
                }


                eng.getAssemblerUnit().exportBinaryCode(executable);
                eng.getAssemblerUnit().exportLineStatements(stream);
                eng.getAssemblerUnit().exportErrors(errors);
                eng.getAssemblerUnit().exportSymbolicLabels(symbols);   
                

            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            
            
        }            
        // admin.outputln("");
    }
}

    
