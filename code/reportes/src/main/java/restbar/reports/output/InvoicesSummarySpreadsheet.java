package restbar.reports.output;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import restbar.reports.data.InvoiceSummary;
import restbar.reports.output.SpreadsheetFormatHelper.CellStyles;

/**
 * Generates a spreadsheet for invoices
 * @author amarenco
 *
 */
public class InvoicesSummarySpreadsheet extends SpreadsheetGenerator<InvoiceSummary> {
	
	
	@Override
	protected Row writeColumnNames(SpreadsheetFormatHelper formatHelper, RowBuilder rowBuilder) {
		Row row = rowBuilder.newRow().build();
		CellBuilder cellBuilder = new CellBuilder(row);
		CellStyle headerStyle = formatHelper.getCellStyle(CellStyles.COLUMN_HEADER);
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("date"))
			.withStyle(headerStyle)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("initialInvoice"))
			.withStyle(headerStyle)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("finalInvoice"))
			.withStyle(headerStyle)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("totalInvoices"))
			.withStyle(headerStyle)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("nonTaxable"))
			.withStyle(headerStyle)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("taxable"))
			.withStyle(headerStyle)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("subtotal"))
			.withStyle(headerStyle)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("discount"))
			.withStyle(headerStyle)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("salesTax"))
			.withStyle(headerStyle)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("serviceTax"))
			.withStyle(headerStyle)
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(this.getReportText("total"))
			.withStyle(headerStyle)
			.build();
		
		return row;
	}
	
	
	
	
	@Override
	protected InvoicesSummaryFooterData writeData(SpreadsheetFormatHelper formatHelper, RowBuilder rowBuilder, List<InvoiceSummary> invoicesSummary) {
		InvoicesSummaryFooterData footerData = new InvoicesSummaryFooterData();
		CellStyle moneyStyle = formatHelper.getCellStyle(CellStyles.MONEY);
		CellStyle dateStyle = formatHelper.getCellStyle(CellStyles.DATE);
		
		
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
	protected void writeFooter(SpreadsheetFormatHelper formatHelper, RowBuilder rowBuilder, FooterData footerData) {
		CellBuilder cellBuilder = new CellBuilder(rowBuilder.newRow().build());
		CellStyle plainStyle = formatHelper.getCellStyle(CellStyles.COLUMN_PLAIN_FOOTER);
		CellStyle moneyStyle = formatHelper.getCellStyle(CellStyles.COLUMN_MONEY_FOOTER);
		InvoicesSummaryFooterData data = (InvoicesSummaryFooterData)footerData;
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_BLANK)
			.withStyle(plainStyle)
			.autoSizeColumn()
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_BLANK)
			.withStyle(plainStyle)
			.autoSizeColumn()
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_BLANK)
			.withStyle(plainStyle)
			.autoSizeColumn()
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_NUMERIC)
			.withValue(data.getInvoices())
			.withStyle(plainStyle)
			.autoSizeColumn()
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_NUMERIC)
			.withValue(data.getNonTaxable())
			.withStyle(moneyStyle)
			.autoSizeColumn()
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_NUMERIC)
			.withValue(data.getTaxable())
			.withStyle(moneyStyle)
			.autoSizeColumn()
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_NUMERIC)
			.withValue(data.getSubtotal())
			.withStyle(moneyStyle)
			.autoSizeColumn()
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_NUMERIC)
			.withValue(data.getDiscount())
			.withStyle(moneyStyle)
			.autoSizeColumn()
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_NUMERIC)
			.withValue(data.getSalesTax())
			.withStyle(moneyStyle)
			.autoSizeColumn()
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_NUMERIC)
			.withValue(data.getServiceTax())
			.withStyle(moneyStyle)
			.autoSizeColumn()
			.build();
		
		cellBuilder.newCell()
			.withType(Cell.CELL_TYPE_NUMERIC)
			.withValue(data.getTotal())
			.withStyle(moneyStyle)
			.autoSizeColumn()
			.build();
	}
	
	
	
	
	@Override
	protected String getReportText(String field) {
		return System.getProperty((field == null) ? "report.invoicesSummary" : "report.invoicesSummary." + field);
	}

}
