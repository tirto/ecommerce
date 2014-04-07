/**
 * Title:        PayFlowLinkServlet.java
 * Description:  Servlet that submit transaction info to the payflow link
 * Copyright:    Copyright (c) 2000
 * Company:      Big Byte Corp.
 * @author       Tirto Adji
 * @version 1.0
 */


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Enumeration;
import java.util.StringTokenizer;

/**
 * An HTTP Servlet that responds to the GET and HEAD methods of the
 * HTTP protocol.  It returns a form to the user that gathers data.
 * The form POSTs to another servlet.
 */
public class PayFlowLinkServlet extends HttpServlet {

    private static final String SHIPMETHOD = "USER1";
	 private static final String SHIPCHARGES = "USER2";
	 private static final String ORDERAMOUNT = "USER3";
	 private String CCNum = "";
	 private String CCType = "";
	 private String expDate = "";
	 private String checkNum = "";
	 private String MICR = "";
	 private String typeOfCheck = "";
	 private String DLnum = "";
	 private String stateOfDL = "";
	 private String method = "";

    private PrintWriter out = null;

    public void doPost (HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        // Get the user\"s session and shopping cart
        HttpSession session = request.getSession(true);
        ShoppingCart cart =
            (ShoppingCart)session.getAttribute(session.getId());
System.out.println("PayFlowLinkServlet");
System.out.println("cart = "+cart);
System.out.println("sessionId = "+session.getId());


        // If the user has no cart, create a new one
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute(session.getId(), cart);
	     }

	     // set content-type header before accessing Writer
        response.setContentType("text/html");
		  out = response.getWriter();

	     // then write the data of the response
	     out.println("<html>"+"<head><title>Process PayFlowLink</title>");

	     // JavaScript to return to the previous window
	     out.println("<script LANGUAGE=\"JavaScript\">");
	     out.println("if (document.images) {");
		  out.println("  defImg=\"row1d\";");
		  out.println("  row1boff = new Image();");
		  out.println("  row1boff.src=\"../secondary/images/row1boff.jpg\";");
		  out.println("  row1bon = new Image();");
		  out.println("  row1bon.src=\"../secondary/images/row1bon.jpg\";");
		  out.println("  row1coff = new Image();");
		  out.println("  row1coff.src=\"../secondary/images/row1coff.jpg\";");
		  out.println("  row1con = new Image();");
		  out.println("  row1con.src=\"../secondary/images/row1con.jpg\";");
		  out.println("  row1doff = new Image();");
		  out.println("  row1doff.src=\"../secondary/images/row1doff.jpg\";");
		  out.println("  row1don = new Image();");
		  out.println("  row1don.src=\"../secondary/images/row1don.jpg\";");
		  out.println("  row1eoff = new Image();");
		  out.println("  row1eoff.src=\"../secondary/images/row1eoff.jpg\";");
		  out.println("  row1eon = new Image();");
		  out.println("  row1eon.src=\"../secondary/images/row1eon.jpg\";");
		  out.println("  row1foff = new Image();");
		  out.println("  row1foff.src=\"../secondary/images/row1foff.jpg\";");
		  out.println("  row1fon = new Image();");
		  out.println("  row1fon.src=\"../secondary/images/row1fon.jpg\";");
		  out.println("}");

		  out.println("function imgOn(imgName) {");
		  out.println("  act(imgName);");
		  out.println("  inact(defImg);");
		  out.println("}");
		  out.println("function imgOff(imgName) {");
		  out.println("  inact(imgName);");
		  out.println("  act(defImg);");
		  out.println("}");
		  out.println("function act(imgName) {");
		  out.println("  if (document.images) ");
		  out.println("	 document[imgName].src = eval(imgName + \"on.src\");");
		  out.println("}");
		  out.println("function inact(imgName) {");
		  out.println("  if (document.images)");
		  out.println("    document[imgName].src = eval(imgName + \"off.src\")");;
		  out.println("}");

