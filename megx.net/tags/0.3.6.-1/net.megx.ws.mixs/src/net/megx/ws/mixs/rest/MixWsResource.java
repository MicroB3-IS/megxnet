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


package net.megx.ws.mixs.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import net.megx.model.ws.mixs.ChecklistItemDetails;
import net.megx.model.ws.mixs.ChecklistItems;
import net.megx.model.ws.mixs.EnvPackage;
import net.megx.model.ws.mixs.EnvPackageList;
import net.megx.model.ws.mixs.ExistingChecklistList;
import net.megx.model.ws.mixs.ExistingChecklists;
import net.megx.model.ws.mixs.IndependentMetadataItem;
import net.megx.model.ws.mixs.IndependentMetadataItems;
import net.megx.ws.core.CustomMediaType;
import net.megx.ws.core.providers.csv.CSVDocumentBuilder;
import net.megx.ws.core.providers.csv.CSVDocumentInfo;
import net.megx.ws.core.providers.csv.annotations.CSVDocument;
import net.megx.ws.core.providers.exceptions.ServiceException;
import net.megx.ws.core.providers.html.annotations.Template;
import net.megx.ws.core.utils.SqlUtils;
import net.megx.ws.mixs.dto.CSVFormattedIndMetadataItem;
import net.megx.ws.mixs.service.MixsWsService;


@Path("/gsc")
//@Component
//@Scope("singleton")
public class MixWsResource implements MixWsResourceConstants {

//	@Autowired
	private MixsWsService mixsWsService;

	public void setMixsWsService(MixsWsService service) {
		mixsWsService = service;
	}

	// 1. Named details of a metadata item independent of a checklist
	/**
	 * Private utility method that essentially returns a List of
	 * IndependentMetadataItem beans warped in IndependentMetadataItems bean for
	 * use of xml, txt, json, html... providers.
	 * 
	 * @param version
	 *            version of the Schema we are querying
	 * @param itemName
	 *            "item_name" rows containing specified item_name
	 * @param cols
	 *            - columns of interest
	 * @return {@link IndependentMetadataItems}
	 * @throws Exception
	 */
	private IndependentMetadataItems independentMetadataItems(String version,
			String itemName, String columns) throws Exception {
		String alowedColumns = SqlUtils.getAllowedColumns(
				clistItemDetailsColumns, columns);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("cols", alowedColumns.split(","));
		map.put("item_name", itemName);
		IndependentMetadataItems citems = new IndependentMetadataItems();
		List<IndependentMetadataItem> items = mixsWsService
				.getIndependentMetadataItems(map, version);
		citems.setItems(items);
		citems.setCols(alowedColumns.split(","));
		return citems;
	}

	/**
	 * Resource that produces xml representation of gsc_db.all_item_details
	 * view, internally uses:
	 * {@link #independentMetadataItems(String,String,String)
	 * independentMetadataItems(version, itemName, cols)}
	 * 
	 * @param Path
	 *            ("{version}/item/{item_name}/{cols}.xml")
	 * @param itemName
	 *            rows containing specified item_name - gotten from Path
	 *            parameter
	 * @param version
	 *            version of the Schema we are querying - gotten from Path
	 *            parameter
	 * @param cols
	 *            columns of interest - gotten from Path parameter
	 */
	@GET
	@Path("{version}/item/{item_name}/{cols}.xml")
	@Produces(MediaType.APPLICATION_XML)
	public IndependentMetadataItems getIndependentMetadataItems_xml(
			@PathParam("item_name") String itemName,
			@PathParam("version") String version,
			@PathParam("cols") String cols, @Context HttpServletRequest request)
			throws Exception {
		request.setAttribute("IntendedMediaType", MediaType.APPLICATION_XML);
		return independentMetadataItems(version, itemName, cols);
	}

