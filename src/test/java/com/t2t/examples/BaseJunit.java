package com.t2t.examples;

import com.t2t.examples.xml.jaxb.XmlUtilTest;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by yangpengfei on 2015/9/30.
 */
public class BaseJunit {
    public Logger log = LoggerFactory.getLogger(XmlUtilTest.class);

    long s = 0L;
    long e = 0L;

    @Before
    public void before() {
        s = new Date().getTime();
    }

    @After
    public void after() {
        e = new Date().getTime();
        log.info("耗时：" + (e - s) + "毫秒");
    }
}
