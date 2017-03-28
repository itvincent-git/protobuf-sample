package com.example.tutorial.nano;// See README.txt for information and build instructions.

import android.content.Context;
import android.util.Log;

import com.google.protobuf.nano.MessageNano;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import static com.example.tutorial.nano.AddressBookProtosNano.AddressBook;
import static com.example.tutorial.nano.AddressBookProtosNano.Person;

public class AddPersonNano {
    private static final String TAG = "AddPersonNano";
    private static final long SEED = System.currentTimeMillis();

    // This function fills in a Person message based on user input.
    static Person setPersonForAddress(String name, String email, String number, String type) throws IOException {
        Log.i(TAG, "Start set person");
        Person person = new Person();

        int id = new Random(SEED).nextInt();
        person.id = id;

        person.name = name;

        if (email.length() > 0) {
            person.email = email;
        }


        Person.PhoneNumber phoneNumber =
                new Person.PhoneNumber();
        phoneNumber.number = number;
        if (type.equals("MOBILE")) {
            phoneNumber.type = Person.MOBILE;
        } else if (type.equals("HOME")) {
            phoneNumber.type = Person.HOME;
        } else if (type.equals("WORK")) {
            phoneNumber.type = Person.WORK;
        } else {
        }

        person.phones = new Person.PhoneNumber[]{phoneNumber};

        Log.i(TAG, "End set person:" + person);
        return person;
    }

    // Main function:  Reads the entire address book from a file,
    //   adds one person based on user input, then writes it back out to the same
    //   file.
    public static void add(Context context, String name, String email, String number, String type) throws Exception {

        File bookFile = new File(context.getFilesDir(), "addressbook.data");
        AddressBook addressBookStored = null;
        // Read the existing address book.
        try {
            Log.i(TAG, "File path:" + bookFile.getAbsolutePath());
            FileInputStream input = new FileInputStream(bookFile);
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            byte[] buff = new byte[256];
            int read = 0;
            while ((read = input.read(buff)) != -1){
                arrayOutputStream.write(buff, 0, read);
            }
            try {
                addressBookStored = AddressBook.parseFrom(arrayOutputStream.toByteArray());
                Log.i(TAG, "parseFrom success:" + addressBookStored);
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
        AddressBook addressBook = new AddressBook();
        addressBook.people = new Person[]{
                setPersonForAddress(name, email, number, type)};
        Log.i(TAG, "addPeople:" + addressBook);

        // Write the new address book back to disk.
        FileOutputStream output = new FileOutputStream(bookFile);
        byte[] serialized = MessageNano.toByteArray(addressBook);

        MessageNano.mergeFrom(addressBookStored, serialized);
        try {
            byte[] storeSerialized = MessageNano.toByteArray(addressBookStored);
            output.write(storeSerialized);
            Log.i(TAG, "File written size:" + storeSerialized.length);
        } finally {
            output.close();
        }
    }

    public static void create(Context context, String name, String email, String number, String type) throws Exception {
        File bookFile = new File(context.getFilesDir(), "addressbook.data");

        // Add an address.
        AddressBook addressBook = new AddressBook();
        addressBook.people = new Person[]{
                setPersonForAddress(name, email, number, type)};
        Log.i(TAG, "addPeople:" + addressBook);

        // Write the new address book back to disk.
        FileOutputStream output = new FileOutputStream(bookFile);
        byte[] serialized = MessageNano.toByteArray(addressBook);

        try {
            output.write(serialized);
            Log.i(TAG, "File written size:" + serialized.length);
        } finally {
            output.close();
        }
    }
}
