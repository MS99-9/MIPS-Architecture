package control;

public class Decode {
	// static String[] IfId=new String[2];
	static int pc;
	static String instruction;
	static String[] IdEx = new String[17];

	static String readReg1;
	static String readReg2;
	static int readData1;
	static int readData2;
	static String writeRegister;

	static String regDst;
	static String branch;
	static String memRead;
	static String memToReg;
	static String ALUOp;
	static String memWrite;
	static String ALUsrc;
	static String regWrite;
	static String jump;

	static String immediate;
	static String rd;
	static String rt;
	static String jumpAddress;

	public static String decode() {

		String output = "**************Decode Stage**************";

		System.out.println("Instruction being executed in decode stage:" + instruction);
		System.out.println("input parameter: pc:" + pc);

		output += "\n" + "Instruction being executed in decode stage is " + instruction + "\n" + "Value of PC is " + pc
				+ "\n";

		rt = instruction.substring(11, 16);
		rd = instruction.substring(16, 21);
		immediate = signExtend(instruction.substring(16));
		jumpAddress = "0000" + instruction.substring(6) + "00";
		regInfo();
		controlUnit(instruction.substring(0, 6));
		IdEx[0] = Integer.toString(pc);
		IdEx[1] = Integer.toString(readData1);
		IdEx[2] = Integer.toString(readData2);
		IdEx[3] = immediate;
		IdEx[4] = rt;
		IdEx[5] = rd;
		IdEx[6] = regDst;
		IdEx[7] = branch;
		IdEx[8] = memRead;
		IdEx[9] = memToReg;
		IdEx[10] = ALUOp;
		IdEx[11] = memWrite;
		IdEx[12] = ALUsrc;
		IdEx[13] = regWrite;
		IdEx[14] = jump;
		IdEx[15] = jumpAddress;
		IdEx[16] = instruction;

		Execute.pc = pc;
		Execute.readData1 = readData1;
		Execute.readData2 = readData2;
		Execute.immediate = immediate;
		Execute.rt = rt;
		Execute.rd = rd;
		Execute.regDst = regDst;
		Execute.branch = branch;
		Execute.memRead = memRead;
		Execute.memToReg = memToReg;
		Execute.ALUOp = ALUOp;
		Execute.memWrite = memWrite;
		Execute.ALUsrc = ALUsrc;
		Execute.regWrite = regWrite;
		Execute.jump = jump;
		Execute.jumpAddress = jumpAddress;
		Execute.instruction = instruction;

		System.out.println("content in ID/EX pipeline register are:");
		System.out.println("pc=" + Integer.valueOf(IdEx[0]));
		System.out.println("readData1=" + Integer.valueOf(IdEx[1]));
		System.out.println("readData2=" + Integer.valueOf(IdEx[2]));
		System.out.println("immediate=" + IdEx[3]);
		System.out.println("rt register=" + IdEx[4]);
		System.out.println("rd register=" + IdEx[5]);
		System.out.println("WB controls: MemToReg:" + IdEx[9] + ",regWrite:" + IdEx[13]);
		System.out.println("Mem controls: MemRead:" + IdEx[8] + ",MemWrte:" + IdEx[11] + ",Branch:" + IdEx[7] + ",Jump:"
				+ IdEx[14]);
		System.out.println("Ex controls: RegDst:" + IdEx[6] + ",ALUOp:" + IdEx[10] + ",ALUsrc:" + IdEx[12]);
		System.out.println("Jump Address:" + IdEx[15]);

		output += "Content of ID/EX pipeline register :-" + "\n" + "1) PC = " + Integer.valueOf(IdEx[0]) + "\n"
				+ "2) readData1 = " + Integer.valueOf(IdEx[1]) + "\n" + "3) readData2 = " + Integer.valueOf(IdEx[2])
				+ "\n" + "4) immediate = " + IdEx[3] + "\n" + "5) rt register = " + IdEx[4] + "\n" + "6) rd register = "
				+ IdEx[5] + "\n" + "7) WB controls : MemToReg = " + IdEx[9] + " , regWrite : " + IdEx[13] + "\n"
				+ "8) Mem controls : MemRead = " + IdEx[8] + " , MemWrte = " + IdEx[11] + " , Branch = " + IdEx[7]
				+ " , Jump = " + IdEx[14] + "\n" + "9) Ex controls : RegDst = " + IdEx[6] + " , ALUOp = " + IdEx[10]
				+ " , ALUsrc = " + IdEx[12] + "\n" + "10) Jump Address = " + IdEx[15] + "\n" + "11) Instruction = "
				+ IdEx[16] + "\n";

		System.out.println();

		return output;

	}

