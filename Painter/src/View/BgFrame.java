package View;
import javax.swing.*;

public class BgFrame extends JFrame{
	public JPanel bgPanel;
	public JTextField sizeText;
	public JLabel label;
	public JButton okButton;
	
	public BgFrame(){
		setTitle("Size");
		bgPanel = new JPanel();
		sizeText = new JTextField(8);
		label = new JLabel("Size : ");
		okButton = new JButton("OK");
		
		bgPanel.add(label);
		bgPanel.add(sizeText);
		bgPanel.add(okButton);
		
		setContentPane(bgPanel);
		
		setLocation(400,300);
		setSize(300,150);
        setResizable(false);
        setVisible(true);
	}
}
