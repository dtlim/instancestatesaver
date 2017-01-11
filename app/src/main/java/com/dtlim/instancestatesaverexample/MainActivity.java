package com.dtlim.instancestatesaverexample;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dtlim.instancestatesaver.SaveInstanceState;

public class MainActivity extends AppCompatActivity {

    @SaveInstanceState
    private int test;

    private String name;
    private String testnull;

    private EditText editTextContent;
    private Button buttonCreateList;
    private Button buttonPrintList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextContent = (EditText) findViewById(R.id.edittext_content);
        buttonCreateList = (Button) findViewById(R.id.button_create_list);
        buttonPrintList = (Button) findViewById(R.id.button_print_list);

        buttonCreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testnull = "asdf";
            }
        });

        buttonPrintList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("INSTANCESTATE", "onClick: " + testnull);
            }
        });

        restoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("name", name);
        outState.putString("testnull", testnull);
        super.onSaveInstanceState(outState);
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            name = savedInstanceState.getString("name");
            testnull = savedInstanceState.getString("testnull");
        }
    }
}
