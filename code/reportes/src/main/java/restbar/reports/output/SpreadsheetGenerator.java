package restbar.reports.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import restbar.reports.data.ReportHeader;
import restbar.reports.output.SpreadsheetFormatHelper.CellStyles;

/**
 * Base class to generate spreadsheets
 * @author amarenco
 *
 * @param <T> Table object to use in the spreadsheet
 */
public abstract class SpreadsheetGenerator<T> {
	protected static Log log = LogFactory.getLog(SpreadsheetGenerator.class);
	
	
	/**
	 * Writes the column names of the table
	 * @param formatHelper Spreadsheet format helper
	 * @param rowBuilder {@link RowBuilder} instance with the current row count
	 * @return {@link Row} with the column names
	 */
	protected abstract Row writeColumnNames(SpreadsheetFormatHelper formatHelper, RowBuilder rowBuilder);
	
	
	/**
	 * Writes the data from the given list
	 * @param formatHelper Spreadsheet format helper
	 * @param rowBuilder {@link RowBuilder} instance with the current row count
	 * @param data List with data
	 * @return {@link FooterData} instance
	 */
	protected abstract FooterData writeData(SpreadsheetFormatHelper formatHelper, RowBuilder rowBuilder, List<T> data);
	
	
	/**
	 * Writes the column names of the table
	 * @param formatHelper Spreadsheet format helper
	 * @param rowBuilder {@link RowBuilder} instance with the current row count
	 * @param footerData Footer data
	 */
	protected abstract void writeFooter(SpreadsheetFormatHelper formatHelper, RowBuilder rowBuilder, FooterData footerData);
	
	
	/**
	 * Gets the field text for the report from the configuration file
	 * @param field Field to get (or <code>null</code> for the base text)
	 * @return Field text for the report
	 */
	protected abstract String getReportText(String field);
	
	
	
	/**
	 * Gets the base text for the report from the configuration file
	 * @return Base text for the report
	 */
	protected String getReportText() {
		return this.getReportText(null);
	}
	
	
	
	/**
	 * Writes the header of the sheet
	 * @param formatHelper Spreadsheet format helper
	 * @param rowBuilder {@link RowBuilder} instance with the current row count
	 * @param reportName Name of the report
	 * @param reportHeader Report header
	 * @return Number of the last row from the header
	 */
	protected int writeHeader(SpreadsheetFormatHelper formatHelper, RowBuilder rowBuilder, String reportName, ReportHeader reportHeader) {
		// Title of the company
		new CellBuilder(rowBuilder.newRow().build()).newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(reportHeader.getName())
			.withStyle(formatHelper.getCellStyle(CellStyles.MAIN_HEADER))
			.build();
		
		// Text of the header
		for(String line : reportHeader.getText()) {
			new CellBuilder(rowBuilder.newRow().build()).newCell().withType(Cell.CELL_TYPE_STRING).withValue(line).build();
		}
		
		// Empty row
		rowBuilder.newRow().build();
		
		// Name of the report
		new CellBuilder(rowBuilder.newRow().build()).newCell()
			.withType(Cell.CELL_TYPE_STRING)
			.withValue(reportName)
			.withStyle(formatHelper.getCellStyle(CellStyles.REPORT_NAME))
			.build();

		// Empty row
		return rowBuilder.newRow().build().getRowNum();
	}
	
	
	
	/**
	 * Generates a spreadsheet with the given list
	 * @param reportName Name of the report
	 * @param reportHeader Report header
	 * @param data List with data
	 * @throws IOException 
	 */
	public void generate(String reportName, ReportHeader reportHeader, List<T> data) throws IOException {
		log.info(MessageFormat.format("Creating \"{0}\"...", reportName));

		FileOutputStream out = null;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(this.getReportText());
		
		try
		{
			SpreadsheetFormatHelper formatHelper = new SpreadsheetFormatHelper(workbook);
			RowBuilder rowBuilder = new RowBuilder(sheet);
			
			// Header
			int lastHeaderRow = this.writeHeader(formatHelper, rowBuilder, reportName, reportHeader);
			Row columnNamesRow = this.writeColumnNames(formatHelper, rowBuilder);
			rowBuilder.mergeCellsFromRows(lastHeaderRow + 1, columnNamesRow.getLastCellNum() - 1);
			
			// Data + Footer
			FooterData footerData = this.writeData(formatHelper, rowBuilder, data);
			this.writeFooter(formatHelper, rowBuilder, footerData);
		
			// Output the file
			String outputPath = System.getProperty("report.output.path").replace('\\', '/');
			out = new FileOutputStream(new File(MessageFormat.format("{0}/{1}.xls", outputPath, reportName)));
			workbook.write(out);

			log.info("Report successfully generated!");
		}
		finally {
			workbook.close();
			
			if(out != null) {
				out.close();
			}
		}
	}
	
	
	
	
	/**
	 * Helper class to create rows in the spreadsheet
	 * @author amarenco
	 *
	 */
	protected class RowBuilder {
		private int rowNum;
		private HSSFSheet sheet;
		
