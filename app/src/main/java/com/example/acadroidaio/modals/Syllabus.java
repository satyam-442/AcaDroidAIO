package com.example.acadroidaio.modals;

public class Syllabus {
    String BookId, BookName, BooksPdf, Date, Section, Semester, Subject, Time;

    public Syllabus() {
    }

    public Syllabus(String bookId, String bookName, String booksPdf, String date, String section, String semester, String subject, String time) {
        BookId = bookId;
        BookName = bookName;
        BooksPdf = booksPdf;
        Date = date;
        Section = section;
        Semester = semester;
        Subject = subject;
        Time = time;
    }

    public String getBookId() {
        return BookId;
    }

    public void setBookId(String bookId) {
        BookId = bookId;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getBooksPdf() {
        return BooksPdf;
    }

    public void setBooksPdf(String booksPdf) {
        BooksPdf = booksPdf;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}