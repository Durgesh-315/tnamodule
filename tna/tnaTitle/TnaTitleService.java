package tna.tnaTitle;

import java.util.ArrayList;

public class TnaTitleService {

	public void displayList(ArrayList<TnaTitle> tnaTitleList)
	{
		tnaTitleList.forEach((tnaTitle) -> print(tnaTitle));
	}
	
	public void print(TnaTitle tnaTitle)
	{
		tnaTitle.displayValues();
	}
	
}
