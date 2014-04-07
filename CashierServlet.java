/**
 * Title:        CashierServlet.java
 * Description:  Servlet that display the total amount of order
 * Copyright:    Copyright (c) 2000
 * Company:      Big Byte Corp.
 * @author       Tirto Adji
 * @version 1.0
 */


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * An HTTP Servlet that responds to the GET and HEAD methods of the
 * HTTP protocol.  It returns a form to the user that gathers data.
 * The form POSTs to another servlet.
 */
public class CashierServlet extends HttpServlet {

    public void doPost (HttpServletRequest request,
                        HttpServletResponse response)
	                        throws ServletException, IOException {

        // Get the user's session and shopping cart
        HttpSession session = request.getSession(true);
        ShoppingCart cart =
            (ShoppingCart)session.getAttribute(session.getId());
System.out.println("CashierServlet");
System.out.println("cart = "+cart);
System.out.println("sessionId = "+session.getId());

        // If the user has no cart, create a new one
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute(session.getId(), cart);
	     }

	     // set content-type header before accessing Writer
        response.setContentType("text/html");
		  PrintWriter out = response.getWriter();

	     // then write the data of the response
	     out.println("<html>"+"<head><title>Process Order</title></head><body>");

        // Determine the total price of the user's order
        Cashier cashier = new Cashier(cart);
	     double total = cashier.getAmount();
	     int weight = cashier.getTotalWeight();

