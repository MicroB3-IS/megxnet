package net.megx.security.oauth.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.oauth.OAuth;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;
import net.oauth.SimpleOAuthValidator;

public class OAuth10aValidator extends SimpleOAuthValidator{
	
	private static Set<String> OAUTH_PARAMETES = new HashSet<String>();
	static{
		OAUTH_PARAMETES.addAll(SINGLE_PARAMETERS);
		OAUTH_PARAMETES.add("oauth_verifier");
	}
	
	@Override
	protected void checkSingleParameters(OAuthMessage message) throws IOException,
			OAuthException {
		// Check for repeated oauth_ parameters:
        boolean repeated = false;
        Map<String, Collection<String>> nameToValues = new HashMap<String, Collection<String>>();
        for (Map.Entry<String, String> parameter : message.getParameters()) {
            String name = parameter.getKey();
            if (getSingleParameters().contains(name)) {
                Collection<String> values = nameToValues.get(name);
                if (values == null) {
                    values = new ArrayList<String>();
                    nameToValues.put(name, values);
                } else {
                    repeated = true;
                }
                values.add(parameter.getValue());
            }
        }
        if (repeated) {
            Collection<OAuth.Parameter> rejected = new ArrayList<OAuth.Parameter>();
            for (Map.Entry<String, Collection<String>> p : nameToValues.entrySet()) {
                String name = p.getKey();
                Collection<String> values = p.getValue();
                if (values.size() > 1) {
                    for (String value : values) {
                        rejected.add(new OAuth.Parameter(name, value));
                    }
                }
            }
            OAuthProblemException problem = new OAuthProblemException(OAuth.Problems.PARAMETER_REJECTED);
            problem.setParameter(OAuth.Problems.OAUTH_PARAMETERS_REJECTED, OAuth.formEncode(rejected));
            throw problem;
        }

	}
	
	protected Set<String> getSingleParameters(){
		return OAUTH_PARAMETES;
	}
}
