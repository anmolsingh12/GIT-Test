import java.sql.*;
  import java.util.*;

  import sailpoint.object.*;
  import sailpoint.object.ProvisioningPlan.AccountRequest;
  import sailpoint.object.ProvisioningPlan.AttributeRequest;
  List<AccountRequest> account = plan.getAccountRequests();
  ProvisioningPlan.AccountRequest accReq = account.get(0);
  //AttributeRequest attrReq = accReq.getAttributeRequest("entitlement_value");
  List<AttributeRequest> accounts = accReq.getAttributeRequests();
  PreparedStatement pstmt = null;
  Identity id = plan.getIdentity();
  String name = id.getName();
  String ent = null;
  try {
    
    for (AttributeRequest attrReq:accounts)
    {
      if(attrReq.getOperation().toString().equals("Set") && attrReq.getName().toString().equals("college"))
    {
        String colgName = attrReq.getValue().toString();
        System.out.println(colgName);

        pstmt = connection.prepareStatement("UPDATE app_account SET college = ? WHERE employee_id = ?");
        pstmt.setString(1, colgName);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
    }

     if(attrReq.getOperation().toString().equals("Set") && attrReq.getName().toString().equals("batch"))
     {
        String batchYear = attrReq.getValue().toString();
        System.out.println(batchYear);

        pstmt = connection.prepareStatement("UPDATE app_account SET batch = ? WHERE employee_id = ?");
        pstmt.setString(1, batchYear);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
     }

    if (attrReq.getOperation().toString().equals("Remove")) {
      System.out.println("Please remove the Entitlement from this user");
      pstmt = connection.prepareStatement("delete from app_access where employee_id=?;");
      pstmt.setString(1,name);
      pstmt.executeUpdate();
      System.out.println("The entitlement has been deleted");
    }
    if (attrReq.getOperation().toString().equals("Add")) {

      System.out.println("Please add the requested entitlement to this user");
      ent = attrReq.getValue().toString();
      System.out.println(ent);
      pstmt = connection.prepareStatement("select employee_id,entitlement_value from app_access where employee_id=?");
      pstmt.setString(1,name);
      ResultSet rs = pstmt.executeQuery();
      if(!rs.next())
      {
        System.out.println("Starting to insert the values in the JDBC table");
        pstmt = connection.prepareStatement("insert into app_access(employee_id,entitlement_value) values (?,?);");
        pstmt.setString(1,name);

        pstmt.setString(2,ent);

        pstmt.executeUpdate();
      }

    }
    }
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
