(function($){
	var AppsManagerClient = function(baseUrl){
		this.baseUrl = baseUrl;
		var self = this;
		this.request = function(url, method, params, success, error, dataType){
			$.ajax({
				url: self.baseUrl + (url?("/" + url):""),
				type: method,
				data: params,
				dataType: dataType || 'json',
				success: function(data, status,xhr){
					if(success){
						success.call(self, data);
					}
				},
				error: function(xhr, txtStatus, err){
					console.log('Error: ', txtStatus, " ", err);
					error = error || (function(s,e){
						alert('Error. Code: '+ s + '\n'+(typeof(e) == 'string' ? e : e.message));
					});
					error.call(self, txtStatus, error);
				}
			});
		};
		
		this.getApps = function(success, error){
			this.request("all", "GET", undefined, success, error);
		};
		
		this.generateAccessToken = function(appKey, success, error){
			this.request(appKey + "/accessToken", "GET", undefined, success, error);
		};
		
		this.addApp = function(app, success, error){
			this.request(null, "POST", app, success, error);
		};
		
		this.updateApp = function(app, success, error){
			this.request(app.key, app, "PUT", undefined, success, error);
		};
		
		this.removeApp = function(appKey, success, error){
			this.request(appKey, "DELETE", undefined, success, error);
		};
	};
	
	apps = {
			Manager: AppsManagerClient
	};
	
	
})(jQuery);