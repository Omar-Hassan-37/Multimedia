import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ArithmeticCode {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArithmeticCode window = new ArithmeticCode();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ArithmeticCode() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 764, 441);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNumberOfLetters = new JLabel("number of letters");
		lblNumberOfLetters.setBounds(34, 34, 108, 16);
		frame.getContentPane().add(lblNumberOfLetters);
		
		JLabel lblLetter = new JLabel("letter");
		lblLetter.setBounds(34, 76, 56, 16);
		frame.getContentPane().add(lblLetter);
		
		JLabel lblProbability = new JLabel("probability");
		lblProbability.setBounds(34, 123, 71, 16);
		frame.getContentPane().add(lblProbability);
		
		textField = new JTextField();
		textField.setBounds(202, 31, 116, 22);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(202, 73, 116, 22);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(202, 120, 116, 22);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnAdd = new JButton("add");
		btnAdd.setBounds(466, 72, 97, 25);
		frame.getContentPane().add(btnAdd);
		
		JButton btnCompress = new JButton("compress");
		btnCompress.setBounds(45, 305, 97, 25);
		frame.getContentPane().add(btnCompress);
		
		JButton btnDecompress = new JButton("decompress");
		btnDecompress.setBounds(273, 305, 116, 25);
		frame.getContentPane().add(btnDecompress);
	}
}
