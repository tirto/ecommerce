/**
 * Title:        ConfirmationServlet.java
 * Description:  Servlet that confirm transaction info before submititing it to the payflow link
 * Copyright:    Copyright (c) 2000
 * Company:      Big Byte Corp.
 * @author       Tirto Adji
 * @version 1.0
 */


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Enumeration;

/**
 * An HTTP Servlet that responds to the GET and HEAD methods of the
 * HTTP protocol.  It returns a form to the user that gathers data.
 * The form POSTs to another servlet.
 */
public class ConfirmationServlet extends HttpServlet {

    public void doPost (HttpServletRequest request,
                       HttpServletResponse response)
	throws ServletException, IOException
    {
        // Get the user\"s session and shopping cart
        HttpSession session = request.getSession(true);
        ShoppingCart cart =
            (ShoppingCart)session.getAttribute(session.getId());
System.out.println("ConfirmationServlet");
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
	     out.println("<html>"+"<head><title>Process PayFlowLink</title>");

	     // JavaScript to return to the previous window
	     out.println("<script LANGUAGE=\"JavaScript\">");
	     out.println("    function GoBack() {         ");
	     out.println("       window.history.go(-1)    ");
        out.println("    }                           ");
        out.println("</script>                       ");
        out.println("<head><body>");


		  out.println("<form action=\"" +
		  				  "PayFlowLinkServlet" +
                    "\" method=\"post\" target=_top>" );

        boolean errorFlag = false;

        Enumeration enum = request.getParameterNames();
		  while (enum.hasMoreElements()) {
			  String name = (String)enum.nextElement();
			  String value = request.getParameter(name).trim();
			  if (!checkParam(name, value)) {
				  out.println("<input name=\""+name+"\" type=\"hidden\" value=\""+value+"\"> "+
								"");//"param = "+name+ " value = "+value);
			  }
			  else {
				  out.println("<font face='verdana' size='-1'>Missing parameter <b>BILLING "+name+ "</b> value!");
				  errorFlag = true;
				  break;
		     }
		  }


        if (!errorFlag) {
			  // Determine the total price of the user\"s order
			  Cashier cashier = new Cashier(cart);
			  int weight = cashier.getTotalWeight();

			  String shipMethod = request.getParameter("SHIPPING").trim();
			  double shipCharges = cashier.getShipCharges(shipMethod, weight);
			  double total = cashier.getAmount();
			  double grandTotal = total + shipCharges;

			  char transactionType = 'A';
			  String method = request.getParameter("METHOD").trim();

			  out.println("<table width='500' border=0 cellspacing=3 cellpadding=0>" +
							  "<tr><th colspan=4><font face='verdana' size='-1'>ORDER SUMMARY:</th>"+
							  "<tr><th align=right width=45%><font face='verdana' size='-1'>Total product weight</th><th>:</th><th align=right width=25%><font face='verdana' size='-1'>"+weight+" lbs</th><td width=20%></td></tr>"+
							  "<tr><th align=right width=45%><font face='verdana' size='-1'>Shipping method</th><th>:</th><th align=right width=25%><font face='verdana' size='-1'>"+shipMethod+"</th><td width=20%></td></tr>"+
							  "<tr><th align=right width=45%><font face='verdana' size='-1'>Total order amount</th><th>:</th><th align=right width=25%><font face='verdana' size='-1'>"+cashier.format(total)+"</th><td width=20%></td></tr>"+
							  "<tr><th align=right width=45%><font face='verdana' size='-1'>Shipping & handling charges</th><th>:</th><th align=right width=25%><font face='verdana' size='-1'>"+cashier.format(shipCharges)+"</th><td width=20%></td></tr>"+
							  "<tr><th align=right width=45%><font face='verdana' size='-1'>GRAND TOTAL</th><th>:</th><th align=right width=25%><font face='verdana' size='-1'>"+cashier.format(grandTotal)+"</th><td width=20%></td></tr>"+
							  "<tr><th align=right width=45%><font face='verdana' size='-1'>Payment Method</th><th>:</th><th align=right width=25%><font face='verdana' size='-1'>"+method+"</th><td width=20%></td></tr>"+
							  "<tr><td colspan=4 align=center><font face='verdana' size='-1'>Click back to change shipping or payment method &nbsp; &nbsp;<input type=\"button\" value=\"&lt;&lt; Back\" onClick=\"GoBack()\"></td></tr>"+
							  "</table><br>");
			  out.println("<table width=\"500\" border=0 cellspacing=3 cellpadding=0>" );

			  if(method.equals("ECHECK")) {
				  transactionType = 'S';
				  out.println("<tr><th colspan=3 bgcolor =#EDECE5><font face=\"verdana\" size=\"-1\">Electronic Check Information:</th></tr>");
				  out.println("<tr><td align=right><font face=\"verdana\" size=\"-1\"><b>Check #</b></td><th>:</th><td><font face=\"verdana\">" +
										  //"<input size=8 name=\"CHECKNUM\" type=\"text\" value=\"1001\"></td></tr>");
										  "<input size=8 name=\"CHECKNUM\" type=\"text\"></td></tr>");
				  out.println("<tr><td align=right><font face=\"verdana\" size=\"-1\"><b>Routing #</b></td><th>:</th><td><font face=\"verdana\">" +
										  //"<input size=25 name=\"MICR\" type=\"text\" value=\"1234567804390850001001\"></td></tr>");
										  "<input size=25 name=\"MICR\" type=\"text\"></td></tr>");
				  out.println("<tr><td align=right><font face=\"verdana\" size=\"-1\"><b>Type Of Check</b></td><th>:</th><td><font face=\"verdana\" size=\"-1\">" +
				  						  "<select name ='TYPEOFCHECK' size=1><font face=\"verdana\" size=\"-1\">"+
											 "<option selected value='B'>Business</option>"+
											 "<option value='P'>Personal</option>"+
										  "</select></td></tr>");
				  out.println("<tr><td align=right><font face=\"verdana\" size=\"-1\"><b>Driver License #</b></td><th>:</th><td><font face=\"verdana\">" +
										  //"<input size=10 name=\"DLNUM\" type=\"text\" value=\"B1234567\"></td></tr>");
										  "<input size=10 name=\"DLNUM\" type=\"text\"></td></tr>");
				  out.println("<tr><td align=right><font face=\"verdana\" size=\"-1\"><b>State of Driver Licence</b></td><th>:</th><td><font face=\"verdana\">" +
										  //"<input size=2 name=\"STATEOFDL\" type=\"text\" value=\"CA\"></td></tr>");
										  "<input size=2 name=\"STATEOFDL\" type=\"text\"></td></tr>");





			  }
			  else if (method.equals("CC")) {
				  out.println("<tr><th colspan=3 bgcolor =#EDECE5><font face=\"verdana\" size=\"-1\">Credit Card Information:</th></tr>");
				  out.println("<tr><td align=\"right\"><font face=\"verdana\" size=\"-1\"><b>Credit Card #</b></td><th>:</th><td><font face=\"verdana\">" +
										  //"<input size=16 name=\"CARDNUM\" type=\"text\" value=\"5105105105105100\"></td></tr>");
										  "<input size=16 name=\"CARDNUM\" type=\"text\"</td></tr>");
				  out.println("<tr><td align=\"right\"><font face=\"verdana\" size=\"-1\"><b>Credit Card Type</b></td><th>:</th>"+
								 "<td><select name=\"CARDTYPE\" size=1>"+
                         "<option selected value='Visa'>Visa</option>"+
                         "<option value=\"MasterCard\">Master Card</option>"+
                         "<option value='Discover'>Discover</option>"+
                         "<option value='Amex'>American Express</option>"+
                         "<option value='Optima'>Optima</option>"+
                         "<option value='DinersClub'>Diners Club</option>"+
                         "<option value='JCB'>JCB</option>"+
                         "</select></td>");

				  out.println("<tr><td align=\"right\"><font face=\"verdana\" size=\"-1\"><b>Expiration Date</b></td><th>:</th>"+
										//"<td><input size=5 name=\"EXPDATE\" type=\"text\" value=\"09/00\"><font face=\"verdana\" size='-1'> (mm/yy)</td></tr>");
										"<td><input size=5 name=\"EXPDATE\" type=\"text\"><font face=\"verdana\" size='-1'> (mm/yy)</td></tr>");


			  }

			  else {
				  out.println("<tr><th colspan=2 bgcolor =#EDECE5><font face=\"verdana\" size=\"-1\">Non-online Information:</th></tr>");

			  }


			  out.println("<input name=\"MFCIsapiCommand\" type=\"hidden\" value=\"Orders\">"+
							  "<input name=\"LOGIN\" type=\"hidden\" value=\"tadji\">"+
							  "<input name=\"AMOUNT\" type=\"hidden\" value=\""+grandTotal+"\">"+
							  "<input name=\"ORDERFORM\" type=\"hidden\" value=\"false\">"+
							  "<input name=\"TYPE\" type=\"hidden\" value=\""+transactionType+"\">"+
							  "<input name=\"USER1\" type=\"hidden\" value=\""+shipMethod+"\">"+
							  "<input name=\"USER2\" type=\"hidden\" value=\""+shipCharges+"\">"+
							  "<input name=\"USER3\" type=\"hidden\" value=\""+total+"\">"+
							  "<input name=\"SHOWCONFIRM\" type=\"hidden\" value=\"false\">"+





							  "<tr><th colspan=3>&nbsp;</th></tr>"+
							  "<tr>" +
								  "<td colspan=3>"+
									 "<center>"+
										"<input type=\"submit\"value=\"Confirm Payment Info\">"+
										"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
										"<input type=\"reset\" value=\"Reset Form\">" +
									 "</center>"+
								  "</td>"+
							  "</tr>" +

							  "</table>" +
							  "</form>" +
							  "</body>" +
							  "</html>");
		  }

		  else {
	    		out.println("<br><font face=\"verdana\" size=\"-1\">Click back to enter required value. &nbsp; &nbsp;<input type=\"button\" value=\"&lt;&lt; Back\" onClick=\"GoBack()\">");
	     }

        out.close();
    }

