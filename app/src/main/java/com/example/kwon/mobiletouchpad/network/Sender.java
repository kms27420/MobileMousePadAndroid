package com.example.kwon.mobiletouchpad.network;

/**
 * Created by Kwon on 2018-04-15.
 */

public abstract class Sender {
    public abstract void sendData(String utfData);
    public abstract void disconnect();
    public abstract boolean checkNormallyDisconnected();
}
