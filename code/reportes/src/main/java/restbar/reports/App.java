package restbar.reports;

import org.joda.time.LocalDate;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        
        try
        {
        	Config.loadConfig();
        	
        	
        	LocalDate fromDate = new LocalDate(2015, 11, 1);
        	LocalDate toDate = new LocalDate(2015, 11, 30);
        	
        	ReportsController controller = new ReportsController();
        	
//        	controller.generateInvoicesReport("1", fromDate, toDate);
        	controller.generateInvoicesSummaryReport("1", fromDate, toDate);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
