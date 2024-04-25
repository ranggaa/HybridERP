package driverFactory;

import org.openqa.selenium.WebDriver;

import commonFunction.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
String inputpath ="./FileInput/DataEngine.xlsx";
String outputpath ="./FileOutput/HybridResults.xlsx";
String TestCases ="MasterTestCases";
WebDriver driver;
public void startTest()throws Throwable
{
	String Module_status="";
	String Module_new ="";
	//create object to call excel methods
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//iterate all rows in mastertestcases sheet
	for(int i=1;i<=xl.rowCount(TestCases);i++)
	{
		if(xl.getCellData(TestCases, i, 2).equalsIgnoreCase("Y"))
		{
			//store corresponding sheet into one varibale
			String TCModule =xl.getCellData(TestCases, i, 1);
			//iterate all rows in TCModule
			for(int j=1;j<=xl.rowCount(TCModule);j++)
			{
				//read all cells from TCModule sheet
				String Description = xl.getCellData(TCModule, j, 0);
				String Object_Type =xl.getCellData(TCModule, j, 1);
				String Ltype =xl.getCellData(TCModule, j, 2);
				String LValue = xl.getCellData(TCModule, j, 3);
				String Test_Data = xl.getCellData(TCModule, j, 4);
				try {
					if(Object_Type.equalsIgnoreCase("startBrowser"))
					{
						driver =FunctionLibrary.startBrowser();
					}
					if(Object_Type.equalsIgnoreCase("openUrl"))
					{
						FunctionLibrary.openUrl();
					}
					if(Object_Type.equalsIgnoreCase("waitForElement"))
					{
						FunctionLibrary.waitForElement(Ltype, LValue, Test_Data);
					}
					if(Object_Type.equalsIgnoreCase("typeAction"))
					{
						FunctionLibrary.typeAction(Ltype, LValue, Test_Data);
					}
					if(Object_Type.equalsIgnoreCase("clickAction"))
					{
						FunctionLibrary.clickAction(Ltype, LValue);
					}
					if(Object_Type.equalsIgnoreCase("validateTitle"))
					{
						FunctionLibrary.validateTitle(Test_Data);
					}
					if(Object_Type.equalsIgnoreCase("closeBrowser"))
					{
						FunctionLibrary.closeBrowser();
					}
					if(Object_Type.equalsIgnoreCase("dropDownAction"))
					{
						FunctionLibrary.dropDownAction(Ltype, LValue, Test_Data);
					}
					if(Object_Type.equalsIgnoreCase("captureStock"))
					{
						FunctionLibrary.captureStock(Ltype, LValue);
					}
					if(Object_Type.equalsIgnoreCase("stockTable"))
					{
						FunctionLibrary.stockTable();
					}
					if(Object_Type.equalsIgnoreCase("captuersupplier"))
					{
						FunctionLibrary.captuersupplier(Ltype, LValue);
					}
					if(Object_Type.equalsIgnoreCase("supplierTable"))
					{
						FunctionLibrary.supplierTable();
					}
					if(Object_Type.equalsIgnoreCase("captuerCustomer"))
					{
						FunctionLibrary.captuerCustomer(Ltype, LValue);
					}
					if(Object_Type.equalsIgnoreCase("customerTable"))
					{
						FunctionLibrary.customerTable();
					}
					//write as pass into status cell in TCModule
					xl.setCellData(TCModule, j, 5, "Pass", outputpath);
					Module_status="True";
				}catch(Exception e)
				{
					System.out.println(e.getMessage());
					//write as Fail into status cell in TCModule
					xl.setCellData(TCModule, j, 5, "Fail", outputpath);
					Module_new="False";
				}
				
			}
			if(Module_new.equalsIgnoreCase("False"))
			{
				xl.setCellData(TestCases, i, 3, "Fail", outputpath);
			}
			
			else if(Module_status.equalsIgnoreCase("True"))
			{
				xl.setCellData(TestCases, i, 3, "Pass", outputpath);
			}
			
			
		}
		else
		{
			//write as blocked in status cell for text cases flag to N
			xl.setCellData(TestCases, i, 3, "Blocked", outputpath);
		}
	}
}
}
