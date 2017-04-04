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
        phoneNumber.number = "123";
        phoneNumber.type = AddressBookProtosNano.Person.HOME;
        byte[] serialized = MessageNano.toByteArray(phoneNumber);

        System.out.println(String.format(
                "serialized size:%d val:%s", serialized.length, Hex.encodeHexString(serialized)));
        assertEquals("0a033132331001", Hex.encodeHexString(serialized));
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
        test2.s = "abc";
        byte[] serialized = MessageNano.toByteArray(test2);

        System.out.println(String.format(Locale.CHINESE,
                "serialized size:%d val:%s", serialized.length, Hex.encodeHexString(serialized)));
        assertEquals("0a03616263", Hex.encodeHexString(serialized));
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
        System.out.println(String.format(Locale.CHINESE,
                "-9 hex val:%s", Integer.toHexString(-10)));
        assertEquals("08f6ffffffffffffffff011013", Hex.encodeHexString(serialized));
    }

    @Test
    public void testNest() throws Exception {
        TestProtos.NestTest nestTest = new TestProtos.NestTest();
        TestProtos.Test1 test1 = new TestProtos.Test1();
        test1.a = 300;
        nestTest.t = test1;
        byte[] serialized = MessageNano.toByteArray(nestTest);

        System.out.println(String.format(Locale.CHINESE,
                "serialized size:%d val:%s", serialized.length, Hex.encodeHexString(serialized)));
        assertEquals("0a0308ac02", Hex.encodeHexString(serialized));
    }

    @Test
    public void testRepeated() throws Exception {
        TestProtos.RepeatedTest repeatedTest = new TestProtos.RepeatedTest();
        repeatedTest.a = new int[]{1, 2, 3};
        byte[] serialized = MessageNano.toByteArray(repeatedTest);

        System.out.println(String.format(Locale.CHINESE,
                "serialized size:%d val:%s", serialized.length, Hex.encodeHexString(serialized)));
        assertEquals("080108020803", Hex.encodeHexString(serialized));
    }

    @Test
    public void testRepeatedPacked() throws Exception {
        TestProtos.RepeatedPackedTest repeatedPackedTest = new TestProtos.RepeatedPackedTest();
        repeatedPackedTest.a = new int[]{1, 2, 3};
        byte[] serialized = MessageNano.toByteArray(repeatedPackedTest);

        System.out.println(String.format(Locale.CHINESE,
                "serialized size:%d val:%s", serialized.length, Hex.encodeHexString(serialized)));
        assertEquals("0a03010203", Hex.encodeHexString(serialized));
    }

    @Test
    public void testBool() throws Exception {
        TestProtos.BoolTest test = new TestProtos.BoolTest();
        test.a = true;
        byte[] serialized = MessageNano.toByteArray(test);

        System.out.println(String.format(Locale.CHINESE,
                "serialized size:%d val:%s", serialized.length, Hex.encodeHexString(serialized)));
        assertEquals("0801", Hex.encodeHexString(serialized));

        TestProtos.BoolTest test2 = new TestProtos.BoolTest();
        test2.a = false;
        byte[] serialized2 = MessageNano.toByteArray(test2);

        System.out.println(String.format(Locale.CHINESE,
                "serialized2 size:%d val:%s", serialized2.length, Hex.encodeHexString(serialized2)));
        assertEquals("", Hex.encodeHexString(serialized2));
    }

    @Test
    public void testBytes() throws Exception {
        TestProtos.BytesTest test = new TestProtos.BytesTest();
        test.a = new byte[]{1, 2, 3};
        byte[] serialized = MessageNano.toByteArray(test);

        System.out.println(String.format(Locale.CHINESE,
                "serialized size:%d val:%s", serialized.length, Hex.encodeHexString(serialized)));
        assertEquals("0a03010203", Hex.encodeHexString(serialized));
    }
}