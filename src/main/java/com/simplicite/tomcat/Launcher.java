package com.simplicite.tomcat;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.connector.Connector;

public class Launcher {
	private final static int DEFAULT_PORT = 8080;
	private final static String DEFAULT_ROOTPATH = "webapps/ROOT";

	private int port = 0;
	private String rootPath = null;

	public Launcher(String rootPath) throws Exception {
		String port = System.getenv("PORT");
		if (port != null && port.length() > 0)
			this.port = Integer.valueOf(port);

		if (rootPath != null && rootPath.length() > 0)
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
		
		int p = port == 0 ? DEFAULT_PORT : port;
		tomcat.setPort(p);
		System.out.println("--- Tomcat port set to [" + p + "]");
		
		if (port != 0) {
			Connector connector = tomcat.getConnector();
			connector.setSecure(true);
			connector.setScheme("https");
			System.out.println("--- Tomcat connector marked secure and scheme forced to https");
		}

		File root= new File(rootPath == null ? DEFAULT_ROOTPATH : rootPath);
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

		// Workaround(?) to get the websockets working.... does not work :-(
		/*
		Container[] cs = tomcat.getService().getContainer().findChildren();
		StandardHost h = (StandardHost)cs[0];
		cs = h.findChildren();
		StandardContext ctx = (StandardContext)cs[0];
		StandardJarScanner js = (StandardJarScanner)ctx.getJarScanner();
		js.setScanClassPath(true);
		*/

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