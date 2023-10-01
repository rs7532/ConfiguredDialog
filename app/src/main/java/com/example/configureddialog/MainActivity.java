package com.example.configureddialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    AlertDialog.Builder adb;
    AlertDialog ad;
    LinearLayout my_dialog;
    EditText first_organ;
    EditText difference_Multiplier;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch series_type;


    ListView lv;
    TextView x, d, n, Sn;

    String[] arr_series;
    Button btn;
    ArrayAdapter<String> adp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.List_View);
        System.out.println(1);
        x = findViewById(R.id.X);
        System.out.println(2);
        d = findViewById(R.id.d);
        System.out.println(3);
        n = findViewById(R.id.n);
        System.out.println(4);
        Sn = findViewById(R.id.Sn);
        System.out.println(5);
        btn = findViewById(R.id.button);
        System.out.println(6);

        arr_series = new String[20];


        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        System.out.println(7);
        lv.setOnItemClickListener(this);

        Reset_series();

        adp = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arr_series);
        System.out.println(8);
        lv.setAdapter(adp);
        System.out.println(9);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println(11);
                ad = createDialog();
                System.out.println(12);
                ad.show();
                System.out.println(13);
            }
        });
    }

    public void create_series(String type, int first_organ, int differenceMultiplier){
        for (int i = 0; i < 20; i++){
            if (type.equals("engineer")){
                if (i == 0){
                    arr_series[i] = String.valueOf(first_organ);
                }
                else{
                    arr_series[i] = String.valueOf(Integer.parseInt(arr_series[i - 1]) * differenceMultiplier);
                }
            }
            else{
                if (i == 0){
                    arr_series[i] = String.valueOf(first_organ);
                }
                else{
                    arr_series[i] = String.valueOf(Integer.parseInt(arr_series[i - 1]) + differenceMultiplier);
                }
            }
        }
    }

    public void Reset_series(){
        for (int i = 0; i < 20; i++){
            arr_series[i] = "0";
        }
    }


    @SuppressLint("InflateParams")
    AlertDialog createDialog(){
        my_dialog = (LinearLayout) getLayoutInflater().inflate(R.layout.my_dialog, null);
        first_organ = my_dialog.findViewById(R.id.first_organ);
        difference_Multiplier = my_dialog.findViewById(R.id.differnce_Multiplier);
        series_type = my_dialog.findViewById(R.id.series_type1);

        adb = new AlertDialog.Builder(this);

        adb.setView(my_dialog);
        my_dialog.setBackgroundColor(Color.WHITE);
        adb.setTitle("Series data input");
        adb.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (series_type.isChecked()){
                    create_series("invoice", Integer.parseInt(String.valueOf(first_organ.getText())), Integer.parseInt(String.valueOf(difference_Multiplier.getText())));
                }
                else {
                    create_series("engineer", Integer.parseInt(String.valueOf(first_organ.getText())), Integer.parseInt(String.valueOf(difference_Multiplier.getText())));
                }
                x.setText("x = "+ first_organ.getText());
                d.setText("d = "+ difference_Multiplier.getText());
            }
        });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        adb.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                x.setText("x = 0");
                d.setText("d = 0");
                n.setText("n = 0");
                Sn.setText("Sn = 0");
                Reset_series();
                lv.setAdapter(adp);
            }
        });
        return adb.create();
    }

    public int sum(int position){
        int sum = 0;
        for (int i = 0; i < position; i++){
            sum += Integer.parseInt(arr_series[i]);
        }
        return sum;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        n.setText("n = "+ (position + 1));
        Sn.setText("Sn = "+sum(position));
    }

    public void credits_pressed(View view) {
        Intent si = new Intent(this, Credits.class);
        startActivity(si);
    }
}