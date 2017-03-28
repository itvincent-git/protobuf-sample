package com.example.tutorial;// See README.txt for information and build instructions.

import android.content.Context;
import android.util.Log;

import com.example.tutorial.AddressBookProtos.AddressBook;
import com.example.tutorial.AddressBookProtos.Person;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;

public class AddPerson {
    private static final String TAG = "AddPerson";

    // This function fills in a Person message based on user input.
    static Person setPersonForAddress(String name, String email, String number, String type) throws IOException {
        Log.i(TAG, "Start set person");
        Person.Builder person = Person.newBuilder();

        int id = new Random(System.currentTimeMillis()).nextInt();
        person.setId(id);

        person.setName(name);

        if (email.length() > 0) {
            person.setEmail(email);
        }


        Person.PhoneNumber.Builder phoneNumber =
                Person.PhoneNumber.newBuilder().setNumber(number);

        if (type.equals("MOBILE")) {
            phoneNumber.setType(Person.PhoneType.MOBILE);
        } else if (type.equals("HOME")) {
            phoneNumber.setType(Person.PhoneType.HOME);
        } else if (type.equals("WORK")) {
            phoneNumber.setType(Person.PhoneType.WORK);
        } else {
        }

        person.addPhones(phoneNumber);

        Log.i(TAG, "End set person");
        return person.build();
    }

    // Main function:  Reads the entire address book from a file,
    //   adds one person based on user input, then writes it back out to the same
    //   file.
    public static void add(Context context, String name, String email, String number, String type) throws Exception {

        AddressBook.Builder addressBook = AddressBook.newBuilder();

        File bookFile = new File(context.getFilesDir(), "addressbook.data");
        // Read the existing address book.
        try {
            Log.i(TAG, "File path:" + bookFile.getAbsolutePath());
            FileInputStream input = new FileInputStream(bookFile);
            try {
                addressBook.mergeFrom(input);
                //Log.i(TAG, "mergeFrom success:" + addressBook);
            } finally {
                try {
                    input.close();
                } catch (Throwable ignore) {
                }
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, bookFile.getAbsolutePath() + " File not found.  Creating a new file.");
        }

        // Add an address.
        addressBook.addPeople(
                setPersonForAddress(name, email, number, type));
        Log.i(TAG, "addPeople");

        // Write the new address book back to disk.
        FileOutputStream output = new FileOutputStream(bookFile);

        try {
            addressBook.build().writeTo(output);
            Log.i(TAG, "File written:" + bookFile.length());
        } finally {
            output.close();
        }
    }
}
