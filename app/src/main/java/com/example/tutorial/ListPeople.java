package com.example.tutorial;// See README.txt for information and build instructions.

import android.content.Context;
import android.util.Log;

import com.example.tutorial.AddressBookProtos.AddressBook;
import com.example.tutorial.AddressBookProtos.Person;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class ListPeople {

    // Main function:  Reads the entire address book from a file and prints all
    //   the information inside.
    public static List<Person> list(Context context) throws Exception {

        File bookFile = new File(context.getFilesDir(), "addressbook.data");
        // Read the existing address book.
        AddressBook addressBook =
                AddressBook.parseFrom(new FileInputStream(bookFile));

        //Log.i("", "list AddressBook:" + addressBook);
        return addressBook.getPeopleList();
    }
}
