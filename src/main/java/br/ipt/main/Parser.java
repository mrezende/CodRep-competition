package br.ipt.main;


import br.ipt.models.SourceLinePosition;
import br.ipt.utils.Datasets;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.visitor.filter.TypeFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {


    public static void main(String[] args) throws IOException {


        Path javaPath = Datasets.getJavaPath(Datasets.DATASET_ONE);

        List<String> javaSourceFiles = Files.list(javaPath).map((Path path) -> path.toString()).collect(Collectors.toList());

        for (String sourceFile: javaSourceFiles) {
            Launcher launcher = new Launcher();

            launcher.addInputResource(sourceFile);

            launcher.buildModel();

            CtModel model = launcher.getModel();
            List<SourceLinePosition> assignments = getAssignmentLines(model);

            List<String> assignmentLines = getSourceLines(sourceFile, assignments);

            createModelFile(sourceFile, assignmentLines);

        }




    }

    private static void createModelFile(String sourceFile, List<String> sourceLines) throws IOException {
        Path modelsPath = Datasets.getModelsPath(Datasets.DATASET_ONE);
        Path assignmentPath = modelsPath.resolve("Assignments/");

        if(Files.notExists(assignmentPath)) {
            Files.createDirectories(assignmentPath);
        }

        Path assignmentFile = assignmentPath.resolve(sourceFile);


        Files.write(assignmentFile, sourceLines);
    }

    private static List<String> getSourceLines(String sourceFile, List<SourceLinePosition> assignments) throws IOException {
        Path javaPath = Datasets.getJavaPath(Datasets.DATASET_ONE);
        Path javaSourcePath = javaPath.resolve(sourceFile);
        List<String> sourceLines = Files.readAllLines(javaSourcePath);

        List<String> assignmentLines = new ArrayList<>();
        for (SourceLinePosition sourceLinePosition : assignments) {
            String codeLine = "";
            if(sourceLinePosition.getStartLine() == sourceLinePosition.getEndLine()) {
                codeLine += sourceLines.get(sourceLinePosition.getStartLine() - 1);
                codeLine = codeLine.trim();


            } else {
                for(int i = sourceLinePosition.getStartLine(); i <= sourceLinePosition.getEndLine(); i++) {
                    codeLine += sourceLines.get(i - 1);
                    codeLine = codeLine.trim();
                }
            }

            assignmentLines.add(codeLine);

        }
        return assignmentLines;
    }

    private static List<SourceLinePosition> getAssignmentLines(CtModel model) {
        List<CtAssignment> list = model.getRootPackage().filterChildren(new TypeFilter<>(CtAssignment.class)).list();


        List<SourceLinePosition> assignments = new ArrayList<>();
        for (CtAssignment assignment: list) {
            SourcePosition position = assignment.getPosition();
            SourceLinePosition sourceLinePosition = getLine(position);
            assignments.add(sourceLinePosition);
        }
        return assignments;
    }

    private static SourceLinePosition getLine(SourcePosition position) {
        int startLine = position.getLine();
        int endLine = position.getEndLine();

        return new SourceLinePosition(startLine, endLine);
    }

}
