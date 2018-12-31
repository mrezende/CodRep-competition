package br.ipt.models;

import br.ipt.utils.Dataset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class InputLocation {

    private Dataset dataset;
    private String codeElement;

    public InputLocation(Dataset dataset, String codeElement) {
        this.dataset = dataset;
        this.codeElement = codeElement;
    }


    public void createInputFile(Path sourceFile, List<String> assignmentLines) {
        String fileName = sourceFile.getFileName().toString();

        try {
            Path codeElementPath = dataset.getInputPath().resolve(codeElement);
            if(Files.notExists(codeElementPath)) {
                Files.createDirectories(codeElementPath);
            }
            Path sourceFilePath = codeElementPath.resolve(fileName);
            Files.write(sourceFilePath, assignmentLines);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
