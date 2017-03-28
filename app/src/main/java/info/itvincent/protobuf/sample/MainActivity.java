package info.itvincent.protobuf.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tutorial.AddressBookProtos;
import com.example.tutorial.ListPeople;
import com.example.tutorial.nano.AddressBookProtosNano;
import com.example.tutorial.nano.ListPeopleNano;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.List;

import info.itvincent.protobuf.sample.R;

public class MainActivity extends AppCompatActivity {
    ListView mListView;
    private PersonListAdapter mAdapter;
    private NanoPersonListAdapter mNanoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPersonActivity.class);
                startActivity(intent);
            }
        });

        mListView = (ListView) findViewById(R.id.list);
        loadData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    void loadData() {
        try {

            if (Env.USE_NANO) {
                List<AddressBookProtosNano.Person> personList = ListPeopleNano.list(this);
                if (personList != null) {
                    mNanoAdapter = new NanoPersonListAdapter(MainActivity.this, personList);
                    mListView.setAdapter(mNanoAdapter);
                }
            } else {
                List<AddressBookProtos.Person> personList = ListPeople.list(this);
                if (personList != null) {
                    mAdapter = new PersonListAdapter(MainActivity.this, personList);
                    mListView.setAdapter(mAdapter);
                }
            }
        } catch (Throwable e) {
            Log.e("", "list data error", e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static class PersonListAdapter extends BaseAdapter {
        List<AddressBookProtos.Person> mPersonList;
        WeakReference<Activity> mActivity;

        PersonListAdapter(Activity activity, List<AddressBookProtos.Person> personList) {
            mPersonList = personList;
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public int getCount() {
            return mPersonList.size();
        }

        @Override
        public AddressBookProtos.Person getItem(int position) {
            return mPersonList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mPersonList.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PersonViewHolder holder = null;
            if (convertView != null) {
                holder = (PersonViewHolder) convertView.getTag();
            } else {
                convertView = LayoutInflater.from(mActivity.get()).inflate(R.layout.item_person_list, null);
                holder = new PersonViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.item_name);
                holder.email = (TextView) convertView.findViewById(R.id.item_email);
                holder.phone = (TextView) convertView.findViewById(R.id.item_phone);
                holder.type = (TextView) convertView.findViewById(R.id.item_type);
                convertView.setTag(holder);
            }
            holder.name.setText(getItem(position).getName());
            holder.email.setText(getItem(position).getEmail());
            holder.phone.setText(getItem(position).getPhonesList().get(0).getNumber());
            holder.type.setText(getItem(position).getPhonesList().get(0).getType().name());
            return convertView;
        }

    }

    static final class PersonViewHolder {
        TextView name;
        TextView email;
        TextView phone;
        TextView type;
    }


    static class NanoPersonListAdapter extends BaseAdapter {
        List<AddressBookProtosNano.Person> mPersonList;
        WeakReference<Activity> mActivity;

        NanoPersonListAdapter(Activity activity, List<AddressBookProtosNano.Person> personList) {
            mPersonList = personList;
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public int getCount() {
            return mPersonList.size();
        }

        @Override
        public AddressBookProtosNano.Person getItem(int position) {
            return mPersonList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mPersonList.get(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PersonViewHolder holder = null;
            if (convertView != null) {
                holder = (PersonViewHolder) convertView.getTag();
            } else {
                convertView = LayoutInflater.from(mActivity.get()).inflate(R.layout.item_person_list, null);
                holder = new PersonViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.item_name);
                holder.email = (TextView) convertView.findViewById(R.id.item_email);
                holder.phone = (TextView) convertView.findViewById(R.id.item_phone);
                holder.type = (TextView) convertView.findViewById(R.id.item_type);
                convertView.setTag(holder);
            }
            holder.name.setText(getItem(position).name);
            holder.email.setText(getItem(position).email);
            holder.phone.setText(getItem (position).phones[0].number);
            switch (getItem(position).phones[0].type) {
                case 0:
                    holder.type.setText("MOBILE");
                    break;
                case 1:
                    holder.type.setText("HOME");
                    break;
                case 2:
                    holder.type.setText("WORK");
                    break;
                default:
                    holder.type.setText("MOBILE");
                    break;
            }
            return convertView;
        }

    }
}
