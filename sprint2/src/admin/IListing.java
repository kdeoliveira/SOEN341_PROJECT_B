package admin;

public interface IListing {
    String getListing();
}

// Template Method Pattern
abstract class TemplateListing implements IListing {
    public String getListing() { return getVirtualCode() + getLabelTable(); }
    public String toString()  { return getListing(); }

    protected abstract String getVirtualCode();
    protected abstract String getLabelTable();
}

class Listing extends TemplateListing {
    private String virtualCode;
	private String labelTable;
	public Listing(
        String virtualCode,
        String labelTable
    ) {
        this.virtualCode = virtualCode;
        this.labelTable = labelTable;
    }
    // Banner's first part.
    protected String getVirtualCode() {
        return " Add your code here " + virtualCode + "\n";
    }
    // Banner's second part.
    protected String getLabelTable() {
        return " Add your code here" + labelTable + "\n";
    }
}


