package tna.tnaReport;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class TnaReportCntrl
 */
@WebServlet("/tna/tnaReport/tnaReportCntrl")
public class TnaReportCntrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TnaReportCntrl() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String page= request.getParameter("page");
		String opr = request.getParameter("opr");
		int pageNo = (null==request.getParameter("pageNo")?0:Integer.parseInt(request.getParameter("pageNo")));
		int limit= (null==request.getParameter("pageNo")?0:Integer.parseInt(request.getParameter("limit")));
		
		RequestDispatcher rd;
		TnaReportDBService tnaReportDBService =new TnaReportDBService();
		TnaReport tnaReport =new TnaReport();
		//Action for close buttons
		String homeURL=(null==request.getSession().getAttribute("homeURL")?"":(String)request.getSession().getAttribute("homeURL"));		
		if(page.equals("tnaReportDashboard"))
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="tnaReportCntrl?page="+page+"&opr=showAll&pageNo="+pageNo+"&limit="+limit;
			request.getSession().setAttribute("homeURL",homeURL);
			
			if(opr.equals("showAll")) 
			{
				ArrayList<TnaReport> tnaReportList =new ArrayList<TnaReport>();
				
				if(pageNo==0)
				tnaReportList = tnaReportDBService.getTnaReportList();
				else { //pagination
					int totalPages= tnaReportDBService.getTotalPages(limit);
					tnaReportList = tnaReportDBService.getTnaReportList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("tnaReportList",tnaReportList);
				rd = request.getRequestDispatcher("tnaReportDashboard.jsp");
				rd.forward(request, response);
			} 
			else if(opr.equals("addNew")) //CREATE
			{
				tnaReport.setDefaultValues();
				tnaReport.displayValues();
				request.setAttribute("tnaReport",tnaReport);
				rd = request.getRequestDispatcher("addNewTnaReport.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("edit")) //UPDATE
			{
				int id = Integer.parseInt(request.getParameter("id"));
				tnaReport = tnaReportDBService.getTnaReport(id);
				request.setAttribute("tnaReport",tnaReport);
				rd = request.getRequestDispatcher("updateTnaReport.jsp");
				rd.forward(request, response);
			}
			//Begin: modified by Dr PNH on 06-12-2022
			else if(opr.equals("editNext")) //Save and Next
			{
				int id = Integer.parseInt(request.getParameter("id"));
				tnaReport = tnaReportDBService.getTnaReport(id);
				request.setAttribute("tnaReport",tnaReport);
				rd = request.getRequestDispatcher("updateNextTnaReport.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("saveShowNext")) //Save, show & next
			{
				tnaReport.setDefaultValues();
				tnaReport.displayValues();
				request.setAttribute("tnaReport",tnaReport);
				
				ArrayList<TnaReport> tnaReportList =new ArrayList<TnaReport>();
				
				if(pageNo==0)
				tnaReportList = tnaReportDBService.getTnaReportList();
				else { //pagination
					int totalPages= tnaReportDBService.getTotalPages(limit);
					tnaReportList = tnaReportDBService.getTnaReportList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("tnaReportList",tnaReportList);
				rd = request.getRequestDispatcher("saveShowNextTnaReport.jsp");
				rd.forward(request, response);
			}
			//End: modified by Dr PNH on 06-12-2022
			else if(opr.equals("delete")) //DELETE
			{
				int id = Integer.parseInt(request.getParameter("id"));
				tnaReport.setId(id);
				tnaReportDBService.deleteTnaReport(id);
				request.setAttribute("tnaReport",tnaReport);
				rd = request.getRequestDispatcher("deleteTnaReportSuccess.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("view")) //READ
			{
				int id = Integer.parseInt(request.getParameter("id"));
				tnaReport = tnaReportDBService.getTnaReport(id);
				request.setAttribute("tnaReport",tnaReport);
				rd = request.getRequestDispatcher("viewTnaReport.jsp");
				rd.forward(request, response);
			}
			
		}
		else if(page.equals("addNewTnaReport")) 
		{
			if(opr.equals("save"))
			{
				tnaReport.setRequestParam(request);
				tnaReport.displayValues();
				tnaReportDBService.createTnaReport(tnaReport);
				request.setAttribute("tnaReport",tnaReport);
				if(pageNo!=0) {//pagination
					int totalPages= tnaReportDBService.getTotalPages(limit);
					homeURL="tnaReportCntrl?page=tnaReportDashboard&opr=showAll&pageNo="+totalPages+"&limit="+limit;
					request.getSession().setAttribute("homeURL", homeURL);
				}
				rd = request.getRequestDispatcher("addNewTnaReportSuccess.jsp");
				rd.forward(request, response);
			}
		}
		//Begin: modified by Dr PNH on 06-12-2022
		else if(page.equals("updateNextTnaReport")) 
		{
			if(opr.equals("update"))
			{
				tnaReport.setRequestParam(request);
				tnaReportDBService.updateTnaReport(tnaReport);
				request.setAttribute("tnaReport",tnaReport);
				request.getSession().setAttribute("msg", "Record saved successfully");
				response.sendRedirect("tnaReportCntrl?page=tnaReportDashboard&opr=editNext&pageNo=0&limit=0&id=10");			}
		}
		else if(page.equals("saveShowNextTnaReport")) 
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="tnaReportCntrl?page=tnaReportDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0";
			request.getSession().setAttribute("homeURL",homeURL);
			if(opr.equals("addNew")) //save new record
			{
				tnaReport.setRequestParam(request);
				tnaReport.displayValues();
				tnaReportDBService.createTnaReport(tnaReport);
				request.setAttribute("tnaReport",tnaReport);
				if(pageNo!=0) {//pagination
					int totalPages= tnaReportDBService.getTotalPages(limit);
					homeURL="tnaReportCntrl?page=tnaReportDashboard&opr=showAll&pageNo="+totalPages+"&limit="+limit;
					request.getSession().setAttribute("homeURL", homeURL);
				}
				request.getSession().setAttribute("msg", "Record saved successfully");
				response.sendRedirect("tnaReportCntrl?page=tnaReportDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
				//rd = request.getRequestDispatcher("tnaReportCntrl?page=tnaReportDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
				//rd.forward(request, response);
			}
			else if(opr.equals("edit"))
			{
				int id = Integer.parseInt(request.getParameter("id"));
				tnaReport = tnaReportDBService.getTnaReport(id);
				request.setAttribute("tnaReport",tnaReport);
				
				ArrayList<TnaReport> tnaReportList =new ArrayList<TnaReport>();
				if(pageNo==0)
				tnaReportList = tnaReportDBService.getTnaReportList();
				else { //pagination
					int totalPages= tnaReportDBService.getTotalPages(limit);
					tnaReportList = tnaReportDBService.getTnaReportList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("tnaReportList",tnaReportList);
				rd = request.getRequestDispatcher("saveShowNextTnaReport.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("update"))
			{
				tnaReport.setRequestParam(request);
				tnaReportDBService.updateTnaReport(tnaReport);
				request.setAttribute("tnaReport",tnaReport);
				request.getSession().setAttribute("msg", "Record updated successfully");
				response.sendRedirect("tnaReportCntrl?page=tnaReportDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
			}
			else if(opr.equals("delete"))
			{
					int id = Integer.parseInt(request.getParameter("id"));
					tnaReport.setId(id);
					tnaReportDBService.deleteTnaReport(id);
					request.setAttribute("tnaReport",tnaReport);
					request.getSession().setAttribute("msg", "Record deleted successfully");
					response.sendRedirect("tnaReportCntrl?page=tnaReportDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");		
			}
			else if(opr.equals("reset")||opr.equals("cancel"))
			{
					response.sendRedirect("tnaReportCntrl?page=tnaReportDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");		
			}
			
		}
		//End: modified by Dr PNH on 06-12-2022
		else if(page.equals("updateTnaReport")) 
		{
			if(opr.equals("update"))
			{
				tnaReport.setRequestParam(request);
				tnaReportDBService.updateTnaReport(tnaReport);
				request.setAttribute("tnaReport",tnaReport);
				rd = request.getRequestDispatcher("updateTnaReportSuccess.jsp");
				rd.forward(request, response);
			}
		}
		else if(page.equals("viewTnaReport")) 
		{
			if(opr.equals("print")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				tnaReport = tnaReportDBService.getTnaReport(id);
				request.setAttribute("tnaReport",tnaReport);
				rd = request.getRequestDispatcher("printTnaReport.jsp");
				rd.forward(request, response);
			}
		}
		else if(page.equals("searchTnaReport"))
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="tnaReportCntrl?page="+page+"&opr=showAll&pageNo="+pageNo+"&limit="+limit;
			request.getSession().setAttribute("homeURL",homeURL);
			if(opr.equals("search")) 
			{
				tnaReport.setRequestParam(request);
				tnaReport.displayValues();
				request.getSession().setAttribute("tnaReportSearch",tnaReport);
				request.setAttribute("opr","search");
				ArrayList<TnaReport> tnaReportList =new ArrayList<TnaReport>();
				if(pageNo==0)
				tnaReportList = tnaReportDBService.getTnaReportList(tnaReport);
				else { //pagination
					int totalPages=0;
					if(limit==0)
					totalPages=0;
					else
					totalPages=tnaReportDBService.getTotalPages(tnaReport,limit);
					pageNo=1;
					tnaReportList = tnaReportDBService.getTnaReportList(tnaReport,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("tnaReportList",tnaReportList);
				rd = request.getRequestDispatcher("searchTnaReport.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
//begin:code for download/print button
			else if(opr.equals("downloadPrint")) 
			{
				tnaReport.setRequestParam(request);
				tnaReport.displayValues();
				request.getSession().setAttribute("tnaReportSearch",tnaReport);
				request.setAttribute("opr","tnaReport");
				ArrayList<TnaReport> tnaReportList =new ArrayList<TnaReport>();
				if(pageNo==0)
				tnaReportList = tnaReportDBService.getTnaReportList(tnaReport);
				else { //pagination
					int totalPages=0;
					if(limit==0)
					totalPages=0;
					else
					totalPages=tnaReportDBService.getTotalPages(tnaReport,limit);
					pageNo=1;
					tnaReportList = tnaReportDBService.getTnaReportList(tnaReport,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("tnaReportList",tnaReportList);
				rd = request.getRequestDispatcher("searchTnaReportDownloadPrint.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			//end:code for download/print button
			
			else if(opr.equals("showAll")) 
			{
				tnaReport=(TnaReport)request.getSession().getAttribute("tnaReportSearch");
				ArrayList<TnaReport> tnaReportList =new ArrayList<TnaReport>();
				if(pageNo==0)
				tnaReportList = tnaReportDBService.getTnaReportList(tnaReport);
				else { //pagination
					int totalPages= tnaReportDBService.getTotalPages(tnaReport,limit);
					tnaReportList = tnaReportDBService.getTnaReportList(tnaReport,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("tnaReportList",tnaReportList);
				rd = request.getRequestDispatcher("searchTnaReport.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("searchNext")||opr.equals("searchPrev")||opr.equals("searchFirst")||opr.equals("searchLast")) 
			{
				request.setAttribute("opr","search");
				tnaReport=(TnaReport)request.getSession().getAttribute("tnaReportSearch");
				ArrayList<TnaReport> tnaReportList =new ArrayList<TnaReport>();
				if(pageNo==0)
				tnaReportList = tnaReportDBService.getTnaReportList(tnaReport);
				else { //pagination
					int totalPages= tnaReportDBService.getTotalPages(tnaReport,limit);
					tnaReportList = tnaReportDBService.getTnaReportList(tnaReport,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("tnaReportList",tnaReportList);
				rd = request.getRequestDispatcher("searchTnaReport.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("showNone"))
			{
				tnaReport.setDefaultValues();
				tnaReport.displayValues();
				request.getSession().setAttribute("tnaReportSearch",tnaReport);
				request.setAttribute("opr","showNone");
				rd = request.getRequestDispatcher("searchTnaReport.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("edit")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				tnaReport = tnaReportDBService.getTnaReport(id);
				request.setAttribute("tnaReport",tnaReport);
				rd = request.getRequestDispatcher("updateTnaReport.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("delete")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				tnaReport.setId(id);
				tnaReportDBService.deleteTnaReport(id);
				request.setAttribute("tnaReport",tnaReport);
				rd = request.getRequestDispatcher("deleteTnaReportSuccess.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("view")) 
			{
 			int id = Integer.parseInt(request.getParameter("id"));
				tnaReport = tnaReportDBService.getTnaReport(id);
				request.setAttribute("tnaReport",tnaReport);
				rd = request.getRequestDispatcher("viewTnaReport.jsp");
				rd.forward(request, response);
			}
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static void main(String[] args) throws URISyntaxException {
		URI uri = new URI("page=updateTnaReport&opr=close&homePage=tnaReportDashboard");
		String v = uri.getQuery();
		
	}
}
