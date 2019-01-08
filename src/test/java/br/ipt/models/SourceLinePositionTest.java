package br.ipt.models;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SourceLinePositionTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void sourceLinePosition_startLineEqualsEndLine_sizeShouldBeOne() {

        File file = null;
        try {
            file = temporaryFolder.newFile("teste.java");

            List<String> sourceCode = Arrays.asList("class A {", "int a = 10;", "}");

            Files.write(file.toPath(), sourceCode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SourceLinePosition> sourceLinePositions = new ArrayList<>();
        SourceLinePosition sourceLinePosition = new SourceLinePosition(1, 1);
        sourceLinePositions.add(sourceLinePosition);

        List<String> codeLines = SourceLinePosition.extractSourceLines(file.toPath(), sourceLinePositions);
        Assert.assertEquals(1, codeLines.size());

    }

    @Test
    public void sourceLinePosition_startLineEndLineEqualsToOne_shouldReturnClassName() {

        File file = null;
        try {
            file = temporaryFolder.newFile("teste.java");

            List<String> sourceCode = Arrays.asList("class A {", "int a = 10;", "}");

            Files.write(file.toPath(), sourceCode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SourceLinePosition> sourceLinePositions = new ArrayList<>();
        SourceLinePosition sourceLinePosition = new SourceLinePosition(1, 1);
        sourceLinePositions.add(sourceLinePosition);

        List<String> codeLines = SourceLinePosition.extractSourceLines(file.toPath(), sourceLinePositions);
        Assert.assertEquals("class A {", codeLines.get(0));

    }

    @Test
    public void sourceLinePosition_startLineNotEqualsToEndLine_sizeShouldBeOne() {

        File file = null;
        try {
            file = temporaryFolder.newFile("teste.java");

            List<String> sourceCode = Arrays.asList("class A {", "int a = 10;", "}");

            Files.write(file.toPath(), sourceCode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SourceLinePosition> sourceLinePositions = new ArrayList<>();
        SourceLinePosition sourceLinePosition = new SourceLinePosition(1, 3);
        sourceLinePositions.add(sourceLinePosition);

        List<String> codeLines = SourceLinePosition.extractSourceLines(file.toPath(), sourceLinePositions);
        Assert.assertEquals(1, codeLines.size());

    }

    @Test
    public void sourceLinePosition_startLineEqualsToOneEndLineEqualsToThree_shouldReturnClassNameFieldAndClosingBracket() {

        File file = null;
        try {
            file = temporaryFolder.newFile("teste.java");

            List<String> sourceCode = Arrays.asList("class A {", "int a = 10;", "}");

            Files.write(file.toPath(), sourceCode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SourceLinePosition> sourceLinePositions = new ArrayList<>();
        SourceLinePosition sourceLinePosition = new SourceLinePosition(1, 3);
        sourceLinePositions.add(sourceLinePosition);

        List<String> codeLines = SourceLinePosition.extractSourceLines(file.toPath(), sourceLinePositions);
        Assert.assertEquals("class A {int a = 10;}", codeLines.get(0));

    }
}
