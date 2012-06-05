package com.novamente.nlgen.unary;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.beanutils.BeanUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UnaryConvertAlgorithmBuilder {
	public static List<AbstractAlgorithm> parseAlgorithmFromFile(String path) {
		List<AbstractAlgorithm> algs = new ArrayList<AbstractAlgorithm>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(path);
			doc.normalize();
			NodeList nl = doc.getElementsByTagName("algorithm");
			AbstractAlgorithm aa = null;
			for (int i = 0; i < nl.getLength(); i++) {
				Element n = (Element) nl.item(i);
				if (n.hasChildNodes()) {
					NodeList childNodes = n.getChildNodes();
					n.normalize();
					String type = getTagContent(n, "type", true);
					if (type.toLowerCase().equals("script")) {
						aa = new ScriptAlgorithm();
					} else if (type.toLowerCase().equals("class")) {
						try {
							aa = (AbstractAlgorithm) Class.forName(
									getTagContent(n, "class", false))
									.newInstance();
						} catch (InstantiationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						continue;
					}
					for (int j = 0; j < childNodes.getLength(); j++) {
						if (childNodes.item(j) instanceof Element) {
							Element child = (Element) childNodes.item(j);
							try {
								BeanUtils.setProperty(aa, child.getTagName(),
										child.getTextContent().trim());
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					algs.add(aa);
				}
			}
			return algs;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private static String getTagContent(Element n, String tag, boolean normalize) {
		String content = null;
		NodeList nodeList = n.getElementsByTagName(tag);
		if (nodeList != null && nodeList.getLength() > 0) {
			content = nodeList.item(0).getTextContent();
			if (normalize) {
				content = content.trim().toLowerCase();
			}
		}
		return content;
	}

	public static void main(String[] args) {
		parseAlgorithmFromFile("unary-convert-algs.xml");
	}
}
