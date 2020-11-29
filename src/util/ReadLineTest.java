package util;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

import org.junit.jupiter.api.Test;

import assembler.tokenization.Parser;

class ReadLineTest {

	@Test
	void test() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    String expectedString = "[ halt]\r\n" + 
	    		"[ pop]\r\n" + 
	    		"[ dup]\r\n" + 
	    		"[ exit]\r\n" + 
	    		"[ ret]\r\n" + 
	    		"[ not, Label]\r\n" + 
	    		"[ and]\r\n" + 
	    		"[ Label]\r\n" + 
	    		"[ xor]\r\n" + 
	    		"[ neg]\r\n" + 
	    		"[ inc]\r\n" + 
	    		"[ dec]\r\n" + 
	    		"[ add]\r\n" + 
	    		"[ sub]\r\n" + 
	    		"[ mul]\r\n" + 
	    		"[ div]\r\n" + 
	    		"[ rem]\r\n" + 
	    		"[ shl]\r\n" + 
	    		"[ shr]\r\n" + 
	    		"[ teq]\r\n" + 
	    		"[ tne]\r\n" + 
	    		"[ tlt]\r\n" + 
	    		"[ tgt]\r\n" + 
	    		"[ tle]\r\n" + 
	    		"[ tge]\r\n" + 
	    		"[ halt]\r\n" + 
	    		"" + 
	    		"";
	    String [] args = null;
	    ReadLine.main(args);
		assertEquals(expectedString,outContent.toString());
	}

}
