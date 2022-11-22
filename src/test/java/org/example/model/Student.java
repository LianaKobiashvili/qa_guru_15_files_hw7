package org.example.model;

import java.util.List;
public class Student {
    public String firstname;
    public String lastname;
    public int age;
    public List<String> pets;
    public Friends friends;

    public static class Friends {
        public int number;
        public String girlsname;
        public String boysname;
    }
}
