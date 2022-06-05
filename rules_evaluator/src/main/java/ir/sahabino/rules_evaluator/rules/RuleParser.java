package ir.sahabino.rules_evaluator.rules;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RuleParser {
    private String fileName;
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    public RuleParser(String fileName) {
        this.fileName = fileName;
    }

    public void parse() {
        List<Rule> rules = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(fileName));

            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("rule");
            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;
                    String id = element.getAttribute("id");

                    // market
                    String market = element.getElementsByTagName("market").item(0).getTextContent();
                    String operator = element.getElementsByTagName("operator").item(0).getTextContent();
                    //first operand
                    rules.add(new Rule(
                            market,
                            operator,
                            extractOperand(element, "first-operand"),
                            extractOperand(element, "second-operand")
                    ));
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
    private Operand extractOperand(Element element, String operandName){
        NodeList firstOperandE = element.getElementsByTagName(operandName);
        String operandField = firstOperandE.item(0).getAttributes().getNamedItem("field").getTextContent();
        String type = firstOperandE.item(0).getAttributes().getNamedItem("type").getTextContent();
        String period = firstOperandE.item(0).getAttributes().getNamedItem("period").getTextContent();
        return new Operand(operandField, type, period);

    }

    public static void main(String[] args) {
        String file = "./src/main/resources/rules.xml";
        RuleParser ruleParser = new RuleParser(file);
        ruleParser.parse();
    }

}

