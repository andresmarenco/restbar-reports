package restbar.reports.output;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import restbar.reports.data.InvoiceSummary;

/**
 * Generates a spreadsheet for invoices
 * @author amarenco
 *
 */
public class InvoicesSummarySpreadsheet extends SpreadsheetGenerator<InvoiceSummary> {
	
	
	@Override
	protected void writeColumnNames(HSSFWorkbook workbook, RowBuilder rowBuilder) {
		CellBuilder cellBuilder = new CellBuilder(rowBuilder.newRow().build());
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("date"))
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("initialInvoice"))
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("finalInvoice"))
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("totalInvoices"))
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("nonTaxable"))
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("taxable"))
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("subtotal"))
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("discount"))
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("salesTax"))
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("serviceTax"))
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("total"))
			.build();
	}
	
	
	
	
	@Override
	protected InvoicesSummaryFooterData writeData(HSSFWorkbook workbook, RowBuilder rowBuilder, List<InvoiceSummary> invoicesSummary) {
		InvoicesSummaryFooterData footerData = new InvoicesSummaryFooterData();
		CellStyle moneyStyle = this.createMoneyStyle(workbook);
		CellStyle dateStyle = this.createDateStyle(workbook);
		
		
		for(InvoiceSummary invoiceSummary : invoicesSummary) {
			CellBuilder cellBuilder = new CellBuilder(rowBuilder.newRow().build());
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_STRING)
				.withValue(invoiceSummary.getDate().toDate())
				.withStyle(dateStyle)
				.build();
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_STRING)
				.withValue(invoiceSummary.getInitialInvoice())
				.build();
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_STRING)
				.withValue(invoiceSummary.getFinalInvoice())
				.build();
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_NUMERIC)
				.withValue(invoiceSummary.getTotalInvoices())
				.build();
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_NUMERIC)
				.withValue(invoiceSummary.getNonTaxable())
				.withStyle(moneyStyle)
				.build();
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_NUMERIC)
				.withValue(invoiceSummary.getTaxable())
				.withStyle(moneyStyle)
				.build();
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_NUMERIC)
				.withValue(invoiceSummary.getSubtotal())
				.withStyle(moneyStyle)
				.build();
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_NUMERIC)
				.withValue(invoiceSummary.getDiscount())
				.withStyle(moneyStyle)
				.build();
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_NUMERIC)
				.withValue(invoiceSummary.getSalesTax())
				.withStyle(moneyStyle)
				.build();
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_NUMERIC)
				.withValue(invoiceSummary.getServiceTax())
				.withStyle(moneyStyle)
				.build();
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_NUMERIC)
				.withValue(invoiceSummary.getTotal())
				.withStyle(moneyStyle)
				.build();
			
			footerData.addInvoices(invoiceSummary.getTotalInvoices());
			footerData.addNonTaxable(invoiceSummary.getNonTaxable());
			footerData.addTaxable(invoiceSummary.getTaxable());
			footerData.addDiscount(invoiceSummary.getDiscount());
			footerData.addSalesTax(invoiceSummary.getSalesTax());
			footerData.addServiceTax(invoiceSummary.getServiceTax());
		}
		
		return footerData;
	}
	
	
	
	
	@Override
	protected String getReportText(String field) {
		return System.getProperty((field == null) ? "report.invoicesSummary" : "report.invoicesSummary." + field);
	}




	@Override
	protected void writeFooter(HSSFWorkbook workbook, RowBuilder rowBuilder, FooterData footerData) {
		CellBuilder cellBuilder = new CellBuilder(rowBuilder.newRow().build());
		CellStyle moneyStyle = this.createMoneyStyle(workbook);
		InvoicesSummaryFooterData data = (InvoicesSummaryFooterData)footerData;
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_BLANK)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_BLANK)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_BLANK)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_NUMERIC)
			.withValue(data.getInvoices())
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_NUMERIC)
			.withValue(data.getNonTaxable())
			.withStyle(moneyStyle)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_NUMERIC)
			.withValue(data.getTaxable())
			.withStyle(moneyStyle)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_NUMERIC)
			.withValue(data.getSubtotal())
			.withStyle(moneyStyle)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_NUMERIC)
			.withValue(data.getDiscount())
			.withStyle(moneyStyle)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_NUMERIC)
			.withValue(data.getSalesTax())
			.withStyle(moneyStyle)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_NUMERIC)
			.withValue(data.getServiceTax())
			.withStyle(moneyStyle)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_NUMERIC)
			.withValue(data.getTotal())
			.withStyle(moneyStyle)
			.build();
	}

}
