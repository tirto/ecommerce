/**
 * Title:        ShoppingCartItem.java
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:      Big Byte Corp.
 * @author       Tirto Adji
 * @version 1.0
 *
 */

public class ShoppingCartItem {
    Object item;
    int quantity;

    public ShoppingCartItem(Object anItem) {
        item = anItem;
        quantity = 1;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        quantity--;
    }

    public Object getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int updatedQty) {
		 this.quantity = updatedQty;
 	 }
}









