package com.bykeeasy.application.port.out;

import java.io.InputStream;

public interface FileStoragePort {
    String store(InputStream file, String originalFileName, String folder);
}
