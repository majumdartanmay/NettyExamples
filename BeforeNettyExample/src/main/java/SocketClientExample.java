import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketClientExample {
    public static final int PORT = ServerSocketExample.PORT;
    public static void main(final String... arg0) throws IOException  {
        final String serverAddress = "localhost";

        try {
            Socket socket = new Socket(serverAddress, PORT);
            System.out.println("Connected to server");

            Scanner scanner = new Scanner(System.in);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Read input from the user and send it to the server
            while (true) {
                System.out.print("Enter a message (Type 'exit' to quit): ");
                String message = scanner.nextLine();

                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }

                writer.println(message);

                // Receive and print the server's response
                String response = new Scanner(socket.getInputStream()).nextLine();
                System.out.println("Server response: " + response);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
