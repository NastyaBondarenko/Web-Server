package com.bondarenko.webserver.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private String webAppPath;

    public Server(int port, String webAppPath) {
        ensureValueOfPortIsInAcceptableLimits(port);
        ensureWebAppPathIsNotNull(webAppPath);
        this.port = port;
        this.webAppPath = webAppPath;
    }

    public Server() {
        this.port = port;
    }

    public void setPort(int port) {
        ensureValueOfPortIsInAcceptableLimits(port);
        this.port = port;

    }

    public void setWebAppPath(String webAppPath) {
        ensureWebAppPathIsNotNull(webAppPath);
        this.webAppPath = webAppPath;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                         BufferedOutputStream outputStream = new BufferedOutputStream(socket.getOutputStream())) {

                        RequestHandler requestHandler = new RequestHandler(bufferedReader, outputStream, webAppPath);
                        requestHandler.handle();

                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void ensureValueOfPortIsInAcceptableLimits(int port) {
        if (port > 65535 || port < 0) {
            throw new IllegalArgumentException("Value of port have to be between [0,65535]");
        }
    }
    private void ensureWebAppPathIsNotNull(String webAppPath) {
        if (webAppPath==null) {
            throw new NullPointerException("WebAppPath can not be null");
        }
    }

}




