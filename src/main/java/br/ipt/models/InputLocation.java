package br.ipt.models;

import br.ipt.utils.Dataset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class InputLocation {

    private Dataset dataset;
    private String codeElementName;

    public InputLocation(Dataset dataset, String codeElementName) {
        this.dataset = dataset;
        this.codeElementName = codeElementName;
    }


    public void createInputFile(String inputFileName, List<String> assignmentLines) {

        try {
            Path codeElementPath = dataset.getInputPath().resolve(codeElementName);
            if(Files.notExists(codeElementPath)) {
                Files.createDirectories(codeElementPath);
            }
            Path sourceFilePath = codeElementPath.resolve(inputFileName);
            Files.write(sourceFilePath, assignmentLines);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
