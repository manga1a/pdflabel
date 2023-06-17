package pojo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public record PalletLabels (String header, @JacksonXmlElementWrapper(localName = "detail") @JacksonXmlProperty(localName = "pallet") List<Pallet> detail) {
}
