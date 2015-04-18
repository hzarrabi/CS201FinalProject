import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;

import com.mysql.jdbc.PreparedStatement;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class CreateUserGUI extends JFrame{
	
	private Dimension dim;
	private JTextField UserNameField;
	private JPasswordField passwordField;
	private JTextField FirstNameField;
	private JTextField LastNameField;
	private JPasswordField passwordField2;
	private JTextField EmailField;
	private JButton btnConfirm;
	private JButton cancel;
	private JLabel title;
	private JPanel bottomColor;
	private JPanel topColor;
	//fix this cause its bad coding style
	Connection connection;
	String dburl;
	String userName;
	String passWord;
	Connection conn;

	
	public CreateUserGUI(){
		super("Create your Account!");
		connect();
		initializeComponents();
		createGUI();
		setEventHandlers();
	}
	
	private void connect(){
		connection = null;
		dburl = "jdbc:mysql://104.236.176.180:3306/cs201";
		userName = "cs201";
		passWord = "manishhostage";

			 try {
				 Class.forName("com.mysql.jdbc.Driver");
				 conn = DriverManager.getConnection("jdbc:mysql://104.236.176.180/cs201", "cs201", "manishhostage");
				 } catch (ClassNotFoundException e) {
				 e.printStackTrace();
				 } catch (SQLException e) {
				 e.printStackTrace();
				 }
	}
	
	private void initializeComponents(){
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		UserNameField = new JTextField("UserName");
		passwordField = new JPasswordField("Password");
		passwordField.setEchoChar((char)0);
		passwordField2 = new JPasswordField("Retype Password");
		passwordField2.setEchoChar((char)0);
		FirstNameField = new JTextField("First Name");
		LastNameField = new JTextField("Last Name");
		EmailField = new JTextField("Email");
		btnConfirm = new JButton("Confirm");
		cancel = new JButton("Cancel");
		title = new JLabel("Create Your Account");

	}
	
	private void createGUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,dim.width/3, dim.height);
		setResizable(false);
		
		bottomColor = new JPanel();
		bottomColor.setPreferredSize(new Dimension(dim.width/3, dim.height/20));
		bottomColor.setBackground(FirstPageGUI.color);
		topColor = new JPanel();
		topColor.setPreferredSize(new Dimension(dim.width/3, dim.height/15));
		topColor.setBackground(FirstPageGUI.color);
		UserNameField.setPreferredSize(new Dimension(dim.width/4, dim.height/15));
		passwordField.setPreferredSize(new Dimension(dim.width/4, dim.height/15));
		title.setBackground(Color.CYAN);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		btnConfirm.setPreferredSize(new Dimension(dim.width/4, dim.height/15));
		cancel.setPreferredSize(new Dimension(dim.width/4, dim.height/15));
		FirstNameField.setPreferredSize(new Dimension(dim.width/4, dim.height/15));
		LastNameField.setPreferredSize(new Dimension(dim.width/4, dim.height/15));
		EmailField.setPreferredSize(new Dimension(dim.width/4, dim.height/15));
		passwordField2.setPreferredSize(new Dimension(dim.width/4, dim.height/15));
		
		passwordField2.setBorder(new RoundedBorder());
		passwordField.setBorder(new RoundedBorder());
		UserNameField.setBorder(new RoundedBorder());
		btnConfirm.setBorder(new RoundedBorder());
		cancel.setBorder(new RoundedBorder());
		FirstNameField.setBorder(new RoundedBorder());
		LastNameField.setBorder(new RoundedBorder());
		EmailField.setBorder(new RoundedBorder());
		
		passwordField.setOpaque(true);
		passwordField.setFont(FirstPageGUI.font);
		passwordField2.setFont(FirstPageGUI.font);
		UserNameField.setFont(FirstPageGUI.font);
		FirstNameField.setFont(FirstPageGUI.font);
		LastNameField.setFont(FirstPageGUI.font);
		EmailField.setFont(FirstPageGUI.font);
		btnConfirm.setFont(FirstPageGUI.font);
		cancel.setFont(FirstPageGUI.font);
		
		passwordField2.setBackground(FirstPageGUI.grey);
		passwordField.setBackground(FirstPageGUI.grey);
		UserNameField.setBackground(FirstPageGUI.grey);
		btnConfirm.setBackground(FirstPageGUI.color);
		cancel.setBackground(FirstPageGUI.green);
		btnConfirm.setOpaque(true);
		cancel.setOpaque(true);
		FirstNameField.setBackground(FirstPageGUI.grey);
		LastNameField.setBackground(FirstPageGUI.grey);
		EmailField.setBackground(FirstPageGUI.grey);
		
		title.setFont(FirstPageGUI.fontTitle);
		title.setForeground(FirstPageGUI.white);
		passwordField2.setForeground(FirstPageGUI.lightGrey);
		passwordField.setForeground(FirstPageGUI.lightGrey);
		UserNameField.setForeground(FirstPageGUI.lightGrey);
		btnConfirm.setForeground(FirstPageGUI.white);
		cancel.setForeground(FirstPageGUI.white);
		FirstNameField.setForeground(FirstPageGUI.lightGrey);
		LastNameField.setForeground(FirstPageGUI.lightGrey);
		EmailField.setForeground(FirstPageGUI.lightGrey);
		//guest.setPreferredSize(new Dimension(dim.width/3-20, dim.height/8));
		//newUser.setPreferredSize(new Dimension(dim.width/5, dim.height/8));
		//newUser.setBackground(Color.cyan);
		//newUser.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel main = new JPanel();
		main.setLayout(new FlowLayout(FlowLayout.CENTER, dim.width, dim.height/40));
		main.setPreferredSize(new Dimension(dim.width/3, 15*dim.height/20));
		//main.add(title);
		main.setBackground(FirstPageGUI.white);
		main.add(FirstNameField);
		main.add(LastNameField);
		main.add(UserNameField);
		main.add(passwordField);
		main.add(passwordField2);
		main.add(EmailField);
		//JPanel bottom = new JPanel();
		//bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
		//Box.createGlue();
		main.add(btnConfirm);
		//Box.createGlue();
		main.add(cancel);
		//Box.createGlue();
		//main.add(bottom);
		topColor.add(title);
		add(topColor, BorderLayout.NORTH);
		add(main, BorderLayout.CENTER);
		add(bottomColor, BorderLayout.SOUTH);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void setEventHandlers(){
		btnConfirm.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Boolean FirstNameEmpty = true;
				Boolean LastNameEmpty = true;
				Boolean UserNameEmpty = true;
				Boolean EmailEmpty = true;
				Boolean Password = true;
				Boolean Password2 = true;
				
				if(FirstNameField.getText().length()>11 || FirstNameField.getText().length()<5) FirstNameEmpty=false;
				if(LastNameField.getText().length()>11 || LastNameField.getText().length()<5) LastNameEmpty=false;
				if(UserNameField.getText().length()>11 || UserNameField.getText().length()<5) UserNameEmpty=false;
				if(EmailField.getText().length()>30 || EmailField.getText().length()<5) EmailEmpty=false;
				if(passwordField.getPassword().length>11 || passwordField2.getPassword().length<5) Password=false;
				if(passwordField2.getPassword().length>11 || passwordField2.getPassword().length<5) Password2=false;
			
				if(FirstNameEmpty && LastNameEmpty && UserNameEmpty && EmailEmpty && Password && Password2)
				{
					String userName=UserNameField.getText();
					try
					{
						Statement st = conn.createStatement();
						String queryCheck = "SELECT * from user_table WHERE username = ?";
						PreparedStatement ps = (PreparedStatement) conn.prepareStatement(queryCheck);
						ps.setString(1, userName);
						ResultSet rs = ps.executeQuery();
						if(rs.absolute(1))
						{
							System.out.println("already exists!");
						}
						else
						{
							Boolean match=Arrays.equals(passwordField.getPassword(),passwordField2.getPassword());
							if(Arrays.equals(passwordField.getPassword(),passwordField2.getPassword()))
							{
								String password=new String(passwordField.getPassword());
								System.out.println("password is "+password);
								
								
								 PreparedStatement stmt = (PreparedStatement) conn.prepareStatement("insert into user_table (first_name, last_name, email, username, password) values (?, ?, ?, ?, ?)");
								 stmt.setString(1, FirstNameField.getText());
								 stmt.setString(2, LastNameField.getText());
								 stmt.setString(3, EmailField.getText());
								 stmt.setString(4, UserNameField.getText());
								 stmt.setString(5,PasswordHash.hash(password));
								 stmt.execute();
								 
								 //TODO open new page and close connection to db
								 
								 System.out.println("new user added!");
							}
							else System.out.println("passwords to not match");
						}
						
					} catch (SQLException e1)
					{
						e1.printStackTrace();
					}
				}
				
				else
				{
					System.out.println("incorrect characters");
				}
			
			}
		});
		
			passwordField2.addFocusListener(new FocusListener()
			{
				@Override
				public void focusLost(FocusEvent e)
				{
					if(Arrays.equals("".toCharArray(), passwordField2.getPassword()))
					{
						passwordField2.setEchoChar((char)0);
						passwordField2.setText("Retype Password");
					}
				}
				@Override
				public void focusGained(FocusEvent e)
				{
					if(Arrays.equals("Retype Password".toCharArray(), passwordField2.getPassword()))
					{
						passwordField2.setText("");
						passwordField2.setEchoChar('*');
					}
				}
			});
			
			
			passwordField.addFocusListener(new FocusListener()
			{
				@Override
				public void focusLost(FocusEvent e)
				{
					if(Arrays.equals("".toCharArray(), passwordField.getPassword()))
					{
						passwordField.setEchoChar((char)0);
						passwordField.setText("Password");
					}
				}
				@Override
				public void focusGained(FocusEvent e)
				{
					if(Arrays.equals("Password".toCharArray(), passwordField.getPassword()))
					{
						passwordField.setText("");
						passwordField.setEchoChar('*');
					}
				}
			});
			
			
			EmailField.addFocusListener(new FocusListener()
			{
				@Override
				public void focusLost(FocusEvent e)
				{
					if(EmailField.getText().equals(""))
					{
						EmailField.setText("Email");
					}
				}
				@Override
				public void focusGained(FocusEvent e)
				{
					if(EmailField.getText().equals("Email"))
					{
						EmailField.setText("");
					}
				}
			});
			
			
			UserNameField.addFocusListener(new FocusListener()
			{
				@Override
				public void focusLost(FocusEvent e)
				{
					if(UserNameField.getText().equals(""))
					{
						UserNameField.setText("UserName");
					}
				}
				@Override
				public void focusGained(FocusEvent e)
				{
					if(UserNameField.getText().equals("UserName"))
					{
						UserNameField.setText("");
					}
				}
			});
			
			
			LastNameField.addFocusListener(new FocusListener()
			{
				@Override
				public void focusLost(FocusEvent e)
				{
					if(LastNameField.getText().equals(""))
					{
						LastNameField.setText("Last Name");
					}
				}
				@Override
				public void focusGained(FocusEvent e)
				{
					if(LastNameField.getText().equals("Last Name"))
					{
						LastNameField.setText("");
					}
				}
			});
			
			FirstNameField.addFocusListener(new FocusListener()
			{
				@Override
				public void focusLost(FocusEvent e)
				{
					if(FirstNameField.getText().equals(""))
					{
						FirstNameField.setText("First Name");
					}
				}
				@Override
				public void focusGained(FocusEvent e)
				{
					if(FirstNameField.getText().equals("First Name"))
					{
						FirstNameField.setText("");
					}
				}
			});
			cancel.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					new FirstPageGUI();
					dispose();
				}
			});
		}
}