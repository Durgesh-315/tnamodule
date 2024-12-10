package tna.tnaReport;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
public class TnaReportDBService {
	Connection con;
	
	
	public TnaReportDBService()
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
		String query="select count(*) from tna_report";
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
	public int getTotalPages(TnaReport tnaReport,int limit)
	{
		String query=getDynamicQuery2(tnaReport);
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
	
	
	public int getTnaReportId(TnaReport tnaReport)
	{
		int id=0;
		String query="select id from tna_report";
String whereClause = " where "+ "prgCat=? and prgTitle=? and choice1=? and choice2=? and choice3=? and choice4=? and choice5=? and choice6=?";
	    query+=whereClause;
		System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, tnaReport.getPrgCat());
pstmt.setString(2, tnaReport.getPrgTitle());
pstmt.setString(3, tnaReport.getChoice1());
pstmt.setString(4, tnaReport.getChoice2());
pstmt.setString(5, tnaReport.getChoice3());
pstmt.setString(6, tnaReport.getChoice4());
pstmt.setString(7, tnaReport.getChoice5());
pstmt.setString(8, tnaReport.getChoice6());
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
	public void createTnaReport(TnaReport tnaReport)
	{
		
String query="INSERT INTO tna_report(prgCat,prgTitle,choice1,choice2,choice3,choice4,choice5,choice6) VALUES(?,?,?,?,?,?,?,?)";
	
    System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, tnaReport.getPrgCat());
pstmt.setString(2, tnaReport.getPrgTitle());
pstmt.setString(3, tnaReport.getChoice1());
pstmt.setString(4, tnaReport.getChoice2());
pstmt.setString(5, tnaReport.getChoice3());
pstmt.setString(6, tnaReport.getChoice4());
pstmt.setString(7, tnaReport.getChoice5());
pstmt.setString(8, tnaReport.getChoice6());
	    int x = pstmt.executeUpdate();
	    }
	    catch (Exception e) {
	  
  	System.out.println(e);
		}
		int id = getTnaReportId(tnaReport);
		tnaReport.setId(id);
	}
	public void updateTnaReport(TnaReport tnaReport)
	{
		
String query="update tna_report set "+"prgCat=?,prgTitle=?,choice1=?,choice2=?,choice3=?,choice4=?,choice5=?,choice6=? where id=?";
	   
 System.out.println(query);
		try {
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, tnaReport.getPrgCat());
pstmt.setString(2, tnaReport.getPrgTitle());
pstmt.setString(3, tnaReport.getChoice1());
pstmt.setString(4, tnaReport.getChoice2());
pstmt.setString(5, tnaReport.getChoice3());
pstmt.setString(6, tnaReport.getChoice4());
pstmt.setString(7, tnaReport.getChoice5());
pstmt.setString(8, tnaReport.getChoice6());
pstmt.setInt(9, tnaReport.getId());
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
	
	public TnaReport getTnaReport(int id)
	{
		TnaReport tnaReport =new TnaReport();
		String query="select * from tna_report where id="+id;
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    if(rs.next()) {
	    	
	
tnaReport.setId(rs.getInt("id")==0?0:rs.getInt("id"));
tnaReport.setPrgCat(rs.getString("prgCat")==null?"":rs.getString("prgCat"));
tnaReport.setPrgTitle(rs.getString("prgTitle")==null?"":rs.getString("prgTitle"));
tnaReport.setChoice1(rs.getString("choice1")==null?"":rs.getString("choice1"));
tnaReport.setChoice2(rs.getString("choice2")==null?"":rs.getString("choice2"));
tnaReport.setChoice3(rs.getString("choice3")==null?"":rs.getString("choice3"));
tnaReport.setChoice4(rs.getString("choice4")==null?"":rs.getString("choice4"));
tnaReport.setChoice5(rs.getString("choice5")==null?"":rs.getString("choice5"));
tnaReport.setChoice6(rs.getString("choice6")==null?"":rs.getString("choice6"));
	    	
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return tnaReport;
	}
	
	
	public ArrayList<TnaReport> getTnaReportList()
	{
		ArrayList<TnaReport> tnaReportList =new ArrayList<TnaReport>();
		String query="select * from tna_report";
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
	    	TnaReport tnaReport =new TnaReport();
tnaReport.setId(rs.getInt("id")==0?0:rs.getInt("id"));
tnaReport.setPrgCat(rs.getString("prgCat")==null?"":rs.getString("prgCat"));
tnaReport.setPrgTitle(rs.getString("prgTitle")==null?"":rs.getString("prgTitle"));
tnaReport.setChoice1(rs.getString("choice1")==null?"":rs.getString("choice1"));
tnaReport.setChoice2(rs.getString("choice2")==null?"":rs.getString("choice2"));
tnaReport.setChoice3(rs.getString("choice3")==null?"":rs.getString("choice3"));
tnaReport.setChoice4(rs.getString("choice4")==null?"":rs.getString("choice4"));
tnaReport.setChoice5(rs.getString("choice5")==null?"":rs.getString("choice5"));
tnaReport.setChoice6(rs.getString("choice6")==null?"":rs.getString("choice6"));
	    	tnaReportList.add(tnaReport);
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return tnaReportList;
	}
	
	public ArrayList<TnaReport> getTnaReportList(int pageNo,int limit)
	{
		ArrayList<TnaReport> tnaReportList =new ArrayList<TnaReport>();
String query="select * from tna_report limit "+limit +" offset "+limit*(pageNo-1);
	    System.out.println(query);
		try {
		Statement stmt = con.createStatement();
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()) {
	    	TnaReport tnaReport =new TnaReport();
tnaReport.setId(rs.getInt("id")==0?0:rs.getInt("id"));
tnaReport.setPrgCat(rs.getString("prgCat")==null?"":rs.getString("prgCat"));
tnaReport.setPrgTitle(rs.getString("prgTitle")==null?"":rs.getString("prgTitle"));
tnaReport.setChoice1(rs.getString("choice1")==null?"":rs.getString("choice1"));
tnaReport.setChoice2(rs.getString("choice2")==null?"":rs.getString("choice2"));
tnaReport.setChoice3(rs.getString("choice3")==null?"":rs.getString("choice3"));
tnaReport.setChoice4(rs.getString("choice4")==null?"":rs.getString("choice4"));
tnaReport.setChoice5(rs.getString("choice5")==null?"":rs.getString("choice5"));
tnaReport.setChoice6(rs.getString("choice6")==null?"":rs.getString("choice6"));
	    	tnaReportList.add(tnaReport);
	    }
		}
	    catch (Exception e) {
	    	System.out.println(e);
		}
	    
	    return tnaReportList;
	}
	
	public void deleteTnaReport(int id) {
		
			String query="delete from tna_report where id="+id;
		    System.out.println(query);
				
			
		    try {
			Statement stmt = con.createStatement();
		    int x = stmt.executeUpdate(query);
		    }
		    catch (Exception e) {
		    	System.out.println(e);
			}
		
	}
	
public String getDynamicQuery(TnaReport tnaReport)
{
String query="select * from tna_report ";
String whereClause="";
whereClause+=(null==tnaReport.getPrgCat()||tnaReport.getPrgCat().equals(""))?"":" prgCat='"+tnaReport.getPrgCat()+"'";
if(whereClause.equals(""))
whereClause+=(null==tnaReport.getPrgTitle()||tnaReport.getPrgTitle().equals(""))?"":" prgTitle='"+tnaReport.getPrgTitle()+"'";
else
whereClause+=(null==tnaReport.getPrgTitle()||tnaReport.getPrgTitle().equals(""))?"":" and prgTitle='"+tnaReport.getPrgTitle()+"'";
if(!whereClause.equals(""))
query+=" where "+whereClause;
System.out.println("Search Query= "+query);
    return query;
}
public String getDynamicQuery2(TnaReport tnaReport)
{
String query="select count(*) from tna_report ";
String whereClause="";
whereClause+=(null==tnaReport.getPrgCat()||tnaReport.getPrgCat().equals(""))?"":" prgCat='"+tnaReport.getPrgCat()+"'";
if(whereClause.equals(""))
whereClause+=(null==tnaReport.getPrgTitle()||tnaReport.getPrgTitle().equals(""))?"":" prgTitle='"+tnaReport.getPrgTitle()+"'";
else
whereClause+=(null==tnaReport.getPrgTitle()||tnaReport.getPrgTitle().equals(""))?"":" and prgTitle='"+tnaReport.getPrgTitle()+"'";
if(!whereClause.equals(""))
query+=" where "+whereClause;
System.out.println("Search Query= "+query);
    return query;
}
public ArrayList<TnaReport> getTnaReportList(TnaReport tnaReport)
{
ArrayList<TnaReport> tnaReportList =new ArrayList<TnaReport>();
String query=getDynamicQuery(tnaReport);
System.out.println("Search Query= "+query);
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next()) {
TnaReport tnaReport2 =new TnaReport();
tnaReport2.setId(rs.getInt("id")==0?0:rs.getInt("id"));
tnaReport2.setPrgCat(rs.getString("prgCat")==null?"":rs.getString("prgCat"));
tnaReport2.setPrgTitle(rs.getString("prgTitle")==null?"":rs.getString("prgTitle"));
tnaReport2.setChoice1(rs.getString("choice1")==null?"":rs.getString("choice1"));
tnaReport2.setChoice2(rs.getString("choice2")==null?"":rs.getString("choice2"));
tnaReport2.setChoice3(rs.getString("choice3")==null?"":rs.getString("choice3"));
tnaReport2.setChoice4(rs.getString("choice4")==null?"":rs.getString("choice4"));
tnaReport2.setChoice5(rs.getString("choice5")==null?"":rs.getString("choice5"));
tnaReport2.setChoice6(rs.getString("choice6")==null?"":rs.getString("choice6"));
    	tnaReportList.add(tnaReport2);
    }
	}
    catch (Exception e) {
    	System.out.println(e);
	}
    return tnaReportList;
}
	
