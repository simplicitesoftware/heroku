package com.simplicite.tomcat;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.catalina.startup.Tomcat;

public class Launcher
{
	public static void main(String[] args) {
		try {
			Tomcat tomcat = new Tomcat();

			File f = new File("").getAbsoluteFile();
			tomcat.setBaseDir(f.getAbsolutePath());
			tomcat.getServer().setCatalinaHome(f);
			System.out.println("Tomcat home dir: " + tomcat.getServer().getCatalinaHome());
			tomcat.getServer().setCatalinaBase(f);
			System.out.println("Tomcat base dir: " + tomcat.getServer().getCatalinaBase());

			tomcat.enableNaming();

			String port = System.getenv("TOMCAT_HTTP_PORT");
			if (port == null || port.length() == 0) port = System.getenv("PORT");
			if (port == null || port.length() == 0) port = "8080";
			tomcat.setPort(Integer.valueOf(port));

			System.out.println("Deploying ROOT webapp");
			File root = new File("./webapps/ROOT");
			if (!root.exists()) {
				root.mkdirs();
				File index = new File(root.getPath() + "/index.jsp");
				FileOutputStream fos = new FileOutputStream(index);
				fos.write(new String("It works!").getBytes());
				fos.close();
			}
			tomcat.addWebapp("", root.getAbsolutePath());

			tomcat.start();

			tomcat.getServer().await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
