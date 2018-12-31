package br.ipt.models;

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
}
