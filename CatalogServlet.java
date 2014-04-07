/**
 * Title:        CatalogServlet.java
 * Description:  Servlet that provide catalog product search
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

public class CatalogServlet extends HttpServlet {

    int mfgId;
    int typeId;
    int categoryId;
    int sizeId;
    int interfaceId;

    public void doGet (HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {

        HttpSession session = req.getSession(true);
        ShoppingCart cart = (ShoppingCart)session.getAttribute(session.getId());
        if (cart==null) {
			  cart= new ShoppingCart();
			  session.setAttribute(session.getId(),cart);
	  	  }

        res.setContentType("text/html");
        ServletOutputStream out = res.getOutputStream();
        out.println("<html>");
        out.println("<head><title>Catalog Servlet</title></head>");
        out.println("<body>");
        out.println("<b><a name='top'><font face='verdana' size='-1' color= 'navy'>"+
		  	    "Search the supported products that you would like to repair:</b></font></a><br><br>");


        typeId = Integer.parseInt(req.getParameter("typeId").trim());
        mfgId = 0;
		  categoryId = 0;
		  sizeId = 0;
		  interfaceId = 0;

        createForm(out, mfgId, typeId, categoryId, sizeId, interfaceId);

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