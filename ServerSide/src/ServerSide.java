//package pictureUploadTest;


import java.awt.image.BufferedImage;

import java.io.File;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.*;

import java.util.Scanner;

import javax.imageio.ImageIO;

public class ServerSide  
{

    private final int SERVER_PORT=5000;
    ServerSocket ss;
    Socket s;
    ObjectInputStream ois; 
    ObjectOutputStream oos;
    Scanner sc;
    
    static java.sql.Connection conn;

    
    public ServerSide()
    {
        try {
            initNet();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }
    
    public void initNet() throws Exception
    {

        try{
           
        ss=new ServerSocket(SERVER_PORT);
        //s=ss.accept();
        new MultiHandler();
        
        
        }catch(Exception e)
        {
            System.out.println("Error in initNEt"+e);
        }finally{

        }

        
    }
    class MultiHandler extends Thread
    {
        public MultiHandler() throws Exception
        {
            System.out.println("Stream attached");
            start();
        }

        @Override
        public void run()
        {

            try{
                while(true)
                {
                    
                    
                    System.out.println("1");
                    Socket socketToClient = ss.accept();
                    ClntHandler clientHandler = new ClntHandler(socketToClient);
                    System.out.println("2");
                    clientHandler.start();
                    System.out.println("3");
                    
                }
                
            }catch(Exception e)
            {
                System.out.println("Error"+e);
            }finally{
                System.out.println("IN Finally");
            }

        }
    }
    class ClntHandler extends Thread
    {
        Socket subSocket;
        ObjectInputStream inputStream;
        public ClntHandler(Socket socket) throws Exception 
        {
            System.out.println("cln getting called?");
            subSocket=socket;
            inputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("in clnthandler constr");
        }
        @Override
        public void run()
        {
            try{
                while(true)
                {
                    System.out.println("still waiting for image!");
                    UserPicture usePic = (UserPicture)inputStream.readObject();
                    System.out.println("got the image");
                    oos = new ObjectOutputStream(subSocket.getOutputStream());
                    oos.writeObject("Yes");
                    oos.flush();
                    BufferedImage userImage = usePic.getProfilePic();
                    
                    if(userImage==null)System.out.println("the image is null");
                    
                    int userID=usePic.getUserID();
                    //File output=new File("C:\\Users\\Hooman Z\\Desktop\\steph"+userID+".jpg");
                    File output=new File("../var/www/html/CS201/profile_pictures/"+userID+".jpg");
                    ImageIO.write(userImage, "jpg", output);
                    System.out.println(userID);

                }
                
            }catch(Exception e)
            {
                System.out.println("Error"+e);
            }finally{
                System.out.println("IN Finally");
            }
        }
    }
    public static void main(String[] args) {
        ServerSide s=new ServerSide();
    }


}