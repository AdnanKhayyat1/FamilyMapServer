package Server;

import com.sun.net.httpserver.*;

import java.io.*;
import java.net.*;
public class MainServer {
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    private void run(String portNumber){

        System.out.println("Initializing HTTP Server");
        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        server.setExecutor(null);
        System.out.println("Creating contexts");

        server.createContext("/", new FileHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/person", new PersonsAllHandler());
        server.createContext("/person/", new PersonIDHandler());
        server.createContext("/event", new GetEventHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/user/register", new RegisterHandler());
        System.out.println("Starting server");
        server.start();
        System.out.println("Server listening on port " + portNumber);
    }
    public static void main(String[] args) throws IOException {
        String portNumber = args[0];
        new MainServer().run(portNumber);
    }

}

