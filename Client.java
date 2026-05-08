import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {

            // Connect to server
            Socket socket = new Socket("localhost", 1234);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            Scanner scanner = new Scanner(System.in);

            // Receive name request
            System.out.println(dis.readUTF());

            // Send name
            String name = scanner.nextLine();
            dos.writeUTF(name);

            // Thread to receive messages
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        String msg = dis.readUTF();
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            });

            receiveThread.start();

            // Send messages
            while (true) {
                String msg = scanner.nextLine();
                dos.writeUTF(msg);

                if (msg.equalsIgnoreCase("exit")) {
                    socket.close();
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}