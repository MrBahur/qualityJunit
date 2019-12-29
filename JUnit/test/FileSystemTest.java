package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import system.*;

import java.nio.file.DirectoryNotEmptyException;

import static org.junit.Assert.*;

public class FileSystemTest {
    FileSystem myTest;

    @Before
    public void init() throws BadFileNameException, OutOfSpaceException {
        myTest = new FileSystem(14);
        String[] rootname = {"root"};
        String[] namef1 = {"root", "file1"};
        String[] named1 = {"root", "dir1"};
        myTest.dir(named1);
        String[] named2 = {"root", "dir1", "dir2"};
        myTest.dir(named2);
        String[] named3 = {"root", "dir3"};
        String[] named4 = {"root", "dir3", "dir4"};
        myTest.dir(named3);
        myTest.dir(named4);
        myTest.file(namef1,5);
//        String[] namef2 = {"root","file2"};
//        myTest.file(namef2,6);
    }

    @Test
    public void dir() throws BadFileNameException {
        String[] newDir={"root","dir5"};
        if(myTest.DirExists(newDir)==null){
            myTest.dir(newDir);
            assertNotEquals(myTest.DirExists(newDir),null);
        }
        else{
            assertNull(myTest.DirExists(newDir));
        }
        String[] fileName={"root","file1"};
        try{
            myTest.dir(fileName);
            //assertEquals(fileName,"dir1");
        }
        catch(BadFileNameException e){
        }
        catch (Exception e){
            //System.out.println(e.toString());
            Assert.fail("Need to throw BadFileNameException");
        }
        String [] name1={"dir9"};
        try{
            myTest.dir(name1);
        }catch(BadFileNameException e){
        }
        catch (Exception e){
            //System.out.println(e.toString());
            Assert.fail("Need to throw BadFileNameException");
        }
    }

    @Test
    public void disk() throws OutOfSpaceException, BadFileNameException {
        String[] test={"root"};
        int count=0;
        String[][] disk=myTest.disk();
        for(int i=0;i<disk.length;i++){
            if(disk[i]!=null){
                count++;
            }
        }
        assertEquals(5,count);

        String[] newFile={"root","file5"};
        myTest.file(newFile,2);
        int count1=0;
        String[][] disk1=myTest.disk();
        for(int i=0;i<disk1.length;i++){
            if(disk1[i]!=null){
                count1++;
            }
        }
        assertEquals(7,count1);
        myTest.rmfile(newFile);
        int count2=0;
        String[][] disk2=myTest.disk();
        for(int i=0;i<disk2.length;i++){
            if(disk2[i]!=null){
                count2++;
            }
        }
        assertEquals(5,count2);
    }

    @Test
    public void file() throws OutOfSpaceException, BadFileNameException {
        String[] newFile={"root","file10"};
        if(myTest.FileExists(newFile)==null){
            myTest.file(newFile,1);
            assertNotEquals(myTest.FileExists(newFile),null);
        }
        else{
            assertNull(myTest.FileExists(newFile));
        }
        String[] fileName={"root","dir1"};
        try{
            myTest.file(fileName,1);
            //assertEquals(fileName,"dir1");
        }
        catch(BadFileNameException e){
        }
        catch (Exception e){
            //System.out.println(e.toString());
            Assert.fail("Need to throw BadFileNameException");
        }
        String [] name1={"file9"};
        try{
            myTest.file(name1,1);
        }catch(BadFileNameException e){
        }
        catch (Exception e){
            //System.out.println(e.toString());
            Assert.fail("Need to throw BadFileNameException");
        }
        String[] name3={"root","dir1","dir2","test2"};
        try {
            myTest.file(name3,100);
        }catch(OutOfSpaceException e){
        }
        catch (Exception e){
            Assert.fail("Need to throw OutOfSpaceException");
        }
    }

    @Test
    public void lsdir() {
        String[]  nameToSearch={"dir1","dir3","file1"};
        String[] name={"root"};
        for(int i=0; i<nameToSearch.length;i++) {
            assertEquals(myTest.lsdir(name)[i], nameToSearch[i]);
        }
    }

    @Test
    public void rmfile() {

        String[] nameToSearch = {"root", "file1"};
        if (myTest.FileExists(nameToSearch) != null) {
            myTest.rmfile(nameToSearch);
            assertNull(myTest.FileExists(nameToSearch));
        }
        else{
            assertNull(myTest.FileExists(nameToSearch));
        }
    }

    @Test
    public void rmdir() throws DirectoryNotEmptyException {
        try {
            String[] nameToSearch1 = {"root", "dir1"};
            if (myTest.DirExists(nameToSearch1) != null) {
                myTest.rmdir(nameToSearch1);
                assertNull(myTest.DirExists(nameToSearch1));
            }
        }catch (DirectoryNotEmptyException e){

        }catch (Exception e){
            Assert.fail("need to throw DirectoryNotEmptyException");
        }

        String[] nameToSearch2 = {"root", "dir2"};
        if (myTest.DirExists(nameToSearch2) != null) {
            myTest.rmdir(nameToSearch2);
            assertNull(myTest.DirExists(nameToSearch2));
        }

    }

    @Test
    public void fileExists() {
        String[]  nameToSearch1={"root","file1"};
        assertNotNull(myTest.FileExists(nameToSearch1));

        String[]  nameToSearch2={"root","file15"};
        assertNull(myTest.FileExists(nameToSearch2));
    }

    @Test
    public void dirExists() {
        String[]  nameToSearch1={"root","dir1"};
        assertNotNull(myTest.DirExists(nameToSearch1));

        String[]  nameToSearch2={"root","dir15"};
        assertNull(myTest.DirExists(nameToSearch2));
    }
}