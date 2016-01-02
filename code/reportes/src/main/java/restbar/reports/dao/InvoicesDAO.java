package restbar.reports.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;

import restbar.reports.data.Invoice;
import restbar.reports.data.InvoiceSummary;

/**
 * Invoices Data Access Object
 * @author amarenco
 *
 */
public class InvoicesDAO {
	private static Log log = LogFactory.getLog(InvoicesDAO.class);
	private final DBConnection connectionManager;
	
	/**
	 * Default Constructor
	 * @param connectionManager Connection Manager
	 */
	public InvoicesDAO(DBConnection connectionManager) {
		this.connectionManager = connectionManager;
	}
	
	
	
	/**
	 * Lists all the invoices in the table
	 * @param fromDate Initial date for the range filter (or <code>null</code>)
	 * @param toDate Final date for the range filter (or <code>null</code>)
	 * @return {@link List} with the invoices
	 */
	public List<Invoice> listAllInvoices(LocalDate fromDate, LocalDate toDate) {
		List<Invoice> result = null;
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try
		{
			connection = connectionManager.getConnection();
			StringBuilder sql = new StringBuilder("SELECT ")
					.append(Invoice.ALL_COLUMNS)
					.append(" FROM " + Invoice.TABLE_NAME);
			
			if((fromDate != null) && (toDate != null)) {
				sql.append(" WHERE Fecha >= ? AND Fecha <= ?");
			} else if(fromDate != null) {
				sql.append(" WHERE Fecha >= ?");
			} else if(toDate != null) {
				sql.append(" WHERE Fecha <= ?");
			}
			
			sql.append(" ORDER BY invoiceDate, invoiceNumber");
			stmt = connection.prepareStatement(sql.toString());
			
			
			if((fromDate != null) && (toDate != null)) {
				stmt.setDate(1, new Date(fromDate.toDate().getTime()));
				stmt.setDate(2, new Date(toDate.toDate().getTime()));
			} else if(fromDate != null) {
				stmt.setDate(1, new Date(fromDate.toDate().getTime()));
			} else if(toDate != null) {
				stmt.setDate(1, new Date(toDate.toDate().getTime()));
			}
			
			log.info("SQL Query: " + sql.toString() + " [fromDate: " + fromDate + "] [toDate: " + toDate + "]");
			
			result = this.executeInvoicesQuery(stmt);
		}
		catch(SQLException ex) {
			log.error(ex.getMessage(), ex);
		}
		finally {
			DBUtils.close(stmt);
			DBUtils.close(connection);
		}
		
		return result;
	}
	
	

	/**
	 * Lists all the invoices in the table
	 * @return {@link List} with the invoices
	 */
	public List<Invoice> listAllInvoices() {
		return listAllInvoices(null, null);
	}
	
	
	
	/**
	 * Lists a summary of the invoices in the table
	 * @param fromDate Initial date for the range filter (or <code>null</code>)
	 * @param toDate Final date for the range filter (or <code>null</code>)
	 * @return {@link List} with summary of the invoices
	 */
	public List<InvoiceSummary> listInvoicesSummary(LocalDate fromDate, LocalDate toDate) {
		List<InvoiceSummary> result = null;
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try
		{
			connection = connectionManager.getConnection();
			StringBuilder sql = new StringBuilder("SELECT ")
					.append(InvoiceSummary.ALL_COLUMNS)
					.append(" FROM " + Invoice.TABLE_NAME);
			
			if((fromDate != null) && (toDate != null)) {
				sql.append(" WHERE Fecha >= ? AND Fecha <= ?");
			} else if(fromDate != null) {
				sql.append(" WHERE Fecha >= ?");
			} else if(toDate != null) {
				sql.append(" WHERE Fecha <= ?");
			}
			
			sql.append(" GROUP BY invoiceDate ")
				.append(" ORDER BY invoiceDate");
			stmt = connection.prepareStatement(sql.toString());
			
			
			if((fromDate != null) && (toDate != null)) {
				stmt.setDate(1, new Date(fromDate.toDate().getTime()));
				stmt.setDate(2, new Date(toDate.toDate().getTime()));
			} else if(fromDate != null) {
				stmt.setDate(1, new Date(fromDate.toDate().getTime()));
			} else if(toDate != null) {
				stmt.setDate(1, new Date(toDate.toDate().getTime()));
			}
			
			log.info("SQL Query: " + sql.toString() + " [fromDate: " + fromDate + "] [toDate: " + toDate + "]");
			
			result = this.executeInvoicesSummaryQuery(stmt);
		}
		catch(SQLException ex) {
			log.error(ex.getMessage(), ex);
		}
		finally {
			DBUtils.close(stmt);
			DBUtils.close(connection);
		}
		
		return result;
	}
	
	
	
