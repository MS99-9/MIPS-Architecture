package control;

public class MemoryAccess {
	static String[] MemWB = new String[6];
	// static String[] ExMem;
	static String writeRegister;
	static int readData2;
	static int ALUresult;
	static String zeroFlag;
	static int addResult;
	static String memRead;
	static String memWrite;
	static String branch;
	static String memToReg;
	static String regWrite;
	static String jump;
	static String jumpAddress;
	static int pc;
	static String instruction;

	static int ReadData;

	public static String memoryAccess() {

		String output = "***********MemoryAccess Stage***********";

		muxJump(jump);
		String xin = readData(memRead, memWrite);
		MemWB[0] = memToReg;
		MemWB[1] = regWrite;
		MemWB[2] = Integer.toString(ReadData);
		MemWB[3] = Integer.toString(ALUresult);
		MemWB[4] = writeRegister;
		MemWB[5] = instruction;

		WriteBack.MemToReg = memToReg;
		WriteBack.RegWrite = regWrite;
		WriteBack.ReadData = ReadData;
		WriteBack.ALUresult = ALUresult;
		WriteBack.writeRegister = writeRegister;
		WriteBack.instruction = instruction;

		System.out.println("instruction being executed in memory stage:" + MemWB[5]);
		System.out.println("content in MEM/WB pipeline register:");
		System.out.println("ALUresult:" + MemWB[3]);
		System.out.println("readData from data memory:" + Integer.valueOf(MemWB[2]));
		System.out.println("writeRegister rt/rd:" + MemWB[4]);
		System.out.println("WB controls: MemToReg:" + MemWB[0] + ",RegWrite:" + MemWB[1]);

		System.out.println();

		if (xin.equals("")) {
			output += "\n" + "Instruction being executed in memory stage is " + MemWB[5] + "\n" + "Value of PC is " + pc
					+ "\n" + "Content of MEM/WB pipeline register :-" + "\n" + "1) ALUresult : " + MemWB[3] + "\n"
					+ "2) readData from data memory : " + Integer.valueOf(MemWB[2]) + "\n" + "3) writeRegister rt/rd : "
					+ MemWB[4] + "\n" + "4) WB controls : MemToReg : " + MemWB[0] + " , RegWrite : " + MemWB[1] + "\n";
		} else {
			output += "\n" + "Instruction being executed in memory stage is " + MemWB[5] + "\n" + "Value of PC is " + pc
					+ "\n" + xin + "\n" + "Content of MEM/WB pipeline register :-" + "\n" + "1) PC = " + pc + "\n"
					+ "2) ALUresult : " + MemWB[3] + "\n" + "3) readData from data memory : "
					+ Integer.valueOf(MemWB[2]) + "\n" + "4) writeRegister rt/rd : " + MemWB[4] + "\n"
					+ "5) WB controls : MemToReg : " + MemWB[0] + " , RegWrite : " + MemWB[1] + "\n";
		}
		return output;
	}

	public static int muxBranch(String x, String y) {
		if (x == "1" && y == "1")
			return addResult;
		else
			return pc;
	}

	public static void muxJump(String x) {
		if (x == "1")
			CPU.pc = Integer.parseInt(jumpAddress, 2);
		else
			CPU.pc = muxBranch(branch, zeroFlag);
	}

	public static String readData(String x, String y) {
		String outputt = "";
		if (x == "1")
			ReadData = CPU.DataMemory[ALUresult];
		else {
			if (y == "1") {
				CPU.DataMemory[ALUresult] = readData2;
				System.out.println("Data in the Data Memory in index " + ALUresult + "=" + readData2);
				outputt += "Data in the Data Memory in index " + ALUresult + " = " + readData2;

				ReadData = -1;
			} else
				ReadData = -1;
		}
		return outputt;
	}

}
