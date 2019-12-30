package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.Assert.*;

import system.*;

public class NodeTest {

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
}