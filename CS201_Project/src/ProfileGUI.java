import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class ProfileGUI extends JPanel{
	private Dimension dim;
	private Image profilePic;

	private JLabel name;
	private JLabel email;
	private JTextArea bio;
	private Set<JButton> followersButtons;
	private Set<JButton> followingButtons;
	private Set<JButton> favoritesButtons;
	private JScrollPane jspFollowers;
	private JScrollPane jspFollowing;
	private JScrollPane jspFavorites;
	private JPanel jpFollowers;
	private JPanel jpFollowing;
	private JPanel jpFavorites;
	
	private JButton edit;
	private JButton unFollow;
	private JButton follow;
	
	private String key;
	public ProfileGUI(Dimension d, String key)
	{
		dim = d;
		this.key = key;
		this.setPreferredSize(dim);
		initializeComponents();
		setVisible(true);
		
	}
	
	private void initializeComponents()
	{
		name = new JLabel("Name");
		email = new JLabel("Email");
		bio = new JTextArea("My Bio");
		
		//buttons depending on user
		edit = new JButton("Edit Profile");
		unFollow = new JButton("UnFollow");
		follow = new JButton("Follow");
		followersButtons = new HashSet<JButton>();
		followingButtons = new HashSet<JButton>();
		favoritesButtons = new HashSet<JButton>();
		jpFollowers = new JPanel();
		jpFollowing = new JPanel();
		jpFavorites = new JPanel();
		jspFollowing = new JScrollPane(jpFollowing);
		jspFollowers = new JScrollPane(jpFollowers);
		jspFavorites = new JScrollPane(jpFavorites);
		jpFollowers.setLayout(new BoxLayout(jpFollowers, BoxLayout.Y_AXIS));
		jpFollowing.setLayout(new BoxLayout(jpFollowing, BoxLayout.Y_AXIS));
		jpFavorites.setLayout(new BoxLayout(jpFavorites, BoxLayout.Y_AXIS));
		populate();
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 3));
		bottomPanel.setPreferredSize(new Dimension(dim.width, dim.height/2));
		bottomPanel.add(jspFollowers);
		bottomPanel.add(jspFollowing);
		bottomPanel.add(jspFavorites);
		JPanel topPanel = new JPanel();
		JPanel middlePanel = new JPanel();
		middlePanel.add(bio);
		bio.setPreferredSize(new Dimension(dim.width, dim.height/4));
		bio.setEditable(false);
		topPanel.setPreferredSize(new Dimension(dim.width, dim.height/4));
		topPanel.add(name);
		topPanel.add(email);
		if (key.equals("not friends"))
		{
			topPanel.add(follow);
		}
		else if (key.equals("friends"))
		{
			topPanel.add(unFollow);
		}
		else if (key.equals("current user"))
		{
			topPanel.add(edit);
		}
		add(topPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);//, BorderLayout.SOUTH);
		//add(new JLabel("OHAI!!!!!"));
		repaint();
		setVisible(true);
		
	}
	
	private void populate()
	{
		for (int i = 0; i<100; i++)
		{
			JButton temp = new JButton("User");
			followersButtons.add(temp);
			JButton temp3 = new JButton("User");
			followingButtons.add(temp3);
			JButton temp2 = new JButton("Song");
			favoritesButtons.add(temp2);
		}
		
		Iterator<JButton> it = followersButtons.iterator();
		Iterator<JButton> it2 = followingButtons.iterator();
		Iterator<JButton> it3 = favoritesButtons.iterator();
		while (it.hasNext())
		{
			JButton temp = it.next();
			jpFollowers.add(temp);
		}
		while (it2.hasNext())
		{
			JButton temp = it2.next();
			jpFollowing.add(temp);
		}
		while (it3.hasNext())
		{
			JButton temp = it3.next();
			jpFavorites.add(temp);
		}
		
	}
}