/**
 * Title:        PostPaymentServlet.java
 * Description:  Servlet that display the order confirmation and details
 * Copyright:    Copyright (c) 2000
 * Company:      Big Byte Corp.
 * @author       Tirto Adji
 * @version 1.0
 */


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;

/**
 * An HTTP Servlet that responds to the GET and HEAD methods of the
 * HTTP protocol.  It returns a form to the user that gathers data.
 * The form POSTs to another servlet.
 */
public class PostPaymentServlet extends HttpServlet {
    private String name = "";
    private String address = "";
    private String city = "";
    private String state = "";
    private String country = "";
    private String zip = "";
    private String phone = "";
    private String fax = "";
    private String email = "";
    private String nameToShip = "";
	 private String addressToShip = "";
	 private String cityToShip = "";
	 private String stateToShip = "";
	 private String countryToShip = "";
	 private String zipToShip = "";
	 private String phoneToShip = "";
	 private String faxToShip = "";
	 private String emailToShip = "";
	 private String CCNum = "";
	 private String CCType = "";
	 private String expDate = "";
	 private String checkNum = "";
	 private String MICR = "";
	 private String typeOfCheck = "";
	 private String DLnum = "";
	 private String stateOfDL = "";
	 private int invoiceNum = 0;
	 private String PNRef = "";
	 private static final String SHIPMETHOD = "USER1";
	 private static final String SHIPCHARGES = "USER2";
	 private static final String ORDERAMOUNT = "USER3";
    private ShoppingCart cart = null;
    private String method = "";