	public static void regInfo() {
		readReg1 = instruction.substring(6, 11);
		readReg2 = instruction.substring(11, 16);
		switch (readReg1) {
		case ("00000"): {
			readData1 = RegFile.$zero;
			break;
		}
		case ("10000"): {
			readData1 = RegFile.$s0;
			break;
		}
		case ("10001"): {
			readData1 = RegFile.$s1;
			break;
		}
		case ("10010"): {
			readData1 = RegFile.$s2;
			break;
		}
		case ("10011"): {
			readData1 = RegFile.$s3;
			break;
		}
		case ("10100"): {
			readData1 = RegFile.$s4;
			break;
		}
		case ("10101"): {
			readData1 = RegFile.$s5;
			break;
		}
		case ("01000"): {
			readData1 = RegFile.$t0;
			break;
		}
		case ("01001"): {
			readData1 = RegFile.$t1;
			break;
		}
		case ("01010"): {
			readData1 = RegFile.$t2;
			break;
		}
		case ("01011"): {
			readData1 = RegFile.$t3;
			break;
		}
		case ("01100"): {
			readData1 = RegFile.$t4;
			break;
		}
		case ("01101"): {
			readData1 = RegFile.$t5;
			break;
		}

		}
		switch (readReg2) {
		case ("00000"): {
			readData2 = RegFile.$zero;
			break;
		}
		case ("10000"): {
			readData2 = RegFile.$s0;
			break;
		}
		case ("10001"): {
			readData2 = RegFile.$s1;
			break;
		}
		case ("10010"): {
			readData2 = RegFile.$s2;
			break;
		}
		case ("10011"): {
			readData2 = RegFile.$s3;
			break;
		}
		case ("10100"): {
			readData2 = RegFile.$s4;
			break;
		}
		case ("10101"): {
			readData2 = RegFile.$s5;
			break;
		}
		case ("01000"): {
			readData2 = RegFile.$t0;
			break;
		}
		case ("01001"): {
			readData2 = RegFile.$t1;
			break;
		}
		case ("01010"): {
			readData2 = RegFile.$t2;
			break;
		}
		case ("01011"): {
			readData2 = RegFile.$t3;
			break;
		}
		case ("01100"): {
			readData2 = RegFile.$t4;
			break;
		}
		case ("01101"): {
			readData2 = RegFile.$t5;
			break;
		}

		}

	}

	public static String signExtend(String x) {
		if (x.charAt(0) == '0') {
			while (x.length() != 32) {
				x = "0" + x;
			}
		} else {
			while (x.length() != 32) {
				x = "1" + x;
			}
		}
		return x;
	}

	public static void controlUnit(String x) {
		switch (x) {
		case ("100011"): {
			regDst = "0";
			ALUsrc = "1";
			branch = "0";
			memRead = "1";
			memToReg = "1";
			memWrite = "0";
			regWrite = "1";
			jump = "0";
			ALUOp = "00";
			break;
		}
		case ("101011"): {
			regDst = "X";
			ALUsrc = "1";
			branch = "0";
			memRead = "0";
			memToReg = "X";
			memWrite = "1";
			regWrite = "0";
			jump = "0";
			ALUOp = "00";
			break;
		}
		case ("000100"): {
			regDst = "X";
			ALUsrc = "0";
			branch = "1";
			memRead = "0";
			memToReg = "X";
			memWrite = "0";
			regWrite = "0";
			jump = "0";
			ALUOp = "01";
			break;
		}
		case ("001000"): {
			regDst = "0";
			ALUsrc = "1";
			branch = "0";
			memRead = "0";
			memToReg = "0";
			memWrite = "0";
			regWrite = "1";
			jump = "0";
			ALUOp = "00";
			break;
		}
		case ("000010"): {
			regDst = "X";
			ALUsrc = "X";
			branch = "0";
			memRead = "0";
			memToReg = "X";
			memWrite = "0";
			regWrite = "0";
			jump = "1";
			ALUOp = "X";
			break;
		}
		case ("000000"): {
			regDst = "1";
			ALUsrc = "0";
			branch = "0";
			memRead = "0";
			memToReg = "0";
			memWrite = "0";
			regWrite = "1";
			jump = "0";
			ALUOp = "10";
			break;
		}

		}
	}

}
