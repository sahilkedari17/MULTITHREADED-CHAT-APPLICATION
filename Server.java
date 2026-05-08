import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    // List to store all connected clients
    static Vector<ClientHandler> clients = new Vector<>();

    public static void main(String[] args) {
        try {
            // Server runs on port 1234
            ServerSocket serverSocket = new ServerSocket(1234);

            System.out.println("Server started...");
            System.out.println("Waiting for clients...");

            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("New client connected!");

                // Create input and output streams
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                // Ask client name
                dos.writeUTF("Enter your name:");
                String name = dis.readUTF();

                // Create handler object
                ClientHandler client = new ClientHandler(socket, name, dis, dos);

                // Add client to list
                clients.add(client);

                // Start thread
                Thread t = new Thread(client);
                t.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}