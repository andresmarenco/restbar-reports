package restbar.reports;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import org.joda.time.LocalDate;

import restbar.reports.data.ReportHeader;

public class Main implements ActionListener {

	// Frames
	private JFrame frmMain;
	
	// Labels
	private JLabel lblRestBarPath;
	private JLabel lblOutputPath;
	
	// Buttons
	private JButton btnChangeOutputPath;
	private JButton btnChangeRestBarPath;
	private JButton btnDetailedReport;
	private JButton btnSummaryReport;
	private JButton btnResetStartMonth;
	private JButton btnResetEndMonth;
	private JButton btnExit;
	
	// Text fields
	private JFormattedTextField txtDateFrom;
	private JFormattedTextField txtDateTo;
	
	// Combo Boxes
	private JComboBox<ReportHeader> cmbHeaders;
	
	// Formatters
	private final DateFormat dateFormat;
	private final MaskFormatter dateMask;

	// Reports Controller
	private ReportsController reportsController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	public Main() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, ParseException {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateMask = new MaskFormatter("##/##/####");
		
		Config.loadConfig();
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMain = new JFrame();
		frmMain.setResizable(false);
		frmMain.setTitle("Generador de Reportes");
		frmMain.setBounds(100, 100, 450, 432);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pnlTop = new JPanel();
		frmMain.getContentPane().add(pnlTop, BorderLayout.NORTH);
		
		JLabel lblTitle = new JLabel("Generador de Reportes");
		lblTitle.setForeground(new Color(0, 139, 139));
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		pnlTop.add(lblTitle);
		
		JPanel pnlBottom = new JPanel();
		frmMain.getContentPane().add(pnlBottom, BorderLayout.SOUTH);
		
		btnExit = new JButton("Salir");
		btnExit.addActionListener(this);
		pnlBottom.add(btnExit);
		
		JPanel pnlCenter = new JPanel();
		frmMain.getContentPane().add(pnlCenter, BorderLayout.CENTER);
		pnlCenter.setLayout(new CardLayout(10, 0));
		
		JPanel pnlMain = new JPanel();
		pnlCenter.add(pnlMain, "name_302703667316131");
		pnlMain.setLayout(null);
		
		JPanel pnlConfig = new JPanel();
		pnlConfig.setBounds(0, 11, 414, 108);
		pnlConfig.setBorder(new TitledBorder(null, "Configuraci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlMain.add(pnlConfig);
		pnlConfig.setLayout(new CardLayout(10, 0));
		
		JPanel pnlInnerConfig = new JPanel();
		pnlConfig.add(pnlInnerConfig, "name_303658609287079");
		pnlInnerConfig.setLayout(null);
		
		JLabel lblConfigRestbar = new JLabel("Ubicación de RestBar:");
		lblConfigRestbar.setBounds(0, 0, 127, 42);
		pnlInnerConfig.add(lblConfigRestbar);
		
		lblRestBarPath = new JLabel(System.getProperty("database.path"));
		lblRestBarPath.setToolTipText(lblRestBarPath.getText());
		lblRestBarPath.setBounds(115, 0, 176, 42);
		pnlInnerConfig.add(lblRestBarPath);
		
		btnChangeRestBarPath = new JButton("Cambiar");
		btnChangeRestBarPath.addActionListener(this);
		btnChangeRestBarPath.setBounds(301, 11, 81, 23);
		pnlInnerConfig.add(btnChangeRestBarPath);
		
		JLabel lblConfigOutputPath = new JLabel("Directorio de Salida:");
		lblConfigOutputPath.setBounds(0, 42, 127, 42);
		pnlInnerConfig.add(lblConfigOutputPath);
		
		lblOutputPath = new JLabel(System.getProperty("report.output.path"));
		lblOutputPath.setToolTipText(lblOutputPath.getText());
		lblOutputPath.setBounds(115, 42, 176, 42);
		pnlInnerConfig.add(lblOutputPath);
		
		btnChangeOutputPath = new JButton("Cambiar");
		btnChangeOutputPath.addActionListener(this);
		btnChangeOutputPath.setBounds(301, 53, 81, 23);
		pnlInnerConfig.add(btnChangeOutputPath);
		
		JPanel pnlReport = new JPanel();
		pnlReport.setBounds(0, 130, 414, 208);
		pnlReport.setBorder(new TitledBorder(null, "Reporte", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnlMain.add(pnlReport);
		pnlReport.setLayout(new CardLayout(10, 0));
		
		JPanel pnlInnerReport = new JPanel();
		pnlReport.add(pnlInnerReport, "name_304788055568296");
		pnlInnerReport.setLayout(null);
		
		JLabel lblHeader = new JLabel("Encabezado:");
		lblHeader.setBounds(0, 0, 102, 25);
		pnlInnerReport.add(lblHeader);
		
		cmbHeaders = new JComboBox<ReportHeader>();
		cmbHeaders.setBounds(112, 0, 270, 25);
		pnlInnerReport.add(cmbHeaders);
		
		JLabel lblDateFrom = new JLabel("Desde:");
		lblDateFrom.setBounds(0, 50, 46, 14);
		pnlInnerReport.add(lblDateFrom);
		
		txtDateFrom = new JFormattedTextField(dateFormat);
		txtDateFrom.setBounds(112, 47, 75, 20);
		pnlInnerReport.add(txtDateFrom);
		dateMask.install(txtDateFrom);
		Calendar firstDay = Calendar.getInstance();
		firstDay.set(Calendar.DAY_OF_MONTH, 1);
		txtDateFrom.setText(dateFormat.format(firstDay.getTime()));
		
		JLabel lblDateTo = new JLabel("Hasta:");
		lblDateTo.setBounds(0, 100, 46, 14);
		pnlInnerReport.add(lblDateTo);
		
		txtDateTo = new JFormattedTextField(dateFormat);
		txtDateTo.setBounds(112, 97, 75, 20);
		pnlInnerReport.add(txtDateTo);
		dateMask.install(txtDateTo);
		txtDateTo.setText(dateFormat.format(LocalDate.now().toDate()));
		
		btnDetailedReport = new JButton("Reporte Detallado");
		btnDetailedReport.addActionListener(this);
		btnDetailedReport.setBounds(43, 148, 147, 23);
		pnlInnerReport.add(btnDetailedReport);
		
		btnSummaryReport = new JButton("Reporte Consolidado");
		btnSummaryReport.addActionListener(this);
		btnSummaryReport.setBounds(200, 148, 147, 23);
		pnlInnerReport.add(btnSummaryReport);
		
		btnResetStartMonth = new JButton("Inicio de mes");
		btnResetStartMonth.addActionListener(this);
		btnResetStartMonth.setBounds(197, 46, 120, 23);
		pnlInnerReport.add(btnResetStartMonth);
		
		btnResetEndMonth = new JButton("Fin de mes");
		btnResetEndMonth.addActionListener(this);
		btnResetEndMonth.setBounds(197, 96, 120, 23);
		pnlInnerReport.add(btnResetEndMonth);
		

		this.initializeData();
	}

	
	/**
	 * Actions performed by the form elements
	 */
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == btnChangeRestBarPath) {
			this.changeRestBarPath();
		} else if(event.getSource() == btnChangeOutputPath) {
			this.changeOutputPath();
		} else if(event.getSource() == btnResetStartMonth) {
			this.resetStartMonth();
		} else if(event.getSource() == btnResetEndMonth) {
			this.resetEndMonth();
		} else if(event.getSource() == btnDetailedReport) {
			this.detailedReport();
		} else if(event.getSource() == btnSummaryReport) {
			this.summaryReport();
		} else if(event.getSource() == btnExit) {
			System.exit(0);
		}
	}
	
	
	
	/**
	 * Initializes the reports controller
	 */
	private void initializeData() {
		try
		{
			reportsController = new ReportsController();
			
			for(ReportHeader header : reportsController.listAllHeaders()) {
				cmbHeaders.addItem(header);
			}
			
		} catch (ReportsException ex) {
			JOptionPane.showMessageDialog(frmMain, ex.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
	
	
	
	/**
	 * Lets the user change the RestBar Path
	 */
	private void changeRestBarPath() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		chooser.setDialogTitle("Directorio de la Base de Datos de RestBar");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		
		if (chooser.showOpenDialog(frmMain) == JFileChooser.APPROVE_OPTION) { 
			System.setProperty("database.path", chooser.getSelectedFile().getPath());
			lblRestBarPath.setText(chooser.getSelectedFile().getPath());
			lblRestBarPath.setToolTipText(lblRestBarPath.getText());
		}
	}
	
	
	
	/**
	 * Lets the user change the Output Path
	 */
	private void changeOutputPath() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		chooser.setDialogTitle("Directorio de Salida para los Reportes");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		
		if (chooser.showOpenDialog(frmMain) == JFileChooser.APPROVE_OPTION) { 
			System.setProperty("report.output.path", chooser.getSelectedFile().getPath());
			lblOutputPath.setText(chooser.getSelectedFile().getPath());
			lblOutputPath.setToolTipText(lblOutputPath.getText());
		}
	}
	
	
	
	/**
	 * Generates the detailed report
	 */
	private void detailedReport() {
		try {
			String headerId = ((ReportHeader)cmbHeaders.getSelectedItem()).getId();
			LocalDate fromDate = LocalDate.fromDateFields(dateFormat.parse(txtDateFrom.getText()));
			LocalDate toDate = LocalDate.fromDateFields(dateFormat.parse(txtDateTo.getText()));
			
			if(this.validateDates(fromDate, toDate)) {
				reportsController.generateInvoicesReport(headerId, fromDate, toDate);
				JOptionPane.showMessageDialog(frmMain, "Reporte generado exitosamente!", "Reporte Detallado", JOptionPane.INFORMATION_MESSAGE);
			}
			
		} catch (ReportsException ex) {
			JOptionPane.showMessageDialog(frmMain, ex.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (ParseException ex) {
			JOptionPane.showMessageDialog(frmMain, "Formato de fecha inválido", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	/**
	 * Generates the summary report
	 */
	private void summaryReport() {
		try {
			String headerId = ((ReportHeader)cmbHeaders.getSelectedItem()).getId();
			LocalDate fromDate = LocalDate.fromDateFields(dateFormat.parse(txtDateFrom.getText()));
			LocalDate toDate = LocalDate.fromDateFields(dateFormat.parse(txtDateTo.getText()));
			
			if(this.validateDates(fromDate, toDate)) {
				reportsController.generateInvoicesSummaryReport(headerId, fromDate, toDate);
				JOptionPane.showMessageDialog(frmMain, "Reporte generado exitosamente!", "Reporte Consolidado", JOptionPane.INFORMATION_MESSAGE);
			}
			
		} catch (ReportsException ex) {
			JOptionPane.showMessageDialog(frmMain, ex.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (ParseException ex) {
			JOptionPane.showMessageDialog(frmMain, "Formato de fecha inválido", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	/**
	 * Validates the current dates
	 * @param fromDate Initial date
	 * @param toDate Final date
	 * @return <code>true</code> if the dates are valid
	 */
	private boolean validateDates(LocalDate fromDate, LocalDate toDate) {
		boolean valid = true;
		
		if(fromDate.isAfter(toDate)) {
			JOptionPane.showMessageDialog(frmMain, "La fecha de inicio debe ser menor a la fecha final", "ERROR", JOptionPane.ERROR_MESSAGE);
			valid = false;;
		}
		
		return valid;
	}
	
	
	
	
	/**
	 * Resets the "From Date" to the first day of the month given the "To Date"
	 */
	private void resetStartMonth() {
		try
		{
			LocalDate toDate = LocalDate.fromDateFields(dateFormat.parse(txtDateTo.getText()));

			Calendar firstDay = Calendar.getInstance();
			firstDay.setTime(toDate.toDate());
			firstDay.set(Calendar.DAY_OF_MONTH, 1);

			txtDateFrom.setText(dateFormat.format(firstDay.getTime()));
			
		} catch (ParseException ex) {
			JOptionPane.showMessageDialog(frmMain, "Formato de fecha inválido", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	
	/**
	 * Resets the "To Date" to the last day of the month given the "From Date"
	 */
	private void resetEndMonth() {
		try
		{
			LocalDate fromDate = LocalDate.fromDateFields(dateFormat.parse(txtDateFrom.getText()));

			Calendar lastDay = Calendar.getInstance();
			lastDay.setTime(fromDate.toDate());
			lastDay.set(Calendar.DAY_OF_MONTH, lastDay.getActualMaximum(Calendar.DAY_OF_MONTH));

			txtDateTo.setText(dateFormat.format(lastDay.getTime()));
			
		} catch (ParseException ex) {
			JOptionPane.showMessageDialog(frmMain, "Formato de fecha inválido", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}
