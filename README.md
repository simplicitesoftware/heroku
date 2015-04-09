Heroku&reg; template for Simplicit&eacute;&reg;
===============================================

This is the embedded Tomcat template for Heroku&reg; designed for Simplicit&eacute;&reg; sandboxes.

Prerequisites
-------------

Java JDK and Maven required to run locally.

A Heroku account and Heroku toolbelt required to deploy to Heroku
please refer to [Heroku dev center](https://devcenter.heroku.com/) for details

Clone our [embedded Tomcat template](https://github.com/simplicitesoftware/heroku-template) (if you are reading this file chances are this is already done ;-)

Get the Simplicit&eacute;&reg; sandbox template from our GIT repository
(see [simplicite.io website](http://www.simplicite.io) for details on how to get access to this repository).

Replace the default test `webapps/ROOT` webapp folder with your Simplicit&eacute; root webapp.

If required you can customize the `webapps/ROOT/META-INF/context.xml` and/or `webapps/ROOT/WEB-INF/web.xml` to your needs (e.g. to add additional datasources).

If required you can also add:

- Additional Java libs in `webapps/ROOT/WEB-INF/lib`
- Static JavaScript files in `webapps/ROOT/scripts`
- Static CSS/images files in `webapps/ROOT/images`
- Etc.

Run locally
-----------

Build the package using Maven with:

```
mvn package
```

Then run the package with:

```
java -jar target/tomcat-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

NB: a similar run command is in the `Procfile`

Deploy on Heroku
----------------

Add the `heroku` remote to the cloned Git repository with:

```
heroku create [<your Heroku app name>]
```

Commit your changes locally in the Git repository:

```
git commit <...>
```

Then deploy to Heroku by:

```
git push heroku master
```
