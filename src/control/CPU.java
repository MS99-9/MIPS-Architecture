package control;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class CPU {
	static String[] InstructionMemory = new String[4096];
	static int[] DataMemory = new int[4096];
	static int pc = 0;
	static int clock = 0;
	static QueueObj fetchq = new QueueObj(4096);
	static QueueObj decodeq = new QueueObj(4096);
	static QueueObj executeq = new QueueObj(4096);
	static QueueObj memoryAccessq = new QueueObj(4096);
	static QueueObj writebackq = new QueueObj(4096);

	public static ArrayList<Vector<Integer>> regEveryCycle = new ArrayList<>();
	public static ArrayList<String> allInstructions = new ArrayList<>();
	public static ArrayList<Vector<String>> instsEveryCycle = new ArrayList<>();

	public static String change(String x) {
		ArrayList<String> m = new ArrayList<String>(Arrays.asList(x.split("\\s")));
		String result = "";
		switch (m.get(0)) {
		case ("add"): {
			result = result + "000000" + regChange(m.get(2)) + regChange(m.get(3)) + regChange(m.get(1))
					+ "00000100000";
		}
			break;
		case ("sub"): {
			result = result + "000000" + regChange(m.get(2)) + regChange(m.get(3)) + regChange(m.get(1))
					+ "00000100010";
		}
			break;
		case ("or"): {
			result = result + "000000" + regChange(m.get(2)) + regChange(m.get(3)) + regChange(m.get(1))
					+ "00000100101";
		}
			break;
		case ("nor"): {
			result = result + "000000" + regChange(m.get(2)) + regChange(m.get(3)) + regChange(m.get(1))
					+ "00000100111";
		}
			break;
		case ("slt"): {
			result = result + "000000" + regChange(m.get(2)) + regChange(m.get(3)) + regChange(m.get(1))
					+ "00000101010";
		}
			break;

		case ("addi"): {
			result = result + "001000" + regChange(m.get(2)) + regChange(m.get(1)) + extend16(m.get(3));
			break;
		}
		case ("lw"): {
			result = result + "100011" + regChange(m.get(3)) + regChange(m.get(1)) + extend16(m.get(2));
			break;
		}
		case ("sw"): {
			result = result + "101011" + regChange(m.get(3)) + regChange(m.get(1)) + extend16(m.get(2));
			break;
		}
		case ("beq"): {
			result = result + "000100" + regChange(m.get(1)) + regChange(m.get(2)) + extend16(m.get(3));
			break;
		}

		case ("j"): {
			result = result + "000010" + extend26(m.get(1));
			break;
		}

		}
		return result;

	}

	public static String extend26(String x) {
		String y = Integer.toBinaryString(Integer.valueOf(x));
		if (Integer.valueOf(x) < 0)
			return y.substring(6);
		else {
			while (y.length() != 26) {
				y = "0" + y;
			}
			return y;
		}
	}

	public static String extend16(String x) {
		String y = Integer.toBinaryString(Integer.valueOf(x));
		if (Integer.valueOf(x) < 0)
			return y.substring(16);
		else {
			while (y.length() != 16) {
				y = "0" + y;
			}
			return y;
		}
	}

	public static String regChange(String x) {
		String result = "";
		switch (x) {
		case ("$s0"):
			result += "10000";
			break;
		case ("$s1"):
			result += "10001";
			break;
		case ("$s2"):
			result += "10010";
			break;
		case ("$s3"):
			result += "10011";
			break;
		case ("$s4"):
			result += "10100";
			break;
		case ("$s5"):
			result += "10101";
			break;
		case ("$t0"):
			result += "01000";
			break;
		case ("$t1"):
			result += "01001";
			break;
		case ("$t2"):
			result += "01010";
			break;
		case ("$t3"):
			result += "01011";
			break;
		case ("$t4"):
			result += "01100";
			break;
		case ("$t5"):
			result += "01101";
			break;
		case ("$zero"):
			result += "00000";
			break;

		}
		return result;
	}

	public static void readFile() {
		try {
			int i = 0;
			File file = new File("Read.txt");
			Scanner sc = new Scanner(file);
			while (sc.hasNext()) {
				String a = sc.nextLine();
				InstructionMemory[i] = change(a);
				i = i + 4;
			}
			sc.close();

		} catch (Exception e) {
		}
	}

	public static void run() {

		// clock = 0;

		@SuppressWarnings("unused")
		RegFile r = new RegFile();

		for (int i = 0; InstructionMemory[i] != null; i += 4) {
			System.out.println("Instruction in index " + i + " is " + InstructionMemory[i]);
			fetchq.enqueue(1);

			allInstructions.add(InstructionMemory[i]);
		}
		System.out.println();
		while (!fetchq.isEmpty() || !decodeq.isEmpty() || !executeq.isEmpty() || !memoryAccessq.isEmpty()
				|| !writebackq.isEmpty()) {
			clock++;
			System.out.println("**********************************");
			System.out.println("*** After clock cycle number " + clock + " ***");
			System.out.println("**********************************");

			// System.out.println(fetchq.isEmpty());
			Vector<String> toAddinsts = new Vector<>();

			if (!writebackq.isEmpty()) {
				toAddinsts.add(WriteBack.writeBack());
				writebackq.dequeue();
			}
			if (!memoryAccessq.isEmpty()) {
				toAddinsts.add(MemoryAccess.memoryAccess());
				memoryAccessq.dequeue();
				writebackq.enqueue(1);
			}
			if (!executeq.isEmpty()) {
				toAddinsts.add(Execute.execute());
				executeq.dequeue();
				memoryAccessq.enqueue(1);
			}
			if (!decodeq.isEmpty()) {
				toAddinsts.add(Decode.decode());
				decodeq.dequeue();
				executeq.enqueue(1);
			}
			if (!fetchq.isEmpty()) {
				toAddinsts.add(Fetch.fetch());
				fetchq.dequeue();
				decodeq.enqueue(1);
			}
			System.out.println("Register file content :-");
			System.out.print("$zero =" + RegFile.$zero);
			System.out.print(" $s0 =" + RegFile.$s0);
			System.out.print(" $s1 =" + RegFile.$s1);
			System.out.print(" $s2 =" + RegFile.$s2);
			System.out.print(" $s3 =" + RegFile.$s3);
			System.out.print(" $s4 =" + RegFile.$s4);
			System.out.print(" $s5 =" + RegFile.$s5);
			System.out.print(" $t0 =" + RegFile.$t0);
			System.out.print(" $t1 =" + RegFile.$t1);
			System.out.print(" $t2 =" + RegFile.$t2);
			System.out.print(" $t3 =" + RegFile.$t3);
			System.out.print(" $t4 =" + RegFile.$t4);
			System.out.print(" $t5 =" + RegFile.$t5);
			System.out.println();
			System.out.println();

			Vector<Integer> toAdd = new Vector<>();
			toAdd.add(RegFile.$s0);
			toAdd.add(RegFile.$s1);
			toAdd.add(RegFile.$s2);
			toAdd.add(RegFile.$s3);
			toAdd.add(RegFile.$s4);
			toAdd.add(RegFile.$s5);
			toAdd.add(RegFile.$t0);
			toAdd.add(RegFile.$t1);
			toAdd.add(RegFile.$t2);
			toAdd.add(RegFile.$t3);
			toAdd.add(RegFile.$t4);
			toAdd.add(RegFile.$t5);
			toAdd.add(RegFile.$zero);
			regEveryCycle.add(toAdd);

			instsEveryCycle.add(toAddinsts);
		}

	}

	public static void main(String[] args) {
		readFile();
		run();
	}

	public static int getPc() {
		return pc;
	}

	public static void setInstructionMemoryAT(int idx, String assembly) {
		InstructionMemory[idx] = assembly;
	}

	public static void setPC(int pcIn) {
		pc = pcIn;
	}

	public static void setFetchq(QueueObj fetchq) {
		CPU.fetchq = fetchq;
	}

	public static void setDecodeq(QueueObj decodeq) {
		CPU.decodeq = decodeq;
	}

	public static void setExecuteq(QueueObj executeq) {
		CPU.executeq = executeq;
	}

	public static void setMemoryAccessq(QueueObj memoryAccessq) {
		CPU.memoryAccessq = memoryAccessq;
	}

	public static void setWritebackq(QueueObj writebackq) {
		CPU.writebackq = writebackq;
	}

	public static int[] getDataMemory() {
		return DataMemory;
	}

}