	/**
	 * Resource that produces txt representation of gsc_db.all_item_details
	 * view, internally uses:
	 * {@link #independentMetadataItems(String,String,String)
	 * independentMetadataItems(version, itemName, cols)}
	 * 
	 * @param Path
	 *            ("{version}/item/{item_name}/{cols}.txt")
	 * @param itemName
	 *            rows containing specified item_name - gotten from Path
	 *            parameter
	 * @param version
	 *            version of the Schema we are querying - gotten from Path
	 *            parameter
	 * @param cols
	 *            columns of interest - gotten from Path parameter
	 */
	@GET
	@Path("{version}/item/{item_name}/{cols}.txt")
	@Produces(MediaType.TEXT_PLAIN)
	public IndependentMetadataItems getIndependentMetadataItems_txt(
			@PathParam("item_name") String itemName,
			@PathParam("version") String version,
			@PathParam("cols") String cols, @Context HttpServletRequest request)
			throws Exception {
		request.setAttribute("IntendedMediaType", MediaType.APPLICATION_JSON);
		return independentMetadataItems(version, itemName, cols);
	}

	/**
	 * Resource that produces json representation of gsc_db.all_item_details
	 * view, internally uses:
	 * {@link #independentMetadataItems(String,String,String)
	 * independentMetadataItems(version, itemName, cols)}
	 * 
	 * @param Path
	 *            ("{version}/item/{item_name}/{cols}.json")
	 * @param itemName
	 *            rows containing specified item_name - gotten from Path
	 *            parameter
	 * @param version
	 *            version of the Schema we are querying - gotten from Path
	 *            parameter
	 * @param cols
	 *            columns of interest - gotten from Path parameter
	 */
	@GET
	@Path("{version}/item/{item_name}/{cols}.json")
	@Produces(MediaType.APPLICATION_JSON)
	public IndependentMetadataItems getIndependentMetadataItems_json(
			@PathParam("item_name") String itemName,
			@PathParam("version") String version,
			@PathParam("cols") String cols, @Context HttpServletRequest request)
			throws Exception {
		request.setAttribute("IntendedMediaType", MediaType.APPLICATION_JSON);
		return independentMetadataItems(version, itemName, cols);
	}

	/**
	 * Resource that produces html representation of gsc_db.all_item_details
	 * view, internally uses:
	 * {@link #independentMetadataItems(String,String,String)
	 * independentMetadataItems(version, itemName, cols)}
	 * 
	 * @param Path
	 *            ("{version}/item/{item_name}/{cols}.html")
	 * @param itemName
	 *            rows containing specified item_name - gotten from Path
	 *            parameter
	 * @param version
	 *            version of the Schema we are querying - gotten from Path
	 *            parameter
	 * @param cols
	 *            columns of interest - gotten from Path parameter
	 */
	@GET
	@Path("{version}/item/{item_name}/{cols}.html")
	@Produces(MediaType.TEXT_HTML)
	@Template(name = "independentMetadataItems.vm")
	public IndependentMetadataItems getIndependentMetadataItems_html(
			@PathParam("item_name") String itemName,
			@PathParam("version") String version,
			@PathParam("cols") String cols, @Context HttpServletRequest request)
			throws Exception {
		request.setAttribute("IntendedMediaType", MediaType.TEXT_HTML);
		return independentMetadataItems(version, itemName, cols);
	}

	/**
	 * Resource that produces csv representation of gsc_db.all_item_details
	 * view, internally uses:
	 * {@link #independentMetadataItems(String,String,String)
	 * independentMetadataItems(version, itemName, cols)}
	 * 
	 * @param Path
	 *            ("{version}/item/{item_name}/{cols}.csv")
	 * @param itemName
	 *            rows containing specified item_name - gotten from Path
	 *            parameter
	 * @param version
	 *            version of the Schema we are querying - gotten from Path
	 *            parameter
	 * @param cols
	 *            columns of interest - gotten from Path parameter
	 */
	@GET
	@Path("{version}/item/{item_name}/{cols}.csv")
	@Produces(CustomMediaType.TEXT_CSV)
	@CSVDocument(preserveHeaderColumns = true, newLineSeparator = "\n", separator = ',')
	public CSVDocumentInfo getIndependentMetadataItems_csv(
			@PathParam("item_name") String itemName,
			@PathParam("version") String version,
			@PathParam("cols") String cols, @Context HttpServletRequest request)
			throws Exception {
		request.setAttribute("IntendedMediaType", CustomMediaType.TEXT_CSV);
		List<IndependentMetadataItem> items = independentMetadataItems(version,
				itemName, cols).getItems();
		String[] columns = SqlUtils.getAllowedColumns(clistItemDetailsColumns,
				cols).split(",");
		List<IndependentMetadataItem> csvMapped = new ArrayList<IndependentMetadataItem>(items.size());
		for(IndependentMetadataItem item: items){
			csvMapped.add( new CSVFormattedIndMetadataItem(item));
		}
		CSVDocumentInfo csvDocument = CSVDocumentBuilder.getInstance()
				.createDocument(true, CSVDocumentInfo.DEFAULT_SEPARATOR_CHAR,
						CSVDocumentInfo.DEFAULT_QUOTE_CHAR,
						CSVDocumentInfo.DEFAULT_LINE_END,
						CSVFormattedIndMetadataItem.class, columns, csvMapped);

		return csvDocument;
	}

