
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket;
        Socket socket = null;
        int port = 1299;

        serverSocket = new ServerSocket(port);
        System.out.println("Opened socket " + port);
        while (true) {
            // keeps listening for new clients, one at a time
            socket = serverSocket.accept(); // waits for client here
            InputStream stream = socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            try {
                // read the first line to get the request method, URI and HTTP version
                String line = in.readLine();
                System.out.println("----------REQUEST START---------");
                System.out.println(line);
                // read only headers
                line = in.readLine();
                while (line != null && line.trim().length() > 0) {
                    int index = line.indexOf(": ");
                    if (index > 0) {
                        System.out.println(line);
                    } else {
                        break;
                    }
                    line = in.readLine();
                }
                System.out.println("----------REQUEST END---------\n\n");
            } catch (IOException e) {
                System.out.println("Error reading");
                System.exit(1);
            }

            BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
            PrintWriter writer = new PrintWriter(out, true);  // char output to the client

            // every response will always have the status-line, date, and server name
            writer.println("HTTP/1.1 200 OK");
            writer.println("Server: TEST");
            writer.println("Connection: close");
            writer.println("Content-type: text/html");
            writer.println("");

            // Body of our response
            writer.println("<h1>Hello World</h1>");

            socket.close();
        }
    }
}