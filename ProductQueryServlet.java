/**
 * Title:        ProductQueryServlet.java
 * Description:  Servlet that provide data access to retreive product info from the database
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
import java.text.NumberFormat;

public class ProductQueryServlet extends HttpServlet {

    int mfgId;
    int typeId;
    int categoryId;
    int sizeId;
    int interfaceId;

    public void doPost (HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {

        HttpSession session = req.getSession(true);
        ShoppingCart cart = (ShoppingCart)session.getAttribute(session.getId());
System.out.println("ProductQueryServlet");
System.out.println("cart = "+cart);
System.out.println("sessionId = "+session.getId());
        if (cart==null) {
			  cart= new ShoppingCart();
			  session.setAttribute(session.getId(),cart);
	  	  }

        res.setContentType("text/html");
        ServletOutputStream out = res.getOutputStream();
        out.println("<html>");
        out.println("<head><title>Product Query Servlet</title></head>");
        out.println("<body>");
        out.println("<b><a name='top'><font face='verdana' size='-1' color= 'navy'>"+
		  	    "Search the supported products that you would like to repair:</b></font></a><br><br>");


        mfgId = Integer.parseInt(req.getParameter("mfgId").trim());
		  typeId = Integer.parseInt(req.getParameter("typeId").trim());
		  categoryId = Integer.parseInt(req.getParameter("categoryId").trim());
		  sizeId = Integer.parseInt(req.getParameter("sizeId").trim());
		  interfaceId = Integer.parseInt(req.getParameter("interfaceId").trim());

        createForm(out, mfgId, typeId, categoryId, sizeId, interfaceId);


        String classname = "sun.jdbc.odbc.JdbcOdbcDriver";
		  String url = "jdbc:odbc:bigbyte";
		  String login = "adji";
		  String password = "noni";

        String whereMfg;
		  if (mfgId==0) {
		    whereMfg = "";
		  }
		  else {
		    whereMfg = " and Product.mfgId = " + mfgId ;
        }

        String whereType;
        if (typeId==0) {
          whereType = "";
        }
        else {
			 whereType = " and Product.typeId = " + typeId;
        }

		  String whereCategory;
        if (categoryId==0) {
          whereCategory = "";
        }
        else {
			 whereCategory = " and Product.categoryId = " + categoryId;
        }

		  String whereSize;
        if (sizeId==0) {
          whereSize = "";
        }
        else {
			 whereSize = " and Product.sizeId = " + sizeId;
        }

		  String whereInterface;
        if (interfaceId==0) {
          whereInterface = "";
        }
        else {
			 whereInterface = " and Product.interfaceId = " + interfaceId;
        }

        String sql = "select Product.ProductId, Type.Name, Product.Description, Manufacturer.Name, Product.Manufacturer_PN, Product.Repair_Price, Product.Weight from Product, Type, Manufacturer "
        					 +"where Product.TypeId = Type.TypeId and Product.MfgId = Manufacturer.ManufacturerId"
        					 + whereMfg + whereType + whereSize + whereCategory + whereInterface + " order by Product.ProductId";


        try {
            // Load the JDBC-ODBC driver
            Class.forName(classname);

            Connection con = DriverManager.getConnection(url,
                login, password);
            Statement stmt = con.createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery(sql);

				out.println("<font face='verdana' size='-1' color ='navy'>"+
				  "<b>Supported Products:</b></font><br><br>");
				out.println("<form method='post' action='../servlet/ShoppingCartServlet' target='_self'>");
				out.println("<table cellspacing='1' cellpadding='3' border='1' BGCOLOR=#EDECE5>");

				out.println("<tr><th><font face='verdana' size='-2'>Select"+
					 "</font></th><th><font face='verdana' size='-2'>Type"+
					 "</font></th><th><font face='verdana' size='-2'>Description"+
					 "</font></th><th><font face='verdana' size='-2'>Manufacturer"+
					 "</font></th><th><font face='verdana' size='-2'>Model Number"+
					 "</font></th><th><font face='verdana' size='-2'>Repair Price" +
					 "</font></th><th><font face='verdana' size='-2'>Weight<br>(lbs)" +
					 "</font></th></tr>");


				int index = 0;

				while(rs.next()) {
					 index++;
					 String v1 = rs.getString(1).trim();
					 String v2 = rs.getString(2).trim();
					 String v3 = rs.getString(3).trim();
					 String v4 = rs.getString(4).trim();
					 String v5 = rs.getString(5).trim();
					 float v6 = rs.getFloat(6);
					 int v7 = rs.getInt(7);

					 String price = format(v6);
					 String input = "<input type='checkbox' name='prodId' value='"+v1+"'>";
					 if (v6==0) {
						 price = "call";
					    input = "&nbsp;";
				 	 }

				 	 out.println("<tr><td bgcolor='#E6E6FF'><center>"+input+"</center></td>" +
						"<td bgcolor='#E6E6FF'><input type='hidden' name='"+v1+"type' value='"+v2+"'><font face='verdana' size='-2'>"+v2+"</font></td>" +
						"<td bgcolor='#E6E6FF'><input type='hidden' name='"+v1+"desc' value='"+v3+"'><font face='verdana' size='-2'>" + v3 + "</font></td>" +
						"<td bgcolor='#E6E6FF'><input type='hidden' name='"+v1+"mfg' value='"+v4+"'><font face='verdana' size='-2'>" + v4 + "</font></td>" +
						"<td bgcolor='#E6E6FF'><input type='hidden' name='"+v1+"mfgPN' value='"+v5+"'><font face='verdana' size='-2'>" + v5 + "</font></td>" +
						"<td align='right' bgcolor='#E6E6FF'><input type='hidden' name='"+v1+"price' value='"+v6+"'><font face='verdana' size='-2'>" + price + "</font></td>" +
						"<td align='center' bgcolor='#E6E6FF'><input type='hidden' name='"+v1+"weight' value='"+v7+"'><font face='verdana' size='-2'>" + v7 + "</font></td>" +
						"</tr>");
				}

				if(index==0) {
					out.println("<tr><td colspan=7><font face='verdana' size='-1'>No supported products found. Please refine your search.<br>");
					out.println("If you still did not find the product you are looking for, "+
									"<br>please <a href='../secondary/contact.html' target='_top'>contact us</a> or call us at 1-800-536-6425."+
									"<br>BigByte Sales Representative will be happy to assist you.</font></td></tr>");
			   }




				out.println("</table>");
				out.println("<br><input type='submit' name='Action' value='Add to repair order list'>");
				out.println("</form>");


				rs.close();
            stmt.close();
            con.close();

        } catch( Exception e ) {
            e.printStackTrace();
        }


        out.println("<a href='#top'><font face='verdana' size='-1'>Top of the page</a></font>");

        out.println("</body></html>");
        out.close();
    }

    public String format(float f) {
		 NumberFormat nf = NumberFormat.getCurrencyInstance();
		 return nf.format(f);
 	 }

    public void createForm(ServletOutputStream out, int mfgId, int typeId, int categoryId, int sizeId, int interfaceId) throws IOException {
		 out.println("<form method='post' action='../servlet/ProductQueryServlet' target='_self'>");
		 out.println("<table border='0' cellpadding='0'><tr><th align='right'><font face='verdana' size='-1'>Product Type:&nbsp;</th>");
 	 	 out.println("<td><select name='typeId' size='1'>"+
 	 	 					"<option value='"+ typeId +"' selected>"+ getType(typeId) +"</option>"+
 	 	 				 	"<option value='0'>All</option>"+
      					"<option value='1'>Tape Drive</option>"+
      					"<option value='2'>Floppy Drive</option>"+
      					"<option value='3'>Zip Drive</option>"+
      					"<option value='4'>Jaz Drive</option>"+
      					"<option value='5'>DVD RAM</option>"+
      					"<option value='6'>DVD ROM</option>"+
      					"<option value='7'>CD ROM</option>"+
      				 "</select></td>");
       out.println("<th align='right'><font face='verdana' size='-1'>&nbsp;&nbsp;Category:&nbsp;</th>");
		 out.println("<td><select name='categoryId' size='1'>"+
							"<option value='"+ categoryId +"' selected>"+ getCategory(categoryId) +"</option>"+
							"<option value='0'>All</option>"+
							"<option value='1'>DAT</option>"+
							"<option value='2'>DLT</option>"+
							"<option value='3'>8MM</option>"+
							"<option value='4'>QIC</option>"+
						 "</select></td>");
		 out.println("<th align='right'><font face='verdana' size='-1'>&nbsp;&nbsp;Interface:&nbsp;</th>");
		 out.println("<td><select name='interfaceId' size='1'>"+
		       			"<option value='"+ interfaceId +"' selected>"+ getInterface(interfaceId) +" </option>"+
		 	 	 		 	"<option value='0'>All</option>"+
		 	 	 		 	"<option value='1'>SCSI</option>"+
		      			"<option value='2'>IDE</option>"+
		      			"<option value='3'>ATAPI</option>"+
		      			"<option value='4'>USB</option>"+
		      		"</select></td></tr>");

		 out.println("<tr><th align='right'><font face='verdana' size='-1'>Manufacturer:&nbsp;</th>");
       out.println("<td><select name='mfgId' size='1'>"+
       					"<option value='"+ mfgId +"' selected>"+ getMfg(mfgId) +"</option>"+
 	 	 				 	"<option value='0'>All</option>"+
 	 	 				 	"<option value='1'>Apple</option>"+
      					"<option value='2'>Compaq</option>"+
      					"<option value='3'>Citizen</option>"+
      					"<option value='4'>Conner</option>"+
      					"<option value='5'>Exabyte</option>"+
      					"<option value='6'>Iomega</option>"+
      					"<option value='7'>Panasonic</option>"+
      					"<option value='8'>Mitsubishi</option>"+
 	 	 				 	"<option value='9'>Mitsumi</option>"+
      					"<option value='10'>NEC</option>"+
      					"<option value='11'>Quantum</option>"+
      					"<option value='12'>Sony</option>"+
      					"<option value='13'>Seagate</option>"+
      					"<option value='14'>Shugart</option>"+
      					"<option value='15'>TEAC</option>"+
      					"<option value='16'>Toshiba</option>"+
      					"<option value='17'>Wangdat</option>"+
      					"<option value='18'>Wangtek</option>"+
      				 "</select></td>");

      out.println("<th align='right'><font face='verdana' size='-1'>Size:&nbsp;</th>");

	   out.println("<td><select name='sizeId' size='1'>"+
							"<option value='"+ sizeId +"' selected>"+ getSize(sizeId) +"</option>"+
							"<option value='0'>All</option>"+
							"<option value='1'>3.5\"</option>"+
							"<option value='2'>5.25\"</option>"+
							"<option value='3'>8\"</option>"+
						"</select></td></tr></table>");
		out.println("<br><input type='submit' value='Search'>");
      out.println("</form>");


 	 }

 	 public String getType(int id) {
		 String type;
		 switch(id) {
			 case 1:
			 	type = "Tape Drive";
			 	break;
			 case 2:
			 	type = "Floppy Drive";
			 	break;
			 case 3:
			 	type = "Zip Drive";
			 	break;
			 case 4:
			 	type = "Jaz Drive";
			 	break;
			 case 5:
			 	type = "DVD RAM";
			 	break;
			 case 6:
			 	type = "DVD ROM";
			 	break;
			 case 7:
			 	type = "CD ROM";
			 	break;
			 default:
			   type = "All";
			   break;
		 }
		 return type;
 	 }


 	 public String getInterface(int id) {
		 String interfaceName;
		 switch(id) {
			 case 1:
				interfaceName = "SCSI";
				break;
			 case 2:
				interfaceName = "IDE";
				break;
			 case 3:
				interfaceName = "ATAPI";
				break;
			 case 4:
				interfaceName = "USB";
				break;
			 default:
			   interfaceName = "All";
			   break;
		 }
		 return interfaceName;
 	 }

 	 public String getSize(int id) {
		 String size;
		 switch(id) {
			 case 1:
				size = "3.5\"";
				break;
			 case 2:
				size = "5.25\"";
				break;
			 case 3:
				size = "8\"";
				break;
			 default:
				size = "All";
			   break;
		 }
		 return size;
 	 }

 	 public String getCategory(int id) {
		 String category;
		 switch(id) {
			 case 1:
				category = "DAT";
				break;
			 case 2:
				category = "DLT";
				break;
			 case 3:
				category = "8MM";
				break;
			 case 4:
				category = "QIC";
				break;
			 default:
				category = "All";
				break;
		 }
		 return category;
 	 }

	 public String getMfg(int id) {
	 		 String mfg;
	 		 switch(id) {
	 			 case 1:
	 			 	mfg = "Apple";
	 			 	break;
	 			 case 2:
	 			 	mfg = "Compaq";
	 			 	break;
	 			 case 3:
	 			 	mfg = "Citizen";
	 			 	break;
	 			 case 4:
	 			 	mfg = "Conner";
	 			 	break;
	 			 case 5:
	 			 	mfg = "Exabyte";
	 			 	break;
	 			 case 6:
	 			 	mfg = "Iomega";
	 			 	break;
	 			 case 7:
	 			 	mfg = "Panasonic";
	 			 	break;
	 			 case 8:
	 			 	mfg = "Mitsubishi";
	 			 	break;
	 			 case 9:
	 			 	mfg = "Mitsumi";
	 			 	break;
	 			 case 10:
	 			 	mfg = "NEC";
	 			 	break;
	 			 case 11:
	 			 	mfg = "Quantum";
	 			 	break;
	 			 case 12:
	 			 	mfg = "Sony";
	 			 	break;
	 			 case 13:
	 			 	mfg = "Seagate";
	 			 	break;
	 			 case 14:
	 			 	mfg = "Shugart";
	 			 	break;
	 			 case 15:
	 			 	mfg = "TEAC";
	 			 	break;
	 			 case 16:
	 			 	mfg = "Toshiba";
	 			 	break;
	 			 case 17:
	 			 	mfg = "Wangdat";
	 			 	break;
	 			 case 18:
	 			 	mfg = "Wangtek";
	 			 	break;
	 			 default:
	 			   mfg = "All";
	 			   break;
	 		 }
	 		 return mfg;
 	 }
}