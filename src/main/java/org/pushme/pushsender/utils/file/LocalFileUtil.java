package org.pushme.pushsender.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 6/1/21.
 * Time: 02:43.
 */
public class LocalFileUtil implements GetFileStream {

    public InputStream getStream(String path, String name) throws FileNotFoundException {
        return new FileInputStream(path + File.separator + name);
    }
}
