package org.commonfarm.dao.support;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author ajax
 * @author calvin
 */
@SuppressWarnings("serial")
public class Page implements Serializable {

	private static int DEFAULT_PAGE_SIZE = 20;

	private int pageSize = DEFAULT_PAGE_SIZE; // 

	private long start; // 

	private Object data; // 

	private long totalCount; // 

	/**
	 * 
	 */
	public Page() {
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList());
	}

	/**
	 * 
	 */
	public Page(long start, long totalSize, int pageSize, Object data) {
		this.pageSize = pageSize;
		this.start = start;
		this.totalCount = totalSize;
		this.data = data;
	}

	/**
	 * 
	 */
	public long getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 
	 */
	public long getTotalPageCount() {
		if (totalCount % pageSize == 0)
			return totalCount / pageSize;
		else
			return totalCount / pageSize + 1;
	}

	/**
	 * 
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 
	 */
	public Object getResult() {
		return data;
	}

	/**
	 * 
	 */
	public long getCurrentPageNo() {
		return start / pageSize + 1;
	}

	/**
	 * 
	 */
	public boolean hasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount() - 1;
	}

	/**
	 * 
	 */
	public boolean hasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}

	/**
	 * 
	 */
	protected static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	/**
	 * 
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}
}