package tna.tnaTitle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class TnaTitleDBService {
	Connection con;
	
	
	public TnaTitleDBService()
	{
		DBConnectionDTO conDTO = new DBConnectionDTO();
		con=conDTO.getConnection();
	}
	
	public void closeConnection()
	{
		try {
			con.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
public int getTotalPages(int limit)
	{
		String query="select count(*) from tna_title";
	    int totalRecords=0;
	    int totalPages=0;
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	totalRecords= rs.getInt(1);
	    }
	    stmt.close();
	    rs.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		totalPages=totalRecords/limit;
		if(totalRecords%limit!=0)
		{
			totalPages+=1;
		}
		return totalPages;
	}
	
	//pagination
	public int getTotalPages(TnaTitle tnaTitle,int limit)
	{
		String query=getDynamicQuery2(tnaTitle);
		int totalRecords=0;
	    int totalPages=0;
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	totalRecords= rs.getInt(1);
	    }
	    stmt.close();
	    rs.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		totalPages=totalRecords/limit;
		if(totalRecords%limit!=0)
		{
			totalPages+=1;
		}
		return totalPages;
	}
	
	
	public int getTnaTitleId(TnaTitle tnaTitle)
	{
		int id=0;
		String query="select id from tna_title";
String whereClause = " where "+ "prgCat=? and prgTitle=? and instituteName=? and mobileNo=? and choice=? and submitted=? and updateBy=? and updateDt=? and updateTime=? and name=? and email=? and state=?";
	    query+=whereClause;
		System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, tnaTitle.getPrgCat());
pstmt.setString(2, tnaTitle.getPrgTitle());
pstmt.setString(3, tnaTitle.getInstituteName());
pstmt.setString(4, tnaTitle.getMobileNo());
pstmt.setString(5, tnaTitle.getChoice());
pstmt.setString(6, tnaTitle.getSubmitted());
pstmt.setString(7, tnaTitle.getUpdateBy());
pstmt.setDate(8, java.sql.Date.valueOf(DateService.getDTSYYYMMDDFormat(tnaTitle.getUpdateDt())));
pstmt.setString(9, tnaTitle.getUpdateTime());
pstmt.setString(10, tnaTitle.getName());
pstmt.setString(11, tnaTitle.getEmail());
pstmt.setString(12, tnaTitle.getState());
	    ResultSet rs = pstmt.executeQuery();
	    if(rs.next()) {
	       	id = rs.getInt("id");
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return id;
	}
	public void createTnaTitle(TnaTitle tnaTitle)
	{
		
String query="INSERT INTO tna_title(prgCat,prgTitle,instituteName,mobileNo,choice,submitted,updateBy,updateDt,updateTime,name,email,state) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
	
    System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, tnaTitle.getPrgCat());
pstmt.setString(2, tnaTitle.getPrgTitle());
pstmt.setString(3, tnaTitle.getInstituteName());
pstmt.setString(4, tnaTitle.getMobileNo());
pstmt.setString(5, tnaTitle.getChoice());
pstmt.setString(6, tnaTitle.getSubmitted());
pstmt.setString(7, tnaTitle.getUpdateBy());
pstmt.setDate(8, java.sql.Date.valueOf(DateService.getDTSYYYMMDDFormat(tnaTitle.getUpdateDt())));
pstmt.setString(9, tnaTitle.getUpdateTime());
pstmt.setString(10, tnaTitle.getName());
pstmt.setString(11, tnaTitle.getEmail());
pstmt.setString(12, tnaTitle.getState());
	    int x = pstmt.executeUpdate();
	    }
	    catch (Exception e) {
	  
  	System.out.println(e);
		}
		int id = getTnaTitleId(tnaTitle);
		tnaTitle.setId(id);
	}
	public void updateTnaTitle(TnaTitle tnaTitle)
	{
		
String query="update tna_title set "+"prgCat=?,prgTitle=?,instituteName=?,mobileNo=?,choice=?,submitted=?,updateBy=?,updateDt=?,updateTime=?,name=?,email=?,state=? where id=?";
	   
 System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, tnaTitle.getPrgCat());
