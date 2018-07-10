package com.example.kwon.mobiletouchpad.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Kwon on 2018-04-16.
 */

public class WifiSender extends Sender {
    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;

    private int port;
    private boolean isNormallyDisconnected;

    public WifiSender(Socket socket) {
        this.socket = socket;
        port = this.socket.getPort();
        try {
            output = new DataOutputStream(this.socket.getOutputStream());
            input = new DataInputStream(this.socket.getInputStream());
        } catch(IOException e) {}
    }

    @Override
    public void sendData(String utfData) {
        try {
            output.writeUTF(utfData);
            output.flush();
        } catch(IOException | NullPointerException e) {}
    }

    @Override
    public void disconnect() {
        isNormallyDisconnected = true;

        sendData("q");

        if(socket!=null && !socket.isClosed())  try { socket.close(); } catch(IOException e) {}
        if(input!=null) try {input.close();} catch(IOException e) {}
        if(output!=null) try {output.close();} catch(IOException e) {}

        socket = null;
        input = null;
        output = null;
    }

    @Override
    public boolean checkNormallyDisconnected() {
        try {
            input.readUTF();
            return true;
        } catch(Exception e) {return false;}
    }
}
