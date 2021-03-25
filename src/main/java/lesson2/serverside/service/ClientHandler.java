package lesson2.serverside.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler {

    private MyServer myServer;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String name;
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB = "jdbc:mysql://localhost/newbase";
    static final String USER = "root";
    static final String PASSWORD = "root";
    ExecutorService executorService;

    public ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
            executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Thread(() -> {
                try {
                    authentication();
                    readMessage();
                    //changeNick();


                } catch (IOException ignored) {

                } catch (ArrayIndexOutOfBoundsException ignored) {

                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    closeConnection();
                }
            }));


        } catch (IOException e) {
            closeConnection();
            throw new RuntimeException("Problem with ClientHandler");
        }
    }



    public void changeNick(String newnick) throws IOException {
        while (true) {

            System.out.println("change");

                try {
                    Class.forName(DRIVER);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try (Connection conn = DriverManager.getConnection(DB, USER, PASSWORD)) {


                    PreparedStatement preparedStatement = null;
                    preparedStatement = conn.prepareStatement("UPDATE users SET nick = ? WHERE nick = ?");
                    preparedStatement.setString(1, newnick);
                    preparedStatement.setString(2, name);
                    preparedStatement.executeUpdate();
                    System.out.println("Обновление завершено");
                    preparedStatement.close();
                    conn.close();

                    return;

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }



    public void authentication() throws IOException {
        while (true) {
            String str = dis.readUTF();
            System.out.println("auth");
            if (str.startsWith("/auth")) {
                String[] arr = str.split("\\s");
                String nick = myServer.getAuthService().getNickByLoginAndPassword(arr[1], arr[2]);

                if (nick != null) {
                    if (!myServer.isNickBusy(nick)) {
                        sendMessage("/authOK " + nick);
                        name = nick;
                        myServer.broadcastMessage("Hello " + name);
                        myServer.subscribe(this);

                        break;
                    } else {
                        sendMessage("Nick is busy");
                    }

                } else {
                    sendMessage("Wrong login and password");
                }
            }
        }
    }

    public void sendMessage(String message) {
        try {

            dos.writeUTF(message);


        } catch (IOException ignored) {

        }

    }


    public void readMessage() throws IOException {
        while (true) {
            String messageFromClient = dis.readUTF();
            System.out.println("read");
            if (messageFromClient.startsWith("/change")) {
                String[] arr = messageFromClient.split("\\s");
                String newnick = arr[1];
                changeNick(newnick);

            }
            System.out.println(name + " send message " + messageFromClient);
            if (messageFromClient.trim().startsWith("/")) {
                if (messageFromClient.startsWith("/w")) {
                    String[] arr = messageFromClient.split(" ");

                    myServer.sendOnlyYou(name, messageFromClient, arr[1]);
                }
                if (messageFromClient == "/end") {
                    return;
                }
            } else {
                myServer.broadcastMessage(name + " : " + messageFromClient);
            }
        }
    }

    public void closeConnection() {
        myServer.unsubscribe(this);
        myServer.broadcastMessage(name + " Leave Chat");
        try {
            dis.close();
            dos.close();
            socket.close();
            executorService.shutdown();
        } catch (IOException ignored) {

        }


    }

    public String getName() {
        return name;
    }

}
