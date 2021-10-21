package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static final int portNumber = 6013;

    public static void main(String[] args){
        ServerSocket sock;
        try {
            sock = new ServerSocket(portNumber);

            while (true){

                Socket client = sock.accept();
                InputStream sockIn = client.getInputStream();
                OutputStream sockOut = client.getOutputStream();

                try {
                    while (true){
                        int nextByte = sockIn.read();
                        if(nextByte == -1) {
                            break;
                        } else {
                            sockOut.write(nextByte);
                        }
                    }
                } catch (IOException ioe){
                    System.err.println(ioe);
                } finally {
                    // sockOut.flush(); //per documentation, this function does nothing
                    client.shutdownOutput();
                }
            }
        } catch (IOException ioe){
            System.err.println(ioe);
        }
    }
}