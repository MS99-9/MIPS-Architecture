package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class StartView extends JFrame implements ActionListener {

	private JLabel start;
	private JButton startbutton;
	private JLabel welcome;

	public StartView() {
		this.validate();
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setTitle("MIPS Simulator");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		welcome = new JLabel("welcome to MIPS Simulator");
		welcome.setBounds(280, 170, 1000, 200);
		welcome.setFont(new Font(Font.MONOSPACED, Font.BOLD, 65));
		welcome.setForeground(Color.red);

		startbutton = new JButton();
		startbutton.setBounds(550, 480, 500, 100);
		startbutton.setContentAreaFilled(false);
		startbutton.setOpaque(false);
		startbutton.addActionListener(this);

		start = new JLabel();
		start.setLayout(null);
		start.setBounds(0, 0, 1500, 900);
		start.add(welcome);
		start.add(startbutton);

		ImageIcon iconstart = new ImageIcon("background.jpg");
		Image img2 = iconstart.getImage();
		Image resizedImage2 = img2.getScaledInstance(2000, 1000, java.awt.Image.SCALE_SMOOTH);
		ImageIcon icon2 = new ImageIcon(resizedImage2);
		start.setIcon(icon2);

		ImageIcon buttonstart1 = new ImageIcon("startbutton.gif");
		startbutton.setIcon(buttonstart1);

		this.add(start);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		new MainView();
		this.setVisible(false);
	}

	public static void main(String[] args) {
		new StartView();
	}

}
