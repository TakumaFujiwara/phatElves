package com.example.tyler.client;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
public class ClientActivity extends Activity {

    EditText serverIp,smessage;
    TextView chat,hat;
    ScrollView scrollchat;
    Button connectPhones,sent;
    String serverIpAddress = "",msg = "",str;
    Handler handler = new Handler();
    TextView hi;
    LinearLayout linearchat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        chat = (TextView) findViewById(R.id.chat);
        serverIp = (EditText) findViewById(R.id.server_ip);
        scrollchat= (ScrollView) findViewById(R.id.scrollchat);
        linearchat=(LinearLayout) findViewById(R.id.linearchat);
        //smessage = (EditText) findViewById(R.id.smessage);
        /*
        sent = (Button) findViewById(R.id.sent_button);
        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread sentThread = new Thread(new sentMessage());
                sentThread.start();
            }
        });*/
        connectPhones = (Button) findViewById(R.id.connect_phones);
        connectPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverIpAddress =serverIp.getText().toString();
                if (serverIpAddress.equals("")) {
                    serverIpAddress = "172.20.10.5";
                }
                else if(!serverIpAddress.equals("")){
                    //serverIpAddress = "172.20.10.5;
                    serverIpAddress =serverIp.getText().toString();
                }
                Thread clientThread = new Thread(new
                        ClientThread());
                clientThread.start();
            }
        });
    }

    /*class sentMessage implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                InetAddress serverAddr =
                        InetAddress.getByName(serverIpAddress);
                Socket socket = new Socket(serverAddr,9090); //create client socket
                DataOutputStream os = new
                        DataOutputStream(socket.getOutputStream());
                str = smessage.getText().toString();
                str = str + "\n";
                msg = msg + "Client : " + str;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        chat.setText(msg);
                    }
                });
                os.writeBytes(str);
                os.flush();
                os.close();
                socket.close();
            }
            catch(IOException e)
            {

            }
        }
    }*/
    public class ClientThread implements Runnable
    {
        public void run() {

            try {
                while (true) {
                    InetAddress serverAddr =
                            InetAddress.getByName(serverIpAddress);
                    System.out.println("hi");
                    Socket socket = new Socket(serverAddr, 9090); //create client socket
/*******************************************
 setup i/p streams
 ******************************************/
                    //BufferedReader input =
                    // new BufferedReader(new InputStreamReader(s.getInputStream()));
                    DataInputStream in = new
                            DataInputStream(socket.getInputStream());
                    String line = null;
                    while ((line = in.readLine()) != null) {
                        msg = /*msg +*/ "Server : " + line + "\n";
                        hi=new TextView(ClientActivity.this);
                        hi.setText(msg);
                        hi.setHeight(64);
                        hi.setTextSize(16);
                        //linearchat.addView(hi);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(hi.getParent()!=null)
                                    ((ViewGroup)hi.getParent()).removeView(hi);
                                linearchat.addView(hi);
                            }
                        });
                    }
                    in.close();
                    socket.close();
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        }
    }
}