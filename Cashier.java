/**
 * Title:        Cashier.java
 * Description:  Class to process total order amount calculation
 * Copyright:    Copyright (c) 2000
 * Company:      Big Byte Corp.
 * @author       Tirto Adji
 * @version 1.0
 */

import java.io.*;
import java.util.*;
import java.text.*;

public class Cashier {

    private static final double SalesTaxRate = 0.0825;

    ShoppingCart cart;

    public Cashier(ShoppingCart cart) {
        this.cart = cart;
    }

    public double getAmount() {
        double amount = 0.0;

        for(Enumeration e = cart.getItems(); e.hasMoreElements(); ) {
            ShoppingCartItem item = (ShoppingCartItem) e.nextElement();
            ProductDetails prodDetails = (ProductDetails) item.getItem();

            amount += item.getQuantity() * prodDetails.getPrice();
        }
        return roundOff(amount);
    }

    public int getTotalWeight() {
        int totalWeight = 0;

        for(Enumeration e = cart.getItems(); e.hasMoreElements(); ) {
            ShoppingCartItem item = (ShoppingCartItem) e.nextElement();
            ProductDetails prodDetails = (ProductDetails) item.getItem();

            totalWeight += item.getQuantity() * prodDetails.getWeight();
        }
        return totalWeight;
    }

    public double getTax() {
        return roundOff(getAmount() * SalesTaxRate);
    }

    public double getTotal() {
        return roundOff(getAmount() + getTax());
    }

    private double roundOff(double x) {
        long val = Math.round(x*100); // cents
        return val/100.0;
    }

    public static String format(double d) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(d);
    }


    public static double getShipCharges(String shipMethod, int weight) {
		  double charges = 0;
		  if (shipMethod.equals("UPS GROUND")) {
		  		charges = 10;
	     }
	     else if (shipMethod.equals("UPS BLUE")) {
			   charges = 15;
	     }
	     else if (shipMethod.equals("UPS RED")) {
			   charges = 20;
	     }

	     return charges;
    }
}

