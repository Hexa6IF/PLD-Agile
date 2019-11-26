package xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class XMLdeliveriesParser {

  public static Integer parser(Document doc) {
      Integer count = 0;
      
    try {

	
			
	//optional, but recommended
	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	doc.getDocumentElement().normalize();

	System.out.println("Element racine :" + doc.getDocumentElement().getNodeName());
	
	NodeList nInfo = doc.getElementsByTagName("entrepot");
	
	Node nNodeInfo = nInfo.item(0);
	System.out.println("\nType de l'element courant :" + nNodeInfo.getNodeName());
	
	for (int temp1 = 0; temp1 < nInfo.getLength(); temp1++) {

		Node nNode1 = nInfo.item(temp1);	
		System.out.println("\nElement courant :" + nNode1.getNodeName());
				
		if (nNode1.getNodeType() == Node.ELEMENT_NODE) {

			Element iElement = (Element) nNode1;
			System.out.println("Date de depart de l'entrepot :" + iElement.getAttribute("heureDepart"));
			System.out.println("Adresse de l'entrepot :" + iElement.getAttribute("adresse"));

		}
	}
	
	
	
	
	
	
			
	System.out.println("*********************************");
	NodeList nList = doc.getElementsByTagName("livraison");
	System.out.println("-----------");

	
	for (int temp = 1; temp < nList.getLength(); temp++) {

		Node nNode = nList.item(temp);
				
		System.out.println("\nElement courant :" + nNode.getNodeName());
				
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) nNode;

			System.out.println("Duree de livraison : " + eElement.getAttribute("dureeLivraison"));
			System.out.println("Duree d'enlevement : " + eElement.getAttribute("dureeEnlevement"));
			System.out.println("Adresse de livraison : " + eElement.getAttribute("adresseLivraison"));
			System.out.println("Adresse d'enlevement : " + eElement.getAttribute("adresseEnlevement"));
			
			count = count++;

		}
		System.out.println("-----------");
	}
    } catch (Exception e) {
	e.printStackTrace();
    }
    return count;
  }

}