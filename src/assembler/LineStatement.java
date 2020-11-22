package assembler;

import assembler.tokenization.EBNF;
import util.BinaryAddress;

public class LineStatement {

	private Node label;
	private Instruction instruction;
	private Comment comment;
	private int lineNumber;
	private BinaryAddress machineCode;
	private String typeEBNF;

	public LineStatement (int lineNumber, Node label, Node instruction, Comment comment, String type)
	{	
		this.lineNumber = lineNumber;
		this.label = label;
		this.instruction = (Instruction) instruction;
		if(this.instruction == null && this.label != null)
			this.machineCode = this.label.getValue();
		else if(this.instruction == null)
			this.machineCode = new BinaryAddress(0x0);
		else
			this.machineCode = this.instruction.getValue();
		this.comment = comment;
		this.typeEBNF = type;
	}
	public LineStatement ()
	{
		this.label = null;
		this.instruction = null;
		this.comment = null;
	}
	public BinaryAddress getMachineCode() {
		return machineCode;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public String getTypeEBNF() {
		return typeEBNF;
	}
	public Node getLabel() {
		return label;
	}
	public void setLabel(Node label) {
		this.label = label;
	}

	// public Node getopcode() {
	// 	return opcode;
	// }
	// public void setopcode(Node opcode) {
	// 	this.opcode = opcode;
	// }
	// public Node getOperand() {
	// 	return operand;
	// }
	// public void setOperand(Node operand) {
	// 	this.operand = operand;
	// }
	public String getComment() {
		return comment.toString();
	}
	public void setComment(String comment) {
		this.comment.setComment(comment);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		if(this.label != null && typeEBNF.equals(EBNF.INHERENT2.name()))
			str.append(String.format("%s\t%s\t%s\t%s", this.machineCode, this.machineCode.getHexCode(), this.label.getKey(), this.instruction));
		else if(this.label != null && this.instruction != null){
			str.append(String.format("%s\t%s\t\t%s ", this.machineCode, this.machineCode.getHexCode(), this.instruction));
			str.append(this.label.getKey());

		}
		else if(this.instruction == null && this.label != null)
			str.append(String.format("%s\t%s\t%s", this.machineCode, this.machineCode.getHexCode(),label.getKey()));
		else
			str.append(String.format("%s\t%s\t\t%s", this.machineCode, this.machineCode.getHexCode(), this.instruction == null ? "" : this.instruction));
		
		return str.append(this.comment == null ? "" : "\t"+this.comment).toString();
	}	
}