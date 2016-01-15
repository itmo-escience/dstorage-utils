package itmo.escience.dstorage.utils;

import itmo.escience.dstorage.utils.enums.StorageLevel;
import itmo.escience.dstorage.utils.requests.Request;
import itmo.escience.dstorage.utils.responses.Response;
import java.io.File;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
    //@Test
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
     * Test of uploadFile method, of class Storage.
     */
    //@Test
    public void testSpeedLevel() {
        System.out.println("SpeedTest Levels");
        String file = "C:/temp/data2";
        long t=(new Date()).getTime();
        String storageFilename = "/test-"+Long.toString(t);
        long size = 0L;
        int reserv = 0;
        String tag = "";
        StorageLevel level0 = StorageLevel.HDD;
        StorageLevel level1 = StorageLevel.SSD;
        StorageLevel level2 = StorageLevel.MEM;
        Storage instance = null;
        Response expResult = null;
        long len=new File(file).length();
        Response result0 = storage.uploadFile(new File(file), storageFilename+"-lvl"+level0.name(), len, reserv, tag, level0.getNum());
        Response result1 = storage.uploadFile(new File(file), storageFilename+"-lvl"+level1.name(), len, reserv, tag, level1.getNum());
        Response result2 = storage.uploadFile(new File(file), storageFilename+"-lvl"+level2.name(), len, reserv, tag, level2.getNum());
        long time=new Date().getTime();
        long result3=storage.downloadFileAsStream(file+"-0", storageFilename+"-lvl"+level0.name(), level0.getNum() );
        System.out.println("HDD time:"+((new Date()).getTime()-time)+" "+result3);
        time=new Date().getTime();
        long result4=storage.downloadFileAsStream(file+"-1", storageFilename+"-lvl"+level1.name(), level1.getNum() );
        System.out.println("SSD time:"+((new Date()).getTime()-time)+" "+result4);
        time=new Date().getTime();
        long result5=storage.downloadFileAsStream(file+"-2", storageFilename+"-lvl"+level2.name(), level2.getNum() );
        System.out.println("RAM time:"+((new Date()).getTime()-time)+" "+result5);
        
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of downloadFile method, of class Storage.
     */
    //@Test
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
        String name = "/meta/bar.emf";
        Response expResult = null;
        Response result = storage.getMetaDataByName(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    /**
     * Test of getMetaDataByName method, of class Storage.
     */
    //@Test
    public void testMoveFileLevelByName() {
        System.out.println("moveFileLevelByName");
        String name = "/13/bar.emf";
        Storage instance = new Storage("127.0.0.1","8084","564b3c550046b23291e28097");
        boolean expResult = true;
        //(String name,String host,StorageLevel lvlfrom,StorageLevel lvlto){
        String host="127.0.0.1";
        StorageLevel lvlfrom=StorageLevel.HDD;
        StorageLevel lvlto=StorageLevel.SSD;
        Response result = instance.moveFileLevelByName(name,host,lvlfrom,lvlto);
        System.out.println("moveFileLevelByName result:"+result.toString());
        assertEquals(expResult, result.getStatus());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    /**
     * Test of getMetaDataByName method, of class Storage.
     */
    //@Test
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
