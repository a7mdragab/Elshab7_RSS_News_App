package elshab7.engineering.elshab7_rss_news_app.MyRssParser;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import elshab7.engineering.elshab7_rss_news_app.Models.MyArticleModel;

class ReadRss extends AsyncTask<String,Void,Void> {
    private List<MyArticleModel> myArticleList;
    //private Context mContext;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... strings) {
        myArticleList = new ArrayList<>();
        Element root = Getdata(strings[0]).getDocumentElement();
        Node channel = root.getChildNodes().item(1);
        NodeList items = channel.getChildNodes();
        for (int i = 0; i < items.getLength(); i++) {
            Node cureentchild = items.item(i);
            if (cureentchild.getNodeName().equalsIgnoreCase("item")) {
                MyArticleModel item = new MyArticleModel();
                NodeList itemchilds = cureentchild.getChildNodes();
                for (int j = 0; j < itemchilds.getLength(); j++) {
                    Node cureent = itemchilds.item(j);
                    if (cureent.getNodeName().equalsIgnoreCase("title")) {
                        item.setTitle(cureent.getTextContent());
                    } else if (cureent.getNodeName().equalsIgnoreCase("description")) {
                        //item.setDescription(cureent.getTextContent());
                    } else if (cureent.getNodeName().equalsIgnoreCase("pubDate")) {
                        item.setPubDate(cureent.getTextContent());
                    } else if (cureent.getNodeName().equalsIgnoreCase("link")) {
                        item.setLink(cureent.getTextContent());
                    } else if (cureent.getNodeName().equalsIgnoreCase("media:thumbnail")) {
                        //this will return us thumbnail url
                        String url = cureent.getAttributes().item(0).getTextContent();
                        item.setImg(url);
                    }
                }
                myArticleList.add(item);


            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    private Document Getdata(String address) {
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MyArticleModel> getMyArticleList() {
        return myArticleList;
    }
}
