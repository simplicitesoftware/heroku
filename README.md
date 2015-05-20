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

Put your **root** webapp in the `webapps/ROOT` folder. If this folder does not exists, a default minimalistic webapp is created at firt launch.
A test root webapp is available in the `test` folder.

Run locally
-----------

Build the package using Maven with:

```
mvn package
```

Then run the package with:

```
java -Dfile.encoding="UTF-8" -jar target/tomcat-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

NB: a similar run command is in the `Procfile`

Deploy on Heroku
----------------

Add the `heroku` remote to the cloned Git repository by creating a new application:

```
heroku create [<your Heroku app name>]
```

or, if the application already exists:

```
heroku git:remote -a <your Heroku app name>
```

Add and commit your changes locally in the Git repository:

```
git add <...>
git commit <...>
```

Then deploy to Heroku by:

```
git push heroku master
```

_Note: to avoid pushing your changes to the default origin repository, we recommend that you remove the `origin` remote by:_

```
git remote rm origin
```

_or, if you still want to be able to pull from `origin`, just inhibitate pushing to `origin` by:_

```
 git remote set-url --push origin no_push
```
