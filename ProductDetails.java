/**
 * Title:        ProductDetails.java
 * Description:  Class to hold model data of a product
 * Copyright:    Copyright (c) 2000
 * Company:      Big Byte Corp.
 * @author       Tirto Adji
 * @version 1.0
 */

public class ProductDetails {
    private String prodId = null;
    private String type = null;
    private String mfg = null;
    private String mfgPN = null;
    private float price = 0.0F;
    private String desc = null;
    private int weight = 0;

    public ProductDetails(String prodId, String type, String desc,
    							  String mfg, String mfgPN, float price, int weight) {
        this.prodId = prodId;
        this.type = type;
        this.desc = desc;
        this.mfg =  mfg;
        this.mfgPN = mfgPN;
        this.price = price;
        this.weight = weight;
    }

    public String getProdId() {
        return this.prodId;
    }

	 public String getType() {
        return this.type;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getMfg() {
        return this.mfg;
    }

    public String getMfgPN() {
        return this.mfgPN;
    }

    public float getPrice() {
	     return this.price;
	 }

	 public int getWeight() {
		  return this.weight;
 	 }

}

