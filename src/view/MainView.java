package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;

import control.CPU;

@SuppressWarnings("serial")
public class MainView extends JFrame {
	private JTextArea assemblyInput;
	private JScrollPane assemblyInSP;
	private JTextArea allInstructions;
	private JScrollPane instscroll;
	JButton run;
	JScrollPane myscroll;
	JLabel output;

	public MainView() {

		this.validate();
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setLayout(null);
		this.setTitle("Mips Simulator");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		assemblyInput = new JTextArea();
		assemblyInput.setBorder(BorderFactory.createTitledBorder("Type your code here..."));
		assemblyInput.setLineWrap(true);
		assemblyInput.setBackground(Color.white);
		assemblyInput.setFont(new Font("Arial", Font.BOLD, 20));

		assemblyInSP = new JScrollPane(assemblyInput);
		assemblyInSP.setBounds(20, 620, 650, 160);
		this.getContentPane().add(assemblyInSP);

		output = new JLabel();
		output.setBorder(BorderFactory.createTitledBorder("Clock cycles"));
		output.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
		output.setBackground(Color.white);
		output.setForeground(Color.black);
		output.setText("" + "\n");
		output.setLayout(null);
		myscroll = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		myscroll.setBounds(20, 20, 1500, 590);
		this.add(myscroll);

		allInstructions = new JTextArea();
		allInstructions.setBorder(BorderFactory.createTitledBorder("All Instructions"));
		allInstructions.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
		allInstructions.setBackground(Color.white);
		allInstructions.setForeground(Color.black);
		allInstructions.setOpaque(false);
		allInstructions.setEditable(false);
		instscroll = new JScrollPane(allInstructions, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		instscroll.setBounds(890, 620, 630, 160);
		instscroll.setOpaque(false);
		this.add(instscroll);

		run = new JButton("Run");
		run.setBounds(680, 620, 200, 160);
		run.setForeground(Color.MAGENTA);
		run.setBackground(Color.LIGHT_GRAY);
		// run.setContentAreaFilled(false);
		run.setFont(new Font("Arial", Font.ITALIC, 70));

//		ImageIcon buttonrun = new ImageIcon("run.gif");
//		run.setIcon(buttonrun);

		run.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int i = 0;
				for (String line : assemblyInput.getText().split("\\n")) {
					CPU.setInstructionMemoryAT(i, CPU.change(line));
					i = i + 4;
				}
				CPU.run();

				int numOfColumns = (CPU.getPc() / 4) + 4;

				for (int j = 0; j < numOfColumns; j++) {
					JLabel newL = new JLabel();
					newL.setBorder(BorderFactory.createTitledBorder("Clock cycle " + (j + 1)));
					newL.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
					newL.setBackground(Color.red);
					newL.setForeground(Color.black);
					newL.setText("" + "\n");
					JScrollPane newS = new JScrollPane(newL, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
							JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					newS.setBounds(30 + j * 340, 30, 320, 530);
					output.add(newS);
					output.setText(output.getText() + "                                        ");
					JTextArea regs = new JTextArea();
					regs.setBorder(BorderFactory.createTitledBorder("Registers"));
					regs.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
					regs.setBackground(Color.LIGHT_GRAY);
					regs.setOpaque(false);
					regs.setForeground(Color.black);
					regs.setEditable(false);
					Vector<Integer> thisregfile = CPU.regEveryCycle.get(j);
					regs.setText("$s0 = " + thisregfile.get(0) + "             $t0 = " + thisregfile.get(6) + "\n"
							+ "$s1 = " + thisregfile.get(1) + "             $t1 = " + thisregfile.get(7) + "\n"
							+ "$s2 = " + thisregfile.get(2) + "             $t2 = " + thisregfile.get(8) + "\n"
							+ "$s3 = " + thisregfile.get(3) + "             $t3 = " + thisregfile.get(9) + "\n"
							+ "$s4 = " + thisregfile.get(4) + "             $t4 = " + thisregfile.get(10) + "\n"
							+ "$s5 = " + thisregfile.get(5) + "             $t5 = " + thisregfile.get(11) + "\n"
							+ "           $zero = " + thisregfile.get(12));
					regs.setBounds(10, 385, 297, 140);
					newL.add(regs);

					Vector<String> thisinsts = CPU.instsEveryCycle.get(j);
					JTextArea insts = new JTextArea();
					insts.setEditable(false);
					insts.setBorder(BorderFactory.createTitledBorder("Stages"));
					insts.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
					insts.setBackground(Color.LIGHT_GRAY);
					insts.setOpaque(false);
					insts.setForeground(Color.black);
					for (String toAdd : thisinsts) {
						insts.setText(insts.getText() + toAdd + "\n");
					}
					JScrollPane instsS = new JScrollPane(insts, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
							JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					instsS.setBounds(10, 20, 297, 360);
					newL.add(instsS);

				}

				for (int j = 0; j < CPU.allInstructions.size(); j++) {
					String thisInstruct = "Instruction in index " + j * 4 + " is " + CPU.allInstructions.get(j);
					allInstructions.setText(allInstructions.getText() + thisInstruct + "\n");
				}

				run.removeActionListener(this);
				run.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						JOptionPane.showMessageDialog(null, "The GUI will close now please run eclipse again");
						System.exit(EXIT_ON_CLOSE);
					}
				});

			}
		});
		this.add(run);

		// Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// this.setSize(screenSize.width, screenSize.height);

		this.setVisible(true);
		// this.revalidate();
		// this.pack();
		this.repaint();
	}

//	public static void main(String[] args) throws Exception {
//		new MainView();
//	}

}
