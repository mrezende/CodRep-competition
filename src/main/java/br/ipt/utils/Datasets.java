package br.ipt.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum Datasets {


    DATASET_ONE("./Datasets/Dataset1"),
    DATASET_TWO("./Datasets/Dataset2"),
    DATASET_THREE("./Datasets/Dataset3"),
    DATASET_FOUR("./Datasets/Dataset4"),
    DATASET_FIVE("./Datasets/Dataset5");

    Datasets(String uri) {
        this.uri = uri;
    }

    private String uri;


    private enum Directory {

        JAVA_DIRECTORY("Java"),
        MODELS_DIRECTORY("Models");

        Directory(String folderName) {
            this.folderName = folderName;
        }

        private String folderName;
    }

    public static Path getJavaPath(Datasets dataset ) {
        return Paths.get(dataset.uri, Directory.JAVA_DIRECTORY.folderName);
    }

    public static Path getModelsPath(Datasets dataset ) {
        return Paths.get(dataset.uri, Directory.MODELS_DIRECTORY.folderName);
    }



}
