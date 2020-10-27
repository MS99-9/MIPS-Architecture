package control;

public class Execute {
	static String[] ExMem = new String[14];
	// static String[] IdEx;
	static int pc;
	static int readData1;
	static int readData2;
	static String immediate;
	static String rt;
	static String rd;
	static String regDst;
	static String branch;
	static String memRead;
	static String memToReg;
	static String ALUOp;
	static String memWrite;
	static String ALUsrc;
	static String regWrite;
	static String jump;
	static String jumpAddress;
	static String instruction;

	static int ALUresult;
	static String zeroFlag;
	static String ALUControlOutput;
	static int addResult;
	static String writeRegister;

	public static String execute() {

		String output = "**************Execute Stage*************";

		rdrt(regDst);
		ALU(readData1, mux(ALUsrc), ALUControl(immediate.substring(26), ALUOp));
		Addresult(pc, immediate);
		ExMem[0] = writeRegister;
		ExMem[1] = Integer.toString(readData2);
		ExMem[2] = Integer.toString(ALUresult);
		ExMem[3] = zeroFlag;
		ExMem[4] = Integer.toString(addResult);
		ExMem[5] = memRead;
		ExMem[6] = memWrite;
		ExMem[7] = branch;
		ExMem[8] = memToReg;
		ExMem[9] = regWrite;
		ExMem[10] = jump;
		ExMem[11] = jumpAddress;
		ExMem[12] = Integer.toString(pc);
		ExMem[13] = instruction;

		MemoryAccess.writeRegister = writeRegister;
		MemoryAccess.readData2 = readData2;
		MemoryAccess.ALUresult = ALUresult;
		MemoryAccess.zeroFlag = zeroFlag;
		MemoryAccess.addResult = addResult;
		MemoryAccess.memRead = memRead;
		MemoryAccess.memWrite = memWrite;
		MemoryAccess.branch = branch;
		MemoryAccess.memToReg = memToReg;
		MemoryAccess.regWrite = regWrite;
		MemoryAccess.jump = jump;
		MemoryAccess.jumpAddress = jumpAddress;
		MemoryAccess.pc = pc;
		MemoryAccess.instruction = instruction;

		System.out.println("instruction being executed in execute stage:" + ExMem[13]);
		System.out.println("content in EX/MEM pipeline register:");
		System.out.println("writeRegister rt/rd:" + ExMem[0]);
		System.out.println("readData2:" + Integer.valueOf(ExMem[1]));
		System.out.println("ALUresult:" + ExMem[2]);
		System.out.println("zeroFlag:" + ExMem[3]);
		System.out.println("addResult/branch address:" + Integer.valueOf(ExMem[4]));
		System.out.println("jump address:" + ExMem[11]);
		System.out.println("Mem Controls: MemToRead:" + ExMem[5] + ",MemWrite:" + ExMem[6] + ",Branch:" + ExMem[7]
				+ ",Jump:" + ExMem[10]);
		System.out.println("WB Controls: MemToReg:" + ExMem[8] + ",RegWrite:" + ExMem[9]);

		output += "\n" + "Instruction being executed in execute stage is " + ExMem[13] + "\n" + "Value of PC is " + pc
				+ "\n" + "Content of EX/MEM pipeline register :- " + "\n" + "1) PC = " + ExMem[12] + "\n"
				+ "2) writeRegister rt/rd : " + ExMem[0] + "\n" + "3) readData2 : " + Integer.valueOf(ExMem[1]) + "\n"
				+ "4) ALUresult : " + ExMem[2] + "\n" + "5) zeroFlag : " + ExMem[3] + "\n"
				+ "6) addResult/branch address : " + Integer.valueOf(ExMem[4]) + "\n" + "7) jump address : " + ExMem[11]
				+ "\n" + "8) Mem Controls : MemToRead = " + ExMem[5] + " , MemWrite = " + ExMem[6] + " , Branch = "
				+ ExMem[7] + " , Jump = " + ExMem[10] + "\n" + "9) WB Controls : MemToReg = " + ExMem[8]
				+ " , RegWrite = " + ExMem[9] + "\n" + "10) Instruction : " + ExMem[13] + "\n";

		System.out.println();

		return output;
	}

	public static void rdrt(String x) {
		if (x == "1")
			writeRegister = rd;
		else
			writeRegister = rt;
	}

	public static String ALUControl(String x, String y) {
		String z = "";
		switch (y) {
		case ("00"): {
			z += "0010";
			break;
		}
		case ("01"): {
			z += "0110";
			break;
		}
		case ("10"): {
			switch (x) {
			case ("100000"): {
				z += "0010";
				break;
			}
			case ("100010"): {
				z += "0110";
				break;
			}
			case ("100100"): {
				z += "0000";
				break;
			}
			case ("100101"): {
				z += "0001";
				break;
			}
			case ("101010"): {
				z += "0111";
				break;
			}
			case ("100111"): {
				z += "1100";
				break;
			}

			}
		}

		}
		return z;
	}

	public static String mux(String x) {
		if (x == "0")
			return Integer.toBinaryString((readData2));
		else
			return immediate;
	}

	public static void ALU(int x, String y, String z) {
		if (y.charAt(0) == '0' || y.length() != 32) {
			switch (z) {
			case ("0010"): {
				ALUresult = x + Integer.parseInt(y, 2);
				break;
			}
			case ("0110"): {
				ALUresult = x - Integer.parseInt(y, 2);
				break;
			}
			case ("0000"): {
				ALUresult = x & Integer.parseInt(y, 2);
				break;
			}
			case ("0001"): {
				ALUresult = x | Integer.parseInt(y, 2);
				break;
			}
			case ("0111"): {
				if (x < Integer.parseInt(y, 2))
					ALUresult = 1;
				else
					ALUresult = 0;
				break;
			}
			case ("1100"): {
				ALUresult = ~(x | Integer.parseInt(y, 2));
				break;
			}

			}
		} else {
			int q = Integer.parseInt(QueueObj.flip(y), 2) * -1;
			switch (z) {
			case ("0010"): {
				ALUresult = x + q;
				break;
			}
			case ("0110"): {
				ALUresult = x - q;
				break;
			}
			case ("0000"): {
				ALUresult = x & q;
				break;
			}
			case ("0001"): {
				ALUresult = x | q;
				break;
			}
			case ("0111"): {
				if (x < q)
					ALUresult = 1;
				else
					ALUresult = 0;
				break;
			}
			case ("1100"): {
				ALUresult = ~(x | q);
				break;
			}

			}

		}
		if (ALUresult == 0)
			zeroFlag = "1";
		else
			zeroFlag = "0";
	}

	public static void Addresult(int x, String y) {
		if (y.charAt(0) == '0') {
			int z = Integer.parseInt(y, 2) * 4;
			addResult = x + z;
		} else {
			int z = Integer.parseInt(QueueObj.flip(y), 2) * -4;
			addResult = x + z;
		}
	}

}
