package elshab7.engineering.elshab7_rss_news_app.MyRssParser;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import elshab7.engineering.elshab7_rss_news_app.Models.MyArticleModel;

class DOMParser {

	public static RSSFeed parseXML(String feedURL) {

		// Create a new RSS feed
		RSSFeed feed = new RSSFeed();
		// Create a new URL
		URL url = null;
		try {
			// Find the new URL from the given URL
			url = new URL(feedURL);
		} catch (MalformedURLException e) {
			// Throw an exception
			e.printStackTrace();
		}
		Log.e("hhhhhhhhhh","here1");
		try {
			// Create a new DocumentBuilder
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			// Parse the XML
			Document doc = null;
			if (url != null) {
				doc = builder.parse(new InputSource(url.openStream()));
			}
			// Normalize the data
			if (doc != null) {
				doc.getDocumentElement().normalize();
			}

			Log.e("hhhhhhhhhh","here2");
			// Get all <item> tags.
			NodeList list = doc != null ? doc.getElementsByTagName("item") : null;
			// Get size of the list
			int length = 0;
			if (list != null) {
				length = list.getLength();
			}

			Log.e("hhhhhhhhhh","here3" + length);
			// For all the items in the feed
			for (int i = 0; i < length; i++) {
				// Create a new node of the first item
				Node currentNode = list.item(i);
				// Create a new RSS item
				//RSSItem item = new RSSItem();
				MyArticleModel item = new MyArticleModel();

				// Get the child nodes of the first item
				NodeList nodeChild = currentNode.getChildNodes();
				// Get size of the child list
				int cLength = nodeChild.getLength();

				Log.e("hhhhhhhhhh","here4");
				// For all the children of a node
				for (int j = 1; j < cLength; j = j + 2) {
					// Get the name of the child
					String nodeName = nodeChild.item(j).getNodeName(), nodeString = null;
					// If there is at least one child element
					if(nodeChild.item(j).getFirstChild() != null){
						// Set the string to be the value of the node
						nodeString = nodeChild.item(j).getFirstChild().getNodeValue();
					}
					// If the string isn't null
					if (nodeString != null) {
						// Set the appropriate value
						switch (nodeName) {
							case "title":
								item.setTitle(nodeString);
								break;
							case "content:encoded":
								//item.setDescription(nodeString);
								break;
							case "pubDate":
								Locale.setDefault(Locale.getDefault());
								SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

								item.setPubDate(nodeString);
								break;
							case "author":
							case "dc:creator":
								//item.setAuthor(nodeString);
								break;
							case "link":
								item.setLink(nodeString);
								break;
							case "img":
							case "media:content":
								try {
									if (nodeChild.item(j).hasChildNodes()) {
										for (Node child = nodeChild.item(j).getFirstChild(); child != null; child = child.getNextSibling()) {
											if (child.getNodeType() == Node.TEXT_NODE) {
												//return child.getNodeValue();
											}
										}
									}
									//Log.e("hhhhhhImg1", nodeChild.item(j).getLocalName());
									//Log.e("hhhhhhImg2", nodeChild.item(j).getNodeName());
									//Log.e("hhhhhhImg3", nodeChild.item(j).getNodeValue());
									//Log.e("hhhhhhImg4", nodeChild.item(j).getPrefix());
									Log.e("hhhhhhImg5", nodeChild.item(j).getTextContent());
									Log.e("hhhhhhImg6", nodeChild.item(j).getNextSibling().getNodeName());
								}catch (Exception e){}
								item.setImg(nodeString);
								break;
						}
					}
				}
				// Add the new item to the RSS feed
				feed.addItem(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Return the feed
		return feed;
	}
}