	// 2. General Specification Checklist Items Details
	private ChecklistItems generalSpecificationItems(String clNameEnvPackage,
			String version) throws Exception {
		ChecklistItems citems = new ChecklistItems();
		List<ChecklistItemDetails> items = mixsWsService
				.getGeneralSpecificationItems(clNameEnvPackage, version);
		citems.setItems(items);
		return citems;
	}

	@GET
	@Path("{version}/cl/{clNameEnvPkg}.xml")
	@Produces(MediaType.APPLICATION_XML)
	public ChecklistItems getGeneralSpecificationItems_xml(
			@PathParam("clNameEnvPkg") String clNameEnvPkg,
			@PathParam("version") String version) throws Exception {
		return generalSpecificationItems(clNameEnvPkg, version);
	}

	@GET
	@Path("{version}/cl/{clNameEnvPkg}.txt")
	@Produces(MediaType.TEXT_PLAIN)
	public ChecklistItems getGeneralSpecificationItems_txt(
			@PathParam("clNameEnvPkg") String clNameEnvPkg,
			@PathParam("version") String version,
			@Context SecurityContext securityContext) throws Exception {

		return generalSpecificationItems(clNameEnvPkg, version);
	}

	@GET
	@Path("{version}/cl/{clNameEnvPkg}.json")
	@Produces(MediaType.APPLICATION_JSON)
	public ChecklistItems getGeneralSpecificationItems_json(
			@PathParam("clNameEnvPkg") String clNameEnvPkg,
			@PathParam("version") String version) throws Exception {
		return generalSpecificationItems(clNameEnvPkg, version);
	}

	@GET
	@Path("{version}/cl/{clNameEnvPkg}.html")
	@Produces(MediaType.TEXT_HTML)
	@Template(name = "checkListItemDetails.vm")
	public ChecklistItems getGeneralSpecificationItems_html(
			@PathParam("clNameEnvPkg") String clNameEnvPkg,
			@PathParam("version") String version) throws Exception {
		return generalSpecificationItems(clNameEnvPkg, version);
	}

	@GET
	@Path("{version}/cl/{clNameEnvPkg}.csv")
	@Produces(CustomMediaType.TEXT_CSV)
	@CSVDocument(preserveHeaderColumns = true, newLineSeparator = "\n", separator = ',')
	public List<ChecklistItemDetails> getGeneralSpecificationItems_csv(
			@PathParam("clNameEnvPkg") String clNameEnvPkg,
			@PathParam("version") String version) throws Exception {
		return generalSpecificationItems(clNameEnvPkg, version).getItems();
	}

	// 3.Specification of items from a checklist and an environmental package
	// name (that is show me the combined checklist)

	private ChecklistItems combinedChecklistSpecificationItems(String version,
			String clName, String envPackage) throws Exception {
		ChecklistItems citems = new ChecklistItems();
		List<ChecklistItemDetails> listClItems = mixsWsService
				.getCombinedChecklistSpecificationItems(clName, envPackage,
						version);
		citems.setItems(listClItems);
		return citems;
	}

	@GET
	@Path("{version}/cl/{cl_name},{env_pkg}.xml")
	@Produces(MediaType.APPLICATION_XML)
	public ChecklistItems getCombinedChecklistSpecificationItems_xml(
			@PathParam("version") String version,
			@PathParam("cl_name") String clName,
			@PathParam("env_pkg") String envPkg) throws Exception {
		return combinedChecklistSpecificationItems(version, clName, envPkg);
	}

