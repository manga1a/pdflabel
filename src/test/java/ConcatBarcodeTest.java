import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

public class ConcatBarcodeTest {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Test
    public void testBestBeforeWithBatch() throws ParseException {
        PalletData palletData = new PalletData();
        palletData.setGtin("09312345000005");
        palletData.setCount("20");
        palletData.setBestBeforeDate(dateFormat.parse("2005-12-01"));
        palletData.setBatchNumber("246813");

        String actual = ConsoleApplication.getConcatBarcode(palletData);
        assertEquals("02093123450000053720 1505120110246813", actual);
    }
}
