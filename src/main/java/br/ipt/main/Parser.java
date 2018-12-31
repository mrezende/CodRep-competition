package br.ipt.main;


import br.ipt.models.Line;
import org.apache.commons.lang3.StringUtils;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.visitor.filter.TypeFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Parser {

    public static final String DATASETS_DIRECTORY = "./Datasets/";
    public static final String DATASET_ONE_SOLUTIONS = DATASETS_DIRECTORY + "Dataset1/Solutions/";
    public static final String DATASET_ONE_TASKS = DATASETS_DIRECTORY + "Dataset1/Tasks/";
    public static final String DATASET_ONE_JAVA = DATASETS_DIRECTORY + "Dataset1/Java/";
    public static final String DATASET_ONE_MODEL = DATASETS_DIRECTORY + "Dataset1/Models/";

    public static void main(String[] args) throws IOException {

        Launcher launcher = new Launcher();
        String sourceFile = "1.java";
        launcher.addInputResource(DATASET_ONE_JAVA + sourceFile);

        launcher.buildModel();

        CtModel model = launcher.getModel();
        List<CtAssignment> list = model.getRootPackage().filterChildren(new TypeFilter<>(CtAssignment.class)).list();


        List<Line> assignments = new ArrayList<>();
        for (CtAssignment assignment: list) {
            SourcePosition position = assignment.getPosition();
            Line line = getLine(position);
            assignments.add(line);
        }

        List<String> sourceLines = Files.readAllLines(Paths.get(DATASET_ONE_JAVA + sourceFile));

        List<String> assignmentLines = new ArrayList<>();
        for (Line line: assignments) {
            String codeLine = "";
            if(line.getStartLine() == line.getEndLine()) {
                codeLine += sourceLines.get(line.getStartLine() - 1);
                codeLine = codeLine.trim();


            } else {
                for(int i = line.getStartLine(); i <= line.getEndLine(); i++) {
                    codeLine += sourceLines.get(i - 1);
                    codeLine = codeLine.trim();
                }
            }

            assignmentLines.add(codeLine);

        }
        String assignmentDirectory = DATASET_ONE_MODEL + "Assignments/";

        Path path = Paths.get(assignmentDirectory);
        if(Files.notExists(path)) {
            Files.createDirectories(path);
        }

        Path file = Files.createFile(Paths.get(assignmentDirectory + sourceFile));



        Files.write(file, assignmentLines);


    }

    private static Line getLine(SourcePosition position) {
        int startLine = position.getLine();
        int endLine = position.getEndLine();

        return new Line(startLine, endLine);
    }

}
