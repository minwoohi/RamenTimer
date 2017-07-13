package com.ktds.ramentimer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ktds.ramentimer.model.Ramen;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

   private List<Ramen> products;
   private ListView Iv_productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        products = new ArrayList<Ramen>();

        Ramen r = new Ramen();

        r.setProdImage(R.drawable.bibimmyeon);
        r.setProdName("팔도 비빔면");
        r.setProdManufactor("팔도");
        r.setCookMin("3분");
        products.add(r);

        r = new Ramen();

        r.setProdImage(R.drawable.garnjjarmbbong);
        r.setProdName("간짬뽕");
        r.setProdManufactor("삼양");
        r.setCookMin("4분");
        products.add(r);


        r = new Ramen();


        r.setProdImage(R.drawable.sinramen);
        r.setProdName("신라면");
        r.setProdManufactor("농심");
        r.setCookMin("5분");
        products.add(r);

        Iv_productList = (ListView) findViewById(R.id.lv_productList);


        Iv_productList.setAdapter(new ArrayAdapter<Ramen>(MainActivity.this, 0, products) {

            Holder holder = null;

            @NonNull
            @Override

            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                //
                if (convertView == null) {

                    LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                    convertView = inflater.inflate(R.layout.item_main, parent, false);

                    holder = new Holder();

                    holder.prodImage = (ImageView) convertView.findViewById(R.id.iv_prodImage);
                    holder.prodName = (TextView) convertView.findViewById(R.id.tv_prodName);
                    holder.cookMin = (TextView) convertView.findViewById(R.id.tv_cookMin);
                    holder.prodManufactor = (TextView) convertView.findViewById(R.id.tv_prodManufactor);

                    convertView.setTag(holder);

                } else {
                    holder = (Holder) convertView.getTag();
                }



                final Ramen item = getItem(position);
                holder.prodImage.setImageResource(item.getProdImage());
                holder.prodName.setText(item.getProdName());
                holder.cookMin.setText(item.getCookMin());
                holder.prodManufactor.setText(item.getProdManufactor());

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     if (position==0){
                         Intent i = new Intent(MainActivity.this, BibimMyeonActivity.class);
                         startActivity(i);
                     }
                     else if (position==1){
                         Intent i = new Intent(MainActivity.this, GarnJjarmBbongActivity.class);
                         startActivity(i);
                     }
                     else if(position==2){
                         Intent i = new Intent(MainActivity.this, SinRamenActivity.class);
                         startActivity(i);
                     }
                    }
                });
                return convertView;
            }
            class Holder {

                public ImageView prodImage;
                public TextView prodName;
                public TextView cookMin;
                public TextView prodManufactor;
            }

        });
    }
}
