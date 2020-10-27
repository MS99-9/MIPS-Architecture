package control;

public class Fetch {
	static int pc = CPU.pc;
	static String instruction;
	static String[] IfId = new String[2];

	public static String fetch() {

		String output = "***************Fetch Stage**************";

		instruction = CPU.InstructionMemory[pc];

		System.out.println("Instruction being executed in fetch stage is " + instruction);
		System.out.println("Value of pc before:" + pc);

		output += "\n" + "Instruction being executed in fetch stage is " + instruction + "\n" + "Value of PC before is "
				+ pc;

		pc += 4;
		CPU.pc += 4;
		IfId[0] = Integer.toString(pc);
		IfId[1] = instruction;

		Decode.pc = pc;
		Decode.instruction = instruction;

		System.out.println("Content in IF/ID pipeline register are:");
		System.out.println("pc=" + IfId[0]);
		System.out.println("instruction=" + IfId[1]);
		System.out.println();
		output += "\n" + "Content of IF/ID pipeline register :- " + "\n" + "1) PC = " + IfId[0] + "\n"
				+ "2) Instruction = " + IfId[1] + "\n";

		return output;
	}

}
