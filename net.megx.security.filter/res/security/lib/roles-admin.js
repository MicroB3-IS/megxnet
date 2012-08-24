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
		   this.contentEl = $('.roles-panel-content', this.el)[0];
		   
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
			
			$(this.contentEl).html('').append(el);
			$(this.placeholder).html('').append(this.el);
			
			var self =  this;
			$('.roles-action-add', this.el).click(function(){
			   self.addRole();
			});
			
			loadPage(1);
		},
		show: function(){
			this.ready(this._show);
		},
		addRole: function(){
			this.ready(function(){
				this._clearContentPanel();
				var self = this;
				this.createRolePanel({}, true,
						function(panel, role, isNew){
							if(!panel.validate()){
								return; // invalid data
							}
							var r = panel.getData();
							var data = {
									label: r.newLabel,
									description: $.trim(r.description) || null,
									permissions: r.permissions.join(',')
							};
							data.users = [];
							for(var i = 0; i < role.usersInRole.length; i++){
								data.users.push(role.usersInRole[i].login);
							}
							data.users = data.users.join(',');
							self.rolesService.post('', data, function(){
								// successfully added
								self.n.message("Info: ", "Group " + data.label + " was successfully added.")
								self.show();
							}, function(){
								// failed to add
								self.n.error("Error: ", "Failed to add group: " + data.label);
							});
						},
						function(panel, role, isNew){
							self.n.confirm("Confirm Cancel", "Are you sure you want to cancel the adding of the new group?", 
									function(){
								self.show();
							});
						}
				);
			});
		},
		editRole: function(role){
			var self = this;
			this.ready(function(){
				this._clearContentPanel();
				this.createRolePanel(role, false, function(panel, role, isNew){
					if(!panel.validate()){
						return; // invalid data
					}
					var r = panel.getData();
					var data = {
							oldLabel: r.oldLabel,
							newLabel: r.newLabel,
							description: r.description,
							permissions: (r.permissions || []).join(',')
					};
					self.rolesService.put('', data, function(result){
						// successfully updated
						if(!result.error){
							self.n.message("Info: ", "Group " + result.label + " was successfully updated.")
							self.show();
						}else{
							self.n.error("Error: ", "Failed to update group: " + result.message);
						}
					},function(){
						// failed to update
						self.n.error("Error: ", "Failed to update group: " + data.label);
					});
				}, function(panel, role, isNew){
					self.n.confirm("Confirm Cancel", "Are you sure you want to cancel the update of group '"+role.label+"'?", 
							function(){
						self.show();
					});
				});
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
		createRolePanel: function(role, isNew, okCallback, cancelCallback){
			var self = this;
			okCallback = okCallback || $.noop;
			cancelCallback = cancelCallback || $.noop;
			var rp = new components.ui.DataPanel({
				selector: $('.roles-panel-content', this.el)[0],
		        title: 'Manage Group',
		        content:[
		            {
		            	type: 'text',
		            	value: role.label || '',
		            	name: 'newLabel',
		            	label: 'Name: ',
		            	validator: function(value){
		            		value = $.trim(value || '');
		            		if(value == ''){
		            			this.validatorMessage = "The name must not be empty.";
		            			return false;
		            		}
		            		else if(value.length < 3 || value.length > 20){
		            			this.validatorMessage = "The length of the group name must be between 3 and 20 characters long.";
		            			return false;
		            		}
		            		return true;
		            	}
		            },
		            {
		            	type: 'hidden',
		            	value: role.label || '',
		            	name: 'oldLabel'
		            },
		            {
		            	type: 'textbox',
		            	value: role.description != 'null' ? role.description  : '&nbsp;',
		            	name: 'description',
		            	label: 'Description: ',
		            	validator: function(value){
		            		value = $.trim(value || '');
		            		if(value.length > 30){
		            			this.validatorMessage = "The length of the group description must be between 10 and 40 characters long.";
		            			return false;
		            		}
		            		return true;
		            	}
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
		            	type: isNew ? 'ignore': 'section',
		            	title: 'Delete this group',
		            	content:[{
		            		type: 'button',
		            		value: 'Delete ' + role.label,
		            		name: 'delete-group',
		            		events:{
			            		click: function(){
			            			self.n.confirm('Confirm','Are you sure you want to remove this group?', function(){
			            				self.rolesService.del(role.label, undefined, function(data){
			            					if(data.error){
			            						self.n.error('Error: ', data.message);
			            					}else{
			            						rp.close();
			            						self.show();
			            					}
			            				},function(err){
			            					self.n.error('Error: ', 'Failed to remove group: ' + role.label);
			            				});
			            			});
			            		}
		            		}
		            	}]
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
		        			okCallback.call(panel, panel, role, isNew);
		        		}
		        	},
		        	'cancel': {
		        		text: 'Cancel',
		        		handler: function(e, panel){
		        			cancelCallback.call(panel, panel, role, isNew);
		        		}
		        	}
		        }
			});
			rp.show();
			
			
			var m = [
			     '<div class="">',
			     	'<div class="users-filter-placeholder"></div>',
			     	'<div>',
			     		'<div>List of users in this group</div>',
			     		'<div class="users-list"></div>',
			     	'</div>',
			     '</div>'
			];
			
			var umel = $(m.join(''))[0];
			var listSelect;
			//debugger
			$('.user-role-management-panel .panel-subsection-content', rp.el).html('').append(umel);
			
			var select = new FilterSelect({
				el: $('.users-filter-placeholder', umel)[0],
				label: 'Search for user to add: ',
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
									    		if(isNew){
									    			role.usersInRole = role.usersInRole || [];
									    			var alreadyInRole = false;
									    			for(var i = 0; i < role.usersInRole.length; i++){
									    				if(user.login == role.usersInRole[i].login){
									    					alreadyInRole = true;
									    					break;
									    				}
									    			}
									    			if(!alreadyInRole){
									    				role.usersInRole.push(user);
									    			// 	update the users list
									    				if(listSelect)
									    					listSelect.refresh();
									    			}
									    		}else{
										    		self.rolesService.post('addUser', {
										    			username: user.login,
										    			role: role.label
										    		}, function(){
										    			// update the users list
										    			if(listSelect)
										    				listSelect.refresh();
										    			
										    		}, function(){
										    			// failed to add user...
										    		});
									    		}
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
			
			
			var listSelect = new LongListSelect({
				el: $('.users-list', umel)[0],
				
			});
			
			
			if(isNew){
				// new group
				role.usersInRole = role.usersInRole ||[];
				listSelect.filter = function(query, page, pageSize, success, error){
					var results = [];
					query = query || '';
					each(role.usersInRole, function(i, u){
						if(query == '' || u.login.toLowerCase().indexOf(query.toLowerCase()) >= 0){
							results.push({
								title: $.trim(u.firstName + ' ' + u.lastName) || u.login || u.email,
								description: u.description || '&nbsp;',
								actions: [{
									text: 'Remove',
									extraClass: 'action-remove-user-from-role',
									handler: function(action){
										//debugger
										for(var i = 0; i < role.usersInRole.length; i++){
											if(u.login == role.usersInRole[i].login){
												role.usersInRole.splice(i, 1);
												listSelect.refresh(page);
												break;
											}
											
										}
									}
								}]
							});
						}
					});
					
					var i = (page-1)*pageSize;
					i = i >= 0 ? i : 0;
					var totalCount = results.length;
					if(i < results.length){
						results = results.slice(i, pageSize);
					}else{
						results = [];
					}
					
					success( {
						totalCount: totalCount,
						results: results,
						page: page,
						pageSize: pageSize
					} );
				};
			}else{
				// for existing group
				listSelect.filter = function(query, page, pageSize, success, error){
					query = query || '';
					query = $.trim(query) || '*';
					self.usersService.get('q/'+(role.label || '') +'/'+query +'/' + ((page-1)*pageSize) +':'+pageSize, undefined,
							function(result){
								if(result.error){
									error(result.message);
									return;
								}
								var results = [];
								
								each(result.results , function(i, r){
									results.push({
										title: $.trim((r.firstName + ' ' + r.lastName)) || r.login || r.email,
										description: r.description || '&nbsp;',
										actions:[{
											text: 'Remove',
											extraClass: 'action-remove-user-from-role',
											handler: function(action){
												var _roles = [];
												each(r.roles || [], function(i, _role){
													if(_role.label != role.label){
														_roles.push(_role.label);
													}
												});
												r.roles = _roles.join(',');
												self.usersService.put('', r, function(){
													listSelect.refresh();
												},function(){
													// TODO: handle error
												});
											}
										}]
									});
								});
								result.results = results;
								success(result);
							},
							function(){});
				};
			}
			
			
			listSelect.refresh();
			
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
			$(this.inputEl).keyup(function(e){
				if(e.which == 27){
					return;
				}
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
	
	
	
	var LongListSelect = function(config){
		extend(this, config);
		LongListSelect.superclass.constructor.call(this, config);
		this.init();
	};
	extend(LongListSelect, components.ui.EventBase);
	extend(LongListSelect, {
		init: function(){
			var m = [
			    '<div class="long-select-wrapper">',
			    	'<div class="long-select-header">',
			    		'<div class="long			-select-filter-wrapper">',
			    			'<label class="long-select-filter-label">Filter: </label>',
			    			'<input type="text" class="long-select-filter-input"/>',
			    		'</div>',
			    		'<div class="long-select-pager-placeholder">',
			    		'</div>',
			    	'</div>',
			    	'<div class="long-select-content">',
			    	'</div>',
			    	'<div class="long-select-footer">',
			    	'</div>',
			    '</div>'
			].join('');
			var el = $(m)[0];
			this.contentEl = $('.long-select-content', el)[0];
			this.filterInputEl = $('.long-select-filter-input', el)[0];
			this.pager = new components.ui.Pager({
				el: $('.long-select-pager-placeholder', el)[0]
			});
			
			var self = this;
			this.pager.on('goto-page', function(e, pager, page){
				self._loadData(page*1);
			});
			$(this.el).html('').append(el);
			
			
			$('.long-select-filter-input', el).keyup(function(e){
				self.filter($(this).val(), 1, 10, function(result){
					self.pager.reload(1, 10, result.totalCount);
					self.pager.update(1);
					self._showResults(result.results);
				}, function(message){
					// TODO: handle exception
				})
			});
			
		},
		filter: function(query, page, pageSize, success, error){
			
		},
		refresh: function(page){
			this._loadData(page || 1);
		},
		_loadData: function(page){
			//this.pager.reload(page, 10, result.totalCount);
			//this.pager.update(page);
			var self = this;
			this.filter(undefined, page, 10, function(result){
				self.pager.reload(page, 10, result.totalCount);
				self.pager.update(page);
				self._showResults(result.results);
			}, function(message){
				// TODO: handle exception
			});
		},
		_showResults: function(results){
			$(this.contentEl).html('');
			for(var  i = 0; i < results.length; i++){
				var entryEl = this._createSingleEntry(results[i]);
				$(this.contentEl).append(entryEl);
			}
		},
		/*
		 * action = {
		 * 		title: 'string',
		 * 		description: 'string',
		 * 		actions:[
		 * 			{
		 *				text: 'string',
		 *				extraClass: 'string',
		 *				handler: function(action){
		 *				} 
		 * 			}
		 * 		]
		 * }
		 * 
		 */
		_createSingleEntry: function(entry){
			var handlers = [];
			var self = this;
			var m = [
			     '<div class="long-select-entry">',
		     		'<div class="long-select-entry-actions"> &nbsp;',
		     			(function(actions){
		     				var fullMarkup = [];
		     				
		     				each(actions, function(i, action){
		     					var cls = '__action-' + i;
		     					var am = '<div class="long-select-entry-action ' + 
		     								(action.extraClass || '') + ' ' + cls +'" >' +
		     								(action.text || '') +
		     							  '</div>';
		     					fullMarkup.push(am);
		     					if(action.handler){
		     						handlers.push({
		     							selector: '.'+cls,
		     							handler: action.handler,
		     							action: action
		     						});
		     					}
		     				});
		     				
		     				return fullMarkup.join('');
		     			})(entry.actions || []),
		     		'</div>',
		     		'<div class="long-select-entry-content">',
				     	'<div class="long-select-entry-title">',
				     		entry.title || '',
				     	'</div>',
				     	'<div class="long-select-entry-description">',
			     			entry.description || '',
			     		'</div>',
		     		'</div>',
			     '</div>'
			].join('');
			var el = $(m)[0];
			each(handlers, function(i, h){
				$(h.selector, el).click(function(){
					h.handler.call(self, h.action);
				});
			});
			return el;
		}
	});
	
	
	
	
	window.admin = window.admin || {};
	window.admin.RolesManager = RolesManager;
})(jQuery, window.components);