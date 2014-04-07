/**
 * Title:        ShoppingCartServlet.java
 * Description:  Servlet that holds and process data in the shopping cart
 * Copyright:    Copyright (c) 2000
 * Company:      Big Byte Corp.
 * @author       Tirto Adji
 * @version 1.0
 */



import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.io.*;
import java.util.*;

public class ShoppingCartServlet extends HttpServlet {

    String[] prodIdArray = null;

    public void doPost(HttpServletRequest req, HttpServletResponse res)
	         throws ServletException, IOException  {
		  doGet(req, res);
    }

    public void doGet (HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {

        HttpSession session = req.getSession(true);
        ShoppingCart cart = (ShoppingCart)session.getAttribute(session.getId());
System.out.println("ShoppingCartServlet");
System.out.println("cart = "+cart);
System.out.println("sessionId = "+session.getId());
        if (cart==null) {
			  cart= new ShoppingCart();
			  session.setAttribute(session.getId(),cart);
	  	  }


        res.setContentType("text/html");
        ServletOutputStream out = res.getOutputStream();
        out.println("<html>");
        out.println("<head><title>Shopping Cart Servlet</title></head>");
        out.println("<body>");
        out.println("<b><font face='verdana' size='-1' color= 'navy'>"+
		  	    "Repair order list:</b><br></font>");


        String action = "View";
        try {
			  action = req.getParameter("Action").trim();
	     }
	     catch (NullPointerException npe) {
			  action = "Update quantity";
	     }


        if (action.equals("Clear list")) {
			  cart.clear();
			  out.println("<font face='verdana' size='-1'><br>You just cleared your repair order list!</font><br>");
	  	  }


        else if (action.equals("Update quantity")) {
	  	  	  prodIdArray = req.getParameterValues("prodId");

	  	  	  if (prodIdArray == null) {
				  out.println("<font face='verdana' size='-1'><br>Please select the product that you want to update.</font><br>");
		  	  }
	  	  	  else {
				  for (int i = 0; i < prodIdArray.length; i++) {
					  String prodId = prodIdArray[i];
					  int updatedQty = Integer.parseInt(req.getParameter(prodId+"qty").trim());
					  if (updatedQty==0) {
						  cart.remove(prodId);
					  }
					  else {
						  cart.updateQty(prodId, updatedQty);
					  }
				  }
			  }

	  	  }

        else if (action.equals("Add to repair order list")) {
			  prodIdArray = req.getParameterValues("prodId");
			  if (prodIdArray!=null) {
				  for (int i = 0; i < prodIdArray.length; i++) {
					  String prodId = prodIdArray[i];
					  String type = req.getParameter(prodId+"type").trim();
					  String desc = req.getParameter(prodId+"desc").trim();
					  String mfg = req.getParameter(prodId+"mfg").trim();
					  String mfgPN = req.getParameter(prodId+"mfgPN").trim();
					  float price = Float.parseFloat(req.getParameter(prodId+"price").trim());
					  int weight = Integer.parseInt(req.getParameter(prodId+"weight").trim());
					  ProductDetails aProduct = new ProductDetails(prodId, type, desc, mfg, mfgPN, price, weight);
					  cart.add(prodId, aProduct);
				  }
			  }
	  	  }

	  	  else if (action.equals("View")) {
	     		//just show cart
	     }

		  Cashier cashier = new Cashier(cart);
		  int num = cart.getNumberOfItems();
		  if(num > 0) {
		  		out.println("<font face='verdana' size='-1'><br>You have "+ num + (num==1? " item" : " items") +
		  						" in your repair order list. </font><br>");
		  		out.println("<form method='post' action='ShoppingCartServlet' target='_self'>");
				out.println("<table cellspacing='1' cellpadding='3' border='1' BGCOLOR=#EDECE5>");
				out.println("<tr><th><font face='verdana' size='-2'>Quantity"+
									 "</font></th><th><font face='verdana' size='-2'>Type"+
									 "</font></th><th><font face='verdana' size='-2'>Description"+
									 "</font></th><th><font face='verdana' size='-2'>Manufacturer"+
									 "</font></th><th><font face='verdana' size='-2'>Weight<br>(lbs)" +
									 "</font></th><th><font face='verdana' size='-2'>Repair Price" +
					 				 "</font></th><th><font face='verdana' size='-2'>Quantity Price" +

					 				 "</font></th></tr>");

		  		Enumeration e = cart.getItems();
		  		while (e.hasMoreElements()) {
					ShoppingCartItem item = (ShoppingCartItem)e.nextElement();
					ProductDetails prod = (ProductDetails)item.getItem();
					float pPrice = prod.getPrice();
					String prodPrice = cashier.format(pPrice);

					String pId = prod.getProdId();
					String pType = prod.getType();
					String pDesc = prod.getDesc();
					String pMfg = prod.getMfg();
					int pWeight = prod.getWeight();
					int quantity = item.getQuantity();

					float quantityPrice = quantity * pPrice;
					String qtyPrice = cashier.format(quantityPrice);

					int qtyWeight = quantity * pWeight;

					out.println("<tr><td align=right bgcolor='#E6E6FF'><input type='hidden' name='prodId' value='"+
							pId+"'><input align='right' type='text' name='"+pId+"qty' value='"+quantity+"' size='5'></font></td>" +
						"<td bgcolor='#E6E6FF'><font face='verdana' size='-2'>"+pType+"</font></td>" +
						"<td bgcolor='#E6E6FF'><font face='verdana' size='-2'>"+pDesc+ "</font></td>" +
						"<td bgcolor='#E6E6FF'><font face='verdana' size='-2'>"+pMfg+ "</font></td>" +
						"<td bgcolor='#E6E6FF' align='right'><font face='verdana' size='-2'>"+qtyWeight+ "</font></td>" +
						"<td bgcolor='#E6E6FF' align='right'><font face='verdana' size='-2'>"+prodPrice+ "</font></td>" +
						"<td bgcolor='#E6E6FF' align='right'><font face='verdana' size='-2'>"+qtyPrice+ "</font></td>" +
						"</tr>");

				}


				out.println("<tr><td colspan='3' rowspan='1' bgcolor='#FFFFFF'></td>"+
                    	     "<td align='right' bgcolor='#E6E6FF'><font face='verdana' size='-2'>" +
									  "Total weight: "+
								  "</td>" +
								  "<td align='right' bgcolor='#ffffaa'><font face='verdana' size='-2'>" +
									  cashier.getTotalWeight() +
                          "</td>" +
                    	     "<td align='right' bgcolor='#E6E6FF'><font face='verdana' size='-2'>" +
                             "<b>Total Order</b>: "+
                          "</td>" +
                          "<td align='right' bgcolor='#ffffaa'><font face='verdana' size='-2'>" +
                              cashier.format(cashier.getAmount()) +
                          "</td>" +
                        /*"</tr>" +
                        "<tr>" +
                          "<td colspan='5' rowspan='2' bgcolor='#FFFFFF'></td>"+
                          "<td align='right' bgcolor='#E6E6FF'><font face='verdana' size='-2'>" +
                             "CA Sales Tax:"+
                          "</td>" +
                          "<td bgcolor='#ffffaa' align='right'><font face='verdana' size='-2'>" +
                             cashier.format(cashier.getTax()) +
                          "</td>" +
                        "</tr>" +
								"<tr>" +
                          "<td align=\"right\"" + "bgcolor=\"#E6E6FF\"><font face='verdana' size='-2'>" +
                             "<strong>Total Order:</strong></font></td>" +
                          "</td>" +
                          "<td bgcolor='ffffaa' align='right'><font face='verdana' size='-2'><b>" +
                             cashier.format(cashier.getTotal()) + "</td>" +
                          "</b></td>"+
                        */ "</tr>");

				out.println("</table>");
				out.println("<br><input type='submit' name='Action' value='Update quantity'>");
				out.println("&nbsp;&nbsp;&nbsp;<input type='submit' name='Action' value='Clear list'>");
				out.println("&nbsp;&nbsp;&nbsp;</a>");
				out.println("</form>");
				out.println("<font face='verdana' size='-1'><b>Note:</b><br>- To increase quantity, enter desired quantity and click update. <br>- To remove a product from the list, set quantity to 0 and click update.</font><br>");
				out.println("<form action=\""+"CashierServlet" +
						                   "\" method='post' target=_self><font face='verdana' size='-1'><input type='submit' value='Finalize your order'></font></form>");
				out.println("<a href=\""+"../secondary/ProductQuery.html" +
						                   "\"><font face='verdana' size='-1'>Back to the Product Search</a></font>");


	     } else {

		     // Shopping cart is empty!
		     out.println("<br><font face='Verdana' size ='-1'>" +
		                          "There is nothing in your repair order list." +
		                          "<br> &nbsp; <br>" +
		                          "<center><a href=\""+
		                          "../secondary/ProductQuery.html" +
		                          "\"><font size='-1'>Back to the Product Search</a> </center></font>");
        }
		  out.println("</body>");
		  out.println("</html>");
		  out.close();

 	 }
}