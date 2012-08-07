(function($, admin){
	var extend = components.util.extend;
	var each = components.util.each;
	
	
	
	var RolesManager = function(config){
		this.rolesService = config.rolesService;
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
				var markup = ['<div>'];
				for(var  i =0; i< roles.length; i++){
					markup.push([
					   '<div>',
					   roles[i].label,
					   '</div>'
					].join(''));
				}
				markup.push('</div>');
				var mel = $(markup.join(''))[0];
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
		editRole: function(role){},
		removeRole: function(role){},
		createRolePanel: function(role){
			
		}
	});
	
	
	window.admin = window.admin || {};
	window.admin.RolesManager = RolesManager;
})(jQuery, window.components);