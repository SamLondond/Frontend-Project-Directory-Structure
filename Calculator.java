

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Calculator extends JFrame implements ActionListener {
	private static final long serialVersionUID = -2297903483596263990L;

	final String KEYS[] = { " ", "Backspace", "CE", "C", " ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+",
			"-", "*", "/", "=", ".", "kxfh", "int", "...", "+/-" };
	/*final String str1[] = { "二进制", "八进制", "十进制", "十六进制" };*/
	final String str2[] = { "sin", "cos", "tan", "log", "x^y", "x^3", "∫", "n!", "", " ", " ", " ", };

	private JButton keys[] = new JButton[KEYS.length];
/*
	JRadioButton jr[] = new JRadioButton[str1.length];
*/
	ButtonGroup group = new ButtonGroup();
	JButton btn1[] = new JButton[str2.length];
	private JTextField resultText = new JTextField("0");
	private boolean firstDigit = true;
	private double resultNum = 0.0;
	private String operator = "=";
	private boolean operateValidFlag = true;

	public Calculator() {
		super();
		init();
		this.setBackground(Color.LIGHT_GRAY);
		this.setTitle("MyCalculator");
		this.setLocation(250, 200);
		this.setResizable(false);
		this.pack();
	}

	private void init() {
		resultText.setHorizontalAlignment(JTextField.RIGHT);
		resultText.setEditable(false);
		resultText.setBackground(Color.WHITE);
		JPanel calckeysPanel = new JPanel();
		calckeysPanel = new JPanel(new GridLayout(5, 5, 3, 3));
		for (int i = 0; i < KEYS.length; i++) {
			keys[i] = new JButton(KEYS[i]);
			calckeysPanel.add(keys[i]);
			keys[i].addActionListener(this);
		}
		keys[1].setPreferredSize(new Dimension(100, 40));
		/*for (int j = 0; j < str1.length; j++) {
			jr[j] = new JRadioButton(str1[j]);
			group.add(jr[j]);
			jr[j].addActionListener(this);
		}*/
		for (int z = 0; z < str2.length; z++) {
			btn1[z] = new JButton("" + str2[z]);
			btn1[z].setContentAreaFilled(true);
			btn1[z].addActionListener(this);
		}

		btn1[1].setPreferredSize(new Dimension(50, 30));
		JPanel p2 = new JPanel();
		p2 = new JPanel(new GridLayout(1, 4, 5, 10));
		/*for (int x = 0; x < str1.length; x++) {
			p2.add(jr[x]);
		}*/
		JPanel p3 = new JPanel();
		p3 = new JPanel(new GridLayout(4, 3, 35, 20));
		for (int y = 0; y < str2.length; y++) {
			p3.add(btn1[y]);
		}

		JPanel p4 = new JPanel();
		p4 = new JPanel(new BorderLayout());
		p4.add(p2, "North");
		p4.add(p3, "Center");
		JPanel panel1 = new JPanel();
		panel1.add("West", calckeysPanel);
		panel1.add("West", p4);
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		top.add("Center", resultText);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add("North", top);
		getContentPane().add("Center", panel1);
	}

	public void actionPerformed(ActionEvent e) {

		String label = e.getActionCommand();
		if (label.equals(KEYS[1])) {
			handleBackspace();
		} else if (label.equals(KEYS[2])) {
			resultText.setText("0");
		} else if (label.equals(KEYS[3])) {
			handleC();
		} else if ("0123456789.".indexOf(label) >= 0) {
			handleNumber(label);
		} else if (e.getActionCommand() == "二进制") {
			int n = Integer.parseInt(resultText.getText());
			resultText.setText(Integer.toBinaryString(n));
			if(e.getActionCommand() == "八进制"){
				
			}
		} else if (e.getActionCommand() == "八进制") {
			int n = Integer.parseInt(resultText.getText());
			resultText.setText(Integer.toOctalString(n));
		} else if (e.getActionCommand() == "十六进制") {
			int n = Integer.parseInt(resultText.getText());
			resultText.setText(Integer.toHexString(n));
		} else {
			handleOperator(label);
		}
	}

	private void handleBackspace() {
		String text = resultText.getText();
		int i = text.length();
		if (i > 0) {

			text = text.substring(0, i - 1);
			if (text.length() == 0) {
				resultText.setText("0");
				firstDigit = true;
				operator = "=";
			} else {
				resultText.setText(text);
			}
		}
	}

	private void handleNumber(String key) {
		if (firstDigit) {
			resultText.setText(key);
		} else if ((key.equals(".")) && (resultText.getText().indexOf(".") < 0)) {
			resultText.setText(resultText.getText() + ".");
		} else if (!key.equals(".")) {
			resultText.setText(resultText.getText() + key);
		}
		firstDigit = false;
	}

	private void handleC() {

		resultText.setText("0");
		firstDigit = true;
		operator = "=";
	}

	private void handleOperator(String key) {
		if (operator.equals("/")) {

			if (getNumberFromText() == 0.0) {

				operateValidFlag = false;
				resultText.setText("除数不能为零");
			} else {
				resultNum /= getNumberFromText();
			}
		} else if (operator.equals("n!")) {
			if (resultNum % 1 == 0) {

				if (getNumberFromText() == 0.0) {

					resultNum = 1;
				} else {
					for (int i = 0; i <= resultNum; i++) {
						int sum = 1;
						for (int j = 1; j <= i; j++) {
							sum *= j;
							resultNum = sum;
						}

					}

				}
			} else
				resultText.setText("阶乘只能是正整数");

		} else if (operator.equals("+")) {
			resultNum += getNumberFromText();
		} else if (operator.equals("-")) {
			resultNum -= getNumberFromText();
		} else if (operator.equals("*")) {
			resultNum *= getNumberFromText();
		} else if (operator.equals("+/-")) {
			resultNum = resultNum * (-1);
		} else if (operator.equals("sin")) {
			double dd = 	Math.toRadians(resultNum);
			resultNum = Math.sin(dd);
		} else if (operator.equals("cos")) {
		  double dd = 	Math.toRadians(resultNum);
			resultNum = Math.cos(dd);
		} else if (operator.equals("tan")) {
			double dd = 	Math.toRadians(resultNum);
			resultNum = Math.tan(dd);
		} else if (operator.equals("log")) {
			resultNum = Math.log(resultNum);
		} else if (operator.equals("∫")) {
			resultNum = Math.sqrt(Double.valueOf(resultNum));
		} else if (operator.equals("x^3")) {
			resultNum = resultNum * resultNum * resultNum;
		} else if (operator.equals("int")) {
			resultNum = (int) resultNum;
		} else if (operator.equals("x^y")) {
			// resultNum = Math.pow(x,y);
		} else if (operator.equals("kxfh")) {
			resultText.setText(String.format(String.valueOf(resultNum)));

		} else if (operator.equals("=")) {
			resultNum = getNumberFromText();
		}
		if (operateValidFlag) {
			long t1;
			double t2;
			t1 = (long) resultNum;
			t2 = resultNum - t1;
			if (t2 == 0) {
				resultText.setText(String.valueOf(t1));
			} else {
				resultText.setText(String.valueOf(resultNum));
			}
		}

		operator = key;
		firstDigit = true;
		operateValidFlag = true;
	}

	private double getNumberFromText() {
		double result = 0;
		try {
			result = Double.valueOf(resultText.getText()).doubleValue();
		} catch (NumberFormatException e) {
		}
		return result;
	}

	public static void main(String args[]) {

		Calculator calculator1 = new Calculator();
		calculator1.setVisible(true);
		calculator1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
