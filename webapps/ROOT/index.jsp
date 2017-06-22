<%@page import="java.nio.charset.Charset"%>
<pre>
It works!
Date: <%= new java.util.Date() %>
Encoding: <%= Charset.defaultCharset()  %>
Auto-upgrade: <%= System.getProperty("platform.autoupgrade")  %>
</pre>
