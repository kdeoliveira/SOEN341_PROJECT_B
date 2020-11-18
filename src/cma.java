// cma.java - (c) 2020 by Team B6: Cm assembler

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import admin.*;
import util.*;

public class cma {
    public static void main(String args[]) throws IOException {
        IListing       cmaListing  = cmaFactory.makeListing();
        IAdministrator admin       = cmaFactory.makeAdmin(args);

        // Parsing options and arguments.
        admin.administer();

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
            
            admin.output(filename + ": ");
            
            try(ReadLine rl = new ReadLine(filename, 3)){

                for(String[] x : rl){
                    System.out.println(Arrays.toString(x));
                }            
            } catch (IOException e) {
            	e.printStackTrace();
            }
            
            if (verbose) admin.outputln("cma: Closing '" + filename + "'");
        }            
        admin.outputln("");
    }
}

    
