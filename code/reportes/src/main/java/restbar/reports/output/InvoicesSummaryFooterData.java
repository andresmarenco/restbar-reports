package restbar.reports.output;

import java.math.BigDecimal;

public class InvoicesSummaryFooterData implements FooterData {
	private int invoices;
	private BigDecimal nonTaxable;
	private BigDecimal taxable;
	private BigDecimal discount;
	private BigDecimal salesTax;
	private BigDecimal serviceTax;
	
	/**
	 * Default Constructor
	 */
	public InvoicesSummaryFooterData() {
		this.invoices = 0;
		this.nonTaxable = BigDecimal.ZERO;
		this.taxable = BigDecimal.ZERO;
		this.discount = BigDecimal.ZERO;
		this.salesTax = BigDecimal.ZERO;
		this.serviceTax = BigDecimal.ZERO;
	}
	
	
	public void addInvoices(int invoices) {
		this.invoices += invoices;
	}
	
	public void addNonTaxable(BigDecimal nonTaxable) {
		this.nonTaxable = this.nonTaxable.add(nonTaxable);
	}
	
	public void addTaxable(BigDecimal taxable) {
		this.taxable = this.taxable.add(taxable);
	}
	
	public void addDiscount(BigDecimal discount) {
		this.discount = this.discount.add(discount);
	}
	
	public void addSalesTax(BigDecimal salesTax) {
		this.salesTax = this.salesTax.add(salesTax);
	}
	
	public void addServiceTax(BigDecimal serviceTax) {
		this.serviceTax = this.serviceTax.add(serviceTax);
	}


	/**
	 * @return the invoices
	 */
	public int getInvoices() {
		return invoices;
	}


	/**
	 * @return the nonTaxable
	 */
	public BigDecimal getNonTaxable() {
		return nonTaxable;
	}


	/**
	 * @return the taxable
	 */
	public BigDecimal getTaxable() {
		return taxable;
	}


	/**
	 * @return the discount
	 */
	public BigDecimal getDiscount() {
		return discount;
	}


	/**
	 * @return the salesTax
	 */
	public BigDecimal getSalesTax() {
		return salesTax;
	}


	/**
	 * @return the serviceTax
	 */
	public BigDecimal getServiceTax() {
		return serviceTax;
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
}
