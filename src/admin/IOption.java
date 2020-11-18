package admin;
//IOption.java - (c) 2020 Team B6

public interface IOption {
 String  getShortName();
 String  getLongName();
 boolean isEnabled();
 void    enable();
}

/** An Option is disabled by default. */
class Option implements IOption {
 public Option(
     String  shortName,
     String  longName,
     String  description
 ) {
     this.shortName   = shortName;
     this.longName    = longName;
     this.description = description;
     this.enabled     = false;
 }
 public String  getShortName() { return shortName; }
 public String  getLongName()  { return longName; }
 public boolean isEnabled()    { return enabled; }
 public void    enable()       { enabled = true; }

 public String  toString() {
     return String.format("-%s or -%-10s %-8s", shortName, longName, description);
 }
 private String  shortName, longName, description;
 private boolean enabled;
}
