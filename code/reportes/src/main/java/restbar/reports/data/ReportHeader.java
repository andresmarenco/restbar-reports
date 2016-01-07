package restbar.reports.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Report header
 * @author amarenco
 *
 */
public class ReportHeader implements Serializable {
	
	private static final long serialVersionUID = 201601062239L;
	
	private String id;
	private String name;
	private List<String> text;
	
	/**
	 * Default Constructor
	 */
	public ReportHeader() {
		this.text = new ArrayList<String>();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the text
	 */
	public List<String> getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(List<String> text) {
		this.text = text;
	}
	
	/**
	 * Adds a line to the text list
	 * @param line Line to add
	 */
	public void addTextLine(String line) {
		this.text.add(line);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReportHeader [id=" + id + ", name=" + name + ", text=" + text
				+ "]";
	}
}
