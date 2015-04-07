import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


class WelcomeWindow extends JFrame
{
	private JLabel lblWelcome = new JLabel("Welcome!");
	private JButton SignInButton= new JButton("Sign In");
	private JButton SignUpButton = new JButton("Sign Up");
	private JButton GuestUserButton = new JButton("New User");
	
	public WelcomeWindow()
	{
		initialize();
		addActionListener();
		setVisible(true);
	}
	
	
	private void initialize()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(450,300);
		setLocationRelativeTo(null);
		setTitle("HerJam");
		
		lblWelcome.setForeground(new Color(220, 20, 60));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Segoe Marker", Font.PLAIN, 32));
		add(lblWelcome, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(40, 40));
		add(panel, BorderLayout.CENTER);
		
		SignInButton.setBounds(66, 99, 100, 40);
		SignInButton.setPreferredSize(new Dimension(100, 40));
		
		SignUpButton.setBounds(176, 99, 100, 40);
		SignUpButton.setPreferredSize(new Dimension(100, 40));
		
		GuestUserButton.setBounds(286, 99, 100, 40);
		GuestUserButton.setPreferredSize(new Dimension(100, 40));
		
		panel.setLayout(null);
		panel.add(SignInButton);
		panel.add(SignUpButton);
		panel.add(GuestUserButton);
	}

	
	private void addActionListener()
	{
		SignInButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("SignIn");
			}
		});
		
		SignUpButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("SignUp");
				
			}
		});
		
		GuestUserButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Guest User");
			}
		});
	}
	
	public static void main(String[] args)
	{
		new WelcomeWindow();
	}

}
