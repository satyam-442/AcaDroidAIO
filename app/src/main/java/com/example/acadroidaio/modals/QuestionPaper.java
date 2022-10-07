package com.example.acadroidaio.modals;

public class QuestionPaper {
    String Date, QPId, QPName, QuestionPaperPdf, Section, Semester, Subject, Time;

    public QuestionPaper() {
    }

    public QuestionPaper(String date, String QPId, String QPName, String questionPaperPdf, String section, String semester, String subject, String time) {
        Date = date;
        this.QPId = QPId;
        this.QPName = QPName;
        QuestionPaperPdf = questionPaperPdf;
        Section = section;
        Semester = semester;
        Subject = subject;
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getQPId() {
        return QPId;
    }

    public void setQPId(String QPId) {
        this.QPId = QPId;
    }

    public String getQPName() {
        return QPName;
    }

    public void setQPName(String QPName) {
        this.QPName = QPName;
    }

    public String getQuestionPaperPdf() {
        return QuestionPaperPdf;
    }

    public void setQuestionPaperPdf(String questionPaperPdf) {
        QuestionPaperPdf = questionPaperPdf;
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
