package pl.api.itoffers.shared.utils.fileManager;

import org.apache.commons.io.IOUtils;

import java.io.*;

public class FileManager {
    private final static String ERROR_MESSAGE = "Internal Error on saving invalid JSON from provider";

    public void saveFile(String rawJsonPayload, String extension) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            File dir = new File ("data/invalid");
            File file = new File(dir, "jjit_invalid."+extension);

            if (!file.exists()) {
                file.createNewFile();
            }

            fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);
            bw.write(rawJsonPayload);
        } catch (IOException e) {
            throw new RuntimeException("Error on saving invalid JSON from provider", e);
        } finally {
            if (null != fw) {
                try {
                    fw.close();
                } catch (IOException e) {
                    throw new RuntimeException(ERROR_MESSAGE, e);
                }
            }
            if (null != bw) {
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(ERROR_MESSAGE, e);
                }
            }
        }
    }

    public static String readFile(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        try {
            return IOUtils.toString(fis, "UTF-8");
        } catch (Exception e) {
            throw e;
        } finally {
            fis.close();
        }
    }
}
