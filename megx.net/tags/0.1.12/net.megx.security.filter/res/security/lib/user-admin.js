(function($, components){
	var extend = components.util.extend;
	var each = components.util.each;
	
	
	var UserManager = function(config){
		   var m = [
		      '<div>',
		         '<div class="security-users-panel">',
		            '<div class="security-users-header">',
		               'Manage Users',
		               '<div style="float: right;">',
		                  '<div class="ui-corner-all admin-general-action security-action-add-user" style="padding: 5px;">Add User<span class="ui-icon ui-icon-plusthick" style="display: inline-block;"></span></div>',
		               '</div>',
		            '</div>',
		            '<div class="security-notification-container"></div>',
		            '<div class="security-users-panel-container"></div>',
		         '</div>',
		      '</div>'
		   ].join('');
		   
		   this.el = $(m)[0];
		   this.n = new components.ui.NotificationManager({
		      selector: $('.security-notification-container', this.el)[0]
		   });
		   var self = this;
		   
		   
		   this.placeholder = config.placeholder;
		   //$(config.placeholder).append(this.el);
		   this.show();
		   this.roles = config.roles || [];
		   this.userService = config.userService;
		   
		};
		
		$.extend(UserManager.prototype, {
		   show: function(){
		      var self = this;
		      $(this.placeholder).html('').append(this.el);
		      $('.security-action-add-user', this.el).click(function(){
		         self.addUser();
		      });
		   },
		   showUsers: function(){
		      var self = this;
		      var pel = $('.security-users-panel-container', this.el);
		      this.n.wait('Loading users');
		      
		      this.userService.get("roles", undefined, function(roles){
		         self.roles = roles;
		         self.userService.get('',undefined, function(users){
		            self.n.done();
		            self.users = {};
		            pel.html('');
		            for(var i = 0; i < users.length; i++){
		               var um = [
		                  '<div class="user-entry ui-corner-all" title="',users[i].login,'">',
		                     '<span class="user-label user-action-edit">',
		                        $.trim((users[i].firstName||'') + ' ' + (users[i].lastName||'')) || users[i].email,
		                     '</span>',
		                     '<span class="user-action-remove ui-icon ui-icon-closethick"></span>',
		                  '</div>'
		               ].join('');
		               var el = $(um)[0];
		               $(pel).append(el);
		               
		               (function(user){
		                  $('.user-action-remove', el).click(function(){
		                     self.removeUser(user);
		                  });
		                  $('.user-action-edit', el).click(function(){
		                     self.editUser(user);
		                  });
		               })(users[i]);
		               self.users[users[i].login] = users[i];
		            }
		            
		            
		         },function(xhr, status, error){
		            self.n.done();
		            self.n.error('Failed to retrieve users list: ', error);
		         });
		      }, function(xhr, status, error){
		         self.n.done();
		         self.n.error('Failed to retrieve roles: ', error);
		      });
		      
		      
		      
		   },
		   getUserEditPanel: function(user, saveCallback, cancelCallback, isEdit){
		      var self = this;
		      
		      var roles = [];
		      if(user.roles){
		         for(var i = 0; i < user.roles.length; i++){
		            roles.push(user.roles[i].label);
		         }
		      }
		      user.roles = roles;
		      var uep = new components.ui.DataPanel({
		         selector: $('.security-users-panel-container', this.el)[0],
		         title: 'Add User',
		         content:[
		            {
		               type: 'text',
		               name: 'login',
		               label: 'Username: ',
		               title: 'Username',
		               validator: function(value){
		            	   value = $.trim(value || '');
		            	   if(!isEdit && (value.length < 3 || value.length > 20) ){
		            		   this.validatorMessage = 'The length of the username must be between 3 and 20 characters long.';
		            		   return false;
		            	   }
		            	   if(!isEdit && !/^[a-zA-Z0-9_\\.\\-]+$/.test(value)){
		            		   this.validatorMessage = "Please enter valid username. The username can be any combination of letters, numbers, underscore dot or a dash.";
		            		   return false;
		            	   }
		            	   return true;
		               }
		            },
		            {
		               type: 'text',
		               name: 'firstName',
		               label: 'First Name: ',
		               title: 'First Name'
		            },
		            {
		               type: 'text',
		               name: 'lastName',
		               label: 'Last Name: ',
		               title: 'Last Name'
		            },
		            {
		               type: 'text',
		               name: 'initials',
		               label: 'Initials: ',
		               title: 'Initials'
		            },
		            {
		               type: 'text',
		               name: 'email',
		               label: 'e-mail: ',
		               title: 'e-mail',
		               validator: function(val){
		            	   val = $.trim(val || '');
		            	   if(val == ''){
		            		   this.validatorMessage = 'Please enter your email.';
		            		   return false;
		            	   }
		            	   if( !(/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(val)) ){
		            		   this.validatorMessage = 'Please enter valid email.';
		            		   return false;
		            	   }
		            	   return true;
		               }
		            },
		            {
		               type: 'text',
		               name: 'description',
		               label: 'Description: ',
		               title: 'Description of the user'
		            },
		            {
		               type: 'password',
		               name: 'password',
		               label: 'Password: ',
		               title: 'Password',
		               validator: function(value){
		            	   value = $.trim(value || '');
		            	   if(isEdit && value == ''){
		            		   return true;
		            	   }
		            	   if(value.length < 3 || value.length > 20){
		            		   this.validatorMessage = 'The length of the password must be between 3 and 20 characters long.';
		            		   return false;
		            	   }
		            	   return true;
		               }
		            },
		            {
		               type: 'password',
		               name: 're_password',
		               label: 'Repeat Password: '
		            },
		            {
		               type: 'checkbox',
		               name: 'disabled',
		               label: 'Disabled',
		               title: 'User is disabled'
		            },
		            {
		               type: 'section',
		               title: 'Roles',
		               content: [
		                  {
		                     type: 'array-values',
		                     title: 'Roles',
		                     name: 'roles',
		                     label: 'Roles: ',
		                     events: {
		                        'value-remove': function(e,f,val){
		                           uep.getDataField('select_roles').addValue(val);
		                        }
		                     },
		                     validator: function(val){
		                    	 if(!val || !val.length){
		                    		 this.validatorMessage = 'The user must have at least one role.';
		                    		 return false;
		                    	 }
		                    	 return true;
		                     }
		                  },
		                  {
		                     type: 'select',
		                     title: 'select roles',
		                     name: 'select_roles',
		                     label: 'Select Role: ',
		                     events: {
		                        'change': function(e, f){
		                           var value = f.getValue();
		                           uep.getDataField('roles').addValue(value);
		                           f.removeValue(value);
		                        }
		                     },
		                     options:this.getUIRoles(roles)
		                  },
		                  {
		                     type: 'button',
		                     title: 'Add Role',
		                     value: 'Add',
		                     name: 'add_role',
		                     events: {
		                        'click': function(){
		                           var value = uep.getDataField('select_roles').getValue();
		                           uep.getDataField('roles').addValue(value);
		                           uep.getDataField('select_roles').removeValue(value);
		                        }
		                     }
		                  }
		               ]
		            }
		         ],
		         
		         buttons: {
		            'save': {
		               text: 'Save',
		               handler: function(panel){
		                  saveCallback.call(this, uep);
		               },
		               order: 1
		            },
		            'cancel': {
		               text: 'Cancel',
		               handler: function(panel){
		                  self.n.confirm('Info', "Are you sure you don't want to save the changes? All canges will be lost.",
		                  function(){
		                     uep.close();
		                     if(cancelCallback){
		                        cancelCallback.call(this, uep);
		                     }
		                  });
		               },
		               order: 0
		            }
		            
		         }
		      });
		      
		      uep.show();
		      if(user){
		         uep.setData(user);
		      }
		      return uep;
		   },
		   removeUser: function(user){
		      var self = this;
		      this.n.confirm('Confirm', "Are you sure you want to remove user '" + 
		         user.firstName + " " + user.lastName + "'?",
		            function(){
		               self.userService.del(user.login, undefined, function(){
		                  self.n.message('Info: ', "User " + user.firstName + " " + user.lastName + "' has been removed");
		                  self.showUsers();
		               }, function(x, status, error){
		                  self.n.error("Error: ", "Failed to remove user.");
		               });
		            });
		   },
		   editUser: function(user){
		      var self = this;
		      $('.security-users-panel-container', this.el).html('');
		      this.getUserEditPanel(user, function(panel){
		    	 if(!panel.validate()){
		    		 return;
		    	 }
		         var u = panel.getData();
		         u.password = $.trim(u.password);
		         u.re_password = $.trim(u.re_password);
		         if(u){
		            u.roles = (u.roles || []).join(',');
		            u.roles = u.roles || [];
		            if(u.password != u.re_password){
		               self.n.alert('','Password does not match! Please re-enter passwords.');
		               return;
		            }
	   	         self.userService.put('',u, function(data){
	   	        	if(data.error){
	   	        		self.n.error("Error: ", data.message);
	   	        		return;
	   	        	}
	   	            self.n.message('Info:', 'User updated.');
	   	            panel.close();
	   	            self.showUsers();
	   	         },function(){
	   	            self.n.error("Error: ", "Failed to update user.");
	   	         });
		         }

		      },
		      function(){
		         self.showUsers();
		      }, true);
		   },
		   addUser: function(){
		      var self = this;
		      $('.security-users-panel-container', this.el).html('');
		      this.getUserEditPanel({}, function(panel){
		         var u = panel.getData();
		         if(!panel.validate()){
		    		 return;
		    	 }
		         if(u){
		            u.roles = (u.roles || []).join(',');
		            u.roles = u.roles || [];
		            if(u.password != u.re_password){
		               self.n.alert('','Password does not match! Please re-enter passwords.');
		               return;
		            }
	   	         self.userService.post('',u, function(data){
	   	        	 if(data.error){
	   	        		 self.n.error("Error: ", data.message);
	   	        		 return;
	   	        	 }
	   	        	 self.n.message('Info:', 'User added.');
	   	        	 panel.close();
	   	        	 self.showUsers();
	   	         },function(){
	   	            self.n.error("Error: ", "Failed to add user.");
	   	         });
		         }

		      },
		      function(){
		         self.showUsers();
		      });
		   },
		   getUIRoles: function(skip){
		      var r = [];
		      var s = {};
		      skip = skip ||[];
		      for(var i = 0; i < skip.length; i++)
		         s[skip[i]]=skip[i];
		      for(var i = 0; i < this.roles.length; i++){
		         if(s[this.roles[i].label])
		            continue;
		         r.push({
		            label: this.roles[i].label,
		            value: this.roles[i].label
		         });
		      }
		      return r;
		   }
		});
		
		
		window.admin = window.admin || {};
		window.admin.UserManager = UserManager;
})(jQuery, window.components)