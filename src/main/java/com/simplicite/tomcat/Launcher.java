package com.simplicite.tomcat;

import java.io.File;
import java.net.URI;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;

public class Launcher {
	private final static int DEFAULT_PORT = 8080;

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
		//System.setProperty("platform.autoupgrade", "true");
		//System.setProperty("server.websocket", "false");

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

		File root = new File(rootPath == null ? "webapps/ROOT" : rootPath);
		String rootAbsPath = root.getAbsolutePath();
		System.out.print("--- Deploying ROOT webapp [" + rootAbsPath + "]... ");
		Context ctx = tomcat.addWebapp("", rootAbsPath);
		System.out.println("Done");

		ContextResource db = null;
		String dbEnvVarName = System.getProperty("db.envvar.name");
		if (dbEnvVarName == null) dbEnvVarName = "DATABASE_URL";
		String dbURL = System.getenv(dbEnvVarName);
		if (dbURL!=null)
		{
			System.out.print("--- Configuring datasource [" + dbURL + "] for ROOT webapp... ");
			try {
				URI uri = new URI(dbURL);
				String driver, url;
				if (dbURL.startsWith("postgres:")) {
					driver = "org.postgresql.Driver";
					url = "postgresql://" + uri.getHost() + ':' + uri.getPort() + uri.getPath() + "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
				} else if (dbURL.startsWith("mysql:")) {
					driver = "com.mysql.jdbc.Driver";
					url = "postgresql://" + uri.getHost() + ':' + uri.getPort() + uri.getPath() + "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
				} else
					throw new Exception("Unhandled database vendor");

				db = new ContextResource();
				db.setName("jdbc/simplicite");
				db.setAuth("Container");
				db.setType("javax.sql.DataSource");
				db.setScope("Sharable");
				db.setProperty("driverClassName", driver);
				db.setProperty("url", "jdbc:" + url);
				String[] userinfo = uri.getUserInfo().split(":");
				db.setProperty("username", userinfo[0]);
				db.setProperty("password", userinfo[1]);
				ctx.getNamingResources().addResource(db);

				System.out.println("Done");
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				db = null;
			}
		}

		// Workaround(?) to get the websockets working.... does not work :-(
		/*
		Container[] cs = tomcat.getService().getContainer().findChildren();
		StandardHost h = (StandardHost)cs[0];
		cs = h.findChildren();
		StandardContext ctx = (StandardContext)cs[0];
		StandardJarScanner js = (StandardJarScanner)ctx.getJarScanner();
		js.setScanClassPath(true);
		*/

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.print("--- Closing... ");
				System.out.println("Done");
			}
		});

		tomcat.start();
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
