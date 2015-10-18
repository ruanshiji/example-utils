package com.t2t.examples.zip;

import com.t2t.examples.BaseJunit;
import com.t2t.examples.xml.jaxb.XmlUtil;
import com.t2t.examples.xml.jaxb.simple.ClassA;
import com.t2t.examples.xml.jaxb.simple.ClassB;
import com.t2t.examples.zip.ZipManager;
import org.junit.Test;

import java.io.File;

/**
 * @author ypf
 */
public class ZipManagerTest extends BaseJunit {

    @Test
    public void test_zip() throws Exception {
        ZipManager zipManager = new ZipManager();
        File sourceFile = new File("D:/xml");
        File targetFile = new File("D:/xml.zip");
        zipManager.zip(sourceFile, targetFile);
        log.info(targetFile.getAbsolutePath());
    }

}