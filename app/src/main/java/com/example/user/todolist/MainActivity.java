package com.example.user.todolist;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.todolist.mDataBase.DBAdapter;
import com.example.user.todolist.mDataObject.Spacecraft;
import com.example.user.todolist.mListView.CustomAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    EditText nameEditText;
    Button saveBtn,retrieveBtn;
    ArrayList<Spacecraft> spacecrafts=new ArrayList<>();
    CustomAdapter adapter;
    SearchView sv;
    final Boolean forUpdate=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nameEditText= (EditText) findViewById(R.id.nameEditTxt);
        lv= (ListView) findViewById(R.id.lv);
        sv= (SearchView) findViewById(R.id.sv);
        adapter=new CustomAdapter(this,spacecrafts);

        this.getSpacecrafts(null);
        // lv.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog(false);
            }
        });

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSpacecrafts(newText);
                return false;
            }
        });
    }


    private void displayDialog(Boolean forUpdate)
    {
        Dialog d=new Dialog(this);
        d.setTitle("SQLITE DATA");
        d.setContentView(R.layout.dialog_layout);

        nameEditText= (EditText) d.findViewById(R.id.nameEditTxt);
        saveBtn= (Button) d.findViewById(R.id.saveBtn);
        retrieveBtn= (Button) d.findViewById(R.id.retrieveBtn);

        if(!forUpdate)
        {
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        save(nameEditText.getText().toString());


                }
            });
            retrieveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSpacecrafts(null);
                }
            });
        }else {

            //SET SELECTED TEXT
            nameEditText.setText(adapter.getSelectedItemName());

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update(nameEditText.getText().toString());
                }
            });
            retrieveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSpacecrafts(null);
                }
            });
        }

        d.show();

    }

    //SAVE
    private void save(String name)
    {
        DBAdapter db=new DBAdapter(this);
        db.openDB();
        boolean saved;
        if (!TextUtils.isEmpty(nameEditText.getText().toString())){
        saved=db.add(name);

        if(saved)
        {
            nameEditText.setText("");
            this.getSpacecrafts(null);
            Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Unable To Save",Toast.LENGTH_SHORT).show();

        }}
        else{
            Toast.makeText(getApplicationContext(), "Please, fill the name", Toast.LENGTH_LONG).show();
        }
    }

    //RETRIEVE OR GETSPACECRAFTS
    private void getSpacecrafts(String searchTerm)
    {
        spacecrafts.clear();
        DBAdapter db=new DBAdapter(this);
        db.openDB();
        Cursor c=db.retrieve(searchTerm);
        Spacecraft spacecraft=null;

        while (c.moveToNext())
        {
            int id=c.getInt(0);
            String name=c.getString(1);

            spacecraft=new Spacecraft();
            spacecraft.setId(id);
            spacecraft.setName(name);

            spacecrafts.add(spacecraft);
        }

        db.closeDB();
        lv.setAdapter(adapter);
    }

    //UPDATE OR EDIT
    private void update(String newName)
    {
        //GET ID OF SPACECRAFT
        int id=adapter.getSelectedItemID();

        spacecrafts.clear();
        //UPDATE IN DB
        DBAdapter db=new DBAdapter(this);
        db.openDB();
        boolean updated;
        if (!TextUtils.isEmpty(nameEditText.getText().toString())){
            updated=db.update(newName,id);
        db.closeDB();

        if(updated)
        {
            nameEditText.setText(newName);
            getSpacecrafts(null);
            Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Unable To Update",Toast.LENGTH_SHORT).show();
        }}
        else{
            Toast.makeText(getApplicationContext(), "Please, fill the name", Toast.LENGTH_LONG).show();
        }
    }

    private void delete()
    {
        //GET ID
        int id=adapter.getSelectedItemID();

        //DELETE FROM DB
        DBAdapter db=new DBAdapter(this);
        db.openDB();
        boolean deleted=db.delete(id);
        db.closeDB();

        if(deleted)
        {
            getSpacecrafts(null);
        }else {
            Toast.makeText(this,"Unable To Delete",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        CharSequence title=item.getTitle();
        if(title=="NEW")
        {
            displayDialog(!forUpdate);

        }else  if(title=="EDIT")
        {
            displayDialog(forUpdate);

        }else  if(title=="DELETE")
        {
            delete();
        }

        return super.onContextItemSelected(item);
    }
}
















