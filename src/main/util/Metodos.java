package main.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Metodos {

    public void leerXML() {
        try {
            // Parseamos el archivo XML
            File xmlFile = new File("src/main/resources/clientes.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // Normalizamos el documento
            doc.getDocumentElement().normalize();

            // Obtenemos la lista de elementos "cliente"
            NodeList nList = doc.getElementsByTagName("cliente");

            // Recorremos la lista de elementos "cliente"
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    // Obtenemos el valor del atributo "dni" del elemento "cliente"
                    String dni = eElement.getAttribute("DNI");
                    System.out.print("DNI: " + dni);

                    // Obtenemos el valor del elemento "apellidos"
                    String apellidos = eElement.getElementsByTagName("apellidos").item(0).getTextContent();
                    System.out.print(" Apellidos: " + apellidos);

                    // Obtenemos el valor del elemento "CP" en caso de que exista, de lo contrario
                    // conseguimos el codigo postal
                    NodeList validezList = eElement.getElementsByTagName("validez");
                    if ((validezList.getLength() > 0)) {
                        Element validezElement = (Element) validezList.item(0);
                        String validez = validezElement.getAttribute("estado");
                        System.out.print(" Validez: " + validez);
                        String timestamp = validezElement.getAttribute("timestamp");
                        System.out.print(" Timestamp: " + timestamp + "\n");

                    } else {
                        int cp = Integer.parseInt(eElement.getElementsByTagName("CP").item(0).getTextContent());
                        System.out.print(" CP: " + cp + "\n");
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearXML() {
        try {
            // creamos nueva instancia
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Creamos donde almacenar nuestro documento
            Document doc = db.newDocument();
            doc.setXmlVersion("1.0");
            // Damos contenido al XML
            // Creamos los elementos
            Element elClientes = doc.createElement("clientes"); // Creamos la etiqueta cliente
            doc.appendChild(elClientes); // La convertimos en el elemento raiz.
            // creamos el cliente ROJAS
            Element elCliente = doc.createElement("cliente"); // creamos la etiqueta cliente
            elCliente.setAttribute("DNI", "DNIDEPRUEBA");
            Element elApell = doc.createElement("apellidos");
            elApell.appendChild(doc.createTextNode("RYASNY")); // Le damos valor
            elCliente.appendChild(elApell);
            Element cpElement = doc.createElement("CP");
            cpElement.appendChild(doc.createTextNode("03201"));
            elCliente.appendChild(cpElement);
            elClientes.appendChild(elCliente); // lo metemos dentro de clientes
            // Generamos o transformamos el XMLL
            DOMSource domSource = new DOMSource(doc); // Creamos el origen

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            StringWriter sw = new StringWriter();
            StreamResult sr = new StreamResult(sw);
            transformer.transform(domSource, sr);
            System.out.println(sw.toString());
            // Creamos destino
            // Si deseas que tu destino sea en el raiz de tu programa
            File ruta = new File("src/main/resources/clientes.xml");
            FileWriter fw = new FileWriter(ruta);
            PrintWriter pw = new PrintWriter(fw);
            Result restultado = new StreamResult(pw);
            transformer.transform(domSource, restultado);// Generamos el XML
        } catch (ParserConfigurationException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregarCliente() {
        try {
            // Parseamos el archivo XML existente
            File xmlFile = new File("src/main/resources/clientes.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // Obtenemos la lista de elementos "cliente"
            NodeList nList = doc.getElementsByTagName("clientes");

            // Creamos el nuevo elemento "cliente"
            Element elCliente = doc.createElement("cliente");
            elCliente.setAttribute("DNI", "12345678Z");
            // Creamos los elementos hijo de "cliente"
            Element elApellidos = doc.createElement("apellidos");
            elApellidos.setTextContent("Pérez");
            Element elCP = doc.createElement("CP");
            elCP.setTextContent("28080");
            // Añadimos los elementos hijo al elemento "cliente"
            elCliente.appendChild(elApellidos);
            elCliente.appendChild(elCP);

            // Añadimos el nuevo elemento "cliente" a la lista de elementos "cliente"
            nList.item(nList.getLength() - 1).appendChild(elCliente);

            // Creamos el transformador
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/main/resources/clientes.xml"));
            // Configuramos el transformador
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            // Realizamos la transformación
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
