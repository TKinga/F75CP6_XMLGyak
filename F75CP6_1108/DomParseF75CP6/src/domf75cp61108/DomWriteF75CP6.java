package domF75CP61108;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class DomWriteF75CP6 {

	public static void main(String[] args) {
		try {
			File xmlFileInput = new File("orarendF75CP6.xml");
			File xmlFileOutput = new File("orarend1F75CP6.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFileInput);
			DomReadF75CP6.printDocument(document);
			writeDocumentToFile(document, xmlFileOutput);
		} catch (IOException | SAXException | ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}
	}
	public static void writeDocumentToFile(Document document, File output) throws TransformerException, IOException {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(document);
		StreamResult outFile = new StreamResult(output);
		transformer.transform(source, outFile);
		deleteBlankLines(output);
	
	}
	
	public static void deleteBlankLines(File output) throws IOException {


		BufferedReader reader = new BufferedReader(new FileReader(output));
		List<String> lines = new ArrayList<String>();
		String line = reader.readLine();
		while(line != null) {
			if(!line.strip().isEmpty()) 

            {
				lines.add(line);
			}

			line = reader.readLine();
		}

		reader.close();

		FileWriter writer = new FileWriter(output);

		for(String s: lines) {
			writer.write(s+"\n");
		}
		writer.close();
	}

}