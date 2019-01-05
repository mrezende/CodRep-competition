package br.ipt.models;

import spoon.reflect.CtModel;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.filter.TypeFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CodeElement<T extends CtElement> {

    private Class<? super T> classCodeElement;

    public CodeElement(Class<? super T> classCodeElement) {
        this.classCodeElement = classCodeElement;
    }

    public String getName() {
        return this.classCodeElement.getSimpleName().replace("Ct", "").toLowerCase();
    }

    public String getClassName() {
        return this.classCodeElement.getSimpleName();
    }

    public Class<T> getType()
    {
        return (Class<T>) classCodeElement;
    }

    public List<String> getSourceLines(Path sourceFile, CtModel model) throws IOException {

        List<SourceLinePosition> assignments = getSourceLinePositions(model);

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

    private List<SourceLinePosition> getSourceLinePositions(CtModel model) {

        List<T> list = model.getRootPackage().filterChildren(new TypeFilter<>(this.getType())).list();


        List<SourceLinePosition> linePositions = new ArrayList<>();
        for (T ctElement : list) {
            SourcePosition position = ctElement.getPosition();
            SourceLinePosition sourceLinePosition = getLine(position);
            linePositions.add(sourceLinePosition);
        }

        return linePositions;

    }

    private SourceLinePosition getLine(SourcePosition position) {
        int startLine = position.getLine();
        int endLine = position.getEndLine();

        return new SourceLinePosition(startLine, endLine);
    }
}
