package restbar.reports.output;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Helper class to format the spreadsheet
 * @author amarenco
 *
 */
public class SpreadsheetFormatHelper {
	private Map<CellStyles, CellStyle> cellStyles;
	private Map<Fonts, Font> fonts;
	
	/**
	 * Default Constructor
	 * @param workbook Base workbook
	 */
	public SpreadsheetFormatHelper(HSSFWorkbook workbook) {
		this.createFonts(workbook);
		this.createCellStyles(workbook);
	}
	
	
	
	
	/**
	 * Populates the map of cell styles
	 * @param workbook Base workbook
	 */
	private void createCellStyles(HSSFWorkbook workbook) {
		this.cellStyles = new HashMap<CellStyles, CellStyle>();
		this.cellStyles.put(CellStyles.MONEY, this.createMoneyStyle(workbook));
		this.cellStyles.put(CellStyles.DATE, this.createDateStyle(workbook));
		this.cellStyles.put(CellStyles.COLUMN_HEADER, this.createColumnHeaderStyle(workbook));
		this.cellStyles.put(CellStyles.COLUMN_PLAIN_FOOTER, this.createColumnFooterStyle(workbook));
		this.cellStyles.put(CellStyles.COLUMN_MONEY_FOOTER, this.createColumnMoneyFooterStyle(workbook));
	}
	
	
	
	
	/**
	 * Populates the map of fonts
	 * @param workbook Base workbook
	 */
	private void createFonts(HSSFWorkbook workbook) {
		this.fonts = new HashMap<Fonts, Font>();
		this.fonts.put(Fonts.BOLD, this.createBoldFont(workbook));
	}
	
	

	
	/**
	 * Creates a cell style for money
	 * @param workbook Base workbook
	 * @return {@link CellStyle} instance for money
	 */
	private CellStyle createMoneyStyle(Workbook workbook) {
		CellStyle moneyStyle = workbook.createCellStyle();
		moneyStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("#,##0.00"));
		
		return moneyStyle;
	}
	
	
	
	
	/**
	 * Creates a cell style for dates
	 * @param workbook Base workbook
	 * @return {@link CellStyle} instance for dates
	 */
	private CellStyle createDateStyle(Workbook workbook) {
		CellStyle dateStyle = workbook.createCellStyle();
		dateStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy"));
		return dateStyle;
	}
	
	
	
	
	/**
	 * Creates a cell style for column header
	 * @param workbook Base workbook
	 * @return {@link CellStyle} instance for column header
	 */
	private CellStyle createColumnHeaderStyle(Workbook workbook) {
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerStyle.setFont(this.getFont(Fonts.BOLD));
		
		return headerStyle;
	}
	
	
	
	
	/**
	 * Creates a cell style for column footer
	 * @param workbook Base workbook
	 * @return {@link CellStyle} instance for column footer
	 */
	private CellStyle createColumnFooterStyle(Workbook workbook) {
		CellStyle footerStyle = workbook.createCellStyle();
		footerStyle.setFont(this.getFont(Fonts.BOLD));
		footerStyle.setBorderTop(CellStyle.BORDER_DOUBLE);
		
		return footerStyle;
	}
	
	
	
	
	/**
	 * Creates a cell style for column money footer
	 * @param workbook Base workbook
	 * @return {@link CellStyle} instance for column money footer
	 */
	private CellStyle createColumnMoneyFooterStyle(Workbook workbook) {
		CellStyle footerStyle = this.createMoneyStyle(workbook);
		footerStyle.setFont(this.getFont(Fonts.BOLD));
		footerStyle.setBorderTop(CellStyle.BORDER_DOUBLE);
		
		return footerStyle;
	}
	
	
	
	
	/**
	 * Creates a bold font
	 * @param workbook Base workbook
	 * @return {@link Font} instance for bold font
	 */
	private Font createBoldFont(Workbook workbook) {
		Font font = workbook.createFont();
		font.setBold(true);
		
		return font;
	}
	
	
	
	/**
	 * Finds the font in the cache
	 * @param font Font to find
	 * @return {@link Font} instance (or <code>null</code>)
	 */
	public Font getFont(Fonts font) {
		return this.fonts.get(font);
	}
	
	
	
	/**
	 * Finds the cell style in the cache
	 * @param style Cell style to find
	 * @return {@link CellStyle} instance (or <code>null</code>)
	 */
	public CellStyle getCellStyle(CellStyles style) {
		return this.cellStyles.get(style);
	}
	
	

	/**
	 * Available fonts in the cache
	 * @author amarenco
	 *
	 */
	public enum Fonts {
		BOLD
	}
	
	
	/**
	 * Available cell styles in the cache
	 * @author amarenco
	 *
	 */
	public enum CellStyles {
		MONEY,
		DATE,
		COLUMN_HEADER,
		COLUMN_PLAIN_FOOTER,
		COLUMN_MONEY_FOOTER
	}
}