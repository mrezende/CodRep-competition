package br.ipt.main;


import br.ipt.models.InputLocation;
import br.ipt.models.SourceLinePosition;
import br.ipt.utils.Dataset;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.visitor.filter.TypeFilter;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {


    public static void main(String[] args) throws IOException {

        System.out.println(args.length);

        List<String> datasetsUri = new ArrayList<>();
        if(args.length < 2) {
            System.out.println("Usage: java -jar codrep-competition-all.jar -d <DATASETS_DIRECTORY>");
            System.exit(1);
        } else if ("-d".equals(args[0])) {
            for(int i = 1; i < args.length; i++) {
                datasetsUri.add(args[i]);
            }

        }


        for (String datasetUri: datasetsUri) {
            Path datasetPath = Paths.get(datasetUri);
            Dataset dataset = new Dataset(datasetPath);

            Path javaPath = dataset.getJavaPath();

            InputLocation assignmentLocation = new InputLocation(dataset, "assignments");

            List<Path> javaSourceFiles = Files.list(javaPath).collect(Collectors.toList());

            for (Path sourceFile: javaSourceFiles) {
                System.out.println("Source file: " + sourceFile);
                Launcher launcher = new Launcher();

                launcher.addInputResource(sourceFile.toString());

                launcher.buildModel();

                CtModel model = launcher.getModel();
                List<SourceLinePosition> assignments = getAssignmentLines(model);

                List<String> assignmentLines = getSourceLines(sourceFile, assignments);

                assignmentLocation.createInputFile(sourceFile, assignmentLines);

            }

        }

    }

    private static List<String> getSourceLines(Path sourceFile, List<SourceLinePosition> assignments) throws IOException {

        List<String> sourceLines = Files.readAllLines(sourceFile);

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
