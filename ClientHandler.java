import java.io.*;
import java.net.*;

class ClientHandler implements Runnable {

    private Socket socket;
    private String name;
    private DataInputStream dis;
    private DataOutputStream dos;

    public ClientHandler(Socket socket, String name,
                         DataInputStream dis,
                         DataOutputStream dos) {
        this.socket = socket;
        this.name = name;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {

        try {
            while (true) {

                String message = dis.readUTF();

                if (message.equalsIgnoreCase("exit")) {
                    System.out.println(name + " disconnected.");
                    socket.close();
                    break;
                }

                String fullMessage = name + ": " + message;

                System.out.println(fullMessage);

                // Send message to ALL clients including sender
                for (ClientHandler client : Server.clients) {
                    client.dos.writeUTF(fullMessage);
                }
            }

        } catch (IOException e) {
            System.out.println(name + " disconnected.");
        }
    }
}