package net.megx.security.filter.ui.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import net.megx.security.filter.ui.ReCaptchaResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ReCaptchaDeserializer implements
    JsonDeserializer<ReCaptchaResponse> {

  @Override
  public ReCaptchaResponse deserialize(JsonElement el, Type type,
      JsonDeserializationContext context) throws JsonParseException {

    ReCaptchaResponse captchaResponse = new ReCaptchaResponse();
    List<String> errorMessages = new ArrayList<String>();
    if (!el.isJsonObject()) {
      throw new JsonParseException("Expected JSON Object. Got: "
          + (el != null ? el.getAsString() : "null") + " instead.");
    }
    JsonObject jo = el.getAsJsonObject();
    captchaResponse.setSuccess(Boolean.valueOf(getString("success", jo)));
    
    JsonArray errMsg = getArray("error-codes", jo);
    
    if(errMsg != null){
      
      for (JsonElement jsonElement : errMsg) {
        
        errorMessages.add(jsonElement.getAsString());
      }
      captchaResponse.setErrorMessages( errorMessages );
    }
    

    return captchaResponse;
  }

  private String getString(String name, JsonObject jo) {
    String s = null;
    if (jo.has(name)) {
      if (!jo.get(name).isJsonNull())
        s = jo.get(name).getAsString();
    }
    return s;
  }
  private JsonArray getArray(String name, JsonObject jo) {
    JsonArray s = null;
    if (jo.has(name)) {
      if (!jo.get(name).isJsonNull())
        s = jo.get(name).getAsJsonArray();
    }
    return s;
  }

}
