(function($, admin){
	var extend = components.util.extend;
	var each = components.util.each;
	
	
	
	var RolesManager = function(config){
		this.rolesService = config.rolesService;
		this.usersService = config.usersService;
		this.placeholder = config.placeholder || document.body;
		var m = [
		         '<div class="roles-panel-wrapper">',
		         	'<div class="roles-panel-actions">',
		         		'Manage groups',
		         		'<div class="ui-corner-all admin-general-action roles-action-add" style="float: right;">',
		         			'Add Group',
		         			'<span class="ui-icon ui-icon-plusthick" style="display: inline-block;"></span>',
		         		'</div>',
		         		'</div>',
		         	'<div class="security-notification-container"></div>',
		         	'<div class="roles-panel-content"></div>',
		         '</div>'
		   ].join('');
		   this.el = $(m)[0];
		   this.n = new components.ui.NotificationManager({
		      selector: $('.security-notification-container', this.el)[0]
		   });
	};
	
	extend(RolesManager, {
		ready: function(callback){
			var self = this;
			this.rolesService.get('permissions', undefined, function(permissions){
				self.permissions = permissions;
				callback.call(self, permissions);
			}, function(){
				// error retrieving permissions...
			});
		},
		_show: function(){
			var self = this;
			var m = [
			   '<div>',
			   		'<div class="panel-content"></div>',
			   		'<div class="panel-footer">',
			   		'</div>',
			   '</div>'
			];
			var el = $(m.join(''));
			
			var pager = new components.ui.Pager({
				el: $('.panel-footer',el)[0]
			});
			
			
			var reloadRoles= function(roles){
				
				var mel = $('<div></div>')[0];
				
				each(roles, function(i, role){
					var m = [
					   '<div class="role-entry">',
					      '<div class="role-entry-title role-action role-action-edit">',
					      	role.label,
					      '</div>',
					      '<div class="role-entry-description">',
					      	role.description || '&nbsp;',
					      '</div',
					   '</div>'
					].join('');
					var rel = $(m)[0];
					mel.appendChild(rel);
					
					$('.role-action-edit',rel).click(function(){
						self.editRole(role);
					});
					
				});
				
				$('.panel-content', el).html('').append(mel);
			};
			
			var loadPage = function(page){
				page = page*1;
				self.rolesService.get((page-1)*10+':'+10, undefined, function(result){
					reloadRoles(result.results);
					pager.reload(page, 10, result.totalCount);
					pager.update(page);
				});
			};
			
			pager.on('goto-page', function(e, pager, page){
				loadPage(page);
			});
			
			$('.roles-panel-content', this.el).html('').append(el);
			$(this.placeholder).html('').append(this.el);
			loadPage(1);
		},
		show: function(){
			this.ready(this._show);
		},
		addRole: function(){},
		editRole: function(role){
			this.ready(function(){
				this._clearContentPanel();
				this.createRolePanel(role);
			});
		},
		_toUIPermissions: function(role){
			var result = [];
			each(role.permissions || [], function(i, p){
				result.push(p.label);
			}, this);
			
			return result;
		},
		_toUIAvailablePermissions: function(role){
			var result = [];
			var c = {};
			each(role.permissions, function(i, p){
				c[p.label] = p;
			},this);
			
			each(this.permissions || [], function(i,p){
				if(!c[p.label]){
					result.push({
						value: p.label,
						label: p.description
					});
				}
			},this);
			
			return result;
		},
		_clearContentPanel: function(){
			$('.roles-panel-content', this.el).html('');
		},
		removeRole: function(role){},
		createRolePanel: function(role){
			var self = this;
			var rp = new components.ui.DataPanel({
				selector: $('.roles-panel-content', this.el)[0],
		        title: 'Manage Group',
		        content:[
		            {
		            	type: 'text',
		            	value: role.label || '',
		            	name: 'newLabel',
		            	label: 'Name: '
		            },
		            {
		            	type: 'hidden',
		            	value: role.label || '',
		            	name: 'oldLabel'
		            },
		            {
		            	type: 'textbox',
		            	value: role.description || '&nbsp;',
		            	name: 'description',
		            	label: 'Description: '
		            },
		            {
		            	type: 'section',
		            	title: 'Group Permissions',
		            	content: [
		            	    {
		            	    	type: 'array-values',
		            	    	value: this._toUIPermissions(role),
		            	    	label: 'Permissions: ',
		            	    	name: 'permissions',
		            	    	events: {
		            	    		'value-remove': function(e, field, value){
		            	    			each(self.permissions, function(i, p){
		            	    				if(p.label == value){
		            	    					rp.getDataField('available_permissions').addValue(p.label, p.description);
		            	    					return false;
		            	    				}
		            	    			});
		            	    		}
		            	    	}
		            	    },
		            	    {
		            	    	type: 'select',
		            	    	options: this._toUIAvailablePermissions(role),
		            	    	label: 'Add Permission: ',
		            	    	name: 'available_permissions',
		            	    	events: {
		            	    		'change': function(e, f){
		            	    			if(!rp)
		            	    				return;
		            	    			
		            	    			rp.getDataField('permissions').addValue(f.getValue());
		            	    			f.removeValue(f.getValue());
		            	    		}
		            	    	}
		            	    },
		            	    {
		            	    	type: 'button',
		            	    	value: 'Add Permission',
		            	    	name: 'btn_add_permission',
		            	    	events: {
		            	    		'click': function(){
		            	    			var val = rp.getDataField('available_permissions').getValue();
		            	    			if(!val)
		            	    				return;
		            	    			rp.getDataField('permissions').addValue( val );
		            	    			rp.getDataField('available_permissions').removeValue(val);
		            	    		}
		            	    	}
		            	    }
		            	]
		            },
		            {
		            	type: 'section',
		            	title: 'Users',
		            	extraClass: 'user-role-management-panel',
		            	content: []
		            }
		        ],
		        buttons:{
		        	'ok':{
		        		text: 'Save',
		        		handler: function(e, panel) {
		        			
		        		}
		        	},
		        	'cancel': {
		        		text: 'Cancel',
		        		handler: function(e, panel){
		        			
		        		}
		        	}
		        }
			});
			rp.show();
			
			
			var m = [
			     '<div class="">',
			     	'<div class="users-filter-placeholder"></div>',
			     '</div>'
			];
			
			var umel = $(m.join(''))[0];
			//debugger
			$('.user-role-management-panel .panel-subsection-content', rp.el).html('').append(umel);
			
			var select = new FilterSelect({
				el: $('.users-filter-placeholder', umel)[0],
				filter: function(query, success, error){
					
					self.usersService.get('q/'+query, undefined, function(users){
						var results = [];
						each(users, function(i, user){
							var result = {
									label: user.login,
									description: user.description,
									actions: [
									    {
									    	extraClass: 'user-add',
									    	text: 'Add',
									    	handler: function(action){
									    		self.rolesService.post('addUser', {
									    			username: user.login,
									    			role: role.label
									    		}, function(){
									    			// update the users list
									    			
									    			
									    		}, function(){
									    			// failed to add user...
									    		});
									    		select.done();
									    	}
									    }
									]
							}
							results.push(result);
						});
						success(results)
					}, function(){
						// TODO: handle error
						error("Failed to filter users");
					});
				}
			});
			
			
			return rp;
		}
	});
	
	
	// UI Components
	
	var FilterSelect = function(config){
		extend(this, config);
		this.triggerLength = this.triggerLength || 3;
		FilterSelect.superclass.constructor.call(this, config);
		this.init();
	};
	
	extend(FilterSelect, components.ui.EventBase);
	extend(FilterSelect, {
		init: function(){
			//debugger
			//FilterSelect.superclass.init.call(this);
			var self = this;
			this.filter = this.filter || function(){};
			
			var m = [
			    '<div class="filter-select-wrapper">',
			    	'<label class="filter-select-label">',
			    	this.label || '',
			    	'</label>',
			    	'<input type="text" class="filter-select-input"/>',
			    	'<div class="filter-select-hint">',
			    		'<div class="filter-select-hint-wrapper"/>',
			    		'<div class="filter-select-footer">',
			    			'<div class="filter-select-action action-close">Close</div>',
			    		'</div>',
			    	'</div>',
			    '</div>'
			].join('');
			
			var el = $(m)[0];
			
			this.inputEl = $('.filter-select-input', el)[0];
			this.hintEl = $('.filter-select-hint-wrapper', el)[0];
			this.hintWrapper = $('.filter-select-hint', el)[0];
			$('.action-close', el).click(function(){
				self._clearResults();
			});
			$(this.el).html('').append(el);
			this._bind();
			var self = this;
			$(document).keyup(function(e){
				if(e.which == 27){
					// Close hints on ESC
					self._clearResults();
				}
			});
			this._clearResults();
		},
		_bind: function(){
			var self = this;
			$(this.inputEl).keyup(function(){
				var value = $.trim($(self.inputEl).val() || '');
				if(value.length > self.triggerLength){
					self.filter(value, function(results){
						self._displayResults(results);
					},function(message){
						alert(message);
					});
				}
			});
		},
		_clearResults: function(){
			$(this.hintEl).html('');
			$(this.hintWrapper).hide();
		},
		_displayResults: function(results){
			var self = this;
			this._clearResults();
			each(results || [], function(i, result){
				var actionHandlers = [];
				var m = [
				     '<div class="filter-select-result">',
				     	
				     	'<div class="filter-select-result-actions">',
				     			(function(actions){
				     				actions = actions || [];
				     				var t = [];
				     				each(actions, function(k, action){
				     					var id = 'action-' + i + '-' + k;
				     					t.push([
				     					     '<div class="result-entry-action ',
				     					     	action.extraClass || '', '" ',
				     					     	'id="',id,'" >',
				     					     	action.text || '',
				     					     '</div>'
				     				    ].join(''));
				     					if(action.handler){
						     				actionHandlers.push({
						     					id: id,
						     					handler: action.handler,
						     					action: action
						     				});
				     					}
				     				});
				     				
				     				return t.join('');
				     			})(result.actions),
				     	'</div>',
				     	'<div class="filter-select-result-content">',
				     		'<div class="filter-select-result-label">',
				     		result.label,
				     		'</div>',
				     		(result.description ? 
				     		'<div class="filter-select-result-description">' + result.description + '</div>' :
				     			''),
				     	'</div>',
				     '</div>'
				].join('');
				
				var rel = $(m)[0];
				
				each(actionHandlers, function(i, hnd){
					$('#'+hnd.id, rel).click(function(){
						hnd.handler.call(self, hnd.action);
					});
				});
				if(result.hander){
					(result.handler.selector ? $(result.handler.selector, rel) : $(rel) ).
						bind(result.handler.event, function(e){
							result.handler.callback.call(self, e);
						});
				}
				$(this.hintWrapper).show();
				$(this.hintEl).append(rel);
			},this);
			
		},
		getValue: function(){
			return $(this.inputEl).val();
		},
		setValue: function(val){
			$(this.inputEl).val(val);
		},
		done: function(){
			this._clearResults();
			this.setValue("");
		}
	});
	
	window.admin = window.admin || {};
	window.admin.RolesManager = RolesManager;
})(jQuery, window.components);