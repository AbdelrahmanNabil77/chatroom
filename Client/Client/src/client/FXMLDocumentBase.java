package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javax.swing.JOptionPane;

public class FXMLDocumentBase extends BorderPane implements Runnable  {

    protected final TextArea txtShow;
    protected final FlowPane flowPane;
    protected final TextField txtSend;
    protected final Button btnSend;
    Socket server;
    PrintStream ps;
    DataInputStream dis;
    Thread th;
    public FXMLDocumentBase() {
        th=new Thread(this);
        try {
            server=new Socket(InetAddress.getLocalHost(),5007);
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtShow = new TextArea();
        flowPane = new FlowPane();
        txtSend = new TextField();
        btnSend = new Button();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        BorderPane.setAlignment(txtShow, javafx.geometry.Pos.CENTER);
        txtShow.setEditable(false);
        txtShow.setPrefHeight(200.0);
        txtShow.setPrefWidth(200.0);
        setCenter(txtShow);

        BorderPane.setAlignment(flowPane, javafx.geometry.Pos.CENTER);
        flowPane.setPrefHeight(48.0);
        flowPane.setPrefWidth(600.0);

        txtSend.setPrefHeight(61.0);
        txtSend.setPrefWidth(432.0);

        btnSend.setMnemonicParsing(false);
        btnSend.setPrefHeight(61.0);
        btnSend.setPrefWidth(166.0);
        btnSend.setText("send");
        setBottom(flowPane);
        btnSend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ps.println(txtSend.getText());
                txtSend.clear();
                
            }
        });
        
        flowPane.getChildren().add(txtSend);
        flowPane.getChildren().add(btnSend);
        th.start();
        
      /*  new Thread(){
            @Override
            public void run() {
                try {
                    dis = new DataInputStream(server.getInputStream ());
                    ps = new PrintStream(server.getOutputStream ());
                    while(true){                                
                        txtShow.appendText(dis.readLine()+"\n");
                    }
                } catch (IOException ex) {
                    
                    Logger.getLogger(FXMLDocumentBase.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }

        }.start();*/
        
      /*new Thread(){
        public void run(){
            try {
                dis=new DataInputStream (server.getInputStream());
                ps=new PrintStream(server.getOutputStream());
                while(true){
                    try {
                        
                        String msg = dis.readLine();
                        txtShow.appendText(msg);
                        txtShow.appendText("\n");
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentBase.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }   } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }.start();*/
        
        

    }

    @Override
    public void run() {
                try {
                    dis = new DataInputStream(server.getInputStream ());
                    ps = new PrintStream(server.getOutputStream ());
                    while(true){
                        //if(server.isClosed()==true||th.isAlive()==true)
                          //  JOptionPane.showMessageDialog(null, "server is down");
                        txtShow.appendText(dis.readLine()+"\n");
                    }
                } catch (IOException ex) {

                    try {
                        th.stop();
                        dis.close();
                        ps.close();
                        server.close();

                    } catch (IOException ex1) {
                        Logger.getLogger(FXMLDocumentBase.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    //JOptionPane.showMessageDialog(null, "server is down");
                    Logger.getLogger(FXMLDocumentBase.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
    }

