package com.wsi.mhe.server.impl;

import com.wsi.mhe.server.api.SocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Component
@Slf4j
public class SocketServerImpl implements SocketServer, CommandLineRunner {

    @Value("${socket.server.port}")
    private int serverPort;

    @Autowired
    RepositoryServiceImpl repositoryService;

    private ServerSocket serverSocket;
    private Socket clientSocket;

    @Override
    public void init() throws IOException {
        serverSocket = new ServerSocket(serverPort);
        while (true) {
            clientSocket = serverSocket.accept();
            log.info("Client request received...processing request!");
            new ClientHandlerServiceImpl(clientSocket).start();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting server from command_line_runner");
        init();
    }
}
