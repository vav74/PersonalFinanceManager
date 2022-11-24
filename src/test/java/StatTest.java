import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class StatTest {
    static Stat stat;

    @BeforeAll
    static void setUpApp() {
        {
            try {
                stat = new Stat();
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    @DisplayName("Тест maxCategoryUpd")
    void maxCategoryUpdTest(Purchase purchase, MaxCat expected) throws IOException, CsvException {
        MaxCat result = stat.maxCategoryUpd(purchase);
        Assertions.assertEquals(result, expected);
    }

    private static Stream<Arguments> getArguments() {
        return Stream.of(
                Arguments.of(new Purchase("булка", 10), new MaxCat(new MaxCategory("еда", 10))),
                Arguments.of(new Purchase("колбаса", 20), new MaxCat(new MaxCategory("еда", 30))),
                Arguments.of(new Purchase("сухарики", 30), new MaxCat(new MaxCategory("еда", 60))),
                Arguments.of(new Purchase("курица", 40), new MaxCat(new MaxCategory("еда", 100))),
                Arguments.of(new Purchase("тапки", 50), new MaxCat(new MaxCategory("еда", 100))),
                Arguments.of(new Purchase("шапка", 60), new MaxCat(new MaxCategory("одежда", 110))),
                Arguments.of(new Purchase("куртка", 70), new MaxCat(new MaxCategory("одежда", 180))),
                Arguments.of(new Purchase("сапоги", 80), new MaxCat(new MaxCategory("одежда", 260))),
                Arguments.of(new Purchase("ведро", 90), new MaxCat(new MaxCategory("одежда", 260))),
                Arguments.of(new Purchase("мыло", 100), new MaxCat(new MaxCategory("одежда", 260))),
                Arguments.of(new Purchase("мочалка", 110), new MaxCat(new MaxCategory("быт", 300))),
                Arguments.of(new Purchase("швабра", 120), new MaxCat(new MaxCategory("быт", 420))),
                Arguments.of(new Purchase("акции", 130), new MaxCat(new MaxCategory("быт", 420))),
                Arguments.of(new Purchase("облигации", 140), new MaxCat(new MaxCategory("быт", 420))),
                Arguments.of(new Purchase("золото", 151), new MaxCat(new MaxCategory("финансы", 421))),
                Arguments.of(new Purchase("монетки", 160), new MaxCat(new MaxCategory("финансы", 581))),
                Arguments.of(new Purchase("текила", 170), new MaxCat(new MaxCategory("финансы", 581))),
                Arguments.of(new Purchase("пиво", 180), new MaxCat(new MaxCategory("финансы", 581))),
                Arguments.of(new Purchase("кола", 190), new MaxCat(new MaxCategory("финансы", 581))),
                Arguments.of(new Purchase("минералка", 200), new MaxCat(new MaxCategory("питье", 740))),
                Arguments.of(new Purchase("хагиваги", 210), new MaxCat(new MaxCategory("питье", 740))),
                Arguments.of(new Purchase("мячик", 220), new MaxCat(new MaxCategory("питье", 740))),
                Arguments.of(new Purchase("кукла", 230), new MaxCat(new MaxCategory("питье", 740))),
                Arguments.of(new Purchase("лего", 240), new MaxCat(new MaxCategory("игрушка", 900))),
                Arguments.of(new Purchase("микрофон", 250), new MaxCat(new MaxCategory("игрушка", 900))),
                Arguments.of(new Purchase("телефон", 260), new MaxCat(new MaxCategory("игрушка", 900))),
                Arguments.of(new Purchase("подшипник", 270), new MaxCat(new MaxCategory("игрушка", 900))),
                Arguments.of(new Purchase("удочка", 280), new MaxCat(new MaxCategory("другое", 1060))));
    }
}