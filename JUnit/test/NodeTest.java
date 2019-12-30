package test;

import org.junit.Before;
import org.junit.Test;

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
        HashMap<String, Node> childrenOfRoot = fileSystem.DirExists(new String[]{"root"}).children;
        Iterator it = childrenOfRoot.entrySet().iterator();
        it.next();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue() instanceof Node) {
                ArrayList<String> path = new ArrayList<String>();
                Node thisNode = ((Node) pair.getValue());
                while (thisNode.parent != null && thisNode != null) {
                    String toPath = thisNode.getPath()[thisNode.getPath().length - 1];
                    path.add(toPath);
                    thisNode = thisNode.parent;
                }
                Collections.reverse(path);
                assertEquals(Arrays.toString(path.toArray()), Arrays.toString(fileSystem.FileExists(new String[]{"root", "file1"}).getPath()));

            }
        }
        it.remove(); // avoids a ConcurrentModificationException
    }
}