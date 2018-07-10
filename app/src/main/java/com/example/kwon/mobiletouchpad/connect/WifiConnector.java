package com.example.kwon.mobiletouchpad.connect;

import com.example.kwon.mobiletouchpad.network.Sender;
import com.example.kwon.mobiletouchpad.network.WifiSender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Kwon on 2018-04-15.
 */

public class WifiConnector implements Connector {
    private final int WAIT_SEC = 10;

    private int port;
    private InetAddress pcAddress;

    private DatagramSocket broadcastSocket;
    private Socket tcpSocket;

    private boolean isPortChanged;

    public void setPort(int port) {
        isPortChanged = true;
        this.port = port;
    }

    @Override
    public Sender connect() throws Exception {
        Sender returnValue = null;
        try {
            if(isPortChanged)   receiveBroadcast();
            isPortChanged = false;
            returnValue = connectDirectly();
        } catch(Exception e) {
            disconnect();
            throw new Exception("Connection failed.");
        }
        return returnValue;
    }

    @Override
    public void disconnect() {
        if(broadcastSocket !=null && !broadcastSocket.isClosed())  broadcastSocket.close();
        if(tcpSocket!=null && !tcpSocket.isClosed())    try{tcpSocket.close();} catch(IOException e){}
        broadcastSocket = null;
        tcpSocket = null;
    }

    private void receiveBroadcast() throws Exception {
        broadcastSocket = new DatagramSocket(port);
        broadcastSocket.setSoTimeout(WAIT_SEC*1000);
        DatagramPacket packet = new DatagramPacket(new byte[4], 4);
        broadcastSocket.receive(packet);
        broadcastSocket.send(packet);
        broadcastSocket.close();
        broadcastSocket = null;
        pcAddress = packet.getAddress();
    }

    private Sender connectDirectly() throws Exception {
        tcpSocket = new Socket();
        tcpSocket.connect(new InetSocketAddress(pcAddress, port), WAIT_SEC*1000);
        return new WifiSender(tcpSocket);
    }
}
