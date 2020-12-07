/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SW
 */
public class Server {

    ServerSocket mySocket;
    public Server() {
        
        try {
            System.out.println("hi server constructor");
            //assign port no. to my Server
            mySocket = new ServerSocket(5007);
              while (true) {
                    try {
                        Socket s = mySocket.accept();
                        new ChatHandler(s);
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*new Thread() {
            public void run() {
                while (true) {
                    try {
                        Socket s = mySocket.accept();
                        new chatHandler(s);
                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();*/
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("hi server");
        new Server();
    }

    public static class ChatHandler  {
        DataInputStream dis;
        PrintStream ps;
          static Vector<ChatHandler>clientsVector = new Vector<ChatHandler>();
        public ChatHandler(Socket cs) {
            try {
                dis = new DataInputStream(cs.getInputStream());
                ps = new PrintStream(cs.getOutputStream());
                clientsVector.add(this);
                //start();
                new Thread() {
                    public void run() {
                        while (true) {
                            String str=null;
                            try {
                                str = dis.readLine();
                                sendMsgToAll(str);
                            } catch (IOException ex) {
                                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }.start();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        void sendMsgToAll(String str) {
            for (ChatHandler ch : clientsVector) {
                ch.ps.println(str);
            }
        }
    }
    
    

   

 
}
