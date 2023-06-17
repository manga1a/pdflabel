import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import pojo.PalletLabels;
import pojo.builder.PalletBuilder;
import pojo.builder.PalletLabelsBuilder;

import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.List;

public class ConsoleApplication {

    public static void main(String[] args) {

        try (ByteArrayInputStream dataStream = new ByteArrayInputStream(getDummyData())) {
            LabelGenerator labelGenerator = new LabelGenerator();
            labelGenerator.generate("./target/sscc-label-test.pdf", dataStream);
        } catch (Exception e) {
            System.err.print(e);
        }
    }

    private static byte[] getDummyData() throws JsonProcessingException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JUNE, 17);

        PalletBuilder palletBuilder = new PalletBuilder();

        PalletLabels palletLabels = new PalletLabelsBuilder()
                .header("The Goods Supplier")
                .detail(List.of(
                        palletBuilder
                                .sscc("395560410000000018")
                                .gtin("29556041603946")
                                .description("Coconut Cream 400ML")
                                .quantity("160")
                                .bestBeforeDate(calendar.getTime())
                                .batchNo("451214").build(),
                        palletBuilder
                                .sscc("393123450000000013")
                                .gtin("09312345000005")
                                .description("Baked Beans 12 x 410g")
                                .quantity("999")
                                .useByDate(calendar.getTime())
                                .batchNo("12345")
                                .build(),
                        palletBuilder
                                .sscc("393123450000000013")
                                .gtin("09312345000005")
                                .description("Coconut Milk 12 x 410g")
                                .quantity("1999")
                                .useByDate(calendar.getTime())
                                .batchNo(null)
                                .build(),
                        palletBuilder
                                .sscc("393123450000000013")
                                .gtin("09312345000005")
                                .description("Coconut Powder 12 x 410g a loooooong text to trim")
                                .quantity("9999")
                                .bestBeforeDate(calendar.getTime())
                                .build()
                ))
                .build();

        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsBytes(palletLabels);
    }
}
