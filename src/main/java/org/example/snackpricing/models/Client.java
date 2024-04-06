package org.example.snackpricing.models;
import java.math.BigDecimal;

public class Client {
    int id;
    String name;
    BigDecimal basicDiscount;
    BigDecimal additionalDiscountLevel1;
    BigDecimal additionalDiscountLevel2;

    public Client() {
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

    public BigDecimal getBasicDiscount() {
        return basicDiscount;
    }

    public void setBasicDiscount(BigDecimal basicDiscount) {
        this.basicDiscount = basicDiscount;
    }

    public BigDecimal getAdditionalDiscountLevel1() {
        return additionalDiscountLevel1;
    }

    public void setAdditionalDiscountLevel1(BigDecimal additionalDiscountLevel1) {
        this.additionalDiscountLevel1 = additionalDiscountLevel1;
    }

    public BigDecimal getAdditionalDiscountLevel2() {
        return additionalDiscountLevel2;
    }

    public void setAdditionalDiscountLevel2(BigDecimal additionalDiscountLevel2) {
        this.additionalDiscountLevel2 = additionalDiscountLevel2;
    }
}

