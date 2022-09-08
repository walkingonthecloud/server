package com.wsi.mhe.server.api;

import java.net.Socket;

public interface ClientHandler {

    void handleClientRequest(String mheRequest);

}