        // Print out the total and the form for the user
        out.println("<form action=\"" +
                    "ConfirmationServlet" +
                    "\" method=\"post\">" +

						  "<table width='500' border=0 cellspacing=3 cellpadding=0>" +
		  				  "<tr><th colspan=4><font face='verdana' size='-1'>ORDER SUMMARY:</th>"+
                    "<tr><th align=right width=45%><font face='verdana' size='-1'>Total product weight</th><th>:</th><th width=25% align=left><font face='verdana' size='-1'>"+weight+" lbs</th><th width=25%></th></tr>"+
						  "<tr><th align=right width=45%><font face='verdana' size='-1'>Total order amount</th><th>:</th><th width=25% align=left><font face='verdana' size='-1'>"+cashier.format(total)+
						     "</th><td width=25%><font face='verdana' size='-1'><a href=ShoppingCartServlet?Action=View>View Order List</td></tr>"+
						  "<tr><th width=45%><th></th><th align=left colspan=2><font face='verdana' size='-1'>(shipping & handling excluded)</th></tr>"+
                    "</table>"+

                    "<table width='500' border=0 cellspacing=2 cellpadding=2>" +
                    "<tr><td colspan=4><center><font face='verdana' size='-1'>To process your repair order," +
                    " please provide the following information:<br>(<b>bold</b> fields are required)</center></td></tr>"+
                    "<tr><th colspan=4 bgcolor =#EDECE5><font face='verdana' size='-1'>Billing Information:</th></tr>"+
                    "<tr><td align='right'><font face='verdana' size='-1'><b>Name:</b></td><td><font face='verdana' ><input size=20 name='NAME' type='text' value='Adji'></td><td>&nbsp</td><td>&nbsp</td></tr>"+
                    "<tr><td align='right'><font face='verdana' size='-1'><b>Address:<b></td><td><font face='verdana' ><input size=20 name='ADDRESS' type='text' value='48 Washington St #39'></td><td>&nbsp</td><td>&nbsp</td></tr>"+
                    "<tr><td width = 20% align='right'><font face='verdana' size='-1'><b>City:<b></td><td width = 30% ><font face='verdana' ><input size=15 name='CITY' type='text' value='Santa Clara'></td>"+
						      "<td width = 20% align='right'><font face='verdana' size='-1'><b>State:<b></td><td width = 30% ><font face='verdana'><input size=10 name='STATE' type='text' value='CA'></td>"+
						  "</tr>"+
                    "<tr><td align='right'><font face='verdana' size='-1'><b>ZIP:<b></td><td><font face='verdana' ><input size=10 name='ZIP' type='text' value='95050'></td>"+
                        "<td align='right'><font face='verdana' size='-1'>Country:</td><td><font face='verdana' ><input size=10 name='COUNTRY' type='text' value='USA'></td>"+
						  "</tr>"+
                    "<tr><td align='right'><font face='verdana' size='-1'><b>Email:<b></td><td><font face='verdana' ><input size=20 name='EMAIL' type='text' value='tirto_adji@yahoo.com'></td><td>&nbsp</td><td>&nbsp</td></tr>"+
                    "<tr><td align='right'><font face='verdana' size='-1'><b>Phone:<b></td><td><font face='verdana'><input size=10 name='PHONE' type='text' value='408-111-2222'></td>"+
                        "<td align='right'><font face='verdana' size='-1'>Fax:</td><td><font face='verdana' ><input size=10 name='FAX' type='text'></td>"+
                    "</tr>"+

                    "<tr><th colspan=4>&nbsp;</th></tr>"+

                    "<tr><th colspan=4 bgcolor = #EDECE5><font face='verdana' size='-1'>Shipping Information:</th></tr>"+
                    "<tr><td colspan=4><font face='verdana' size='-1'><center>(if different than billing)</center></td></tr>"+

                    "<tr><td align='right'><font face='verdana' size='-1'>Name:</td><td><font face='verdana'><input size=20 name='NAMETOSHIP' type='text' value='Juni'></td></tr>"+
						  "<tr><td align='right'><font face='verdana' size='-1'>Address:</td><td><font face='verdana'><input size=20 name='ADDRESSTOSHIP' type='text' value='507 Valley Way'></td></tr>"+
						  "<tr><td align='right'><font face='verdana' size='-1'>City:</td><td><font face='verdana'><input size=10 name='CITYTOSHIP' type='text' value='Milpitas'></td>"+
								"<td align='right'><font face='verdana' size='-1'>State:</td><td><font face='verdana'><input size=10 name='STATETOSHIP' type='text'value='NY'></td>"+
						  "</tr>"+
						  "<tr><td align='right'><font face='verdana' size='-1'>ZIP:</td><td><font face='verdana' ><input size=10 name='ZIPTOSHIP' type='text' value='90000'></td>"+
								  "<td align='right'><font face='verdana' size='-1'>Country:</td><td><font face='verdana'><input size=10 name='COUNTRYTOSHIP' type='text' value='Canada'></td>"+
						  "</tr>"+
						  "<tr><td align='right'><font face='verdana' size='-1'>Email:</td><td><font face='verdana'><input size=20 name='EMAILTOSHIP' type='text' value='tirto1@yahoo.com'></td></tr>"+
						  "<tr><td align='right'><font face='verdana' size='-1'>Phone:</td><td><font face='verdana'><input size=10 name='PHONETOSHIP' type='text' value='555-999-000'></td>"+
						 	   "<td align='right'><font face='verdana' size='-1'>Fax:</td><td><font face='verdana'><input size=10 name='FAXTOSHIP' type='text'></td>"+
						  "</tr>"+

						  "<tr><th colspan=4>&nbsp;</th></tr>"+

						  "<tr><th colspan=4 bgcolor = #EDECE5><font face='verdana' size='-1'>Shipping Method</th></tr>"+
						  "<tr><td colspan=4><font face='verdana' size='-1'>Select the form of shipping: <select name='SHIPPING' size=1>"+
						       "<option selected value=\"UPS GROUND\">UPS Ground</option>"+
								 "<option value=\"UPS BLUE\">UPS Blue</option>"+
								 "<option value=\"UPS RED\">UPS Red</option>"+
								 "<option value=\"OWN CARRIER\">Your Own Carrier</option>"+
								 "</select></td>"+
						  "</tr>"+


                    "<tr><td align='center' valign='top'><font face='Verdana' size='-1'>Special<br>instruction:</TD><TD colspan=3><TEXTAREA name='DESCRIPTION' rows='3' wrap='soft' cols='50'>Special instruction goes here</TEXTAREA></TD></TR>"+

						  "<tr><th colspan=4>&nbsp;</th></tr>"+

						  "<tr><th colspan=4 bgcolor = #EDECE5><font face='verdana' size='-1'>Payment Method</th></tr>"+
                    "<tr><td colspan=4><font face='verdana' size='-1'>Select the form of payment: <select name=\"METHOD\" size=1>"+
                         "<option selected value='CC'>Credit Card</option>"+
                         "<option value=\"ECHECK\">Electronic Check</option>"+
                         "<option value='NET'>Net Terms</option>"+
                         "</select></td>"+
                    "</tr>"+

                    "<tr><td colspan=4><font face='verdana' size='-1'>"+
                         "By clicking the \"Accept Terms and Proceed\" button, you are agree with BigByte's "+
                         "<a href='../secondary/terms_and_condition.html' target='_blank'>terms and conditions</a>"+
                         ". Please read carefully before continue.</td>"+
                    "</tr>"+

                    "<tr><th colspan=4>&nbsp;</th></tr>"+

			           "<tr>" +
                    "<td colspan='2'><center><input type=\"submit\"" +
                    "value=\"Accept Terms and Continue\"></center></td>" +
                    "<td colspan='2'><center><input type=\"reset\"" +
                    "value=\"Reset Form\"></center></td>" +
                    "</tr>" +

                    "</table>" +
                    "</form>" +
                    "</body>" +
                    "</html>");
        out.close();
    }

    public String getServletInfo() {
        return "The Cashier servlet takes the user's name and " +
               "credit-card number so that the user can order the repair service.";

    }
}
