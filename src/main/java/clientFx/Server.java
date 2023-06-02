package clientFx;
import java.io.*;
import java.lang.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws IOException {
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        ServerSocket ss = new ServerSocket(4999);
        Socket s = ss.accept();

        System.out.println("client connected");

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);

    }
}

