package com.simplicite.tomcat;

import java.io.File;
import java.net.URI;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

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
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.print("--- Closing... ");
				System.out.println("Done");
			}
		});

		System.setProperty("file.encoding", "UTF-8");
		System.setProperty("platform.autoupgrade", "true");

		String db = System.getenv("DATABASE_URL");
		System.err.println(db);
		if (db!=null)
		{
			try {
				URI uri = new URI(db);
				if (db.startsWith("postgres:")) {
					System.setProperty("db.driver", "org.postgresql.Driver");
					String url = "postgresql://" + uri.getHost() + ':' + uri.getPort() + uri.getPath() + "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
					System.setProperty("db.url", url);
					System.setProperty("db.maxpoolsize", "20");
					System.out.println("--- Configured PostgreSQL database properties with URL [" + url + "]");
				} else if (db.startsWith("mysql:")) {
					System.setProperty("db.driver", "com.mysql.jdbc.Driver");
					String url = "mysql://" + uri.getHost() + ':' + uri.getPort() + uri.getPath() + "?autoReconnect=true&useSSL=false&characterEncoding=utf8&characterResultSets=utf8";
					System.setProperty("db.url", url);
					System.setProperty("db.maxpoolsize", "20");
					System.out.println("--- Configured MySQL database properties with URL [" + url + "]");
				} else
					throw new Exception("Unhandled database URL [" + db + "]");
				String[] userinfo = uri.getUserInfo().split(":");
				System.setProperty("db.username", userinfo[0]);
				System.setProperty("db.password", userinfo[1]);
			} catch (Exception e) {
				System.out.println("--- Error configuring database properties: " + e.getMessage());
			}
		}

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
		System.out.print("--- Looking for ROOT webapp in [" + rootAbsPath + "]... ");
		if (!root.exists()) {
			root = new File("test/ROOT");
			rootAbsPath = root.getAbsolutePath();
			System.out.print("does not exists, using test webapp in [" + rootAbsPath + "]... ");
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