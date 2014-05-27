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
	   
	
	var RESTClient = function(baseUrl, errorHnd){
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
				success: function(data, status, xhr){
				   console.log('REST-DONE: ', method, ' ', url, data);
					if(success){
						success.call(self, data);
					}
				},
				error: function(xhr, txtStatus, err){
				   console.log('REST-ERR: ', method, ' ', url, data, ' :: ', txtStatus, err);
					self.error(txtStatus, err, xhr, error);
				}
			});
		};
		
		
		this.error = function(status, e, xhr, errCallback){
			if(errorHnd)
				errorHnd.call(self, status, e, xhr, errCallback);
			else{
				errCallback = errCallback || function(){alert("Error: " + status + " - " + e);}
				errCallback.call(this, status, e, xhr);
			}
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
	         $(this.notifyEl).html('');
	         $(this.el).removeClass('panel-field-state-notify');
	      }else{
	         $(this.notifyEl).html(message);
	         $(this.el).addClass('panel-field-state-notify');
	      }
	   },
	   message: function(msg){
		   $(this.notifyEl).html(msg);
	   },
	   validate: function(){
	      if(this.validator){
	         if(typeof(this.validator) == 'function'){
	            if(!this.validator(this.getValue())){
	               this.notify(this.validatorMessage || 'Invalid value');
	               return false;
	            }
	         }else if(typeof(this.validator) == 'string'){
	            try{
	               var reg = new RegExp(this.validtor);
	               if(!reg.test(this.getValue())){
	                  this.notify(this.validatorMessage || 'Invalid value');
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
	      if(this.value){
	         if($.isArray(this.value)){
	            each(this.value, function(i, val){
	               this.addValue(val);
	            },this);
	         }else if(typeof(this.value) == 'string' ||
	            typeof(this.value) == 'number'){
	            this.addValue(val);
	         }else{
	            each(this.value, function(k,v){
	               this.addValue(v);
	            },this);
	         }
	      }
	      
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
		  if(val === undefined  || val === null){
			  return;
		  }
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
		  if(value === undefined  || value === null)
			  return;
		  var contains = false;
		  $('option', this.el).each(function(){
			  var v = $(this).attr('value');
			  if(v == value){
				  contains = true;
				  return false;
			  }
		  });
		  if(!contains){
		      $(this.el).append([
		         '<option id="',_getId('option'),'" ',
		         'value="', value, '" ',
		         '>', label || value,
		         '</option>'].join(''));
		  }
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
	   'textarea': DataField,
	   'textbox': DataField,
	   'hidden': DataField,
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
               validatorMessage: d.validatorMessage,
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
                  d.type=="password" ||
                  d.type == 'hidden'){
               m.push([
                  '<input ',
                  'type="',d.type,'" ',
                  //'id="', id ,'" ',
                  'value="', d.value || '', '" ',
                  'class="panel-input-field field-type-',d.type,' ', d.extraClass || '', '" ',
                  'title="', d.title || '', '" ',
                  'name="', d.name || '', '" ',
                  (d.checked != undefined ? (d.checked ? 'checked="checked"':'') : ''),
                  (d.readonly != undefined ? (d.readonly ? 'readonly="readonly"':'') : ''),
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
            }else if(d.type == 'textarea' ||
            		 d.type == 'textbox'){
            	m.push([
            	    '<textarea ',
                    'class="panel-input-field panel-textarea', d.extraClass || '', '" ',
                    'title="', d.title || '', '" ',
                    'name="', d.name || '', '" ',
                    '>', (d.value || ''), '</textarea>'
            	].join(''));
            }else if(d.type == 'raw' || d.type == 'markup'){
            	m.push([
            	   '<div class="panel-input-field-raw">',
            	   	  d.markup || '',
            	   '</div>'
                ].join(''));
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
	
	
	var Pager = function(config){
		
		
		this.range = config.range || 7;
		
		Pager.superclass.constructor.call(this, config);
		this.reload(
				config.page || 0,
				config.pageSize || 0,
				config.total || 0
		);
		this.update(this.page);
	};
	
	extend(Pager, EventBase);
	extend(Pager, {
		init: function(){
			Pager.superclass.init.call(this);
			this.update(this.page);
		},
		update: function(page){
			page = page *1;
			if(page <= 0)
				page = 1;
			
			if(page > this.pageCount)
				page = this.pageCount;
			
			var m = ['<div class="pagintor-wrapper">'];
				m.push('<hr><div class="paginator-pages">')
					m.push('<span class="paginator-page-selectable" page="1">First</span>');
					m.push('<span class="paginator-page-selectable" page="'+(page-1 > 0 ? page-1: page)+'">&lt;</span>');
			var halfRange = Math.floor(this.range/2);
			var current = page - halfRange;
			
			if(current <= 0)
				current = 1;
			if(this.pageCount >= this.range){
				if(page > (this.pageCount-halfRange))
					current = this.pageCount-this.range+1;
			}
					m.push('<div class="paginator-page-numbers">');
					for(var i = 0; i < this.range; i++, current++){
						if(current > 0 && current <= this.pageCount ){
							m.push([
							   '<span class="', (current == page ? 'paginator-page-unselectable paginator-page-current' : 'paginator-page-selectable') ,'" ',
							   'page="', current, '" ', '>',
							   current,
							   '</span>'
							].join(''));
						}
					}
					m.push('</div>');
					m.push('<span class="paginator-page-selectable" page="'+( (page + 1) <= this.pageCount ? (page+1) : this.pageCount)+'">&gt;</span>');
					m.push('<span class="paginator-page-selectable" page="'+(this.pageCount > 0 ? this.pageCount : 1)+'">Last</span>');
				m.push('</div>')
				m.push([
				    '<hr><div class="paginator-info">',
				    	'Page ', page, ' of ', this.pageCount,
				    '</div>'
				].join(''));
			m.push('</div>');
			
			var el = $(m.join(''))[0];
			var self = this;
			$('.paginator-page-selectable', el).click(function(){
				var p = $(this).attr('page');
				self.trigger('goto-page', p);
				self.update(parseInt(p));
			});
			$(this.el).html('').append(el);
		},
		reload: function(page, pageSize, total){
			this.page = page < 1 ? 1 : page;
			
			if(pageSize < 0)
				throw new Error("Invalid page size (less than 0)");
			
			if(total < 0)
				throw new Error("Invalid total items count (less than 0)");
			
			this.pageCount = pageSize > 0 ? Math.ceil(total/pageSize) : 1;
			if(this.page > this.pageCount)
				this.page = this.pageCount;
			
			this.start = (this.page-1) * pageSize;
			this.pageSize = pageSize;
			this.total = total;
		}
	});
	
	window.components = {
			util: {
				extend: extend,
				each: each
			},
			ui:{
				NotificationManager: NotificationManager,
				EventBase: EventBase,
				DataField: DataField,
				ArrayValuesDataField: ArrayValuesDataField,
				SelectDataField: SelectDataField,
				BooleanDataField: BooleanDataField,
				DataPanel: DataPanel,
				Pager: Pager
			},
			data: {
				RESTClient: RESTClient
			}
	};
	
})(jQuery);
