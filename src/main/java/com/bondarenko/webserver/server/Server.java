package com.bondarenko.webserver.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int DEFAULT_PORT = 3000;
    private static final String DEFAULT_PATH = "src/main/resources/";
//    private static final String DEFAULT_PATH = Objects.requireNonNull(Server.class.getClassLoader().getResource("")).getPath();

    private int port;
    private String path;

    public Server() {
        this(DEFAULT_PORT);
    }

    public Server(int port) {
        validatePort(this.port = port);
    }

    public Server(String webAppPath) {
        this(DEFAULT_PORT, webAppPath);
    }

    public Server(int port, String webAppPath) {
        this(port);
        setWebAppPath(webAppPath);
    }

    public void setPort(int port) {
        validatePort(port);
        this.port = port;
    }

    public String setWebAppPath(String webAppPath) {
        path = DEFAULT_PATH + webAppPath;
        validate(path);
        return path;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    try (BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                         BufferedOutputStream socketOutputStream = new BufferedOutputStream(socket.getOutputStream())) {

                        RequestHandler requestHandler = new RequestHandler(socketBufferedReader, socketOutputStream, path);
                        requestHandler.handle();
                    }
                }
            }
        }
    }

    private void validatePort(int port) {
        if (port > 65535 || port < 0) {
            throw new IllegalArgumentException("Value of port have to be between [0,65535]");
        }
    }

    private void validate(String webAppPath) {
        if (webAppPath == null) {
            throw new NullPointerException("WebAppPath can not be null");
        }
    }
}







