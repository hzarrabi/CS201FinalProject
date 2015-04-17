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
	public ProfileGUI(Dimension d)
	{
		dim = d;
		this.setPreferredSize(dim);
		setVisible(true);
	}
	
	private void initializeComponents()
	{
		name = new JLabel("Name");
		email = new JLabel("Email");
		bio = new JTextArea("My Bio");
		followersButtons = new HashSet<JButton>();
		followingButtons = new HashSet<JButton>();
		favoritesButtons = new HashSet<JButton>();
		jpFollowers = new JPanel();
		jpFollowing = new JPanel();
		jpFavorites = new JPanel();
		jspFollowing = new JScrollPane(jpFollowing);
		jspFollowers = new JScrollPane(jpFollowers);
		jspFavorites = new JScrollPane(jpFavorites);
		jpFollowers.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		jpFollowing.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		jpFavorites.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		populate();
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 3));
		bottomPanel.setPreferredSize(new Dimension(dim.width/4, dim.height/4));
		bottomPanel.add(jpFollowers);
		bottomPanel.add(jpFollowing);
		bottomPanel.add(jpFavorites);
		JPanel topPanel = new JPanel();
		JPanel middlePanel = new JPanel();
		middlePanel.add(bio);
		bio.setPreferredSize(new Dimension(dim.width/4, dim.height/4));
		bio.setEditable(false);
		topPanel.setPreferredSize(new Dimension(dim.width/4, dim.height/4));
		topPanel.add(name);
		topPanel.add(email);
		add(topPanel);
		add(middlePanel);
		add(bottomPanel);
		setVisible(true);
		
	}
	
	private void populate()
	{
		for (int i = 0; i<100; i++)
		{
			JButton temp = new JButton("User");
			followersButtons.add(temp);
			followingButtons.add(temp);
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
			jpFollowers.add(temp);
		}
		while (it3.hasNext())
		{
			JButton temp = it3.next();
			jpFollowers.add(temp);
		}
		
	}
}