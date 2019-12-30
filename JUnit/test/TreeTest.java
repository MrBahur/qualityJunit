package test;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

import system.*;

public class TreeTest {
    FileSystem fileSystem;

    @Before
    public void initialize() throws BadFileNameException, OutOfSpaceException {
        fileSystem = new FileSystem(10);
        String[] rootName = {"root"};
        String[] nameFi1e = {"root", "file1"};
        String[] nameDir = {"root", "dir1"};
        String[] nameDir2 = {"root", "dir1", "dir2"};
        String[] nameDir3 = {"root", "dir3"};
        String[] nameDir4 = {"root", "dir3", "dir4"};
        fileSystem.dir(nameDir);
        fileSystem.dir(nameDir2);
        fileSystem.dir(nameDir4);
        fileSystem.file(nameFi1e, 5);

    }

    @Test
    public void testForGetChildByName() {
        //test for existing "children"
        assertEquals(fileSystem.DirExists(new String[]{"root"}), fileSystem.DirExists(new String[]{"root"}));
        assertEquals(fileSystem.DirExists(new String[]{"root"}).children.get("dir1"), fileSystem.DirExists(new String[]{"root"}).GetChildByName("dir1"));

        //test for not existing "children"
        Tree test1 = fileSystem.DirExists(new String[]{"root"}).GetChildByName("notExist1");
        assertEquals(test1, fileSystem.DirExists(new String[]{"root"}).GetChildByName("notExist1"));
        assertEquals("notExist1", fileSystem.DirExists(new String[]{"root"}).GetChildByName("notExist1").getPath()[fileSystem.DirExists(new String[]{"root"}).GetChildByName("notExist1").getPath().length - 1]);
        assertEquals(1, test1.getPath().length - 1);
        assertNotNull(test1.children);
        assertEquals(0, test1.children.size());
        Tree test2 = fileSystem.DirExists(new String[]{"root"}).GetChildByName("notExist2");
        assertEquals(test2, fileSystem.DirExists(new String[]{"root"}).GetChildByName("notExist2"));
        assertEquals("notExist2", fileSystem.DirExists(new String[]{"root"}).GetChildByName("notExist2").getPath()[fileSystem.DirExists(new String[]{"root"}).GetChildByName("notExist2").getPath().length - 1]);
        assertEquals(1, test2.getPath().length - 1);
        assertNotNull(test2.children);
        assertEquals(0, test2.children.size());
        //
    }

    @Test
    public void testForConstructor() {
        Tree t1 = new Tree("test");
//        assertEquals("test", t1.getPath()[t1.getPath().length - 1]);
        assertNotNull(t1.children);
        assertEquals(0, t1.children.size());
        assertEquals(0, t1.getPath().length);

        Tree t2 = new Tree(null);
//        assertNull(t2.getPath()[t2.getPath().length - 1]);
        assertNotNull(t2.children);
        assertEquals(0, t2.children.size());
        assertEquals(0, t2.getPath().length);

    }
}