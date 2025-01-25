package pl.api.itoffers.shared.utils.fileManager;

import java.io.*;
import org.apache.commons.io.IOUtils;

public final class FileManager {

  private FileManager() {}

  public static String readFile(String path) throws IOException {
    try (FileInputStream fis = new FileInputStream(path)) {
      return IOUtils.toString(fis, "UTF-8");
    }
  }
}
