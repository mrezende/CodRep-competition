package br.ipt.main;

import com.fasterxml.jackson.databind.ser.std.FileSerializer;
import spoon.Launcher;
import spoon.compiler.Environment;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtComment;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtModule;
import spoon.reflect.visitor.filter.TypeFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Parser {

    public static final String DATASETS_DIRECTORY = "./Datasets/";
    public static final String DATASET_ONE_SOLUTIONS = DATASETS_DIRECTORY + "Dataset1/Solutions";
    public static final String DATASET_ONE_TASKS = DATASETS_DIRECTORY + "Dataset1/Tasks";
    public static final String DATASET_ONE_JAVA = DATASETS_DIRECTORY + "Dataset1/Java";

    public static void main(String[] args) throws IOException {

        Launcher launcher = new Launcher();
        launcher.addInputResource(DATASET_ONE_JAVA + "/1.java");


        launcher.buildModel();

        CtModel model = launcher.getModel();
        List<CtAssignment> list = model.getRootPackage().filterChildren(new TypeFilter<CtAssignment>(CtAssignment.class)).list();



        for (CtAssignment assignment: list) {

            System.out.println("Line: " + assignment.getPosition().getLine() + ", End Line: " + assignment.getPosition().getEndLine());
            System.out.println(assignment);
        }



    }

}
