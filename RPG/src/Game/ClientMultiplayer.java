package Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by piano_000 on 10/7/2015.
 */

/* TODO: Client side tasks:
Connect with Server
Send Party to Server
Wait for response for party update from server
Take turn
Send response to server
Wait for response fro party update from server

 */
public class ClientMultiplayer {

    protected String hostname;
    protected int port;

    public ClientMultiplayer(String host, int port) {
        this.hostname = host;
        this.port = port;
        startClient();
    }

    public void startClient() {
        try {
            Socket socket = new Socket(hostname, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

            String response;
            while((response = stdin.readLine()) != null) {
                out.println(response);
                System.out.println("Sent: " + in.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("Unrecognized host: " + hostname);
            //TODO: Handle the problem
        } catch (IOException e) {
            System.err.println("Failed to establish connection to " + hostname);
            //TODO: Handle the problem
        }
    }

}
