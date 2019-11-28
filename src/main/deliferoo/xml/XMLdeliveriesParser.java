/* Classname
 * 
 * version 4
 * Derniere date de nodification : 28/11/2019
 * 
 * Auteure : GRENIER Marie marie.grenier@insa-lyon.fr
 * */
package xml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class XMLdeliveriesParser {

    /*public enum EnumXMLdeliveriesParser {
	INSTANCE;
    }

    public static List<Delivery> parser(Document doc, FullMap map) throws ParseException {

	ArrayList<Delivery> listDeliveries = new ArrayList<Delivery>();
	model.Node warehouseAddress = null;
	DateTimeFormatter startTimeFormatter = DateTimeFormatter.ofPattern("H:m:s");
	LocalDateTime startTime = LocalDateTime.now();// L'heure de depart par defaut est 8h

	try {

	    // algorithme de parsing de fichier XML base sur l'exemple trouve sur la page
	    // web suivante :
	    // https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
	    doc.getDocumentElement().normalize();

	    NodeList nInfo = doc.getElementsByTagName("entrepot");

	    Node nNodeInfo = nInfo.item(0);
	    // System.out.println("\nType de l'element courant :" +
	    // nNodeInfo.getNodeName());
	    Map<String, model.Node> nodeMap = map.getNodeMap();
	    for (int temp1 = 0; temp1 < nInfo.getLength(); temp1++) {

		Node nNode1 = nInfo.item(temp1);
		System.out.println("\nElement courant :" + nNode1.getNodeName());

		if (nNode1.getNodeType() == Node.ELEMENT_NODE) {

		    Element iElement = (Element) nNode1;
		    System.out.println("Date de depart de l'entrepot :" + iElement.getAttribute("heureDepart"));
		    startTime = LocalDateTime.parse(iElement.getAttribute("heureDepart"), startTimeFormatter);
		    System.out.println("Adresse de l'entrepot :" + iElement.getAttribute("adresse"));
		    warehouseAddress = (model.Node) nodeMap.get(iElement.getAttribute("adresse"));
		    SpecialNode wareHouseSrt = new SpecialNode(warehouseAddress, SpecialNodeType.START, 0.0, startTime);
		    SpecialNode wareHouseFin = new SpecialNode(warehouseAddress, SpecialNodeType.FINISH, 0.0, null);
		    Delivery wareHouseDel = new Delivery(wareHouseSrt, wareHouseFin);
		    listDeliveries.add(wareHouseDel);

		}
	    }

	    System.out.println("*********************************");
	    NodeList nList = doc.getElementsByTagName("livraison");

	    for (int temp = 0; temp < nList.getLength(); temp++) {

		Node nNode = nList.item(temp);

		System.out.println("\nElement courant :" + nNode.getNodeName());

		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

		    Element eElement = (Element) nNode;


		    // delivery duration extraction
		    System.out.println("Duree de livraison : " + eElement.getAttribute("dureeLivraison"));
		    Integer dureeLivr = Integer.parseInt(eElement.getAttribute("dureeLivraison"));
		    Double delivDuration = dureeLivr / 60.0;

		    // recovery duration extraction
		    System.out.println("Duree d'enlevement : " + eElement.getAttribute("dureeEnlevement"));
		    Integer dureeEnlev = Integer.parseInt(eElement.getAttribute("dureeEnlevement"));
		    Double pickDuration = dureeEnlev / 60.0;

		    // delivery address node extraction
		    System.out.println("Adresse de livraison : " + eElement.getAttribute("adresseLivraison"));
		    model.Node delivAdr = nodeMap.get(eElement.getAttribute("adresseLivraison"));

		    // drop-off address node extraction
		    System.out.println("Adresse d'enlevement : " + eElement.getAttribute("adresseEnlevement"));
		    model.Node pickAdr = nodeMap.get(eElement.getAttribute("adresseEnlevement"));

		    SpecialNode startNode = new SpecialNode(pickAdr, SpecialNodeType.PICKUP, pickDuration, null);
		    SpecialNode endNode = new SpecialNode(delivAdr, SpecialNodeType.DROPOFF, delivDuration, null);
		    Delivery delivery = new Delivery(endNode, startNode);
		    listDeliveries.add(delivery);

		}
		System.out.println("-----------");
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return listDeliveries;
    }*/

}