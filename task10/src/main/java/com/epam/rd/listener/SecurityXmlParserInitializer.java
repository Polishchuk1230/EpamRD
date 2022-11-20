package com.epam.rd.listener;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanNames;
import com.epam.rd.entity.Role;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityXmlParserInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Document document = null;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(sce.getServletContext().getInitParameter("securitySettingsPath"));

            Map<String, List<Role>> constraints = new HashMap<>();

            Node security = document.getChildNodes().item(0);
            NodeList constraintNodes = security.getChildNodes();
            for (int i = 0; i < constraintNodes.getLength(); i++) {
                Node constraint = constraintNodes.item(i);
                if (constraint.getNodeName().equalsIgnoreCase("constraint")) {
                    String pattern = null;
                    List<Role> roles = new ArrayList<>();

                    Node parameter = constraint.getFirstChild();
                    while (parameter != null) {
                        if (parameter.getNodeName().equalsIgnoreCase("url-pattern")) {
                            pattern = parameter.getTextContent();
                        } else if (parameter.getNodeName().equalsIgnoreCase("role")) {
                            roles.add(Role.valueOf(parameter.getTextContent()));
                        }

                        parameter = parameter.getNextSibling();
                    }

                    if (pattern != null) {
                        constraints.put(pattern, roles);
                    }
                }
            }

            ApplicationContext.getInstance().setAttribute(BeanNames.CONSTRAINTS, constraints);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
