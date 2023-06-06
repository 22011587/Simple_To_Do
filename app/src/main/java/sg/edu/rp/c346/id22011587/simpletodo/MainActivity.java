package sg.edu.rp.c346.id22011587.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etElement;
    Button btnAdd, btnClearAll;
    ListView lv;
    Spinner spn;
    Button btnDelete;

    ArrayList<String> altoDoList;
    ArrayAdapter<String> aatoDoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etElement = findViewById(R.id.toDoList);
        btnAdd = findViewById(R.id.buttonAddItem);
        btnClearAll = findViewById(R.id.buttonClearAllItem);
        lv = findViewById(R.id.listView);
        spn = findViewById(R.id.spinner);
        btnDelete = findViewById(R.id.buttonDeleteItem);

        altoDoList= new ArrayList<>();

        aatoDoList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, altoDoList);
        lv.setAdapter(aatoDoList);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_items, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(spinnerAdapter);

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = spn.getSelectedItem().toString();

                if (selectedItem.equals("Add a new task")) {
                    etElement.setHint("Type in a new task here");
                    btnDelete.setEnabled(false);
                    btnAdd.setEnabled(true);
                } else if (selectedItem.equals("Remove a task")) {
                    etElement.setHint("Type in the index of the task to be removed");
                    btnDelete.setEnabled(true);
                    btnAdd.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle any necessary functionality when nothing is selected
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spn.getSelectedItem().equals("Add a new task")) {
                    String newTask = etElement.getText().toString().trim();
                    if (!newTask.isEmpty()) {
                        altoDoList.add(newTask);
                        aatoDoList.notifyDataSetChanged();
                        etElement.setText("");
                    }
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spn.getSelectedItem().equals("Remove a task")) {
                    String indexString = etElement.getText().toString().trim();
                    if (!indexString.isEmpty()) {
                        int index = Integer.parseInt(indexString);
                        if (index >= 0 && index < altoDoList.size()) {
                            altoDoList.remove(index);
                            aatoDoList.notifyDataSetChanged();
                            etElement.setText("");
                        } else {
                            Toast.makeText(MainActivity.this, "Wrong index number", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Please enter an index", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                altoDoList.clear();
                aatoDoList.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "All tasks cleared", Toast.LENGTH_SHORT).show();
            }
        });
    }
}