	     out.println("    function GoBack() {         ");
	     out.println("       window.history.go(-1)    ");
        out.println("    }                           ");
        out.println("</script>                       ");
        out.println("<head><body>");
        out.println("<table border=0 cellspacing=0 cellpadding=0>");

		  out.println("<tr>");
		  out.println("       <td rowspan=2><a href=\"../index.html\" target=\"_top\"><img src=\"../secondary/images/row1a_2.jpg\" width=199 height=65 border=0></a></td>");
		  out.println("       <td colspan=5><img src=\"../secondary/images/top2.jpg\" width=401 height=37  border=0></td>");

		  out.println("</tr>");
		  out.println("<tr>");
		  out.println("       <td><a href=\"../secondary/profile.html\" target=\"_top\" onMouseOut=\"imgOff('row1b')\" onMouseOver=\"imgOn('row1b')\"><img src=\"../secondary/images/row1boff.jpg\" name=\"row1b\" width=51 height=28 border=0></a></td>");
		  out.println("       <td><a href=\"../secondary/service.html\" target=\"_top\" onMouseOut=\"imgOff('row1c')\" onMouseOver=\"imgOn('row1c')\"><img src=\"../secondary/images/row1coff.jpg\" name=\"row1c\" width=60 height=28 border=0></a></td>");
		  out.println("       <td><a href=\"../secondary/product.html\" target=\"_top\" onMouseOut=\"imgOff('row1d')\" onMouseOver=\"imgOn('row1d')\"><img src=\"../secondary/images/row1don.jpg\" name=\"row1d\" width=118 height=28 border=0></a></td>");
		  out.println("       <td><a href=\"../secondary/order.html\" target=\"_top\" onMouseOut=\"imgOff('row1e')\" onMouseOver=\"imgOn('row1e')\"><img src=\"../secondary/images/row1eoff.jpg\" name=\"row1e\" width=94 height=28 border=0></a></td>");
		  out.println("       <td><a href=\"../secondary/contact.html\" target=\"_top\" onMouseOut=\"imgOff('row1f')\" onMouseOver=\"imgOn('row1f')\"><img src=\"../secondary/images/row1foff.jpg\" name=\"row1f\" width=78 height=28 border=0></a></td>");


		  out.println("</tr>");
		  out.println("</table>");



		  Cashier cashier = new Cashier(cart);
		  int num = cart.getNumberOfItems();
		  if(num > 0) {
				out.println("<br><table cellspacing='1' cellpadding='3' border='1' BGCOLOR=#EDECE5>");
				out.println("<caption><font face='verdana' size='-1'><b>REPAIR ORDER DESCRIPTION</b></caption>");
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

					out.println("<tr><td align=center bgcolor='#E6E6FF'><font face=verdana size=-2>"+quantity+"</font></td>" +
						"<td bgcolor='#E6E6FF'><font face='verdana' size='-2'>"+pType+"</font></td>" +
						"<td bgcolor='#E6E6FF'><font face='verdana' size='-2'>"+pDesc+ "</font></td>" +
						"<td bgcolor='#E6E6FF'><font face='verdana' size='-2'>"+pMfg+ "</font></td>" +
						"<td bgcolor='#E6E6FF' align='right'><font face='verdana' size='-2'>"+qtyWeight+ "</font></td>" +
						"<td bgcolor='#E6E6FF' align='right'><font face='verdana' size='-2'>"+prodPrice+ "</font></td>" +
						"<td bgcolor='#E6E6FF' align='right'><font face='verdana' size='-2'>"+qtyPrice+ "</font></td>" +
						"</tr>");

				}

