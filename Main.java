import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;

class Login extends Template implements ActionListener, Runnable
{
   JButton SUBMIT;
   JLabel label1 , label2, label3 ,TopLabel;
   final JTextField text1,text2;
   private static int attempt = 3;
   
   Login()
   {

    TopLabel = new JLabel();
    TopLabel.setHorizontalAlignment(SwingConstants.CENTER);
    TopLabel.setText("Packer Unpacker: Login");
    TopLabel.setForeground(Color.BLUE);
	
    Dimension topsize = TopLabel.getPreferredSize();
    TopLabel.setBounds(50,40, topsize.width, topsize.height);
    _header.add(TopLabel);

	label1 = new JLabel();
	label1.setText("Username:");	
    label1.setForeground(Color.white);

	Dimension size = label1.getPreferredSize();
	label1.setBounds(50,135,size.width,size.height);
	
	label1.setHorizontalAlignment(SwingConstants.CENTER);
	
	text1 = new JTextField(15);
	Dimension tsize = text1.getPreferredSize();
	text1.setBounds(200,135, tsize.width, tsize.height);
	
	text1.setToolTipText("ENTER USERNAME");
	
	label2 = new JLabel();
    label2.setText("Password");
	label2.setBounds(50,175, size.width, size.height);
	label2.setForeground(Color.white);
	label2.setHorizontalAlignment(SwingConstants.CENTER);

	text2 = new JPasswordField(15);
    text2.setBounds(200, 175, tsize.width, tsize.height);
	text2.setToolTipText("ENTER PASSWORD");
	
	text2.addFocusListener(new FocusListener()
	{
		public void focusGained(FocusEvent e)
		{ }
		public void focusLost(FocusEvent e)
		{
			label3.setText("");
		}
	});

	SUBMIT = new JButton("SUBMIT");
	SUBMIT.setHorizontalAlignment(SwingConstants.CENTER);
	
	Dimension ssize = SUBMIT.getPreferredSize();
	
    SUBMIT.setBounds(50,220,ssize.width,ssize.height);
	
   label3 = new JLabel();
   label3.setText("");

   Dimension l3size = label3.getPreferredSize();
   label3.setForeground(Color.RED);
   label3.setBounds(50,250 ,l3size.width,l3size.height);


   Thread t = new Thread(this);
   t.start();

	_content.add(label1);
	_content.add(text1);
	
	_content.add(label2);
	_content.add(text2);

	_content.add(label3);
	_content.add(SUBMIT);
	
	
	
	pack();
	validate();
	
	ClockHome();
	setVisible(true);
	this.setSize(500,500);
	this.setResizable(false);
	setLocationRelativeTo(null);
	SUBMIT.addActionListener(this);
   }
   
   public boolean Validate(String Username,String password)
   {
	   if((Username.length()<8) || (password.length()<8))
		   return false;
	   else
		   return true;
   }
   
   public void actionPerformed(ActionEvent ae)
   {
	String value1 = text1.getText();
	String value2 = text2.getText();
	
	if(ae.getSource() == exit )
	{
		this.setVisible(false);
		System.exit(0);
	}
	
	if (ae.getSource() == minimize)
	{
		this.setState(this.ICONIFIED);
	}
	if(ae.getSource() == SUBMIT)
	{
		if(Validate(value1,value2)== false)
		{
			text1.setText("");
			text2.setText("");
			JOptionPane.showMessageDialog(this, "Short username", "Packer-Unpacker", JOptionPane.ERROR_MESSAGE);
		}
		if(value1.equals("PackerAdmin") && value2.equals("PackerAdmin"))  // Change this
		{
			NextPage page = new NextPage(value1);
			this.setVisible(false);
			page.pack();
			page.setVisible(true);
			page.setSize(500,500);
		}
		else
		{
			attempt--;
			if(attempt==0)
			{
				JOptionPane.showMessageDialog(this,"Number of attempts finished","Packer-Unpacker",JOptionPane.ERROR_MESSAGE);
				this.dispose();
				System.exit(0);
			}
			
			JOptionPane.showMessageDialog(this,"Incorrect login or password","Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	}

	public void run()
	{
		for(;;)
		{
			if(text2.isFocusOwner())
			{
				if(Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK))
				{
					text2.setToolTipText("Warning : CAPS is ON");
				}
				else
				{
					text2.setToolTipText("");
				}
				if((text2.getText()).length()<8)
				{
					label3.setText("Weak password");
				}
				else
				{
					label3.setText("");
				}
			}
		}
	}
	
	
	
}
class Main
{
	public static void main(String args[])
	{
		/*Scanner scan = new Scanner(System.in);
		int option;
		int ans;
		do
		{
		System.out.println("Select options :\n\t1)Pack files\n\t2)Unpack files");
		option = scan.nextInt();
		switch(option)
		{
			case 1:
			
			System.out.print("\nEnter the folder name from where you want to pack the files : ");
			String srcfolder = scan.next();
		
			System.out.print("\nEnter the file name into which the files are to be packed : ");
			String destFile = scan.next();
			
			Packing pack = new Packing(srcfolder,destFile);
			
			break;
			
			case 2:
			
			System.out.print("Enter the file name that is to be unpacked : ");
			String srcfile = scan.next();
		
			Unpacking unpack = new Unpacking(srcfile);
			break;
			
			default:
			System.out.println("Invalid option entered !!!");
		}
		System.out.print("Do you want to continue : Yes(1) No(0) : ");
		ans = scan.nextInt();
		}while(ans == 1);*/
		
		try
		{
			Login frame = new Login();
			frame.setVisible(true);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
		
	}
}