    public boolean checkParam(String name, String value) {
		 boolean missingValue = false;
	    if (name.equals("CITY") && value.equals("") || value == null) {
			 missingValue = true;
		 }
		 else if (name.equals("EMAIL") && value.equals("") || value == null) {
		    missingValue = true;
		 }
		 else if (name.equals("COUNTRY") && value.equals("") || value == null) {
		 	 missingValue = true;
		 }
		 else if (name.equals("NAME") && value.equals("") || value == null) {
		 	 missingValue = true;
		 }
		 else if (name.equals("NAME") && value.equals("") || value == null) {
		 	 missingValue = true;
		 }
		 else if (name.equals("STATE") && value.equals("") || value == null) {
		 	 missingValue = true;
		 }
		 else if (name.equals("ADDRESS") && value.equals("") || value == null) {
		 	 missingValue = true;
		 }
		 else if (name.equals("PHONE") && value.equals("") || value == null) {
		 	 missingValue = true;
		 }
		 else if (name.equals("ZIP") && value.equals("") || value == null) {
		 	 missingValue = true;
		 }

		 return missingValue;
    }

    public String getServletInfo() {
        return "The ConfirmationServlet takes the transaction's info and validate them "+
        			"before sending the transaction info to the PayFlowLinkServlet";

    }
}
