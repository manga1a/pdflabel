import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DateVariablesTest {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void testBestBeforeDate() throws ParseException {
        PalletData palletData = new PalletData();
        palletData.setBestBeforeDate(dateFormat.parse("2005-12-01"));
        Map<String, Object> variables = new HashMap<>();

        ConsoleApplication.addDateVariables(palletData, variables);

        assertEquals("BEST BEFORE", variables.get("dateLabel"));
        assertEquals("01.12.05", variables.get("date"));
    }

    @Test
    public void testUseByDate() throws ParseException {
        PalletData palletData = new PalletData();
        palletData.setUseByDate(dateFormat.parse("2022-10-08"));
        Map<String, Object> variables = new HashMap<>();

        ConsoleApplication.addDateVariables(palletData, variables);

        assertEquals("USE BY", variables.get("dateLabel"));
        assertEquals("08.10.22", variables.get("date"));
    }
}
