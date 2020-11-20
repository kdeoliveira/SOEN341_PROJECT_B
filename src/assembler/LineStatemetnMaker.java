// package assembler;

// import assembler.tokenization.SYNTAX;
// import util.BinaryAddress;

// import java.util.*;

// import assembler.tokenization.*;

// public class LineStatemetnMaker {

// 	private List<Node> nodes;
	
// 	public LineStatement makeLineStatement(Token[] elements, String typeEBNF, BinaryAddress bin)
// 	{
// 		nodes = new ArrayList<>();
// 		if(elements.length==0)
// 		{
// 			return new LineStatement();
// 		}else if (elements.length==4)
// 		{
// 			return new LineStatement(elements[0], elements[1], elements[2], elements[3]);
// 		}else
// 		{
// 			LineStatement temporairy = new LineStatement();

// 			for(int i = 0; i < elements.length; i++)
// 			{
// 				if(elements[i].getKey() == SYNTAX.LABEL)
// 				{
// 					nodes.add(new Node(bin, elements[i].getValue()))
// 					temporairy.setLabel(elements[i]);
// 				}else if(elements[i].getKey() == SYNTAX.OPCODE)
// 				{
// 					temporairy.setopcode(elements[i]);
// 				}else if(elements[i].getKey() == SYNTAX.OPERAND)
// 				{
// 					temporairy.setOperand(elements[i]);
// 				}else if(elements[i].getKey() == SYNTAX.COMMENT)
// 				{
// 					temporairy.setComment(elements[i]);
// 				}
// 			}
// 			return temporairy;
// 		}
// 	}

// }
