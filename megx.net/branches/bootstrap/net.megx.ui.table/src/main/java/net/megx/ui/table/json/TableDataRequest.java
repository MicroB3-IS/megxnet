package net.megx.ui.table.json;

public class TableDataRequest {
	private Integer iDisplayStart;
	private Integer iDisplayLength;
	private Integer iColumns;
	private String sSearch;
	private Boolean bRegex;
	private Boolean[] bSearchableArr;
	private String[] sSearchArr;
	private Boolean[] bRegexArr;
	private Boolean[] bSortableArr;
	private Integer iSortingCols;
	private Integer[] iSortColArr;
	private String[] sSortDirArr;
	private String[] mDataPropArr;
	private String sColumns;
	private String sEcho;
	public Integer getiDisplayStart() {
		return iDisplayStart;
	}
	public void setiDisplayStart(Integer iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}
	public Integer getiDisplayLength() {
		return iDisplayLength;
	}
	public void setiDisplayLength(Integer iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}
	public Integer getiColumns() {
		return iColumns;
	}
	public void setiColumns(Integer iColumns) {
		this.iColumns = iColumns;
	}
	public String getsSearch() {
		return sSearch;
	}
	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}
	public Boolean getbRegex() {
		return bRegex;
	}
	public void setbRegex(Boolean bRegex) {
		this.bRegex = bRegex;
	}
	public Boolean[] getbSearchableArr() {
		return bSearchableArr;
	}
	public void setbSearchableArr(Boolean[] bSearchableArr) {
		this.bSearchableArr = bSearchableArr;
	}
	public String[] getsSearchArr() {
		return sSearchArr;
	}
	public void setsSearchArr(String[] sSearchArr) {
		this.sSearchArr = sSearchArr;
	}
	public Boolean[] getbRegexArr() {
		return bRegexArr;
	}
	public void setbRegexArr(Boolean[] bRegexArr) {
		this.bRegexArr = bRegexArr;
	}
	public Boolean[] getbSortableArr() {
		return bSortableArr;
	}
	public void setbSortableArr(Boolean[] bSortableArr) {
		this.bSortableArr = bSortableArr;
	}
	public Integer getiSortingCols() {
		return iSortingCols;
	}
	public void setiSortingCols(Integer iSortingCols) {
		this.iSortingCols = iSortingCols;
	}
	public Integer[] getiSortColArr() {
		return iSortColArr;
	}
	public void setiSortColArr(Integer[] iSortColArr) {
		this.iSortColArr = iSortColArr;
	}
	public String[] getsSortDirArr() {
		return sSortDirArr;
	}
	public void setsSortDirArr(String[] sSortDirArr) {
		this.sSortDirArr = sSortDirArr;
	}
	public String[] getmDataPropArr() {
		return mDataPropArr;
	}
	public void setmDataPropArr(String[] mDataPropArr) {
		this.mDataPropArr = mDataPropArr;
	}
	public String getsColumns() {
		return sColumns;
	}
	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}
	public String getsEcho() {
		return sEcho;
	}
	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}
}
