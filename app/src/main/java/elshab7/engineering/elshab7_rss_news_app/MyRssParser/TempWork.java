package elshab7.engineering.elshab7_rss_news_app.MyRssParser;


class TempWork {
}

    /*private void fetchLink(final String s) {
        PkRSS.with(this).load(s).callback(new Callback() {
            @Override
            public void OnPreLoad() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                });
            }

            @Override
            public void OnLoaded(List<Article> list) {
                List<MyArticleModel> mList=new ArrayList<>();
                SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
                int c=1;
                for(com.pkmmte.pkrss.Article article:list) {
                    Log.e("hhhhhPkRss " + c, article.getImage().toString());
                    mList.add(new MyArticleModel(article.getTitle(), article.getSource().toString()
                            , article.getImage().toString(), df2.format(article.getDate())));
                }
                try {
                    /*mProvidersModelList.add(new ProviderModel(
                            getDomainName(s),
                            mList));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRssAdapter=new RSS_Adapter(MainActivity.this,mProvidersModelList);
                        mRecyclerView.setAdapter(mRssAdapter);
                        mRssAdapter.notifyDataSetChanged();
                        mProgressBar.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });

            }

            @Override
            public void OnLoadFailed() {

            }
        }).async();
    }*/

    //***************************************************************************************
    //***************************************************************************************
    /*public class ProcessInBackground2 extends AsyncTask<String, Void,Void> {
        RSSFeed feed;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            //feed = DOMParser.parseXML("http://blog.zackehh.com/feed/");
            feed = DOMParser.parseXML(strings[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mProvidersModelList.add(new ProviderModel(
                    "Test1",
                    feed.getItemList()));
            mRssAdapter=new RSS_Adapter(MainActivity.this,mProvidersModelList);
            mRecyclerView.setAdapter(mRssAdapter);
            mRssAdapter.notifyDataSetChanged();
        }
    }*/
    //***************************************************************************************
    //***************************************************************************************
    /*public class ProcessInBackground3 extends AsyncTask<Void, Void,Void>{
        RSSFeed feed;
        List<MyArticleModel> mList=new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... strings) {
            //feed = DOMParser.parseXML("http://blog.zackehh.com/feed/");
            //feed = DOMParser.parseXML(strings[0]);
            mList=new RSSParser().getRSSFeedItems(mNewsProviders.get(0));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mProvidersModelList.add(new ProviderModel(
                    "Test1",
                    mList));
            mRssAdapter=new RSS_Adapter(MainActivity.this,mProvidersModelList);
            mRecyclerView.setAdapter(mRssAdapter);
            mRssAdapter.notifyDataSetChanged();
        }
    }*/

    /*public class ProcessInBackground extends AsyncTask<Integer, Void, Exception>
    {

        List<MyArticleModel> myArticleModelList=new ArrayList<>();
        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //mProgressBar.set("Busy loading rss feed...please wait...");
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Exception doInBackground(Integer... params) {

            try
            {
                //URL url = new URL("http://feeds.news24.com/articles/fin24/tech/rss");

                //creates new instance of PullParserFactory that can be used to create XML pull parsers
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                //Specifies whether the parser produced by this factory will provide support
                //for XML namespaces
                factory.setNamespaceAware(false);

                //creates a new instance of a XML pull parser using the currently configured
                //factory features
                XmlPullParser xpp = factory.newPullParser();

                Log.e("hhhhhh Link",mNewsProviders.get(1));
                // We will get the XML from an input stream
                xpp.setInput(getInputStream(new URL(mNewsProviders.get(1))), "UTF_8");


                //boolean insideItem = false;

                // Returns the type of current event: START_TAG, END_TAG, START_DOCUMENT,
                int eventType = xpp.getEventType(); //loop control variable

                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    //if we are at a START_TAG (opening tag)
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        //if the tag is called "item"
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            Log.e("hhhhhhhh","0 "+xpp.getName());
                            while(xpp.next()!=XmlPullParser.START_TAG);
                            Log.e("hhhhhhhh","1 "+xpp.getName());
                            String title = "",date=null,link = null,img = null;
                            while (!(xpp.getName().equalsIgnoreCase("item"))){
                                Log.e("hhhhhhhh","2 "+xpp.getName());

                                if (eventType == XmlPullParser.START_TAG) {
                                    if (xpp.getName().equalsIgnoreCase("title")) {
                                        title = xpp.nextText();
                                    } else if (xpp.getName().equalsIgnoreCase("link")) {
                                        link = xpp.nextText();
                                    } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                                        date = xpp.nextText();
                                    } else if (xpp.getName().equalsIgnoreCase("img")) {
                                        if (xpp.getAttributeValue(null, "url") != null) {
                                            img = xpp.getAttributeValue(null, "url");
                                        } else if (xpp.getAttributeValue(null, "src") != null) {
                                            img = xpp.getAttributeValue(null, "src");
                                        }
                                        //img=xpp.nextText();
                                    }
                                }
                                //while(xpp.next()!=XmlPullParser.START_TAG);
                                eventType=xpp.next(); //move to next element
                            }
                            MyArticleModel myArticleModel=new MyArticleModel(title,link,img,date);
                            Log.e("hhhhhhhhhhh",myArticleModel.toString());
                            myArticleModelList.add(myArticleModel);
                        }
                    }
                    Log.e("hhhhhhhhh5","Next");
                    eventType = xpp.next(); //move to next element
                }


            }
            catch (MalformedURLException e)
            {
                exception = e;
            }
            catch (XmlPullParserException e)
            {
                exception = e;
            }
            catch (IOException e)
            {
                exception = e;
            }

            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);

            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, titles);

            //lvRss.setAdapter(adapter);
            mProvidersModelList.add(new ProviderModel(
                    "Provider1",
                    myArticleModelList));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mRssAdapter=new RSS_Adapter(MainActivity.this,mProvidersModelList);
                    mRecyclerView.setAdapter(mRssAdapter);
                    mRssAdapter.notifyDataSetChanged();
                }
            });


            mProgressBar.setVisibility(View.GONE);
        }
    }*/

    //***************************************************************************************
    //***************************************************************************************
    /*
    public class ReadRss2 extends AsyncTask<String,Void,Void> {
        private List<MyArticleModel> myArticleList;
        //private Context mContext;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected Void doInBackground(String... strings) {
            Log.e("hhhhhhhRSS",strings[0]);
            myArticleList = new ArrayList<>();
            Element root = getData(strings[0]).getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node cureentchild = items.item(i);
                if (cureentchild.getNodeName().equalsIgnoreCase("item")) {
                    MyArticleModel item = new MyArticleModel();
                    NodeList itemchilds = cureentchild.getChildNodes();
                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node current = itemchilds.item(j);
                        if (current.getNodeName().equalsIgnoreCase("title")) {
                            item.setTitle(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("description")) {
                            String desc=current.getTextContent();
                            //article.setDescription(desc.substring(desc.indexOf("/>")+2));
                            String imageUrl=desc.substring(desc.indexOf("src=")+5,desc.indexOf("jpg")+3);
                            item.setImg(imageUrl);
                            //item.setDescription(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("pubDate")) {
                            item.setPubDate(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("content:encoded")){
                            NodeList contentChilds = current.getChildNodes();
                            for (int k = 0; k < contentChilds.getLength(); k++){
                                Node cur = contentChilds.item(k);
                                Log.e("hhhhCur ",cur.getTextContent());
                                //Log.e("hhhhCur2 "+k,cur.getNodeValue());
                                //Log.e("hhhhCur3 "+k,cur.getTextContent());

                                //for (int w = 0; w < contentChilds.getLength(); w++) {
                                //    String url = current.getAttributes().item(w).getTextContent();
                                //}
                            }
                        } else if (current.getNodeName().equalsIgnoreCase("link")){
                            item.setLink(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("media:thumbnail")
                                           ||(current.getNodeName().equalsIgnoreCase("img"))){
                            //this will return us thumbnail url
                            String url = current.getAttributes().item(0).getTextContent();
                            Log.e("hhhhhhimg",url);
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
            mProvidersModelList.add(new ProviderModel(
                    "Provider",
                    myArticleList));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mRssAdapter=new RSS_Adapter(MainActivity.this,mProvidersModelList);
                    mRecyclerView.setAdapter(mRssAdapter);
                    mRssAdapter.notifyDataSetChanged();
                    mProgressBar.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });


            mProgressBar.setVisibility(View.GONE);
        }

        Document getData(String address) {
            try {
                URL url = new URL(address);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = builderFactory.newDocumentBuilder();
                return builder.parse(inputStream);
            } catch (Exception e){
                getData(address);
                e.printStackTrace();
                return null;
            }
        }

        public List<MyArticleModel> getMyArticleList() {
            return myArticleList;
        }
    }
    */
    //***************************************************************************************
    //***************************************************************************************
    /*
    public class MyJsonTask extends AsyncTask<String,Void,String>{

        JSONObject jsonObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressBar.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                XmlToJson xmlToJson = new XmlToJson.Builder(inputStream, null).build();
                inputStream.close();

                jsonObject = xmlToJson.toJson();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return strings[0];
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);

            try {
                mProvidersModelList.add(new ProviderModel(
                        getDomainName(aVoid),
                        getArticles(jsonObject)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //tasks.remove(MyJsonTask.this);
                    if(tasks.size()!=0) {
                        mRssAdapter = new RSS_Adapter(MainActivity.this, mProvidersModelList);
                        mRecyclerView.setAdapter(mRssAdapter);
                        mRssAdapter.notifyDataSetChanged();
                        mProgressBar.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            });
        }*/

        //***************************************************************************************
        //***************************************************************************************
        /*
        private List<MyArticleModel> getArticles(JSONObject jsonObject) {
            List<MyArticleModel> modelList=new ArrayList<>();
            try {
                //Log.e("hhhhhhhhJson",jsonObject.toString(4));
                JSONArray items = jsonObject.getJSONObject("rss").getJSONObject("channel")
                                          .getJSONArray("item");
                for (int i = 0; i < items.length(); i++) {
                    JSONObject c = items.getJSONObject(i);
                    MyArticleModel myArticleModel=new MyArticleModel();
                    if(c.has("title"))myArticleModel.setTitle(c.getString("title"));
                    if(c.has("link"))myArticleModel.setLink(c.getString("link"));
                    if(c.has("pubDate"))myArticleModel.setPubDate(c.getString("pubDate"));
                    if(c.has("img")){
                        JSONObject im=c.getJSONObject("img");
                        if(im.has("url"))myArticleModel.setImg(im.getString("url"));
                        else if(im.has("src"))myArticleModel.setImg(im.getString("src"));
                    }
                    else if(c.has("media:thumbnail")){
                        JSONObject im=c.getJSONObject("media:thumbnail");
                        if(im.has("url"))myArticleModel.setImg(im.getString("url"));
                        else if(im.has("src"))myArticleModel.setImg(im.getString("src"));
                    }

                    else if(c.has("content:encoded")){
                        String st=c.getString("content:encoded").toLowerCase();

                        String image=st.substring(st.indexOf("<img"));
                        //Log.e("hhhImg","Start******************"+image);
                        image=image.substring(image.indexOf("src=")+5);

                        //int x=findLastIndex(image);
                        image=image.substring(0, findLastIndex(image));
                        //Log.e("hhhImg",x+":END******************"+image);
                        //Log.e("hhhImg2",image);


                        if(!image.contains("logo") && check(image))
                            myArticleModel.setImg(image);
                        //myArticleModel.setPubDate(image);
                    }
                    //Log.e("hhhhhhhhhJson2",c.getString("title"));

                    modelList.add(myArticleModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return modelList;
        }
        */
        //***************************************************************************************
        //***************************************************************************************


    //***************************************************************************************
    //***************************************************************************************
    //***************************************************************************************
    //***************************************************************************************
    //***************************************************************************************
    //***************************************************************************************
    //***************************************************************************************
    //***************************************************************************************
