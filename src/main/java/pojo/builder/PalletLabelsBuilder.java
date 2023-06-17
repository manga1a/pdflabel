package pojo.builder;

import pojo.Pallet;
import pojo.PalletLabels;

import java.util.List;

public class PalletLabelsBuilder {
    private String header;
    private List<Pallet> detail;

    public PalletLabelsBuilder header(String header) {
        this.header = header;
        return this;
    }

    public PalletLabelsBuilder detail(List<Pallet> detail) {
        this.detail = detail;
        return this;
    }

    public PalletLabels build() {
        return new PalletLabels(header, detail);
    }
}
