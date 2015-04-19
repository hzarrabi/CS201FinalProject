import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;

import com.mysql.jdbc.Statement;

import sun.security.util.Password;





public class FirstPageGUI extends JFrame{
	private Dimension dim;
	private JTextField userName;
	private JPasswordField password;
	private JLabel logo;
	private JLabel newUser;
	private JButton createNewUser;
	private JButton guest;
	private JButton login;
	private JPanel bottomColor;
	private JPanel topColor;
	private JLabel incorrectInput;
	private JPanel main = new JPanel();
	final static Color color = new Color(0x0AB2D8);
	final static Color white = new Color(0xf7f7f7);
	final static Color green = new Color(0xAD89B);
	final static Color lightGrey = new Color(0xB2B0B0);
	final static Color grey = new Color(0xE4E4E4);
	final static Color darkGrey = new Color(0x696969);
	final static Font font = new Font("Helvetica Neue", Font.PLAIN, 18);
	final static Font fontTitle = new Font("Helvetica Neue", Font.PLAIN, 24);
	
	//for connecting database
	Connection connection;
	String dburl;
	String UserName;
	String passWord;
	Connection conn;
	
	public FirstPageGUI()
	{
		super("Login Screen");
		initializeComponents();
		createGUI();
		makePretty();
		//System.out.println("YOOOOOOO");
		setEventHandlers();
		connect();
	}
	
	private void connect(){
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
		userName = new JTextField("UserName");
		password = new JPasswordField("Password");
		logo = new JLabel("201-Tunes");
		newUser = new JLabel("Not Signed Up?");
		createNewUser = new JButton("Create Account");
		guest = new JButton("Login as Guest");
		login = new JButton("Login");
		bottomColor = new JPanel();
		bottomColor.setPreferredSize(new Dimension(dim.width/3, dim.height/20));
		bottomColor.setBackground(color);
		topColor = new JPanel();
		topColor.setPreferredSize(new Dimension(dim.width/3, dim.height/15));
		topColor.setBackground(color);
	}
	
