package com.wsi.mhe.server.impl;

import com.wsi.mhe.server.api.ClientHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Slf4j
public class ClientHandlerImpl extends Thread implements ClientHandler {

    private final Socket clientSocket;

    private PrintWriter out;
    private BufferedReader in;

    public ClientHandlerImpl(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void start()
    {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            log.error("Error reading from client socket...");
            throw new RuntimeException(e);
        }

        String inputLine;
        while (true) {
            try {
                inputLine = in.readLine();
                if ("X".equals(inputLine)) {
                    log.error("Client input is 'X'...ending communication");
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            out.println(inputLine);
            log.info(inputLine);
        }

        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            log.info("Unable to close one of: client socket, buffer reader or print writer");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleClientRequest(String mheRequest) {
        // real world MHE request, pass on the JSON to this method to do necessary updates
    }
}
