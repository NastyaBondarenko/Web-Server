package com.bondarenko.webserver.response;

import com.bondarenko.webserver.exception.ServerException;
import com.bondarenko.webserver.io.ResourceReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ResourceReaderTest {

    @Test
    @DisplayName("when Read Content with Empty Web App Path And Uri than Null Pointer Exception Thrown")
    public void whenReadContent_withEmptyWebAppPathAndUri_thanNullPointerException_Thrown() {
        Assertions.assertThrows(NullPointerException.class, () -> ResourceReader.readContent(null, null));
    }

    @Test
    @DisplayName("when Read Content with Not Existing Uri than Server Exception Thrown")
    public void whenReadContent_withNotExistingUri_thanServerException_Thrown() {
        Assertions.assertThrows(ServerException.class, () ->
                ResourceReader.readContent("src/main/resources/", "----.txt"));
    }

    @Test
    @DisplayName("when Read Content with Not Existing WebAppPath than Server Exception Thrown")
    public void whenReadContent_withNotExistingWebAppPath_thanServerException_Thrown() {
        Assertions.assertThrows(ServerException.class, () ->
                ResourceReader.readContent("src/main/resource_/", "test.txt"));
    }

    @Test
    @DisplayName("when Read Content than Array Of Bytes Is Returned")
    public void whenReadContent_thanArrayOfBytes_IsReturned() {
        ResourceReader resourceReader = new ResourceReader();
        byte[] actualContent = resourceReader.readContent("src/test/resources/test/", "test.txt");

        Assertions.assertEquals('F', (char) actualContent[0]);
        Assertions.assertEquals('o', (char) actualContent[1]);
        Assertions.assertEquals('r', (char) actualContent[2]);
        Assertions.assertEquals('e', (char) actualContent[3]);
        Assertions.assertEquals('s', (char) actualContent[4]);
        Assertions.assertEquals('t', (char) actualContent[5]);
        Assertions.assertEquals(6, (char) actualContent.length);
    }
}