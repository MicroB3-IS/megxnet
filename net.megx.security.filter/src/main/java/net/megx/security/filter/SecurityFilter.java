package net.megx.security.filter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.megx.security.auth.AuthenticationManager;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.SecurityException;
import net.megx.security.auth.model.WebResource;
import net.megx.security.auth.services.WebResourcesService;
import net.megx.security.auth.web.WebContextUtils;
import net.megx.security.auth.web.WebUtils;
import net.megx.security.filter.http.HttpRequestWrapper;
import net.megx.security.filter.http.RequestUtils;
import net.megx.security.filter.http.TemplatePageNodeFactory;
import net.megx.security.filter.http.TemplatePageRenderer;
import net.megx.security.filter.http.impl.AuthorizePageNode;
import net.megx.security.filter.http.impl.ErrorFeedbackExtension;
import net.megx.security.filter.http.impl.ExceptionPageNode;
import net.megx.security.filter.http.impl.RegisterPageNode;
import net.megx.security.filter.impl.DefaultExceptionHandler;
import net.megx.security.logging.ErrorLog;
import net.megx.utils.OSGIUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.model.content.IContentNodeFactory;
import org.chon.cms.model.content.INodeRenderer;
import org.chon.web.RegUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.BundleContext;

public class SecurityFilter implements Filter {

    private Log log = LogFactory.getLog(getClass());

    private BundleContext context;
    private JSONObject bundleConfig;
    private AuthenticationManager authenticationManager;
    private WebResourcesService resourcesService;
    List<EntryPointWrapper> entryPoints = new ArrayList<SecurityFilter.EntryPointWrapper>();
    private List<ExceptionHandlerWrapper> exceptionHandlers = new LinkedList<ExceptionHandlerWrapper>();
    private boolean enabled;

    private Map<String, Object> contextParameters;

    private String ignorePattern = "^.*\\.(js|png|jpg|jpeg|gif|tiff|css)$";

