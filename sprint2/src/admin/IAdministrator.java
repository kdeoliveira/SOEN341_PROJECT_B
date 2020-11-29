package admin;
//IAdministrator.java - (c) 2020 by Team B6

/**
The Administrator class is only responsible for parsing options
and arguments on the command line. The main method is expected
to create an object from that class in order to retrieve:
- valid options that have been enabled (if any); and
- arguments (if any)
*/

import java.io.*;
import java.util.ArrayList; // import the ArrayList class

public interface IAdministrator {
 void               administer();

 void output(String msg);
 void outputln(String msg);
 void error(String msg);
 void usage();

 IOptionTable       getOptions();
 ArrayList<String>  getArguments();
}

class Administrator implements IAdministrator {
 private String       args[];
 private String       usage;
 private IOptionTable options;
 private ArrayList<String>  arguments;

 /** The stream where error message are printed. */
 private OutputStream  out;

 public Administrator(String args[], String usage, IOptionTable options) {
     this.args = args;
     this.usage = usage;
     this.options = options;
     this.arguments = new ArrayList<String>();
     this.out = System.out;
 }
 public void administer() {
     parse();
//Potential template method here:
//   parseOptions();
//   parseArguments();
 }
 public String             getUsage() { return usage; }
 public IOptionTable       getOptions() { return options; }
 public ArrayList<String>  getArguments() { return arguments; }

 /** Output a message. */
 public void output(String msg) { // throws IOException {
     try {
         int length = msg.length();
         for (int i = 0 ; i < length ; i++) {
             out.write(msg.charAt(i));
         }
     } catch (IOException e) {
     }
 }
 /** Output a message with newline. */
 public void outputln(String msg) { // throws IOException {
     output(msg + "\n");
 }
 /** Output an error message. */
 public void error(String msg) { //throws IOException {
     outputln("Error: " + msg + "\n");
     usage();
 }
 /** Output an error message. */
 public void usage() { // throws IOException {
     outputln("Usage: " + usage + "\n");
     outputln("where options are:");
     outputln(options.toString());
     System.exit(1);
 }

 private void parse() {
     IOption invalidOption = options.invalidOption();
     int n = 0;

     // Parse options...
     while ( n < args.length  &&  args[n].startsWith("-") ) {
         // must check in table if it is a valid option for this command
         // if yes, get the format (argument?)
         // set the flag and check if allowed
         String  optionName = args[n].substring(1);
         IOption option = options.get(optionName);

         // valid option?
         if ( option != invalidOption ) {
             option.enable();
             n++;
         } else {
             error("invalid option: " + args[n]);
             return;
         }
     }
     // Parse arguments...
     while ( n < args.length ) {
         arguments.add(args[n++]);
     }
 }
}

