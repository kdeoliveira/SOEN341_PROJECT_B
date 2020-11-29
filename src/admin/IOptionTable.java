package admin;
//OptionTable.java - (c) 2020 by Team B6

import java.util.HashMap; // To cache options.

public interface IOptionTable {
 /** add option by short name and long name */
 void    add(IOption option);

 /** get option by short name or long name. Returns invalid option is not found. */
 IOption get(String name);

 IOption invalidOption();
}

class OptionTable implements IOptionTable {
 public OptionTable() {
     options = new HashMap<String, IOption>();
 }
 /** add option by short name and long name */
 public void add(IOption option) {
     if (option != null  &&  options.get(option.getShortName()) == null ) {
         options.put(option.getShortName(), option);
         options.put(option.getLongName(), option);
     }
 }
 public IOption get(String name) {
     IOption option = (IOption)options.get(name);
     return (option == null) ? invalid : option;
 }
 public IOption invalidOption() {
     return invalid;
 }
 // Print only options with short name as a key (to avoid duplication).
 public String toString() {
     String s = "";
     for (String optionName : options.keySet()) {
         if (optionName.length() == 1) {
             s += options.get(optionName).toString() + "\n";
         }
     }
     return s;
 }
 private HashMap<String, IOption> options;
 private static final IOption invalid = new Option("", "<invalid>", "Invalid Option.");
}

