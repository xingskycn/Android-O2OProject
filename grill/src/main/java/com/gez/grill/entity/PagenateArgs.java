package com.gez.grill.entity;

public class PagenateArgs {
	private int pageIndex;

	private int pageSize;

	private int pageStart;

	private int pageEnd;

	public PagenateArgs(int pageIndex, int pageSize) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		pageStart = pageIndex * pageSize;
		pageEnd = pageIndex * pageSize + pageSize + 1;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public int getPageStart() {
		return pageStart;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageEnd() {
		return pageEnd;
	}
}