				double shipCharges = Double.parseDouble(request.getParameter(SHIPCHARGES));
				out.println("<tr><td colspan='3' rowspan='1' bgcolor='#FFFFFF'></td>"+
									  "<td align='right' bgcolor='#E6E6FF'><font face='verdana' size='-2'>" +
									  "Total weight: "+
								  "</td>" +
								  "<td align='right' bgcolor='#ffffaa'><font face='verdana' size='-2'>" +
									    cashier.getTotalWeight() +
									 "</td>" +
									  "<td align='right' bgcolor='#E6E6FF'><font face='verdana' size='-2'>" +
										 "Subtotal:"+
									 "</td>" +
									 "<td align='right' bgcolor='#ffffaa'><font face='verdana' size='-2'>" +
										  cashier.format(cashier.getAmount()) +
									 "</td>" +
								  "</tr>" +
								  "<tr>" +
									 "<td colspan='5' rowspan='2' bgcolor='#FFFFFF'></td>"+
									 "<td align='right' bgcolor='#E6E6FF'><font face='verdana' size='-2'>" +
										 "Shipping:"+
									 "</td>" +
									 "<td bgcolor='#ffffaa' align='right'><font face='verdana' size='-2'>" +
										 cashier.format(shipCharges) +
									 "</td>" +
								  "</tr>" +
								"<tr>" +
									 "<td align=\"right\"" + "bgcolor=\"#E6E6FF\"><font face='verdana' size='-2'>" +
										 "<strong>Total Order:</strong></font></td>" +
									 "</td>" +
									 "<td bgcolor='ffffaa' align='right'><font face='verdana' size='-2'><b>" +
										 cashier.format(cashier.getAmount() + shipCharges) + "</td>" +
									 "</b></td>"+
									"</tr>");
			  out.println("</table>");



			  out.println("<form action=\"" +
							  "https://payflowlink.signio.com/paylinks.dll"+
							  "\" method=\"post\" target=_top>" );


			  method = request.getParameter("METHOD").trim();


