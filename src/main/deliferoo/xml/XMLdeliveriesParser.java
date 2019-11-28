package xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import model.Deliveries;
import model.Delivery;
import model.FullGraph;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class XMLdeliveriesParser {
    
  public enum EnumXMLdeliveriesParser {
      INSTANCE;
  }

    public static Deliveries parser(Document doc, FullGraph graph) throws ParseException {
      
      Deliveries deliveries = null;
      ArrayList<Delivery> listDeliveries = new ArrayList<Delivery>();
      model.Node warehouseAddress =null;
      SimpleDateFormat startTime = new SimpleDateFormat("H:m:s");
      Date date = startTime.parse("8:0:0");//L'heure de depart par defaut est 8h
      
    try {

	
			
	//algorithme de parsing de fichier XML base sur l'exemple trouve sur la page web suivante :
	//https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
	doc.getDocumentElement().normalize();

	System.out.println("Element racine :" + doc.getDocumentElement().getNodeName());
	
	NodeList nInfo = doc.getElementsByTagName("entrepot");
	
	Node nNodeInfo = nInfo.item(0);
	//System.out.println("\nType de l'element courant :" + nNodeInfo.getNodeName());
	
	for (int temp1 = 0; temp1 < nInfo.getLength(); temp1++) {

		Node nNode1 = nInfo.item(temp1);	
		System.out.println("\nElement courant :" + nNode1.getNodeName());
				
		if (nNode1.getNodeType() == Node.ELEMENT_NODE) {

			Element iElement = (Element) nNode1; 
			System.out.println("Date de depart de l'entrepot :" + iElement.getAttribute("heureDepart"));
			date = startTime.parse(iElement.getAttribute("heureDepart"));
			System.out.println("Adresse de l'entrepot :" + iElement.getAttribute("adresse"));
			warehouseAddress = (model.Node) graph.getNodeByID(Long.parseLong(iElement.getAttribute("adresse")));

		}
	}
	
			
	System.out.println("*********************************");
	NodeList nList = doc.getElementsByTagName("livraison");
	

	
	for (int temp = 0; temp < nList.getLength(); temp++) {

		Node nNode = nList.item(temp);
				
		System.out.println("\nElement courant :" + nNode.getNodeName());
				
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) nNode;
			
			SimpleDateFormat deliveryTimepatern = new SimpleDateFormat("m:s");
			
			//delivery duration extraction
			System.out.println("Duree de livraison : " + eElement.getAttribute("dureeLivraison"));
			Integer dureeLivr = Integer.parseInt(eElement.getAttribute("dureeLivraison"));
			Integer min1 = dureeLivr/60;
			Integer sec1 = dureeLivr%60;
			System.out.println("durée de la livraison calculée :" + min1+":"+sec1);
			Date delivDuration = deliveryTimepatern.parse(min1+":"+sec1);
			
			//recovery duration extraction
			System.out.println("Duree d'enlevement : " + eElement.getAttribute("dureeEnlevement"));
			Integer dureeEnlev = Integer.parseInt(eElement.getAttribute("dureeEnlevement"));
			Integer min2 = dureeEnlev/60;
			Integer sec2 = dureeEnlev%60;
			System.out.println("Durée de l'enlèvement calculée : "+ min2+":"+sec2);
			Date pickDuration = deliveryTimepatern.parse(min2+":"+sec2);
			
			//delivery address node extraction
			System.out.println("Adresse de livraison : " + eElement.getAttribute("adresseLivraison"));
			model.Node delivAdr = (model.Node) graph.getNodeByID(Long.parseLong(eElement.getAttribute("adresseLivraison")));
			
			//drop-off address node extraction
			System.out.println("Adresse d'enlevement : " + eElement.getAttribute("adresseEnlevement"));
			model.Node pickAdr = (model.Node) graph.getNodeByID(Long.parseLong(eElement.getAttribute("adresseEnlevement")));
			
			listDeliveries.add(new Delivery(delivAdr, pickAdr, delivDuration,pickDuration));
			

		}
		System.out.println("-----------");
	}
	Delivery[] arrayDeliveries = new Delivery[listDeliveries.size()];
	arrayDeliveries = listDeliveries.toArray(arrayDeliveries);
	
	deliveries = new Deliveries(arrayDeliveries, warehouseAddress, date);
    } catch (Exception e) {
	e.printStackTrace();
    }
    return deliveries;
  }

}