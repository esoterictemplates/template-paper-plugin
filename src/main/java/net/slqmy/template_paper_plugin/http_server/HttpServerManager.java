package net.slqmy.template_paper_plugin.http_server;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import net.slqmy.template_paper_plugin.TemplatePaperPlugin;

public class HttpServerManager {

  private final TemplatePaperPlugin plugin;

  public HttpServerManager(TemplatePaperPlugin plugin) {
    this.plugin = plugin;

    HttpServer server;
    try {
      server = HttpServer.create(new InetSocketAddress(8000), 0);
    } catch (IOException exception) {
      exception.printStackTrace();
      return;
    }

    // Create a context for a specific path and set the handler
    server.createContext("/", new MyHandler());

    // Start the server
    server.setExecutor(null); // Use the default executor
    server.start();

    plugin.getLogger().info("Server is running on port 8000");
  }

  class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
      // Set the path to the zip file
      String filePath = "plugins/template-paper-plugin/Template Paper Plugin Resource Pack.zip";
      File file = new File(filePath);

      if (file.exists()) {
        // Set the response headers
        exchange.getResponseHeaders().set("Content-Type", "application/zip");
        exchange.getResponseHeaders().set("Content-Disposition", "attachment; filename=\"Template Paper Plugin Resource Pack.zip\"");

        // Send the response headers with the file length
        exchange.sendResponseHeaders(200, file.length());

        // Write the file to the response body
        try (FileInputStream fis = new FileInputStream(file); OutputStream os = exchange.getResponseBody()) {
          byte[] buffer = new byte[1024];
          int count;
          while ((count = fis.read(buffer)) != -1) {
            os.write(buffer, 0, count);
          }
        }
      } else {
        // If the file does not exist, return a 404 error
        String response = "404 (Not Found)\n";
        exchange.sendResponseHeaders(404, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
      }
    }
  }
}
