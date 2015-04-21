
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.FlowLayout;

import javax.swing.JPasswordField;

import com.mysql.jdbc.PreparedStatement;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;


public class NewUserWindow extends JFrame
{

	private JTextField UserNameField;
	private JTextField EmailField;
	private JPanel panel;
	private JButton btnConfirm;
	private JButton btnNewButton;
	private JTextField FirstNameField;
	private JTextField LastNameField;
	private JPasswordField passwordField;
	private JPasswordField passwordField2;
	
	Connection connection;
	String dburl;
	String userName;
	String passWord;
	Connection conn;

	public NewUserWindow()
	{
		//connect();
		initialize();
		addActionListener();
	}

//	private void connect()
//	{
//		connection = null;
//		dburl = "jdbc:mysql://104.236.176.180:3306/cs201";
//		userName = "cs201";
//		passWord = "manishhostage";
//
//			 try {
//				 Class.forName("com.mysql.jdbc.Driver");
//				 conn = DriverManager.getConnection("jdbc:mysql://104.236.176.180/cs201", "cs201", "manishhostage");
//				 } catch (ClassNotFoundException e) {
//				 e.printStackTrace();
//				 } catch (SQLException e) {
//				 e.printStackTrace();
//				 }
//	}
	
	
	
	private void initialize()
	{
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("New User");
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel label = new JLabel("New User");
		label.setForeground(new Color(255, 105, 180));
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Segoe Marker", Font.PLAIN, 32));
		label.setAlignmentX(0.5f);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridwidth = 4;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 5;
		gbc_label.gridy = 0;
		add(label, gbc_label);
		
		FirstNameField = new JTextField("First Name");
		FirstNameField.setHorizontalAlignment(SwingConstants.LEFT);
		FirstNameField.setColumns(20);
		GridBagConstraints gbc_FirstNameField = new GridBagConstraints();
		gbc_FirstNameField.gridwidth = 4;
		gbc_FirstNameField.insets = new Insets(0, 0, 5, 5);
		gbc_FirstNameField.gridx = 5;
		gbc_FirstNameField.gridy = 2;
		add(FirstNameField, gbc_FirstNameField);
		
		LastNameField = new JTextField("Last Name");
		GridBagConstraints gbc_LastNameField = new GridBagConstraints();
		gbc_LastNameField.insets = new Insets(0, 0, 5, 5);
		gbc_LastNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_LastNameField.gridx = 6;
		gbc_LastNameField.gridy = 3;
		add(LastNameField, gbc_LastNameField);
		LastNameField.setColumns(10);
		
		UserNameField = new JTextField("UserName");
		UserNameField.setColumns(20);
		GridBagConstraints gbc_UserNameField = new GridBagConstraints();
		gbc_UserNameField.gridwidth = 3;
		gbc_UserNameField.insets = new Insets(0, 0, 5, 5);
		gbc_UserNameField.gridx = 5;
		gbc_UserNameField.gridy = 4;
		add(UserNameField, gbc_UserNameField);
		
		EmailField = new JTextField("Email");
		EmailField.setColumns(20);
		GridBagConstraints gbc_EmailField = new GridBagConstraints();
		gbc_EmailField.gridwidth = 3;
		gbc_EmailField.insets = new Insets(0, 0, 5, 5);
		gbc_EmailField.gridx = 5;
		gbc_EmailField.gridy = 5;
		add(EmailField, gbc_EmailField);
		
		passwordField = new JPasswordField("Password");
		passwordField.setEchoChar((char)0);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 6;
		gbc_passwordField.gridy = 6;
		add(passwordField, gbc_passwordField);
		
		passwordField2 = new JPasswordField("Retype Password");
		passwordField2.setEchoChar((char)0);
		GridBagConstraints gbc_passwordField2 = new GridBagConstraints();
		gbc_passwordField2.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField2.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField2.gridx = 6;
		gbc_passwordField2.gridy = 7;
		add(passwordField2, gbc_passwordField2);
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 4;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 4;
		gbc_panel.gridy = 8;
		getContentPane().add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.setForeground(new Color(0, 128, 0));
		panel.add(btnConfirm);
		
		btnNewButton = new JButton("Cancel");
		btnNewButton.setForeground(new Color(255, 0, 0));
		panel.add(btnNewButton);
		
		setVisible(true);
	}
	
	private void addActionListener ()
	{
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

}
