package com.elves.phat.wifiloginhistory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText et;
    Button bt;
    ListView lv;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    int numberOfItems = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.tempInput);
        bt = findViewById(R.id.addButton);
        lv = findViewById(R.id.history);

        //arrayList = new ArrayList<>();
        //adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
        //lv.setAdapter(adapter);

        HashMap<String, String> nameAddresses = new HashMap<>();
        nameAddresses.put("10/10/18", "1.000.22.3");
        nameAddresses.put("11/6/17", "111.55.6.33");
        nameAddresses.put("3/4/15", "333.3.6.688.1");
        nameAddresses.put("12/6/20", "23.43.4.2222");
        nameAddresses.put("2/1/01", "2223.2.2221.1");

        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"First line", "Second line"},
                new int[]{R.id.text1, R.id.text2});

        Iterator it = nameAddresses.entrySet().iterator();
        while (it.hasNext()) {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First line", pair.getKey().toString());
            resultsMap.put("Second line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        lv.setAdapter(simpleAdapter);

        //onBtnClick();
    }

    public void onBtnClick() {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfItems++;
                if (numberOfItems > 20) {
                    numberOfItems--;
                    arrayList.remove(arrayList.size() - 1);
                }

                String result = et.getText().toString();
                arrayList.add(result);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
