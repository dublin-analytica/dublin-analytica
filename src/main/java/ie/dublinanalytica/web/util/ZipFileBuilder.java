package ie.dublinanalytica.web.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Builder for creating a zip file in memory.
 */
public class ZipFileBuilder {
  private final ByteArrayOutputStream baos;
  private final ZipOutputStream zos;

  public ZipFileBuilder() {
    baos = new ByteArrayOutputStream();
    zos = new ZipOutputStream(baos);
  }

  /**
   * Adds a file to this zip file.
   *
   * @param name The filename
   * @param contents the bytes of the file
   * @return this
   * @throws IOException i/o error for zip entry
   */
  public ZipFileBuilder addFile(String name, byte[] contents) throws IOException {
    ZipEntry entry = new ZipEntry(name);
    zos.putNextEntry(entry);
    zos.write(contents);
    zos.closeEntry();

    return this;
  }

  /**
   * Returns the bytes of the zip file.
   *
   * @return bytes of the zip file
   * @throws IOException i/o exception for ZipOutputStream
   */
  public byte[] build() throws IOException {
    zos.finish();
    zos.flush();
    zos.close();

    return baos.toByteArray();
  }
}