	@GET
	@Path("{version}/cl/{cl_name},{env_pkg}.txt")
	@Produces(MediaType.TEXT_PLAIN)
	public ChecklistItems getCombinedChecklistSpecificationItems_txt(
			@PathParam("version") String version,
			@PathParam("cl_name") String clName,
			@PathParam("env_pkg") String envPkg) throws Exception {
		return combinedChecklistSpecificationItems(version, clName, envPkg);
	}

	@GET
	@Path("{version}/cl/{cl_name},{env_pkg}.json")
	@Produces(MediaType.APPLICATION_JSON)
	public ChecklistItems getCombinedChecklistSpecificationItems_json(
			@PathParam("version") String version,
			@PathParam("cl_name") String clName,
			@PathParam("env_pkg") String envPkg) throws Exception {
		return combinedChecklistSpecificationItems(version, clName, envPkg);
	}

	@GET
	@Path("{version}/cl/{cl_name},{env_pkg}.html")
	@Produces(MediaType.TEXT_HTML)
	@Template(name = "combinedCheckListDetails.vm")
	public ChecklistItems getCombinedChecklistSpecificationItems_html(
			@PathParam("version") String version,
			@PathParam("cl_name") String clName,
			@PathParam("env_pkg") String envPkg) throws Exception {
		return combinedChecklistSpecificationItems(version, clName, envPkg);
	}

	@GET
	@Path("{version}/cl/{cl_name},{env_pkg}.csv")
	@Produces(CustomMediaType.APPLICATION_CSV)
	@CSVDocument(preserveHeaderColumns = true, newLineSeparator = "\n", separator = ',')
	public List<ChecklistItemDetails> getCombinedChecklistSpecificationItems_csv(
			@PathParam("version") String version,
			@PathParam("cl_name") String clName,
			@PathParam("env_pkg") String envPkg) throws Exception {
		return combinedChecklistSpecificationItems(version, clName, envPkg)
				.getItems();
	}

	// 4. Specification of a particular item from a checklist or an
	// environmental package name

	private ChecklistItemDetails particularSpecificationItem(String version,
			String clNameEnvPackage, String itemName) throws Exception {
		ChecklistItemDetails checkListItem = mixsWsService
				.getParticularSpecificationItem(itemName, clNameEnvPackage,
						version);
		if (checkListItem == null) {
			throw new ServiceException(null, "Not found",
					"Requested resource was not found",
					Response.Status.NOT_FOUND, null);
		}
		return checkListItem;
	}

	@GET
	@Path("{version}/cl/{cl_name_env_pkg}/{item_name}.xml")
	@Produces(MediaType.APPLICATION_XML)
	public ChecklistItemDetails getParticularSpecificationItem_xml(
			@PathParam("version") String version,
			@PathParam("cl_name_env_pkg") String clNameEnvPkg,
			@PathParam("item_name") String itemName) throws Exception {
		return particularSpecificationItem(version, clNameEnvPkg, itemName);
	}

	@GET
	@Path("{version}/cl/{cl_name_env_pkg}/{item_name}.txt")
	@Produces(MediaType.TEXT_PLAIN)
	public ChecklistItemDetails getParticularSpecificationItem_txt(
			@PathParam("version") String version,
			@PathParam("cl_name_env_pkg") String clNameEnvPkg,
			@PathParam("item_name") String itemName) throws Exception {
		return particularSpecificationItem(version, clNameEnvPkg, itemName);
	}

	@GET
	@Path("{version}/cl/{cl_name_env_pkg}/{item_name}.json")
	@Produces(MediaType.APPLICATION_JSON)
	public ChecklistItemDetails getParticularSpecificationItem_json(
			@PathParam("version") String version,
			@PathParam("cl_name_env_pkg") String clNameEnvPkg,
			@PathParam("item_name") String itemName) throws Exception {
		return particularSpecificationItem(version, clNameEnvPkg, itemName);
	}

	@GET
	@Path("{version}/cl/{cl_name_env_pkg}/{item_name}.html")
	@Produces(MediaType.TEXT_HTML)
	@Template(name = "particularCheckListItemDetails.vm")
	public ChecklistItemDetails getParticularSpecificationItem_html(
			@PathParam("version") String version,
			@PathParam("cl_name_env_pkg") String clNameEnvPkg,
			@PathParam("item_name") String itemName) throws Exception {
		return particularSpecificationItem(version, clNameEnvPkg, itemName);
	}

