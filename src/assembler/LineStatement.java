package assembler;

import util.BinaryAddress;

public class LineStatement {

	private Node label;
	private Node instruction;
	private Node comment;
	private int lineNumber;
	private BinaryAddress machineCode;
	private String typeEBNF;

	
	public LineStatement (int lineNumber, Node label, Node instruction, Node comment)
	{	
		this.lineNumber = lineNumber;
		this.label = label;
		this.instruction = instruction;
		this.machineCode = (this.instruction != null) ? this.instruction.getValue() : this.label.getValue();
		this.comment = comment;
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
	public Node getComment() {
		return comment;
	}
	public void setComment(Node comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		if(this.label != null && this.instruction != null)
			return String.format("%s\t%s\t\t%s\s%s", this.machineCode, this.machineCode.getHexCode(), this.instruction.getKey(), this.label.getKey());
		else if(this.instruction == null && this.label != null)
			return String.format("%s\t%s\t%s", this.machineCode, this.machineCode.getHexCode(), this.label.getKey());
		else
			return String.format("%s\t%s\t\t%s", this.machineCode, this.machineCode.getHexCode(), this.instruction.getKey());
	}
	

	
}
