package net.megx.was.webdav;

import java.net.MalformedURLException;
import java.net.URL;

import javax.jcr.Repository;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.jackrabbit.server.CredentialsProvider;
import org.apache.jackrabbit.webdav.simple.SimpleWebdavServlet;

public class SimpleWebdavServletImpl extends SimpleWebdavServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			String resCfg = config.getInitParameter(SimpleWebdavServlet.INIT_PARAM_RESOURCE_CONFIG);
			URL re = getServletContext().getResource(resCfg);
			System.out.println(re);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SimpleWebdavServletImpl(Repository repository) {
		this.repository = repository;
	}

	/**
     * the jcr repository
     */
    private Repository repository;

    /**
     * Returns the <code>Repository</code>. If no repository has been set or
     * created the repository initialized by <code>RepositoryAccessServlet</code>
     * is returned.
     *
     * @return repository
     * @see RepositoryAccessServlet#getRepository(ServletContext)
     */
    public Repository getRepository() {
        return repository;
    }
    
    @Override
    protected CredentialsProvider getCredentialsProvider() {
    	return new DavCredentialsProvider(repository, getInitParameter(INIT_PARAM_MISSING_AUTH_MAPPING));
    }
}