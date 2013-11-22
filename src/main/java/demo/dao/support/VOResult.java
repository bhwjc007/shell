package shell.framework.dao.support;

import java.util.List;
import java.util.Map;

/**
 * 分页对象 - 值对象
 * 通过该对象，可以在页面直接获取总页数、当前页码
 * 以及总记录数等
 * @author yangchangming
 *
 */
public class VOResult {

	//每页显示记录数
	private int pageSize = 10;
	
	//当前页码
	private int currentPage = 1;
	
	//总记录数
	private int totalRows = 0;
	
	//总页数
	private int totalPages = 0;
	
	//分页查询的数据集(List)
	private List<?> resultList = null;
	
	//分页查询的数据集(Map)
	private Map<?,?> resultMap = null;
	
	public VOResult(){}
	
	/**
	 * 查询结果为list，构造分页对象
	 * @param resultList
	 */
	public VOResult(List<?> resultList){
		this.resultList = resultList;
	}
	
	
	/**
	 * 查询结果集为map，构造分页对象
	 * @param resultMap
	 */
	public VOResult(Map<?,?> resultMap){
		this.resultMap = resultMap;
	}
	
	
	
	
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	
	
	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	
	/**
	 * @return the totalRows
	 */
	public int getTotalRows() {
		return totalRows;
	}
	
	
	/**
	 * @param totalRows the totalRows to set
	 */
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	

	/**
	 * 获取总页数
	 * @return
	 */
	public int getTotalPages() {
//		int temp = (this.totalRows % this.pageSize) >0 ? 1 : 0;  
//		int _totalPages = this.totalRows / this.pageSize + temp;
		
		//页数算法
		int _totalPages = (totalRows+pageSize-1)/pageSize; 
		this.setTotalPages(_totalPages);
		return totalPages;
	}
	
	
	/**
	 * @param totalPage the totalPage to set
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	
	/**
	 * @return the resultList
	 */
	public List<?> getResultList() {
		return resultList;
	}
	
	
	/**
	 * @param resultList the resultList to set
	 */
	public void setResultList(List<?> resultList) {
		this.resultList = resultList;
	}
	
	/**
	 * @return the resultMap
	 */
	public Map<?, ?> getResultMap() {
		return resultMap;
	}
	
	
	/**
	 * @param resultMap the resultMap to set
	 */
	public void setResultMap(Map<?, ?> resultMap) {
		this.resultMap = resultMap;
	}
	
}
