package assembler.tokenization;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

import org.junit.jupiter.api.Test;


class ParserTest {

	@Test
	void test() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    String expectedString = "[Label (LABEL)]\r\n" + 
	    		"[[Label (LABEL)]]\r\n" + 
	    		"LABEL\r\n" + 
	    		"";
	    String [] args = null;
	    Parser.main(args);
		assertEquals(expectedString,outContent.toString());
	}

}