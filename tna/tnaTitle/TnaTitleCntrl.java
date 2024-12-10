package tna.tnaTitle;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nitttr.poc.UserDTO;
/**
 * Servlet implementation class TnaTitleCntrl
 */
@WebServlet("/tna/tnaTitle/tnaTitleCntrl")
public class TnaTitleCntrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TnaTitleCntrl() {
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
		TnaTitleDBService tnaTitleDBService =new TnaTitleDBService();
		TnaTitle tnaTitle =new TnaTitle();
		//Action for close buttons
		String homeURL=(null==request.getSession().getAttribute("homeURL")?"":(String)request.getSession().getAttribute("homeURL"));		
		if(page.equals("tnaTitleDashboard"))
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="tnaTitleCntrl?page="+page+"&opr=showAll&pageNo="+pageNo+"&limit="+limit;
			request.getSession().setAttribute("homeURL",homeURL);
			
			if(opr.equals("showAll")) 
			{
				ArrayList<TnaTitle> tnaTitleList =new ArrayList<TnaTitle>();
				
				if(pageNo==0)
				tnaTitleList = tnaTitleDBService.getTnaTitleList();
				else { //pagination
					int totalPages= tnaTitleDBService.getTotalPages(limit);
					tnaTitleList = tnaTitleDBService.getTnaTitleList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("tnaTitleList",tnaTitleList);
				rd = request.getRequestDispatcher("tnaTitleDashboard.jsp");
				rd.forward(request, response);
			} 
			else if(opr.equals("addNew")) //CREATE
			{
				tnaTitle.setDefaultValues();
				tnaTitle.displayValues();
				request.setAttribute("tnaTitle",tnaTitle);
				rd = request.getRequestDispatcher("addNewTnaTitle.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("edit")) //UPDATE
			{
				int id = Integer.parseInt(request.getParameter("id"));
				tnaTitle = tnaTitleDBService.getTnaTitle(id);
				request.setAttribute("tnaTitle",tnaTitle);
				rd = request.getRequestDispatcher("updateTnaTitle.jsp");
				rd.forward(request, response);
			}
			//Begin: modified by Dr PNH on 06-12-2022
			else if(opr.equals("editNext")) //Save and Next
			{
				int id = Integer.parseInt(request.getParameter("id"));
				tnaTitle = tnaTitleDBService.getTnaTitle(id);
				request.setAttribute("tnaTitle",tnaTitle);
				rd = request.getRequestDispatcher("updateNextTnaTitle.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("saveShowNext")) //Save, show & next
			{
				tnaTitle.setDefaultValues();
				tnaTitle.displayValues();
				request.setAttribute("tnaTitle",tnaTitle);
				
				ArrayList<TnaTitle> tnaTitleList =new ArrayList<TnaTitle>();
				
				if(pageNo==0)
				tnaTitleList = tnaTitleDBService.getTnaTitleList();
				else { //pagination
					int totalPages= tnaTitleDBService.getTotalPages(limit);
					tnaTitleList = tnaTitleDBService.getTnaTitleList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("tnaTitleList",tnaTitleList);
				rd = request.getRequestDispatcher("saveShowNextTnaTitle.jsp");
				rd.forward(request, response);
			}
			//End: modified by Dr PNH on 06-12-2022
			else if(opr.equals("delete")) //DELETE
			{
				int id = Integer.parseInt(request.getParameter("id"));
				tnaTitle.setId(id);
				tnaTitleDBService.deleteTnaTitle(id);
				request.setAttribute("tnaTitle",tnaTitle);
				rd = request.getRequestDispatcher("deleteTnaTitleSuccess.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("view")) //READ
			{
				int id = Integer.parseInt(request.getParameter("id"));
				tnaTitle = tnaTitleDBService.getTnaTitle(id);
				request.setAttribute("tnaTitle",tnaTitle);
				rd = request.getRequestDispatcher("viewTnaTitle.jsp");
				rd.forward(request, response);
			}
			
		}
		if (page.equals("tnaForm0")) { // New initial page
		    if (opr.equals("submit")) {
		        String email = request.getParameter("email");

		        if (email != null && !email.isEmpty()) {
		            HttpSession session = request.getSession();
		            session.setAttribute("email", email); // Store email in session
		            
		            // Check if email exists in the database
		            boolean emailExists = tnaTitleDBService.checkEmailExists(email);

		            if (emailExists) {
		            	TnaTitle userRecord = tnaTitleDBService.getTnaUserDetailByEmail(email);
		            	// Fetch submitted field for the email
		                String submittedStatus = tnaTitleDBService.getSubmittedStatusByEmail(email);
		            	 if ("Yes".equalsIgnoreCase(submittedStatus)) {
		                     // If "submitted" field is yes, redirect to the view page
		                     session.setAttribute("userId", email);
		                     rd = request.getRequestDispatcher("tnaView.jsp"); // Redirect to view page
		                 } else {
		                // Fetch user data for the email and populate
		                
		                
		                session.setAttribute("userId", email);
		                request.setAttribute("userRecords", userRecord);
		                System.out.println("User Record Email: " + userRecord.getEmail());
		            	System.out.println("User Record State: " + userRecord.getState());
		                
		                rd = request.getRequestDispatcher("tnaForm.jsp"); // Forward to form with pre-filled data
		                 }
		            	 } else {
		                // New user, proceed to tnaForm.jsp with blank data
		            
		            	TnaTitle userRecord = new TnaTitle();
		            	userRecord.setDefaultValues();
		            	session.setAttribute("userId", email);
		            	request.setAttribute("userRecords", userRecord);
		            	System.out.println("User Record Email: " + userRecord.getEmail());
		            	System.out.println("User Record State: " + userRecord.getState());

		               rd = request.getRequestDispatcher("tnaForm.jsp"); // Forward to blank form
		            }
		        } else {
		            request.setAttribute("error", "Email cannot be empty!");
		            rd = request.getRequestDispatcher("tnaForm0.jsp");
		        }
		        rd.forward(request, response);
		    }
		}
		// Assuming this is part of your existing method handling the form page logic
		if (page.equals("tnaForm")) { // first page
		    System.out.println("tnaForm1 called");
		    
		
		    if (opr.equals("next")) {
		    	 // Pass orderNo as a request attribute
		    	
		        // Extract the email parameter from the request
		        String email = request.getParameter("email"); // Assuming you have an email field in the form
		        UserDTO user = new UserDTO();
		        user.setEmail(request.getParameter("email"));
		        // Store the UserDTO object in the session
		        HttpSession session = request.getSession();
		        session.setAttribute("userDTO", user);
		        // Check if the email exists in the database
		        boolean emailExists = tnaTitleDBService.checkEmailExists(email);

		        if (emailExists) {
		            // If the email exists, redirect to a different page indicating that the email already exists
		            System.out.println("Email already exists: " + email);
		            //response.sendRedirect("tnaForm1.jsp"); // Redirect to the email exists page
		        } else {
		            // If email doesn't exist, proceed with the data population logic

		            // Populate the data (category and titles) into tna_title
		            List<String> prgCat = tnaTitleDBService.getPrgCat();

		            for (String code : prgCat) {
		                List<String> prgTitles = tnaTitleDBService.getPrgTitlesByCode(code);

		                if(!code.equals("Additional Titles"))
		                {
		                for (String title : prgTitles) {
		                    tnaTitle = new TnaTitle();
		                    tnaTitle.setRequestParam(request);
		                    tnaTitle.setPrgCat(code);
		                    tnaTitle.setPrgTitle(title);
		                    tnaTitle.setEmail(email); // Set the email for the tnaTitle record

		                    // Create the tnaTitle record in the database
		                    tnaTitleDBService.createTnaTitle(tnaTitle);
		                }
		                }
		                else//insert 10 blank records into tnatitles 
		                {
		            	for (int i=0;i<10;i++) {
		                    tnaTitle = new TnaTitle();
		                    tnaTitle.setRequestParam(request);
		                    tnaTitle.setPrgCat(code);
		                    tnaTitle.setPrgTitle("");
		                    tnaTitle.setEmail(email); // Set the email for the tnaTitle record

		                    // Create the tnaTitle record in the database
		                    tnaTitleDBService.createTnaTitle(tnaTitle);
		                }
		            	
		            	
		            }
		            }
		            
		            // After creating the records, forward to the next page (e.g., tnaForm1.jsp)
		         
		        }
		       
		        rd = request.getRequestDispatcher("tnaForm1.jsp?orderNo=1");
	            rd.forward(request, response);
		    }
		}

		/*
		 * // Controller action method else if (page.equals("tnaForm1")) { // Retrieve
		 * form data if (opr.equals("saveNext")) { Enumeration<String> parameterNames =
		 * request.getParameterNames(); while (parameterNames.hasMoreElements()) {
		 * 
		 * String paramName = parameterNames.nextElement();
		 * 
		 * String[] paramValues = request.getParameterValues(paramName); for (int i = 0;
		 * i < paramValues.length; i++) { String paramValue = paramValues[i];
		 * System.out.println("param name=" + paramName + " and param value=" +
		 * paramValue); }
		 * 
		 * } }
		 * 
		 * // Redirect or forward after successful insertion RequestDispatcher rd1
		 * =request.getRequestDispatcher("successPage.jsp"); rd1.forward(request,
		 * response); }
		 */
		else if (page.equals("tnaForm1")) {
		    int orderNo = Integer.parseInt(request.getParameter("orderNo"));  // Get current orderNo from the request

		    // Handle the Previous button operation
		    if (opr.equals("previous")) {
		        if (orderNo == 1) {
		            String email = (String) request.getSession().getAttribute("userId");
		            TnaTitle userRecord = tnaTitleDBService.getTnaUserDetailByEmail(email);
		            request.setAttribute("userRecords", userRecord);

		            rd = request.getRequestDispatcher("tnaForm.jsp");
		            if (!response.isCommitted()) {
		                rd.forward(request, response);
		            } else {
		                System.out.println("Response already committed, cannot forward!");
		            }
		        } else if (orderNo > 1) {
		            orderNo--;  // Decrease orderNo for the previous page
		        }
		    }

		    // Retrieve form data
		    if (opr.equals("saveNext")) {
		        int maxorderNo = tnaTitleDBService.getTnaMaxOrder();
		        if (orderNo == (maxorderNo - 1)) {
		            rd = request.getRequestDispatcher("tnaForm2.jsp?orderNo=" + maxorderNo);
		            if (!response.isCommitted()) {
		                rd.forward(request, response);
		            } else {
		                System.out.println("Response already committed, cannot forward!");
		            }
		        } else {
		            orderNo++;
		        }

		        // Initialize a map to hold ID and choice pairs
		        Map<Integer, String> choicesMap = new HashMap<>();

		        try {
		            // Iterate over all parameters to extract ch[ID]
		            Enumeration<String> parameterNames = request.getParameterNames();
		            while (parameterNames.hasMoreElements()) {
		                String paramName = parameterNames.nextElement();

		                // Check if the parameter name starts with "ch["
		                if (paramName.startsWith("ch[")) {
		                    // Extract the ID from the parameter name
		                    String idStr = paramName.substring(3, paramName.length() - 1); // Extract numeric part
		                    try {
		                        int id = Integer.parseInt(idStr);

		                        // Get the parameter value (selected choice)
		                        String[] paramValues = request.getParameterValues(paramName);
		                        if (paramValues != null && paramValues.length > 0) {
		                            String selectedChoice = paramValues[0];
		                            choicesMap.put(id, selectedChoice.trim()); // Trim to avoid unwanted spaces
		                        }
		                    } catch (NumberFormatException e) {
		                        System.err.println("Invalid ID format in parameter: " + paramName);
		                    }
		                }
		            }

		            // Debugging: Print the collected ID-choice pairs
		            choicesMap.forEach((id, choice) -> 
		                System.out.println("ID: " + id + ", Choice: " + choice)
		            );

		            // Update the database using updateTnaTitle method
		            TnaTitleDBService dbService = new TnaTitleDBService();
		            for (Map.Entry<Integer, String> entry : choicesMap.entrySet()) {
		                int id = entry.getKey();
		                String choice = entry.getValue();

		                // Ensure choice is not empty before updating
		                if (!choice.isEmpty()) {
		                    dbService.updateTnaTitle(id, choice);
		                    System.out.println("Successfully updated ID " + id + " with choice: " + choice);
		                } else {
		                    System.out.println("Skipping update: No choice provided for ID " + id);
		                }
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		            System.err.println("Error processing saveNext operation: " + e.getMessage());
		        }
		    }

		    // Redirect or forward after successful operation
		    RequestDispatcher rd1 = request.getRequestDispatcher("tnaForm1.jsp?orderNo=" + orderNo);
		    if (!response.isCommitted()) {
		        rd1.forward(request, response);
		    } else {
		        System.out.println("Response already committed, cannot forward!");
		    }
		}

		else if (page.equals("tnaForm2")) {
		    int orderNo = Integer.parseInt(request.getParameter("orderNo"));  // Get current orderNo from the request

		    // Handle the Previous button operation
		    if (opr.equals("previous")) {
		    	 int maxorderNo = tnaTitleDBService.getTnaMaxOrder();
		        if (orderNo == maxorderNo) {
		            // Navigate to the previous form
		            rd = request.getRequestDispatcher("tnaForm1.jsp?orderNo="+(maxorderNo-1));
		            rd.forward(request, response);
		        } else if (orderNo > 1) {
		            orderNo--;  // Decrease orderNo for the previous page
		        }
		    }

		    // Retrieve form data for saving titles and choices
		    if (opr.equals("saveNext")) {
		        
		        // Initialize a map to hold ID and title-choices pairs
		        Map<Integer, String> titlesMap = new HashMap<>();
		        Map<Integer, String> choicesMap = new HashMap<>();

		        try {
		            // Iterate over all parameters to extract titles and choices
		            Enumeration<String> parameterNames = request.getParameterNames();
		            while (parameterNames.hasMoreElements()) {
		                String paramName = parameterNames.nextElement();

		                // Check for title parameters (e.g., titles[ID])
		                if (paramName.startsWith("titles[")) {
		                    // Extract ID from the parameter name
		                    String idStr = paramName.substring(7, paramName.length() - 1); // Extract numeric part
		                    try {
		                        int id = Integer.parseInt(idStr);

		                        // Get the title value (can be modified by user)
		                        String title = request.getParameter(paramName);
		                        if (title != null && !title.trim().isEmpty()) {
		                            titlesMap.put(id, title.trim());  // Store the title
		                        }
		                    } catch (NumberFormatException e) {
		                        System.err.println("Invalid ID format in title parameter: " + paramName);
		                    }
		                }

		                // Check for choice parameters (e.g., ch[ID])
		                if (paramName.startsWith("ch[")) {
		                    // Extract ID from the parameter name
		                    String idStr = paramName.substring(3, paramName.length() - 1);
		                    try {
		                        int id = Integer.parseInt(idStr);

		                        // Get the selected choice
		                        String choice = request.getParameter(paramName);
		                        if (choice != null && !choice.trim().isEmpty()) {
		                            choicesMap.put(id, choice.trim());  // Store the choice
		                        }
		                    } catch (NumberFormatException e) {
		                        System.err.println("Invalid ID format in choice parameter: " + paramName);
		                    }
		                }
		            }

		            // Debugging: Print the collected titles and choices
		            titlesMap.forEach((id, title) -> System.out.println("Title ID: " + id + ", Title: " + title));
		            choicesMap.forEach((id, choice) -> System.out.println("Choice ID: " + id + ", Choice: " + choice));

		            // Update the database using the titlesMap and choicesMap
		            TnaTitleDBService dbService = new TnaTitleDBService();

		            // Update titles
		            for (Map.Entry<Integer, String> entry : titlesMap.entrySet()) {
		                int id = entry.getKey();
		                String title = entry.getValue();

		                if (!title.isEmpty()) {
		                	
		                    dbService.updateTnaTitleTitle(id, title);
		                    System.out.println("Successfully updated title for ID " + id + " with: " + title);
		                } else {
		                    System.out.println("Skipping update: No title provided for ID " + id);
		                }
		            }

		            // Update choices
		            for (Map.Entry<Integer, String> entry : choicesMap.entrySet()) {
		                int id = entry.getKey();
		                String choice = entry.getValue();

		                if (!choice.isEmpty()) {
		                    dbService.updateTnaTitle(id, choice);  // Update choice for the title in database
		                    System.out.println("Successfully updated choice for ID " + id + " with: " + choice);
		                } else {
		                    System.out.println("Skipping update: No choice provided for ID " + id);
		                }
		            }// Retrieve email from session
		            HttpSession session = request.getSession();
		            String email = (String) session.getAttribute("email");

		            // Update the 'submitted' field to 'yes' after titles and choices are saved
		            dbService.updateSubmittedFieldToYes(email);

		        } catch (Exception e) {
		            e.printStackTrace();
		            System.err.println("Error processing saveNext operation for tnaForm2: " + e.getMessage());
		        }
		    }

		    // Redirect or forward after successful operation
		    RequestDispatcher rd1 = request.getRequestDispatcher("tnaFormSubmit.jsp");
		    rd1.forward(request, response);
		}



		else if(page.equals("addNewTnaTitle")) 
		{
			if(opr.equals("save"))
			{
				tnaTitle.setRequestParam(request);
				tnaTitle.displayValues();
				tnaTitleDBService.createTnaTitle(tnaTitle);
				request.setAttribute("tnaTitle",tnaTitle);
				if(pageNo!=0) {//pagination
					int totalPages= tnaTitleDBService.getTotalPages(limit);
					homeURL="tnaTitleCntrl?page=tnaTitleDashboard&opr=showAll&pageNo="+totalPages+"&limit="+limit;
					request.getSession().setAttribute("homeURL", homeURL);
				}
				rd = request.getRequestDispatcher("addNewTnaTitleSuccess.jsp");
				rd.forward(request, response);
			}
		}
		//Begin: modified by Dr PNH on 06-12-2022
		else if(page.equals("updateNextTnaTitle")) 
		{
			if(opr.equals("update"))
			{
				tnaTitle.setRequestParam(request);
				tnaTitleDBService.updateTnaTitle(tnaTitle);
				request.setAttribute("tnaTitle",tnaTitle);
				request.getSession().setAttribute("msg", "Record saved successfully");
				response.sendRedirect("tnaTitleCntrl?page=tnaTitleDashboard&opr=editNext&pageNo=0&limit=0&id=10");			}
		}
		else if(page.equals("saveShowNextTnaTitle")) 
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="tnaTitleCntrl?page=tnaTitleDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0";
			request.getSession().setAttribute("homeURL",homeURL);
			if(opr.equals("addNew")) //save new record
			{
				tnaTitle.setRequestParam(request);
				tnaTitle.displayValues();
				tnaTitleDBService.createTnaTitle(tnaTitle);
				request.setAttribute("tnaTitle",tnaTitle);
				if(pageNo!=0) {//pagination
					int totalPages= tnaTitleDBService.getTotalPages(limit);
					homeURL="tnaTitleCntrl?page=tnaTitleDashboard&opr=showAll&pageNo="+totalPages+"&limit="+limit;
					request.getSession().setAttribute("homeURL", homeURL);
				}
				request.getSession().setAttribute("msg", "Record saved successfully");
				response.sendRedirect("tnaTitleCntrl?page=tnaTitleDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
				//rd = request.getRequestDispatcher("tnaTitleCntrl?page=tnaTitleDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
				//rd.forward(request, response);
			}
			else if(opr.equals("edit"))
			{
				int id = Integer.parseInt(request.getParameter("id"));
				tnaTitle = tnaTitleDBService.getTnaTitle(id);
				request.setAttribute("tnaTitle",tnaTitle);
				
				ArrayList<TnaTitle> tnaTitleList =new ArrayList<TnaTitle>();
				if(pageNo==0)
				tnaTitleList = tnaTitleDBService.getTnaTitleList();
				else { //pagination
					int totalPages= tnaTitleDBService.getTotalPages(limit);
					tnaTitleList = tnaTitleDBService.getTnaTitleList(pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("tnaTitleList",tnaTitleList);
				rd = request.getRequestDispatcher("saveShowNextTnaTitle.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("update"))
			{
				tnaTitle.setRequestParam(request);
				tnaTitleDBService.updateTnaTitle(tnaTitle);
				request.setAttribute("tnaTitle",tnaTitle);
				request.getSession().setAttribute("msg", "Record updated successfully");
				response.sendRedirect("tnaTitleCntrl?page=tnaTitleDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");
			}
			else if(opr.equals("delete"))
			{
					int id = Integer.parseInt(request.getParameter("id"));
					tnaTitle.setId(id);
					tnaTitleDBService.deleteTnaTitle(id);
					request.setAttribute("tnaTitle",tnaTitle);
					request.getSession().setAttribute("msg", "Record deleted successfully");
					response.sendRedirect("tnaTitleCntrl?page=tnaTitleDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");		
			}
			else if(opr.equals("reset")||opr.equals("cancel"))
			{
					response.sendRedirect("tnaTitleCntrl?page=tnaTitleDashboard&opr=saveShowNext&id=10&pageNo=0&limit=0");		
			}
			
		}
		//End: modified by Dr PNH on 06-12-2022
		else if(page.equals("updateTnaTitle")) 
		{
			if(opr.equals("update"))
			{
				tnaTitle.setRequestParam(request);
				tnaTitleDBService.updateTnaTitle(tnaTitle);
				request.setAttribute("tnaTitle",tnaTitle);
				rd = request.getRequestDispatcher("updateTnaTitleSuccess.jsp");
				rd.forward(request, response);
			}
		}
		else if(page.equals("viewTnaTitle")) 
		{
			if(opr.equals("print")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				tnaTitle = tnaTitleDBService.getTnaTitle(id);
				request.setAttribute("tnaTitle",tnaTitle);
				rd = request.getRequestDispatcher("printTnaTitle.jsp");
				rd.forward(request, response);
			}
		}
		else if(page.equals("searchTnaTitle"))
		{
			request.getSession().setAttribute("homePage",page);
			homeURL="tnaTitleCntrl?page="+page+"&opr=showAll&pageNo="+pageNo+"&limit="+limit;
			request.getSession().setAttribute("homeURL",homeURL);
			if(opr.equals("search")) 
			{
				tnaTitle.setRequestParam(request);
				tnaTitle.displayValues();
				request.getSession().setAttribute("tnaTitleSearch",tnaTitle);
				request.setAttribute("opr","search");
				ArrayList<TnaTitle> tnaTitleList =new ArrayList<TnaTitle>();
				if(pageNo==0)
				tnaTitleList = tnaTitleDBService.getTnaTitleList(tnaTitle);
				else { //pagination
					int totalPages=0;
					if(limit==0)
					totalPages=0;
					else
					totalPages=tnaTitleDBService.getTotalPages(tnaTitle,limit);
					pageNo=1;
					tnaTitleList = tnaTitleDBService.getTnaTitleList(tnaTitle,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("tnaTitleList",tnaTitleList);
				rd = request.getRequestDispatcher("searchTnaTitle.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
//begin:code for download/print button
			else if(opr.equals("downloadPrint")) 
			{
				tnaTitle.setRequestParam(request);
				tnaTitle.displayValues();
				request.getSession().setAttribute("tnaTitleSearch",tnaTitle);
				request.setAttribute("opr","tnaTitle");
				ArrayList<TnaTitle> tnaTitleList =new ArrayList<TnaTitle>();
				if(pageNo==0)
				tnaTitleList = tnaTitleDBService.getTnaTitleList(tnaTitle);
				else { //pagination
					int totalPages=0;
					if(limit==0)
					totalPages=0;
					else
					totalPages=tnaTitleDBService.getTotalPages(tnaTitle,limit);
					pageNo=1;
					tnaTitleList = tnaTitleDBService.getTnaTitleList(tnaTitle,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("tnaTitleList",tnaTitleList);
				rd = request.getRequestDispatcher("searchTnaTitleDownloadPrint.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			//end:code for download/print button
			
			else if(opr.equals("showAll")) 
			{
				tnaTitle=(TnaTitle)request.getSession().getAttribute("tnaTitleSearch");
				ArrayList<TnaTitle> tnaTitleList =new ArrayList<TnaTitle>();
				if(pageNo==0)
				tnaTitleList = tnaTitleDBService.getTnaTitleList(tnaTitle);
				else { //pagination
					int totalPages= tnaTitleDBService.getTotalPages(tnaTitle,limit);
					tnaTitleList = tnaTitleDBService.getTnaTitleList(tnaTitle,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("tnaTitleList",tnaTitleList);
				rd = request.getRequestDispatcher("searchTnaTitle.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("searchNext")||opr.equals("searchPrev")||opr.equals("searchFirst")||opr.equals("searchLast")) 
			{
				request.setAttribute("opr","search");
				tnaTitle=(TnaTitle)request.getSession().getAttribute("tnaTitleSearch");
				ArrayList<TnaTitle> tnaTitleList =new ArrayList<TnaTitle>();
				if(pageNo==0)
				tnaTitleList = tnaTitleDBService.getTnaTitleList(tnaTitle);
				else { //pagination
					int totalPages= tnaTitleDBService.getTotalPages(tnaTitle,limit);
					tnaTitleList = tnaTitleDBService.getTnaTitleList(tnaTitle,pageNo,limit);
					request.setAttribute("totalPages",totalPages);
				}
				request.setAttribute("tnaTitleList",tnaTitleList);
				rd = request.getRequestDispatcher("searchTnaTitle.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("showNone"))
			{
				tnaTitle.setDefaultValues();
				tnaTitle.displayValues();
				request.getSession().setAttribute("tnaTitleSearch",tnaTitle);
				request.setAttribute("opr","showNone");
				rd = request.getRequestDispatcher("searchTnaTitle.jsp?pageNo="+pageNo+"&limit="+limit);
				rd.forward(request, response);
			}
			else if(opr.equals("edit")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				tnaTitle = tnaTitleDBService.getTnaTitle(id);
				request.setAttribute("tnaTitle",tnaTitle);
				rd = request.getRequestDispatcher("updateTnaTitle.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("delete")) 
			{
				int id = Integer.parseInt(request.getParameter("id"));
				tnaTitle.setId(id);
				tnaTitleDBService.deleteTnaTitle(id);
				request.setAttribute("tnaTitle",tnaTitle);
				rd = request.getRequestDispatcher("deleteTnaTitleSuccess.jsp");
				rd.forward(request, response);
			}
			else if(opr.equals("view")) 
			{
 			int id = Integer.parseInt(request.getParameter("id"));
				tnaTitle = tnaTitleDBService.getTnaTitle(id);
				request.setAttribute("tnaTitle",tnaTitle);
				rd = request.getRequestDispatcher("viewTnaTitle.jsp");
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
		URI uri = new URI("page=updateTnaTitle&opr=close&homePage=tnaTitleDashboard");
		String v = uri.getQuery();
		
	}
}