			  int CCNumValidCode = 0;
			  int expDateValidCode = 0;
			  if (method.equals("CC")) {
				  // Check and get CCNumber and expDate field

				  CCNum = request.getParameter("CARDNUM");
			     boolean CCNumIsValid = false;
			     boolean expDateIsValid = false;


			     CCNumValidCode = validateCCNum(CCNum);
				  switch (CCNumValidCode) {
					  case 1:
					  	  out.println("<font face='verdana' size='-1'>Missing parameter <b>Credit Card Number </b> value!");
					  	  out.println("<br><font face=\"verdana\" size=\"-1\">Click back to enter required value. &nbsp; &nbsp;<input type=\"button\" value=\"&lt;&lt; Back\" onClick=\"GoBack()\">");
				     	  CCNumIsValid = false;
				     	  break;
				     case 2:
				  		  out.println("<font face='verdana' size='-1'>The <b>Credit Card Number </b> you just enter is incorrect! It must contains 16 digits number");
						  out.println("<br><font face=\"verdana\" size=\"-1\">Click back to enter the correct value. &nbsp; &nbsp;<input type=\"button\" value=\"&lt;&lt; Back\" onClick=\"GoBack()\">");
						  CCNumIsValid = false;
						  break;
				  	  case 3:
				  	     //System.out.println("CCNumValidCode = "+CCNumValidCode);
				  	  	  CCNumIsValid = true;
				  	  	  break;
				  	  default:
				  	  	  out.println("<font face='verdana' size='-1'>Invalid <b>Credit Card Number </b> value!");
					  	  out.println("<br><font face=\"verdana\" size=\"-1\">Click back to enter required value. &nbsp; &nbsp;<input type=\"button\" value=\"&lt;&lt; Back\" onClick=\"GoBack()\">");
					  	  CCNumIsValid = false;
					  	  break;
				  }


				  if (CCNumIsValid) {
				  	  expDate = request.getParameter("EXPDATE");
				  	  expDateValidCode = validateExpDate(expDate);
					  switch (expDateValidCode) {
						  case 1:
							  out.println("<font face='verdana' size='-1'>Missing parameter <b>Credit Card Expiration Date </b> value!");
							  out.println("<br><font face=\"verdana\" size=\"-1\">Click back to enter required value. &nbsp; &nbsp;<input type=\"button\" value=\"&lt;&lt; Back\" onClick=\"GoBack()\">");
							  expDateIsValid = false;
							  break;
						  case 2:
							  out.println("<font face='verdana' size='-1'>The <b>Credit Card Expiration Date</b> you just enter is incorrect!");
							  out.println("<br><font face=\"verdana\" size=\"-1\">The correct format is MM/YY. Click back to enter the correct value. &nbsp; &nbsp;<input type=\"button\" value=\"&lt;&lt; Back\" onClick=\"GoBack()\">");
							  expDateIsValid = false;
							  break;
						  case 3:
							  expDateIsValid = true;
							  break;
						  case 4:
						  	  out.println("<font face='verdana' size='-1'>Invalid <b>Credit Card Expiration Month</b>. Month should be between 01-12!");
							  out.println("<br><font face=\"verdana\" size=\"-1\">The correct format is MM/YY. Click back to enter the correct value. &nbsp; &nbsp;<input type=\"button\" value=\"&lt;&lt; Back\" onClick=\"GoBack()\">");
							  expDateIsValid = false;
							  break;
						  default:
							  out.println("<font face='verdana' size='-1'>Invalid <b>Credit Card Expiration Date </b> value!");
							  out.println("<br><font face=\"verdana\" size=\"-1\">Click back to enter required value. &nbsp; &nbsp;<input type=\"button\" value=\"&lt;&lt; Back\" onClick=\"GoBack()\">");
							  expDateIsValid = false;
							  break;
						}
					}



				  if (CCNumIsValid && expDateIsValid) {
					  CCType = request.getParameter("CARDTYPE");

					  cart.setCCNum(CCNum);
					  cart.setExpDate(expDate);
					  cart.setCCType(CCType);
					  displayOrderInfo(out,request);

					  Enumeration enum = request.getParameterNames();
					  while (enum.hasMoreElements()) {
						  String name = (String)enum.nextElement();
						  String value = request.getParameter(name).trim();
						  out.println("<input name=\""+name+"\" type=\"hidden\" value=\""+value+"\">");

					  }
					  out.println("<table width='500' border=0 cellspacing=3 cellpadding=0>" +
										  "<tr><td colspan=3>&nbsp;</td>"+
										  "</tr>"+
										  "<tr>" +
											  "<td colspan=3>"+
												 "<center>"+
													"<input type=\"submit\"value=\"Submit Payment\">"+
													"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
													//"<input type=\"reset\" value=\"Reset Form\">" +
												 "</center>"+
											  "</td>"+
										  "</tr>" +
										  "<tr>" +
											  "<td colspan=3>"+
												 "<center>"+
													"<br><font face=verdana size=-2>Please wait 30-60 seconds to process your payment depending on your Internet connection."+
													" Thank you for your patience."+
												 "</center>"+
											  "</td>"+
										  "</tr>" +
										  "</table>");
				  } // CCNumIsValid && expDateIsValid

				} // end method.equals(CC)

