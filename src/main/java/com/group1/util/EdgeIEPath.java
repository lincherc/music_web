package com.group1.util;

import org.springframework.stereotype.Service;

@Service
public class EdgeIEPath {
    public String pathChange(String filename) {
        int unixSep = filename.lastIndexOf('/');
        int winSep = filename.lastIndexOf('\\');
        int pos = (winSep > unixSep ? winSep : unixSep);
        if (pos != -1) {
            filename = filename.substring(pos + 1);
        }
        return filename;
    }
}