	@GET
	@Path("{version}/cl/{cl_name_env_pkg}/{item_name}.csv")
	@Produces(CustomMediaType.APPLICATION_CSV)
	@CSVDocument(preserveHeaderColumns = true, newLineSeparator = "\n", separator = ',')
	public ChecklistItemDetails getParticularSpecificationItem_csv(
			@PathParam("version") String version,
			@PathParam("cl_name_env_pkg") String clNameEnvPkg,
			@PathParam("item_name") String itemName) throws Exception {
		return particularSpecificationItem(version, clNameEnvPkg, itemName);
	}
	

	// 5.List of existing checklists
	private ExistingChecklistList existingChecklists(String version)
			throws Exception {
		ExistingChecklistList citems = new ExistingChecklistList();
		List<ExistingChecklists> items = mixsWsService
				.getExistingChecklists(version);
		citems.setItems(items);
		return citems;
	}

	@GET
	@Path("{version}/existingChecklists.xml")
	@Produces(MediaType.APPLICATION_XML)
	public ExistingChecklistList getExistingChecklists_xml(
			@PathParam("version") String version) throws Exception {
		return existingChecklists(version);
	}

	@GET
	@Path("{version}/existingChecklists.txt")
	@Produces(MediaType.TEXT_PLAIN)
	public ExistingChecklistList getExistingChecklists_txt(
			@PathParam("version") String version) throws Exception {
		return existingChecklists(version);
	}

	@GET
	@Path("{version}/existingChecklists.json")
	@Produces(MediaType.APPLICATION_JSON)
	public ExistingChecklistList getExistingChecklists_json(
			@PathParam("version") String version) throws Exception {
		return existingChecklists(version);
	}

	@GET
	@Path("{version}/existingChecklists.html")
	@Produces(MediaType.TEXT_HTML)
	@Template(name = "existingCheckListList.vm")
	public ExistingChecklistList getExistingChecklists_html(
			@PathParam("version") String version) throws Exception {
		return existingChecklists(version);
	}

	@GET
	@Path("{version}/existingChecklists.csv")
	@Produces(CustomMediaType.APPLICATION_CSV)
	@CSVDocument(preserveHeaderColumns = true, newLineSeparator = "\n", separator = ',')
	public List<ExistingChecklists> getExistingChecklists_csv(
			@PathParam("version") String version) throws Exception {
		return existingChecklists(version).getItems();
	}

	// 6.List of environmental packages
	private EnvPackageList envPackagesList(String version) throws Exception {
		List<EnvPackage> envNames = mixsWsService.getEnvPackagesList(version);
		EnvPackageList envList = new EnvPackageList();
		envList.setEnvPackages(envNames);
		return envList;
	}

	@GET
	@Path("{version}/cl/envNamesList.xml")
	@Produces(MediaType.APPLICATION_XML)
	public EnvPackageList getEnvPackagesList_xml(
			@PathParam("version") String version) throws Exception {
		return envPackagesList(version);
	}
	
	@GET
	@Path("{version}/cl/envNamesList.txt")
	@Produces(MediaType.TEXT_PLAIN)
	public EnvPackageList getEnvPackagesList_txt(
			@PathParam("version") String version) throws Exception {
		return envPackagesList(version);
	}
	
	@GET
	@Path("{version}/cl/envNamesList.json")
	@Produces(MediaType.APPLICATION_JSON)
	public EnvPackageList getEnvPackagesList_json(
			@PathParam("version") String version) throws Exception {
		return envPackagesList(version);
	}
	
	@GET
	@Path("{version}/cl/envNamesList.html")
	@Produces(MediaType.TEXT_HTML)
	@Template(name = "envPackagesDetails.vm")
	public EnvPackageList getEnvPackagesList_html(
			@PathParam("version") String version) throws Exception {
		return envPackagesList(version);
	}
	
	@GET
	@Path("{version}/cl/envNamesList.csv")
	@Produces(CustomMediaType.APPLICATION_CSV)
	@CSVDocument(preserveHeaderColumns = true, newLineSeparator = "\n", separator = ',')
	public List<EnvPackage> getEnvPackagesList_csv(
			@PathParam("version") String version) throws Exception {
		return envPackagesList(version).getEnvPackages();
	}
	

}
