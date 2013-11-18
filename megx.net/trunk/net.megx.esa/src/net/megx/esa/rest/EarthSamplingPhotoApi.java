package net.megx.esa.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

import net.megx.esa.util.ImageResizer;
import net.megx.megdb.esa.EarthSamplingAppService;
import net.megx.model.esa.SamplePhoto;

@Path("v1/esa/v1.0.0/content")
public class EarthSamplingPhotoApi extends BaseRestService{
	
	private EarthSamplingAppService service;
	private ImageResizer imageResizer;
	
	private static final int THUMBNAIL_WIDTH = 240;
	private static final int THUMBNAIL_HEIGHT = 240;
	
	public EarthSamplingPhotoApi(EarthSamplingAppService service){
		this.service = service;
		this.imageResizer = new ImageResizer();
	}
	
	@GET
	@Path("photo/thumbnail/{imageId}")
	public Response getThumbnailImage(@PathParam("imageId") final String imageId){
		try {
			final SamplePhoto photo = service.getSamplePhoto(imageId, false);
			if(photo != null){
				
				if(photo.getThumbnail() == null){
					//If the photo doesn't have a thumbnail, save one in db
					List<SamplePhoto> photosToUpdate = new ArrayList<SamplePhoto>();
					SamplePhoto originalPhoto = service.getSamplePhoto(imageId, true);
					if(originalPhoto.getData() != null){
						byte[] thumbnailPhoto = this.imageResizer.resizeImage(originalPhoto.getData(), THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT);
						photo.setThumbnail(thumbnailPhoto);
						photo.setData(originalPhoto.getData());
						photosToUpdate.add(photo);
						service.storePhotos(photosToUpdate);
					} else{
						//User hasn't uploaded this photo from his device to server
						throw new WebApplicationException(Response.Status.NOT_FOUND);
					}
				}
				
				ResponseBuilder rb = Response.ok().entity(new StreamingOutput(){
					@Override
					public void write(OutputStream out) throws IOException,
							WebApplicationException {
						out.write(photo.getThumbnail());
						out.flush();
					}
				});
				rb.type(photo.getMimeType());
				return rb.build();
			} else{
				throw new WebApplicationException(404);
			}
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
	
	@GET
	@Path("photo/original/{imageId}")
	public Response getOriginalImage(@PathParam("imageId") final String imageId){
		try {
			final SamplePhoto photo = service.getSamplePhoto(imageId, true);
			if(photo != null){
				ResponseBuilder rb = Response.ok().entity(new StreamingOutput(){
					@Override
					public void write(OutputStream out) throws IOException,
							WebApplicationException {
						if(photo.getData() != null){
							out.write(photo.getData());
							out.flush();
						} else{
							//User hasn't uploaded this photo from his device to server
							throw new WebApplicationException(500);
						}
					}
				});
				rb.type(photo.getMimeType());
				return rb.build();
			} else{
				throw new WebApplicationException(404);
			}
		} catch (Exception e) {
			throw new WebApplicationException(e);
		}
	}
}
