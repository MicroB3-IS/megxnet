(function(){var c="2.0.4-javascript",a={},d,f=[],e=[],b=Object.prototype.hasOwnProperty;
a={onError:function(g){},onClose:function(g){},onOpen:function(g){},onReopen:function(g){},onMessage:function(g){},onReconnect:function(h,g){},onMessagePublished:function(g){},onTransportFailure:function(h,g){},onLocalMessage:function(g){},onFailureToReconnect:function(h,g){},onClientTimeout:function(g){},AtmosphereRequest:function(L){var N={timeout:300000,method:"GET",headers:{},contentType:"",callback:null,url:"",data:"",suspend:true,maxRequest:-1,reconnect:true,maxStreamingLength:10000000,lastIndex:0,logLevel:"info",requestCount:0,fallbackMethod:"GET",fallbackTransport:"streaming",transport:"long-polling",webSocketImpl:null,webSocketBinaryType:null,dispatchUrl:null,webSocketPathDelimiter:"@@",enableXDR:false,rewriteURL:false,attachHeadersAsQueryString:true,executeCallbackBeforeReconnect:false,readyState:0,lastTimestamp:0,withCredentials:false,trackMessageLength:false,messageDelimiter:"|",connectTimeout:-1,reconnectInterval:0,dropAtmosphereHeaders:true,uuid:0,async:true,shared:false,readResponsesHeaders:false,maxReconnectOnClose:5,enableProtocol:true,onError:function(az){},onClose:function(az){},onOpen:function(az){},onMessage:function(az){},onReopen:function(aA,az){},onReconnect:function(aA,az){},onMessagePublished:function(az){},onTransportFailure:function(aA,az){},onLocalMessage:function(az){},onFailureToReconnect:function(aA,az){},onClientTimeout:function(az){}};
var V={status:200,reasonPhrase:"OK",responseBody:"",messages:[],headers:[],state:"messageReceived",transport:"polling",error:null,request:null,partialMessage:"",errorHandled:false,id:0};
var Y=null;
var n=null;
var u=null;
var D=null;
var F=null;
var aj=true;
var k=0;
var av=false;
var Z=null;
var ap;
var p=null;
var I=a.util.now();
var J;
var ay;
ax(L);
function aq(){aj=true;
av=false;
k=0;
Y=null;
n=null;
u=null;
D=null
}function z(){al();
aq()
}function K(aA,az){if(V.partialMessage===""&&(az.transport==="streaming")&&(aA.responseText.length>az.maxStreamingLength)){V.messages=[];
ah(true);
C();
al();
Q(aA,az,0)
}}function C(){if(N.enableProtocol&&!N.firstMessage){var aA="X-Atmosphere-Transport=close&X-Atmosphere-tracking-id="+N.uuid;
a.util.each(N.headers,function(aB,aD){var aC=a.util.isFunction(aD)?aD.call(this,N,N,V):aD;
if(aC!=null){aA+="&"+encodeURIComponent(aB)+"="+encodeURIComponent(aC)
}});
var az=N.url.replace(/([?&])_=[^&]*/,aA);
az=az+(az===N.url?(/\?/.test(N.url)?"&":"?")+aA:"");
N.attachHeadersAsQueryString=false;
N.dropAtmosphereHeaders=true;
N.url=az;
N.transport="polling";
m("",N)
}}function am(){N.reconnect=false;
av=true;
V.request=N;
V.state="unsubscribe";
V.responseBody="";
V.status=408;
V.partialMessage="";
B();
C();
al()
}function al(){V.partialMessage="";
if(N.id){clearTimeout(N.id)
}if(D!=null){D.close();
D=null
}if(F!=null){F.abort();
F=null
}if(u!=null){u.abort();
u=null
}if(Y!=null){if(Y.webSocketOpened){Y.close()
}Y=null
}if(n!=null){n.close();
n=null
}ar()
}function ar(){if(ap!=null){clearInterval(J);
document.cookie=ay+"=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
ap.signal("close",{reason:"",heir:!av?I:(ap.get("children")||[])[0]});
ap.close()
}if(p!=null){p.close()
}}function ax(az){z();
N=a.util.extend(N,az);
N.mrequest=N.reconnect;
if(!N.reconnect){N.reconnect=true
}}function o(){return N.webSocketImpl!=null||window.WebSocket||window.MozWebSocket
}function R(){return window.EventSource
}function s(){if(N.shared){p=ag(N);
if(p!=null){if(N.logLevel==="debug"){a.util.debug("Storage service available. All communication will be local")
}if(p.open(N)){return
}}if(N.logLevel==="debug"){a.util.debug("No Storage service available.")
}p=null
}N.firstMessage=true;
N.isOpen=false;
N.ctime=a.util.now();
if(N.transport!=="websocket"&&N.transport!=="sse"){r(N)
}else{if(N.transport==="websocket"){if(!o()){P("Websocket is not supported, using request.fallbackTransport ("+N.fallbackTransport+")")
}else{ai(false)
}}else{if(N.transport==="sse"){if(!R()){P("Server Side Events(SSE) is not supported, using request.fallbackTransport ("+N.fallbackTransport+")")
}else{H(false)
}}}}}function ag(aD){var aE,aC,aH,az="atmosphere-"+aD.url,aA={storage:function(){function aI(aM){if(aM.key===az&&aM.newValue){aB(aM.newValue)
}}if(!a.util.storage){return
}var aL=window.localStorage,aJ=function(aM){return a.util.parseJSON(aL.getItem(az+"-"+aM))
},aK=function(aM,aN){aL.setItem(az+"-"+aM,a.util.stringifyJSON(aN))
};
return{init:function(){aK("children",aJ("children").concat([I]));
a.util.on(window,"storage",aI);
return aJ("opened")
},signal:function(aM,aN){aL.setItem(az,a.util.stringifyJSON({target:"p",type:aM,data:aN}))
},close:function(){var aM=aJ("children");
a.util.off(window,"storage",aI);
if(aM){if(aF(aM,aD.id)){aK("children",aM)
}}}}
},windowref:function(){var aI=window.open("",az.replace(/\W/g,""));
if(!aI||aI.closed||!aI.callbacks){return
}return{init:function(){aI.callbacks.push(aB);
aI.children.push(I);
return aI.opened
},signal:function(aJ,aK){if(!aI.closed&&aI.fire){aI.fire(a.util.stringifyJSON({target:"p",type:aJ,data:aK}))
}},close:function(){if(!aH){aF(aI.callbacks,aB);
aF(aI.children,I)
}}}
}};
function aF(aL,aK){var aI,aJ=aL.length;
for(aI=0;
aI<aJ;
aI++){if(aL[aI]===aK){aL.splice(aI,1)
}}return aJ!==aL.length
}function aB(aI){var aK=a.util.parseJSON(aI),aJ=aK.data;
if(aK.target==="c"){switch(aK.type){case"open":M("opening","local",N);
break;
case"close":if(!aH){aH=true;
if(aJ.reason==="aborted"){am()
}else{if(aJ.heir===I){s()
}else{setTimeout(function(){s()
},100)
}}}break;
case"message":E(aJ,"messageReceived",200,aD.transport);
break;
case"localMessage":ab(aJ);
break
}}}function aG(){var aI=new RegExp("(?:^|; )("+encodeURIComponent(az)+")=([^;]*)").exec(document.cookie);
if(aI){return a.util.parseJSON(decodeURIComponent(aI[2]))
}}aE=aG();
if(!aE||a.util.now()-aE.ts>1000){return
}aC=aA.storage()||aA.windowref();
if(!aC){return
}return{open:function(){var aI;
J=setInterval(function(){var aJ=aE;
aE=aG();
if(!aE||aJ.ts===aE.ts){aB(a.util.stringifyJSON({target:"c",type:"close",data:{reason:"error",heir:aJ.heir}}))
}},1000);
aI=aC.init();
if(aI){setTimeout(function(){M("opening","local",aD)
},50)
}return aI
},send:function(aI){aC.signal("send",aI)
},localSend:function(aI){aC.signal("localSend",a.util.stringifyJSON({id:I,event:aI}))
},close:function(){if(!av){clearInterval(J);
aC.signal("close");
aC.close()
}}}
}function ac(){var aA,az="atmosphere-"+N.url,aE={storage:function(){function aF(aH){if(aH.key===az&&aH.newValue){aB(aH.newValue)
}}if(!a.util.storage){return
}var aG=window.localStorage;
return{init:function(){a.util.on(window,"storage",aF)
},signal:function(aH,aI){aG.setItem(az,a.util.stringifyJSON({target:"c",type:aH,data:aI}))
},get:function(aH){return a.util.parseJSON(aG.getItem(az+"-"+aH))
},set:function(aH,aI){aG.setItem(az+"-"+aH,a.util.stringifyJSON(aI))
},close:function(){a.util.off(window,"storage",aF);
aG.removeItem(az);
aG.removeItem(az+"-opened");
aG.removeItem(az+"-children")
}}
},windowref:function(){var aG=az.replace(/\W/g,""),aF=document.getElementById(aG),aH;
if(!aF){aF=document.createElement("div");
aF.id=aG;
aF.style.display="none";
aF.innerHTML='<iframe name="'+aG+'" />';
document.body.appendChild(aF)
}aH=aF.firstChild.contentWindow;
return{init:function(){aH.callbacks=[aB];
aH.fire=function(aI){var aJ;
for(aJ=0;
aJ<aH.callbacks.length;
aJ++){aH.callbacks[aJ](aI)
}}
},signal:function(aI,aJ){if(!aH.closed&&aH.fire){aH.fire(a.util.stringifyJSON({target:"c",type:aI,data:aJ}))
}},get:function(aI){return !aH.closed?aH[aI]:null
},set:function(aI,aJ){if(!aH.closed){aH[aI]=aJ
}},close:function(){}}
}};
function aB(aF){var aH=a.util.parseJSON(aF),aG=aH.data;
if(aH.target==="p"){switch(aH.type){case"send":ak(aG);
break;
case"localSend":ab(aG);
break;
case"close":am();
break
}}}Z=function aD(aF){aA.signal("message",aF)
};
function aC(){document.cookie=ay+"="+encodeURIComponent(a.util.stringifyJSON({ts:a.util.now()+1,heir:(aA.get("children")||[])[0]}))
}aA=aE.storage()||aE.windowref();
aA.init();
if(N.logLevel==="debug"){a.util.debug("Installed StorageService "+aA)
}aA.set("children",[]);
if(aA.get("opened")!=null&&!aA.get("opened")){aA.set("opened",false)
}ay=encodeURIComponent(az);
aC();
J=setInterval(aC,1000);
ap=aA
}function M(aB,aE,aA){if(N.shared&&aE!=="local"){ac()
}if(ap!=null){ap.set("opened",true)
}aA.close=function(){am()
};
if(k>0&&aB==="re-connecting"){aA.isReopen=true;
ad(V)
}else{if(V.error==null){V.request=aA;
var aC=V.state;
V.state=aB;
var az=V.transport;
V.transport=aE;
var aD=V.responseBody;
B();
V.responseBody=aD;
V.state=aC;
V.transport=az
}}}function y(aB){aB.transport="jsonp";
var aA=N,az;
if((aB!=null)&&(typeof(aB)!=="undefined")){aA=aB
}F={open:function(){var aD="atmosphere"+(++I);
function aC(){var aE=aA.url;
if(aA.dispatchUrl!=null){aE+=aA.dispatchUrl
}var aG=aA.data;
if(aA.attachHeadersAsQueryString){aE=W(aA);
if(aG!==""){aE+="&X-Atmosphere-Post-Body="+encodeURIComponent(aG)
}aG=""
}var aF=document.head||document.getElementsByTagName("head")[0]||document.documentElement;
az=document.createElement("script");
az.src=aE+"&jsonpTransport="+aD;
az.clean=function(){az.clean=az.onerror=az.onload=az.onreadystatechange=null;
if(az.parentNode){az.parentNode.removeChild(az)
}};
az.onload=az.onreadystatechange=function(){if(!az.readyState||/loaded|complete/.test(az.readyState)){az.clean()
}};
az.onerror=function(){az.clean();
ae(0,"maxReconnectOnClose reached")
};
aF.insertBefore(az,aF.firstChild)
}window[aD]=function(aG){if(aA.reconnect){if(aA.maxRequest===-1||aA.requestCount++<aA.maxRequest){if(!aA.executeCallbackBeforeReconnect){Q(F,aA,0)
}if(aG!=null&&typeof aG!=="string"){try{aG=aG.message
}catch(aF){}}var aE=w(aG,aA,V);
if(!aE){E(V.responseBody,"messageReceived",200,aA.transport)
}if(aA.executeCallbackBeforeReconnect){Q(F,aA,0)
}}else{a.util.log(N.logLevel,["JSONP reconnect maximum try reached "+N.requestCount]);
ae(0,"maxRequest reached")
}}};
setTimeout(function(){aC()
},50)
},abort:function(){if(az.clean){az.clean()
}}};
F.open()
}function i(az){if(N.webSocketImpl!=null){return N.webSocketImpl
}else{if(window.WebSocket){return new WebSocket(az)
}else{return new MozWebSocket(az)
}}}function j(){return a.util.getAbsoluteURL(W(N)).replace(/^http/,"ws")
}function aw(){var az=W(N);
return az
}function H(aA){V.transport="sse";
var az=aw(N.url);
if(N.logLevel==="debug"){a.util.debug("Invoking executeSSE");
a.util.debug("Using URL: "+az)
}if(N.enableProtocol&&aA){var aC=a.util.now()-N.ctime;
N.lastTimestamp=Number(N.stime)+Number(aC)
}if(aA&&!N.reconnect){if(n!=null){al()
}return
}try{n=new EventSource(az,{withCredentials:N.withCredentials})
}catch(aB){ae(0,aB);
P("SSE failed. Downgrading to fallback transport and resending");
return
}if(N.connectTimeout>0){N.id=setTimeout(function(){if(!aA){al()
}},N.connectTimeout)
}n.onopen=function(aD){x(N);
if(N.logLevel==="debug"){a.util.debug("SSE successfully opened")
}if(!N.enableProtocol){if(!aA){M("opening","sse",N)
}else{M("re-opening","sse",N)
}}aA=true;
if(N.method==="POST"){V.state="messageReceived";
n.send(N.data)
}};
n.onmessage=function(aE){x(N);
if(!N.enableXDR&&aE.origin&&aE.origin!==window.location.protocol+"//"+window.location.host){a.util.log(N.logLevel,["Origin was not "+window.location.protocol+"//"+window.location.host]);
return
}V.state="messageReceived";
V.status=200;
aE=aE.data;
var aD=w(aE,N,V);
if(n.URL){n.interval=100;
n.URL=aw(N.url)
}if(!aD){B();
V.responseBody="";
V.messages=[]
}};
n.onerror=function(aD){clearTimeout(N.id);
if(V.state==="closedByClient"){return
}ah(aA);
al();
if(av){a.util.log(N.logLevel,["SSE closed normally"])
}else{if(!aA){P("SSE failed. Downgrading to fallback transport and resending")
}else{if(N.reconnect&&(V.transport==="sse")){if(k++<N.maxReconnectOnClose){M("re-connecting",N.transport,N);
if(N.reconnectInterval>0){N.id=setTimeout(function(){H(true)
},N.reconnectInterval)
}else{H(true)
}V.responseBody="";
V.messages=[]
}else{a.util.log(N.logLevel,["SSE reconnect maximum try reached "+k]);
ae(0,"maxReconnectOnClose reached")
}}}}}
}function ai(aA){V.transport="websocket";
if(N.enableProtocol&&aA){var aB=a.util.now()-N.ctime;
N.lastTimestamp=Number(N.stime)+Number(aB)
}var az=j(N.url);
if(N.logLevel==="debug"){a.util.debug("Invoking executeWebSocket");
a.util.debug("Using URL: "+az)
}if(aA&&!N.reconnect){if(Y!=null){al()
}return
}Y=i(az);
if(N.webSocketBinaryType!=null){Y.binaryType=N.webSocketBinaryType
}if(N.connectTimeout>0){N.id=setTimeout(function(){if(!aA){var aC={code:1002,reason:"",wasClean:false};
Y.onclose(aC);
try{al()
}catch(aD){}return
}},N.connectTimeout)
}Y.onopen=function(aC){x(N);
if(N.logLevel==="debug"){a.util.debug("Websocket successfully opened")
}if(!N.enableProtocol){if(!aA){M("opening","websocket",N)
}else{M("re-opening","websocket",N)
}}aA=true;
if(Y!=null){Y.webSocketOpened=aA;
if(N.method==="POST"){V.state="messageReceived";
Y.send(N.data)
}}};
Y.onmessage=function(aE){x(N);
V.state="messageReceived";
V.status=200;
aE=aE.data;
var aC=typeof(aE)==="string";
if(aC){var aD=w(aE,N,V);
if(!aD){B();
V.responseBody="";
V.messages=[]
}}else{if(!t(N,aE)){return
}V.responseBody=aE;
B();
V.responseBody=null
}};
Y.onerror=function(aC){clearTimeout(N.id)
};
Y.onclose=function(aC){clearTimeout(N.id);
if(V.state==="closed"){return
}var aD=aC.reason;
if(aD===""){switch(aC.code){case 1000:aD="Normal closure; the connection successfully completed whatever purpose for which it was created.";
break;
case 1001:aD="The endpoint is going away, either because of a server failure or because the browser is navigating away from the page that opened the connection.";
break;
case 1002:aD="The endpoint is terminating the connection due to a protocol error.";
break;
case 1003:aD="The connection is being terminated because the endpoint received data of a type it cannot accept (for example, a text-only endpoint received binary data).";
break;
case 1004:aD="The endpoint is terminating the connection because a data frame was received that is too large.";
break;
case 1005:aD="Unknown: no status code was provided even though one was expected.";
break;
case 1006:aD="Connection was closed abnormally (that is, with no close frame being sent).";
break
}}if(N.logLevel==="warn"){a.util.warn("Websocket closed, reason: "+aD);
a.util.warn("Websocket closed, wasClean: "+aC.wasClean)
}if(V.state==="closedByClient"){return
}ah(aA);
V.state="closed";
if(av){a.util.log(N.logLevel,["Websocket closed normally"])
}else{if(!aA){P("Websocket failed. Downgrading to Comet and resending")
}else{if(N.reconnect&&V.transport==="websocket"){al();
if(k++<N.maxReconnectOnClose){M("re-connecting",N.transport,N);
if(N.reconnectInterval>0){N.id=setTimeout(function(){V.responseBody="";
V.messages=[];
ai(true)
},N.reconnectInterval)
}else{V.responseBody="";
V.messages=[];
ai(true)
}}else{a.util.log(N.logLevel,["Websocket reconnect maximum try reached "+N.requestCount]);
if(N.logLevel==="warn"){a.util.warn("Websocket error, reason: "+aC.reason)
}ae(0,"maxReconnectOnClose reached")
}}}}};
if(Y.url===undefined){Y.onclose({reason:"Android 4.1 does not support websockets.",wasClean:false})
}}function t(aC,aB){var az=true;
if(a.util.trim(aB).length!==0&&aC.enableProtocol&&aC.firstMessage){aC.firstMessage=false;
var aA=aB.split(aC.messageDelimiter);
var aD=aA.length===2?0:1;
aC.uuid=a.util.trim(aA[aD]);
aC.stime=a.util.trim(aA[aD+1]);
az=false;
if(aC.transport!=="long-polling"){an(aC)
}}else{if(aC.enableProtocol&&aC.firstMessage){az=false
}else{an(aC)
}}return az
}function x(az){clearTimeout(az.id);
if(az.timeout>0&&az.transport!=="polling"){az.id=setTimeout(function(){q(az);
C();
al()
},az.timeout)
}}function q(az){V.state="closedByClient";
V.responseBody="";
V.status=408;
V.messages=[];
B()
}function ae(az,aA){al();
clearTimeout(N.id);
V.state="error";
V.reasonPhrase=aA;
V.responseBody="";
V.status=az;
V.messages=[];
B()
}function w(aD,aC,az){if(!t(N,aD)){return true
}if(aD.length===0){return true
}if(aC.trackMessageLength){aD=az.partialMessage+aD;
var aB=[];
var aA=aD.indexOf(aC.messageDelimiter);
while(aA!==-1){var aF=a.util.trim(aD.substring(0,aA));
var aE=+aF;
if(isNaN(aE)){throw new Error('message length "'+aF+'" is not a number')
}aA+=aC.messageDelimiter.length;
if(aA+aE>aD.length){aA=-1
}else{aB.push(aD.substring(aA,aA+aE));
aD=aD.substring(aA+aE,aD.length);
aA=aD.indexOf(aC.messageDelimiter)
}}az.partialMessage=aD;
if(aB.length!==0){az.responseBody=aB.join(aC.messageDelimiter);
az.messages=aB;
return false
}else{az.responseBody="";
az.messages=[];
return true
}}else{az.responseBody=aD
}return false
}function P(az){a.util.log(N.logLevel,[az]);
if(typeof(N.onTransportFailure)!=="undefined"){N.onTransportFailure(az,N)
}else{if(typeof(a.util.onTransportFailure)!=="undefined"){a.util.onTransportFailure(az,N)
}}N.transport=N.fallbackTransport;
var aA=N.connectTimeout===-1?0:N.connectTimeout;
if(N.reconnect&&N.transport!=="none"||N.transport==null){N.method=N.fallbackMethod;
V.transport=N.fallbackTransport;
N.fallbackTransport="none";
if(aA>0){N.id=setTimeout(function(){s()
},aA)
}else{s()
}}else{ae(500,"Unable to reconnect with fallback transport")
}}function W(aB,az){var aA=N;
if((aB!=null)&&(typeof(aB)!=="undefined")){aA=aB
}if(az==null){az=aA.url
}if(!aA.attachHeadersAsQueryString){return az
}if(az.indexOf("X-Atmosphere-Framework")!==-1){return az
}az+=(az.indexOf("?")!==-1)?"&":"?";
az+="X-Atmosphere-tracking-id="+aA.uuid;
az+="&X-Atmosphere-Framework="+c;
az+="&X-Atmosphere-Transport="+aA.transport;
if(aA.trackMessageLength){az+="&X-Atmosphere-TrackMessageSize=true"
}if(aA.lastTimestamp!=null){az+="&X-Cache-Date="+aA.lastTimestamp
}else{az+="&X-Cache-Date="+0
}if(aA.contentType!==""){az+="&Content-Type="+aA.contentType
}if(aA.enableProtocol){az+="&X-atmo-protocol=true"
}a.util.each(aA.headers,function(aC,aE){var aD=a.util.isFunction(aE)?aE.call(this,aA,aB,V):aE;
if(aD!=null){az+="&"+encodeURIComponent(aC)+"="+encodeURIComponent(aD)
}});
return az
}function an(az){if(!az.isOpen){az.isOpen=true;
M("opening",az.transport,az)
}else{if(az.isReopen){az.isReopen=false;
M("re-opening",az.transport,az)
}}}function r(aB){var az=N;
if((aB!=null)||(typeof(aB)!=="undefined")){az=aB
}az.lastIndex=0;
az.readyState=0;
if((az.transport==="jsonp")||((az.enableXDR)&&(a.util.checkCORSSupport()))){y(az);
return
}if(a.util.browser.msie&&a.util.browser.version<10){if((az.transport==="streaming")){if(az.enableXDR&&window.XDomainRequest){O(az)
}else{au(az)
}return
}if((az.enableXDR)&&(window.XDomainRequest)){O(az);
return
}}var aC=function(){az.lastIndex=0;
if(az.reconnect&&k++<az.maxReconnectOnClose){M("re-connecting",aB.transport,aB);
Q(aA,az,aB.reconnectInterval)
}else{ae(0,"maxReconnectOnClose reached")
}};
if(az.force||(az.reconnect&&(az.maxRequest===-1||az.requestCount++<az.maxRequest))){az.force=false;
var aA=a.util.xhr();
aA.hasData=false;
g(aA,az,true);
if(az.suspend){u=aA
}if(az.transport!=="polling"){V.transport=az.transport;
aA.onabort=function(){ah(true)
};
aA.onerror=function(){V.error=true;
try{V.status=XMLHttpRequest.status
}catch(aE){V.status=500
}if(!V.status){V.status=500
}al();
if(!V.errorHandled){aC()
}}
}aA.onreadystatechange=function(){if(av){return
}V.error=null;
var aF=false;
var aK=false;
if(az.transport==="streaming"&&az.readyState>2&&aA.readyState===4){al();
aC();
return
}az.readyState=aA.readyState;
if(az.transport==="streaming"&&aA.readyState>=3){aK=true
}else{if(az.transport==="long-polling"&&aA.readyState===4){aK=true
}}x(N);
if(az.transport!=="polling"){if((!az.enableProtocol||!aB.firstMessage)&&aA.readyState===2){an(az)
}var aE=200;
if(aA.readyState>1){aE=aA.status>1000?0:aA.status
}if(aE>=300||aE===0){V.errorHandled=true;
al();
aC();
return
}}else{if(aA.readyState===4){aK=true
}}if(aK){var aI=aA.responseText;
if(a.util.trim(aI).length===0&&az.transport==="long-polling"){if(!aA.hasData){aC()
}else{aA.hasData=false
}return
}aA.hasData=true;
af(aA,N);
if(az.transport==="streaming"){if(!a.util.browser.opera){var aH=aI.substring(az.lastIndex,aI.length);
aF=w(aH,az,V);
az.lastIndex=aI.length;
if(aF){return
}}else{a.util.iterate(function(){if(V.status!==500&&aA.responseText.length>az.lastIndex){try{V.status=aA.status;
V.headers=a.util.parseHeaders(aA.getAllResponseHeaders());
af(aA,N)
}catch(aM){V.status=404
}x(N);
V.state="messageReceived";
var aL=aA.responseText.substring(az.lastIndex);
az.lastIndex=aA.responseText.length;
aF=w(aL,az,V);
if(!aF){B()
}K(aA,az)
}else{if(V.status>400){az.lastIndex=aA.responseText.length;
return false
}}},0)
}}else{aF=w(aI,az,V)
}try{V.status=aA.status;
V.headers=a.util.parseHeaders(aA.getAllResponseHeaders());
af(aA,az)
}catch(aJ){V.status=404
}if(az.suspend){V.state=V.status===0?"closed":"messageReceived"
}else{V.state="messagePublished"
}var aG=aB.transport!=="streaming"&&aB.transport!=="polling";
if(aG&&!az.executeCallbackBeforeReconnect){Q(aA,az,0)
}if(V.responseBody.length!==0&&!aF){B()
}if(aG&&az.executeCallbackBeforeReconnect){Q(aA,az,0)
}K(aA,az)
}};
try{aA.send(az.data);
aj=true
}catch(aD){a.util.log(az.logLevel,["Unable to connect to "+az.url])
}}else{if(az.logLevel==="debug"){a.util.log(az.logLevel,["Max re-connection reached."])
}ae(0,"maxRequest reached")
}}function g(aB,aC,aA){var az=aC.url;
if(aC.dispatchUrl!=null&&aC.method==="POST"){az+=aC.dispatchUrl
}az=W(aC,az);
az=a.util.prepareURL(az);
if(aA){aB.open(aC.method,az,aC.async);
if(aC.connectTimeout>0){aC.id=setTimeout(function(){if(aC.requestCount===0){al();
E("Connect timeout","closed",200,aC.transport)
}},aC.connectTimeout)
}}if(N.withCredentials){if("withCredentials" in aB){aB.withCredentials=true
}}if(!N.dropAtmosphereHeaders){aB.setRequestHeader("X-Atmosphere-Framework",a.util.version);
aB.setRequestHeader("X-Atmosphere-Transport",aC.transport);
if(aC.lastTimestamp!=null){aB.setRequestHeader("X-Cache-Date",aC.lastTimestamp)
}else{aB.setRequestHeader("X-Cache-Date",0)
}if(aC.trackMessageLength){aB.setRequestHeader("X-Atmosphere-TrackMessageSize","true")
}aB.setRequestHeader("X-Atmosphere-tracking-id",aC.uuid)
}if(aC.contentType!==""){aB.setRequestHeader("Content-Type",aC.contentType)
}a.util.each(aC.headers,function(aD,aF){var aE=a.util.isFunction(aF)?aF.call(this,aB,aC,aA,V):aF;
if(aE!=null){aB.setRequestHeader(aD,aE)
}})
}function Q(aA,aB,aC){if(aB.reconnect||(aB.suspend&&aj)){var az=0;
if(aA&&aA.readyState!==0){az=aA.status>1000?0:aA.status
}V.status=az===0?204:az;
V.reason=az===0?"Server resumed the connection or down.":"OK";
clearTimeout(aB.id);
if(aC>0){aB.id=setTimeout(function(){r(aB)
},aC)
}else{r(aB)
}}}function ad(az){az.state="re-connecting";
aa(az)
}function O(az){if(az.transport!=="polling"){D=U(az);
D.open()
}else{U(az).open()
}}function U(aB){var aA=N;
if((aB!=null)&&(typeof(aB)!=="undefined")){aA=aB
}var aG=aA.transport;
var aF=0;
var az=new window.XDomainRequest();
var aD=function(){if(aA.transport==="long-polling"&&(aA.reconnect&&(aA.maxRequest===-1||aA.requestCount++<aA.maxRequest))){az.status=200;
O(aA)
}};
var aE=aA.rewriteURL||function(aI){var aH=/(?:^|;\s*)(JSESSIONID|PHPSESSID)=([^;]*)/.exec(document.cookie);
switch(aH&&aH[1]){case"JSESSIONID":return aI.replace(/;jsessionid=[^\?]*|(\?)|$/,";jsessionid="+aH[2]+"$1");
case"PHPSESSID":return aI.replace(/\?PHPSESSID=[^&]*&?|\?|$/,"?PHPSESSID="+aH[2]+"&").replace(/&$/,"")
}return aI
};
az.onprogress=function(){aC(az)
};
az.onerror=function(){if(aA.transport!=="polling"){al();
if(k++<aA.maxReconnectOnClose){if(aA.reconnectInterval>0){aA.id=setTimeout(function(){M("re-connecting",aB.transport,aB);
O(aA)
},aA.reconnectInterval)
}else{M("re-connecting",aB.transport,aB);
O(aA)
}}else{ae(0,"maxReconnectOnClose reached")
}}};
az.onload=function(){};
var aC=function(aH){clearTimeout(aA.id);
var aJ=aH.responseText;
aJ=aJ.substring(aF);
aF+=aJ.length;
if(aG!=="polling"){x(aA);
var aI=w(aJ,aA,V);
if(aG==="long-polling"&&a.util.trim(aJ).length===0){return
}if(aA.executeCallbackBeforeReconnect){aD()
}if(!aI){E(V.responseBody,"messageReceived",200,aG)
}if(!aA.executeCallbackBeforeReconnect){aD()
}}};
return{open:function(){var aH=aA.url;
if(aA.dispatchUrl!=null){aH+=aA.dispatchUrl
}aH=W(aA,aH);
az.open(aA.method,aE(aH));
if(aA.method==="GET"){az.send()
}else{az.send(aA.data)
}if(aA.connectTimeout>0){aA.id=setTimeout(function(){if(aA.requestCount===0){al();
E("Connect timeout","closed",200,aA.transport)
}},aA.connectTimeout)
}},close:function(){az.abort()
}}
}function au(az){D=v(az);
D.open()
}function v(aC){var aB=N;
if((aC!=null)&&(typeof(aC)!=="undefined")){aB=aC
}var aA;
var aD=new window.ActiveXObject("htmlfile");
aD.open();
aD.close();
var az=aB.url;
if(aB.dispatchUrl!=null){az+=aB.dispatchUrl
}if(aB.transport!=="polling"){V.transport=aB.transport
}return{open:function(){var aE=aD.createElement("iframe");
az=W(aB);
if(aB.data!==""){az+="&X-Atmosphere-Post-Body="+encodeURIComponent(aB.data)
}az=a.util.prepareURL(az);
aE.src=az;
aD.body.appendChild(aE);
var aF=aE.contentDocument||aE.contentWindow.document;
aA=a.util.iterate(function(){try{if(!aF.firstChild){return
}var aI=aF.body?aF.body.lastChild:aF;
var aK=function(){var aM=aI.cloneNode(true);
aM.appendChild(aF.createTextNode("."));
var aL=aM.innerText;
aL=aL.substring(0,aL.length-1);
return aL
};
if(!aF.body||!aF.body.firstChild||aF.body.firstChild.nodeName.toLowerCase()!=="pre"){var aH=aF.head||aF.getElementsByTagName("head")[0]||aF.documentElement||aF;
var aG=aF.createElement("script");
aG.text="document.write('<plaintext>')";
aH.insertBefore(aG,aH.firstChild);
aH.removeChild(aG);
aI=aF.body.lastChild
}if(aB.closed){aB.isReopen=true
}aA=a.util.iterate(function(){var aM=aK();
if(aM.length>aB.lastIndex){x(N);
V.status=200;
V.error=null;
aI.innerText="";
var aL=w(aM,aB,V);
if(aL){return""
}E(V.responseBody,"messageReceived",200,aB.transport)
}aB.lastIndex=0;
if(aF.readyState==="complete"){ah(true);
M("re-connecting",aB.transport,aB);
if(aB.reconnectInterval>0){aB.id=setTimeout(function(){au(aB)
},aB.reconnectInterval)
}else{au(aB)
}return false
}},null);
return false
}catch(aJ){V.error=true;
M("re-connecting",aB.transport,aB);
if(k++<aB.maxReconnectOnClose){if(aB.reconnectInterval>0){aB.id=setTimeout(function(){au(aB)
},aB.reconnectInterval)
}else{au(aB)
}}else{ae(0,"maxReconnectOnClose reached")
}aD.execCommand("Stop");
aD.close();
return false
}})
},close:function(){if(aA){aA()
}aD.execCommand("Stop");
ah(true)
}}
}function ak(az){if(p!=null){l(az)
}else{if(u!=null||n!=null){h(az)
}else{if(D!=null){X(az)
}else{if(F!=null){T(az)
}else{if(Y!=null){G(az)
}else{ae(0,"No suspended connection available");
a.util.error("No suspended connection available. Make sure atmosphere.subscribe has been called and request.onOpen invoked before invoking this method")
}}}}}}function m(aA,az){if(!az){az=ao(aA)
}az.transport="polling";
az.method="GET";
az.async=false;
az.withCredentials=false;
az.reconnect=false;
az.force=true;
az.suspend=false;
r(az)
}function l(az){p.send(az)
}function A(aA){if(aA.length===0){return
}try{if(p){p.localSend(aA)
}else{if(ap){ap.signal("localMessage",a.util.stringifyJSON({id:I,event:aA}))
}}}catch(az){a.util.error(az)
}}function h(aA){var az=ao(aA);
r(az)
}function X(aA){if(N.enableXDR&&a.util.checkCORSSupport()){var az=ao(aA);
az.reconnect=false;
y(az)
}else{h(aA)
}}function T(az){h(az)
}function S(az){var aA=az;
if(typeof(aA)==="object"){aA=az.data
}return aA
}function ao(aA){var aB=S(aA);
var az={connected:false,timeout:60000,method:"POST",url:N.url,contentType:N.contentType,headers:N.headers,reconnect:true,callback:null,data:aB,suspend:false,maxRequest:-1,logLevel:"info",requestCount:0,withCredentials:N.withCredentials,async:N.async,transport:"polling",isOpen:true,attachHeadersAsQueryString:true,enableXDR:N.enableXDR,uuid:N.uuid,dispatchUrl:N.dispatchUrl,enableProtocol:false,messageDelimiter:"|",maxReconnectOnClose:N.maxReconnectOnClose};
if(typeof(aA)==="object"){az=a.util.extend(az,aA)
}return az
}function G(az){var aC=S(az);
var aA;
try{if(N.dispatchUrl!=null){aA=N.webSocketPathDelimiter+N.dispatchUrl+N.webSocketPathDelimiter+aC
}else{aA=aC
}if(!Y.webSocketOpened){a.util.error("WebSocket not connected.");
return
}Y.send(aA)
}catch(aB){Y.onclose=function(aD){};
al();
P("Websocket failed. Downgrading to Comet and resending "+az);
h(az)
}}function ab(aA){var az=a.util.parseJSON(aA);
if(az.id!==I){if(typeof(N.onLocalMessage)!=="undefined"){N.onLocalMessage(az.event)
}else{if(typeof(a.util.onLocalMessage)!=="undefined"){a.util.onLocalMessage(az.event)
}}}}function E(aC,az,aA,aB){V.responseBody=aC;
V.transport=aB;
V.status=aA;
V.state=az;
B()
}function af(az,aC){if(!aC.readResponsesHeaders&&!aC.enableProtocol){aC.lastTimestamp=a.util.now();
aC.uuid=I;
return
}try{var aB=az.getResponseHeader("X-Cache-Date");
if(aB&&aB!=null&&aB.length>0){aC.lastTimestamp=aB.split(" ").pop()
}var aA=az.getResponseHeader("X-Atmosphere-tracking-id");
if(aA&&aA!=null){aC.uuid=aA.split(" ").pop()
}if(aC.headers){a.util.each(N.headers,function(aF){var aE=az.getResponseHeader(aF);
if(aE){V.headers[aF]=aE
}})
}}catch(aD){}}function aa(az){at(az,N);
at(az,a.util)
}function at(aA,aB){switch(aA.state){case"messageReceived":k=0;
if(typeof(aB.onMessage)!=="undefined"){aB.onMessage(aA)
}break;
case"error":if(typeof(aB.onError)!=="undefined"){aB.onError(aA)
}break;
case"opening":if(typeof(aB.onOpen)!=="undefined"){aB.onOpen(aA)
}break;
case"messagePublished":if(typeof(aB.onMessagePublished)!=="undefined"){aB.onMessagePublished(aA)
}break;
case"re-connecting":if(typeof(aB.onReconnect)!=="undefined"){aB.onReconnect(N,aA)
}break;
case"closedByClient":if(typeof(aB.onClientTimeout)!=="undefined"){aB.onClientTimeout(N)
}break;
case"re-opening":if(typeof(aB.onReopen)!=="undefined"){aB.onReopen(N,aA)
}break;
case"fail-to-reconnect":if(typeof(aB.onFailureToReconnect)!=="undefined"){aB.onFailureToReconnect(N,aA)
}break;
case"unsubscribe":case"closed":var az=typeof(N.closed)!=="undefined"?N.closed:false;
if(typeof(aB.onClose)!=="undefined"&&!az){aB.onClose(aA)
}N.closed=true;
break
}}function ah(az){if(V.state!=="closed"){V.state="closed";
V.responseBody="";
V.messages=[];
V.status=!az?501:200;
B()
}}function B(){var aB=function(aE,aF){aF(V)
};
if(p==null&&Z!=null){Z(V.responseBody)
}N.reconnect=N.mrequest;
var az=typeof(V.responseBody)==="string";
var aC=(az&&N.trackMessageLength)?(V.messages.length>0?V.messages:[""]):new Array(V.responseBody);
for(var aA=0;
aA<aC.length;
aA++){if(aC.length>1&&aC[aA].length===0){continue
}V.responseBody=(az)?a.util.trim(aC[aA]):aC[aA];
if(p==null&&Z!=null){Z(V.responseBody)
}if(V.responseBody.length===0&&V.state==="messageReceived"){continue
}aa(V);
if(e.length>0){if(N.logLevel==="debug"){a.util.debug("Invoking "+e.length+" global callbacks: "+V.state)
}try{a.util.each(e,aB)
}catch(aD){a.util.log(N.logLevel,["Callback exception"+aD])
}}if(typeof(N.callback)==="function"){if(N.logLevel==="debug"){a.util.debug("Invoking request callbacks")
}try{N.callback(V)
}catch(aD){a.util.log(N.logLevel,["Callback exception"+aD])
}}}}this.subscribe=function(az){ax(az);
s()
};
this.execute=function(){s()
};
this.close=function(){am()
};
this.disconnect=function(){C()
};
this.getUrl=function(){return N.url
};
this.push=function(aB,aA){if(aA!=null){var az=N.dispatchUrl;
N.dispatchUrl=aA;
ak(aB);
N.dispatchUrl=az
}else{ak(aB)
}};
this.getUUID=function(){return N.uuid
};
this.pushLocal=function(az){A(az)
};
this.enableProtocol=function(az){return N.enableProtocol
};
this.request=N;
this.response=V
}};
a.subscribe=function(g,j,i){if(typeof(j)==="function"){a.addCallback(j)
}if(typeof(g)!=="string"){i=g
}else{i.url=g
}var h=new a.AtmosphereRequest(i);
h.execute();
f[f.length]=h;
return h
};
a.unsubscribe=function(){if(f.length>0){var g=[].concat(f);
for(var j=0;
j<g.length;
j++){var h=g[j];
h.close();
clearTimeout(h.response.request.id)
}}f=[];
e=[]
};
a.unsubscribeUrl=function(h){var g=-1;
if(f.length>0){for(var k=0;
k<f.length;
k++){var j=f[k];
if(j.getUrl()===h){j.close();
clearTimeout(j.response.request.id);
g=k;
break
}}}if(g>=0){f.splice(g,1)
}};
a.addCallback=function(g){if(a.util.inArray(g,e)===-1){e.push(g)
}};
a.removeCallback=function(h){var g=a.util.inArray(h,e);
if(g!==-1){e.splice(g,1)
}};
a.util={browser:{},parseHeaders:function(h){var g,j=/^(.*?):[ \t]*([^\r\n]*)\r?$/mg,i={};
while(g=j.exec(h)){i[g[1]]=g[2]
}return i
},now:function(){return new Date().getTime()
},isArray:function(g){return Object.prototype.toString.call(g)==="[object Array]"
},inArray:function(j,k){if(!Array.prototype.indexOf){var g=k.length;
for(var h=0;
h<g;
++h){if(k[h]===j){return h
}}return -1
}return k.indexOf(j)
},isBinary:function(h){var g=Object.prototype.toString.call(h);
return g==="[object Blob]"||g==="[object ArrayBuffer]"
},isFunction:function(g){return Object.prototype.toString.call(g)==="[object Function]"
},getAbsoluteURL:function(g){var h=document.createElement("div");
h.innerHTML='<a href="'+g+'"/>';
return encodeURI(decodeURI(h.firstChild.href))
},prepareURL:function(h){var i=a.util.now();
var g=h.replace(/([?&])_=[^&]*/,"$1_="+i);
return g+(g===h?(/\?/.test(h)?"&":"?")+"_="+i:"")
},trim:function(g){if(!String.prototype.trim){return g.toString().replace(/(?:(?:^|\n)\s+|\s+(?:$|\n))/g,"").replace(/\s+/g," ")
}else{return g.toString().trim()
}},param:function(k){var i,g=[];
function j(l,m){m=a.util.isFunction(m)?m():(m==null?"":m);
g.push(encodeURIComponent(l)+"="+encodeURIComponent(m))
}function h(m,n){var l;
if(a.util.isArray(n)){a.util.each(n,function(p,o){if(/\[\]$/.test(m)){j(m,o)
}else{h(m+"["+(typeof o==="object"?p:"")+"]",o)
}})
}else{if(Object.prototype.toString.call(n)==="[object Object]"){for(l in n){h(m+"["+l+"]",n[l])
}}else{j(m,n)
}}}for(i in k){h(i,k[i])
}return g.join("&").replace(/%20/g,"+")
},storage:!!(window.localStorage&&window.StorageEvent),iterate:function(i,h){var j;
h=h||0;
(function g(){j=setTimeout(function(){if(i()===false){return
}g()
},h)
})();
return function(){clearTimeout(j)
}
},each:function(m,n,h){var l,j=0,k=m.length,g=a.util.isArray(m);
if(h){if(g){for(;
j<k;
j++){l=n.apply(m[j],h);
if(l===false){break
}}}else{for(j in m){l=n.apply(m[j],h);
if(l===false){break
}}}}else{if(g){for(;
j<k;
j++){l=n.call(m[j],j,m[j]);
if(l===false){break
}}}else{for(j in m){l=n.call(m[j],j,m[j]);
if(l===false){break
}}}}return m
},extend:function(k){var j,h,g;
for(j=1;
j<arguments.length;
j++){if((h=arguments[j])!=null){for(g in h){k[g]=h[g]
}}}return k
},on:function(i,h,g){if(i.addEventListener){i.addEventListener(h,g,false)
}else{if(i.attachEvent){i.attachEvent("on"+h,g)
}}},off:function(i,h,g){if(i.removeEventListener){i.removeEventListener(h,g,false)
}else{if(i.detachEvent){i.detachEvent("on"+h,g)
}}},log:function(i,h){if(window.console){var g=window.console[i];
if(typeof g==="function"){g.apply(window.console,h)
}}},warn:function(){a.util.log("warn",arguments)
},info:function(){a.util.log("info",arguments)
},debug:function(){a.util.log("debug",arguments)
},error:function(){a.util.log("error",arguments)
},xhr:function(){try{return new window.XMLHttpRequest()
}catch(h){try{return new window.ActiveXObject("Microsoft.XMLHTTP")
}catch(g){}}},parseJSON:function(g){return !g?null:window.JSON&&window.JSON.parse?window.JSON.parse(g):new Function("return "+g)()
},stringifyJSON:function(i){var l=/[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,j={"\b":"\\b","\t":"\\t","\n":"\\n","\f":"\\f","\r":"\\r",'"':'\\"',"\\":"\\\\"};
function g(m){return'"'+m.replace(l,function(n){var o=j[n];
return typeof o==="string"?o:"\\u"+("0000"+n.charCodeAt(0).toString(16)).slice(-4)
})+'"'
}function h(m){return m<10?"0"+m:m
}return window.JSON&&window.JSON.stringify?window.JSON.stringify(i):(function k(r,q){var p,o,m,n,t=q[r],s=typeof t;
if(t&&typeof t==="object"&&typeof t.toJSON==="function"){t=t.toJSON(r);
s=typeof t
}switch(s){case"string":return g(t);
case"number":return isFinite(t)?String(t):"null";
case"boolean":return String(t);
case"object":if(!t){return"null"
}switch(Object.prototype.toString.call(t)){case"[object Date]":return isFinite(t.valueOf())?'"'+t.getUTCFullYear()+"-"+h(t.getUTCMonth()+1)+"-"+h(t.getUTCDate())+"T"+h(t.getUTCHours())+":"+h(t.getUTCMinutes())+":"+h(t.getUTCSeconds())+'Z"':"null";
case"[object Array]":m=t.length;
n=[];
for(p=0;
p<m;
p++){n.push(k(p,t)||"null")
}return"["+n.join(",")+"]";
default:n=[];
for(p in t){if(b.call(t,p)){o=k(p,t);
if(o){n.push(g(p)+":"+o)
}}}return"{"+n.join(",")+"}"
}}})("",{"":i})
},checkCORSSupport:function(){if(a.util.browser.msie&&!window.XDomainRequest){return true
}else{if(a.util.browser.opera&&a.util.browser.version<12){return true
}else{if(a.util.trim(navigator.userAgent).slice(0,16)==="KreaTVWebKit/531"){return true
}else{if(a.util.trim(navigator.userAgent).slice(-7).toLowerCase()==="Kreatel"){return true
}}}}var g=navigator.userAgent.toLowerCase();
var h=g.indexOf("android")>-1;
if(h){return true
}return false
}};
d=a.util.now();
(function(){var h=navigator.userAgent.toLowerCase(),g=/(chrome)[ \/]([\w.]+)/.exec(h)||/(webkit)[ \/]([\w.]+)/.exec(h)||/(opera)(?:.*version|)[ \/]([\w.]+)/.exec(h)||/(msie) ([\w.]+)/.exec(h)||h.indexOf("compatible")<0&&/(mozilla)(?:.*? rv:([\w.]+)|)/.exec(h)||[];
a.util.browser[g[1]||""]=true;
a.util.browser.version=g[2]||"0";
if(a.util.browser.msie||(a.util.browser.mozilla&&a.util.browser.version.split(".")[0]==="1")){a.util.storage=false
}})();
a.util.on(window,"unload",function(g){a.unsubscribe()
});
a.util.on(window,"keypress",function(g){if(g.which===27){g.preventDefault()
}});
a.util.on(window,"offline",function(){a.unsubscribe()
});
window.atmosphere=a
})();