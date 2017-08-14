package br.com.dsls.useCaseDiagram;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import br.com.models.UseCaseDiagram;

/**
 *
 * @author Leandro Luque
 */
public class ConvertModes {

    public static String DSLToXML (String dslText) {
        ANTLRInputStream input = new ANTLRInputStream(dslText);
        UseCaseDiagramLexer lexer = new UseCaseDiagramLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        UseCaseDiagramParser parser = new UseCaseDiagramParser(tokens);
        parser.removeErrorListeners(); // remove ConsoleErrorListener
        MyErrorListener eListener = new MyErrorListener();
        parser.addErrorListener(eListener); // add ours

        ParseTree tree = parser.useCaseDiagram();

        ParseTreeWalker walker = new ParseTreeWalker();
        
        DSLToXML xml = new DSLToXML();
        walker.walk(xml, tree);
        
        if(eListener.getErrors().size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (String error : eListener.getErrors()) {
                sb.append(error).append("\n");
            }
            return sb.toString();
        }
        
        return xml.getXML();
    }
    
    public static UseCaseDiagram DSLToJavaObject (String dslText) {
        ANTLRInputStream input = new ANTLRInputStream(dslText);
        UseCaseDiagramLexer lexer = new UseCaseDiagramLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        UseCaseDiagramParser parser = new UseCaseDiagramParser(tokens);
        parser.removeErrorListeners(); // remove ConsoleErrorListener
        parser.addErrorListener(new MyErrorListener()); // add ours

        ParseTree tree = parser.useCaseDiagram();

        ParseTreeWalker walker = new ParseTreeWalker();
        
        DSLToJavaObject obj = new DSLToJavaObject();
        walker.walk(obj, tree);
        
        return obj.getDiagram();
    }
    
    public static String XMLToDSL (String xml) {
        
        StringBuilder ucd = new StringBuilder();
        StringBuilder ucs = new StringBuilder();
        StringBuilder acts = new StringBuilder();
        StringBuilder rels = new StringBuilder();
        Map<String, String> nomes = new HashMap<String, String>();
        
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(xml)));
            
            Element e = doc.getDocumentElement();
            
            ucd.append("Diagrama de Casos de Uso \"".concat(e.getAttribute("name")).concat("\"\n"));
            
            ucs.append("\tCasos de Uso\n");
            
            acts.append("\tAtores\n");
            
            rels.append("\tRelacionamentos\n");
            
            for (int i = 0; i < e.getChildNodes().getLength(); i++) {
                Element n = (Element) e.getChildNodes().item(i);

                if (n.getNodeName().equals("UMLUseCase")) {
                    for (int j = 0; j < n.getChildNodes().getLength(); j++) {
                        Element item = (Element)n.getChildNodes().item(j);
                        if (item.getNodeName().equals("item")) {
                            nomes.put(n.getAttribute("id"), item.getAttribute("value"));
                            ucs.append("\t\t\"".concat(item.getAttribute("value")).concat("\",\n"));
                        }
                    }
                } else if (n.getNodeName().equals("UMLActor")) {
                    for (int j = 0; j < n.getChildNodes().getLength(); j++) {
                        Element item = (Element)n.getChildNodes().item(j);
                        if (item.getNodeName().equals("item")) {
                            nomes.put(n.getAttribute("id"), item.getAttribute("value"));
                            acts.append("\t\t\"".concat(item.getAttribute("value")).concat("\",\n"));
                        }
                    }
                } else {
                    String relName;
                    if(n.getNodeName().equals("UMLCommunication"))
                        relName = " se associa com ";
                    else if(n.getNodeName().equals("UMLInclude"))
                        relName = " inclui ";
                    else
                        relName = " estende ";
                    
                    rels.append("\t\t\"".concat(nomes.get(n.getAttribute("side_A"))).concat("\""));
                    rels.append(relName);
                    rels.append("\"".concat(nomes.get(n.getAttribute("side_B"))).concat("\",\n"));
                }
            }
            
            ucs.replace(ucs.length()-2, ucs.length()-1, ".");
            acts.replace(acts.length()-2, acts.length()-1, ".");
            rels.replace(rels.length()-2, rels.length()-1, ".");
            
            ucd.append(acts).append(ucs).append(rels);
            
            System.out.println(ucd.toString());
            
        } catch (SAXException ex) {
            Logger.getLogger(ConvertModes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConvertModes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ConvertModes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ucd.toString();
    }
    
}
