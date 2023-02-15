package com.wsi.mhe.server.impl;

import com.wsi.mhe.server.api.ClientHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
public class ClientHandlerServiceImpl extends Thread implements ClientHandlerService {

    private Socket clientSocket;

    private OutputStream out;
    private OutputStreamWriter writer;
    private BufferedWriter bw;
    private DataInputStream in;

    public ClientHandlerServiceImpl(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    @Override
    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    @Override
    public void run() {
        try {
            out = clientSocket.getOutputStream();
            in = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            log.error("Error reading from client socket...");
            throw new RuntimeException(e);
        }
        writer = new OutputStreamWriter(out);
        bw = new BufferedWriter(writer);

        Instant start = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant recent = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        boolean first = true;

        while (true) {
            try {

                if (in.available()>0) {
                    log.info("Strating to process request for {} at {}", clientSocket.getInetAddress()+":"+clientSocket.getPort(), Instant.now().truncatedTo(ChronoUnit.SECONDS));
                    StringBuilder stringBuffer = null;
                    String input = null;
                    int x;

                    try {
                        stringBuffer = new StringBuilder();
                        while (true) {
                            x = in.readByte();
                            if (x == 0x03 || x == -1) break; // read until ETX or socket reset
                            if (x != 0x02)
                                stringBuffer.append((char) x);
                        }
                        input = stringBuffer.toString();

                        log.info("Message Received from client {}:{} is: {}", clientSocket.getInetAddress(), clientSocket.getPort(), input);
                        log.info("Sending ACK...");

                        if (first)
                        {
                            first = false;
                            start = Instant.now().truncatedTo(ChronoUnit.SECONDS);
                        }
                        bw.write(0x02);
                        String ackMsg = "ACK";
                        if (!StringUtils.isEmpty(input))
                            ackMsg = input.substring(0, 5) + "ACK";
                        bw.write(ackMsg);
                        bw.write(0x03);
                        bw.flush();
                        recent = Instant.now().truncatedTo(ChronoUnit.SECONDS);
                        log.info("ACK {} sent...", ackMsg);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (start.plusSeconds(180).compareTo(recent) < 0 ) {
                    clientSocket.close();
                    log.info("Process complete for {} at {}", clientSocket.getInetAddress()+":"+clientSocket.getPort(), Instant.now().truncatedTo(ChronoUnit.SECONDS));
                    break;
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
