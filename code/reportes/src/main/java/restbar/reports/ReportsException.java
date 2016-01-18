package restbar.reports;

import java.text.MessageFormat;

public class ReportsException extends Exception {

	private static final long serialVersionUID = 201512311813L;
	private int errorCode;

	public static final int NO_CONFIG = 0;
	public static final int INITIALIZE_DATABASE = 1;
	public static final int PARSE_HEADERS = 2;
	public static final int READ_HEADERS = 3;
	public static final int HEADERS_DATA = 4;
	public static final int OUTPUT_FILE = 5;
	public static final int NO_DATA = 6;

	/**
	 * Default Constructor
	 */
	public ReportsException() {
		super();
	}

	/**
	 * @param message
	 * @param ex
	 */
	public ReportsException(int errorCode, String message, Throwable ex) {
		super(message, ex);
		this.errorCode = errorCode;
	}

	/**
	 * @param message
	 */
	public ReportsException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * @param ex
	 */
	public ReportsException(Throwable ex) {
		super(ex);
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}
	
	@Override
	public String getLocalizedMessage() {
		String message = null;
		switch(errorCode) {
		case NO_CONFIG: {
			message = "No se pudo leer el archivo de configuración";
			break;
		}
		
		case INITIALIZE_DATABASE: {
			message = "Imposible inicializar la conexión a la base de datos de RestBar";
			break;
		}
		
		case PARSE_HEADERS: {
			message = "No se pudo obtener datos del archivo de encabezados";
			break;
		}
		
		case READ_HEADERS: {
			message = "No se pudo leer el archivo de encabezados";
			break;
		}
		
		case HEADERS_DATA: {
			message = "No se pudo encontrar el encabezado en el archivo";
			break;
		}
		
		case OUTPUT_FILE: {
			message = "No se pudo crear el archivo de salida";
			break;
		}
		
		case NO_DATA: {
			message = "No se pudieron obtener datos de RestBar";
			break;
		}
		
		
		default: {
			message = super.getLocalizedMessage();
			break;
		}
		}
		
		return MessageFormat.format("{0} [error: {1}]", message, errorCode);
	}
}
