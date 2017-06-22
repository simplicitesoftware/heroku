![Simplicit&eacute; Software](https://www.simplicite.io/resources/logos/logo250.png)
* * *

Heroku&reg; template for Simplicit&eacute;&reg;
===============================================

This is the embedded Tomcat template for Heroku&reg; designed for Simplicit&eacute;&reg; instances.

Prerequisites
-------------

Java JDK and Maven required to run locally.

A Heroku account and Heroku toolbelt required to deploy to Heroku
please refer to [Heroku dev center](https://devcenter.heroku.com/) for details

Clone our [embedded Tomcat template](https://github.com/simplicitesoftware/heroku-template) (if you are reading this file chances are this is already done ;-)

Get the Simplicit&eacute;&reg; instance template from our GIT repository
(see [simplicite.io website](http://www.simplicite.io) for details on how to get access to this repository).

Copy your **root** webapp in the `webapps/ROOT` folder (or leave the default test webapp as is):

	cp -r <template path>/app webapps/ROOT

Run locally
-----------

Build the package using Maven with:

	mvn package

Then run the package with:

	java -Dfile.encoding=UTF-8 -jar target/tomcat-0.0.1-SNAPSHOT-jar-with-dependencies.jar

NB: a similar run command is in the `Procfile`

Deploy on Heroku
----------------

### Initial deployment

Add the `heroku` remote to the cloned Git repository by creating a new application:

	heroku create [<your Heroku app name>]

or, if the application already exists:

	heroku git:remote -a <your Heroku app name>

Add and commit your changes locally in the Git repository:

	git add <...>
	git commit <...>

Then deploy to Heroku by:

	git push [--force] heroku master

or if you want to configure `heroku` as the default pustream for pushes:

	 git push --set-upstream heroku master

in this case subsequent pushes will only require:

	git push

**Note**: _to avoid accidentally pushing your changes to the `origin` remote, we recommend that you remove it by:_

	git remote remove origin

_or, if you still want to be able to pull from the `origin` remote, just inhibitate pushing to it by:_

	git remote set-url --push origin no_push

### Upgrade deployments

To upgrade a deployed instance you should resynchronize the **root** webapp, for instance by:

	rsync -auv <template path>/app/ webapps/ROOT

And then add, commit and push like described above (as of Simplicit&eacute;&reg; version 4.0, the auto upgrade option `-Dplatform.autoupgrade=true` will apply platform patches at startup).