	/**
	 * Executes the give statement and maps the results to the corresponding object
	 * @param stmt {@link PreparedStatement} instance
	 * @return {@link List} of {@link Invoice} objects
	 * @throws SQLException
	 */
	private List<Invoice> executeInvoicesQuery(PreparedStatement stmt) throws SQLException {
		List<Invoice> result = new ArrayList<Invoice>();
		ResultSet rs = stmt.executeQuery();
		
		try
		{
			result = new ArrayList<Invoice>();
			int total = 0;
		
	        while(rs.next())
	        {
	        	Invoice invoice = new Invoice();
	        	invoice.setDate(LocalDate.fromDateFields(rs.getDate("invoiceDate")));
	        	invoice.setInvoiceNumber(rs.getString("invoiceNumber"));
	        	invoice.setNonTaxable(rs.getBigDecimal("nonTaxable"));
	        	invoice.setTaxable(rs.getBigDecimal("taxable"));
	        	invoice.setDiscount(rs.getBigDecimal("discount"));
	        	invoice.setSalesTax(rs.getBigDecimal("salesTax"));
	        	invoice.setServiceTax(rs.getBigDecimal("serviceTax"));
	        	
	        	result.add(invoice);
	        	total++;
	        }
	        
	        log.info(String.valueOf(total) + " results found");
		}
		finally {
			DBUtils.close(rs);
		}
        
		return result;
	}
	
	
	
	/**
	 * Executes the give statement and maps the results to the corresponding object
	 * @param stmt {@link PreparedStatement} instance
	 * @return {@link List} of {@link InvoiceSummary} objects
	 * @throws SQLException
	 */
	private List<InvoiceSummary> executeInvoicesSummaryQuery(PreparedStatement stmt) throws SQLException {
		List<InvoiceSummary> result = new ArrayList<InvoiceSummary>();
		ResultSet rs = stmt.executeQuery();
		
		try
		{
			result = new ArrayList<InvoiceSummary>();
			int total = 0;
		
	        while(rs.next())
	        {
	        	InvoiceSummary invoiceSummary = new InvoiceSummary();
	        	invoiceSummary.setDate(LocalDate.fromDateFields(rs.getDate("invoiceDate")));
	        	invoiceSummary.setInitialInvoice(rs.getString("initialInvoice"));
	        	invoiceSummary.setFinalInvoice(rs.getString("finalInvoice"));
	        	invoiceSummary.setTotalInvoices(rs.getInt("totalInvoices"));
	        	invoiceSummary.setNonTaxable(rs.getBigDecimal("nonTaxable"));
	        	invoiceSummary.setTaxable(rs.getBigDecimal("taxable"));
	        	invoiceSummary.setDiscount(rs.getBigDecimal("discount"));
	        	invoiceSummary.setSalesTax(rs.getBigDecimal("salesTax"));
	        	invoiceSummary.setServiceTax(rs.getBigDecimal("serviceTax"));
	        	
	        	result.add(invoiceSummary);
	        	total++;
	        }
	        
	        log.info(String.valueOf(total) + " results found");
		}
		finally {
			DBUtils.close(rs);
		}
        
		return result;
	}
}