    public void doPost (HttpServletRequest request,
                        HttpServletResponse response)
	                        throws ServletException, IOException {

        // Get the user's session and shopping cart
        HttpSession session = request.getSession(true);
        cart =
            (ShoppingCart)session.getAttribute(session.getId());
System.out.println("PostPaymentServlet");
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
	     out.println("<html>"+"<head><title>Order Confirmation and Details</title>"+
	     				  "<META HTTP-EQUIV=\"refresh\" CONTENT=\"60; URL=../index.html\"></head><body>");

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

		  //Database info
		  String classname = "sun.jdbc.odbc.JdbcOdbcDriver";
		  String url = "jdbc:odbc:bigbyte";
		  String login = "adji";
		  String password = "noni";

		  //Customer details
		  name = request.getParameter("NAME");
		  address = request.getParameter("ADDRESS");
		  city = request.getParameter("CITY");
		  state = request.getParameter("STATE");
		  country = request.getParameter("COUNTRY");
		  zip = request.getParameter("ZIP");
		  phone = request.getParameter("PHONE");
		  fax = request.getParameter("FAX");
		  email = request.getParameter("EMAIL");

		  nameToShip = request.getParameter("NAMETOSHIP");
		  addressToShip = request.getParameter("ADDRESSTOSHIP");
		  cityToShip = request.getParameter("CITYTOSHIP");
		  stateToShip = request.getParameter("STATETOSHIP");
		  countryToShip = request.getParameter("COUNTRYTOSHIP");
		  zipToShip = request.getParameter("ZIPTOSHIP");
		  phoneToShip = request.getParameter("PHONETOSHIP");
		  faxToShip = request.getParameter("FAXTOSHIP");
		  emailToShip = request.getParameter("EMAILTOSHIP");

		  //Order details
		  PNRef = request.getParameter("PNREF");
		  Calendar today = Calendar.getInstance();
		  java.util.Date todayDate = today.getTime();
		  java.sql.Timestamp orderDate = new java.sql.Timestamp(todayDate.getTime());

		  String shipMethod = request.getParameter(SHIPMETHOD);
		  double shipCharges = Double.parseDouble(request.getParameter(SHIPCHARGES));
		  double orderAmount = Double.parseDouble(request.getParameter(ORDERAMOUNT));
		  double grandTotal = Double.parseDouble(request.getParameter("AMOUNT"));
		  String paymentMethod = request.getParameter("METHOD");
		  String paymentType = request.getParameter("TYPE");
		  String avsData = request.getParameter("AVSDATA");
		  String authCode = request.getParameter("AUTHCODE");
		  String respMsg = request.getParameter("RESPMSG");
		  byte resultCode = Byte.parseByte(request.getParameter("RESULT"));
		  String orderDesc = request.getParameter("DESCRIPTION");

		  try {
			  // Load the JDBC-ODBC driver
			  Class.forName(classname);
		  }
		  catch (ClassNotFoundException cnfe) {
	     	  cnfe.printStackTrace();
	     }

		  Connection con = null;
		  try {

			  con = DriverManager.getConnection(url,
					login, password);
			  con.setAutoCommit(false);

			  if (request.getParameter("METHOD").equals("CC")) {
				  CCNum = cart.getCCNum();
				  CCType = cart.getCCType();
				  expDate = cart.getExpDate();
				  PreparedStatement pstmt = con.prepareStatement("INSERT INTO CUSTOMERS (NAME,ADDRESS,CITY,STATE,COUNTRY,ZIP,PHONE,FAX,EMAIL,NAMETOSHIP,ADDRESSTOSHIP,CITYTOSHIP,STATETOSHIP,COUNTRYTOSHIP,ZIPTOSHIP,PHONETOSHIP,FAXTOSHIP,EMAILTOSHIP, CCNUM, CCTYPE, EXPDATE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				  pstmt.setString(1,name);
				  pstmt.setString(2,address);
				  pstmt.setString(3,city);
				  pstmt.setString(4,state);
				  pstmt.setString(5,country);
				  pstmt.setString(6,zip);
				  pstmt.setString(7,phone);
				  pstmt.setString(8,fax);
				  pstmt.setString(9,email);
				  pstmt.setString(10,nameToShip);
				  pstmt.setString(11,addressToShip);
				  pstmt.setString(12,cityToShip);
				  pstmt.setString(13,stateToShip);
				  pstmt.setString(14,countryToShip);
				  pstmt.setString(15,zipToShip);
				  pstmt.setString(16,phoneToShip);
				  pstmt.setString(17,faxToShip);
				  pstmt.setString(18,emailToShip);
				  pstmt.setString(19,CCNum);
				  pstmt.setString(20,CCType);
				  pstmt.setString(21,expDate);
				  pstmt.executeUpdate();
				  pstmt.close();
		     }

		     else if (request.getParameter("METHOD").equals("ECHECK"))  {
				  checkNum = cart.getCheckNum();
				  MICR = cart.getMICR();
				  typeOfCheck = cart.getTypeOfCheck();
				  DLnum = cart.getDLnum();
				  stateOfDL = cart.getStateOfDL();
		     	  PreparedStatement pstmt = con.prepareStatement("INSERT INTO CUSTOMERS (NAME,ADDRESS,CITY,STATE,COUNTRY,ZIP,PHONE,FAX,EMAIL,NAMETOSHIP,ADDRESSTOSHIP,CITYTOSHIP,STATETOSHIP,COUNTRYTOSHIP,ZIPTOSHIP,PHONETOSHIP,FAXTOSHIP,EMAILTOSHIP, CHECKNUM, MICR, TYPEOFCHECK, DLNUM, STATEOFDL) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				  pstmt.setString(1,name);
				  pstmt.setString(2,address);
				  pstmt.setString(3,city);
				  pstmt.setString(4,state);
				  pstmt.setString(5,country);
				  pstmt.setString(6,zip);
				  pstmt.setString(7,phone);
				  pstmt.setString(8,fax);
				  pstmt.setString(9,email);
				  pstmt.setString(10,nameToShip);
				  pstmt.setString(11,addressToShip);
				  pstmt.setString(12,cityToShip);
				  pstmt.setString(13,stateToShip);
				  pstmt.setString(14,countryToShip);
				  pstmt.setString(15,zipToShip);
				  pstmt.setString(16,phoneToShip);
				  pstmt.setString(17,faxToShip);
				  pstmt.setString(18,emailToShip);
				  pstmt.setString(19,checkNum);
				  pstmt.setString(20,MICR);
				  pstmt.setString(21,typeOfCheck);
				  pstmt.setString(22,DLnum);
				  pstmt.setString(23,stateOfDL);
				  pstmt.executeUpdate();
				  pstmt.close();
			  }


			  PreparedStatement pstmt2 = con.prepareStatement("SELECT CUSTOMERID FROM CUSTOMERS WHERE NAME = ? AND CCNUM = ? OR CHECKNUM = ?");
			  pstmt2.setString(1,name);
			  pstmt2.setString(2,CCNum);
			  pstmt2.setString(3,checkNum);
			  pstmt2.executeQuery();
			  ResultSet rs2 = pstmt2.getResultSet();
			  rs2.next();
			  int customerId = rs2.getInt(1);
			  rs2.close();
			  pstmt2.close();

			  PreparedStatement pstmt3 = con.prepareStatement("INSERT INTO ORDERS (CUSTOMERID,PNREF,ORDERDATE,ORDERAMOUNT,SHIPMETHOD,SHIPCHARGES,GRANDTOTAL,PAYMENTMETHOD,PAYMENTTYPE,AVSDATA,AUTHCODE,RESPMSG,RESULTCODE,DESCRIPTION) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			  pstmt3.setInt(1,customerId);
			  pstmt3.setString(2,PNRef);
			  pstmt3.setTimestamp(3,orderDate);
			  pstmt3.setDouble(4,orderAmount);
			  pstmt3.setString(5,shipMethod);
			  pstmt3.setDouble(6,shipCharges);
			  pstmt3.setDouble(7,grandTotal);
			  pstmt3.setString(8,paymentMethod);
			  pstmt3.setString(9,paymentType);
			  pstmt3.setString(10,avsData);
			  pstmt3.setString(11,authCode);
			  pstmt3.setString(12,respMsg);
			  pstmt3.setByte(13,resultCode);
			  pstmt3.setString(14,orderDesc);
			  pstmt3.executeUpdate();
			  pstmt3.close();

			  PreparedStatement pstmt4 = con.prepareStatement("SELECT INVOICE FROM ORDERS WHERE CUSTOMERID = ? AND ORDERDATE = ?");
		  	  pstmt4.setInt(1,customerId);
		  	  pstmt4.setTimestamp(2,orderDate);
			  pstmt4.executeQuery();
			  ResultSet rs4 = pstmt4.getResultSet();
			  rs4.next();
			  invoiceNum = rs4.getInt(1);
			  rs4.close();
  			  pstmt4.close();
  			  System.out.println("invoiceNum = "+invoiceNum);

  			  PreparedStatement pstmt5 = con.prepareStatement("INSERT INTO ORDERDETAILS (INVOICE,PRODUCTID,QUANTITY) VALUES (?,?,?)");
			  Enumeration e = cart.getItems();
			  System.out.println(e);
			  while (e.hasMoreElements()) {
					ShoppingCartItem item = (ShoppingCartItem)e.nextElement();
					ProductDetails prod = (ProductDetails)item.getItem();
					String productId = prod.getProdId();
					int quantity = item.getQuantity();
					pstmt5.clearParameters();
					pstmt5.setInt(1,invoiceNum);
					pstmt5.setString(2,productId);
					pstmt5.setInt(3,quantity);
					pstmt5.executeUpdate();
					System.out.println("executing "+pstmt5);
			  }
  			  pstmt5.close();


  		     con.commit();


  		     Enumeration enum = request.getParameterNames();
			  while (enum.hasMoreElements()) {
				 String name = (String)enum.nextElement();
				 String value = request.getParameter(name).trim();
				 //out.println("<br><input name=\""+name+"\" type=\"hidden\" value=\""+value+"\">								"");
												//"param = "+name+ " value = "+value);
			  }


  		     displayOrderDetails(out,request);
  		     System.out.println("Order submission succesful");
  		     session.invalidate();

		  }
	 	  catch(Exception e) {
			  e.printStackTrace();
			  try {
				  con.rollback();
			  }
			  catch (SQLException sqle) {
		     	  out.println("Order confirmation failed. Please contact technical support.");
		     }
        }
        finally {
			  try {
				  if (con!=null) {
					  con.close();
				  }
			  }
			  catch (SQLException sqle) {}
		  }

        out.println("<br><font face=verdana size=-1>Thank you for using BigByte service. We are looking forward for your next business.");
        out.println("<br><br><a href=\"../index.html\">Back to Home</a>");
        out.println("</body></html>");
        out.close();
    }




    public String getServletInfo() {
        return "The Post Payment servlet takes the payment information and save them to the database from PayFlowLink";

    }


    public void displayOrderDetails(PrintWriter out, HttpServletRequest request) {
		 Cashier cashier = new Cashier(cart);
		 int num = cart.getNumberOfItems();
		 out.println("<br><table cellspacing='1' cellpadding='3' border='1' BGCOLOR=#EDECE5>");
		 out.println("<caption><font face='verdana' size='-1'><b>REPAIR ORDER RECEIPT</b></caption>");
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
		 out.println("<table width=600 border=0 cellpadding=2 cellspacing=2>");

		 out.println("<tr><td colspan=2><font face=verdana size=-1>Your order has been issued with an invoice/RMA (Return Materials Authorization) #.<td></tr>");
	    out.println("<tr><td><font color=navy face=verdana size=-1><b>Invoice #: </b></font><font face=verdana size=-1>"+invoiceNum+"</td></tr>");
		 out.println("<tr><td><font color=navy face=verdana size=-1><b>Transaction Ref: </b></font><font face=verdana size=-1>"+PNRef+"</td></tr>");
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
       method = request.getParameter("METHOD").trim();
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

		 out.println("<tr><td colspan=2><font color=navy face=verdana size=-1>Your order will be processed once the returned "+
		         					"material(s) arrive at our repair center. Our Customer representative will "+
		         					"then contact you to notify you of our target completion date, "+
        					         "and if any, discrepancies. </td></tr>");
       out.println("<tr><td colspan=2 align=center><font face=verdana size=-1>"+
                     "<br><b>Please print this page for your own record."+
        					"<br>This page will refresh itself in 60 seconds.</b></td></tr>");
		 out.println("</table>");

	}

	public String displayCC(String CCNum) {
			 StringBuffer num = new StringBuffer(CCNum);
			 num.replace(4,12,"XXXXXXXX");
			 String number = num.toString();
			 return number;
   }

}
