package elshab7.engineering.elshab7_rss_news_app.MyRssParser;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import elshab7.engineering.elshab7_rss_news_app.Models.MyArticleModel;

class RSSParser {

    public List<MyArticleModel> getRSSFeedItems(String rss_url) {
        List<MyArticleModel> itemsList = new ArrayList<>();
        String rss_feed_xml;
        Log.e("Hhhhhhhh",rss_url);

        rss_feed_xml = this.getXmlFromUrl(rss_url);
        if (rss_feed_xml != null) {
            try {
                Document doc = this.getDomElement(rss_feed_xml);
                // RSS XML document CHANNEL tag
                String TAG_CHANNEL = "channel";
                NodeList nodeList = doc.getElementsByTagName(TAG_CHANNEL);
                Element e = (Element) nodeList.item(0);

                Log.e("hhhhhhhImg","1");
                String TAG_ITEM = "item";
                NodeList items = e.getElementsByTagName(TAG_ITEM);
                for (int i = 0; i < items.getLength(); i++) {
                    Element e1 = (Element) items.item(i);

                    String TAG_TITLE = "title";
                    String title = this.getValue(e1, TAG_TITLE);
                    String TAG_LINK = "link";
                    String link = this.getValue(e1, TAG_LINK);
                    String TAG_DESRIPTION = "description";
                    String description = this.getValue(e1, TAG_DESRIPTION);
                    String TAG_PUB_DATE = "pubDate";
                    String pubdate = this.getValue(e1, TAG_PUB_DATE);
                    String TAG_GUID = "guid";
                    String guid = this.getValue(e1, TAG_GUID);
                    String TAG_IMG = "media:thumbnail url";
                    String img = this.getValue(e1, TAG_IMG);

                    Log.e("hhhhhhhImg2",img);

                    //RSSItem rssItem = new RSSItem(title, link, description, pubdate, guid);
                    MyArticleModel rssItem = new MyArticleModel(title, link, img, pubdate);

                    // adding item to list
                    itemsList.add(rssItem);
                }
            } catch (Exception e) {
                // Check log for errors
                e.printStackTrace();
            }
        }

        // return item list
        return itemsList;
    }


    private String getXmlFromUrl(String url) {
        String xml = null;

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return XML
        return xml;
    }


    private Document getDomElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }

        return doc;
    }

    private String getElementValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE || (child.getNodeType() == Node.CDATA_SECTION_NODE)) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }


    private String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }
}
