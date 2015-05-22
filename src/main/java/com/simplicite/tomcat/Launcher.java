package com.simplicite.tomcat;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.catalina.startup.Tomcat;

public class Launcher {
	private int port = 8080;
	private String rootPath = "webapps/ROOT";

	public Launcher(String rootPath) throws Exception {
		String port = System.getenv("TOMCAT_HTTP_PORT");
		if (port == null || port.length() == 0)
			port = System.getenv("PORT");
		if (port != null && port.length() > 0)
			this.port = Integer.valueOf(port);

		if (rootPath != null)
			this.rootPath = rootPath;
	}

	public void launch() throws Exception {
		Tomcat tomcat = new Tomcat();

		File f = new File("").getAbsoluteFile();
		tomcat.setBaseDir(f.getAbsolutePath());
		tomcat.getServer().setCatalinaHome(f);
		tomcat.getServer().setCatalinaBase(f);
		System.out.println("--- Tomcat home and base dirs set to [" + tomcat.getServer().getCatalinaHome() + "]");

		tomcat.enableNaming();

		tomcat.setPort(port);
		System.out.println("--- Tomcat port set to [" + port + "]");

		File root= new File(rootPath);
		String rootAbsPath = root.getAbsolutePath();
		System.out.print("--- Looking for ROOT webapp in [" + rootAbsPath + "]... ");
		if (!root.exists()) {
			System.out.print("Creating... ");
			root.mkdirs();
			File index = new File(root.getPath() + "/index.jsp");
			FileOutputStream fos = new FileOutputStream(index);
			fos.write(new String("It works (<%= new java.util.Date() %>)!").getBytes());
			fos.close();
		}
		System.out.println("Done");

		System.out.print("--- Deploying ROOT webapp... ");
		tomcat.addWebapp("", rootAbsPath);
		System.out.println("Done");

		tomcat.start();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.print("--- Closing... ");
				// TODO: cleaning cache, temporary and work folders
				System.out.println("Done");
			}
		});

		tomcat.getServer().await();
	}

	public static void main(String[] args) {
		try {
			new Launcher(args != null && args.length > 0 ? args[0] : null).launch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}