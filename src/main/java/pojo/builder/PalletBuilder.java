package pojo.builder;

import pojo.Dtm;
import pojo.LineItem;
import pojo.Pallet;

import java.util.Date;

public class PalletBuilder {
    private String sscc;
    private String gtin;
    private String description;
    private String quantity;
    private String batchNo;
    private Dtm dtm;

    public PalletBuilder sscc(String sscc) {
        this.sscc = sscc;
        return this;
    }

    public PalletBuilder gtin(String gtin) {
        this.gtin = gtin;
        return this;
    }

    public PalletBuilder description(String description) {
        this.description = description;
        return this;
    }

    public PalletBuilder quantity(String qty) {
        this.quantity = qty;
        return this;
    }

    public PalletBuilder bestBeforeDate(Date date) {
        this.dtm = new Dtm("Best Before", "15", date);
        return this;
    }

    public PalletBuilder useByDate(Date date) {
        this.dtm = new Dtm("Use By", "17", date);
        return this;
    }

    public PalletBuilder batchNo(String batchNo) {
        this.batchNo = batchNo;
        return this;
    }

    public Pallet build() {
        LineItem lineItem = new LineItem(gtin, description, quantity, dtm, batchNo);
        return new Pallet(sscc, lineItem);
    }
}
