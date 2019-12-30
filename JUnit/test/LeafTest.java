package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import system.*;

import static org.junit.Assert.*;

public class LeafTest {
    FileSystem fileSystem;

    @Before
    public void initialize() throws BadFileNameException, OutOfSpaceException {
        fileSystem = new FileSystem(10);
        String[] nameFi1e = {"root", "file1"};
        String[] nameDir = {"root", "dir1"};
        String[] nameDir2 = {"root", "dir1", "dir2"};
        String[] nameDir4 = {"root", "dir3", "dir4"};
        fileSystem.dir(nameDir);
        fileSystem.dir(nameDir2);
        fileSystem.dir(nameDir4);
        fileSystem.file(nameFi1e, 5);

    }

    @Test
    public void testGetPath() {
        assertEquals("[root]", Arrays.toString(fileSystem.DirExists(new String[]{"root"}).getPath()));
        assertEquals("[root, file1]", Arrays.toString(fileSystem.DirExists(new String[]{"root"}).children.get("file1").getPath()));
        assertEquals("[root, dir1]", Arrays.toString(fileSystem.DirExists(new String[]{"root"}).children.get("dir1").getPath()));
        assertEquals("[root, dir1, dir2]", Arrays.toString(((Tree) (fileSystem.DirExists(new String[]{"root"}).children.get("dir1"))).children.get("dir2").getPath()));
        assertEquals("[root, dir3, dir4]", Arrays.toString(((Tree) (fileSystem.DirExists(new String[]{"root"}).children.get("dir3"))).children.get("dir4").getPath()));
    }

    @Test
    public void testForConstructor() {
        Leaf l1 = null;
        try {
            l1 = new Leaf("test", 2);
        } catch (OutOfSpaceException e) {
            e.printStackTrace();
        }
        assertEquals(0, l1.size);


        Leaf l2 = null;
        try {
            l2 = new Leaf(null, 0);
        } catch (OutOfSpaceException e) {
            e.printStackTrace();
        }
        assertEquals(l2.size, 0);

    }

    @Test
    public void testForOutOfSpace() {
        Leaf l1 = null;
        try {
            l1 = new Leaf("test", 1000);
            Assert.fail();
        } catch (NullPointerException e) {

        } catch (OutOfSpaceException e) {
            assertTrue(true);
        } catch (Exception e) {
            Assert.fail();
        }

    }

    @Test
    public void testForMinusSize() {
        Leaf l1 = null;
        try {
            l1 = new Leaf("test", -1);
        } catch (Exception e) {
            assertEquals(NegativeArraySizeException.class, e.getClass());
        }

    }
}