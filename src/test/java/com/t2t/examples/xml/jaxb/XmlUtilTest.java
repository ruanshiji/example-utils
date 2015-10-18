package com.t2t.examples.xml.jaxb;

import com.t2t.examples.BaseJunit;
import com.t2t.examples.xml.jaxb.simple.ClassA;
import com.t2t.examples.xml.jaxb.simple.ClassB;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yangpengfei on 2015/9/30.
 */
public class XmlUtilTest extends BaseJunit {

    public Logger log = LoggerFactory.getLogger(XmlUtilTest.class);

    @Test
    public void toXML() {
        ClassB classB = new ClassB();
        classB.setClassBId(22);
        classB.setClassBName("B2");

        ClassA classA = new ClassA();
        classA.setClassAId(11);
        classA.setClassAName("A1");
        classA.setClassB(classB);

        log.info(XmlUtil.toXML(classA));
    }

    @Test
    public void fromXML() {
        ClassB classB = new ClassB();
        classB.setClassBId(22);
        classB.setClassBName("B2");

        ClassA classA = new ClassA();
        classA.setClassAId(11);
        classA.setClassAName("A1");
        classA.setClassB(classB);

        String xml = XmlUtil.toXML(classA);
        ClassA result = XmlUtil.fromXML(xml, ClassA.class);
        log.info("结果:" + result.getClassB().getClassBId() + "");
    }
}
