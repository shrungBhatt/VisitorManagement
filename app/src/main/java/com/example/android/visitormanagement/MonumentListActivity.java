package com.example.android.visitormanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

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
import java.util.List;

public class MonumentListActivity extends AppCompatActivity {

    private List<MonumentData> mMonumentDatas;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monument_list);
        fetchMonumentData();

        mRecyclerView = (RecyclerView)findViewById(R.id.monument_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }



    private class MonumentHolder extends RecyclerView.ViewHolder{
        private ImageView mMonumentImageView;
        private TextView mMonumentNameTextView;
        private ProgressBar mMonumentRushProgressBar;
        private RatingBar mRatingBar;
        private MonumentData mMonumentData;


        public MonumentHolder(LayoutInflater inflater, ViewGroup container){
            super(inflater.inflate(R.layout.list_item_monument,container,false));

            mMonumentImageView = (ImageView)itemView.
                    findViewById(R.id.list_item_monument_image_view);

            mMonumentNameTextView = (TextView)itemView.
                    findViewById(R.id.list_item_monument_name_textView);

            mMonumentRushProgressBar = (ProgressBar)itemView.
                    findViewById(R.id.list_item_rush_status);

            mRatingBar = (RatingBar)itemView.findViewById(R.id.list_item_monument_ratingbar);
        }

        public void bindMonumentData(MonumentData monumentData){
            mMonumentData = monumentData;
            mMonumentNameTextView.setText(mMonumentData.getMonumentName());
            mRatingBar.setRating((float)mMonumentData.getRating());
            mMonumentRushProgressBar.setProgress((mMonumentData.getRfidCnt()+ mMonumentData.getQrCnt())/10);
        }

    }

    private class MonumentAdapter extends RecyclerView.Adapter<MonumentHolder>{
        List<MonumentData> mMonumentDatas;

        public MonumentAdapter(List<MonumentData> monumentDatas){
            mMonumentDatas = monumentDatas;
        }

        @Override
        public MonumentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(MonumentListActivity.this);
            return new MonumentHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(MonumentHolder holder, int position) {
            MonumentData monumentData = mMonumentDatas.get(position);
            holder.bindMonumentData(monumentData);

        }

        @Override
        public int getItemCount() {
            return mMonumentDatas.size();
        }
    }

    private void fetchMonumentData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://ersnexus.esy.es/rmc_to_android.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mMonumentDatas = getMonumentsData(response);
                        if(mMonumentDatas != null){
                            mRecyclerView.setAdapter(new MonumentAdapter(mMonumentDatas));
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MonumentListActivity.this);
        requestQueue.add(stringRequest);


    }

    private List<MonumentData> getMonumentsData(String jsonString){
        List<MonumentData> monumentDatas = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for(int i=0;i<=jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                MonumentData monumentData = new MonumentData();
                monumentData.setMonumentName(jsonObject.getString("monument_name"));
                monumentData.setRfidCnt(Integer.parseInt(jsonObject.getString("rfid_count")));
                monumentData.setPirCnt(Integer.parseInt(jsonObject.getString("pir_result")));
                monumentData.setQrCnt(Integer.parseInt(jsonObject.getString("qr_count")));
                monumentData.setDateAndTime(jsonObject.getString("date_time"));
                monumentData.setRating(Float.valueOf(jsonObject.getString("rating")));

                monumentDatas.add(monumentData);
            }
        }catch(JSONException je){
            Log.e("MonumentListActivity",je.toString());
        }
        return monumentDatas;





    }
}
