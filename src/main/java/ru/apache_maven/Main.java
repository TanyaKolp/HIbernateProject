package ru.apache_maven;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.apache_maven.models.Company;
import ru.apache_maven.models.User;

import java.util.*;

/**
 * Created by tania on 11/14/16.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("DB");
        init();



    }

    private static Optional<ArrayList<String>> init2(int k) {
        ArrayList<String> a = new ArrayList<String>();
        a.add("aaa");
        a.add("bbb");
        Optional<ArrayList<String>> li = Optional.of(a);
        if(k%2 == 0) return Optional.empty();
        return li;
    }


    private static void init() {
        try {
            Tests t = new Tests();
            t.b();
        } catch (NullPointerException e) {
            System.out.println("NPE");
        }catch (Exception e){
            System.out.println("Exp");
        }
    }
}



