package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.DirectoryNotEmptyException;
import java.util.Arrays;

import static org.junit.Assert.*;

import system.*;

public class FileSystemTest {
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
    public void testForDir1() {
        try {
            fileSystem.dir(new String[]{"abc", "def", "ghi"});
            Assert.fail();
        } catch (Exception e) {
            assertEquals(BadFileNameException.class, e.getClass());
        }
    }

    @Test
    public void testForDir2() {
        assertEquals("[root, dir1]", Arrays.toString(fileSystem.DirExists(new String[]{"root"}).children.get("dir1").getPath()));
        Tree t1 = (Tree) (fileSystem.DirExists(new String[]{"root"}).children.get("dir1"));
        assertEquals("[root, dir1, dir2]", Arrays.toString(t1.children.get("dir2").getPath()));
        Tree t2 = (Tree) (fileSystem.DirExists(new String[]{"root"}).children.get("dir3"));
        assertEquals("[root, dir3, dir4]", Arrays.toString(t2.children.get("dir4").getPath()));
    }

    @Test
    public void testForDir3() {
        String[] nameDir = {"root", "dir1"};
        try {
            fileSystem.dir(nameDir);
            Assert.fail();
        } catch (Exception e) {
            //assertEquals(ClassCastException.class,e.getClass());
        }
    }

    @Test
    public void testForDir4() {
        String[] nameDir = {"root", "dir1587"};
        try {
            fileSystem.dir(nameDir);
        } catch (BadFileNameException e) {
            Assert.fail();
        }
    }

    @Test
    public void testForDisk() {
        assertEquals("[[root, file1], [root, file1], [root, file1], [root, file1], [root, file1], null, null, null, null, null]", Arrays.deepToString(fileSystem.disk()));
        FileSystem f2 = new FileSystem(10);
        String[] nameFi1e = {"root", "dir1", "file1"};
        String[] nameDir = {"root", "dir1", "dir2"};
        String[] nameDir2 = {"root", "dir1", "dir2", "dir7"};
        String[] nameDir4 = {"root", "dir3", "dir4"};
        String[] nameFi1e2 = {"root", "dir3", "dir4", "file4"};
        assertEquals("[null, null, null, null, null, null, null, null, null, null]", Arrays.deepToString(f2.disk()));
        try {
            f2.dir(nameDir);
            f2.dir(nameDir2);
            f2.dir(nameDir4);
            f2.file(nameFi1e, 3);
            f2.file(nameFi1e2, 6);
        } catch (BadFileNameException | OutOfSpaceException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.deepToString(f2.disk()));
        assertEquals("[[root, dir1, file1], [root, dir1, file1], [root, dir1, file1], [root, dir3, dir4, file4]," +
                " [root, dir3, dir4, file4], [root, dir3, dir4, file4], [root, dir3, dir4, file4], [root, dir3, dir4, file4]," +
                " [root, dir3, dir4, file4], null]", Arrays.deepToString(f2.disk()));

    }

    @Test
    public void testForFile1() {
        try {
            fileSystem.file(new String[]{"abc", "def", "ghi"}, 3);
        } catch (Exception e) {
            assertEquals(BadFileNameException.class, e.getClass());
        }
    }

    @Test
    public void testForFile2() {
        try {
            fileSystem.file(new String[]{"root", "file2"}, 6);
        } catch (Exception e) {
            assertEquals(OutOfSpaceException.class, e.getClass());
        }
    }

    @Test
    public void testForFile3() {

        Tree t1 = (Tree) (fileSystem.DirExists(new String[]{"root"}).children.get("dir1"));
        try {
            fileSystem.file(new String[]{"root", "dir1", "file2"}, 2);

        } catch (BadFileNameException | OutOfSpaceException e) {
            e.printStackTrace();
        }
        try {
            fileSystem.file(new String[]{"root", "dir1", "file2"}, 3);
        } catch (BadFileNameException | OutOfSpaceException e) {
            assertEquals(BadFileNameException.class, e.getClass());
        }
        try {
            fileSystem.file(new String[]{"root", "dir1", "file2"}, 4);
        } catch (BadFileNameException | OutOfSpaceException e) {
            assertEquals(OutOfSpaceException.class, e.getClass());
        }
        assertEquals("[root, dir1, file2]", Arrays.toString(t1.children.get("file2").getPath()));
        assertEquals("[root, file1]", Arrays.toString(fileSystem.DirExists(new String[]{"root"}).children.get("file1").getPath()));

    }

