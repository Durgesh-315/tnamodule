package tna.tnaReport;

import java.util.ArrayList;

public class TnaReportService {

	public void displayList(ArrayList<TnaReport> tnaReportList)
	{
		tnaReportList.forEach((tnaReport) -> print(tnaReport));
	}
	
	public void print(TnaReport tnaReport)
	{
		tnaReport.displayValues();
	}
	
}
