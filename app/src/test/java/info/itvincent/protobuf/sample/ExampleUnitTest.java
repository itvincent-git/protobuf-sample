package info.itvincent.protobuf.sample;

import android.util.Log;

import com.example.test.nano.TestProtos;
import com.example.tutorial.nano.AddPersonNano;
import com.example.tutorial.nano.AddressBookProtosNano;
import com.google.protobuf.nano.MessageNano;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.io.FileOutputStream;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testAddPeople() throws Exception {
        String name = "a";
        String email = "";
        String number = "";
        String type = "";
        AddressBookProtosNano.AddressBook addressBook = new AddressBookProtosNano.AddressBook();
        addressBook.people = new AddressBookProtosNano.Person[]{
                AddPersonNano.setPersonForAddress(name, email, number, type)};

        byte[] serialized = MessageNano.toByteArray(addressBook);
        String serializedStr = Hex.encodeHexString(serialized);
        System.out.println(serializedStr);
    }

    @Test
    public void testPhoneNumber() throws Exception {
        AddressBookProtosNano.Person.PhoneNumber phoneNumber =
                new AddressBookProtosNano.Person.PhoneNumber();
        phoneNumber.number = "";
        phoneNumber.type = AddressBookProtosNano.Person.HOME;
        byte[] serialized = MessageNano.toByteArray(phoneNumber);

        System.out.println(String.format(
                "serialized size:%d val:%s", serialized.length, Hex.encodeHexString(serialized)));

    }

    @Test
    public void test1() throws Exception {
        TestProtos.Test1 test1 = new TestProtos.Test1();
        test1.a = 300;
        byte[] serialized = MessageNano.toByteArray(test1);

        System.out.println(String.format(Locale.CHINESE,
                "serialized size:%d val:%s", serialized.length, Hex.encodeHexString(serialized)));
        assertEquals("08ac02", Hex.encodeHexString(serialized));
    }

    @Test
    public void test2() throws Exception {
        TestProtos.Test2 test2 = new TestProtos.Test2();
        test2.s = "";
        byte[] serialized = MessageNano.toByteArray(test2);

        System.out.println(String.format(Locale.CHINESE,
                "serialized size:%d val:%s", serialized.length, Hex.encodeHexString(serialized)));

        /**
         * serialized size:3 val:089601
         */
    }

    @Test
    public void testUser() throws Exception {
        TestProtos.User user = new TestProtos.User();
        user.id = 10;
        user.name = "Jo";
        byte[] serialized = MessageNano.toByteArray(user);

        System.out.println(String.format(Locale.CHINESE,
                "serialized size:%d val:%s", serialized.length, Hex.encodeHexString(serialized)));
        assertEquals("080a12024a6f", Hex.encodeHexString(serialized));
    }

    @Test
    public void testSigned() throws Exception {
        TestProtos.Signed user = new TestProtos.Signed();
        user.a = -10;
        user.b = -10;
        byte[] serialized = MessageNano.toByteArray(user);

        System.out.println(String.format(Locale.CHINESE,
                "serialized size:%d val:%s", serialized.length, Hex.encodeHexString(serialized)));
        //assertEquals("080a12024a6f", Hex.encodeHexString(serialized));
    }
}