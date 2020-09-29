import java.io.*;
import java.net.*;
import java.util.*;

public class Slide_Sender
{
     Socket sender;
     ObjectOutputStream out;
     ObjectInputStream in;
     String pkt;
     char data='a';
     int SeqNum = 1, SWS = 5;
     int LAR = 0, LFS = 0;
     int NF;
     Slide_Sender()
     {
     }
   
     public void SendFrames()
     {
          if((SeqNum<=15)&&(SWS > (LFS - LAR)) )
          {
              try
              {
                   NF = SWS - (LFS - LAR);
                   for(int i=0;i<NF;i++)
                   {
                        pkt = String.valueOf(SeqNum);
                        pkt = pkt.concat(" ");
                        pkt = pkt.concat(String.valueOf(data));
                        out.writeObject(pkt);
                        LFS = SeqNum;
                        System.out.println("Sent  " + SeqNum + "  " + data);
                            
                        data++;
                        if(data=='f')
                             data='a';
                        SeqNum++;
                        out.flush();
                   }
              }  
              catch(Exception e)
              {}
          }
     }
    
     public void run() throws IOException
     {
          sender = new Socket("localhost",1500);
          out = new ObjectOutputStream(sender.getOutputStream());
          in = new ObjectInputStream(sender.getInputStream());
       
          while(LAR<15)
          {       
              try
              {  
                   SendFrames();      
                  
                   String Ack = (String)in.readObject();
                   LAR = Integer.parseInt(Ack);
                   System.out.println("ack received : " + LAR);
              }       
              catch(Exception e)
              {
              }
          }
         
          in.close();
          out.close();
          sender.close();
          System.out.println("\nConnection Terminated.");  
     }
    
     public static void main(String as[]) throws IOException
     {
          Slide_Sender s = new Slide_Sender();
          s.run();
     }
}

