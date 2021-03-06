package units;

import registers.Register;
import instructions.Instruction;

public class ArithmeticLogicUnit {
	public int output;
	public boolean branch;
	public void execute(Instruction instruction) {
		branch = false;
		int format = instruction.getFormat();
		switch(format) {
		case 0:
			output = executeRTypeInstruction(instruction);
			break;
		case 1:
			output = instruction.getRs().getValue() + instruction.getImmediateConstant();
			break;
		case 2:
		case 3:
			output = executeMemoryTypeInstruction(instruction);
			break;
		case 4:
			output = executeBranchTypeInstruction(instruction);
		case 5:
		case 6:
			output = executeJumpTypeInstruction(instruction);
		}
	}
	
	public int executeRTypeInstruction(Instruction instruction) {
		int result = 0;
		String type = instruction.getType();
		Register rs = instruction.getRs();
		Register rt = instruction.getRt();
		switch(type) {
			case "add":
				result = rs.getValue() + rt.getValue();
				break;
			case "sub":
				result = rs.getValue() - rt.getValue();
				break;
			case "and":
				result = rs.getValue() & rt.getValue();
				break;
			case "nor":
				result = ~(rs.getValue() | rt.getValue());
				break;
			case "slt":
			case "sltu":
				result = rs.getValue() < rt.getValue() ? 1 : 0;
				break;
			case "sll":
				result = rt.getValue() << instruction.getShiftAmount();
				break;
			case "srl":
				result = rt.getValue() >> instruction.getShiftAmount();
				break;
		}
		return result;
	}
	
	public int executeMemoryTypeInstruction(Instruction instruction) {
		int result = 0;
		String type = instruction.getType();
		Register rs = instruction.getRs();
		int immediateConstant = instruction.getImmediateConstant();
		switch(type){
			case "sw":
			case "sb":
			case "lb":
			case "lw":
			case "lbu":
				result = immediateConstant + rs.getValue();
				break;
			case "lui":
			
				System.out.println("LUI yet to be implemented");
				break;
			
		}
		return result;
	}
	
	public int executeBranchTypeInstruction(Instruction instruction) {
		int result = 0;
		String type = instruction.getType();
		Register rs = instruction.getRs();
		Register rt = instruction.getRt();
		switch(type){
			case "beq":
				result = rs.getValue() - rt.getValue();
				branch = result == 0 ? true : false;
				break;
			case "bne":
				result = rs.getValue() - rt.getValue();
				branch = result != 0 ? true : false;
				break;
		}
		return result;
	}
	
	public int executeJumpTypeInstruction(Instruction instruction) {
		int result = 0;
		String type = instruction.getType();
		switch(type) {
			case "jr":
			case "jal":
			case "j":
				branch = true;
				break;	
		}
		return result;
	}
}
