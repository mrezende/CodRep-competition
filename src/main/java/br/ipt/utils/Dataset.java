package br.ipt.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Dataset {

    public Dataset(Path uri) {
        this.uri = uri;
    }

    private Path uri;


    private enum Directory {

        JAVA_DIRECTORY("Java"),
        INPUT_DIRECTORY("Input");

        Directory(String folderName) {
            this.folderName = folderName;
        }

        private String folderName;
    }

    public Path getJavaPath() {
        return this.uri.resolve(Directory.JAVA_DIRECTORY.folderName);
    }



    public Path getInputPath() {
        return this.uri.resolve(Directory.INPUT_DIRECTORY.folderName);
    }



}
