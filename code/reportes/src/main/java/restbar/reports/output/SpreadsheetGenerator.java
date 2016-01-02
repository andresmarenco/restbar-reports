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
import org.apache.poi.ss.usermodel.Workbook;

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
	 * @param workbook Base workbook
	 * @param rowBuilder {@link RowBuilder} instance with the current row count
	 */
	protected abstract void writeColumnNames(HSSFWorkbook workbook, RowBuilder rowBuilder);
	
	
	/**
	 * Writes the data from the given list
	 * @param workbook Base workbook
	 * @param rowBuilder {@link RowBuilder} instance with the current row count
	 * @param data List with data
	 * @return {@link FooterData} instance
	 */
	protected abstract FooterData writeData(HSSFWorkbook workbook, RowBuilder rowBuilder, List<T> data);
	
	
	/**
	 * Writes the column names of the table
	 * @param workbook Base workbook
	 * @param rowBuilder {@link RowBuilder} instance with the current row count
	 * @param footerData Footer data
	 */
	protected abstract void writeFooter(HSSFWorkbook workbook, RowBuilder rowBuilder, FooterData footerData);
	
	
	/**
	 * Gets the field text for the report from the configuration file
	 * @param field Field to get (or <code>null</code> for the base text)
	 * @return Field text for the report
	 */
	protected abstract String getReportText(String field);
	
	

	
	/**
	 * Creates a cell style for money
	 * @param workbook Base workbook
	 * @return {@link CellStyle} instance for money
	 */
	protected CellStyle createMoneyStyle(Workbook workbook) {
		CellStyle moneyStyle = workbook.createCellStyle();
		moneyStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("#,##0.00"));
		
		return moneyStyle;
	}
	
	
	
	
	/**
	 * Creates a cell style for dates
	 * @param workbook Base workbook
	 * @return {@link CellStyle} instance for dates
	 */
	protected CellStyle createDateStyle(Workbook workbook) {
		CellStyle dateStyle = workbook.createCellStyle();
		dateStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy"));
		return dateStyle;
	}
	
	
	
	
	/**
	 * Gets the base text for the report from the configuration file
	 * @return Base text for the report
	 */
	protected String getReportText() {
		return this.getReportText(null);
	}
	
	
	
	/**
	 * Writes the header of the sheet
	 * @param workbook Base workbook
	 * @param rowBuilder {@link RowBuilder} instance with the current row count
	 */
	protected void writeHeader(HSSFWorkbook workbook, RowBuilder rowBuilder) {
		
	}
	
	
	
	/**
	 * Generates a spreadsheet with the given list
	 * @param outputName Output file name
	 * @param data List with data
	 * @throws IOException 
	 */
	public void generate(String outputName, List<T> data) throws IOException {
		log.info(MessageFormat.format("Creating \"{0}\"...", outputName));

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(this.getReportText());
		
		try
		{
			RowBuilder rowBuilder = new RowBuilder(sheet);
			this.writeColumnNames(workbook, rowBuilder);
			FooterData footerData = this.writeData(workbook, rowBuilder, data);
			this.writeFooter(workbook, rowBuilder, footerData);
		
			FileOutputStream out = new FileOutputStream(new File("C:\\" + outputName));
			workbook.write(out);

			log.info("Report successfully generated!");
		}
		finally {
			workbook.close();
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
			 * Builds the cell with the defined parameters
			 * @return {@link Cell} instance
			 */
			public Cell build() {
				Cell cell = row.createCell(cellNum++);
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
				
				return cell;
			}
		}
	}
}
