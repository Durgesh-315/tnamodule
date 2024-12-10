package tna.tnaTitle;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class TnaTitle {
 
int id;
String prgCat;
String prgCatValue;
String prgTitle;
String instituteName;
String mobileNo;
String choice;
String choiceValue;
String submitted;
String submittedValue;
String updateBy;
Date updateDt;
Date updateDtFrom;
Date updateDtTo;
String updateTime;
String name;
String email;
String state;
String stateValue;

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
public String getPrgCatValue()
{
return prgCatValue;
}
public void setPrgCat(String prgCat)
{
this.prgCat=prgCat;
}
public void setPrgCatValue(String prgCatValue)
{
this.prgCatValue = prgCatValue;
}
public String getPrgTitle()
{
return prgTitle;
}
public void setPrgTitle(String prgTitle)
{
this.prgTitle=prgTitle;
}
public String getInstituteName()
{
return instituteName;
}
public void setInstituteName(String instituteName)
{
this.instituteName=instituteName;
}
public String getMobileNo()
{
return mobileNo;
}
public void setMobileNo(String mobileNo)
{
this.mobileNo=mobileNo;
}
public String getChoice()
{
return choice;
}
public String getChoiceValue()
{
return choiceValue;
}
public void setChoice(String choice)
{
this.choice=choice;
}
public void setChoiceValue(String choiceValue)
{
this.choiceValue = choiceValue;
}
public String getSubmitted()
{
return submitted;
}
public String getSubmittedValue()
{
return submittedValue;
}
public void setSubmitted(String submitted)
{
this.submitted=submitted;
}
public void setSubmittedValue(String submittedValue)
{
this.submittedValue = submittedValue;
}
public String getUpdateBy()
{
return updateBy;
}
public void setUpdateBy(String updateBy)
{
this.updateBy=updateBy;
}
public Date getUpdateDt()
{
return updateDt;
}
public Date getUpdateDtFrom()
{
return updateDtFrom;
}
public Date getUpdateDtTo()
{
return updateDtTo;
}
public void setUpdateDt(Date updateDt)
{
this.updateDt=updateDt;
}
public void setUpdateDtFrom(Date updateDtFrom)
{
this.updateDtFrom=updateDtFrom;
}
public void setUpdateDtTo(Date updateDtTo)
{
this.updateDtTo=updateDtTo;
}
public String getUpdateTime()
{
return updateTime;
}
public void setUpdateTime(String updateTime)
{
this.updateTime=updateTime;
}
public String getName()
{
return name;
}
public void setName(String name)
{
this.name=name;
}
public String getEmail()
{
return email;
}
public void setEmail(String email)
{
this.email=email;
}
public String getState()
{
return state;
}
public String getStateValue()
{
return stateValue;
}
public void setState(String state)
{
this.state=state;
}
public void setStateValue(String stateValue)
{
this.stateValue = stateValue;
}


public void setRequestParam(HttpServletRequest request) {

this.setId(null!=request.getParameter("id")&&!request.getParameter("id").equals("")?Integer.parseInt((String)request.getParameter("id")):0);
this.setPrgCat(null!=request.getParameter("prgCat")?request.getParameter("prgCat"):"");
this.setPrgTitle(null!=request.getParameter("prgTitle")?request.getParameter("prgTitle"):"");
this.setInstituteName(null!=request.getParameter("instituteName")?request.getParameter("instituteName"):"");
this.setMobileNo(null!=request.getParameter("mobileNo")?request.getParameter("mobileNo"):"");
this.setChoice(null!=request.getParameter("choice")?request.getParameter("choice"):"");
this.setSubmitted(null!=request.getParameter("submitted")?request.getParameter("submitted"):"");
this.setUpdateBy(null!=request.getParameter("updateBy")?request.getParameter("updateBy"):"");
this.setUpdateDt(null!=request.getParameter("updateDt")&&!request.getParameter("updateDt").equals("")?DateService.getSTDYYYYMMDDFormat(request.getParameter("updateDt")):DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdateDtFrom(null!=request.getParameter("updateDtFrom")&&!request.getParameter("updateDtFrom").equals("")?DateService.getSTDYYYYMMDDFormat(request.getParameter("updateDtFrom")):DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdateDtTo(null!=request.getParameter("updateDtTo")&&!request.getParameter("updateDtTo").equals("")?DateService.getSTDYYYYMMDDFormat(request.getParameter("updateDtTo")):DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdateTime(null!=request.getParameter("updateTime")?request.getParameter("updateTime"):"");
this.setName(null!=request.getParameter("name")?request.getParameter("name"):"");
this.setEmail(null!=request.getParameter("email")?request.getParameter("email"):"");
this.setState(null!=request.getParameter("state")?request.getParameter("state"):"");

}

public void displayReqParam(HttpServletRequest request) {


System.out.println("------Begin:Request Param Values---------");
System.out.println("id = "+request.getParameter("id"));
System.out.println("prgCat = "+request.getParameter("prgCat"));
System.out.println("prgTitle = "+request.getParameter("prgTitle"));
System.out.println("instituteName = "+request.getParameter("instituteName"));
System.out.println("mobileNo = "+request.getParameter("mobileNo"));
System.out.println("choice = "+request.getParameter("choice"));
System.out.println("submitted = "+request.getParameter("submitted"));
System.out.println("updateBy = "+request.getParameter("updateBy"));
System.out.println("updateDt = "+request.getParameter("updateDt"));
System.out.println("updateDtFrom = "+request.getParameter("updateDtFrom"));
System.out.println("updateDtTo = "+request.getParameter("updateDtTo"));
System.out.println("updateTime = "+request.getParameter("updateTime"));
System.out.println("name = "+request.getParameter("name"));
System.out.println("email = "+request.getParameter("email"));
System.out.println("state = "+request.getParameter("state"));

System.out.println("------End:Request Param Values---------");
}

public void displayValues() {

System.out.println("Id = "+this.getId());
System.out.println("PrgCat = "+this.getPrgCat());
System.out.println("PrgTitle = "+this.getPrgTitle());
System.out.println("InstituteName = "+this.getInstituteName());
System.out.println("MobileNo = "+this.getMobileNo());
System.out.println("Choice = "+this.getChoice());
System.out.println("Submitted = "+this.getSubmitted());
System.out.println("UpdateBy = "+this.getUpdateBy());
System.out.println("UpdateDt = "+DateService.getDTSYYYMMDDFormat(this.getUpdateDt()));
System.out.println("UpdateDtFrom = "+DateService.getDTSYYYMMDDFormat(this.getUpdateDtFrom()));
System.out.println("UpdateDtTo = "+DateService.getDTSYYYMMDDFormat(this.getUpdateDtTo()));
System.out.println("UpdateTime = "+this.getUpdateTime());
System.out.println("Name = "+this.getName());
System.out.println("Email = "+this.getEmail());
System.out.println("State = "+this.getState());

}

public void setDefaultValues() {

this.setPrgCat("");
this.setPrgTitle("");
this.setInstituteName("");
this.setMobileNo("");
this.setChoice("");
this.setSubmitted("");
this.setUpdateBy("");
this.setUpdateDt(DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdateDtFrom(DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdateDtTo(DateService.getSTDYYYYMMDDFormat("11-11-1111"));
this.setUpdateTime("");
this.setName("");
this.setEmail("");
this.setState("");

}
}