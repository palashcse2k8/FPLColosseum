package com.infotech.fplcolosseum.features.homepage.models.myteam;

public class TransferUpdate {
    private float element_in;
    private float element_out;
    private String purchase_price;
    private String selling_price;


    // Getter Methods

    public float getElement_in() {
        return element_in;
    }

    public float getElement_out() {
        return element_out;
    }

    public String getPurchase_price() {
        return purchase_price;
    }

    public String getSelling_price() {
        return selling_price;
    }

    // Setter Methods

    public void setElement_in(float element_in) {
        this.element_in = element_in;
    }

    public void setElement_out(float element_out) {
        this.element_out = element_out;
    }

    public void setPurchase_price(String purchase_price) {
        this.purchase_price = purchase_price;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }
}
