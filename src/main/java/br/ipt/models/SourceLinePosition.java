package br.ipt.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SourceLinePosition {

    private int startLine;
    private int endLine;
    public SourceLinePosition(int startLine, int endLine) {
        this.startLine = startLine;
        this.endLine = endLine;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public static List<String> extractSourceLines(Path sourceFile, List<SourceLinePosition> sourceLinePositions) {


        List<String> sourceLines = null;
        try {
            sourceLines = Files.readAllLines(sourceFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> codeLines = new ArrayList<>();
        for (SourceLinePosition sourceLinePosition : sourceLinePositions) {
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

            codeLines.add(codeLine);

        }
        return codeLines;
    }
}
