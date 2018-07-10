package com.example.kwon.mobiletouchpad.connect;

import com.example.kwon.mobiletouchpad.network.Sender;

/**
 * Created by Kwon on 2018-05-10.
 */

public interface Connector {
    Sender connect() throws Exception;
    void disconnect();
}
