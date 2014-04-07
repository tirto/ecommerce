/**
 * Title:        ShoppingCart.java
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:      Big Byte Corp.
 * @author       Tirto Adji
 * @version 1.0
 */


import java.util.*;

public class ShoppingCart {
    Hashtable items = null;
    int numberOfItems = 0;
    String CCNum = null;
    String CCType = null;
    String expDate = null;
    String checkNum = null;
    String MICR = null;
    String typeOfCheck = null;
    String DLnum = null;
    String stateOfDL = null;

    public ShoppingCart() {
        items = new Hashtable();
        CCNum = "";
        CCType = "";
        expDate = "";
        checkNum = "";
        MICR = "";
        typeOfCheck = "";
        DLnum = "";
        stateOfDL = "";
    }

    public void add(String prodId, ProductDetails prod) {
        if(items.containsKey(prodId)) {
            ShoppingCartItem scitem = (ShoppingCartItem) items.get(prodId);
            scitem.incrementQuantity();
        } else {
            ShoppingCartItem newItem = new ShoppingCartItem(prod);
            items.put(prodId, newItem);
            numberOfItems++;
        }


    }

    public void updateQty(String prodId, int updatedQty) {
		 if(items.containsKey(prodId)) {
		      ShoppingCartItem scitem = (ShoppingCartItem) items.get(prodId);
		      scitem.setQuantity(updatedQty);
	    }
 	 }

    public void remove(String prodId) {
		 if(items.containsKey(prodId)) {
			 ShoppingCartItem scitem = (ShoppingCartItem) items.get(prodId);
			 items.remove(prodId);
			 numberOfItems--;
		 }
	 }

    public Enumeration getItems() {
        return items.elements();
    }

    protected void finalize() throws Throwable {
        items.clear();
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numItems) {
		  this.numberOfItems = numItems;
    }

    public String getCCNum() {
		 return CCNum;
    }

    public void setCCNum(String num) {
		 this.CCNum = num;
    }

	 public String getCCType() {
		 return CCType;
    }

    public void setCCType(String type) {
		 this.CCType = type;
    }

    public String getExpDate() {
		 return expDate;
    }

    public void setExpDate(String date) {
		 this.expDate = date;
    }

    public String getCheckNum() {
		 return checkNum;
	 }

	 public void setCheckNum(String num) {
		 this.checkNum = num;
    }

    public String getMICR() {
		 return MICR;
	 }

	 public void setMICR(String MICR) {
		 this.MICR = MICR;
	 }

    public String getTypeOfCheck() {
		 return typeOfCheck;
	 }

	 public void setTypeOfCheck(String type) {
		 this.typeOfCheck = type;
	 }

	 public String getDLnum() {
	    return DLnum;
	 }

    public void setDLnum(String num) {
		 this.DLnum = num;
	 }

	 public String getStateOfDL() {
		 return stateOfDL;
	 }

	 public void setStateOfDL(String state) {
		 this.stateOfDL = state;
	 }

    public void clear() {
        items.clear();
        numberOfItems = 0;
        CCNum = "";
        expDate = "";
        checkNum = "";
        MICR = "";
        typeOfCheck = "";
        DLnum = "";
        stateOfDL = "";
    }
}

