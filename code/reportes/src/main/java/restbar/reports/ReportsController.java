package restbar.reports;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;
import org.xml.sax.SAXException;

import restbar.reports.dao.DBConnection;
import restbar.reports.dao.InvoicesDAO;
import restbar.reports.dao.ReportHeaderDAO;
import restbar.reports.dao.VFPConnection;
import restbar.reports.data.Invoice;
import restbar.reports.data.InvoiceSummary;
import restbar.reports.data.ReportHeader;
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
	private ReportHeaderDAO reportHeaderDAO;
	
	
	/**
	 * Default Constructor 
	 * @throws ReportsException 
	 */
	public ReportsController() throws ReportsException {
		try
		{
			DBConnection connectionManager = new VFPConnection();
			this.invoicesDAO = new InvoicesDAO(connectionManager);
			this.reportHeaderDAO = new ReportHeaderDAO();
		}
		catch(ClassNotFoundException ex) {
			log.error(ex.getMessage(), ex);
			throw new ReportsException(ReportsException.INITIALIZE_DATABASE, "Could not initialize database connection");
		} catch (ParserConfigurationException ex) {
			log.error(ex.getMessage(), ex);
			throw new ReportsException(ReportsException.PARSE_HEADERS, "Could not parse XML headers file");
		} catch (SAXException ex) {
			log.error(ex.getMessage(), ex);
			throw new ReportsException(ReportsException.PARSE_HEADERS, "Could not create DOM with headers file");
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
			throw new ReportsException(ReportsException.READ_HEADERS, "Could not read XML headers file");
		}
	}
	
	
	
	/**
	 * Lists all the available headers
	 * @return List of all headers
	 * @throws ReportsException 
	 */
	public List<ReportHeader> listAllHeaders() throws ReportsException {
		List<ReportHeader> result = null;
		try
		{
			result = reportHeaderDAO.listAllHeaders();
		} catch (XPathExpressionException ex) {
			log.error(ex.getMessage(), ex);
			throw new ReportsException(ReportsException.HEADERS_DATA, "Could not find the headers in the file (is the format of the XML file correct?)");
		}
		
		return result;
	}
	
	
	
	/**
	 * Generates a spreadsheet with the invoices report
	 * @param headerId Id of the header for the report
	 * @param fromDate Initial date for the range filter (or null)
	 * @param toDate Final date for the range filter (or null)
	 * @throws ReportsException 
	 */
	public void generateInvoicesReport(String headerId, LocalDate fromDate, LocalDate toDate) throws ReportsException {
		try
		{
			List<Invoice> invoices = this.invoicesDAO.listAllInvoices(fromDate, toDate);
			SpreadsheetGenerator<Invoice> spreadsheet = new InvoicesSpreadsheet();
			spreadsheet.generate(this.createName("invoices", fromDate, toDate), this.reportHeaderDAO.getHeaderById(headerId), invoices);
		}
		catch(IOException ex) {
			log.error(ex.getMessage(), ex);
			throw new ReportsException(ReportsException.OUTPUT_FILE, "Could not create ouput file");
		}
		catch(SQLException ex) {
			log.error(ex.getMessage(), ex);
			throw new ReportsException(ReportsException.NO_DATA, "Could not obtain data from database connection");
		}
		catch (XPathExpressionException ex) {
			log.error(ex.getMessage(), ex);
			throw new ReportsException(ReportsException.HEADERS_DATA, "Could not search the header in the file");
		}
	}
	
	
	
	/**
	 * Generates a spreadsheet with the invoices summary report
	 * @param headerId Id of the header for the report
	 * @param fromDate Initial date for the range filter (or null)
	 * @param toDate Final date for the range filter (or null)
	 * @throws ReportsException 
	 */
	public void generateInvoicesSummaryReport(String headerId, LocalDate fromDate, LocalDate toDate) throws ReportsException {
		try
		{
			List<InvoiceSummary> invoicesSummary = this.invoicesDAO.listInvoicesSummary(fromDate, toDate);
			SpreadsheetGenerator<InvoiceSummary> spreadsheet = new InvoicesSummarySpreadsheet();
			spreadsheet.generate(this.createName("invoicesSummary", fromDate, toDate), this.reportHeaderDAO.getHeaderById(headerId), invoicesSummary);
		}
		catch(IOException ex) {
			log.error(ex.getMessage(), ex);
			throw new ReportsException(ReportsException.OUTPUT_FILE, "Could not create ouput file");
		}
		catch(SQLException ex) {
			log.error(ex.getMessage(), ex);
			throw new ReportsException(ReportsException.NO_DATA, "Could not obtain data from database connection");
		}
		catch (XPathExpressionException ex) {
			log.error(ex.getMessage(), ex);
			throw new ReportsException(ReportsException.HEADERS_DATA, "Could not search the header in the file");
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
			result.append(" ").append(System.getProperty("report.from")).append(" ").append(fromDate.toString("dd-MM-yyyy"));
		}
		
		if(toDate != null) {
			result.append(" ").append(System.getProperty("report.to")).append(" ").append(toDate.toString("dd-MM-yyyy"));
		}
		
		return result.toString();
	}

}
