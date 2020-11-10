import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PackFront extends Template implements ActionListener
{
	JButton SUBMIT,PREVIOUS;
	JLabel label1,label2,title;
	final JTextField text1,text2;
	
	public PackFront()
	{
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		title = new JLabel("Packing Portal");
		Dimension size = title.getPreferredSize();
		
		title.setBounds(40,50,size.width+60,size.height);
		title.setFont(new Font("Century",Font.BOLD,17));
		title.setForeground(Color.blue);
		
		label1 = new JLabel();
		label1.setText("Directory name");
		label1.setForeground(Color.white);
		label1.setBounds(350,50,size.width,size.height);
		
		text1 = new JTextField(15);
		Dimension tsize = text1.getPreferredSize();
		text1.setBounds(500,50,tsize.width,tsize.height);
		text1.setToolTipText("Enter name of the directory");
		
		label2 = new JLabel();
		label2.setText("Destination file name");
		label2.setForeground(Color.white);
		label2.setBounds(350,100,size.width+60,size.height);
		
		text2 = new JTextField(15);
		text2.setBounds(500,100,tsize.width,tsize.height);
		text2.setToolTipText("Enter destination file name");
		
		SUBMIT = new JButton("SUBMIT");
		
		Dimension bsize = SUBMIT.getPreferredSize();
		SUBMIT.setBounds(350,200,bsize.width,bsize.height);
		SUBMIT.addActionListener(this);
		
		PREVIOUS = new JButton("PREVIOUS");
		Dimension b2size = PREVIOUS.getPreferredSize();
		PREVIOUS.setBounds(500,200,b2size.width,b2size.height);
		PREVIOUS.addActionListener(this);
		
		_header.add(title);
		_content.add(label1);
		_content.add(label2);
		_content.add(text1);
		_content.add(text2);
		_content.add(SUBMIT);
		_content.add(PREVIOUS);
		
		this.setSize(1000,400);
		this.setResizable(false);
		this.setVisible(true);
		text1.requestFocusInWindow();
		
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == SUBMIT)
		{
			try
			{
				Pack pack = new Pack(text1.getText(),text2.getText());  // Object for packing
				this.dispose();
				NextPage t = new NextPage("PackerAdmin");  // Change this 
				
			}
			catch(Exception e)
			{}
		}
		if(ae.getSource() == PREVIOUS)
		{
			this.setVisible(false);
			this.dispose();
			NextPage t = new NextPage("PackerAdmin");
		}
	}
}