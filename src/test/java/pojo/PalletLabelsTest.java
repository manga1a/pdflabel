package pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PalletLabelsTest {

    @Test
    public void foo() throws JsonProcessingException {
        PalletLabels palletLabels = new PalletLabels("Some header");
        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(palletLabels);
        assertNotNull(xml);
    }
}
