package com.changyi.fi.core.tool;

import com.changyi.fi.util.FIConstants;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;

public class FileReader {

    public static String readFileContentByClasspath(String fileName) throws Exception {
        ClassLoader classLoader = FileReader.class.getClassLoader();
        URL url = classLoader.getResource(fileName);
        File file = new File(url.getFile());
        return FileUtils.readFileToString(file, FIConstants.DEFAULT_CHARSET);
    }
}
