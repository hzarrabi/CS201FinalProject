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
		UserNameField = new JTextField("username");
		passwordField = new JPasswordField("password");
		FirstNameField = new JTextField("first name");
		LastNameField = new JTextField("last name");
		passwordField2 = new JPasswordField("re-type password");
		EmailField = new JTextField("email");
		btnConfirm = new JButton("Confirm");
		cancel = new JButton("Cancel");
		title = new JLabel("Create Your Account");

	}
	
	private void createGUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,dim.width/3, dim.height);
		setVisible(true);
		setResizable(false);
		
		UserNameField.setPreferredSize(new Dimension(dim.width/3 - 50, 50));
		passwordField.setPreferredSize(new Dimension(dim.width/3 - 50, 50));
		title.setBackground(Color.CYAN);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setPreferredSize(new Dimension(dim.width/3-20, dim.height/5));
		btnConfirm.setPreferredSize(new Dimension(dim.width/6, dim.height/10));
		cancel.setPreferredSize(new Dimension(dim.width/6, dim.height/10));
		FirstNameField.setPreferredSize(new Dimension(dim.width/3 - 50, 50));
		LastNameField.setPreferredSize(new Dimension(dim.width/3 - 50, 50));
		EmailField.setPreferredSize(new Dimension(dim.width/3 - 50, 50));
		passwordField2.setPreferredSize(new Dimension(dim.width/3 - 50, 50));
		//guest.setPreferredSize(new Dimension(dim.width/3-20, dim.height/8));
		//newUser.setPreferredSize(new Dimension(dim.width/5, dim.height/8));
		//newUser.setBackground(Color.cyan);
		//newUser.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel main = new JPanel();
		main.add(title);
		main.add(FirstNameField);
		main.add(LastNameField);
		main.add(UserNameField);
		main.add(passwordField);
		main.add(passwordField2);
		main.add(EmailField);
		JPanel bottom = new JPanel();
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
		Box.createGlue();
		bottom.add(btnConfirm);
		Box.createGlue();
		bottom.add(cancel);
		Box.createGlue();
		main.add(bottom);
		add(main);
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
		}
//		cancel.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e)
//			{
//				dispose();
//				new FirstPageGUI();
//			}
//		});
}