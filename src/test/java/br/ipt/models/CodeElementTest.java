package br.ipt.models;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import spoon.Launcher;
import spoon.compiler.SpoonResource;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.visitor.chain.CtQuery;
import spoon.reflect.visitor.chain.CtQueryable;
import spoon.reflect.visitor.filter.TypeFilter;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class CodeElementTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

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

    @Test
    public void extractAssignmentSourceLinePositions_shouldBeSizeOne() {


        Launcher launcher = new Launcher();

        File file = null;
        try {
            file = temporaryFolder.newFile("teste.java");
            List<String> lines = Arrays.asList(new String[] { "class A { int a = 10; A() { a = 20; /* assignment */ } }" });
            Files.write(file.toPath(), lines);


        } catch (IOException e) {
            e.printStackTrace();
        }


        launcher.addInputResource(file.getAbsolutePath());
        CtModel ctModel = launcher.buildModel();


        CodeElement<CtAssignment> ctAssignmentCodeElement = new CodeElement<>(CtAssignment.class);
        List<SourceLinePosition> sourceLinePositions = ctAssignmentCodeElement.getSourceLinePositions(ctModel);

        Assert.assertEquals(1, sourceLinePositions.size());

    }

}
