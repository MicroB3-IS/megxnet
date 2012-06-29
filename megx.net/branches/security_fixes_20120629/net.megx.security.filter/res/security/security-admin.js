(function($){
	
	if(!window.console)
	   window.console = {
	      log: function(){
	         // do nothing
	      }
	   };
	
	// Object Oriented paradigm "extends"
	var extend =  function(a, b){
       var oa = $.isFunction(a) ? a.prototype : a;
       var ob = $.isFunction(b) ? b.prototype : b;
       $.extend(oa, ob);
       if($.isFunction(a) && $.isFunction(b)){
           a.superclass = ob;
       }
   };
	
	var each = function(set, callback, scope){
      var i = 0;
      for(k in set){
         if(set.hasOwnProperty(k)){
            if(callback.call(scope || window, k, set[k], i) === false){
               break;
            }
            i++;
         }
      }
   };
	   
	
	var RESTClient = function(baseUrl){
	   this.baseUrl = baseUrl;
	   var self = this;
	   
		this._request = function(url, method, data, success, error){
		   var _url = self.baseUrl + (url?("/" + url):"");
		   console.log('REST: ', method, ' ', _url, data);
			$.ajax({
				url: _url,
				type: method,
				data: data || {},
				dataType: 'json',
				success: function(data, status,xhr){
				   console.log('REST-DONE: ', method, ' ', url, data);
					if(success){
						success.call(self, data);
					}
				},
				error: function(xhr, txtStatus, err){
				   console.log('REST-ERR: ', method, ' ', url, data, ' :: ', txtStatus, err);
					error = error || (function(s,e){
						alert('Error. Code: '+ s + '\n'+(typeof(e) == 'string' ? e : e.message));
					});
					error.call(self, txtStatus, error);
				}
			});
		};
		
		this.get = function(url, data, success, error){
		   this._request(url, 'GET', data, success, error);
		};
		
		this.put = function(url, data, success, error){
		   this._request(url, 'PUT', data, success, error);
		};
		
		this.post = function(url, data, success, error){
		   this._request(url, 'POST', data, success, error);
		};
		
		this.del = function(url, data, success, error){
		   this._request(url, 'DELETE', data, success, error);
		};
		
		
	};
	
	
	var NotificationManager = function(config){
	   this.selector = config.selector || document.body;
	   this.infoTimeout = config.infoTimeout || 15000; // 15 seconds
	   
	   this.getMessageEl = function(type, title, message, noClose){
	      var m = [
		   '<div class="ui-widget apps-notify">',
			   '<div class="ui-corner-all ',(type=='info'?'ui-state-highlight':'ui-state-error'),'">',
				   '<div class="security-notify-close">',
					   '<span class="ui-icon ui-icon-close" style="float: right;"></span>',
				   '</div>',
				   '<p>',
					   '<span class="ui-icon ',(type=='info'?'ui-icon-info':'ui-icon-alert'),'" style="float: left;"></span>',
					   '<span style="font-weight: bold;">', title , '</span>',
					   message,
				   '</p>',
			   '</div>',
		   '</div>'
		   ].join('');
		   
		   var el = $(m)[0];
		   $('.security-notify-close', el).click(function(){
		      $(el).remove();
		   });
		   if(!noClose || type == 'info'){
		      setTimeout(function(){
		         $(el).remove();
		      }, this.infoTimeout);
		   }
		   
		   return el;
	   };
	   
	   
	   this.message = function(title, msg){
	      $(this.selector).append(this.getMessageEl('info',title || 'Info: ', msg));
	   };
	   
	   this.notify = function(title, msg){
	      this.message(title, msg);
	   }
	   
	   this.error = function(title, msg){
	      $(this.selector).append(this.getMessageEl('error', title || 'Error: ', msg));
	   }
	   
	   this.wait = function(msg){
	      if(this._w)
	         return;
	      this._w = this.getMessageEl('info', '', (msg || 'Loading') + '<span class="notification-wait-animation"></span>');
	       $(this.selector).append(this._w);
	   };
	   
	   this.done = function(){
	      if(this._w)
	         $(this._w).remove();
	   };
	   this.confirm = function(title, message, ok, cancel){
	      var body = $('<div class="notification-message">' + message + '</div>')[0];
	      $(body).dialog({
	         title: title,
	         buttons: {
	            'Ok': function(){
	               if(ok)
   	               ok();
   	            $(body).dialog('close');
	            },
	            'Cancel':function(){
	               if(cancel)
   	               cancel();
	               $(body).dialog('close');
	            }
	         }
	      });
	      $(body).dialog('open');
	   };
	   this.alert = function(title, message, onOk){
	      var body = $('<div class="notification-message">' + message + '</div>')[0];
	      $(body).
	         dialog({
	            title: title || 'Alert',
	            buttons: {
	               'Ok': function(){
	                  if(onOk)
   	                  onOk();
	                  $(body).dialog('close');
	               }
	            }
	         }).dialog('open');
	   };
	};
	
	var __seq = 0;
	var _getId = function(pref){
	   return (pref||'gen')+'-'+__seq++;
	};
	
	
	
	var EventBase = function(config){
	   this.el = config.el || {};
	   
	   var self = this;
	   var events = {};
	   
	   this.on = function(eventName, callback, scope){
	      var ehs = events[eventName];
	      if(!ehs){
	         ehs = [];
	         events[eventName] = ehs;
	      }
	      
	     var hnd = {
	         callback: callback,
	         handler: function(){
	            var args = [];
	            args[0] = arguments[0];// the event itself
	            args[1] = self; // the field
	            try{
	               for(var i = 2; i < arguments.length; i++){
	                  args[i]=arguments[i];
	               }
	               callback.apply(scope || self, args);
	            }catch(err){
	               console.log('ERROR: ',err);
	               throw err;
	            }
	         }
	      };
	      
	      ehs.push(hnd);
	      
	      $(this.el).bind(eventName, hnd.handler);
	   };
	   
	   this.removeListener = function(eventName, callback){
	      var ehs = events[eventName];
	      if(!ehs)
	         return;
	      if(!callback){
	         $(this.el).unbind(eventName);
	         delete events[eventName];
	      }else{
	         for(var i = 0; i < ehs.length; i++){
	            if(ehs[i].callback == callback){
	               $(this.el).unbind(eventName, ehs[i].handler);
	               ehs.splice(i,1);
	               break;
	            }
	         }
	      }
	      
	   };
	   
	   this.trigger = function(){ // basically function(event, [arg1, arg2....])
	      var event  = arguments[0];
	      var args = [];
	      for(var i = 1; i < arguments.length; i++){
	         args[i] = arguments[i];
	      }
	      if(event && events[event]){
   	      $(this.el).trigger(event, args);
   	   }
	   };
	   
	   
	   
	}
	
	
	
	
	var DataField = function(config){
	   this.id = config.id;
	   this.validator = config.validator;
	   this.type = config.type;
	   this.name = config.name;
	   this.value = config.value;
	   
	   this.events = config.events || {};
	   
	   DataField.superclass.constructor.call(this, config);
	   
	   
	   
	};
	
	extend(DataField, EventBase);
	extend(DataField,{
	   init: function(wel){
	      this.el = $('.panel-input-field', wel)[0];
	      this.labelEl = $('.panel-field-label', wel)[0];
	      this.notifyEl = $('.panel-field-notification', wel)[0];
	      this.wrapper = wel;
	      
	      each(this.events, function(name, handler){
	         this.on(name, handler);
	      }, this);
	      
	   },
	   
	   setValue: function(value){
	      var oldValue = this.getValue();
	      if(this.type =='select' || this.type=='dropdown'){
	         if($.isArray(value)){
	            for(var i = 0; i < value.length; i++){
	               var label = value[i].label === undefined ? value[i] : value[i].label;
	               var v = value[i].value === undefined ? value[i] : value[i].value;
	               this.addValue(value, label);
	            }
	         }else if(typeof(value) == 'string'){
	            this.addValue(value);
	         }else{
	            each(value, function(k,v){
	               this.addvalue(k,v);
	            },this);
	         }
	      }else{
	         $(this.el).val(value);
	      }
	      
	      if(oldValue != value){
	         this.trigger('change', value, oldValue);
	      }
	      
	   },
	   
	   setChecked: function(chk){
	      this.el.checked=chk;
	   },
	   
	   getValue: function(){
	      return $(this.el).val();
	   },
	   
	   notify: function(message, clear){
	      if(clear){
	         $(this.notifEl).html('');
	      }else{
	         $(this.notifEl).html(message);
	      }
	   },
	   
	   validate: function(){
	      if(this.validator){
	         if(typeof(this.validator) == 'function'){
	            if(!this.validator(this.getValue())){
	               this.notify('Invalid value');
	               return false;
	            }
	         }else if(typeof(this.validator) == 'string'){
	            try{
	               var reg = new RegExp(this.validtor);
	               if(!reg.test(this.getValue())){
	                  this.notify('Invalid value');
	                  return false;
	               }
	            }catch(e){
	               console.log(e);
	            }
	         }
	      }
	      this.notify('', true);
   	   return true;
	   },
	   getLabel: function(){
	      if(this.labelEl)
	         return $(this.labelEl).html();
	      return undefined;
	   },
	   
	   setLabel: function(lbl){
	      if(this.labelEl)
	         $(this.labelEl).html(lbl);
	   },
	   
	   show: function(){
	      $(this.wrapper).show();
	   },
	   
	   hide: function(){
	      $(this.wrapper).hide();
	   }
	});
	
	
	
	
	
	var ArrayValuesDataField = function(config){
	   ArrayValuesDataField.superclass.constructor.call(this, config);
	};
	extend(ArrayValuesDataField, DataField);
	extend(ArrayValuesDataField, {
	   init: function(wel){
	      this.values = {};
	      ArrayValuesDataField.superclass.init.call(this, wel);
	      this.on('change', this.onValuesChange, this);
	   },
	   getValue: function(){
	      var vls = [];
	      each(this.values, function(k, v){
	         if(v !== undefined)
   	         vls.push(k);
	      }, this);
	      return vls;
	   },
	   setValue: function(val){
	      if($.isArray(val)){
   	      for(var  i = 0; i < val.length; i++){
   	         this.addValue(val[i], false);
   	      }
	      }else{
   	      this.addValue(val, false);
   	   }
	   },
	   addValue: function(val, notify){
	      notify = notify === undefined ? true : notify;
	      if(this.values[val] === undefined){
	         this.values[val] = val;
	         this.trigger('value-add', val);
	         this.trigger('change', this.values);
	      }
	   },
	   removeValue: function(val){
         if(this.values[val] !== undefined){
            delete this.values[val];
            this.trigger('value-remove', val);
            this.trigger('change', this.values);
         }
	   },
	   onValuesChange: function(){
	      var self = this;
	      $(this.el).html('');
	      var m = [];
	      each(this.values, function(k,v){
	         m.push([
               '<span class="panel-input-array-element ui-corner-all">',
                  '<span class="panel-input-array-value">',
                  k,
                  '</span>',
                  '<span class="panel-input-array-value-remove ui-icon ui-icon-close" style="display: inline-block;"></span>',
               '</span>'
            ].join(''));
	      }, this);
	      if(!m.length)
	         m.push('none');
	      $(this.el).html(m.join(''));
	      $('.panel-input-array-value-remove', this.el).click(function(){
	         var val = $('.panel-input-array-value',$(this).parent()).text();
	         self.removeValue(val);
	      });
	   }
	});
	
	
	var SelectDataField = function(config){
	   SelectDataField.superclass.constructor.call(this, config);
	};
	
	extend(SelectDataField, DataField);
	extend(SelectDataField, {
	   init: function(wel){
	      SelectDataField.superclass.init.call(this, wel);
	      if(this.value !== undefined)
	         this.setValue(this.value);
	   },
	   addValue: function(value, label){
	      $(this.el).append([
	         '<option id="',_getId('option'),'" ',
	         'value="', value, '" ',
	         '>', label || value,
	         '</option>'].join(''));
	   },
	   removeValue: function(val){
	      $('option', this.el).each(function(){
	         if(this.value == val){
	            $(this).remove();
	         }
	      });
	   }
	});
	
	var BooleanDataField = function(config){
	   BooleanDataField.superclass.constructor.call(this, config);
	};
	extend(BooleanDataField, DataField);
	extend(BooleanDataField, {
	   init: function(wel){
	      BooleanDataField.superclass.init.call(this, wel);
	      if(this.value)
	         this.el.checked = "checked";
	   },
	   getValue: function(){
	      return this.el.checked;
	   },
	   setValue: function(v){
	      if(v)
	         this.el.checked = "checked";
	      else
	         $(this.el).removeAttr("checked");
	   }
	});
	
	
	
	var __DATA_FIELD_TYPES = {
	   'text': DataField,
	   'radio': BooleanDataField,
	   'checkbox': BooleanDataField,
	   'submit': DataField,
	   'button': DataField,
	   'password': DataField,
	   'select': SelectDataField,
	   'dropdown': SelectDataField,
	   'array-values': ArrayValuesDataField
	};
	
	
	
	
	
	
	
	
	
	var DataPanel = function(config){
	   var self = this;
	   var dataFieldsDefs = {};
	   
	   var dataFields = {};
	   
	   
	   
	   var getMarkupFor = function(dd, readonly){
	      var m = [];
         var self = this;
         
         for(var i = 0; i < dd.length; i++){
            var d = dd[i];
            var id = d.id || _getId(d.type);
            
            dataFieldsDefs[id] = {
               id: id,
               name: d.name,
               type: d.type,
               validator: d.validator,
               events: d.events,
               value: d.value
            };
            
            
            m.push('<div class="panel-field-wrapper" id="'+id+'">');
            if(d.label){
               m.push('<label class="panel-field-label">'+d.label+'</label>');
            }
            if(d.type == 'text' || 
                  d.type=='radio' || 
                  d.type=='checkbox' ||
                  d.type=="submit" ||
                  d.type=="button" || 
                  d.type=="password"){
               m.push([
                  '<input ',
                  'type="',d.type,'" ',
                  //'id="', id ,'" ',
                  'value="', d.value || '', '" ',
                  'class="panel-input-field ', d.extraClass || '', '" ',
                  'title="', d.title || '', '" ',
                  'name="', d.name || '', '" ',
                  (d.checked != undefined ? (d.checked ? 'checked="checked"':'') : ''),
                  '/>'
               ].join(''));
            }else if(d.type=='select' || d.type=='dropdown'){
               // 
               d.options = d.options || [];
               var sm = ['<select ',
                     //'id="', id, '" ',
                     'class="panel-input-field panel-input-dropdown ', d.extraClass || '', '" ',
                     'title="', d.title || '' ,'" ',
                     'name="', d.name || '','" ',
                     '>'];
               for(var j = 0; j < d.options.length; j++){
                  var opt = d.options[j];
                  var optid = id+'_'+j;
                  sm.push([
                  '<option id="', optid,'" ',
                  'value="', opt.value || '', '" ',
                  'class="panel-dropdow-option ', opt.extraClass || '', '" ',
                  '>', opt.label || '',
                  '</option>'
                  ].join(''));
               }
               sm.push('</select>');
               m.push(sm.join(''));
            }else if(d.type=='section'){
               // handle subsection here
               m.push([
                  '<div class="panel-subsection ', d.extraClass || '', '" >',
                     '<div class="panel-subsection-title">', d.title || '', '</div>',
                     '<div class="panel-subsection-content">'
               ].join(''));
               m.push(getMarkupFor(d.content, readonly));
               m.push([
                     '</div>',
                  '</div>'
               ].join(''));
            }else if(d.type=='array-values'){
               m.push('<span class="panel-input-field">');
               if(d.values){
                  for(var j = 0; j < d.values.length; j++){
                     m.push([
                        '<span class="panel-input-array-element ui-corner-all">',
                           '<span class="panel-input-array-value">',
                           d.values[j],
                           '</span>',
                           '<span class="panel-input-array-value-remove ui-icon ui-icon-close"></span>',
                        '</span>'
                     ].join(''));
                  }
                  if(!d.values.length)
                     m.push('none');
               }else{
                  m.push('none');
               }
               m.push('</span>');
            }
            m.push('<span class="panel-field-notification"></span>');
            m.push('</div>');
         }
	      
	      return m.join('');
	   };
	   
	   var content = getMarkupFor(config.content || []);
	   if(!content){
	      content = '<div class="data-panel-empty">No content</div>';
	   }
	   
	   var markup = [
	   '<div class="data-panel-wrapper ', config.extraClass, '" style="display:none">',
	      '<div class="data-panel-header">', config.title || '', '</div>',
	      '<div class="data-panel-content">',
	      content,
	      '</div>',
	      '<div class="data-panel-footer">',
          '</div>',
	   '</div>'
	   ].join('');
	   
	   
	   
	   var buttons = [];
	   each(config.buttons || {}, function(name, def){
	      var b = {};
	      var id = _getId("button");
	      b.markup = [
	         '<input type="button" ',
	         'id="', id, '" ',
	         'name="', name, '" ',
	         'value="', def.text,'" ',
	         '/>' 
	      ].join('');
	      b.order = def.order || 0;
	      b.name = def.name;
	      b.handler = def.handler || function(){};
	      b.id = id;
	      buttons.push(b);
	   }, this);
	   
	   buttons.sort(function(a,b){
	      return a.order-b.order;
	   });
	   
	   
	   this.el = $(markup)[0];
	   
	   for(var  i = 0; i < buttons.length; i++){
	      $('.data-panel-footer',this.el).append(buttons[i].markup);
	      (function(button){
	         $('#'+button.id, self.el).click(function(e){
	            button.handler.call(self, e, self);
	         });
	      })(buttons[i]);
	   }
	   
	   DataPanel.superclass.constructor.call(this, {el:this.el}); // must call superclass constructor here...
	   
	   
	   this.show = function(){
	      $(this.el).show("slide", {direction:"up"},800);
	   }
	   
	   this.hide = function(){
	      $(this.el).hide("slide", {direction:"up"}, 800);
	   };
	   
	   
	   this.getDataField = function(name){
	      var field = undefined;
	      each(dataFields, function(id, f){
	         if(f.name == name){
	            field = f;
	            return false;
	         }
	      });
	      return field;
	   };
	   
	   
	   this.validate = function(){
	      var valid = true;
	      each(dataFields, function(id, df){
	         valid = valid && df.validate();
	      }, this);
	      return valid;
	   };
	   
	   this.getData = function(validate){
	      var data = {};
	      if(validate && !this.validate())
	         return undefined;
	      each(dataFields, function(id, df){
	         data[df.name] = df.getValue();
	      }, this);
	      
	      return data;
	   };
	   
	   this.setData = function(data){
	      each(data, function(name, value){
	         var df = this.getDataField(name);
	         if(df){
	            df.setValue(value);
	         }
	      }, this);
	   };
	   
	   this.close = function(){
	      this.hide();
	      $(self.el).remove()
	   };
	   
	   each(dataFieldsDefs, function(id, def){
	      var _Type = __DATA_FIELD_TYPES[def.type];
	      if(_Type){
   	      dataFields[id] = new _Type(def);
	         dataFields[id].init($('#'+id, this.el)[0]);
	      }
	   }, this);
	   
	   this.trigger('ready', this);
	   
	   $(config.selector || document.body).append(this.el);
	   
	   
	   
	};
	extend(DataPanel, EventBase);
	
	
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
	   this.n = new NotificationManager({
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
	      var uep = new DataPanel({
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
	   this.n = new NotificationManager({
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
	   each(ResourcesMaager.HTTP_METHODS, function(k,v){
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
                                 getArr(resources[i].roles, 'label'),
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
	      this.ready(function(){
	         this.clearMainPanel();
	         var ap = this.getResourcePanel({}, function(){}, function(){});
	      });
	   },
	   removeResource: function(){},
	   editResource: function(){},
	   clearMainPanel: function(){
	      $('.resources-panel-content', this.el).html('');
	   },
	   getResourcePanel: function(resource, onOk, onCancel){
	      var self = this;
	      var rp = new DataPanel({
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
	                     return false;
	                  }
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
	                     value: this.getRolesForResource(resource),
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
	                  }else{
	                     console.log("invalid");
	                  }
	                  var rc = panel.getData();
	                  console.log("RESOURCE: ", rc);
	                  if(resource.urlPattern){
	                     
	                  }else{
	                     
	                  }  
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
	      
	      for(var i = 0; i < resources.length; i++){
	         var r = rsc[resources[i].urlPattern];
	         if(!r){
               r = rsc[resources[i].urlPattern] = resources[i]; 
               r.httpMethods = {};
               rs.push(r);
	         }
            var hm = r.httpMethod.toLowerCase();
            r.httpMethods[hm] = ResourcesManager.HTTP_METHODS[hm];
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
	         roles.push(r.roles[i].value);
	      }
	      return rs;
	   },
	   availableRolesForResource: function(r){
	      r = r || {};
	      r.roles = r.roles | [];
	      var c = {};
	      for(var i = 0; i < r.roles.length; i++){
	         c[r.roles[i].value] = c[r.roles[i].label];
	      }
	      var rs = [];
	      for(var  i = 0; i < this.roles.length; i++){
	         if(!c[this.roles[i].label]){
	            rs.push({
	               value: this.roles[i].label,
	               label: this.roles[i].description
	            });
	         }
	      }
	      return rs;
	   }
	});
	
	window.admin = {
	   UserManager:UserManager,
	   ResourcesManager: ResourcesManager,
   	RESTClient: RESTClient
	};
	
})(jQuery);
