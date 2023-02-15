package com.wsi.mhe.server.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.Socket;

@RestController
@RequestMapping(value = "/api")
public class SocketClientController {

    public SocketClientController(){}

    @GetMapping(value = "/send/{msg}")
    public ResponseEntity<?> sendMsg(@PathVariable String msg) throws IOException {
        Socket socket = new Socket("10.168.88.102", 7104);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
