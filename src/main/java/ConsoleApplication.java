import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public class ConsoleApplication {

    public static void main(String[] args) {

        LabelGenerator labelGenerator = new LabelGenerator();
        List<Map<String, Object>> pageVariables = getDummyData();
        try {
            labelGenerator.generate("./target/sscc-label-test.pdf", pageVariables);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Map<String, Object>> getDummyData() {
        List<Map<String, Object>> variables = new ArrayList<>();
        final String title = "A.Clouet Australia";

        PalletData palletData = new PalletData();
        palletData.setSscc("395560410000000018");
        palletData.setDescription("Coconut Cream 400ML");
        palletData.setGtin("29556041603946");
        palletData.setCount("160");
        palletData.setBestBeforeDate(Date.from(Instant.now()));
        palletData.setBatchNumber("451214");
        variables.add(getVariables(title, palletData));

//        palletData = new PalletData();
//        palletData.setSscc("393123450000000013");
//        palletData.setDescription("Baked Beans 12 x 410g");
//        palletData.setGtin("09312345000005");
//        palletData.setCount("999");
//        palletData.setBestBeforeDate(Date.from(Instant.now()));
//        palletData.setBatchNumber("123456");
//        variables.add(getVariables(title, palletData));
//
//        palletData = new PalletData();
//        palletData.setSscc("393123450000000013");
//        palletData.setDescription("Coconut Milk 12 x 410g");
//        palletData.setGtin("09312345000005");
//        palletData.setCount("1999");
//        palletData.setUseByDate(Date.from(Instant.now()));
//        variables.add(getVariables(title, palletData));
//
//        palletData = new PalletData();
//        palletData.setSscc("393123450000000013");
//        palletData.setDescription("Coconut Powder 12 x 410g");
//        palletData.setGtin("09312345000005");
//        palletData.setCount("9999");
//        palletData.setBestBeforeDate(Date.from(Instant.now()));
//        variables.add(getVariables(title, palletData));

        return variables;
    }

    static Map<String, Object> getVariables(String title, PalletData palletData) {

        Map<String, Object> variables = new HashMap<>();

        variables.put("title", title);
        variables.put("sscc", palletData.getSscc());
        variables.put("description", palletData.getDescription());
        variables.put("gtin", palletData.getGtin());
        variables.put("count", palletData.getCount());
        variables.put("batch", palletData.getBatchNumber() == null ? "" : palletData.getBatchNumber());
        addDateVariables(palletData, variables);
        variables.put("barcodeConcat", getConcatBarcode(palletData));
        variables.put("barcodeSscc", getSsccBarcode(palletData.getSscc()));

        return variables;
    }

    public static void addDateVariables(PalletData palletData, Map<String, Object> variables) {
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
        builder.append("02").append(palletData.getGtin()).append(' ');

        if (palletData.getBestBeforeDate() != null) {
            builder.append("15").append(dateFormat.format(palletData.getBestBeforeDate()));
        } else if(palletData.getUseByDate() != null) {
            builder.append("17").append(dateFormat.format(palletData.getUseByDate()));
        }

        builder.append(' ').append("37").append(palletData.getCount());

        if (palletData.getBatchNumber() != null) {
            builder.append(' ').append("10").append(palletData.getBatchNumber());
        }

        return builder.toString();
    }

    public static String getSsccBarcode(String sscc) {
        return String.format("00%s", sscc);
    }
}
