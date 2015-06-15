<html>
<head>
<style type="text/css">
body {
	font-family: sans-serif;
}

label {
	width: 250px;
	float: left;
}
input[type=text] {
	width: 400px;
	
}
</style>
</head>
<body>
	<h2>OAuth test</h2>
	<div>
		<form action="test" method="post">
			<label>API Key: </label><input type="text" name="apiKey"
				value="test.consumer.key"><br />
			
			<label>API Secret: </label><input type="text" name="apiSecret" value="test.consumer.secret"><br />
			
			<label>Request Token Endpoint:</label> <input type="text"
				name="requestTokenEndpoint" value="http://localhost:8080/megx.net/oauth/request_token"><br /> 
			
			<label>Access
				Token Endpoint:</label> <input type="text" name="accessTokenEndpoint"
				value="http://localhost:8080/megx.net/oauth/access_token"><br />
			
			<label>Authorization URL:</label> <input type="text"
				name="authorizationURL" value="http://localhost:8080/megx.net/oauth/authorize"><br /> 
				
			<label>Resource:</label> <input
				type="text" name="resourceURL" value="http://localhost:8080/megx.net/pubmap/some/resource"><br /> 
				
			<input type="submit"
				value="Go!">
		</form>
	</div>

</body>
</html>
