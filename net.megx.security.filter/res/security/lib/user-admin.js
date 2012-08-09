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
		                        users[i].firstName, ' ', users[i].lastName,
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
		   getUserEditPanel: function(user, saveCallback, cancelCallback){
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
		               title: 'Username'
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
		               title: 'e-mail'
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
		               title: 'Password'
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
		         var u = panel.getData();
		         if(u){
		            //var roles = [];
		            u.roles = (u.roles || []).join(',');
		            u.roles = u.roles || [];
		            if(u.password != u.re_password){
		               self.n.alert('','Password does not match! Please re-enter passwords.');
		               return;
		            }

		            //for(var i = 0; i < u.roles.length; i++){
		           //    roles.push({
		             //     label: u.roles[i]
		            //   });
		            //}
		            //u.roles = roles;
	   	         self.userService.put('',u, function(){
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
		      });
		   },
		   addUser: function(){
		      var self = this;
		      $('.security-users-panel-container', this.el).html('');
		      this.getUserEditPanel({}, function(panel){
		         var u = panel.getData();
		         if(u){
		            //var roles = [];
		            u.roles = (u.roles || []).join(',');
		            u.roles = u.roles || [];
		            if(u.password != u.re_password){
		               self.n.alert('','Password does not match! Please re-enter passwords.');
		               return;
		            }

		            //for(var i = 0; i < u.roles.length; i++){
		             //  roles.push({
		             //     label: u.roles[i]
		             //  });
		            //}
		            //u.roles = roles;
	   	         self.userService.post('',u, function(){
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