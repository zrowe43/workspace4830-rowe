
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertRowe")
public class InsertRowe extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertRowe() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String ename = request.getParameter("ename");
      String date = request.getParameter("date");
      String notes = request.getParameter("notes");
      String address = request.getParameter("address");

      Connection connection = null;
      String insertSql = " INSERT INTO MyTableRoweTechEx (id, ENAME, DATE, NOTES, ADDRESS) values (default, ?, ?, ?, ?)";

      try {
         DBConnectionRowe.getDBConnection();
         connection = DBConnectionRowe.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, ename);
         preparedStmt.setString(2, date);
         preparedStmt.setString(3, notes);
         preparedStmt.setString(4, address);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to Event List";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Event Title</b>: " + ename + "\n" + //
            "  <li><b>Date</b>: " + date + "\n" + //
            "  <li><b>Notes</b>: " + notes + "\n" + //
            "  <li><b>Address</b>: " + address + "\n" + //

            "</ul>\n");

      out.println("<a href=/webproject-techex-rowe/insert_rowe.html>Insert an Event</a> <br>");
      out.println("<a href=/webproject-techex-rowe/search_rowe.html>Search Events</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
