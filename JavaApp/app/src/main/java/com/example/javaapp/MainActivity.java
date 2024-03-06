package com.example.javaapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditTxt, descEditTxt;
    private MyHelper myHelper;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Java SQLite");

        // Initialize UI elements
        nameEditTxt = findViewById(R.id.addNameEditText);
        descEditTxt = findViewById(R.id.addDescEditText);
        Button addBtn = findViewById(R.id.addButton);
        Button updateBtn = findViewById(R.id.updateButton);
        Button deleteBtn = findViewById(R.id.deleteButton);

        // Set click listeners for buttons
        addBtn.setOnClickListener(v -> addData());
        updateBtn.setOnClickListener(v -> updateData());
        deleteBtn.setOnClickListener(v -> deleteData());

        myHelper = new MyHelper(getApplicationContext());
        sqLiteDatabase = myHelper.getWritableDatabase();

        // Refresh the displayed data
        readData();
    }

    private void addData() {
        String name = nameEditTxt.getText().toString().trim();
        String description = descEditTxt.getText().toString().trim();

        if (!name.isEmpty() && !description.isEmpty()) {

            myHelper.insertData(name, description, sqLiteDatabase);

            nameEditTxt.setText("");
            descEditTxt.setText("");

            Toast.makeText(getApplicationContext(), "Data added successfully", Toast.LENGTH_SHORT).show();

            // Refresh the displayed data
            readData();
        } else {
            Toast.makeText(getApplicationContext(), "Please enter both name and description", Toast.LENGTH_SHORT).show();
        }
    }

    // ========= update only first item present in database ========= //
    private void updateData() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT id FROM PRODUCTS LIMIT 1", null);

        if (cursor != null && cursor.moveToFirst()) {
            try {
                @SuppressLint("Range")
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String newName = nameEditTxt.getText().toString().trim();
                String newDesc = descEditTxt.getText().toString().trim();

                if (!newName.isEmpty() && !newDesc.isEmpty()) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("NAME", newName);
                    contentValues.put("DESCRIPTION", newDesc);

                    // sqLiteDatabase.update("PRODUCTS", contentValues, "id=?", new String[]{"1"});
                    // sqLiteDatabase.update("PRODUCTS", contentValues, "NAME=? AND DESCRIPTION=?", new String[]{"Jam", "Fruit Jam"});
                    sqLiteDatabase.update("PRODUCTS", contentValues, "id=?", new String[]{String.valueOf(id)});

                    nameEditTxt.setText("");
                    descEditTxt.setText("");

                    Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Please enter both name and description", Toast.LENGTH_SHORT).show();
                }

                // Refresh the displayed data
                readData();
            } finally {
                cursor.close();
            }
        } else {
            Toast.makeText(this, "No data available to update", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteData() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT id FROM PRODUCTS LIMIT 1", null);

        if (cursor != null && cursor.moveToFirst()) {
            try {
                @SuppressLint("Range")
                int id = cursor.getInt(cursor.getColumnIndex("id"));

                // Delete data based on the retrieved ID
                sqLiteDatabase.delete("PRODUCTS", "id=?", new String[]{String.valueOf(id)});

                nameEditTxt.setText("");
                descEditTxt.setText("");

                Toast.makeText(this, "Data deleted successfully", Toast.LENGTH_SHORT).show();

                // Refresh the displayed data
                readData();
            } finally {
                cursor.close();
            }
        } else {
            Toast.makeText(this, "No data available to delete", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void readData() {
        @SuppressLint("Recycle")
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT NAME, DESCRIPTION FROM PRODUCTS", null);

        if (cursor != null && cursor.moveToFirst()) {
            StringBuilder stringBuilder = new StringBuilder();

            do {
                //String name = cursor.getString(0);
                //String descp = cursor.getString(1);

                @SuppressLint("Range")
                String name = cursor.getString(cursor.getColumnIndex("NAME"));
                @SuppressLint("Range")
                String desc = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));

                stringBuilder.append("Name: ").append(name).append("\n").append("Desc: ").append(desc).append("\n\n");
            } while (cursor.moveToNext());

            TextView textView = findViewById(R.id.textData);
            textView.setText(stringBuilder.toString());

            cursor.close();
        } else {
            TextView textView = findViewById(R.id.textData);
            textView.setText("No data available");
        }
    }

}