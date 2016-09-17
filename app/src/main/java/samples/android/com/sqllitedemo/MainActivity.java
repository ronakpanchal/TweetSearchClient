package samples.android.com.sqllitedemo;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<JSONObject> tweetList=new ArrayList<>();
    TweetAdapter adapter;
    public ListView list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue = Volley.newRequestQueue(this);
        Button closeButton = (Button) this.findViewById(R.id.close);
        adapter = new TweetAdapter(this);
        list = (ListView) findViewById(R.id.listview);
        list.setAdapter(adapter);

        String url = "http://mysolr.compute.amazonaws.com:8983/solr/IRF16P1/select?indent=on&q=tweet_lang:tr&wt=json";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray responseArray = new JSONArray(new JSONObject(obj.getString("response")).getString("docs"));
                            for (int i = 0; i < responseArray.length(); i++) {
                                tweetList.add(new JSONObject(responseArray.get(i).toString()));
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Log.d("error:", e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error:", error.getMessage());
            }
        });
        queue.add(request);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("The button was clicked");
                Toast.makeText(MainActivity.this, (String) "Close the application", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public ArrayList<JSONObject> getTweetList() {
        return tweetList;
    }
}
