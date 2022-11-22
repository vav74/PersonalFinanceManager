import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException, CsvException {
        Stat stat = new Stat();
        try (ServerSocket serverSocket = new ServerSocket(8989)) { // стартуем сервер один(!) раз
            while (true) { // в цикле(!) принимаем подключения
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
                ) {
                    Purchase purchase = new ObjectMapper().readValue(in.readLine(), Purchase.class);
                    MaxCat maxCat = stat.maxCategoryUpd(purchase);
                    String maxCatJson = new ObjectMapper().writeValueAsString(maxCat);
                    out.println(maxCatJson);
                }
            }
        } catch (IOException | CsvException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}