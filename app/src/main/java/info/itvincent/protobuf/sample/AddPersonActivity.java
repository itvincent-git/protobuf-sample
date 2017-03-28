package info.itvincent.protobuf.sample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tutorial.AddPerson;
import com.example.tutorial.AddressBookProtos;
import com.example.tutorial.nano.AddPersonNano;

public class AddPersonActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner mSpinner;
    String mSelectType;
    String[] mSpinnerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText) findViewById(R.id.name)).getText().toString();
                String email = ((EditText) findViewById(R.id.email)).getText().toString();
                String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
                try {
                    if (Env.USE_NANO) {
                        AddPersonNano.add(getApplicationContext(), name, email, phone, mSelectType);
                    } else {
                        AddPerson.add(getApplicationContext(), name, email, phone, mSelectType);
                    }
                    Snackbar.make(view, "add person success", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } catch (Exception e) {
                    Log.e("", "add person error", e);
                    Snackbar.make(view, "add person error: " + e.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


        mSpinner = (Spinner) findViewById(R.id.type);

        mSpinnerData = new String[]{AddressBookProtos.Person.PhoneType.HOME.name(),
                AddressBookProtos.Person.PhoneType.MOBILE.name(),
                AddressBookProtos.Person.PhoneType.WORK.name()};
        mSpinner.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mSpinnerData));
        mSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mSelectType = mSpinnerData[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_create) {
            String name = ((EditText) findViewById(R.id.name)).getText().toString();
            String email = ((EditText) findViewById(R.id.email)).getText().toString();
            String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
            try {
                AddPersonNano.create(getApplicationContext(), name, email, phone, mSelectType);
                Snackbar.make(mSpinner, "create person success", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } catch (Exception e) {

            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
