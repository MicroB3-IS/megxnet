TableUtils = {
	//convert stupid search_0, search_1 ... to arrays: search[0], search[1]
	converToReqObj: function(aoData) {
		var json = {};
    	for(var i=0; i<aoData.length; i++) {
    		var name = aoData[i].name;
    		var idx_ = name.indexOf("_");
    		var isArr = false;
    		var arrIdx = -1;
    		if(idx_ >= 0) {
    			var arr2 = name.split("_")
    			name = arr2[0];
    			arrIdx = arr2[1];
    			isArr=true;
    		}
    		if(isArr) {
    			var aname = name+"Arr";
    			if(!json[aname]) json[aname]=[];
    			json[aname][arrIdx] = aoData[i].value
    		} else {        			
    			json[name] = aoData[i].value;
    		}
    	}
    	return json;
	}
}