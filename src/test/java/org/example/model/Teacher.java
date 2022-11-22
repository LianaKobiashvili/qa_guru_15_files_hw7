package org.example.model;
//{
//        "name":"Dmitrii",
//        "age": "33",
//        "isGoodTeacher": true,
//        "lessons": ["JUnit 5", "Files"],
//        "passport" : {
//        "number": 123456,
//        "issueDate": "12.12.2022"
//
//        }

import java.util.List;
public class Teacher {
    public String name;
    public int age;
    public boolean isGoodTeacher;
    public List <String> lessons;
    public Passport passport;

    public static class Passport {
        public int number;
        public String issueDate;

    }


}
