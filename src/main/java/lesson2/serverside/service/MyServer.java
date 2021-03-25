package lesson2.serverside.service;


import lesson2.serverside.interfaces.AuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    private final int PORT = 8081;
    private List<ClientHandler> clients;
    private AuthService authService;
    private static Logger logger = LogManager.getLogger(MyServer.class.getSimpleName());


    public AuthService getAuthService() {

        return this.authService;
    }

    public MyServer() {


        try (ServerSocket server = new ServerSocket(PORT)) {



            authService = new BaseAuthService();
            clients = new ArrayList<>();


            while (true) {
                System.out.println(" сервер ожидает подключения ");
                logger.info("Server Wait");
                Socket socket = server.accept();
                System.out.println(socket.getInetAddress().getCanonicalHostName());
                System.out.println("Клиент подключился");
                logger.info("Client " + this + " connected");
                new ClientHandler(this, socket);

            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Сервер отключился");
            logger.info("Server disconected");

        } finally {
            if (authService != null) {
                authService.stop();
            }
        }
    }

    public synchronized void broadcastMessage(String message) {
        for (ClientHandler c : clients) {
            c.sendMessage(message);

        }
        logger.info("client send message: " + message);
    }

    public synchronized void subscribe(ClientHandler client) {
        clients.add(client);
    }

    public synchronized void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

    public boolean isNickBusy(String nick) {
        for (ClientHandler c : clients) {
            if (c.getName().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public synchronized String sendOnlyYou(String from, String message, String nick) {
        for (ClientHandler c : clients) {
            if (c.getName().equals(nick)) {
                c.sendMessage(from + " : " + message);
            }
            if (c.getName().equals(from)) {
                c.sendMessage(from + " : " + message);
            }


        }
        return message;
    }
}
