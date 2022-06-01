package com.bondarenko.webserver.response;

import com.bondarenko.webserver.exception.ServerException;
import com.bondarenko.webserver.io.ResourceReader;
import com.bondarenko.webserver.server.Server;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceReaderTest {
    private String path = Objects.requireNonNull(ResourceReaderTest.class.getClassLoader().getResource("")).getPath() + "test";

    private final ResourceReader resourceReader = new ResourceReader();

    private final String expectedContent = """
            The World Bank also supports large-scale sustainable forest and land use programs, such as the Forest Carbon
            Partnership Facility and the BioCarbon Fund Initiative for Sustainable Forest Landscapes.
            Healthy forests provide critical ecosystem services important to nature, people and economies, 
            including the provision of drinking water, water and climate cycle regulation.""";

    @BeforeEach
    public void init() throws IOException {
        String path = Objects.requireNonNull(ResourceReaderTest.class.getClassLoader().getResource("")).getPath() + "test";
        File dir = new File(path);
        File file = new File(path + "/file.txt");
        dir.mkdir();
        file.createNewFile();

        OutputStream outputStream = new FileOutputStream(file);
        byte[] content = expectedContent.getBytes();
        outputStream.write(content);
        outputStream.close();
    }

    @AfterEach
    public void clean() {
        String path = Objects.requireNonNull(ResourceReaderTest.class.getClassLoader().getResource("")).getPath() + "test";
        File dir = new File(path);
        File file = new File(path + "/file.txt");
        file.delete();
        dir.delete();
    }

    @Test
    @DisplayName("when Read Content than String Of Content Is Returned")
    public void whenReadContent_thanStringOfContent_IsReturned() {
        String actualContent = new String(resourceReader.readContent(path, "/file.txt"));
        assertEquals(expectedContent, actualContent);
    }

    @Test
    @DisplayName("when Read Content with Empty Web App Path And Uri than Null Pointer Exception Thrown")
    public void whenReadContent_withEmptyWebAppPathAndUri_thanNullPointerException_Thrown() {
        Assertions.assertThrows(NullPointerException.class, () -> ResourceReader.readContent(null, null));
    }

    @Test
    @DisplayName("when Read Content with Not Existing Uri than Server Exception Thrown")
    public void whenReadContent_withNotExistingUri_thanServerException_Thrown() {
        Assertions.assertThrows(ServerException.class, () ->
                ResourceReader.readContent("path", "----.txt"));
    }

    @Test
    @DisplayName("when Read Content with Not Existing WebAppPath than Server Exception Thrown")
    public void whenReadContent_withNotExistingWebAppPath_thanServerException_Thrown() {
        Assertions.assertThrows(ServerException.class, () ->
                ResourceReader.readContent(path="_/", "test.txt"));
    }
}