package restbar.reports.data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;

/**
 * Invoice
 * @author amarenco
 *
 */
public class Invoice implements Serializable {

	private static final long serialVersionUID = 201512301331L;
	private static Log log = LogFactory.getLog(Invoice.class);
	
	/** Name of the Invoices table */
	public static final String TABLE_NAME = System.getProperty("database.table.invoices");
	
	/** All the table columns as mapped in the configuration file */
	public static final String ALL_COLUMNS;
	
	
	/**
	 * Map table columns as described in configuration file
	 */
	static {
		StringBuilder result = new StringBuilder();
		
		Iterator<Field> fieldIterator = Arrays.asList(Invoice.class.getDeclaredFields()).iterator();
		
		while(fieldIterator.hasNext()) {
			Field field = fieldIterator.next();
			
			if(!Modifier.isStatic(field.getModifiers())) {
				String fieldSQL = getTableField(field.getName());
				result.append(fieldSQL);
				
				log.debug("Invoice Table: Mapping " + field.getName() + " as SQL: " + fieldSQL);
				
				if(fieldIterator.hasNext()) {
					result.append(", ");
				}
			}
		}
		
		ALL_COLUMNS = result.toString();
	}
	
	private LocalDate date;
	private String invoiceNumber;
	private BigDecimal nonTaxable;
	private BigDecimal taxable;
	private BigDecimal discount;
	private BigDecimal salesTax;
	private BigDecimal serviceTax;
	
	
	/**
	 * Default Constructor
	 */
	public Invoice() {
	}


	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}


	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}


	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}


	/**
	 * @return the nonTaxable
	 */
	public BigDecimal getNonTaxable() {
		return nonTaxable;
	}


	/**
	 * @param nonTaxable the nonTaxable to set
	 */
	public void setNonTaxable(BigDecimal nonTaxable) {
		this.nonTaxable = nonTaxable;
	}


	/**
	 * @return the taxable
	 */
	public BigDecimal getTaxable() {
		return taxable;
	}


	/**
	 * @param taxable the taxable to set
	 */
	public void setTaxable(BigDecimal taxable) {
		this.taxable = taxable;
	}


	/**
	 * @return the discount
	 */
	public BigDecimal getDiscount() {
		return discount;
	}


	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}


	/**
	 * @return the salesTax
	 */
	public BigDecimal getSalesTax() {
		return salesTax;
	}


	/**
	 * @param salesTax the salesTax to set
	 */
	public void setSalesTax(BigDecimal salesTax) {
		this.salesTax = salesTax;
	}


	/**
	 * @return the serviceTax
	 */
	public BigDecimal getServiceTax() {
		return serviceTax;
	}


	/**
	 * @param serviceTax the serviceTax to set
	 */
	public void setServiceTax(BigDecimal serviceTax) {
		this.serviceTax = serviceTax;
	}

	/**
	 * Subtotal: nonTaxable + taxable
	 * @return Sum of nonTaxable + taxable
	 */
	public BigDecimal getSubtotal() {
		return nonTaxable.add(taxable);
	}
	
	/**
	 * Total: subtotal + salesTax + serviceTax
	 * @return Sum of subtotal + salesTax + serviceTax
	 */
	public BigDecimal getTotal() {
		return getSubtotal().add(salesTax).add(serviceTax);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Invoice [date=" + date + ", invoiceNumber=" + invoiceNumber + "]";
	}
	
	/**
	 * Gets the name of the field in the table
	 * @param field Field to lookup
	 * @return Name of the corresponding field
	 */
	public static String getTableField(String field) {
		return System.getProperty("database.table.invoices." + field);
	}
}