    public SecurityFilter(BundleContext context, JSONObject bundleConfig,
            Map<String, Object> contextParameters) {
        super();
        this.context = context;
        this.bundleConfig = bundleConfig;
        this.contextParameters = contextParameters;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        if (!enabled) {
            log.debug("Security filter is not enabled. Will pass the request to the chain.");
            chain.doFilter(request, response);
            return;
        }
        
        if (request instanceof HttpServletRequest
                && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            WebContextUtils.storeExtraParameters(httpRequest,
                    new HashMap<String, Object>(contextParameters));
            
            if (log.isDebugEnabled()) {
                log.debug(String.format("Got request from requestURL %s",
                        httpRequest.getParameter("redirectURL")));
            }
            
            String requestPath = null;
            try {
                requestPath = WebUtils.getRequestPath(httpRequest, true);
                /*
                 * If the user tries to reach the application with the megx.site.url without the leading '/'
                 * chon somewhere along the way throws error 500, WebUtils.getAppURL(httpRequest) returns the
                 * application URL with the leading '/'
                 * Adding '/' to megx.site.url causes many links in the app to contain double slash '//'
                 * e.x: .../megx.net//login 
                 */
                if(requestPath.isEmpty()){
                	httpResponse.sendRedirect(WebUtils.getAppURL(httpRequest));
        			return;
                }
            } catch (URISyntaxException e) {
                log.error("Wrong URI: " + e.getMessage());
                throw new ServletException(e);
            }
            try {
                requestPath = WebUtils.getRequestPath(httpRequest, false);
            } catch (URISyntaxException e) {
                log.error("Wrong URI: " + e.getMessage());
                throw new ServletException(e);
            }

            if (requestPath.matches(ignorePattern)) {
                if (log.isDebugEnabled()) {
                    log.debug("Ignored path: " + requestPath);
                }
                chain.doFilter(request, response);
                return;
            }

            if (httpRequest.getSession() != null) {
                if (log.isDebugEnabled())
                    log.debug("Session at filter start: "
                            + httpRequest.getSession().getId());
            } else {
                log.debug("No session at filter start");
            }

            for (EntryPointWrapper entryPoint : entryPoints) {

                if (entryPoint.matches(requestPath)) {
                    if (log.isDebugEnabled()) {
                        log.debug(String.format(
                                "Request-path %s matches entry-point %s",
                                requestPath, entryPoint.name));
                    }
                    try {
                        entryPoint.entrypoint.doFilter(httpRequest,
                                httpResponse);
                    } catch (StopFilterException e) {
                        log.error("Catched a StopFilterException: " + e);
                        return;
                    } catch (Exception e) {
                        handleException(e, httpRequest, httpResponse);
                        return;
                    }
                }
            }

            try {
                checkAuthenticationResult(httpRequest, httpResponse);
            } catch (SecurityException e) {
                handleException(e, httpRequest, httpResponse);
                return;
            }

            SecurityContext context = WebContextUtils
                    .getSecurityContext(httpRequest);
            if (context != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Current Authentication: "
                            + context.getAuthentication());
                }
                httpRequest = new HttpRequestWrapper(httpRequest,
                        context.getAuthentication());
            }
            log.debug("Security filter - pass chain.");
            if (httpRequest.getSession() != null) {
                if (log.isDebugEnabled())
                    log.debug("Session at filter end: "
                            + httpRequest.getSession().getId());
            } else {
                log.debug("No session at filter end");
            }
            log.debug("Security filter end.");
            chain.doFilter(httpRequest, httpResponse);
        } else {
            throw new ServletException("Wrong Request Type.");
        }
    }

    protected void handleException(Exception e, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        log.error("Handling additional exception:" + e);
        for (ExceptionHandlerWrapper w : exceptionHandlers) {
            if (w.canHandle(request)) {
                if (!w.handler.handleException(e, request, response)) {
                    break;
                }
            }
        }
    }

    protected void checkAuthenticationResult(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
            SecurityException {
        List<WebResource> resources = null;
        try {
            // step 1 - get all rules that match the request path
            resources = resourcesService
                    .match(WebUtils.getRequestPath(request, true),
                            request.getMethod());
        } catch (Exception e) {
            throw new ServletException(e);
        }
        if (resources == null || resources.size() == 0) {
            log.debug("No rulez match this resource. Filter pass...");
            return;
        }
        SecurityContext context = WebContextUtils.getSecurityContext(request);
        if (context == null) {
            throw new SecurityException(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (context.getAuthentication() == null) {
            throw new SecurityException(HttpServletResponse.SC_UNAUTHORIZED);
        }
        // for(WebResource resource: resources){
        authenticationManager.checkAuthentication(context.getAuthentication(),
                resources);
        // }
        if (log.isDebugEnabled())
            log.debug("Authentication successful: "
                    + context.getAuthentication());
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        log.debug("Initializing security filter chain...");
        try {
            JSONObject filterConfig = bundleConfig.getJSONObject("filter");
            if (log.isDebugEnabled()) {
                log.debug(String.format("Filter configuration: %s",
                        filterConfig.toString(3)));
            }
            enabled = Boolean.TRUE.equals(filterConfig.optBoolean("enabled"));
            JSONArray entryPoints = filterConfig.getJSONArray("entrypoints");

            log.debug("Initializing security entrypoints...");
            for (int i = 0; i < entryPoints.length(); i++) {
                JSONObject entryPointConfig = entryPoints.getJSONObject(i);
                buildEntryPoint(entryPointConfig);
            }
            log.debug("Entrypoint built.");
            Collections.sort(this.entryPoints);

            ignorePattern = filterConfig.optString("ignorePattern",
                    ignorePattern);

            OSGIUtils.requestService(AuthenticationManager.class.getName(),
                    context,
                    new OSGIUtils.OnServiceAvailable<AuthenticationManager>() {

                        @Override
                        public void serviceAvailable(String serviceName,
                                AuthenticationManager service) {
                            SecurityFilter.this.authenticationManager = service;
                            if (log.isDebugEnabled())
                                log.debug("Obtained authentication manager instance: "
                                        + service.toString());
                        }

                    });

            OSGIUtils.requestService(WebResourcesService.class.getName(),
                    context,
                    new OSGIUtils.OnServiceAvailable<WebResourcesService>() {

                        @Override
                        public void serviceAvailable(String serviceName,
                                WebResourcesService service) {
                            SecurityFilter.this.resourcesService = service;
                            if (log.isDebugEnabled())
                                log.debug("Obtained WebResourcesService instance:  "
                                        + service);
                        }
                    });

            initializeEndpoints();
            buildExceptionHandlers(filterConfig);
        } catch (Exception e) {
            log.error(e);
            throw new ServletException(e);
        }
        log.debug("Security filter successfully initialized.");
    }

    private void buildExceptionHandlers(JSONObject config) throws Exception {
        JSONArray handlersCfg = config.optJSONArray("exceptionHandlers");
        DefaultExceptionHandler defaultExceptionHandler = new DefaultExceptionHandler();
        defaultExceptionHandler.init(config.optJSONObject("defaultHandler"));
        ExceptionHandlerWrapper defaultHandler = new ExceptionHandlerWrapper(
                defaultExceptionHandler, -1, new String[] { "/.*" });
        if (handlersCfg == null) {
            log.debug("Using only the default exception handler");
            exceptionHandlers.add(defaultHandler);
            return;
        }

        for (int i = 0; i < handlersCfg.length(); i++) {
            JSONObject cfg = handlersCfg.optJSONObject(i);
            if (cfg != null) {
                String className = cfg.optString("class");
                int order = cfg.optInt("order", 0);
                String[] patterns = null;
                JSONArray patternsArr = cfg.optJSONArray("patterns");
                if (patternsArr != null && patternsArr.length() > 0) {
                    patterns = new String[patternsArr.length()];
                    for (int pi = 0; pi < patternsArr.length(); pi++) {
                        patterns[pi] = patternsArr.optString(pi);
                    }
                }
                log.debug("Building exception handler of type: " + className);
                try {
                    Class<?> ehClass = getClassLoader().loadClass(className);

                    Object instance = ehClass.newInstance();

                    if (instance instanceof ExceptionHandler) {
                        ((ExceptionHandler) instance).init(cfg);
                        exceptionHandlers.add(new ExceptionHandlerWrapper(
                                (ExceptionHandler) instance, order, patterns));
                        log.debug("Build exception handler of instance: "
                                + instance + " with order=" + order);
                    } else {
                        log.warn(String
                                .format("Object (%s) is not instance of ExceptionHandler. Discarding instance.",
                                        instance.toString()));
                    }

                } catch (ClassNotFoundException e) {
                    log.error(
                            "The class for the exception handler implementation was not found.",
                            e);
                    throw e;
                }
            }
        }
        Collections.sort(exceptionHandlers);
        // add the default handler as last...
        exceptionHandlers.add(defaultHandler);
        log.debug("Exception handlers initialized.");
    }

    @SuppressWarnings("unchecked")
    protected void buildEntryPoint(JSONObject config) throws JSONException,
            ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        String name = config.getString("name");
        Object urlPattern = config.get("urlPattern");
        String clazz = config.getString("class");
        int order = config.optInt("order");

        boolean enabled = config.optBoolean("enabled", true);

        if (log.isDebugEnabled()) {
            log.debug(String
                    .format("Building entrypoint (name='%s',urlPattern='%s',class='%s',order='%d')",
                            name, urlPattern, clazz, order));
            log.debug("Full configuration for entrypoint: " + config);
        }

        if (!enabled) {
            log.debug("The entry point has been disabled.");
            return;
        }

        Class<? extends SecurityFilterEntrypoint> epClass = (Class<? extends SecurityFilterEntrypoint>) getClassLoader()
                .loadClass(clazz);
        SecurityFilterEntrypoint entrypoint = epClass.newInstance();
        entrypoint.initialize(context, config);

        String[] urlPatterns = null;

        if (urlPattern instanceof String) {
            urlPatterns = new String[] { (String) urlPattern };
        } else if (urlPattern instanceof JSONArray) {
            JSONArray ja = (JSONArray) urlPattern;
            urlPatterns = new String[ja.length()];
            for (int i = 0; i < ja.length(); i++) {
                String pattern = ja.getString(i);
                urlPatterns[i] = pattern;
            }
        } else {
            urlPatterns = new String[] {};
        }

        entryPoints.add(new EntryPointWrapper(entrypoint, urlPatterns, name,
                order));
        log.debug("Entry-point successfuly built.");
    }

    private void initializeEndpoints() {
        log.debug("Initializing endpoints renderers...");
        RegUtils.reg(context, IContentNodeFactory.class.getName(),
                new TemplatePageNodeFactory() {

                    @Override
                    protected void initialize() {
                        register(AuthorizePageNode.class);
                        register(RegisterPageNode.class);
                        register(ExceptionPageNode.class);
                    }
                }, null);

        OSGIUtils.requestService(ErrorLog.class.getName(), context,
                new OSGIUtils.OnServiceAvailable<ErrorLog>() {

                    @Override
                    public void serviceAvailable(String name, ErrorLog service) {
                        Hashtable<String, String> props = new Hashtable<String, String>();
                        props.put("renderer",
                                TemplatePageRenderer.class.getName());
                        RequestUtils requestUtils = buildRequestUtils(service);
                        RegUtils.reg(
                                context,
                                INodeRenderer.class.getName(),
                                new TemplatePageRenderer(requestUtils, service),
                                props);

                        JCRApplication application = (JCRApplication) contextParameters
                                .get("JCRApplicationInstance");
                        if (application != null) {
                            application.regExtension("feedback",
                                    new ErrorFeedbackExtension(service,
                                            requestUtils));
                            log.debug("Feedback extension registered.");
                        }

                        log.debug("Initializing endpoints renderers complete.");
                    }

                });

    }

    private RequestUtils buildRequestUtils(ErrorLog errorLog) {
        JSONObject cfg = null;
        try {
            cfg = bundleConfig.getJSONObject("filter").getJSONObject("errors")
                    .getJSONObject("feedback");
            long ttl = cfg.getLong("requestValidity");
            int maxrequests = cfg.getInt("maxNumberOfRequests");
            return new RequestUtils(ttl, maxrequests, errorLog);
        } catch (JSONException e) {
            log.error("Unable to parse configuration for errors logging", e);
            return new RequestUtils();
        }

    }

    protected class EntryPointWrapper implements Comparable<EntryPointWrapper> {
        public SecurityFilterEntrypoint entrypoint;
        private String[] urlPatterns;
        public String name;
        public int order;

        public EntryPointWrapper(SecurityFilterEntrypoint entrypoint,
                String[] urlPatterns, String name, int order) {
            super();
            this.entrypoint = entrypoint;
            this.urlPatterns = urlPatterns;
            this.name = name;
            this.order = order;
        }

        public boolean matches(String path) {
            for (String urlPattern : urlPatterns) {
                if (path.matches(urlPattern)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int compareTo(EntryPointWrapper o) {
            return order - o.order;
        }
    }

    protected class ExceptionHandlerWrapper implements
            Comparable<ExceptionHandlerWrapper> {
        public ExceptionHandler handler;
        public int order;
        public String[] patterns;

        public ExceptionHandlerWrapper() {
        }

        public ExceptionHandlerWrapper(ExceptionHandler handler, int order,
                String[] patterns) {
            super();
            this.handler = handler;
            this.order = order;
            this.patterns = patterns;
        }

        @Override
        public int compareTo(ExceptionHandlerWrapper o) {
            return order - o.order;
        }

        public boolean canHandle(HttpServletRequest request)
                throws ServletException {
            if (patterns == null)
                return false;
            String path;
            try {
                path = WebUtils.getRequestPath(request, false);
            } catch (URISyntaxException e) {
                throw new ServletException(e);
            }
            for (String pattern : patterns) {
                if (path.matches(pattern))
                    return true;
            }
            return false;
        }

    }

    protected ClassLoader getClassLoader() {
        return SecurityFilter.class.getClassLoader();
    }
}
