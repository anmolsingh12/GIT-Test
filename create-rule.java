import java.sql.*;
import sailpoint.object.Identity;

                PreparedStatement pstmt = null;
		Identity id = plan.getIdentity();
                String name = id.getName();


		try {

		pstmt = connection.prepareStatement("INSERT INTO app_account VALUES (?, ?, ?, ?,?)");
		pstmt.setString(1,name);
		pstmt.setString(2,id.getDisplayName());
                pstmt.setString(3,id.getAttribute("college_name"));
                pstmt.setString(4,id.getAttribute("batch_year"));
                pstmt.setString(5,id.isInactive().toString());
		 
		pstmt.executeUpdate();

		}
		catch(Exception se) {
		   se.printStackTrace();
		}
		finally {

		         if(pstmt!=null)
                         {
		            pstmt.close();
                         }
		      }
