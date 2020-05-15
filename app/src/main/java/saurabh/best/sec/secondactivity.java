package saurabh.best.sec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class secondactivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondactivity);

     setTitle("Menu");
    setContentView(R.layout.activity_secondactivity);
    ListView listView = (ListView) findViewById(R.id.listView);
     ArrayList<String> menu = new ArrayList<String>();
        menu.add("Create group");
        menu.add("Join group");
        menu.add("Find people");
        menu.add("Communities");
        menu.add("Friend circle");
        menu.add("Volunteer group");
        menu.add("My groups");
        menu.add("Group request");
        menu.add("Resume");
        menu.add("Chat");
        menu.add("Profile");
        menu.add("Logout");
    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, menu);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(i==10) {
                Intent intent = new Intent(getApplicationContext(), thirdactivity.class);
                intent.putExtra("note", i);
                startActivity(intent);
            }
            if(i==11){
                //logout user

            }
            if(i==2){
               //Find people
                Intent intent = new Intent(getApplicationContext(), findPeople.class);
                intent.putExtra("note", i);
                startActivity(intent);
            }

        }

    });
}
}