		/**
		 * Default Constructor
		 * @param sheet Base sheet for the rows
		 */
		public RowBuilder(HSSFSheet sheet) {
			this.rowNum = 0;
			this.sheet = sheet;
		}
		
		
		
		/**
		 * Merges the columns of the individual previous rows
		 * @param row Last row to merge
		 * @param cols Columns to merge
		 */
		public void mergeCellsFromRows(int row, int cols) {
			for(int i = 0; i < row; i++) {
				sheet.addMergedRegion(new CellRangeAddress(i, i, 0, cols));
			}
		}
		
		
		
		/**
		 * Merges the columns of the individual previous rows
		 * @param cols Columns to merge
		 */
		public void mergeCellsFromRows(int cols) {
			this.mergeCellsFromRows(rowNum, cols);
		}
		
		
		
		
		/**
		 * Creates a new {@link RowObject}
		 * @return {@link RowObject} instance
		 */
		public RowObject newRow() {
			return new RowObject();
		}
		
		
		
		
		/**
		 * Used to set up and construct a new row
		 * @author amarenco
		 *
		 */
		protected class RowObject {
			private CellStyle style = null;
			
			/**
			 * Defines the style of the row
			 * @param style Style of the row
			 * @return Current builder
			 */
			public RowObject withStyle(CellStyle style) {
				this.style = style;
				return this;
			}
			
			
			
			/**
			 * Builds the row with the defined parameters
			 * @return {@link Row} instance
			 */
			public Row build() {
				Row row =  sheet.createRow(rowNum++);
				
				if(style != null) {
					row.setRowStyle(style);
				}
				
				return row;
			}
			
		}
	}
	
	
	
	
	/**
	 * Helper class to create cells in the spreadsheet
	 * @author amarenco
	 *
	 */
	protected class CellBuilder {
		private int cellNum;
		private Row row;
		
		/**
		 * Default Constructor
		 * @param row Base row for the cells
		 */
		public CellBuilder(Row row) {
			this.cellNum = 0;
			this.row = row;
		}
		
		
		
		
		/**
		 * Creates a new {@link CellObject}
		 * @return {@link CellObject} instance
		 */
		public CellObject newCell() {
			return new CellObject();
		}
		
		
		
		
		/**
		 * Used to set up and construct a new cell
		 * @author amarenco
		 *
		 */
		protected class CellObject {
			private int type = Integer.MIN_VALUE;
			private Object value = null;
			private CellStyle style = null;
			private boolean autoSize = false;
			
			
			/**
			 * Defines the type of the cell
			 * @param type Type of the cell
			 * @return Current builder
			 */
			public CellObject withType(int type) {
				this.type = type;
				return this;
			}
			
			
			/**
			 * Defines the value of the cell
			 * @param value Value of the cell
			 * @return Current builder
			 */
			public CellObject withValue(Object value) {
				this.value = value;
				return this;
			}
			
			
			/**
			 * Defines the style of the cell
			 * @param style Style of the cell
			 * @return Current builder
			 */
			public CellObject withStyle(CellStyle style) {
				this.style = style;
				return this;
			}
			
			
			
			/**
			 * Sets the auto size property of the sheet in <code>true</code> for the current column
			 * @return Current builder
			 */
			public CellObject autoSizeColumn() {
				this.autoSize = true;
				return this;
			}
			
			
			
			/**
			 * Builds the cell with the defined parameters
			 * @return {@link Cell} instance
			 */
			public Cell build() {
				Cell cell = row.createCell(cellNum);
				if(type != Integer.MIN_VALUE) {
					cell.setCellType(type);
				}
				
				if(style != null) {
					cell.setCellStyle(style);
				}
				
				if(value != null) {
					if(value instanceof Double) {
						cell.setCellValue((Double)value);
					} else if(value instanceof Integer) {
						cell.setCellValue(((Integer)value).doubleValue());
					} else if(value instanceof BigDecimal) {
						cell.setCellValue(((BigDecimal)value).doubleValue());
					} else if(value instanceof String) {
						cell.setCellValue((String)value);
					} else if(value instanceof Date) {
						cell.setCellValue((Date)value);
					} else if(value instanceof Boolean) {
						cell.setCellValue((Boolean)value);
					} else {
						log.error("Unsupported cell type: " + value.getClass());
					}
				}
				

				if(autoSize) {
					row.getSheet().autoSizeColumn(cellNum, true);
				}
				
				cellNum++;
				
				return cell;
			}
		}
	}
}
