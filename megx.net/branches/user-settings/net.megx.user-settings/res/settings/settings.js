(function($){
	var util = components.util;
	
	var AccountManager = function(config){
		util.extend(this, config);
		AccountManager.superclass.constructor.call(this, config);
		this.init();
	};
	util.extend(AccountManager, components.ui.EventBase);
	util.extend(AccountManager, {
		init: function(){
			//AccountManager.superclass.init.call(this);
			this._client = new components.data.RESTClient(this.wsBase, 
					function(status, e, xhr, errCallback){
						errCallback.call(this, status, e, xhr);
					});
		},
		_wrap: function(success, error){
			var self = this;
			return function(data){
				if(data.error === true){
					error.call(self._client, data);
				}else{
					success.call(self._client, data);
				}
			};
		},
		checkUsername: function(username, success, error){
			this._client.get('username/'+username,{},this._wrap(success, error), error);
		},
		getUserInfo: function(success, error){
			this._client.get('',{}, this._wrap(success, error), error);
		},
		updatePassword: function(oldPass, newPass, success, error){
			this._client.post('password',{
				oldPassword: oldPass,
				newPassword: newPass
			}, this._wrap(success, error), error);
		},
		updateUserAccount: function(user, success, error){
			this._client.post('', user, this._wrap(success, error), error);
		}
	});
	
	
	var ProfileEdit = function(accountSvcUrl, selector){
		ProfileEdit.superclass.constructor.call(this, {});
		this.accountManager = new AccountManager({
			wsBase: accountSvcUrl
		});
		this.selector = selector;
		this.bind();
	};
	util.extend(ProfileEdit, components.ui.EventBase);
	util.extend(ProfileEdit, {
		bind: function(){
			var self = this;
			this.modal = createModal();
			var editPanel = new components.ui.DataPanel({
				selector: this.modal.content,
				title: 'Edit Profile Info',
				content: [
				          {
				        	  type: 'raw',
				        	  markup:'<div class="notifications"></div>'
				          },
				          {
				        	  type: 'text',
				        	  name: 'firstName',
				        	  label: 'First Name: ',
				        	  title: 'First Name',
				        	  extraClass: 'settings-input'
				          },
				          {
				        	  type: 'text',
				        	  name: 'lastName',
				        	  label: 'Last Name: ',
				        	  title: 'Last Name',
				        	  extraClass: 'settings-input'
				          },
				          {
				        	  type: 'text',
				        	  name: 'initials',
				        	  label: 'Initials: ',
				        	  title: 'Initials',
				        	  extraClass: 'settings-input'
				          },
				          {
				        	  type: 'textarea',
				        	  name: 'description',
				        	  label: 'Description: ',
				        	  title: 'Short decription',
				        	  extraClass: 'settings-input'
				          },
				          {
				        	  type: 'text',
				        	  name: 'email',
				        	  label: 'Email: ',
				        	  title: 'Email',
				        	  extraClass: 'settings-input',
				        	  validator: function(val){
				        		  val = $.trim(val || '');
				        		  if(!val){
				        			  this.validatorMessage = 'Please enter your email.';
				        			  return false;
				        		  }else{
				        			  if(! /^[A-Za-z0-9\._%\+-]+@[A-Za-z0-9\.-]+\.[A-Za-z]{2,4}$/.test(val) ){
				        				  this.validatorMessage = 'Invalid email. Please enter a valid email address.';
				        				  return false
				        			  }
				        			  return true;;
				        		  }
				        	  }
				          },
				          {
				        	  type: 'section',
				        	  title: 'Username',
				        	  content:[
				        	     {
					        		  type: 'raw',
					        		  markup: ['<div style="color: gray; text-align: left;">',
					        		           		'Change your username.',
					        		           		'<p> Once you\'ve done typing, the system will check the availability of the new username.',
					        		           '</div>'].join('')
				        	  	},
					        	{
				        		  type: 'text',
				        		  name: 'login',
				        		  title: 'Your user ID',
				        		  label: 'Username',
				        		  extraClass: 'settings-input',
				        		  events:{
				        			  'blur': function(e,field){
				        				  var newUsername = field.getValue();
				        				  if(self.user.login != newUsername){
				        					  this.checking = true;
				        					  self.accountManager.checkUsername(newUsername, function(result){
				        						  
				        						  if(result.available == "true"){
				        							  field.message('<span class="notification-ok">The username is available</span>');
				        							  this.usernameValid = true;
				        						  }else{
				        							  field.notify("The username is not available");
				        							  this.usernameValid = false;
				        						  }
				        						  this.checking = false;
				        					  }, function(){
				        						  this.checking = false;
				        						  field.notify("Unable to check the username");
				        						  this.usernameValid = false;
				        					  });
				        				  }
				        			  }
				        		  },
				        		  validator: function(val){
				        			  if(val != self.user.login){
				        				  if( this.checking){
				        					  return false;
				        				  }else{
				        					  return this.usernameValid;
				        				  }
				        			  }
				        			  return true;
				        		  }
				        	  }]
				          },
				],
				buttons: {
					'save': {
						text: 'Save',
						handler: function(e, p){
							
							if(p.validate()){
								var data = p.getData();
								self.user.login = data.login;
								self.user.firstName = data.firstName;
								self.user.lastName = data.lastName;
								self.user.initials = data.initials;
								self.user.email = data.email;
								self.user.description = data.description;
								self.accountManager.updateUserAccount(self.user, function(updated){
									self.trigger('info-updated', updated);
									self.destroy();
								}, function(err){
									self.error("Failed to update user account: " + err);
								});
							}
						},
						order: 0
					},
					'cancel':{
						text: 'Cancel',
						handler: function(){
							self.destroy();
						},
						order: 1
					}
				}
			});
			this.panel = editPanel;
		},
		editUserData: function(){
			var self = this;
			this.accountManager.getUserInfo(function(user){
				self.user = user;
				self.panel.setData(user);
				self.show();
			},function(){
				self.error('Unable to obtain the user data.');
			});
		},
		show: function(){
			this.modal.show();
			this.panel.show();
		},
		destroy: function(){
			this.panel.close();
			this.modal.close();
		},
		error: function(msg){
			$('.notifications', this.panel.el).append('<div class="edit-notification-error">' + msg + '</div>');
		},
		notify: function(msg){
			$('.notifications', this.panel.el).append('<div class="edit-notification">' + msg + '</div>');
		},
		clearMsg: function(){
			$('.notifications', this.panel.el).html('');
		}
	});
	
	var createModal = function(onClose){
		var m = [
		         '<div class="modal-overlay" style="display: none;">',
		         	'<div class="modal-content">',
		         	'</div>',
		         '</div>'
		];
		var el = $(m.join(''))[0];
		$(document.body).append(el);
		var content = $('.modal-content')[0];
		return {
			el: el,
			content: content,
			show: function(){
				$(el).show();
			},
			close: function(){
				if(onClose){
					onClose.call(this);
				}
				$(el).remove();
			}
		};
	};
	
	
	window.components.settings = {
			ProfileEdit: ProfileEdit,
			AccountManager: AccountManager
	};
	
})(jQuery);