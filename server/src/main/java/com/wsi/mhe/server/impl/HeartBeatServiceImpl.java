package com.wsi.mhe.server.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;

public class HeartBeatServiceImpl extends Thread{

    private final Logger log = LoggerFactory.getLogger(HeartBeatServiceImpl.class);

    private final PrintWriter out;

    private boolean endThread = false;

    public HeartBeatServiceImpl(PrintWriter out) {
        this.out = out;
    }

    public void setEndThread() {
        this.endThread = true;
    }

    public void run() {
        log.info("Starting heart beats...");
        while (!endThread) {
            try {
                byte stx = 0x02;
                byte etx = 0x03;
                out.write(stx+"00001KEEPALIVE"+etx);
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

