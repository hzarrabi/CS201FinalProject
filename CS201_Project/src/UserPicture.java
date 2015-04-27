import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class UserPicture implements Serializable
{
	transient BufferedImage profilePic;
	int userId;
	
	public UserPicture(BufferedImage profilePic, int userId)
	{
		this.profilePic=profilePic;
		this.userId=userId;
	}
	
	
	private void writeObject(ObjectOutputStream out)throws IOException{
        out.defaultWriteObject();
        //write buff with imageIO to out
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(profilePic, "jpg", out);
        //out.write();
    }
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();

        profilePic = ImageIO.read(in);
    }
	
	public BufferedImage getProfilePic()
	{
		return profilePic;
	}
	
	public int getUserID()
	{
		return userId;
	}
}
