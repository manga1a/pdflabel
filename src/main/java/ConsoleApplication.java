import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsoleApplication {

    public static void main(String[] args) {

        LabelGenerator labelGenerator = new LabelGenerator();
        List<String> pageValues = new ArrayList<>(Arrays.asList("Page 1", "Page 2", "Page 3"));
        try {
            labelGenerator.generate("./target/sscc-label-test.pdf", pageValues);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<Map<String, String>> getDummyData() {
        final String title = "A.Clouet Australia";

        PalletData palletData = new PalletData();
        palletData.setSscc("393123450000000013");
        palletData.setDescription("Baked Beans 12 x 410g");
        palletData.setGtin("09312345000005");

        return null;
    }

    static Map<String, String> getVariables(String title, PalletData palletData) {

        Map<String, String> variables = new HashMap<>();

        variables.put("title", title);
        variables.put("sscc", palletData.getSscc());
        variables.put("description", palletData.getDescription());
        variables.put("gtin", palletData.getGtin());
        variables.put("count", palletData.getCount());
        variables.put("batch", palletData.getBatchNumber() == null ? "" : palletData.getBatchNumber());
        addDateVariables(palletData, variables);
        variables.put("barcodeConcat", getConcatBarcode(palletData));

        return variables;
    }

    public static void addDateVariables(PalletData palletData, Map<String, String> variables) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        String dateLabel = null;
        Date dateValue = null;
        if (palletData.getBestBeforeDate() != null) {
            dateLabel = "BEST BEFORE";
            dateValue = palletData.getBestBeforeDate();
        } else if (palletData.getUseByDate() != null) {
            dateLabel = "USE BY";
            dateValue = palletData.getUseByDate();
        }
        if (dateLabel != null && dateValue != null) {
            variables.put("dateLabel", dateLabel);
            variables.put("date", dateFormat.format(dateValue));
        }
    }

    public static String getConcatBarcode(PalletData palletData)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyMMdd");

        StringBuilder builder = new StringBuilder();
        builder.append("02").append(palletData.getGtin())
                .append("37").append(palletData.getCount())
                .append(" ");
        if (palletData.getBestBeforeDate() != null) {
            builder.append("15").append(dateFormat.format(palletData.getBestBeforeDate()));
        } else if(palletData.getUseByDate() != null) {
            builder.append("17").append(dateFormat.format(palletData.getUseByDate()));
        }

        if (palletData.getBatchNumber() != null) {
            builder.append("10").append(palletData.getBatchNumber());
        }

        return builder.toString();
    }

    public static String getSsccBarcode(String sscc) {
        return String.format("00%s", sscc);
    }
}
