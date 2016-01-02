package restbar.reports.output;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import restbar.reports.data.Invoice;

/**
 * Generates a spreadsheet for invoices
 * @author amarenco
 *
 */
public class InvoicesSpreadsheet extends SpreadsheetGenerator<Invoice> {
	
	/**
	 * Generates a spreadsheet with the given list of invoices
	 * @param outputName Output file name
	 * @param invoices List of invoices
	 * @throws IOException
	 */
	@Override
	public void generate(String outputName, List<Invoice> invoices) throws IOException {
	}

	@Override
	protected void writeColumnNames(HSSFWorkbook workbook, RowBuilder rowBuilder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected InvoicesFooterData writeData(HSSFWorkbook workbook, RowBuilder rowBuilder,
			List<Invoice> data) {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	protected String getReportText(String field) {
		return System.getProperty((field == null) ? "report.invoices" : "report.invoices." + field);
	}

	@Override
	protected void writeFooter(HSSFWorkbook workbook, SpreadsheetGenerator<Invoice>.RowBuilder rowBuilder,
			FooterData footerData) {
		// TODO Auto-generated method stub
		
	}

}
