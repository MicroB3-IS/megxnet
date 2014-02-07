package net.megx.storage.ams;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import net.megx.storage.Context;
import net.megx.storage.ResourceAccessException;
import net.megx.storage.StorageSecuirtyException;

/**
 * Just an example
 * @author pavle
 *
 */
public class DBAccessMechanism extends BaseAccessMechanism{
	
	private DataSource dataSource;
	
	public DBAccessMechanism(Context context) {
		super(context);
		try {
			dataSource = (DataSource)new InitialContext().lookup((String)context.getProperty("datsource"));
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean resourceExist(URI uri) throws StorageSecuirtyException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public InputStream readResource(URI uri) throws StorageSecuirtyException,
			ResourceAccessException {
		PreparedStatement ps;
		try {
			ps = dataSource.getConnection().prepareStatement("select resourceBlob from resources where URI=?");
			ps.setString(1, resolve(uri).toString());
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return rs.getBlob("resourceBlob").getBinaryStream();
			}else{
				throw new ResourceAccessException("Not found!");
			}
		} catch (SQLException e) {
			throw new ResourceAccessException(e);
		}
	}

	@Override
	public OutputStream writeResource(URI uri) throws StorageSecuirtyException,
			ResourceAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI create(URI uri) throws StorageSecuirtyException,
			ResourceAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(URI uri) throws StorageSecuirtyException,
			ResourceAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public URI move(URI uri, URI toURI) throws StorageSecuirtyException,
			ResourceAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI copy(URI uri, URI toURI) throws StorageSecuirtyException,
			ResourceAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI resolve(URI input) {
		PreparedStatement ps;
		try {
			ps = dataSource.getConnection().prepareStatement("select accessURI from uri_mappings where universal_URI=?");
			ps.setString(1, input.toString());
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				try {
					return new URI(rs.getString("accessURI"));
				} catch (URISyntaxException e) {
					return null;
				}
			}else{
				return null;
			}
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public Object readAttribute(URI resource, String attrName)
			throws StorageSecuirtyException, ResourceAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveAttribute(URI resource, String attrName, Object attrValue)
			throws StorageSecuirtyException, ResourceAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public URI createURI(String... parts) throws URISyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

}
