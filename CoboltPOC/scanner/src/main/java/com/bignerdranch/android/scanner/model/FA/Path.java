package com.bignerdranch.android.scanner.model.FA;

public class Path {
    private String pathString;

    public Path(String string) {
        pathString = string;
    }

    public String getPathString() {
        return pathString;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Path
                && this.pathString.equals(((Path) o ).pathString);
    }

}
