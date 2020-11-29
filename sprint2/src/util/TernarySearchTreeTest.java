package util;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

import org.junit.jupiter.api.Test;

import assembler.tokenization.Parser;

class TernarySearchTreeTest {

	@Test
	void test() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	    String expectedString = "00110011\r\n" + 
	    		"";
	    String [] args = null;
	    TernarySearchTree.main(args);
		assertEquals(expectedString,outContent.toString());
	}

}
