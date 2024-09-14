package org.esoteric_organisation.template_paper_plugin.http_server;

import com.sun.net.httpserver.HttpServer;

import org.esoteric_organisation.template_paper_plugin.TemplatePaperPlugin;
import org.esoteric_organisation.template_paper_plugin.file.FileUtil;
import org.esoteric_organisation.template_paper_plugin.http_server.event.listeners.PlayerJoinListener;
import org.esoteric_organisation.template_paper_plugin.resource_pack.ResourcePackManager;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;

public class HttpServerManager {

  private ExamplePaperPlugin plugin;

  private String hostName;
  private int port;

  private final int successResponseCode = 200;
  private final int notFoundResponseCode = 404;

  private HttpServer server;

  public int getPort() {
    return server.getAddress().getPort();
  }

  public String getHostName() {
    return server.getAddress().getHostName();
  }

  public String getSocketAddress() {
    return getHostName() + ":" + getPort();
  }

  public HttpServerManager(ExamplePaperPlugin plugin) {
    if (plugin.getResourcePackManager().getResourcePackZipFile() == null) {
      return;
    }

    this.plugin = plugin;

    hostName = Bukkit.getServer().getIp();
    port = plugin.getConfig().getInt("http-server.port");

    try {
      server = HttpServer.create(new InetSocketAddress(hostName, port), 0);
    } catch (IOException exception) {
      exception.printStackTrace();
      return;
    }

    server.createContext("/", new ResourcePackDownloadHandler());

    server.setExecutor(null);
    server.start();

    Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(plugin, this), plugin);
  }

  class ResourcePackDownloadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
      ResourcePackManager resourcePackManager = plugin.getResourcePackManager();      

      File file = new File(resourcePackManager.getResourceZipFilePath());

      if (file.exists()) {
        exchange.getResponseHeaders().set("Content-Type", resourcePackManager.getResourcePackFileMimeType());
        exchange.getResponseHeaders().set("Content-Disposition", "attachment; filename=\"" + resourcePackManager.getResourcePackResourceFolderName() + FileUtil.getFileExtensionSeparator() + resourcePackManager.getResourcePackFileExtension() + "\"");

        exchange.sendResponseHeaders(successResponseCode, file.length());

        try (FileInputStream fileInputStream = new FileInputStream(file); OutputStream outputStream = exchange.getResponseBody()) {
          byte[] buffer = new byte[1024];
          int count;
          while ((count = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, count);
          }
        }
      } else {
        String response = "404 (Not Found)\n";
        exchange.sendResponseHeaders(notFoundResponseCode, response.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
      }
    }
  }
}
