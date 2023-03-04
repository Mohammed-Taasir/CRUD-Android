package com.example.sqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;

    EditText name, surname, marks, id;
    Button addData, seeDB, updateDB, deleteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);

        name = findViewById(R.id.editTextTextPersonName);
        surname = findViewById(R.id.editTextTextPersonName2);
        marks = findViewById(R.id.editTextTextPersonName3);
        id = findViewById(R.id.editTextTextPersonName4);

        addData = findViewById(R.id.add_data);
        seeDB = findViewById(R.id.see_db);
        updateDB = findViewById(R.id.update);
        deleteDB = findViewById(R.id.delete);

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = myDB.insertData(name.getText().toString(), surname.getText().toString(), marks.getText().toString());

                if(isInserted)
                    Toast.makeText(getApplicationContext(), "Data inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Data not inserted", Toast.LENGTH_LONG).show();
            }
        });

        seeDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDB.getAllData();
                if(res.getCount() == 0){
                    // no data
                    showMessage("Error", "Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("id: "+res.getString(0)+"\n");
                    buffer.append("name: "+res.getString(1)+"\n");
                    buffer.append("surname: "+res.getString(2)+"\n");
                    buffer.append("marks: "+res.getString(3)+"\n\n");
                }

                showMessage("Data", buffer.toString());
            }
        });

        updateDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = myDB.updateData(id.getText().toString(), name.getText().toString(), surname.getText().toString(), marks.getText().toString());
                if(isUpdate) Toast.makeText(getApplicationContext(), "Data updated", Toast.LENGTH_LONG).show();
                else Toast.makeText(getApplicationContext(), "Data failed to update", Toast.LENGTH_LONG).show();
            }
        });

        deleteDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRows = myDB.deleteData(id.getText().toString());

                if(deletedRows > 0) Toast.makeText(getApplicationContext(), "Data deleted", Toast.LENGTH_LONG).show();
                else Toast.makeText(getApplicationContext(), "Data failed to delete", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}