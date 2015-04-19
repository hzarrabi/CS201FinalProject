import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;


public class ProfileGUI extends JPanel{
	private Dimension dim;
	private File pictureFile;
	private ImageIcon profilePic;
	private JButton saveButton;
	private JButton cancelButton;
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
	private JPanel buttonP;
	private JButton edit;
	private JButton unFollow;
	private JButton follow;
	private JPanel picture;
	private JPanel namePanel;
	private JPanel emailPanel;
	private JTextField editFirstName;
	private JTextField editLastName;
	private JTextField editEmail;
	private JFileChooser jfl;
	private JLabel picturePic;
	private JButton pictureButton;
	
	private String key;
	public ProfileGUI(Dimension d, String key)
	{
		dim = d;
		this.key = key;
		//profilePic = new ImageIcon("data/MomAndMoose.jpg");
		ImageIcon newIcon2 = new ImageIcon("data/MomAndMoose.jpg");
		Image img2 = newIcon2.getImage().getScaledInstance(dim.width/2, dim.height/4, Image.SCALE_SMOOTH);
		profilePic = new ImageIcon(img2);
		this.setPreferredSize(dim);
		initializeComponents();
		setEventHandlers();
		setVisible(true);
		
	}
	
	private void initializeComponents()
	{
		name = new JLabel("Name");
		email = new JLabel("Email");
		bio = new JTextArea("My Bio");
		picturePic = new JLabel("");

		//picturePic.setBorder(new RoundedBorder());
		picturePic.setPreferredSize(new Dimension(dim.width/2, dim.height/4));
		picturePic.setBorder(new RoundedBorder());
		picturePic.setIcon(profilePic);
		//buttons depending on user
		edit = new JButton("Edit Profile");
		unFollow = new JButton("UnFollow");
		follow = new JButton("Follow");
		saveButton = new JButton("Save");
		cancelButton = new JButton("Cancel");
		pictureButton = new JButton("");
		pictureButton.setPreferredSize(new Dimension(dim.width/2, dim.height/4));
		pictureButton.setBorder(new RoundedBorder());
		pictureButton.setIcon(profilePic);
		editFirstName = new JTextField();
		editLastName = new JTextField();
		editEmail = new JTextField();
		jfl = new JFileChooser();
		//jfl = new JFileChooser();
	    jfl.setCurrentDirectory(new File("."));
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	            "jpg", "jpeg", "png");
	     	jfl.setFileFilter(filter);
	    
