package br.ipt.main;


import br.ipt.models.CodeElement;
import br.ipt.models.InputLocation;
import br.ipt.models.SourceLinePosition;
import br.ipt.utils.Dataset;
import org.eclipse.jdt.core.dom.PrimitiveType;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtElement;
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

        long initialTimeMillis = System.currentTimeMillis();


        for (String datasetUri: datasetsUri) {
            Path datasetPath = Paths.get(datasetUri);
            Dataset dataset = new Dataset(datasetPath);

            Path javaPath = dataset.getJavaPath();



            List<Path> javaSourceFiles = Files.list(javaPath).collect(Collectors.toList());

            for (Path sourceFile: javaSourceFiles) {
                System.out.println("Source file: " + sourceFile);
                Launcher launcher = new Launcher();

                launcher.addInputResource(sourceFile.toString());

                try {
                    launcher.buildModel();

                    CtModel model = launcher.getModel();

                    CodeElement<CtAssignment> codeElement = new CodeElement<>(CtAssignment.class);
                    List<SourceLinePosition> sourceLinePositions = codeElement.getSourceLinePositions(model);
                    List<String> codeLines = SourceLinePosition.extractSourceLines(sourceFile, sourceLinePositions);

                    InputLocation assignmentLocation = new InputLocation(dataset, codeElement.getName());

                    String inputFileName = sourceFile.getFileName().toString();
                    assignmentLocation.createInputFile(inputFileName, codeLines);
                } catch(Exception e) {
                    e.printStackTrace();

                }

            }



        }

        long elapsedTime = System.currentTimeMillis() - initialTimeMillis;

        System.out.println("Elapsed Time: " + elapsedTime + " ms");

    }



}
