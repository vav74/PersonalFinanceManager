import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

// логика подсчёта максимальных категорий
public class Stat {
    Map<String, String> mapFromTsvFile;
    List<Purchase> purchaseList;
    AppendableObjectOutputStream oos;
    File binFile;
    boolean append;

    public Stat() throws IOException, CsvException, ClassNotFoundException {
        this.mapFromTsvFile = new HashMap<>();
        this.purchaseList = new ArrayList<>();
        readFileTSV();
        binFile = new File("data.bin");
        if (binFile.exists()) {
            deserializePurchases();
            append = true;
        }
        FileOutputStream fos = new FileOutputStream(binFile, append);
        oos = new AppendableObjectOutputStream(fos, append);
    }

    public void deserializePurchases() throws ClassNotFoundException, IOException {
        try (FileInputStream fis = new FileInputStream(binFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Purchase p = null;
            while (true) {
                try {
                    p = (Purchase) ois.readObject();
                    purchaseList.add(p);
                } catch (EOFException e) {
                    System.out.println("End of file reached: " + purchaseList.size());
                    break;
                } catch (ClassCastException e) {
                    System.out.println(p.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void readFileTSV() throws IOException, CsvException {
        CSVParser parser = new CSVParserBuilder().withSeparator('\t').build();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader("categories.tsv")).withCSVParser(parser).build()) {
            List<String[]> allData = reader.readAll();
            allData.forEach(v -> mapFromTsvFile.put(v[0], v[1]));
        }
    }

    public MaxCat maxCategoryUpd(String request) throws IOException, CsvException {
        System.out.println(request);
        Purchase purchase = new ObjectMapper().readValue(request, Purchase.class);
        purchase.setCat(mapFromTsvFile.getOrDefault(purchase.getTitle(), "другое"));
        purchaseList.add(purchase);
        System.out.println(purchase);//----------
        oos.writeObject(purchase);
        //oos.flush();

        Map.Entry<String, Integer> maxCategoryEntry = purchaseList.stream()
                .collect(Collectors.groupingBy(Purchase::getCat, Collectors.summingInt(Purchase::getSum)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
        assert maxCategoryEntry != null;
        return new MaxCat(new MaxCategory(maxCategoryEntry.getKey(), maxCategoryEntry.getValue()));
    }
}