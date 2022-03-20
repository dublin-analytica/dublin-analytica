package ie.dublinanalytica.web.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Utils for order service.
 */
public class Utils {

  /**
   * Fetches a file from a remote link.
   *
   * @param link The link to the file
   * @return The string of the file
   * @throws IOException on I/O error
   * @throws InterruptedException If the thread is interrupted
   */
  public static String fetchFile(String link) {
    try {
      URI uri = URI.create(link);
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest fileRequest = HttpRequest
          .newBuilder()
          .uri(uri)
          .GET()
          .build();
      HttpResponse<String> response =
          client.send(fileRequest, HttpResponse.BodyHandlers.ofString());

      return response.body();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
      return "";
    }
  }

  /**
   * Get the size for a dataset.
   *
   * @param link The link to the dataset
   * @return The int size
   * @throws IOException i/o error
   * @throws InterruptedException if the thread is interrupted
   */
  public static int getSize(String link) {
    return fetchFile(link).split("\n").length;
  }
}
