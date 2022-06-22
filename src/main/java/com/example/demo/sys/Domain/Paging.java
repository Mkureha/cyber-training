package com.example.demo.sys.Domain;

public class Paging {

	private int pageSize = 10;
	private int blockSize = 5;
	private int page = 1;
	private int block = 1;
	private int totalListCnt;
	private int totalPageCnt;
	private int totalBlockCnt;
	private int startPage = 1;
	private int endPage = 1;
	private int startIndex = 0;
	private int prevBlock;
	private int nextBlock;
		
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getBlock() {
		return block;
	}

	public void setBlock(int block) {
		this.block = block;
	}

	public int getTotalListCnt() {
		return totalListCnt;
	}

	public void setTotalListCnt(int totalListCnt) {
		this.totalListCnt = totalListCnt;
	}

	public int getTotalPageCnt() {
		return totalPageCnt;
	}

	public void setTotalPageCnt(int totalPageCnt) {
		this.totalPageCnt = totalPageCnt;
	}

	public int getTotalBlockCnt() {
		return totalBlockCnt;
	}

	public void setTotalBlockCnt(int totalBlockCnt) {
		this.totalBlockCnt = totalBlockCnt;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPrevBlock() {
		return prevBlock;
	}

	public void setPrevBlock(int prevBlock) {
		this.prevBlock = prevBlock;
	}

	public int getNextBlock() {
		return nextBlock;
	}

	public void setNextBlock(int nextBlock) {
		this.nextBlock = nextBlock;
	}

		public Paging(int totalListCnt, int page) {
	
			setPage(page);
	        
			setTotalListCnt(totalListCnt);
	
			setTotalPageCnt((int) Math.ceil(totalListCnt * 1.0 / pageSize));
	
			setTotalBlockCnt((int) Math.ceil(totalPageCnt * 1.0 / blockSize));
	        
			setBlock((int) Math.ceil((page * 1.0)/blockSize)); 
	
			setStartPage((block - 1) * blockSize + 1);
	        
			setEndPage(startPage + blockSize - 1);
	        
			if(endPage > totalPageCnt){this.endPage = totalPageCnt;}
	        
			setPrevBlock((block * blockSize) - blockSize);
	
			if(prevBlock < 1) {this.prevBlock = 1;}
	
			setNextBlock((block * blockSize) + 1);
	
			if(nextBlock > totalPageCnt) {nextBlock = totalPageCnt;}
	
			setStartIndex((page-1) * pageSize);
		}
}
