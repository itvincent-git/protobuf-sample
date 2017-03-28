package com.example.tutorial.nano;// See README.txt for information and build instructions.

import android.content.Context;


import com.google.protobuf.nano.CodedInputByteBufferNano;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.tutorial.nano.AddressBookProtosNano.*;

public class ListPeopleNano {

    // Main function:  Reads the entire address book from a file and prints all
    //   the information inside.
    public static List<AddressBookProtosNano.Person> list(Context context) throws Exception {

        File bookFile = new File(context.getFilesDir(), "addressbook.data");

        FileInputStream input = new FileInputStream(bookFile);
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[256];
        int read = 0;
        while ((read = input.read(buff)) != -1) {
            arrayOutputStream.write(buff, 0, read);
        }
        // Read the existing address book.
        AddressBook addressBook =
                AddressBook.parseFrom(arrayOutputStream.toByteArray());

        return Arrays.asList(addressBook.people);
    }
}
