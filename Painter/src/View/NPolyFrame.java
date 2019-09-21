package View;

import javax.swing.*;

public class NPolyFrame extends JFrame{
	public JPanel npPanel;
	public JTextField sizeText;
	public JLabel label;
	public JButton okButton2;
	
	public NPolyFrame(){
		setTitle("Polygon");
		npPanel = new JPanel();
		sizeText = new JTextField(8);
		label = new JLabel("N : ");
		okButton2 = new JButton("OK");
		
		npPanel.add(label);
		npPanel.add(sizeText);
		npPanel.add(okButton2);
		
		setContentPane(npPanel);
		
		setLocation(400,300);
		setSize(300,150);
        setResizable(false);
        setVisible(true);
	}
}
