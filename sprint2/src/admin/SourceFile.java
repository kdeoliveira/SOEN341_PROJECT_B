package admin;

import java.io.File;

public class SourceFile {
	private String name;
	private String extension;
	
	private File file;
	
	public SourceFile (String name, String extension)
	{
		this.name = name;
		this.extension = extension;
		file = new File (name+extension);
	}
	
	public SourceFile (String nameAndExtension)
	{
	
		String stringArray[] = nameAndExtension.split("\\.",2);
		this.name = stringArray[0];
		this.extension = "."+stringArray[1];
		file = new File (nameAndExtension);
	}
	
	public String getName()
	{
		return name;
	}

	public String getExtension() {
		return extension;
	}

	public File getFile() {
		return file;
	}
	
	public String toString() {
		return name+extension;
	}
}
