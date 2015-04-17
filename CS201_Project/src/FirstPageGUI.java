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
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;


public class FirstPageGUI extends JFrame{
	private Dimension dim;
	private JTextField userName;
	private JTextField password;
	private JLabel logo;
	private JLabel newUser;
	private JButton createNewUser;
	private JButton guest;
	private JButton login;
	private JPanel bottomColor;
	private JPanel topColor;
	JPanel main = new JPanel();
	final static Color color = new Color(0x0AB2D8);
	public FirstPageGUI()
	{
		super("Login Screen");
		initializeComponents();
		createGUI();
		makePretty();
		setEventHandlers();
	}
	
	private void initializeComponents(){
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		userName = new JTextField("username");
		password = new JTextField("password");
		logo = new JLabel("CsMusic");
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
		setBounds(0,0,dim.width/3, dim.height);
		setVisible(true);
		setResizable(false);
		userName.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		password.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		topColor.add(logo);
		login.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		createNewUser.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		guest.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		newUser.setPreferredSize(new Dimension(dim.width/4, dim.height/12));
		//newUser.setBackground(Color.cyan);
		newUser.setHorizontalAlignment(SwingConstants.CENTER);
		//Image myImage = image.getScaledInstance(dim.width-1, dim.height-1, Image.SCALE_SMOOTH);
		
		main.setLayout(new FlowLayout(FlowLayout.CENTER, dim.width, dim.height/30));
		main.setPreferredSize(new Dimension(dim.width/3, 15*dim.height/20));
		//main.setBackground(Color.WHITE);
		//Box.createGlue();
		main.add(userName);
		//Box.createGlue();
		main.add(password);
		//Box.createGlue();
		main.add(login);
		//Box.createGlue();
		main.add(newUser);
		//Box.createGlue();
		main.add(createNewUser);
		//Box.createGlue();
		main.add(guest);
		//Box.createGlue();
		add(topColor, BorderLayout.NORTH);
		add(main, BorderLayout.CENTER);
		add(bottomColor, BorderLayout.SOUTH);
	}
	
	private void makePretty(){
		Font font = new Font("Helvetica", Font.PLAIN, 18);
		logo.setFont(font);
		Color white = new Color(0xf7f7f7);
		Color green = new Color(0xAD89B);
		Color lightGrey = new Color(0xB2B0B0);
		Color grey = new Color(0xC1C3C4);
		Color darkGrey = new Color(0x696969);
		
		main.setBackground(white);
		password.setBorder(new RoundedCornerBorder());
		password.setFont(font);
		userName.setBorder(new RoundedCornerBorder());
		userName.setBackground(grey);
		userName.setFont(font);
		password.setBackground(grey);
		login.setBorder(new RoundedCornerBorder());
		login.setBackground(green);
		login.setFont(font);
		createNewUser.setBorder(new RoundedCornerBorder());
		createNewUser.setBackground(color);
		createNewUser.setFont(font);
		guest.setBorder(new RoundedCornerBorder());
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
			}
		});
		guest.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new GuestGUI();
			}
		});
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new LoggedInDriverGUI();
			}
		});
	}
	
	public static void main(String [] args)
	{
		new FirstPageGUI();
	}
}

class RoundedCornerBorder extends AbstractBorder {
	  @Override public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	    Graphics2D g2 = (Graphics2D)g.create();
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    int r = height-1;
	    RoundRectangle2D round = new RoundRectangle2D.Float(x, y, width-1, height-1, r, r);
	    Container parent = c.getParent();
	    if(parent!=null) {
	      g2.setColor(parent.getBackground());
	      Area corner = new Area(new Rectangle2D.Float(x, y, width, height));
	      corner.subtract(new Area(round));
	      g2.fill(corner);
	    }
	    g2.setColor(Color.GRAY);
	    g2.draw(round);
	    g2.dispose();
	  }
	  @Override public Insets getBorderInsets(Component c) {
	    return new Insets(4, 8, 4, 8);
	  }
	  @Override public Insets getBorderInsets(Component c, Insets insets) {
	    insets.left = insets.right = 8;
	    insets.top = insets.bottom = 4;
	    return insets;
	  }
	}