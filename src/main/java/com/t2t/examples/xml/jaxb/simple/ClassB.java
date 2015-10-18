package com.t2t.examples.xml.jaxb.simple;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ClassB {
    private int ClassBId;
    private String ClassBName;

    public int getClassBId() {
        return ClassBId;
    }

    public void setClassBId(int classBId) {
        this.ClassBId = classBId;
    }

    public String getClassBName() {
        return ClassBName;
    }

    public void setClassBName(String classBName) {
        this.ClassBName = classBName;
    }
}