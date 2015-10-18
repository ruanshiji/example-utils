package com.t2t.examples.xml.jaxb.simple;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace="com.t2t")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClassA {
    private int classAId;
    
    @XmlElement(name="ClassAName")
    private String classAName;

    private ClassB classB;

    public int getClassAId() {
        return classAId;
    }
    public void setClassAId(int classAId) {
        this.classAId = classAId;
    }

    public String getClassAName() {
        return classAName;
    }

    public void setClassAName(String classAName) {
        this.classAName = classAName;
    }

    public ClassB getClassB() {
        return classB;
    }

    public void setClassB(ClassB classB) {
        this.classB = classB;
    }
}

