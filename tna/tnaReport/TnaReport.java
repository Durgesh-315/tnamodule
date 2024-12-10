package tna.tnaReport;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class TnaReport {
 
int id;
String prgCat;
String prgTitle;
String choice1;
String choice2;
String choice3;
String choice4;
String choice5;
String choice6;

public int getId()
{
return id;
}
public void setId(int id)
{
this.id=id;
}
public String getPrgCat()
{
return prgCat;
}
public void setPrgCat(String prgCat)
{
this.prgCat=prgCat;
}
public String getPrgTitle()
{
return prgTitle;
}
public void setPrgTitle(String prgTitle)
{
this.prgTitle=prgTitle;
}
public String getChoice1()
{
return choice1;
}
public void setChoice1(String choice1)
{
this.choice1=choice1;
}
public String getChoice2()
{
return choice2;
}
public void setChoice2(String choice2)
{
this.choice2=choice2;
}
public String getChoice3()
{
return choice3;
}
public void setChoice3(String choice3)
{
this.choice3=choice3;
}
public String getChoice4()
{
return choice4;
}
public void setChoice4(String choice4)
{
this.choice4=choice4;
}
public String getChoice5()
{
return choice5;
}
public void setChoice5(String choice5)
{
this.choice5=choice5;
}
public String getChoice6()
{
return choice6;
}
public void setChoice6(String choice6)
{
this.choice6=choice6;
}


public void setRequestParam(HttpServletRequest request) {

this.setId(null!=request.getParameter("id")&&!request.getParameter("id").equals("")?Integer.parseInt((String)request.getParameter("id")):0);
this.setPrgCat(null!=request.getParameter("prgCat")?request.getParameter("prgCat"):"");
this.setPrgTitle(null!=request.getParameter("prgTitle")?request.getParameter("prgTitle"):"");
this.setChoice1(null!=request.getParameter("choice1")?request.getParameter("choice1"):"");
this.setChoice2(null!=request.getParameter("choice2")?request.getParameter("choice2"):"");
this.setChoice3(null!=request.getParameter("choice3")?request.getParameter("choice3"):"");
this.setChoice4(null!=request.getParameter("choice4")?request.getParameter("choice4"):"");
this.setChoice5(null!=request.getParameter("choice5")?request.getParameter("choice5"):"");
this.setChoice6(null!=request.getParameter("choice6")?request.getParameter("choice6"):"");

}

public void displayReqParam(HttpServletRequest request) {


System.out.println("------Begin:Request Param Values---------");
System.out.println("id = "+request.getParameter("id"));
System.out.println("prgCat = "+request.getParameter("prgCat"));
System.out.println("prgTitle = "+request.getParameter("prgTitle"));
System.out.println("choice1 = "+request.getParameter("choice1"));
System.out.println("choice2 = "+request.getParameter("choice2"));
System.out.println("choice3 = "+request.getParameter("choice3"));
System.out.println("choice4 = "+request.getParameter("choice4"));
System.out.println("choice5 = "+request.getParameter("choice5"));
System.out.println("choice6 = "+request.getParameter("choice6"));

System.out.println("------End:Request Param Values---------");
}

public void displayValues() {

System.out.println("Id = "+this.getId());
System.out.println("PrgCat = "+this.getPrgCat());
System.out.println("PrgTitle = "+this.getPrgTitle());
System.out.println("Choice1 = "+this.getChoice1());
System.out.println("Choice2 = "+this.getChoice2());
System.out.println("Choice3 = "+this.getChoice3());
System.out.println("Choice4 = "+this.getChoice4());
System.out.println("Choice5 = "+this.getChoice5());
System.out.println("Choice6 = "+this.getChoice6());

}

public void setDefaultValues() {

this.setPrgCat("");
this.setPrgTitle("");
this.setChoice1("");
this.setChoice2("");
this.setChoice3("");
this.setChoice4("");
this.setChoice5("");
this.setChoice6("");

}
}