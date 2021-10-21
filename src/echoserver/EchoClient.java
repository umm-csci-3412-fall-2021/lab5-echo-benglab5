package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;

public class EchoClient {
    public static final int portNumber = 6013;

    public static void main(String[] args) {
        String server;
        if (args.length == 0) {
            server = "127.0.0.1";
        } else {
            server = args[0];
        }

        try {
            Socket sock = new Socket(server, portNumber);
            InputStream sockIn = sock.getInputStream();
            OutputStream sockOut = sock.getOutputStream();

            try {
                while(true){
                    int nextByteOut;
                    try {
                        nextByteOut = System.in.read();
                    } catch (IOException ioe) {
                        System.out.println("Error reading from System.in");
                        throw(ioe);
                    }
                    if(nextByteOut == -1) {
                        try {
                            sock.shutdownOutput();
                        } catch (IOException ioe){
                            System.out.println("Error closing output");
                            throw(ioe);
                        }
                    } else {
                        try {
                            sockOut.write(nextByteOut);
                        } catch (IOException ioe) {
                            System.out.println("Error writing to socket");
                            throw(ioe);
                        }
                    }

                    int nextByteIn;
                    try {
                        nextByteIn = sockIn.read();
                    } catch (IOException ioe){
                        System.out.println("Error reading from socket");
                        throw(ioe);
                    }
                    if(nextByteIn == -1){
                        break;
                    } else {
                        System.out.print(nextByteIn);
                    }
                }
            } catch (IOException ioe) {
                System.err.println(ioe);
            } finally {
                sock.close();
            }
            
        } catch (ConnectException ce) {
            System.out.println("Unable to connect to" + server);
        } catch (IOException ioe) {
            System.out.println("IOException encountered creating the server or a stream.");
            System.err.println(ioe);
        }
        
    }
}