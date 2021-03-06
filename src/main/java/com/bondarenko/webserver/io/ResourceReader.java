package com.bondarenko.webserver.io;

import com.bondarenko.webserver.entity.HttpStatus;
import com.bondarenko.webserver.exception.ServerException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ResourceReader {

    public static byte[] readContent(String webAppPath, String uri) {
        try {
            File file = new File(webAppPath, uri);
            return Files.readAllBytes(file.toPath());

        } catch (IOException ex) {
            throw new ServerException(ex, HttpStatus.NOT_FOUND);
        }
    }
}
