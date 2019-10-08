import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchRowe")
public class SearchRowe extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchRowe() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Events Found";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnectionRowe.getDBConnection();
         connection = DBConnectionRowe.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM MyTableRoweTechEx";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM MyTableRoweTechEx WHERE ENAME LIKE ?";
            String ename = keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, ename);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            int id = rs.getInt("id");
            String ename = rs.getString("ename").trim();
            String date = rs.getString("date").trim();
            String notes = rs.getString("notes").trim();
            String address = rs.getString("address").trim();

            if (keyword.isEmpty() || ename.contains(keyword)) {
               out.println("ID: " + id + "<br>");
               out.println("Event Name: " + ename + "<br>");
               out.println("Date: " + date + "<br>");
               out.println("Notes: " + notes + "<br>");
               out.println("Address: " + address + ";<br><br>");
            }
         }
         out.println("<a href=/webproject-techex-rowe/search_rowe.html>Search Again</a> <br>");
         out.println("<a href=/webproject-techex-rowe/insert_rowe.html>Insert an Event</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
