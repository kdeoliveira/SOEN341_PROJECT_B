// package assembler.tokenization;

// import static org.junit.jupiter.api.Assertions.*;
// import java.io.*;

// import org.junit.jupiter.api.Test;

// class LexerTest {

// 	@Test
// 	void test() {
// 		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
// 	    System.setOut(new PrintStream(outContent));
// 	    String expectedString = "[(Unknown:1) [ Label]]\r\n" + 
// 	    		"1 - 17\r\n" + 
// 	    		"[Label (LABEL), add (OPCODE), ;kfsfsd (COMMENT)]\r\n" + 
// 	    		"0 - 16\r\n" + 
// 	    		"[(Unknown:6) [add]]\r\n" + 
// 	    		"6 - 15\r\n" + 
// 	    		"";
// 	    String [] args = null;
// 	    Lexer.main(args);
// 		assertEquals(expectedString,outContent.toString());
// 	}

// }
