import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Quantizer {
	private JLabel lblImg = null;
	private JButton btnLoad = null;
	private JLabel lblImg2 = null;
	private BufferedImage img = null;
	private BufferedImage img2 = null;
	private JFrame frame;
	private JTextField bitsField;
	private JButton btnQuantize = null;
	private JTextField sizeField;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Quantizer window = new Quantizer();
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
	public Quantizer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1200, 660);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblImg = new JLabel("");
		lblImg.setBounds(10, 11, 374, 431);
		frame.getContentPane().add(lblImg);
		
		btnLoad = new JButton("Load");
		btnLoad.setBounds(66, 546, 89, 23);
		btnLoad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFileChooser chooser = new JFileChooser();
					File inFile = new File("");
					chooser.setCurrentDirectory(inFile.getAbsoluteFile());
					
					FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "jpg");
					chooser.addChoosableFileFilter(filter);
					chooser.setAcceptAllFileFilterUsed(false);
					filter = new FileNameExtensionFilter("png", "png");
					chooser.addChoosableFileFilter(filter);

					int option = chooser.showOpenDialog(null);
					
					if(option == JFileChooser.APPROVE_OPTION) {
						
					inFile = chooser.getSelectedFile();
					
					img = ImageIO.read(inFile);
					img2 = new BufferedImage(img.getWidth(), img.getHeight(), 10);
					for(int i =0; i<img.getWidth(); i++) {
						for(int j = 0;j<img.getHeight(); j++) {
							img2.setRGB(i, j, img.getRGB(i, j));
						}
					}
					lblImg.setIcon(new ImageIcon(img));
					lblImg2.setIcon(new ImageIcon(img2));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}

			
		});
		
		frame.getContentPane().add(btnLoad);
		
		lblImg2 = new JLabel("");
		lblImg2.setBounds(394, 11, 368, 431);
		frame.getContentPane().add(lblImg2);
		
		btnQuantize = new JButton("Quantize");
		btnQuantize.setBounds(258, 546, 89, 23);
		btnQuantize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				VectorQuantizer vq = new VectorQuantizer(img2);
				
				String bitsString = bitsField.getText();
				String vectorSizeString = sizeField.getText();
				int bits = 0;
				int size = 0;
				
				if(bitsString.equals("") || vectorSizeString.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill all fields");
					return;
				}
				else {
					bits = Integer.parseInt(bitsString);
					size = Integer.parseInt(vectorSizeString);
				}
				
				lblImg2.setIcon(new ImageIcon(vq.quantize(bits, size)));
				

			}
		});
		
		frame.getContentPane().add(btnQuantize);
		
		bitsField = new JTextField();
		bitsField.setBounds(362, 547, 45, 20);
		frame.getContentPane().add(bitsField);
		bitsField.setColumns(10);
		
		sizeField = new JTextField();
		sizeField.setBounds(417, 547, 45, 20);
		frame.getContentPane().add(sizeField);
		sizeField.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser chooser = new JFileChooser();
				File outFile = new File("");
				chooser.setCurrentDirectory(outFile.getAbsoluteFile());
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "jpg");
				chooser.addChoosableFileFilter(filter);
				chooser.setAcceptAllFileFilterUsed(false);
				filter = new FileNameExtensionFilter("png", "png");
				chooser.addChoosableFileFilter(filter);

				int option = chooser.showSaveDialog(null);
				
				if(option == JFileChooser.APPROVE_OPTION) {
					outFile = new File( chooser.getSelectedFile().getPath()+"." + chooser.getFileFilter().getDescription());
					try {
						ImageIO.write(img2, chooser.getFileFilter().getDescription(), outFile );
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btnSave.setBounds(515, 546, 89, 23);
		frame.getContentPane().add(btnSave);
		
		JLabel lblBits = new JLabel("Bits");
		lblBits.setBounds(361, 522, 46, 14);
		frame.getContentPane().add(lblBits);
		
		JLabel lblVectorSide = new JLabel("Vector Side");
		lblVectorSide.setBounds(417, 522, 98, 14);
		frame.getContentPane().add(lblVectorSide);
	}
}
