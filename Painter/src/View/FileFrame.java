package View;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileFrame extends JFrame{
	public JPanel filePanel;
	public JTextField fileName;
	public JLabel label;
	public JButton okButton3;
	
	public FileFrame(){
		setTitle("저장, 불러오기");
		filePanel = new JPanel();
		fileName = new JTextField(8);
		label = new JLabel("ㅇㅇ : ");
		okButton3 = new JButton("OK");
		
		filePanel.add(label);
		filePanel.add(fileName);
		filePanel.add(okButton3);
		
		setContentPane(filePanel);
		
		setLocation(400,300);
		setSize(300,150);
        setResizable(false);
        setVisible(true);
	}
}