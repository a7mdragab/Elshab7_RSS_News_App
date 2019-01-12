package elshab7.engineering.elshab7_rss_news_app.MyRssParser;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import elshab7.engineering.elshab7_rss_news_app.Models.MyArticleModel;


public class RSSFeed implements Serializable {

	// Create a new item count
	private int itemCount = 0;
	// Create a new item list
	private List<MyArticleModel> itemList;
	// Serializable ID
	private static final long serialVersionUID = 1L;

	public RSSFeed() {
		// Initialize the item list
		itemList = new Vector<>(0);
	}

	void addItem(MyArticleModel item) {
		// Add an item to the Vector
		itemList.add(item);
		// Increment the item count
		itemCount++;
	}

	public List<MyArticleModel> getItemList() {
		return itemList;
	}

	public void setItemList(List<MyArticleModel> itemList) {
		this.itemList = itemList;
	}

	public MyArticleModel getItem(int position) {
		// Return the item at the chosen position
		return itemList.get(position);
	}

	public int getItemCount() {
		// Return the number of items in the feed
		return itemCount;
	}

}
