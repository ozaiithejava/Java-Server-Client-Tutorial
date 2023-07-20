package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        listenerServer();
    }

    public static void listenerServer() throws IOException {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server started. Listening on port 12345...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected from: " + clientSocket.getInetAddress());

            // Client'in HWID'sini alalım
            String clientHwid = getClientHwid(clientSocket);
            System.out.println("Client HWID: " + clientHwid);

            // Client'tan gelen verileri okuyalım ve ekrana yazdıralım
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                System.out.println("Client: " + clientMessage);
            }

            // Client'a hoşgeldin mesajı gönderelim
            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println("Merhaba, bağlantı başarılı!");

            // İşlem tamamlandıktan sonra soketleri kapat
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    private static String getClientHwid(Socket clientSocket) throws IOException {
        InputStream inputStream = clientSocket.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        return in.readLine(); // Client tarafından gönderilen HWID'yi alıyoruz
    }
}
