/*
 * Copyright 2011 Max Planck Institute for Marine Microbiology
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.chon.megx.net.template;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.Extension;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.mpac.Action;
import org.json.JSONObject;
import org.osgi.framework.BundleContext;

public class Activator extends ResTplConfiguredActivator {

  private static final Log log = LogFactory.getLog(Activator.class);

  @Override
  protected String getName() {
    return "net.megx.chon.template";
  }

  @Override
  protected void onAppAdded(BundleContext context, JCRApplication app) {
    super.onAppAdded(context, app);
  }

  @Override
  protected void registerExtensions(JCRApplication app) {
    log.debug("registering megx template");


    final String OPT_GMSBASEURL_KEY = "gmsBaseUrl";

    app.regExtension("megx", new Extension() {

      String gmsBaseUrl = "http://gms-dev.megx.net/map";

      @Override
      public Map<String, Action> getAdminActons() {
        // TODO Auto-generated method stub
        return null;
      }

      @Override
      public Map<String, Action> getAjaxActons() {
        // TODO Auto-generated method stub
        return null;
      }

      @Override
      public Object getTplObject(Request arg0, Response arg1, IContentNode arg2) {

        JSONObject cfg = getConfig();

        // if cfg is null then log as error but continue with empty
        // Jsonobject in order to apply default config

        if ( cfg == null ) {
          log.error("Could not load config json getConfig() returned null");
          cfg = new JSONObject();
        }

        log.debug("config is=" + cfg);
        log.debug("creating velocity template object");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(OPT_GMSBASEURL_KEY,
            cfg.optString(OPT_GMSBASEURL_KEY, "http://gms.megx.net"));

        return map;
      }

    });
  }
}
