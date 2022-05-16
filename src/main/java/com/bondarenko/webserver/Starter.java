package com.bondarenko.webserver;

import com.bondarenko.webserver.server.Server;

public class Starter {
    private static final String WEB_APP_PATH = "src/test/resources/webapp";
    private static final int PORT = 3000;

    public static void main(String[] args) {
        Server server = new Server(PORT, WEB_APP_PATH);

        server.start();
    }
}
