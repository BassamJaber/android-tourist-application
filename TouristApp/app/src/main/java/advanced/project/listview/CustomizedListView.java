package advanced.project.listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

import edu.birzeit.lab.advanced.touristapp.R;


public class CustomizedListView extends Activity {
	// All static variables
	static final String URL = "http://api.androidhive.info/music/music.xml";
	// XML node keys
	static final String KEY_SONG = "song"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_TITLE = "title";
	static final String KEY_ARTIST = "artist";
	static final String KEY_DURATION = "duration";
	static final String KEY_THUMB_URL = "thumb_url";
	
	ListView list;
    LazyAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_layout);
		
//data is obtained using hash map , get the data from db and add it to list
		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

//		XMLParser parser = new XMLParser();
//		String xml = parser.getXmlFromUrl(URL); // getting XML from URL
//		Document doc = parser.getDomElement(xml); // getting DOM element
//
//		NodeList nl = doc.getElementsByTagName(KEY_SONG);
		// looping through all song nodes <song>
//		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
//			Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
        for (int i=0;i<10;i++){
            map.put(KEY_ID,i+"");
            map.put(KEY_TITLE, "Testing Title");
            map.put(KEY_ARTIST, "Bassam Jaber");
            map.put(KEY_DURATION, "this 999");
            map.put(KEY_THUMB_URL,"./drawable/rihanna.png" );

            // adding HashList to ArrayList
            songsList.add(map);
        }

//		}
		

		list=(ListView)findViewById(R.id.list);

        Button addItem = (Button)findViewById(R.id.addNewItem);
        addItem.setText("Add new Customer");
		// Getting adapter by passing xml data ArrayList
        adapter=new LazyAdapter(this, songsList);        
        list.setAdapter(adapter);
        

        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
							

			}
		});		
	}	
}