package restbar.reports.output;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import restbar.reports.data.Invoice;
import restbar.reports.output.SpreadsheetFormatHelper.CellStyles;

/**
 * Generates a spreadsheet for invoices
 * @author amarenco
 *
 */
public class InvoicesSpreadsheet extends SpreadsheetGenerator<Invoice> {
	
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
			.withValue(this.getReportText("invoiceNumber"))
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
	protected InvoicesFooterData writeData(SpreadsheetFormatHelper formatHelper, RowBuilder rowBuilder, List<Invoice> invoices) {
		InvoicesFooterData footerData = new InvoicesFooterData();
		CellStyle moneyStyle = formatHelper.getCellStyle(CellStyles.MONEY);
		CellStyle dateStyle = formatHelper.getCellStyle(CellStyles.DATE);
		
		
		for(Invoice invoice : invoices) {
			CellBuilder cellBuilder = new CellBuilder(rowBuilder.newRow().build());
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_STRING)
				.withValue(invoice.getDate().toDate())
				.withStyle(dateStyle)
				.build();
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_STRING)
				.withValue(invoice.getInvoiceNumber())
				.build();
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_NUMERIC)
				.withValue(invoice.getNonTaxable())
				.withStyle(moneyStyle)
				.build();
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_NUMERIC)
				.withValue(invoice.getTaxable())
				.withStyle(moneyStyle)
				.build();
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_NUMERIC)
				.withValue(invoice.getSubtotal())
				.withStyle(moneyStyle)
				.build();
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_NUMERIC)
				.withValue(invoice.getDiscount())
				.withStyle(moneyStyle)
				.build();
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_NUMERIC)
				.withValue(invoice.getSalesTax())
				.withStyle(moneyStyle)
				.build();
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_NUMERIC)
				.withValue(invoice.getServiceTax())
				.withStyle(moneyStyle)
				.build();
			
			cellBuilder.newCell()
				.withType(Cell.CELL_TYPE_NUMERIC)
				.withValue(invoice.getTotal())
				.withStyle(moneyStyle)
				.build();
			
			footerData.addNonTaxable(invoice.getNonTaxable());
			footerData.addTaxable(invoice.getTaxable());
			footerData.addDiscount(invoice.getDiscount());
			footerData.addSalesTax(invoice.getSalesTax());
			footerData.addServiceTax(invoice.getServiceTax());
		}
		
		return footerData;
		
	}

	@Override
	protected void writeFooter(SpreadsheetFormatHelper formatHelper, SpreadsheetGenerator<Invoice>.RowBuilder rowBuilder, FooterData footerData) {
		CellBuilder cellBuilder = new CellBuilder(rowBuilder.newRow().build());
		CellStyle plainStyle = formatHelper.getCellStyle(CellStyles.COLUMN_PLAIN_FOOTER);
		CellStyle moneyStyle = formatHelper.getCellStyle(CellStyles.COLUMN_MONEY_FOOTER);
		InvoicesFooterData data = (InvoicesFooterData)footerData;
		
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
		return System.getProperty((field == null) ? "report.invoices" : "report.invoices." + field);
	}

}
