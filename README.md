Heroku template for Simplicit&eacute;&reg;
==========================================

This is the embedded Tomcat template for Heroku designed for Simplicit&eacute;&reg; sandboxes.

Prerequisites
-------------

A Heroku account and Heroku toolbelt installed. plese refer to [Heroku dev center](https://devcenter.heroku.com/)  for details

Deployment
----------

Clone our [Tomcat launcher](https://github.com/simplicitesoftware/heroku-template) (if you are reading this file chances are this is already done ;-)

Replace the `webapps/ROOT` webapp folder by your Simplicit&eacute; sandbox webapp template, then add the `heroku` Git remote by:

```
heroku create [<your app name>]
```

If required you can customize the `webapps/ROOT/META-INF/context.xml` and/or `webapps/ROOT/WEB-INF/web.xml` to your needs (e.g. to add additional datasources).

If required you can also add additional Java libs in `webapps/ROOT/WEB-INF/lib`
and/or static JavaScript files in `webapps/ROOT/scripts`
and/or static CSS/images files in `webapps/ROOT/images`

Commit your changes to the local copy of the Git repository `git commit <...>`, then deploy to Heroku by pushing to the `heroku` remote:

```
git push heroku master
```
