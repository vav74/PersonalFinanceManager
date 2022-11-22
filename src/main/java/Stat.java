import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

// логика подсчёта максимальных категорий
public class Stat {
    Map<String, String> mapFromTsvFile;
    List<Purchase> purchaseList;

    public Stat() throws IOException, CsvException {
        this.mapFromTsvFile = new HashMap<>();
        this.purchaseList = new ArrayList<>();
        readFileTSV();
    }

    public void readFileTSV() throws IOException, CsvException {
        CSVParser parser = new CSVParserBuilder().withSeparator('\t').build();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader("categories.tsv")).withCSVParser(parser).build()) {
            List<String[]> allData = reader.readAll();
            allData.forEach(v -> mapFromTsvFile.put(v[0], v[1]));
        }
    }

    public MaxCat maxCategoryUpd(Purchase purchase) throws IOException, CsvException {
        purchase.setCat(mapFromTsvFile.getOrDefault(purchase.getTitle(), "другое"));
        purchaseList.add(purchase);

        Map.Entry<String, Integer> maxCategoryEntry = purchaseList.stream()
                .collect(Collectors.groupingBy(Purchase::getCat, Collectors.summingInt(Purchase::getSum)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
        assert maxCategoryEntry != null;
        return new MaxCat(new MaxCategory(maxCategoryEntry.getKey(), maxCategoryEntry.getValue()));
    }
}