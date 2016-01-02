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
        	LocalDate toDate = new LocalDate(2015, 11, 5);
        	
        	ReportsController controller = new ReportsController();
        	
        	controller.generateInvoicesSummaryReport(fromDate, toDate);
        	
        	
        	
//        	Connection connection = connManager.getConnection();
//        	
//            String sql="SELECT orden FROM FACTURA1";// usual sql query
//            Statement stmt=connection.createStatement();
//            ResultSet resultSet=stmt.executeQuery(sql);
//            while(resultSet.next())
//            {
//                System.out.println(resultSet.getString(1));
//            }
//            System.out.println();
//            
//            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
