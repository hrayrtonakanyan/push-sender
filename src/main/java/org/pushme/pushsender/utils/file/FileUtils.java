package org.pushme.pushsender.utils.file;


import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 6/1/21.
 * Time: 02:43.
 */
public class FileUtils {

    private static final FileUtils instance = new FileUtils();
    private final GetFileStream getFileStream;

    private FileUtils() {
        getFileStream = new LocalFileUtil();
    }

    public static FileUtils getInstance() {
        return instance;
    }

    public InputStream getFileInputStream(String path, String name) throws IOException {
        return getFileStream.getStream(path, name);
    }
}
