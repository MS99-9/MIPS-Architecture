package control;

public class WriteBack {
	static String[] MemWB;
	static String MemToReg;
	static String RegWrite;
	static int ReadData;
	static int ALUresult;
	static String writeRegister;
	static String instruction;

	public static String writeBack() {

		String output = "*************WriteBack Stage************";

		System.out.println("instruction being executed in writeBack stage:" + instruction);
		output += "\n" + "Instruction being executed in writeBack stage is " + instruction + "\n" + "Value of PC is "
				+ MemoryAccess.pc + "\n";
		if (RegWrite == "1") {
			if (MemToReg == "1") {
				switch (writeRegister) {
				case ("10000"): {
					RegFile.$s0 = ReadData;
					System.out.println("Value stored in write register:" + ReadData);
					output += "Value stored in write register : " + ReadData + "\n";
					break;
				}
				case ("10001"): {
					RegFile.$s1 = ReadData;
					System.out.println("Value stored in write register:" + ReadData);
					output += "Value stored in write register : " + ReadData + "\n";
					break;
				}
				case ("10010"): {
					RegFile.$s2 = ReadData;
					System.out.println("Value stored in write register:" + ReadData);
					output += "Value stored in write register : " + ReadData + "\n";
					break;
				}
				case ("10011"): {
					RegFile.$s3 = ReadData;
					System.out.println("Value stored in write register:" + ReadData);
					output += "Value stored in write register : " + ReadData + "\n";
					break;
				}
				case ("10100"): {
					RegFile.$s4 = ReadData;
					System.out.println("Value stored in write register:" + ReadData);
					output += "Value stored in write register : " + ReadData + "\n";
					break;
				}
				case ("10101"): {
					RegFile.$s5 = ReadData;
					System.out.println("Value stored in write register:" + ReadData);
					output += "Value stored in write register : " + ReadData + "\n";
					break;
				}
				case ("01000"): {
					RegFile.$t0 = ReadData;
					System.out.println("Value stored in write register:" + ReadData);
					output += "Value stored in write register : " + ReadData + "\n";
					break;
				}
				case ("01001"): {
					RegFile.$t1 = ReadData;
					System.out.println("Value stored in write register:" + ReadData);
					output += "Value stored in write register : " + ReadData + "\n";
					break;
				}
				case ("01010"): {
					RegFile.$t2 = ReadData;
					System.out.println("Value stored in write register:" + ReadData);
					output += "Value stored in write register : " + ReadData + "\n";
					break;
				}
				case ("01011"): {
					RegFile.$t3 = ReadData;
					System.out.println("Value stored in write register:" + ReadData);
					output += "Value stored in write register : " + ReadData + "\n";
					break;
				}
				case ("01100"): {
					RegFile.$t4 = ReadData;
					System.out.println("Value stored in write register:" + ReadData);
					output += "Value stored in write register : " + ReadData + "\n";
					break;
				}
				case ("01101"): {
					RegFile.$t5 = ReadData;
					System.out.println("Value stored in write register:" + ReadData);
					output += "Value stored in write register : " + ReadData + "\n";
					break;
				}

				}
			} else {
				switch (writeRegister) {
				case ("10000"): {
					RegFile.$s0 = ALUresult;
					System.out.println("Value stored in write register:" + ALUresult);
					output += "Value stored in write register : " + ALUresult + "\n";
					break;
				}
				case ("10001"): {
					RegFile.$s1 = ALUresult;
					System.out.println("Value stored in write register:" + ALUresult);
					output += "Value stored in write register : " + ALUresult + "\n";
					break;
				}
				case ("10010"): {
					RegFile.$s2 = ALUresult;
					System.out.println("Value stored in write register:" + ALUresult);
					output += "Value stored in write register : " + ALUresult + "\n";
					break;
				}
				case ("10011"): {
					RegFile.$s3 = ALUresult;
					System.out.println("Value stored in write register:" + ALUresult);
					output += "Value stored in write register : " + ALUresult + "\n";
					break;
				}
				case ("10100"): {
					RegFile.$s4 = ALUresult;
					System.out.println("Value stored in write register:" + ALUresult);
					output += "Value stored in write register : " + ALUresult + "\n";
					break;
				}
				case ("10101"): {
					RegFile.$s5 = ALUresult;
					System.out.println("Value stored in write register:" + ALUresult);
					output += "Value stored in write register : " + ALUresult + "\n";
					break;
				}
				case ("01000"): {
					RegFile.$t0 = ALUresult;
					System.out.println("Value stored in write register:" + ALUresult);
					output += "Value stored in write register : " + ALUresult + "\n";
					break;
				}
				case ("01001"): {
					RegFile.$t1 = ALUresult;
					System.out.println("Value stored in write register:" + ALUresult);
					output += "Value stored in write register : " + ALUresult + "\n";
					break;
				}
				case ("01010"): {
					RegFile.$t2 = ALUresult;
					System.out.println("Value stored in write register:" + ALUresult);
					output += "Value stored in write register : " + ALUresult + "\n";
					break;
				}
				case ("01011"): {
					RegFile.$t3 = ALUresult;
					System.out.println("Value stored in write register:" + ALUresult);
					output += "Value stored in write register : " + ALUresult + "\n";
					break;
				}
				case ("01100"): {
					RegFile.$t4 = ALUresult;
					System.out.println("Value stored in write register:" + ALUresult);
					output += "Value stored in write register : " + ALUresult + "\n";
					break;
				}
				case ("01101"): {
					RegFile.$t5 = ALUresult;
					System.out.println("Value stored in write register:" + ALUresult);
					output += "Value stored in write register : " + ALUresult + "\n";
					break;
				}

				}
			}
		}
		System.out.println();

		return output;

	}

}