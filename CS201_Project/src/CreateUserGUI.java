import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
	private JLabel incorrectInput;
	//fix this cause its bad coding style
	Connection connection;
	String dburl;
	String userName;
	String passWord;
	Connection conn;
	//FirstPageGUI firstPage;
	
	public CreateUserGUI(){
		super("Create your Account!");
		this.conn=ConnectionClass.conn;
		//connect();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {
				//firstPage.setVisible(true);
				new FirstPageGUI();
				CreateUserGUI.this.dispose();		
			}

		});
		initializeComponents();
		createGUI();
		setEventHandlers();
	}
	
//	private void connect(){
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
	
	private void initializeComponents(){
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		incorrectInput = new JLabel("");
		UserNameField = new JTextField("Username");
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
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,dim.width/3, dim.height-80);
		setResizable(false);
		
		incorrectInput.setForeground(Color.RED);
		bottomColor = new JPanel();
		bottomColor.setPreferredSize(new Dimension(dim.width/3, dim.height/20));
		bottomColor.setBackground(FirstPageGUI.color);
		topColor = new JPanel();
		topColor.setPreferredSize(new Dimension(dim.width/3, dim.height/15));
		topColor.setBackground(FirstPageGUI.color);
		UserNameField.setPreferredSize(new Dimension(dim.width/4, dim.height/17));
		passwordField.setPreferredSize(new Dimension(dim.width/4, dim.height/17));
		title.setBackground(Color.CYAN);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		btnConfirm.setPreferredSize(new Dimension(dim.width/4, dim.height/17));
		cancel.setPreferredSize(new Dimension(dim.width/4, dim.height/17));
		FirstNameField.setPreferredSize(new Dimension(dim.width/4, dim.height/17));
		LastNameField.setPreferredSize(new Dimension(dim.width/4, dim.height/17));
		EmailField.setPreferredSize(new Dimension(dim.width/4, dim.height/17));
		passwordField2.setPreferredSize(new Dimension(dim.width/4, dim.height/17));
		
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
		JPanel bottom = new JPanel();
		bottom.add(incorrectInput);
		incorrectInput.setHorizontalAlignment(SwingConstants.CENTER);
		bottom.setPreferredSize(new Dimension(dim.width/3, dim.height/20));
		main.setLayout(new FlowLayout(FlowLayout.CENTER, dim.width, dim.height/40));
		main.setPreferredSize(new Dimension(dim.width/3, 14*dim.height/20));
		//main.add(title);
		main.setBackground(FirstPageGUI.white);
		//JTextField first = new JTextField();
		//first.setVisible(true);//we do this because we don't want focus on first textfield initially
		add(new JTextField());
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
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(FirstPageGUI.white);
		mainPanel.add(main, BorderLayout.CENTER);
		bottom.setBackground(FirstPageGUI.white);
		mainPanel.add(bottom, BorderLayout.SOUTH);
		add(topColor, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
		add(bottomColor, BorderLayout.SOUTH);
		setBackground(FirstPageGUI.white);
		repaint();
		setVisible(true);
	}
	
	private void setEventHandlers(){
		btnConfirm.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				newUserAction();
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
						passwordField2.setForeground(FirstPageGUI.lightGrey);
					}
				}
				@Override
				public void focusGained(FocusEvent e)
				{
					if(Arrays.equals("Retype Password".toCharArray(), passwordField2.getPassword()))
					{
						passwordField2.setText("");
						passwordField2.setEchoChar('*');
						passwordField2.setForeground(FirstPageGUI.darkGrey);
					}
				}
			});
			
			passwordField2.addKeyListener(new KeyListener()
			{
				@Override
				public void keyTyped(KeyEvent e){}
				@Override
				public void keyReleased(KeyEvent e){}
				@Override
				public void keyPressed(KeyEvent e)
				{
					if(e.getKeyChar() == KeyEvent.VK_ENTER)newUserAction();
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
						passwordField.setForeground(FirstPageGUI.lightGrey);
					}
				}
				@Override
				public void focusGained(FocusEvent e)
				{
					if(Arrays.equals("Password".toCharArray(), passwordField.getPassword()))
					{
						passwordField.setText("");
						passwordField.setForeground(FirstPageGUI.darkGrey);
						passwordField.setEchoChar('*');
					}
				}
			});
			
			passwordField.addKeyListener(new KeyListener()
			{
				@Override
				public void keyTyped(KeyEvent e){}
				@Override
				public void keyReleased(KeyEvent e){}
				@Override
				public void keyPressed(KeyEvent e)
				{
					if(e.getKeyChar() == KeyEvent.VK_ENTER) newUserAction();
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
						EmailField.setForeground(FirstPageGUI.lightGrey);
					}
				}
				@Override
				public void focusGained(FocusEvent e)
				{
					if(EmailField.getText().equals("Email"))
					{
						EmailField.setText("");
						EmailField.setForeground(FirstPageGUI.darkGrey);
					}
				}
			});
			
			EmailField.addKeyListener(new KeyListener()
			{
				@Override
				public void keyTyped(KeyEvent e){}
				@Override
				public void keyReleased(KeyEvent e){}
				@Override
				public void keyPressed(KeyEvent e)
				{
					if(e.getKeyChar() == KeyEvent.VK_ENTER) newUserAction();
				}
			});
			
			UserNameField.addFocusListener(new FocusListener()
			{
				@Override
				public void focusLost(FocusEvent e)
				{
					if(UserNameField.getText().equals(""))
					{
						UserNameField.setText("Username");
						UserNameField.setForeground(FirstPageGUI.lightGrey);
					}
				}
				@Override
				public void focusGained(FocusEvent e)
				{
					if(UserNameField.getText().equals("Username"))
					{
						UserNameField.setText("");
						UserNameField.setForeground(FirstPageGUI.darkGrey);
					}
				}
			});
			
			UserNameField.addKeyListener(new KeyListener()
			{
				@Override
				public void keyTyped(KeyEvent e){}
				@Override
				public void keyReleased(KeyEvent e){}
				@Override
				public void keyPressed(KeyEvent e)
				{
					if(e.getKeyChar() == KeyEvent.VK_ENTER)newUserAction();
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
						LastNameField.setForeground(FirstPageGUI.lightGrey);
					}
				}
				@Override
				public void focusGained(FocusEvent e)
				{
					if(LastNameField.getText().equals("Last Name"))
					{
						LastNameField.setText("");
						LastNameField.setForeground(FirstPageGUI.darkGrey);
					}
				}
			});
			
			LastNameField.addKeyListener(new KeyListener()
			{
				@Override
				public void keyTyped(KeyEvent e){}
				@Override
				public void keyReleased(KeyEvent e){}
				@Override
				public void keyPressed(KeyEvent e)
				{
					if(e.getKeyChar() == KeyEvent.VK_ENTER) newUserAction();
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
						FirstNameField.setForeground(FirstPageGUI.lightGrey);
					}
				}
				@Override
				public void focusGained(FocusEvent e)
				{
					if(FirstNameField.getText().equals("First Name"))
					{
						FirstNameField.setText("");
						FirstNameField.setForeground(FirstPageGUI.darkGrey);
					}
				}
			});
			
			FirstNameField.addKeyListener(new KeyListener()
			{
				@Override
				public void keyTyped(KeyEvent e){}
				@Override
				public void keyReleased(KeyEvent e){}
				@Override
				public void keyPressed(KeyEvent e)
				{
					if(e.getKeyChar() == KeyEvent.VK_ENTER)newUserAction();
				}
			});
			
			
			cancel.addKeyListener(new KeyListener()
			{
				@Override
				public void keyTyped(KeyEvent e){}
				@Override
				public void keyReleased(KeyEvent e){}
				@Override
				public void keyPressed(KeyEvent e)
				{
					if(e.getKeyChar() == KeyEvent.VK_ENTER) newUserAction();	
				}
			});
		}
	
	public void newUserAction() {
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
		if(passwordField.getPassword().length>15 || passwordField2.getPassword().length<5) Password=false;
		if(passwordField2.getPassword().length>15 || passwordField2.getPassword().length<5) Password2=false;
	
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
					incorrectInput.setText("username already exists");
				}
				else
				{
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
						 
						 stmt.close();
						 
						Statement stat = (Statement) conn.createStatement();
						String sql = "Select * from user_table Where username='" + UserNameField.getText() +"'";
						System.out.println(UserNameField.getText());
						ResultSet rs1 = stat.executeQuery(sql);
						if (rs1.next())
			            {
							System.out.println("the id is "+rs1.getInt("iduser_table"));
							new LoggedInDriverGUI(rs1.getInt("iduser_table"));
							stat.close();
							conn.close();
							System.out.println("new user added!");
							dispose();
			            }						 
					}
					else
					{ 
						System.out.println("passwords to not match");
						incorrectInput.setText("passwords do not match");
					}
				}
				
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		
		else
		{
			System.out.println("incorrect characters");
			incorrectInput.setText("incorrect characters");
		}			
	}
	
	
	
}



