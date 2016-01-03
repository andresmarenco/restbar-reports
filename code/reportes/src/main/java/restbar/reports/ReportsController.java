package restbar.reports;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;

import restbar.reports.dao.DBConnection;
import restbar.reports.dao.InvoicesDAO;
import restbar.reports.dao.VFPConnection;
import restbar.reports.data.Invoice;
import restbar.reports.data.InvoiceSummary;
import restbar.reports.output.InvoicesSpreadsheet;
import restbar.reports.output.InvoicesSummarySpreadsheet;
import restbar.reports.output.SpreadsheetGenerator;

/**
 * Controller to handle database requests and spreadsheet creation
 * @author amarenco
 *
 */
public class ReportsController {
	private static Log log = LogFactory.getLog(ReportsController.class);
	private InvoicesDAO invoicesDAO;
	
	/**
	 * Default Constructor 
	 * @throws ReportsException 
	 */
	public ReportsController() throws ReportsException {
		try
		{
			DBConnection connectionManager = new VFPConnection();
			this.invoicesDAO = new InvoicesDAO(connectionManager);
		}
		catch(ClassNotFoundException ex) {
			log.error(ex.getMessage(), ex);
			throw new ReportsException("Could not initialize database connection");
		}
	}
	
	
	
	/**
	 * Generates a spreadsheet with the invoices report
	 * @param fromDate Initial date for the range filter (or null)
	 * @param toDate Final date for the range filter (or null)
	 * @throws ReportsException 
	 */
	public void generateInvoicesReport(LocalDate fromDate, LocalDate toDate) throws ReportsException {
		try
		{
			List<Invoice> invoices = this.invoicesDAO.listAllInvoices(fromDate, toDate);
			SpreadsheetGenerator<Invoice> spreadsheet = new InvoicesSpreadsheet();
			spreadsheet.generate(this.createName("invoices", fromDate, toDate), invoices);
		}
		catch(IOException ex) {
			log.error(ex.getMessage(), ex);
			throw new ReportsException("Could not create ouput file");
		}
		catch(SQLException ex) {
			log.error(ex.getMessage(), ex);
			throw new ReportsException("Could not obtain data from database connection");
		}
	}
	
	
	
	/**
	 * Generates a spreadsheet with the invoices summary report
	 * @param fromDate Initial date for the range filter (or null)
	 * @param toDate Final date for the range filter (or null)
	 * @throws ReportsException 
	 */
	public void generateInvoicesSummaryReport(LocalDate fromDate, LocalDate toDate) throws ReportsException {
		try
		{
			List<InvoiceSummary> invoicesSummary = this.invoicesDAO.listInvoicesSummary(fromDate, toDate);
			SpreadsheetGenerator<InvoiceSummary> spreadsheet = new InvoicesSummarySpreadsheet();
			spreadsheet.generate(this.createName("invoicesSummary", fromDate, toDate), invoicesSummary);
		}
		catch(IOException ex) {
			log.error(ex.getMessage(), ex);
			throw new ReportsException("Could not create ouput file");
		}
		catch(SQLException ex) {
			log.error(ex.getMessage(), ex);
			throw new ReportsException("Could not obtain data from database connection");
		}
	}
	
	
	
	/**
	 * Generates the name for the output file
	 * @param reportName Name of the report
	 * @param fromDate Initial date for the range filter (or null)
	 * @param toDate Final date for the range filter (or null)
	 * @return Name for the output file
	 */
	private String createName(String reportName, LocalDate fromDate, LocalDate toDate) {
		StringBuilder result = new StringBuilder(System.getProperty("report." + reportName));
		
		if(fromDate != null) {
			result.append(" ").append(System.getProperty("report.from")).append(" ").append(fromDate);
		}
		
		if(toDate != null) {
			result.append(" ").append(System.getProperty("report.to")).append(" ").append(toDate);
		}
		
		return result.append(".xls").toString();
	}

}
