package org.kamsoft.school.school.adapter;

public class subjectsItems {

    private int Subject_id;
    private String SubjectName;
    private String Homework;
    private String Teacher;



    private String img;

    public subjectsItems() {

    }


    public int getSubject_id() {
        return Subject_id;
    }

    public void setSubject_id(int subject_id) {
        Subject_id = subject_id;
    }


    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        Teacher = teacher;
    }



    public String getHomework() {
        return Homework;
    }

    public void setHomework(String homework) {
        Homework = homework;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public subjectsItems(int Subject_id , String SubjectName ,String Homework, String Teacher , String Img)
    {
        this.Subject_id = Subject_id;
        this.SubjectName = SubjectName;
        this.Homework = Homework;
        this.Teacher = Teacher;
        this.img = Img;
    }


}
