package admin;

public class cmaFactory {
    private static final IOption help =
        new Option("h", "help", "List a summary of all options and their arguments.");
    private static final IOption verbose =
        new Option("v", "verbose", "Enable verbose output.");
    private static final IOption listing =
        new Option("l" , "listing", "Print the virtual code and the label table.");

    private static IListing       cmaListing;
    private static IOptionTable   optionTable;
    private static IAdministrator admin;

    public static IListing     makeListing()     { return cmaListing; }
    public static IOptionTable makeOptionTable() { return optionTable; }

    public static IAdministrator makeAdmin(String args[]) {
        if (admin == null) {
            admin = new Administrator(
                        args,
                        "cma [ -h | -v | -l ]",
                        optionTable
                    );
        }
        return admin;
    }

    static {
        cmaListing = new Listing("", "");
        optionTable = new OptionTable();
        optionTable.add(help);
        optionTable.add(verbose);
        optionTable.add(listing);        
    }
}