				else if (method.equals("ECHECK")) {
					checkNum = request.getParameter("CHECKNUM").trim();
				   MICR = request.getParameter("MICR").trim();
				   typeOfCheck = request.getParameter("TYPEOFCHECK").trim();
				   DLnum = request.getParameter("DLNUM");
				   stateOfDL = request.getParameter("STATEOFDL");
				   if (checkNum.equals("")) {
					  out.println("<font face='verdana' size='-1'>Missing parameter <b>Check # </b> value!");
					  out.println("<br><font face=\"verdana\" size=\"-1\">Click back to enter required value. &nbsp; &nbsp;<input type=\"button\" value=\"&lt;&lt; Back\" onClick=\"GoBack()\">");
				   }
				   else if (MICR.equals("")) {
					  out.println("<font face='verdana' size='-1'>Missing parameter <b>Routing # </b> value!");
					  out.println("<br><font face=\"verdana\" size=\"-1\">Click back to enter required value. &nbsp; &nbsp;<input type=\"button\" value=\"&lt;&lt; Back\" onClick=\"GoBack()\">");
				   }
				   else if (DLnum.equals("")) {
					  out.println("<font face='verdana' size='-1'>Missing parameter <b>Driver License #</b> value!");
					  out.println("<br><font face=\"verdana\" size=\"-1\">Click back to enter required value. &nbsp; &nbsp;<input type=\"button\" value=\"&lt;&lt; Back\" onClick=\"GoBack()\">");
				   }
				   else if (stateOfDL.equals("")) {
					  out.println("<font face='verdana' size='-1'>Missing parameter <b>State of Driver License </b> value!");
					  out.println("<br><font face=\"verdana\" size=\"-1\">Click back to enter required value. &nbsp; &nbsp;<input type=\"button\" value=\"&lt;&lt; Back\" onClick=\"GoBack()\">");
				   }
				   if (!checkNum.equals("") && !MICR.equals("") && !DLnum.equals("") && !stateOfDL.equals("")) {
					  cart.setCheckNum(checkNum);
					  cart.setMICR(MICR);
					  cart.setTypeOfCheck(typeOfCheck);
					  cart.setDLnum(DLnum);
					  cart.setStateOfDL(stateOfDL);
					  displayOrderInfo(out, request);
					  Enumeration enum = request.getParameterNames();
					  while (enum.hasMoreElements()) {
						  String name = (String)enum.nextElement();
						  String value = request.getParameter(name).trim();
						  out.println("<input name=\""+name+"\" type=\"hidden\" value=\""+value+"\"> "+
											"");
											//"<br>param = "+name+ " value = "+value);
					  }
					  out.println("<table width='500' border=0 cellspacing=3 cellpadding=0>" +
										  "<tr><td colspan=3>&nbsp;</td>"+
										  "</tr>"+
										  "<tr>" +
											  "<td colspan=3>"+
												 "<center>"+
													"<input type=\"submit\"value=\"Submit Payment\">"+
													"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
													//"<input type=\"reset\" value=\"Reset Form\">" +
												 "</center>"+
											  "</td>"+
										  "</tr>" +
										  "</table>");
				   } // !CheckNum.equals("") && ....



			   } //end method.equals("ECHECK")
				out.println("</form>");



		  } else {
			  // Shopping cart is empty!
			  out.println("<br><font face='Verdana' size ='-1'>" +
										  "There is nothing in your repair order list." +
										  "<br> &nbsp; <br>" +
										  "<center><a href=\""+
										  "../secondary/ProductQuery.html" +
										  "\"><font size='-1'>Back to the Product Search</a> </center></font>");
        }

  		  out.println("</body></html>");
		  out.close();
    } // end doPost




    public void displayOrderInfo(PrintWriter out, HttpServletRequest request) {//throws ServletException {
		 out.println("<table border=0 cellpadding=2 cellspacing=2>");
		 out.println("<tr><td><font color=navy face=verdana size=-1><b>Shipping Method: </b></font><font face=verdana size=-1>"+request.getParameter(SHIPMETHOD).trim()+"</td></tr>");
		 out.println("<tr><td><font color=navy face=verdana size=-1><b>Billing info:</b></td><td><font color=navy face=verdana size=-1><b>Shipping info:</b></td></tr>");
		 out.println("<tr><td><font face=verdana size=-1>"+request.getParameter("NAME").trim()+"</td><td><font face=verdana size=-1>"+request.getParameter("NAMETOSHIP").trim()+"</td></tr>");
		 out.println("<tr>"+
							"<td><font face=verdana size=-1>"+request.getParameter("ADDRESS").trim()+
								 "<br>"+request.getParameter("CITY").trim()+", "+request.getParameter("STATE").trim()+" "+request.getParameter("ZIP").trim()+
								 "<br>"+request.getParameter("COUNTRY").trim()+
								 "<br>Phone: "+request.getParameter("PHONE").trim()+
								 "<br>Fax: "+request.getParameter("FAX").trim()+
							"</td>"+
							"<td><font face=verdana size=-1>"+request.getParameter("ADDRESSTOSHIP").trim()+
								 "<br>"+request.getParameter("CITYTOSHIP").trim()+", "+request.getParameter("STATETOSHIP").trim()+" "+request.getParameter("ZIPTOSHIP").trim()+
								 "<br>"+request.getParameter("COUNTRYTOSHIP").trim()+
								 "<br>Phone: "+request.getParameter("PHONETOSHIP").trim()+
								 "<br>Fax: "+request.getParameter("FAXTOSHIP").trim()+
							"</td>"+
							"</tr>");
       String pMethod = method.equals("CC")?"Credit Card":"Electronic Check";
		 out.println("<tr><td><font color=navy face=verdana size=-1><b>Payment Method: </b></font><font face=verdana size=-1>"+pMethod);
		 if (method.equals("CC")) {
			 out.println("<br>Credit Card #: "+displayCC(CCNum)+
			 				 "<br>Credit Card Type: "+CCType+
		                "<br>Expiration Date: "+expDate);
	    }
	    else if (method.equals("ECHECK")) {
			 String checkType = typeOfCheck.equals("P")?"Personal":"Business";
			 out.println("<br>Check #: "+checkNum+
		                "<br>Routing #: "+MICR+
		                "<br>Type Of Check: "+ checkType+
		                "<br>Driver License: "+ DLnum+
							 "<br>State of DL: "+ stateOfDL);
	    }
		 out.println("</td><td align=center><font face='verdana' size='-1'>Click back to change payment info <br><input type=\"button\" value=\"&lt;&lt; Back\" onClick=\"GoBack()\"></td></tr>");
		 out.println("</table>");
    }

    public String getServletInfo() {
        return "The PayFlowLink servlet takes the payment info and validate them "+
        			"before sending the transaction info to the PayFlowLink";

    }

    public String displayCC(String CCNum) {
		 StringBuffer num = new StringBuffer(CCNum);
		 num.replace(4,12,"XXXXXXXX");
		 String number = num.toString();
		 return number;
    }

    public int validateCCNum(String CCNum) {
		 int validCode = 0;
		 try {
			 int cardNum = Integer.parseInt(CCNum);
		 }
		 catch (NumberFormatException nfe) {
			 System.out.println("null");
		 }

	    if (CCNum.length()==0) {
			 validCode = 1;
	    }
	    else if (CCNum.length()!=16) {
			 System.out.println("Invalid CCNum length = "+CCNum.length());
			 validCode = 2;
		 }
		 else {
			 System.out.println("Valid CC number");
			 validCode = 3;
		 }

		 return validCode;
 	 }

 	 public int validateExpDate(String expDate) {
		 int validCode = 0;
		 int expDateLength = expDate.length();
		 if (expDateLength==0) {
			 System.out.println("null");
			 validCode = 1;
	    }
	    else if (expDateLength!=5) {
		 	 System.out.println("Invalid expDate length");
		 }
		 else {
			 try {
				 StringTokenizer st = new StringTokenizer(expDate,"/");
				 int month = Integer.parseInt(st.nextToken());
				 System.out.println("month = "+ month);
				 int year = Integer.parseInt(st.nextToken());
				 System.out.println("year = "+ year);

				 if (month > 12 || month < 0) {
					 System.out.println("month is not valid");
					 validCode = 4;
				 }
				 else if (year > 99 || year < 0) {
					 System.out.println("year is not valid");
					 validCode = 5;
				 }

				 else {
					 validCode = 3;
				 }
			  }
			  catch (Exception e) {
				  System.out.println("Exception caught");
			  }

	    }
		 return validCode;
 	 }
}
