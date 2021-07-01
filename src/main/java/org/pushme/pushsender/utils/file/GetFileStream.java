package org.pushme.pushsender.utils.file;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 6/1/21.
 * Time: 02:43.
 */
public interface GetFileStream {
    InputStream getStream(String path, String name) throws IOException;
}
