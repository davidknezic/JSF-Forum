package models;

import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Settings {
	private static Settings instance;
	private HashMap<String, String> map = new HashMap<String, String>();
	
	public Settings() throws Exception {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser		 parser  = factory.newSAXParser();
			
			parser.parse(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/settings.xml")
					, new SettingsHandler());
		} catch(Exception e) {
			throw e;
		}
	}
	
	public static Settings getInstance() throws Throwable {
		if(Settings.instance == null) Settings.instance = new Settings();
		
		return Settings.instance;
	}
	
	private void set(String key, String value) {
		map.put(key, value);
	}
	
	public String get(String key) {
		return map.get(key);
	}
	
	private class SettingsHandler extends DefaultHandler {
		private String currentValue = "";
		private String currentKey = "";

		@Override
		public void startDocument() throws SAXException {
		}

		@Override
		public void endElement(String namespace, String localName, String qName) {
			set(this.currentKey, this.currentValue);
		}

		@Override
		public void startElement(String namespace, String localName, String qName, Attributes attr) {
			this.currentKey = qName;
			this.currentValue = "";
		}

		@Override
		public void characters(char ch[], int start, int length) {
			this.currentValue += new String(ch, start, length).trim();
		}
	}
}
