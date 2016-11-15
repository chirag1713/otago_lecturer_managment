/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

/**
 *
 * @author admin123
 */
import java.net.URL;
import java.net.URLClassLoader;

public class Classpath {

    public static void main(String[] args) {

        //Get the System Classloader
        ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();

        //Get the URLs
        URL[] urls = ((URLClassLoader) sysClassLoader).getURLs();

        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i].getFile().toString().replace("/F:/Projects/SujavtechFinal/trunk/", "")); //replace pre path as per req
        }

    }

}