    @Test
    public void testForLsdir() {
        assertEquals("[dir1, dir3, file1]", (Arrays.toString(fileSystem.lsdir(new String[]{"root"}))));
        assertEquals("[dir2]", (Arrays.toString(fileSystem.lsdir(new String[]{"root", "dir1"}))));
        assertEquals("[]", (Arrays.toString(fileSystem.lsdir(new String[]{"root", "dir1", "dir2"}))));
        assertEquals("[dir4]", (Arrays.toString(fileSystem.lsdir(new String[]{"root", "dir3"}))));
        assertEquals("[]", (Arrays.toString(fileSystem.lsdir(new String[]{"root", "dir3", "dir4"}))));
    }

    @Test
    public void testForRmfile() {//almost not up

        String[] nameToSearch = {"root", "file1"};
        if (fileSystem.FileExists(nameToSearch) != null) {
            assertEquals(5, FileSystem.fileStorage.countFreeSpace());
            fileSystem.rmfile(nameToSearch);
            assertEquals(10, FileSystem.fileStorage.countFreeSpace());
            assertNull(fileSystem.FileExists(nameToSearch));
        } else {
            assertNull(fileSystem.FileExists(nameToSearch));
        }
    }

    @Test
    public void testForRmdir2() {
        String[] nameToSearch = {"root", "dir1", "dir6"};
        try {
            fileSystem.dir(nameToSearch);
            int x = ((Tree) (fileSystem.DirExists(new String[]{"root"}).children.get("dir1"))).children.size();
            fileSystem.rmdir(nameToSearch);
            int y = ((Tree) (fileSystem.DirExists(new String[]{"root"}).children.get("dir1"))).children.size();
            assertEquals(x - 1, y);
        } catch (BadFileNameException e) {
            e.printStackTrace();
        } catch (DirectoryNotEmptyException e) {
            Assert.fail();
        }
    }

    @Test
    public void testForRmdir() throws DirectoryNotEmptyException {
        String[] nameToSearch1 = {"root", "dir1"};
        String[] nameToSearch4 = {"root", "dir1", "dir5"};
        String[] nameToSearch3 = {"root", "dir1", "dir6"};

        try {
            fileSystem.dir(nameToSearch4);
            fileSystem.dir(nameToSearch3);
        } catch (BadFileNameException e) {
            e.printStackTrace();
        }
        boolean tester = false;
        Tree t3 = fileSystem.DirExists(new String[]{"root"});
        Tree t4 = (Tree) t3.children.get("dir1");
        int x = t4.children.size();
        try {
            if (fileSystem.DirExists(nameToSearch1) != null) {
                fileSystem.rmdir(nameToSearch1);
                assertEquals(0, x);
                assertNull(fileSystem.DirExists(nameToSearch1));
            }
        } catch (Exception e) {
            assertTrue(x > 0);
            Tree t1 = fileSystem.DirExists(new String[]{"root"});
            Tree t2 = (Tree) t1.children.get("dir1");
            assertTrue(t2.children.size() > 0);
            assertEquals(DirectoryNotEmptyException.class, e.getClass());
        }

        String[] nameToSearch2 = {"root", "dir2"};
        if (fileSystem.DirExists(nameToSearch2) != null) {
            fileSystem.rmdir(nameToSearch2);
            assertNull(fileSystem.DirExists(nameToSearch2));
        }

    }

    @Test
    public void testForFileExists1() {
        assertNull(fileSystem.FileExists(new String[]{"asd", "bef"}));
        assertEquals("[root, file1]", Arrays.toString(fileSystem.FileExists(new String[]{"root", "file1"}).getPath()));
    }

    @Test
    public void testForDirExists() {// not up
        assertNull(fileSystem.DirExists(new String[]{"asd", "bef"}));
        assertEquals("[root, dir1]", Arrays.toString(fileSystem.DirExists(new String[]{"root", "dir1"}).getPath()));
    }
}