	private void createGUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,dim.width/3, dim.height-80);
		setResizable(false);
		userName.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		password.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		logo.setForeground(white);
		topColor.add(logo);
		login.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		createNewUser.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		guest.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		newUser.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		//newUser.setBackground(Color.cyan);
		newUser.setHorizontalAlignment(SwingConstants.CENTER);
		//Image myImage = image.getScaledInstance(dim.width-1, dim.height-1, Image.SCALE_SMOOTH);
		
		main.setLayout(new FlowLayout(FlowLayout.CENTER, dim.width, dim.height/40));
		main.setPreferredSize(new Dimension(dim.width/3, 13*dim.height/20));
		//main.setBackground(Color.WHITE);
		//Box.createGlue();
		add(new JTextField());//we do this because we don't want focus on first jtexfield initially
		main.add(userName);
		//Box.createGlue();
		main.add(password);
		password.setEchoChar((char)0);
		//Box.createGlue();
		main.add(login);
		//Box.createGlue();
		main.add(newUser);
		//Box.createGlue();
		main.add(createNewUser);
		//Box.createGlue();
		main.add(guest);
		//Box.createGlue();
		JPanel mainPanel = new JPanel();
		mainPanel.add(main, BorderLayout.CENTER);
		incorrectInput = new JLabel("");
		incorrectInput.setHorizontalAlignment(SwingConstants.CENTER);
		incorrectInput.setForeground(Color.RED);
		mainPanel.setBackground(FirstPageGUI.white);
		mainPanel.add(main);
		mainPanel.add(incorrectInput);
		incorrectInput.setPreferredSize(new Dimension(dim.width, dim.height/20));
		mainPanel.setPreferredSize(new Dimension(dim.width/3, 14*dim.height/20));
		add(topColor, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
		add(bottomColor, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	private void makePretty(){
		logo.setFont(fontTitle);
		
		main.setBackground(white);
		password.setBorder(new RoundedBorder());
		password.setFont(font);
		userName.setBorder(new RoundedBorder());
		userName.setBackground(grey);
		userName.setFont(font);
		password.setBackground(grey);
		login.setBorder(new RoundedBorder());
		login.setBackground(green);
		login.setFont(font);
		createNewUser.setBorder(new RoundedBorder());
		createNewUser.setBackground(color);
		createNewUser.setFont(font);
		guest.setBorder(new RoundedBorder());
		guest.setOpaque(true);
		guest.setBackground(darkGrey);
		guest.setForeground(white);
		guest.setFont(font);
		newUser.setFont(font);
		createNewUser.setForeground(white);
		login.setOpaque(true);
		createNewUser.setOpaque(true);
		login.setForeground(white);
		password.setForeground(lightGrey);
		userName.setForeground(lightGrey);
		
	}
	
	private void setEventHandlers(){
		createNewUser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new CreateUserGUI();
				dispose();
			}
		});
		guest.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new GuestGUI();
			}
		});
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String theUserName=userName.getText();
				String thePassword=PasswordHash.hash(password.getText());//returning the password hashed
				try
				{
					Statement stat = (Statement) conn.createStatement();
					String sql = "Select * from user_table Where username='" + theUserName + "' and password='"+thePassword+"'";
					ResultSet rs = stat.executeQuery(sql);
					if (rs.next() && theUserName.equals(rs.getString("username")) && thePassword.equals(rs.getString("password")))
		            {
						new LoggedInDriverGUI(rs.getInt("iduser_table"));
						stat.close();
						conn.close();
						dispose();
		            }
		            else
		            {
		            	System.out.println("incorrect username password combo");
		            	incorrectInput.setText("incorrect username or password");
		            }
				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}				
			}
		});
		
		userName.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if(userName.getText().equals("UserName"))
				{
					userName.setText("");
					userName.setForeground(FirstPageGUI.darkGrey);
				}
			}
			@Override
			public void focusLost(FocusEvent e)
			{
				if(userName.getText().equals(""))
				{
					userName.setText("UserName");
					userName.setForeground(FirstPageGUI.lightGrey);
				}
			}
		});
		
		userName.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
			
			@Override
			public void keyTyped(KeyEvent e)
			{
				if(e.getKeyChar() == KeyEvent.VK_ENTER)
				{
					String theUserName=userName.getText();
					String thePassword=PasswordHash.hash(password.getText());//returning the password hashed
					try
					{
						Statement stat = (Statement) conn.createStatement();
						String sql = "Select * from user_table Where username='" + theUserName + "' and password='"+thePassword+"'";
						ResultSet rs = stat.executeQuery(sql);
						if (rs.next() && theUserName.equals(rs.getString("username")) && thePassword.equals(rs.getString("password")))
			            {
							new LoggedInDriverGUI(rs.getInt("iduser_table"));
							stat.close();
							conn.close();
							dispose();
			            }
			            else
			            {
			            	System.out.println("incorrect username password combo");
			            	incorrectInput.setText("incorrect username or password");
			            }
					} catch (SQLException e1)
					{
						e1.printStackTrace();
					}				
                }       
			}
		});
		
		password.addFocusListener(new FocusListener()
		{

			@Override
			public void focusGained(FocusEvent e)
			{
				if(password.getText().equals("Password"))
				{
					password.setEchoChar(('*'));
					password.setText("");
				}
				password.setForeground(FirstPageGUI.darkGrey);
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if(password.getText().equals(""))
				{
					password.setText("Password");
					password.setEchoChar((char)0);
				}
				password.setForeground(FirstPageGUI.lightGrey);
			}
		});
		
		password.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
			
			@Override
			public void keyTyped(KeyEvent e)
			{
				if(e.getKeyChar() == KeyEvent.VK_ENTER)
				{
					String theUserName=userName.getText();
					String thePassword=PasswordHash.hash(password.getText());//returning the password hashed
					try
					{
						Statement stat = (Statement) conn.createStatement();
						String sql = "Select * from user_table Where username='" + theUserName + "' and password='"+thePassword+"'";
						ResultSet rs = stat.executeQuery(sql);
						if (rs.next() && theUserName.equals(rs.getString("username")) && thePassword.equals(rs.getString("password")))
			            {
							new LoggedInDriverGUI(rs.getInt("iduser_table"));
							stat.close();
							conn.close();
							dispose();
			            }
			            else
			            {
			            	System.out.println("incorrect username password combo");
			            	incorrectInput.setText("incorrect username or password");
			            }
					} catch (SQLException e1)
					{
						e1.printStackTrace();
					}				
                }       
			}
		});
	}
	
	public static void main(String [] args)
	{
		new FirstPageGUI();
//		try {
//			new MusicLibrary();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}