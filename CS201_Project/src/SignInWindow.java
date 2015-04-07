import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;


public class SignInWindow extends JFrame
{

	private JTextField UserNameField= new JTextField();;
	private JTextField PasswordField= new JTextField();;
	private JLabel IncorrectLabel = new JLabel("Incorrect User Name or Password");
	
	public SignInWindow()
	{
		initialize();
	}
	
	private void initialize()
	{
		setBounds(100, 100, 450, 470);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		UserNameField.setColumns(10);
		
		JButton btnNewButton = new JButton("Log In");
		
		IncorrectLabel.setForeground(Color.RED);
		
		PasswordField.setColumns(10);
		
		
		
		JLabel lblReadyToS = new JLabel("Ready to start Jammin'?");
		
		JLabel lblLogIn = new JLabel("Log In!!");
		lblLogIn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 23));
		lblLogIn.setForeground(new Color(220, 20, 60));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(271, Short.MAX_VALUE)
					.addComponent(IncorrectLabel)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(160)
					.addComponent(lblReadyToS)
					.addContainerGap(167, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(182)
					.addComponent(lblLogIn)
					.addContainerGap(183, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(128)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(PasswordField, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
						.addComponent(UserNameField, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(132, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(165)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(170, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(65)
					.addComponent(lblReadyToS)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLogIn)
					.addGap(39)
					.addComponent(UserNameField, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(PasswordField, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(55)
					.addComponent(IncorrectLabel)
					.addContainerGap(114, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
		setVisible(true);
	}

	
	public static void main(String[] args)
	{
		new SignInWindow();

	}

}
