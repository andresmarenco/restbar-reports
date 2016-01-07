package restbar.reports.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import restbar.reports.data.ReportHeader;

public class ReportHeaderDAO {
	private static final String headerFilename = System.getProperty("report.header.filename");
	private Document headerDOM;
	
	
	/**
	 * Default Constructor
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public ReportHeaderDAO() throws ParserConfigurationException, SAXException, IOException {
		this.headerDOM = this.readDocument();
	}
	
	
	
	/**
	 * Reads the XML document with the headers
	 * @return DOM instance with the document
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	private Document readDocument() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		return dBuilder.parse(ReportHeaderDAO.class.getClassLoader().getResourceAsStream(headerFilename));
	}
	
	
	
	
	/**
	 * Lists all the available headers
	 * @return List of all headers
	 * @throws XPathExpressionException 
	 */
	public List<ReportHeader> listAllHeaders() throws XPathExpressionException {
		List<ReportHeader> result = new ArrayList<ReportHeader>();
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile("/headers/header");
		
		NodeList nodes = (NodeList)expr.evaluate(headerDOM, XPathConstants.NODESET);
		for(int i = 0; i < nodes.getLength(); i++) {
			Element node = (Element) nodes.item(i);
			Node idNode = (Node) node.getElementsByTagName("id").item(0);
			Node titleNode = (Node) node.getElementsByTagName("title").item(0);
			
			ReportHeader header = new ReportHeader();
			header.setId(idNode.getTextContent());
			header.setName(titleNode.getTextContent());
			
			result.add(header);
		}
		
		return result;
	}
	
	
	
	
	/**
	 * Gets a report header search its id in the XML document
	 * @param id Id to search
	 * @return {@link ReportHeader} instance or <code>null</code>
	 * @throws XPathExpressionException
	 */
	public ReportHeader getHeaderById(String id) throws XPathExpressionException {
		ReportHeader result = null;
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile("/headers/header[id/text() = '" + id + "']");
		
		Element node = (Element) expr.evaluate(headerDOM, XPathConstants.NODE);

		if(node != null) {
			Node idNode = (Node) node.getElementsByTagName("id").item(0);
			Node titleNode = (Node) node.getElementsByTagName("title").item(0);
			NodeList textNodes = node.getElementsByTagName("text");
			
			result = new ReportHeader();
			result.setId(idNode.getTextContent());
			result.setName(titleNode.getTextContent());
			
			for(int i = 0; i < textNodes.getLength(); i++) {
				result.addTextLine(textNodes.item(i).getTextContent());
			}
		}
		
		
		return result;
	}
	

}
