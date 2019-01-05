package br.ipt.models;

import org.junit.Assert;
import org.junit.Test;
import spoon.reflect.code.CtAssignment;


public class CodeElementTest {

    @Test
    public void getNameFromCtAssignmentClass_ShouldBeAssignment() {

        CodeElement<CtAssignment> ctAssignmentCodeElement = new CodeElement<>(CtAssignment.class);
        Assert.assertEquals("assignment", ctAssignmentCodeElement.getName());

    }

    @Test
    public void getClassNameFromCtAssignmentClass_ShouldBeCtAssignment() {

        CodeElement<CtAssignment> ctAssignmentCodeElement = new CodeElement<>(CtAssignment.class);
        Assert.assertEquals("CtAssignment", ctAssignmentCodeElement.getClassName());

    }

}
