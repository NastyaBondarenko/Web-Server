package com.bondarenko.webserver.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ServerTest {
    private Server server = new Server();

    @Test
    @DisplayName("test Set Port when Port Is More Than 65535 throw IllegalArgumentException")
    public void testSetPort_whenPortIsMoreThan_65535_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                server.setPort(70000));
    }

    @Test
    @DisplayName("test Set Port when Port Is Less Than Zero throw IllegalArgumentException")
    public void testSetPort_whenPortIsLessThanZero_throwIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                server.setPort(-2000));
    }

    @Test
    @DisplayName("test Set Web App Path when Web App Path Is Null than throw Null Pointer Exception")
    public void testSetWebAppPath_whenWebAppPathIsNull_thanThrowNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () ->
                server.setWebAppPath(null));
    }
}


