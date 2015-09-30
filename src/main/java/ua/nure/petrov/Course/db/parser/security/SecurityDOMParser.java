package ua.nure.petrov.Course.db.parser.security;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SecurityDOMParser {
    private static final String SECURITY_XML = "security/rules.xml";
    private static final String RULE = "rule";
    private static final String ROLE = "role";
    private static final String URLS = "urls";
    private static final String URL = "url";
    private static final int FIRST = 0;
    private static SecurityDOMParser domParser;

    private final File file = new File(getClass().getClassLoader().getResource(SECURITY_XML).getFile());

    public Set<URI> parseRole(String roleName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(file);

        doc.getDocumentElement().normalize();

        NodeList securityNodes = doc.getElementsByTagName(RULE);
        for (int securityIndex = 0; securityIndex < securityNodes.getLength(); securityIndex++) {
            Node securityNode = securityNodes.item(securityIndex);

            if (isNodeIsElement(securityNode)) {
                Element innerElement = (Element) securityNode;
                NodeList ruleNodes = innerElement.getElementsByTagName(ROLE);

                if (isNodeEqualsCurrentRole(ruleNodes.item(FIRST), roleName)) {
                    NodeList urlNodes = innerElement.getElementsByTagName(URLS);
                    for (int urlIndex = 0; urlIndex < urlNodes.getLength(); urlIndex++) {
                        Node urlNode = urlNodes.item(urlIndex);

                        if (isNodeIsElement(urlNode)) {
                            Element urlElement = (Element) urlNode;

                            NodeList urlList = urlElement.getElementsByTagName(URL);
                            return convertNodeListToSet(urlList);
                        }
                    }
                }
            }
        }
        throw new IllegalArgumentException("No rules for role: " + roleName);
    }

    private boolean isNodeIsElement(Node node) {
        return node.getNodeType() == Node.ELEMENT_NODE;
    }

    private boolean isNodeEqualsCurrentRole(Node node, String role) {
        return role.equals(node.getTextContent());
    }

    private Set<URI> convertNodeListToSet(NodeList nodeList) {
        Set<URI> urls = new HashSet<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node url = nodeList.item(i);
            urls.add(new URI(url.getTextContent()));
        }
        return urls;
    }



    public static SecurityDOMParser getInstance() {
        if (domParser == null) {
            synchronized (SecurityDOMParser.class) {
                if (domParser == null) {
                    domParser = new SecurityDOMParser();
                }
            }
        }
        return domParser;
    }

    private SecurityDOMParser() {
    }
}
