import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerSocketExample {

    public static final int PORT = 8888;

    public static void main(final String... arg0) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final Scanner scanner;
        private final PrintWriter writer;

        public ClientHandler(Socket socket) throws IOException {
            this.clientSocket = socket;
            this.scanner = new Scanner(clientSocket.getInputStream());
            this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String message = scanner.nextLine();
                    System.out.println("Received from client: " + message);

                    // Echo the message back to the client
                    writer.println("Server: " + message);
                }
            } catch (Exception e) {
                System.out.println("Client disconnected: " + clientSocket.getInetAddress().getHostAddress());
            }
        }
    }
}
