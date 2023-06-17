package pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import pojo.builder.PalletBuilder;
import pojo.builder.PalletLabelsBuilder;

import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PalletLabelsTest {

    @Test
    public void givenPojo_whenSerialized_returnsXml() throws JsonProcessingException {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.JULY, 2);

        PalletBuilder palletBuilder = new PalletBuilder();

        PalletLabels palletLabels = new PalletLabelsBuilder()
                .header("The Goods Supplier")
                .detail(List.of(
                        palletBuilder
                                .sscc("00999999999000000245")
                                .gtin("19999999999762")
                                .description("ACME DUNE HARVESTER OIL")
                                .quantity("48")
                                .bestBeforeDate(calendar.getTime())
                                .batchNo("212301").build(),
                        palletBuilder
                                .sscc("00999999999000000252")
                                .gtin("19999999993678")
                                .description("ACME GOLD STANDARD 750ML XXX")
                                .quantity("128")
                                .useByDate(calendar.getTime())
                                .batchNo("")
                                .build(),
                        palletBuilder
                                .sscc("00999999999000000269")
                                .gtin("19999999992763")
                                .description("ACME GOLDEN PEEL OIL 750ML")
                                .quantity("145")
                                .bestBeforeDate(calendar.getTime())
                                .batchNo(null)
                                .build()
                ))
                .build();

        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(palletLabels);
        assertNotNull(xml);
    }
}
