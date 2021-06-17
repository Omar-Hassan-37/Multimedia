import java.awt.EventQueue;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Interface {
	private HashMap<String, Category> cat_Table = new HashMap<String, Category>();
	private JFrame frame;
	private JTextField textField;
	private Operations op;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface window = new Interface();
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
	public Interface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 792, 503);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(199, 83, 399, 31);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblText = new JLabel("Text");
		lblText.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		lblText.setBounds(102, 83, 87, 22);
		frame.getContentPane().add(lblText);
		
		
		
		
		  JButton btndecompress = new JButton("Decompress");
		  btndecompress.setVisible(false); 
		  btndecompress.setFont(new Font("Tahoma", Font.BOLD,15)); 
		  btndecompress.setBounds(323, 174, 133, 45);
		 
		frame.getContentPane().add(btndecompress);
		JButton btnCompress = new JButton("Compress");
		btnCompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int counter = 0;
				for(int i = 1;i<=5;i++) {
					int temp = (int)Math.pow(2, counter);
					for(int j = (int) Math.pow(2 , i)-1;j>=temp;j--) {
						String binary = Integer.toBinaryString(j);
						String tempBinary = binary;
						String negative_Binary = "";
						for(int k = 0;k<tempBinary.length();k++) {
							String symbol = Character.toString(tempBinary.charAt(k));
							if(symbol.equals("0")) {
								negative_Binary+="1";
							}
							else
							{
								negative_Binary+="0";
							}
						}
						cat_Table.put(String.valueOf(j), new Category(i , binary));
						cat_Table.put(String.valueOf(j*-1), new Category(i , negative_Binary));
						
					}
					counter++;
				}
				 op = new Operations(cat_Table, textField.getText());
				op.Compress();
				textField.setVisible(false);
				lblText.setVisible(false);
				btnCompress.setVisible(false);
				btndecompress.setVisible(true);
			}
		});
		btnCompress.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCompress.setBounds(323, 174, 133, 45);
		frame.getContentPane().add(btnCompress);
		btndecompress.addActionListener(new ActionListener() { 
			 public void actionPerformed(ActionEvent e) {
				 	op.Decompress(); 
			 	} 
			 }); 
	}
}
