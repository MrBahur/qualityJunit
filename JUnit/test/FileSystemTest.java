package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import system.*;

import java.nio.file.DirectoryNotEmptyException;

import static org.junit.Assert.*;

public class FileSystemTest {
    FileSystem fileSystem;

    @Before
    public void init() throws BadFileNameException, OutOfSpaceException {
        fileSystem = new FileSystem(14);
        String[] fileToAdd1 = {"root", "file1"};
        String[] dirToAdd1 = {"root", "dir1"};
        String[] dirToAdd2 = {"root", "dir1", "dir2"};
        String[] dirToAdd3 = {"root", "dir3"};
        String[] dirToAdd4 = {"root", "dir3", "dir4"};
        fileSystem.dir(dirToAdd1);
        fileSystem.dir(dirToAdd2);
        fileSystem.dir(dirToAdd3);
        fileSystem.dir(dirToAdd4);
        fileSystem.file(fileToAdd1, 5);
//        String[] namef2 = {"root","file2"};
//        myTest.file(namef2,6);
    }

    @Test
    public void dir() throws BadFileNameException {
        String[] newDir = {"root", "dir5"};
        if (fileSystem.DirExists(newDir) == null) {
            fileSystem.dir(newDir);
            assertNotEquals(fileSystem.DirExists(newDir), null);
        } else {
            assertNull(fileSystem.DirExists(newDir));
        }
        String[] fileName = {"root", "file1"};
        try {
            fileSystem.dir(fileName);
            //assertEquals(fileName,"dir1");
        } catch (BadFileNameException e) {
        } catch (Exception e) {
            //System.out.println(e.toString());
            Assert.fail("Need to throw BadFileNameException");
        }
        String[] name1 = {"dir9"};
        try {
            fileSystem.dir(name1);
        } catch (BadFileNameException e) {
        } catch (Exception e) {
            //System.out.println(e.toString());
            Assert.fail("Need to throw BadFileNameException");
        }
    }

    @Test
    public void disk() throws OutOfSpaceException, BadFileNameException {
        String[] test = {"root"};
        int count = 0;
        String[][] disk = fileSystem.disk();
        for (int i = 0; i < disk.length; i++) {
            if (disk[i] != null) {
                count++;
            }
        }
        assertEquals(5, count);

        String[] newFile = {"root", "file5"};
        fileSystem.file(newFile, 2);
        int count1 = 0;
        String[][] disk1 = fileSystem.disk();
        for (int i = 0; i < disk1.length; i++) {
            if (disk1[i] != null) {
                count1++;
            }
        }
        assertEquals(7, count1);
        fileSystem.rmfile(newFile);
        int count2 = 0;
        String[][] disk2 = fileSystem.disk();
        for (int i = 0; i < disk2.length; i++) {
            if (disk2[i] != null) {
                count2++;
            }
        }
        assertEquals(5, count2);
    }

    @Test
    public void file() throws OutOfSpaceException, BadFileNameException {
        String[] newFile = {"root", "file10"};
        if (fileSystem.FileExists(newFile) == null) {
            fileSystem.file(newFile, 1);
            assertNotEquals(fileSystem.FileExists(newFile), null);
        } else {
            assertNull(fileSystem.FileExists(newFile));
        }
        String[] fileName = {"root", "dir1"};
        try {
            fileSystem.file(fileName, 1);
            //assertEquals(fileName,"dir1");
        } catch (BadFileNameException e) {
        } catch (Exception e) {
            //System.out.println(e.toString());
            Assert.fail("Need to throw BadFileNameException");
        }
        String[] name1 = {"file9"};
        try {
            fileSystem.file(name1, 1);
        } catch (BadFileNameException e) {
        } catch (Exception e) {
            //System.out.println(e.toString());
            Assert.fail("Need to throw BadFileNameException");
        }
        String[] name3 = {"root", "dir1", "dir2", "test2"};
        try {
            fileSystem.file(name3, 100);
        } catch (OutOfSpaceException e) {
        } catch (Exception e) {
            Assert.fail("Need to throw OutOfSpaceException");
        }
    }

    @Test
    public void lsdir() {
        String[] nameToSearch = {"dir1", "dir3", "file1"};
        String[] name = {"root"};
        for (int i = 0; i < nameToSearch.length; i++) {
            assertEquals(fileSystem.lsdir(name)[i], nameToSearch[i]);
        }
    }

    @Test
    public void rmfile() {

        String[] nameToSearch = {"root", "file1"};
        if (fileSystem.FileExists(nameToSearch) != null) {
            fileSystem.rmfile(nameToSearch);
            assertNull(fileSystem.FileExists(nameToSearch));
        } else {
            assertNull(fileSystem.FileExists(nameToSearch));
        }
    }

    @Test
    public void rmdir() throws DirectoryNotEmptyException {
        try {
            String[] nameToSearch1 = {"root", "dir1"};
            if (fileSystem.DirExists(nameToSearch1) != null) {
                fileSystem.rmdir(nameToSearch1);
                assertNull(fileSystem.DirExists(nameToSearch1));
            }
        } catch (DirectoryNotEmptyException e) {

        } catch (Exception e) {
            Assert.fail("need to throw DirectoryNotEmptyException");
        }

        String[] nameToSearch2 = {"root", "dir2"};
        if (fileSystem.DirExists(nameToSearch2) != null) {
            fileSystem.rmdir(nameToSearch2);
            assertNull(fileSystem.DirExists(nameToSearch2));
        }

    }

    @Test
    public void fileExists() {
        String[] nameToSearch1 = {"root", "file1"};
        assertNotNull(fileSystem.FileExists(nameToSearch1));

        String[] nameToSearch2 = {"root", "file15"};
        assertNull(fileSystem.FileExists(nameToSearch2));
    }

    @Test
    public void dirExists() {
        String[] nameToSearch1 = {"root", "dir1"};
        assertNotNull(fileSystem.DirExists(nameToSearch1));

        String[] nameToSearch2 = {"root", "dir15"};
        assertNull(fileSystem.DirExists(nameToSearch2));
    }
}