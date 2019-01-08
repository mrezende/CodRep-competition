package br.ipt.models;

import spoon.reflect.CtModel;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.visitor.chain.CtQuery;
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

    public List<SourceLinePosition> getSourceLinePositions(CtModel model) {

        CtPackage rootPackage = model.getRootPackage();

        CtQuery ctQuery = rootPackage.filterChildren(new TypeFilter<>(this.getType()));

        List<T> list = ctQuery.list();


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
