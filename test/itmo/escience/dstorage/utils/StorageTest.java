package itmo.escience.dstorage.utils;

import itmo.escience.dstorage.utils.enums.StorageLevel;
import itmo.escience.dstorage.utils.requests.Request;
import itmo.escience.dstorage.utils.responses.Response;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author anton
 */
public class StorageTest {
    private static Storage storage;
    private static String name;
    public StorageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        storage=new Storage("127.0.0.1","8084","564b3c550046b23291e28097");
        name = "/4/BOOTSECT.BAK";
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of uploadFile method, of class Storage.
     */
    @Test
    public void testUploadFile() {
        System.out.println("uploadFile");
        File file = null;
        String storageFilename = "";
        long size = 0L;
        int reserv = 0;
        String tag = "";
        int level = 0;
        Storage instance = null;
        Response expResult = null;
        Response result = instance.uploadFile(file, storageFilename, size, reserv, tag, level);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of downloadFile method, of class Storage.
     */
    @Test
    public void testDownloadFile() {
        System.out.println("downloadFile");
        String filename = "";
        String storageFilename = "";
        int level = 0;
        Storage instance = null;
        Response expResult = null;
        Response result = instance.downloadFile(filename, storageFilename, level);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMetaDataByName method, of class Storage.
     */
    @Test
    public void testGetMetaDataByName() {
        System.out.println("getMetaDataByName");
        String name = "";
        Storage instance = null;
        Response expResult = null;
        Response result = instance.getMetaDataByName(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    /**
     * Test of getMetaDataByName method, of class Storage.
     */
    @Test
    public void testMoveFileLevelByName() {
        System.out.println("moveFileLevelByName");
        String name = "/13/bar.emf";
        Storage instance = new Storage("127.0.0.1","8084","564b3c550046b23291e28097");
        Response expResult = null;
        //(String name,String host,StorageLevel lvlfrom,StorageLevel lvlto){
        String host="127.0.0.1";
        StorageLevel lvlfrom=StorageLevel.HDD;
        StorageLevel lvlto=StorageLevel.SSD;
        Response result = instance.moveFileLevelByName(name,host,lvlfrom,lvlto);
        System.out.println("moveFileLevelByName result:"+result.toString());
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    /**
     * Test of getMetaDataByName method, of class Storage.
     */
    @Test
    public void testMoveFileByName() {
        System.out.println("moveFileByName");
        //String name = "";
        Storage instance = null;
        Response expResult = null;
        //(String name,String fromhost,String tohost,StorageLevel lvlfrom,StorageLevel lvlto){
        String fromhost="127.0.0.1";
        String tohost="192.168.8.120";
        StorageLevel lvlfrom=StorageLevel.HDD;
        StorageLevel lvlto=StorageLevel.MEM;
        Response result = instance.moveFileByName(name,fromhost,tohost,lvlfrom,lvlto);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
