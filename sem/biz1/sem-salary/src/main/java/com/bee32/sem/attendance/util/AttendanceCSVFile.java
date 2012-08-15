package com.bee32.sem.attendance.util;

import org.apache.myfaces.custom.fileupload.UploadedFile;

public class AttendanceCSVFile {

    String _name;
    String _encoding;
    UploadedFile _csvFile;

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_encoding() {
        return _encoding;
    }

    public void set_encoding(String _encoding) {
        this._encoding = _encoding;
    }

    public UploadedFile get_csvFile() {
        return _csvFile;
    }

    public void set_csvFile(UploadedFile _csvFile) {
        this._csvFile = _csvFile;
    }

}
