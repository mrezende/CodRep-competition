package br.ipt.models;

public class Line {

    private int startLine;
    private int endLine;
    public Line(int startLine, int endLine) {
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
