package org.example.snackpricing.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {
    int id;
    String name;
    BigDecimal unitCost;
    BigDecimal markup;
    String promotion;
    boolean isMarkupPercentage;

    public Product() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getMarkup() {
        return markup;
    }

    public void setMarkup(BigDecimal markup) {
        this.markup = markup;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public boolean isMarkupPercentage() {
        return isMarkupPercentage;
    }

    public void setMarkupPercentage(boolean markupPercentage) {
        isMarkupPercentage = markupPercentage;
    }
}