public ArrayList<TnaReport> getTnaReportList(TnaReport tnaReport,int pageNo,int limit)
{
ArrayList<TnaReport> tnaReportList =new ArrayList<TnaReport>();
String query=getDynamicQuery(tnaReport);
query+= " limit "+limit +" offset "+limit*(pageNo-1);
System.out.println("Search Query= "+query);
try {
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
while(rs.next()) {
TnaReport tnaReport2 =new TnaReport();
tnaReport2.setId(rs.getInt("id")==0?0:rs.getInt("id"));
tnaReport2.setPrgCat(rs.getString("prgCat")==null?"":rs.getString("prgCat"));
tnaReport2.setPrgTitle(rs.getString("prgTitle")==null?"":rs.getString("prgTitle"));
tnaReport2.setChoice1(rs.getString("choice1")==null?"":rs.getString("choice1"));
tnaReport2.setChoice2(rs.getString("choice2")==null?"":rs.getString("choice2"));
tnaReport2.setChoice3(rs.getString("choice3")==null?"":rs.getString("choice3"));
tnaReport2.setChoice4(rs.getString("choice4")==null?"":rs.getString("choice4"));
tnaReport2.setChoice5(rs.getString("choice5")==null?"":rs.getString("choice5"));
tnaReport2.setChoice6(rs.getString("choice6")==null?"":rs.getString("choice6"));
    	tnaReportList.add(tnaReport2);
    }
	}
    catch (Exception e) {
    	System.out.println(e);
	}
    return tnaReportList;
}
	
	
	public static void main(String[] args) {
		
		TnaReportDBService tnaReportDBService =new TnaReportDBService();
		
		
		
		 //Test-1 : Create TnaReport
		  
		  TnaReport tnaReport = new TnaReport(); tnaReport.setDefaultValues();
		  tnaReportDBService.createTnaReport(tnaReport);
		  
		 ArrayList<TnaReport> tnaReportList = tnaReportDBService.getTnaReportList();
		TnaReportService tnaReportService =new TnaReportService();
		tnaReportService.displayList(tnaReportList);
		
	}
}