pstmt.setString(2, tnaTitle.getPrgTitle());
pstmt.setString(3, tnaTitle.getInstituteName());
pstmt.setString(4, tnaTitle.getMobileNo());
pstmt.setString(5, tnaTitle.getChoice());
pstmt.setString(6, tnaTitle.getSubmitted());
pstmt.setString(7, tnaTitle.getUpdateBy());
pstmt.setDate(8, java.sql.Date.valueOf(DateService.getDTSYYYMMDDFormat(tnaTitle.getUpdateDt())));
pstmt.setString(9, tnaTitle.getUpdateTime());
pstmt.setString(10, tnaTitle.getName());
pstmt.setString(11, tnaTitle.getEmail());
pstmt.setString(12, tnaTitle.getState());
pstmt.setInt(13, tnaTitle.getId());
	    int x = pstmt.executeUpdate();
	    }
	    catch (Exception e) {
	    	System.out.println(e);
		}
		
	}
	public String getValue(String code,String table) {
		
		String value="";
		String query="select value from "+table+" where code='"+code+"'";
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	
	    	value=rs.getString("value");
	    }
		}
		catch (Exception e) {
			System.out.println(e);
		}
	    return value;
	}
	
	public TnaTitle getTnaTitle(int id)
	{
		TnaTitle tnaTitle =new TnaTitle();
		String query="select * from tna_title where id="+id;
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	
	
tnaTitle.setId(rs.getInt("id")==0?0:rs.getInt("id"));
tnaTitle.setPrgCat(rs.getString("prgCat")==null?"":rs.getString("prgCat"));
tnaTitle.setPrgCatValue(rs.getString("prgCat")==null?"":getValue(rs.getString("prgCat"),"dd_train_cat"));
tnaTitle.setPrgTitle(rs.getString("prgTitle")==null?"":rs.getString("prgTitle"));
tnaTitle.setInstituteName(rs.getString("instituteName")==null?"":rs.getString("instituteName"));
tnaTitle.setMobileNo(rs.getString("mobileNo")==null?"":rs.getString("mobileNo"));
tnaTitle.setChoice(rs.getString("choice")==null?"":rs.getString("choice"));
tnaTitle.setChoiceValue(rs.getString("choice")==null?"":getValue(rs.getString("choice"),"dd_submitted_choice"));
tnaTitle.setSubmitted(rs.getString("submitted")==null?"":rs.getString("submitted"));
tnaTitle.setSubmittedValue(rs.getString("submitted")==null?"":getValue(rs.getString("submitted"),"dd_answer"));
tnaTitle.setUpdateBy(rs.getString("updateBy")==null?"":rs.getString("updateBy"));
tnaTitle.setUpdateDt(DateService.getDTDYYYYMMDDFormat(rs.getDate("updateDt")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updateDt")));
tnaTitle.setUpdateTime(rs.getString("updateTime")==null?"":rs.getString("updateTime"));
tnaTitle.setName(rs.getString("name")==null?"":rs.getString("name"));
tnaTitle.setEmail(rs.getString("email")==null?"":rs.getString("email"));
tnaTitle.setState(rs.getString("state")==null?"":rs.getString("state"));
tnaTitle.setStateValue(rs.getString("state")==null?"":getValue(rs.getString("state"),"dd_state"));
	    	
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return tnaTitle;
	}
	
	
	public ArrayList<TnaTitle> getTnaTitleList()
	{
		ArrayList<TnaTitle> tnaTitleList =new ArrayList<TnaTitle>();
		String query="select * from tna_title";
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
	    	TnaTitle tnaTitle =new TnaTitle();
tnaTitle.setId(rs.getInt("id")==0?0:rs.getInt("id"));
tnaTitle.setPrgCat(rs.getString("prgCat")==null?"":rs.getString("prgCat"));
tnaTitle.setPrgCatValue(rs.getString("prgCat")==null?"":getValue(rs.getString("prgCat"),"dd_train_cat"));
tnaTitle.setPrgTitle(rs.getString("prgTitle")==null?"":rs.getString("prgTitle"));
tnaTitle.setInstituteName(rs.getString("instituteName")==null?"":rs.getString("instituteName"));
tnaTitle.setMobileNo(rs.getString("mobileNo")==null?"":rs.getString("mobileNo"));
tnaTitle.setChoice(rs.getString("choice")==null?"":rs.getString("choice"));
tnaTitle.setChoiceValue(rs.getString("choice")==null?"":getValue(rs.getString("choice"),"dd_submitted_choice"));
tnaTitle.setSubmitted(rs.getString("submitted")==null?"":rs.getString("submitted"));
tnaTitle.setSubmittedValue(rs.getString("submitted")==null?"":getValue(rs.getString("submitted"),"dd_answer"));
tnaTitle.setUpdateBy(rs.getString("updateBy")==null?"":rs.getString("updateBy"));
tnaTitle.setUpdateDt(DateService.getDTDYYYYMMDDFormat(rs.getDate("updateDt")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updateDt")));
tnaTitle.setUpdateTime(rs.getString("updateTime")==null?"":rs.getString("updateTime"));
tnaTitle.setName(rs.getString("name")==null?"":rs.getString("name"));
tnaTitle.setEmail(rs.getString("email")==null?"":rs.getString("email"));
tnaTitle.setState(rs.getString("state")==null?"":rs.getString("state"));
tnaTitle.setStateValue(rs.getString("state")==null?"":getValue(rs.getString("state"),"dd_state"));
	    	tnaTitleList.add(tnaTitle);
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return tnaTitleList;
	}
	
	public ArrayList<TnaTitle> getTnaTitleList(int pageNo,int limit)
	{
		ArrayList<TnaTitle> tnaTitleList =new ArrayList<TnaTitle>();
String query="select * from tna_title limit "+limit +" offset "+limit*(pageNo-1);
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
	    	TnaTitle tnaTitle =new TnaTitle();
tnaTitle.setId(rs.getInt("id")==0?0:rs.getInt("id"));
tnaTitle.setPrgCat(rs.getString("prgCat")==null?"":rs.getString("prgCat"));
tnaTitle.setPrgCatValue(rs.getString("prgCat")==null?"":getValue(rs.getString("prgCat"),"dd_train_cat"));
tnaTitle.setPrgTitle(rs.getString("prgTitle")==null?"":rs.getString("prgTitle"));
tnaTitle.setInstituteName(rs.getString("instituteName")==null?"":rs.getString("instituteName"));
tnaTitle.setMobileNo(rs.getString("mobileNo")==null?"":rs.getString("mobileNo"));
tnaTitle.setChoice(rs.getString("choice")==null?"":rs.getString("choice"));
tnaTitle.setChoiceValue(rs.getString("choice")==null?"":getValue(rs.getString("choice"),"dd_submitted_choice"));
tnaTitle.setSubmitted(rs.getString("submitted")==null?"":rs.getString("submitted"));
tnaTitle.setSubmittedValue(rs.getString("submitted")==null?"":getValue(rs.getString("submitted"),"dd_answer"));
tnaTitle.setUpdateBy(rs.getString("updateBy")==null?"":rs.getString("updateBy"));
tnaTitle.setUpdateDt(DateService.getDTDYYYYMMDDFormat(rs.getDate("updateDt")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updateDt")));
tnaTitle.setUpdateTime(rs.getString("updateTime")==null?"":rs.getString("updateTime"));
tnaTitle.setName(rs.getString("name")==null?"":rs.getString("name"));
tnaTitle.setEmail(rs.getString("email")==null?"":rs.getString("email"));
tnaTitle.setState(rs.getString("state")==null?"":rs.getString("state"));
tnaTitle.setStateValue(rs.getString("state")==null?"":getValue(rs.getString("state"),"dd_state"));
	    	tnaTitleList.add(tnaTitle);
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return tnaTitleList;
	}
	
	public void deleteTnaTitle(int id) {
		
			String query="delete from tna_title where id="+id;
		    System.out.println(query);
				
			
		    try {
			Statement stmt = con.createStatement();
		    int x = stmt.executeUpdate(query);
		    }
		    catch (Exception e) {
		    	System.out.println(e);
			}
		
	}
	public int getTnaCatId()
	{
		int catId=0;
		/* String query="select * from tna_title where id="+id; */
		String query="SELECT id FROM dd_train_cat WHERE flag = 'yes'";

	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	
	
	    	catId=(rs.getInt("id")==0?0:rs.getInt("id"));

	    	
	    }
		rs.close();
		stmt.close();
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return catId;
	}
	public int getTnaMaxOrder() {
	    int maxOrder = 0;
	    String query = "SELECT MAX(orderNo) AS maxOrder FROM dd_train_cat WHERE flag = 'yes'";

	    System.out.println(query); // Log the query for debugging
	    try (Statement stmt = con.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {

	        if (rs.next()) { // Check if a result is available
	            maxOrder = rs.getInt("maxOrder"); // Use column alias for clarity
	        }

	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	        e.printStackTrace();
	    }

	    return maxOrder;
	}


	public int getNextTnaCatId(int currentId)
	{
		int catId=0;
		/* String query="select * from tna_title where id="+id; */
		String query="SELECT id FROM dd_train_cat WHERE flag = 'yes' and id>"+currentId;

	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	
	
	    	catId=(rs.getInt("id")==0?0:rs.getInt("id"));
	    	

	    	
	    }
		rs.close();
		stmt.close();
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return  catId;
	}
	// Assuming dd_code_table has columns: id and code
		public String getValueById(int id) {
		    String value = null;
		    String query = "SELECT value FROM dd_train_cat WHERE orderNo = ?";

		    try (PreparedStatement stmt = con.prepareStatement(query)) {
		        stmt.setInt(1, id);  // Set the id parameter in the query
		        ResultSet rs = stmt.executeQuery();
		        
		        if (rs.next()) {
		            value = rs.getString("value");  // Fetch the code
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();  // Handle exception
		    }

		    return value;  // Return the code or null if not found
		}
		// Assuming dd_code_table has columns: id and code
		public String getValueByOrderNo(int orderNo) {
				    String value = null;
				    String query = "SELECT value FROM dd_train_cat WHERE orderNo = ?";

				    try (PreparedStatement stmt = con.prepareStatement(query)) {
				        stmt.setInt(1, orderNo);  // Set the id parameter in the query
				        ResultSet rs = stmt.executeQuery();
				        
				        if (rs.next()) {
				            value = rs.getString("value");  // Fetch the code
				        }
				    } catch (SQLException e) {
				        e.printStackTrace();  // Handle exception
				    }

				    return value;  // Return the code or null if not found
				}
	// Assuming dd_code_table has columns: id and code
	public String getCodeById(int id) {
	    String code = null;
	    String query = "SELECT code FROM dd_train_cat WHERE orderNo = ?";

	    try (PreparedStatement stmt = con.prepareStatement(query)) {
	        stmt.setInt(1, id);  // Set the id parameter in the query
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            code = rs.getString("code");  // Fetch the code
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();  // Handle exception
	    }

	    return code;  // Return the code or null if not found
	}

// Assuming dd_code_table has columns: id and code
public String getEmailById(int id) {
    String email = null;
    String query = "SELECT email FROM dd_train_cat WHERE id = ?";

    try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setInt(1, id);  // Set the id parameter in the query
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            email = rs.getString("email");  // Fetch the code
        }
    } catch (SQLException e) {
        e.printStackTrace();  // Handle exception
    }

    return email;  // Return the code or null if not found
}
	public List<String> getPrgTitlesByCode(String code) {
	    List<String> prgTitles = new ArrayList<>();  // Create a list to store all prgTitles
	    String query = "SELECT prgTitle FROM poc_prog_detail WHERE trainCat = ?";  // Query to fetch prgTitle for the given code

	    try (PreparedStatement stmt = con.prepareStatement(query)) {
	        stmt.setString(1, code);  // Set the code parameter in the query
	        ResultSet rs = stmt.executeQuery();
	        
	        // Loop through all rows returned by the query
	        while (rs.next()) {
	            String prgTitle = rs.getString("prgTitle");  // Get the prgTitle
	            if (prgTitle != null) {
	                prgTitles.add(prgTitle);  // Add each prgTitle to the list
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();  // Handle any SQL exceptions
	    }

	    return prgTitles;  // Retu
	}
	public List<TnaTitle> getTnaPrgTitles(String code, String email) {
	    List<TnaTitle> prgTitles = new ArrayList<>();  // Create a list to store all prgTitles
	    String query = "SELECT id, prgTitle, choice FROM tna_title WHERE prgCat = ? AND email = ?";  // Query to fetch prgTitle for the given code

	    try (PreparedStatement stmt = con.prepareStatement(query)) {
	        stmt.setString(1, code);  // Set the code parameter in the query
	        stmt.setString(2, email);  // Set the email parameter in the query

	        ResultSet rs = stmt.executeQuery();
	        
	        // Loop through all rows returned by the query
	        while (rs.next()) {
	            String prgTitle = rs.getString("prgTitle");  // Get the prgTitle
	            int id  = rs.getInt("id");  // Get the id
	            String choice = rs.getString("choice");  // Get the choice
	            TnaTitle title = new TnaTitle();
	            title.setId(id);
	            title.setPrgTitle(prgTitle);
	            title.setChoice(choice);
	            prgTitles.add(title);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();  // Handle any SQL exceptions
	    }

	    return prgTitles;  // Return the list of TnaTitle objects
	}

	public List<String> getPrgCat() {
	    List<String> prgCat = new ArrayList<>();  // Create a list to store all prgTitles
	    String query = "SELECT code FROM dd_train_cat where orderNo>0";  // Query to fetch prgTitle for the given code

	    try (PreparedStatement stmt = con.prepareStatement(query)) {
	        
	        ResultSet rs = stmt.executeQuery();
	        
	        // Loop through all rows returned by the query
	        while (rs.next()) {
	            String prgTitle = rs.getString("code");  // Get the prgTitle
	            if (prgTitle != null) {
	            	prgCat.add(prgTitle);  // Add each prgTitle to the list
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();  // Handle any SQL exceptions
	    }

	    return prgCat;  // Retu
	}
	
	public List<String> getChoicesForPrgTitle() {
	    List<String> choices = new ArrayList<>();  // List to store choices
	    String query = "SELECT value FROM dd_submitted_choice";  // Query to fetch all choices (values)

	    try {
	        // Your connection object (assuming 'con' is already established elsewhere in your class)
	        PreparedStatement stmt = con.prepareStatement(query);  // Prepare the statement with the connection
	        ResultSet rs = stmt.executeQuery();  // Execute the query

	        // Loop through the result set and add all available choices (values) to the list
	        while (rs.next()) {
	            String choice = rs.getString("value");  // Retrieve the value from the column
	            if (choice != null) {
	                choices.add(choice);  // Add to the choices list
	            }
	        }

	        // Close the resources after use
	        rs.close();
	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();  // Handle any exception during the process
	    }

	    return choices;  // Return the list of all choices from the table
	}

	public int getPrevTnaCatId(int currentId)
	{
		int catId=0;
		/* String query="select * from tna_title where id="+id; */
		String query="SELECT id FROM dd_train_cat WHERE flag = 'yes' and id<"+currentId;

	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	
	
	    	catId=(rs.getInt("id")==0?0:rs.getInt("id"));

	    	
	    }
		rs.close();
		stmt.close();
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return catId;
	}
	public List<String> getStatesFromDb() {
	    List<String> states = new ArrayList<>();
	    try {
	        // Assuming you have a Connection object initialized as 'conn'
	        String sql = "SELECT value FROM dd_state"; // Modify as per your table structure
	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        
	        while (rs.next()) {
	            String state = rs.getString("value"); // Assuming 'state_name' is the column
	            states.add(state);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return states;
	}
	public List<String> getChoices() {
	    List<String> choices = new ArrayList<>();
	    try {
	        // Assuming you have a Connection object initialized as 'conn'
	        String sql = "SELECT value FROM dd_choices"; // Modify as per your table structure
	        Statement stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        
	        while (rs.next()) {
	            String choice = rs.getString("value"); // Assuming 'state_name' is the column
	            choices.add(choice);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return choices;
	}
	public void updateTnaTitle(int id, String choice) {
	    String query = "UPDATE tna_title SET choice = ? WHERE id = ?";

	    try (PreparedStatement pstmt = con.prepareStatement(query)) {
	        pstmt.setString(1, choice); // Set the choice value
	        pstmt.setInt(2, id);        // Set the ID

	        // Execute the update
	        int rowsUpdated = pstmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Record updated successfully for ID: " + id);
	        } else {
	            System.out.println("No record updated for ID: " + id);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	public List<TnaTitle> getTnaTitlesByEmail(String email) {
	    List<TnaTitle> titles = new ArrayList<>();
	    String query = "SELECT * FROM tna_titles WHERE email = ?";

	    try (PreparedStatement ps = con.prepareStatement(query)) {
	        ps.setString(1, email);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                TnaTitle title = new TnaTitle();
	                title.setEmail(rs.getString("email"));
	                title.setState(rs.getString("state"));
	                title.setInstituteName(rs.getString("instituteName"));
	                title.setName(rs.getString("name"));
	                title.setMobileNo(rs.getString("mobileNo"));
	                titles.add(title);
	            }
	        }
	    } catch (SQLException e) {
	        // Log the exception or handle it
	        System.err.println("SQLException: " + e.getMessage());
	    }
	    return titles;
	}
	public TnaTitle getTnaUserDetailByEmail(String email) {
	    TnaTitle title = new TnaTitle();
	    String query = "SELECT * FROM tna_title WHERE email = ?";

	    try (PreparedStatement ps = con.prepareStatement(query)) {
	        ps.setString(1, email);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	               
	                title.setEmail(rs.getString("email"));
	                title.setState(rs.getString("state"));
	                title.setInstituteName(rs.getString("instituteName"));
	                title.setName(rs.getString("name"));
	                title.setMobileNo(rs.getString("mobileNo"));
	                
	            }
	        }
	    } catch (SQLException e) {
	        // Log the exception or handle it
	        System.err.println("SQLException: " + e.getMessage());
	    }
	    return title;
	}

	public boolean checkEmailExists(String email) {
	    String query = "SELECT COUNT(*) FROM tna_title WHERE email = ?";
	    try (
	         PreparedStatement stmt = con.prepareStatement(query)) {
	        stmt.setString(1, email);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next() && rs.getInt(1) > 0) {
	            return true; // Email exists
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // Email doesn't exist
	}

	public List<TnaTitle> getUserTitlesByEmail(String email) {
	    List<TnaTitle> titles = new ArrayList<>();
	    String query = "SELECT * FROM tna_title WHERE email = ?";
	    try (
	         PreparedStatement stmt = con.prepareStatement(query)) {
	        stmt.setString(1, email);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            TnaTitle title = new TnaTitle();
	            title.setPrgCat(rs.getString("prgCat"));
	            title.setPrgTitle(rs.getString("prgTitle"));
	            title.setEmail(rs.getString("email"));
	            titles.add(title);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return titles;
	}
	public void updateTnaTitleTitle(int id, String title) {
	    // Implement logic to update the title in the database based on the ID
	    String updateQuery = "UPDATE tna_title SET prgtitle = ? WHERE id = ?";
	    try (
	         PreparedStatement stmt = con.prepareStatement(updateQuery)) {
	        stmt.setString(1, title);
	        stmt.setInt(2, id);
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.err.println("Error updating title: " + e.getMessage());
	    }
	}
	// Method to update the 'submitted' field to 'yes'
    public boolean updateSubmittedFieldToYes(String email) {
        String sql = "UPDATE tna_title SET submitted = 'yes' WHERE email = ?";

        try ( PreparedStatement stmt = con.prepareStatement(sql)) {
           
            stmt.setString(1, email);
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Get submitted status by email
    public String getSubmittedStatusByEmail(String email) {
        String query = "SELECT submitted FROM tna_title WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("submitted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

public String getDynamicQuery(TnaTitle tnaTitle)
{
String query="select * from tna_title ";
String whereClause="";
whereClause+=(null==tnaTitle.getPrgCat()||tnaTitle.getPrgCat().equals(""))?"":" prgCat='"+tnaTitle.getPrgCat()+"'";
if(whereClause.equals(""))
whereClause+=(null==tnaTitle.getInstituteName()||tnaTitle.getInstituteName().equals(""))?"":" instituteName='"+tnaTitle.getInstituteName()+"'";
else
whereClause+=(null==tnaTitle.getInstituteName()||tnaTitle.getInstituteName().equals(""))?"":" and instituteName='"+tnaTitle.getInstituteName()+"'";
if(whereClause.equals(""))
whereClause+=(null==tnaTitle.getChoice()||tnaTitle.getChoice().equals(""))?"":" choice='"+tnaTitle.getChoice()+"'";
else
whereClause+=(null==tnaTitle.getChoice()||tnaTitle.getChoice().equals(""))?"":" and choice='"+tnaTitle.getChoice()+"'";
if(whereClause.equals(""))
whereClause+=(null==tnaTitle.getSubmitted()||tnaTitle.getSubmitted().equals(""))?"":" submitted='"+tnaTitle.getSubmitted()+"'";
else
whereClause+=(null==tnaTitle.getSubmitted()||tnaTitle.getSubmitted().equals(""))?"":" and submitted='"+tnaTitle.getSubmitted()+"'";
if(whereClause.equals(""))
whereClause+=(null==tnaTitle.getEmail()||tnaTitle.getEmail().equals(""))?"":" email='"+tnaTitle.getEmail()+"'";
else
whereClause+=(null==tnaTitle.getEmail()||tnaTitle.getEmail().equals(""))?"":" and email='"+tnaTitle.getEmail()+"'";
if(whereClause.equals(""))
whereClause+=(null==tnaTitle.getState()||tnaTitle.getState().equals(""))?"":" state='"+tnaTitle.getState()+"'";
else
whereClause+=(null==tnaTitle.getState()||tnaTitle.getState().equals(""))?"":" and state='"+tnaTitle.getState()+"'";
if(!whereClause.equals(""))
query+=" where "+whereClause;
System.out.println("Search Query= "+query);
    return query;
}
public String getDynamicQuery2(TnaTitle tnaTitle)
{
String query="select count(*) from tna_title ";
String whereClause="";
whereClause+=(null==tnaTitle.getPrgCat()||tnaTitle.getPrgCat().equals(""))?"":" prgCat='"+tnaTitle.getPrgCat()+"'";
if(whereClause.equals(""))
whereClause+=(null==tnaTitle.getInstituteName()||tnaTitle.getInstituteName().equals(""))?"":" instituteName='"+tnaTitle.getInstituteName()+"'";
else
whereClause+=(null==tnaTitle.getInstituteName()||tnaTitle.getInstituteName().equals(""))?"":" and instituteName='"+tnaTitle.getInstituteName()+"'";
if(whereClause.equals(""))
whereClause+=(null==tnaTitle.getChoice()||tnaTitle.getChoice().equals(""))?"":" choice='"+tnaTitle.getChoice()+"'";
else
whereClause+=(null==tnaTitle.getChoice()||tnaTitle.getChoice().equals(""))?"":" and choice='"+tnaTitle.getChoice()+"'";
if(whereClause.equals(""))
whereClause+=(null==tnaTitle.getSubmitted()||tnaTitle.getSubmitted().equals(""))?"":" submitted='"+tnaTitle.getSubmitted()+"'";
else
whereClause+=(null==tnaTitle.getSubmitted()||tnaTitle.getSubmitted().equals(""))?"":" and submitted='"+tnaTitle.getSubmitted()+"'";
if(whereClause.equals(""))
whereClause+=(null==tnaTitle.getEmail()||tnaTitle.getEmail().equals(""))?"":" email='"+tnaTitle.getEmail()+"'";
else
whereClause+=(null==tnaTitle.getEmail()||tnaTitle.getEmail().equals(""))?"":" and email='"+tnaTitle.getEmail()+"'";
if(whereClause.equals(""))
whereClause+=(null==tnaTitle.getState()||tnaTitle.getState().equals(""))?"":" state='"+tnaTitle.getState()+"'";
else
whereClause+=(null==tnaTitle.getState()||tnaTitle.getState().equals(""))?"":" and state='"+tnaTitle.getState()+"'";
if(!whereClause.equals(""))
query+=" where "+whereClause;
System.out.println("Search Query= "+query);
    return query;
}
public ArrayList<TnaTitle> getTnaTitleList(TnaTitle tnaTitle)
{
ArrayList<TnaTitle> tnaTitleList =new ArrayList<TnaTitle>();
String query=getDynamicQuery(tnaTitle);
System.out.println("Search Query= "+query);
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next()) {
TnaTitle tnaTitle2 =new TnaTitle();
tnaTitle2.setId(rs.getInt("id")==0?0:rs.getInt("id"));
tnaTitle2.setPrgCat(rs.getString("prgCat")==null?"":rs.getString("prgCat"));
tnaTitle2.setPrgCatValue(rs.getString("prgCat")==null?"":getValue(rs.getString("prgCat"),"dd_train_cat"));
tnaTitle2.setPrgTitle(rs.getString("prgTitle")==null?"":rs.getString("prgTitle"));
tnaTitle2.setInstituteName(rs.getString("instituteName")==null?"":rs.getString("instituteName"));
tnaTitle2.setMobileNo(rs.getString("mobileNo")==null?"":rs.getString("mobileNo"));
tnaTitle2.setChoice(rs.getString("choice")==null?"":rs.getString("choice"));
tnaTitle2.setChoiceValue(rs.getString("choice")==null?"":getValue(rs.getString("choice"),"dd_submitted_choice"));
tnaTitle2.setSubmitted(rs.getString("submitted")==null?"":rs.getString("submitted"));
tnaTitle2.setSubmittedValue(rs.getString("submitted")==null?"":getValue(rs.getString("submitted"),"dd_answer"));
tnaTitle2.setUpdateBy(rs.getString("updateBy")==null?"":rs.getString("updateBy"));
tnaTitle2.setUpdateDt(DateService.getDTDYYYYMMDDFormat(rs.getDate("updateDt")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updateDt")));
tnaTitle2.setUpdateTime(rs.getString("updateTime")==null?"":rs.getString("updateTime"));
tnaTitle2.setName(rs.getString("name")==null?"":rs.getString("name"));
tnaTitle2.setEmail(rs.getString("email")==null?"":rs.getString("email"));
tnaTitle2.setState(rs.getString("state")==null?"":rs.getString("state"));
tnaTitle2.setStateValue(rs.getString("state")==null?"":getValue(rs.getString("state"),"dd_state"));
    	tnaTitleList.add(tnaTitle2);
    }
	}
    catch (Exception e) {
    	System.out.println(e);
	}
    return tnaTitleList;
}
	
public ArrayList<TnaTitle> getTnaTitleList(TnaTitle tnaTitle,int pageNo,int limit)
{
ArrayList<TnaTitle> tnaTitleList =new ArrayList<TnaTitle>();
String query=getDynamicQuery(tnaTitle);
query+= " limit "+limit +" offset "+limit*(pageNo-1);
System.out.println("Search Query= "+query);
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next()) {
TnaTitle tnaTitle2 =new TnaTitle();
tnaTitle2.setId(rs.getInt("id")==0?0:rs.getInt("id"));
tnaTitle2.setPrgCat(rs.getString("prgCat")==null?"":rs.getString("prgCat"));
tnaTitle2.setPrgCatValue(rs.getString("prgCat")==null?"":getValue(rs.getString("prgCat"),"dd_train_cat"));
tnaTitle2.setPrgTitle(rs.getString("prgTitle")==null?"":rs.getString("prgTitle"));
tnaTitle2.setInstituteName(rs.getString("instituteName")==null?"":rs.getString("instituteName"));
tnaTitle2.setMobileNo(rs.getString("mobileNo")==null?"":rs.getString("mobileNo"));
tnaTitle2.setChoice(rs.getString("choice")==null?"":rs.getString("choice"));
tnaTitle2.setChoiceValue(rs.getString("choice")==null?"":getValue(rs.getString("choice"),"dd_submitted_choice"));
tnaTitle2.setSubmitted(rs.getString("submitted")==null?"":rs.getString("submitted"));
tnaTitle2.setSubmittedValue(rs.getString("submitted")==null?"":getValue(rs.getString("submitted"),"dd_answer"));
tnaTitle2.setUpdateBy(rs.getString("updateBy")==null?"":rs.getString("updateBy"));
tnaTitle2.setUpdateDt(DateService.getDTDYYYYMMDDFormat(rs.getDate("updateDt")==null?DateService.getDefaultDtYYYYMMDD():rs.getDate("updateDt")));
tnaTitle2.setUpdateTime(rs.getString("updateTime")==null?"":rs.getString("updateTime"));
tnaTitle2.setName(rs.getString("name")==null?"":rs.getString("name"));
tnaTitle2.setEmail(rs.getString("email")==null?"":rs.getString("email"));
tnaTitle2.setState(rs.getString("state")==null?"":rs.getString("state"));
tnaTitle2.setStateValue(rs.getString("state")==null?"":getValue(rs.getString("state"),"dd_state"));
    	tnaTitleList.add(tnaTitle2);
    }
	}
    catch (Exception e) {
    	System.out.println(e);
	}
    return tnaTitleList;
}
	
	
	public static void main(String[] args) {
		
		TnaTitleDBService tnaTitleDBService =new TnaTitleDBService();
		
		
		
		 //Test-1 : Create TnaTitle
		  
		  TnaTitle tnaTitle = new TnaTitle(); tnaTitle.setDefaultValues();
		  tnaTitleDBService.createTnaTitle(tnaTitle);
		  
		 ArrayList<TnaTitle> tnaTitleList = tnaTitleDBService.getTnaTitleList();
		TnaTitleService tnaTitleService =new TnaTitleService();
		tnaTitleService.displayList(tnaTitleList);
		
	}
}