	    editEmail.setPreferredSize(new Dimension(dim.width/3, dim.height/16));
	    editLastName.setPreferredSize(new Dimension(dim.width/5, dim.height/16));
	    editFirstName.setPreferredSize(new Dimension(dim.width/5, dim.height/16));
	    cancelButton.setPreferredSize(new Dimension(dim.width/5, dim.height/18));
	   // editFirstName.setPreferredSize(new Dimension(dim.width/3, dim.height/16));
	   // edit.setPreferredSize(new Dimension(dim.width/3, dim.height/16));
	    email.setPreferredSize(new Dimension(dim.width/3, dim.height/16));
	    name.setPreferredSize(new Dimension(dim.width/3, dim.height/16));
	    follow.setPreferredSize(new Dimension(dim.width/3, dim.height/16));
	    edit.setPreferredSize(new Dimension(dim.width/3, dim.height/16));
	    saveButton.setPreferredSize(new Dimension(dim.width/5, dim.height/18));
	    unFollow.setPreferredSize(new Dimension(dim.width/3, dim.height/16));
	    email.setFont(FirstPageGUI.smallFont);
	    name.setFont(FirstPageGUI.font);
	    edit.setFont(FirstPageGUI.smallFont);
	    email.setForeground(FirstPageGUI.darkGrey);
	    name.setForeground(FirstPageGUI.darkGrey);
	    name.setHorizontalAlignment(SwingConstants.CENTER);
		email.setHorizontalAlignment(SwingConstants.CENTER);
		followersButtons = new HashSet<JButton>();
		followingButtons = new HashSet<JButton>();
		favoritesButtons = new HashSet<JButton>();
		jpFollowers = new JPanel();
		jpFollowing = new JPanel();
		jpFavorites = new JPanel();
		jpFollowing.setPreferredSize(new Dimension(dim.width/3, dim.height/2));
		jpFollowers.setPreferredSize(new Dimension(dim.width/3, dim.height/2));
		jpFavorites.setPreferredSize(new Dimension(dim.width/3, dim.height/2));
		jpFollowing.setBackground(FirstPageGUI.white);
		jpFollowers.setBackground(FirstPageGUI.white);
		jpFavorites.setBackground(FirstPageGUI.white);
		jpFollowers.setBackground(FirstPageGUI.white);
		jpFollowing.setBackground(FirstPageGUI.white);
		jpFavorites.setBackground(FirstPageGUI.white);
		jspFollowing = new JScrollPane(jpFollowing);
		jspFollowers = new JScrollPane(jpFollowers);
		jspFavorites = new JScrollPane(jpFavorites);
		//jpFollowers.setLayout()
		//jpFollowing.setLayout(new BoxLayout(jpFollowing, BoxLayout.Y_AXIS));
		//jpFavorites.setLayout(new BoxLayout(jpFavorites, BoxLayout.Y_AXIS));
		populate();
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(FirstPageGUI.color);
		bottomPanel.setLayout(new GridLayout(1, 3));
		JPanel buttonLabelsPanel = new JPanel();
		buttonLabelsPanel.setBackground(FirstPageGUI.white);
		buttonLabelsPanel.setPreferredSize(new Dimension(dim.width, dim.height/14));
		buttonLabelsPanel.setLayout(new GridLayout(1, 3));
		JLabel followers = new JLabel("Followers");
		JLabel following = new JLabel("Following");
		JLabel favorites = new JLabel("Favorites");
		followers.setFont(FirstPageGUI.fontTitle);
		following.setFont(FirstPageGUI.fontTitle);
		favorites.setFont(FirstPageGUI.fontTitle);
		followers.setForeground(FirstPageGUI.darkGrey);
		following.setForeground(FirstPageGUI.darkGrey);
		favorites.setForeground(FirstPageGUI.darkGrey);
		followers.setHorizontalAlignment(SwingConstants.CENTER);
		following.setHorizontalAlignment(SwingConstants.CENTER);
		favorites.setHorizontalAlignment(SwingConstants.CENTER);
		buttonLabelsPanel.add(followers);
		buttonLabelsPanel.add(following);
		buttonLabelsPanel.add(favorites);
		buttonLabelsPanel.setBackground(FirstPageGUI.white);
		bottomPanel.setPreferredSize(new Dimension(dim.width, 7*dim.height/16));
		bottomPanel.add(jspFollowers);
		bottomPanel.add(jspFollowing);
		bottomPanel.add(jspFavorites);
		JPanel topPanel = new JPanel();
		JPanel middlePanel = new JPanel();
		middlePanel.add(bio);
		bio.setPreferredSize(new Dimension(dim.width, dim.height/5));
		bio.setEditable(false);
		bio.setBackground(FirstPageGUI.color);
		bio.setForeground(FirstPageGUI.white);
		edit.setBorder(new RoundedBorder());
		edit.setBackground(FirstPageGUI.green);
		follow.setBorder(new RoundedBorder());
		unFollow.setBorder(new RoundedBorder());
		follow.setForeground(FirstPageGUI.white);
		unFollow.setForeground(FirstPageGUI.white);
		follow.setBackground(FirstPageGUI.green);
		unFollow.setBackground(FirstPageGUI.green);
		edit.setForeground(FirstPageGUI.white);
		saveButton.setBorder(new RoundedBorder());
		cancelButton.setBorder(new RoundedBorder());
		saveButton.setBackground(FirstPageGUI.green);
		cancelButton.setBackground(FirstPageGUI.color);
		saveButton.setForeground(FirstPageGUI.white);
		cancelButton.setForeground(FirstPageGUI.white);
		editEmail.setBorder(new RoundedBorder());
		editFirstName.setBorder(new RoundedBorder());
		editLastName.setBorder(new RoundedBorder());
		editEmail.setBackground(FirstPageGUI.grey);
		editEmail.setOpaque(true);
		editFirstName.setBackground(FirstPageGUI.grey);
		editFirstName.setOpaque(true);
		editLastName.setBackground(FirstPageGUI.grey);
		editLastName.setOpaque(true);
		cancelButton.setOpaque(true);
		saveButton.setOpaque(true);
		edit.setOpaque(true);
		unFollow.setOpaque(true);
		follow.setOpaque(true);
		editEmail.setText("email");
		editEmail.setFont(FirstPageGUI.smallFont);
		editFirstName.setText("first name");
		editFirstName.setFont(FirstPageGUI.smallFont);
		editLastName.setText("last name");
		editLastName.setFont(FirstPageGUI.smallFont);
		editEmail.setForeground(FirstPageGUI.lightGrey);
		editLastName.setForeground(FirstPageGUI.lightGrey);
		editFirstName.setForeground(FirstPageGUI.lightGrey);
		topPanel.setPreferredSize(new Dimension(dim.width, 3*dim.height/10));
		JPanel info = new JPanel();
		info.setBackground(FirstPageGUI.white);
		info.setPreferredSize(new Dimension(dim.width/2, dim.height/4));
		buttonP = new JPanel();
		buttonP.setBackground(FirstPageGUI.white);
		buttonP.setPreferredSize(new Dimension(dim.width, dim.height/14));
		emailPanel = new JPanel();
		emailPanel.setBackground(FirstPageGUI.white);
		emailPanel.setPreferredSize(new Dimension(dim.width, dim.height/14));
		namePanel = new JPanel();
		namePanel.setBackground(FirstPageGUI.white);
		namePanel.setPreferredSize(new Dimension(dim.width, dim.height/14));
		namePanel.add(name);
		setBackground(FirstPageGUI.white);
		emailPanel.add(email);
		//JPanel allInfoP = new JPanel();
		info.setPreferredSize(new Dimension(new Dimension(dim.width/2, dim.height/4)));
		//picture.add()
		if (key.equals("not friends"))
		{
			buttonP.add(follow);
		}
		else if (key.equals("friends"))
		{
			buttonP.add(unFollow);
		}
		else if (key.equals("current user"))
		{
			buttonP.add(edit);
			
		}
		info.add(namePanel);
		info.add(emailPanel);
		info.add(buttonP);
		picture = new JPanel();
		bio.setFont(FirstPageGUI.font);
		bio.setWrapStyleWord(true);
		bio.setLineWrap(true);
		picture.setBackground(FirstPageGUI.white);
		picture.setPreferredSize(new Dimension(dim.width/2, dim.height/4));
		picture.add(picturePic);
		topPanel.setLayout(new GridLayout(1, 2));
		topPanel.add(picture);
		//picture.setBorder(border);
		topPanel.add(info);
		topPanel.setBackground(FirstPageGUI.white);
		add(topPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		middlePanel.setBackground(FirstPageGUI.white);
		setBackground(FirstPageGUI.white);
		JPanel mid = new JPanel();
		mid.setBackground(FirstPageGUI.white);
		mid.setPreferredSize(new Dimension(dim.width, dim.height/2));
		mid.add(buttonLabelsPanel, BorderLayout.NORTH);
		mid.add(bottomPanel, BorderLayout.CENTER);
		add(mid, BorderLayout.SOUTH);//, BorderLayout.SOUTH);
		//add(new JLabel("OHAI!!!!!"));
		//setBackground(FirstPageGUI.color);
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
			temp.setBackground(FirstPageGUI.green);
			temp.setForeground(FirstPageGUI.white);
			temp.setFont(FirstPageGUI.smallFont);
			temp.setBorder(new RoundedBorder());
			temp.setPreferredSize(new Dimension(dim.width/4, dim.height/20));
			temp.setOpaque(true);
			jpFollowers.add(temp);
		}
		while (it2.hasNext())
		{
			JButton temp = it2.next();
			temp.setBackground(FirstPageGUI.green);
			temp.setForeground(FirstPageGUI.white);
			temp.setFont(FirstPageGUI.smallFont);
			temp.setBorder(new RoundedBorder());
			temp.setPreferredSize(new Dimension(dim.width/4, dim.height/20));
			temp.setOpaque(true);
			jpFollowing.add(temp);
		}
		while (it3.hasNext())
		{
			JButton temp = it3.next();
			temp.setBackground(FirstPageGUI.green);
			temp.setForeground(FirstPageGUI.white);
			temp.setFont(FirstPageGUI.smallFont);
			temp.setBorder(new RoundedBorder());
			temp.setPreferredSize(new Dimension(dim.width/4, dim.height/20));
			temp.setOpaque(true);
			jpFavorites.add(temp);
		}
		
	}
	
	private void setEventHandlers()
	{
		edit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				bio.setEditable(true);
				namePanel.remove(name);
				name.setVisible(false);
				emailPanel.remove(email);
				email.setVisible(false);
				buttonP.remove(edit);
				edit.setVisible(false);
				buttonP.add(saveButton);
				saveButton.setVisible(true);
				buttonP.add(cancelButton);
				cancelButton.setVisible(true);
				emailPanel.add(editEmail);
				editEmail.setVisible(true);
				namePanel.add(editFirstName);
				editFirstName.setVisible(true);
				namePanel.add(editLastName);
				editLastName.setVisible(true);
				picture.remove(picturePic);
				picture.add(pictureButton);
				pictureButton.setVisible(true);
				repaint();
			}
			
		});
		pictureButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int temp1 = jfl.showOpenDialog(pictureButton);
				if (temp1 == JFileChooser.APPROVE_OPTION)
				{
					//get the file
					pictureFile = jfl.getSelectedFile();
					ImageIcon newIcon2 = new ImageIcon(pictureFile.getPath());
					Image img2 = newIcon2.getImage().getScaledInstance(dim.width/2, dim.height/4, Image.SCALE_SMOOTH);
					pictureButton.setIcon(new ImageIcon(img2));
					//profilePic = new ImageIcon(img2);
				}
			}
			
		});
		
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				bio.setEditable(false);
				namePanel.remove(editFirstName);
				editFirstName.setVisible(false);
				namePanel.remove(editLastName);
				editLastName.setVisible(false);
				emailPanel.remove(editEmail);
				editEmail.setVisible(false);
				buttonP.remove(saveButton);
				saveButton.setVisible(false);
				buttonP.remove(cancelButton);
				cancelButton.setVisible(false);
				buttonP.add(edit);
				edit.setVisible(true);
				emailPanel.add(email);
				email.setVisible(true);
				namePanel.add(name);
				name.setVisible(true);
				pictureFile = null;
				picture.remove(pictureButton);
				picture.add(picturePic);
				pictureButton.setVisible(false);
				repaint();
			}

		});
		
		saveButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String newFirstName = editFirstName.getText();
				String newLastName = editLastName.getText();
				String newEmail = editEmail.getText();
				editFirstName.setText("first name");
				editLastName.setText("last name");
				editEmail.setText("email");
				editFirstName.setForeground(FirstPageGUI.lightGrey);
				editLastName.setForeground(FirstPageGUI.lightGrey);
				editEmail.setForeground(FirstPageGUI.lightGrey);
				if (pictureFile != null)
				{
					ImageIcon newIcon2 = new ImageIcon(pictureFile.getPath());
					Image img2 = newIcon2.getImage().getScaledInstance(dim.width/2, dim.height/4, Image.SCALE_SMOOTH);
					picturePic.setIcon(new ImageIcon(img2));
					profilePic = new ImageIcon(img2);
				}
				name.setText(newFirstName + " "+newLastName);
				email.setText(newEmail);
				bio.setEditable(false);
				namePanel.remove(editFirstName);
				editFirstName.setVisible(false);
				namePanel.remove(editLastName);
				editLastName.setVisible(false);
				emailPanel.remove(editEmail);
				editEmail.setVisible(false);
				buttonP.remove(saveButton);
				saveButton.setVisible(false);
				buttonP.remove(cancelButton);
				cancelButton.setVisible(false);
				buttonP.add(edit);
				edit.setVisible(true);
				emailPanel.add(email);
				email.setVisible(true);
				namePanel.add(name);
				name.setVisible(true);
				picture.remove(pictureButton);
				picture.add(picturePic);
				pictureButton.setVisible(false);
				repaint();
				
			}
			
		});
		
		editFirstName.addFocusListener(new FocusListener()
		{

			@Override
			public void focusGained(FocusEvent e)
			{
				if(editFirstName.getText().equals("first name"))
				{
					//editFirstName.setEchoChar(('*'));
					editFirstName.setText("");
				}
				editFirstName.setForeground(FirstPageGUI.darkGrey);
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if(editFirstName.getText().equals(""))
				{
					editFirstName.setText("first name");
					//editFirstName.setEchoChar((char)0);
					editFirstName.setForeground(FirstPageGUI.lightGrey);
				}
				
			}
		});
		editLastName.addFocusListener(new FocusListener()
		{

			@Override
			public void focusGained(FocusEvent e)
			{
				if(editLastName.getText().equals("last name"))
				{
					//editFirstName.setEchoChar(('*'));
					editLastName.setText("");
				}
				editLastName.setForeground(FirstPageGUI.darkGrey);
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if(editLastName.getText().equals(""))
				{
					editLastName.setText("last name");
					//editFirstName.setEchoChar((char)0);
					editLastName.setForeground(FirstPageGUI.lightGrey);
				}
				
			}
		});
		editEmail.addFocusListener(new FocusListener()
		{

			@Override
			public void focusGained(FocusEvent e)
			{
				if(editEmail.getText().equals("email"))
				{
					//editFirstName.setEchoChar(('*'));
					editEmail.setText("");
				}
				editEmail.setForeground(FirstPageGUI.darkGrey);
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if(editEmail.getText().equals(""))
				{
					editEmail.setText("email");
					//editFirstName.setEchoChar((char)0);
					editEmail.setForeground(FirstPageGUI.lightGrey);
				}
				
			}
		});
	}
}