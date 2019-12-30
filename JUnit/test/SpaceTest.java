package test;

import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import system.*;

public class SpaceTest {
    private Space space;

    @Before
    public void initialize() {
        space = new Space(10);
        FileSystem fileSystem = new FileSystem(10);
    }

    @Test
    public void testForConstructor() {
        try {
            Space test1 = new Space(-1);
        } catch (Exception e) {
            assertEquals(new NegativeArraySizeException().toString(), e.toString());
        }
        Space test2 = new Space(10);
        assertEquals(10, test2.countFreeSpace());
        assertEquals(10, test2.getAlloc().length);
        for (int i = 0; i < 10; i++) {
            assertNull(test2.getAlloc()[i]);
        }
    }

    @Test
    public void testForAlloc() {
        try {
            Leaf l1 = new Leaf("test1", 3);
            space.Alloc(3, l1);
            assertEquals(7, space.countFreeSpace());
            assertEquals(l1, space.getAlloc()[0]);
            assertEquals(l1, space.getAlloc()[1]);
            assertEquals(l1, space.getAlloc()[2]);
            assertNull(space.getAlloc()[3]);
            Leaf l2 = new Leaf("test2", 7);
            space.Alloc(7, l2);
            assertEquals(0, space.countFreeSpace());
            assertEquals(l2, space.getAlloc()[3]);
            assertEquals(l2, space.getAlloc()[4]);
            assertEquals(l2, space.getAlloc()[5]);
            assertEquals(l2, space.getAlloc()[6]);
            assertEquals(l2, space.getAlloc()[7]);
            assertEquals(l2, space.getAlloc()[8]);
            assertEquals(l2, space.getAlloc()[9]);
            Leaf l3 = new Leaf("test3", 1);
            space.Alloc(1, l3);
        } catch (Exception e) {
            //assertEquals(OutOfSpaceException.class, e.getClass());
        }


    }

    @Test
    public void testForDealloc() {
        try {
            Leaf l1 = new Leaf("test1", 3);
            space.Alloc(3, l1);
            Leaf l2 = new Leaf("test2", 7);
            space.Alloc(7, l2);
            try {
                space.Dealloc(l1);
            } catch (Exception e) {
                assertEquals(NullPointerException.class, e.getClass());
            }
            assertEquals(3, space.countFreeSpace());
            assertNull(space.getAlloc()[0]);
            assertNull(space.getAlloc()[1]);
            assertNull(space.getAlloc()[2]);
            try {
                space.Dealloc(l2);
            } catch (Exception e) {
                assertEquals(NullPointerException.class, e.getClass());
            }
            assertNull(space.getAlloc()[3]);
            assertNull(space.getAlloc()[4]);
            assertNull(space.getAlloc()[5]);
            assertNull(space.getAlloc()[6]);
            assertNull(space.getAlloc()[7]);
            assertNull(space.getAlloc()[8]);
            assertNull(space.getAlloc()[9]);
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }
    }

    @Test
    public void testForCountFreeSpace() {
        assertEquals(10, space.countFreeSpace());
        try {
            Leaf l1 = new Leaf("test", 3);
            space.Alloc(3, l1);
            assertEquals(7, space.countFreeSpace());
        } catch (OutOfSpaceException e) {
            //e.printStackTrace();
        }
    }

    @Test
    public void testForGetAlloc() {
        Leaf[] leaves = new Leaf[10];
        try {
            for (int i = 0; i < 10; i++) {
                leaves[i] = new Leaf("test" + i, 1);
                space.Alloc(1, leaves[i]);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            assertEquals(leaves[i], space.getAlloc()[i]);
        }

    }
}