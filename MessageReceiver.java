/*
 * Project: Client Server Performance Measurement Program
 * Authors: Jessica Lynch and Andrew Arnopoulos
 * Date:    27-Apr-2015
 */
package tcpclientserver; // package for Netbeans project

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;


/**
 * MessageReceiver program starts each thread to receive a message.
 */
public class MessageReceiver extends Thread implements MessageSender<String>
{
    
    private DataInputStream input;
    private DataOutputStream output;
    private Socket clientSocket;

    public MessageReceiver( Socket aClientSocket )
    {
        try
        {
            clientSocket = aClientSocket;
            input = new DataInputStream( clientSocket.getInputStream() );
            output = new DataOutputStream( clientSocket.getOutputStream() );
            this.start();
        }
        catch( IOException e )
        {
            System.out.println("MessageHandler: " + e.getMessage() );
        }
    }

    public void sendMessage(String str) {
        System.out.println("Receive from: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " message - " + str);
        try {
            System.out.println(str);
            output.writeInt(str.length());
            output.writeBytes(str);
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("An Error Occured While Sending A Message.");
        }

        try 
        {
            clientSocket.close();
        }
        catch( IOException e )
        {
            System.out.println("Close failed: " + e.getMessage() );
        }   
        
    }

    public void run() 
    {
        int numBytes;
        byte[] digit;
        String str;

        //FileWriter outFile = new FileWriter("test.txt");
        //BufferedWriter bufWriter = new BufferedWriter( outFile );
        try 
        {
            numBytes = input.readInt();
            System.out.println("Read length: " + numBytes);
            digit = new byte[numBytes];
            System.out.println("Writing ...");
            for( int i = 0; i < numBytes; i++ )
            {
                try
                {
                    digit[i] = input.readByte();
                }
                catch( IOException e )
                {
                    System.out.println("Read: " + e.getMessage() );
                }
            }

            str = new String( digit );
            System.out.println("hello" + str);
            //bufWriter.append( str );
            //bufWriter.close();

            MessageQueue.queue.add(new MessageContainer<String>(this,str));
            
        }
        catch( EOFException e )
        {
            System.out.println("Read: " + e.getMessage() );
        }
        catch( IOException e )
        {
            System.out.println("Output file: " + e.getMessage() );
        }
        
              
    }
    
}
/* //My old code.
public class MessageReceiver extends Thread
{
    
    DataInputStream input;
    DataOutputStream output;
    Socket clientSocket;

    public MessageReceiver( Socket aClientSocket )
    {
        try
        {
            clientSocket = aClientSocket;
            input = new DataInputStream( clientSocket.getInputStream() );
            output = new DataOutputStream( clientSocket.getOutputStream() );
            this.start();
        }
        catch( IOException e )
        {
            System.out.println("MessageHandler: " + e.getMessage() );
        }
    }

    public void run() 
    {
        int numBytes;
        byte[] digit;
        String str;

        //FileWriter outFile = new FileWriter("test.txt");
        //BufferedWriter bufWriter = new BufferedWriter( outFile );
        try 
        {
            numBytes = input.readInt();
            System.out.println("Read length: " + numBytes);
            digit = new byte[numBytes];
            System.out.println("Writing ...");
            for( int i = 0; i < numBytes; i++ )
            {
                try
                {
                    digit[i] = input.readByte();
                }
                catch( IOException e )
                {
                    System.out.println("Read: " + e.getMessage() );
                }
            }
            str = new String( digit );
            //bufWriter.append( str );
            //bufWriter.close();
            System.out.println("Receive from: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " message - " + str);
            output.writeInt(str.length());
            output.writeBytes(str);
        }
        catch( EOFException e )
        {
            System.out.println("Read: " + e.getMessage() );
        }
        catch( IOException e )
        {
            System.out.println("Output file: " + e.getMessage() );
        }
        
        try 
        {
            clientSocket.close();
        }
        catch( IOException e )
        {
            System.out.println("Close failed: " + e.getMessage() );
        }         
    }
    
}
*/