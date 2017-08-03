package s3585826.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class Friends extends Fragment{

    private static final String LOG_TAG = "1";
    private View view;
    private int friendCount = 0;
    protected static final int PICK_CONTACTS = 100;
    private ArrayList <String> names = new ArrayList<>();
    private BaseAdapter la;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friends,container,false);
        ListView flv = view.findViewById(R.id.flw1);

        for(Friend e: MainActivity.user1.getFriendList()){
            names.add(e.getName());
        }

        la = new ArrayAdapter <String> (this.getContext(),
            android.R.layout.simple_list_item_1, names) {
        };
        flv.setAdapter(la);

        if(friendCount == 0) {
            Toast.makeText(getActivity(), "You have no friends",
                    Toast.LENGTH_LONG).show();
        }else{
            //TODO
        }

        FloatingActionButton flb = view.findViewById(R.id.ffab);
        flb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(contactPickerIntent, PICK_CONTACTS);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String id = "";
        String name = "";
        String email = "";

        if (requestCode == PICK_CONTACTS) {
            if (resultCode == RESULT_OK) {
                ContactDataManager contactsManager = new
                        ContactDataManager(this.getContext(), data);
                try {
                    name = contactsManager.getContactName();
                    email = contactsManager.getContactEmail();
                    names.add(name);
                    la.notifyDataSetChanged();
                } catch (ContactDataManager.ContactQueryException e) {
                    Log.e(LOG_TAG, e.getMessage());
                }
            }
        }
    }
}