(function($, components){
	
	var extend = components.util.extend;
	var each = components.util.each;
	
	var ResourcesManager = function(config){
	   this.resourcesService = config.resourcesService;
	   this.userService = config.userService;
	   
	   var m = [
	      '<div class="resources-panel-wrapper">',
	         '<div class="resources-panel-actions">',
	            'Manage protected resources',
	            '<div class="ui-corner-all admin-general-action resources-action-add" style="float: right;">',
	               'Add Protected URL',
	               '<span class="ui-icon ui-icon-plusthick" style="display: inline-block;"></span>',
	             '</div>',
	         '</div>',
	         '<div class="security-notification-container"></div>',
	         '<div class="resources-panel-content"></div>',
	      '</div>'
	   ].join('');
	   this.el = $(m)[0];
	   this.n = new components.ui.NotificationManager({
	      selector: $('.security-notification-container', this.el)[0]
	   });
	   
	};
	ResourcesManager.HTTP_METHODS = {
	   'get': 'GET',
	   'put': 'PUT',
	   'post': 'POST',
	   'delete': 'DELETE',
	   'all': 'Any'
	};
	ResourcesManager.GET_HTTP_METHODS = function(){
	   var ms = [];
	   each(ResourcesManager.HTTP_METHODS, function(k,v){
	      ms.push({
	         value: k,
	         label: v
	      });
	   });
	   return ms;
	};
	
	extend(ResourcesManager, {
	   show: function(){
	      $('.content-placeholder').html('').append(this.el);
	      // add handlers here...
	      var self = this;
	      $('.resources-action-add', this.el).click(function(){
            self.addResource();
	      });
	   },
	   ready: function(callback){
	      var self = this;
	      this.userService.get('roles', undefined, function(roles){
	         self.roles = roles;
	         callback.call(self);
	      }, function(){
	         self.n.error('Error: ', 'Failed to retrieve the list of roles.');
	      });
	   },
	   showResources: function(){
	      var self = this;
	      
	      var getArr = function(arr, key){
	         var m = [];
	         each(arr, function(k,v){
	            var val = key ? v[key] : v;
	            m.push('<span class="array-value-inline ui-corner-all">' + (val  || '') + '</span>');
	         });
	         if(!m.length){
	            m.push('<span class="array-value-inline ui-corner-all">none</span>');
	         }
	         return m.join('');
	      };
	      
         this.ready(function(){
            var self = this;
            this.resourcesService.get('',undefined, function(resources){
               resources = self.fromServer(resources);
               var m = '<div class="resources-panel"></div>';
               var rpel = $(m)[0];
               for(var  i = 0; i < resources.length; i++){
                  var rc = resources[i];
                  var rm = [
                     '<div class="resource-entry ui-corner-all">',
                        '<span class="resource-pattern resource-action-edit">',
                           rc.urlPattern,
                        '</span>',
                        '<span class="rc-action-remove ui-icon ui-icon-closethick" style="float: right;"></span>',
                        '<div class="resource-entry-details">',
                              '<div>',
                                 '<label class="panel-field-label">Allowed HTTP Methods:</label>',
                                 getArr(resources[i].httpMethods),
                              '</div>',
                              '<div>',
                                 '<label class="panel-field-label">Allowed Roles:</label>',
                                 getArr(resources[i].roles),
                              '</div>',
                        '</div>',
                     '</div>'
                  ].join('');
                  var rmel = $(rm)[0];
                  $(rpel).append(rmel);
                  (function(resource){
                     $('.resource-action-edit', rmel).click(function(){
                        self.editResource(resource);
                     });
                     $('.rc-action-remove', rmel).click(function(){
                        self.removeResource(resource);
                     });
                  })(resources[i]);
                  
               }
               $('.resources-panel-content', self.el).html('').append(rpel);
               
               
               
               
            },function(){
               self.n.error('Error: ', 'Failed to retrieve the web resources list');
            });
         });
	   },
	   addResource: function(){
	      var self = this;
	      this.ready(function(){
	         this.clearMainPanel();
	         var ap = this.getResourcePanel({}, function(panel, resource){
	            var rc = {
                  urlPattern: resource.urlPattern,
                  httpMethods: (resource.httpMethods || []).join(','),
                  roles: (resource.roles || []).join(',')
               };
	            self.resourcesService.post('', rc, function(){
                  
	               panel.close();
	               self.n.message('Info: ', 'Resource added successfuly');
	               self.showResources();
	            }, function(status, err){
	               console.log('ERROR: ', status,' : ',err);
	               self.n.error('Error: ', 'Failed to add protected URL - ' + err);
	            });
	            return false;
	         }, function(panel){
	            self.showResources();
	            return true;
	         });
	      });
	   },
	   removeResource: function(rc){
	      var self = this;
	      this.n.confirm('Warning', 'Are you sure you want to remove the protected url: ' + 
	            '"<span style="font-weight: bold">' + rc.urlPattern + '</span>"?',
	               function(){
	                  self.resourcesService.del('', {urlPattern: rc.urlPattern}, function(){
	                     self.n.message("Info: ", 'Protected URL mapping was successfuly removed.');
	                     self.showResources();
	                  },function(status, err){
	                     self.n.error("Error: ", "Failed to remove the protected URL - " + err);
	                  });
	               });
	   },
	   editResource: function(res){
	      var self = this;
	      this.ready(function(roles){
	         this.clearMainPanel();
	         var ap = this.getResourcePanel(res, function(panel, resource){
	            var rc = {
                  urlPattern: resource.urlPattern,
                  httpMethods: (resource.httpMethods || []).join(','),
                  roles: (resource.roles || []).join(',')
               };
	            self.resourcesService.put('', rc, function(){
	               panel.close();
	               self.n.message('Info: ', 'Resource updated successfuly');
	               self.showResources();
	            }, function(status, err){
	               console.log('ERROR: ', status,' : ',err);
	               self.n.error('Error: ', 'Failed to add protected URL - ' + err);
	            });
	            return false;
	         }, function(panel){
	            self.showResources();
	            return true;
	         });
	      });
	   },
	   clearMainPanel: function(){
	      $('.resources-panel-content', this.el).html('');
	   },
	   getResourcePanel: function(resource, onOk, onCancel){
	      var self = this;
	      var rp = new components.ui.DataPanel({
	         selector: $('.resources-panel-content', this.el)[0],
	         title: 'Manage Web Resources Access',
	         content: [
	            {
	               type: 'text',
	               name: 'urlPattern',
	               label: 'URL Pattern: ',
	               title: 'URL Pattern',
	               value: resource.urlPattern || '',
	               validator: function(val){
	                  if($.trim(val) == ""){
	                	 this.notifyEl.innerHTML = "<b><font color='red'>Please enter non empty URL pattern</font></b>";
	                     return false;
	                  }
	                  this.notifyEl.innerHTML = "";
	                  return true;
	               }
	            },
	            {
	               type: 'section',
	               title: 'HTTP Methods allowed',
	               content: [
	                  {
	                     type: 'array-values',
	                     name: 'httpMethods',
	                     label: 'Allowed Methods: ',
	                     value: (function(r){
	                           var values = [];
	                           each(r.httpMethods || {}, function(k){ values.push(k);});   
	                           return values;
	                        })(resource),
	                     events: {
	                        'value-remove': function(e, f, value){
	                           rp.getDataField('httpMethod').addValue(value, ResourcesManager.HTTP_METHODS[value]);
	                        }
	                     }
	                  },
	                  {
	                     type: 'select',
	                     name: 'httpMethod',
	                     label: 'HTTP Method: ',
	                     title: 'HTTP Verb',
                        options: this._getHttpMethodsForResource(resource),
                        events: {
	                        'change': function(e, f){
	                           var value = f.getValue();
	                           rp.getDataField('httpMethods').addValue(value);
	                           f.removeValue(value);
	                        }
	                     },
	                  },
	                  {
	                     type: 'button',
	                     value: 'Add',
	                     name: 'add_http_method',
	                     events: {
	                        'click': function(e,f){
	                           var value = rp.getDataField('httpMethod').getValue();
	                           rp.getDataField('httpMethods').addValue(value);
	                           rp.getDataField('httpMethod').removeValue(value);
	                        }
	                     }
	                  }
	               ]
	            },
	            {
	               type: 'section',
	               title: 'Allowed Roles',
	               content: [
	                  {
	                     type: 'array-values',
	                     name: 'roles',
	                     label: 'Allowed For Roles',
	                     title: 'Allowed roles to access this resource',
	                     value: (function(o){
	                        var r = [];
	                        each(o, function(k){r.push(k);});
	                        return r;
	                     })(resource.roles),//this.getRolesForResource(resource),
	                     events: {
	                        'value-remove': function(e, f, value){
	                           for(var  i = 0; i < self.roles.length; i++){
	                              if(self.roles[i].label == value){
	                                 rp.getDataField('allRoles').addValue(value, self.roles[i].description);
	                              }
	                           }
	                        }
	                     }
	                  },
	                  {
	                     type: 'select',
	                     name: 'allRoles',
	                     label: 'Available Roles: ',
	                     options: this.availableRolesForResource(resource),
	                     events:{
	                        'change': function(e, f){
	                           if(rp){
   	                           var value = f.getValue();
   	                           rp.getDataField('roles').addValue(value);
	                              f.removeValue(value);
	                           }
	                        }
	                     }
	                  },
	                  {
	                     type: 'button',
	                     value: 'Add',
	                     name: 'add_roles',
	                     events:{
	                        'click': function(){
	                           
	                           var value = rp.getDataField('allRoles').getValue();
	                           rp.getDataField('roles').addValue(value);
	                           rp.getDataField('allRoles').removeValue(value);
	                        }
	                     }
	                  }
	               ]
	            }
	         ],
	         buttons: {
	            'ok': {
	               text: 'Save',
	               handler: function(e, panel){
	                  if(panel.validate()){
	                     console.log("valid");
	                     var rc = panel.getData();
	                     console.log("RESOURCE: ", rc);
	                     if(onOk){
	                        if(onOk.call(this, panel, rc)){
	                           panel.close();
	                        }
	                     }
	                  }else{
	                     console.log("invalid");
	                  }
	                    
	               }
	            },
	            'cancel': {
	               text: 'Cancel',
	               handler: function(e, panel){
	                  self.n.confirm('Info', "Are you sure you don't want to save the changes? All canges will be lost.",
                     function(){
                        panel.close();
                        if(onCancel){
                           onCancel.call(this, panel);
                        }
                     });
	               }
	            }
	         }
	      });
	      rp.show();
	      return rp;
	   },
	   _getHttpMethodsForResource: function(res){
	      res = res || {};
	      var htms = res.httpMethods || {};
	      var methods = [];
	      each(ResourcesManager.HTTP_METHODS, function(k,v){
	         if(!htms[k])
	            methods.push({
	               value: k,
	               label: v
	            });
	      }, this);
	      return methods;
	   },
	   fromServer: function(resources){
	      var rs = [];
	      var rsc = {};
	      //debugger
	      for(var i = 0; i < resources.length; i++){
	         var r = rsc[resources[i].urlPattern];
	         if(!r){
               r = rsc[resources[i].urlPattern] = {};//resources[i]; 
               r.urlPattern = resources[i].urlPattern;
               r.httpMethods = {};
               r.roles = {};
               rs.push(r);
	         }
            var hm = resources[i].httpMethod.toLowerCase();
            r.httpMethods[hm] = ResourcesManager.HTTP_METHODS[hm];
            for(var j = 0; j < resources[i].roles.length; j++){
               r.roles[resources[i].roles[j].label] = resources[i].roles[j].description;
            }
	      }
	      return rs;
	   },
	   fromUI: function(resource){
	      var rs = {
	         urlPattern: resource.urlPattern
	      };
	      rs.httpMethod = [];
	      each(resource.httpMethods || {}, function(k,v){
	         rs.httpMethod.push(v);
	      }); 
	      rs.httpMethod.join(',');
	      return rs;
	   },
	   getRolesForResource: function(r){
	      r = r || {};
	      var rs = [];
	      r.roles = r.roles || [];
	      for(var i = 0; i < r.roles.length; i++){
	         roles.push(r.roles[i].label);
	      }
	      return rs;
	   },
	   availableRolesForResource: function(r){
	      r = r || {};
	      r.roles = r.roles || {};
	      
	      var rs = [];
	      for(var  i = 0; i < this.roles.length; i++){
	         if(!r.roles[this.roles[i].label]){
	            rs.push({
	               value: this.roles[i].label,
	               label: this.roles[i].description
	            });
	         }
	      }
	      return rs;
	   }
	});
	
	window.admin = window.admin || {};
	window.admin.ResourcesManager = ResourcesManager;
	
})(jQuery, window.components)