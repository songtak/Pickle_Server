package com.pickle.web.commons;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component @Data
public class Pagination {
    private int rowCount, startRow, endRow,
            pageCount, pageSize, startPage, endPage, currentPage,
            blockCount, blockSize, preBlock, nextBlock, currentBlock;
    private boolean existPrev, existNext;
    public void paging(int count, int currentPage){
        //사이즈와 카운트만 1부터 시작, 나머지는 index이므로 0부터 시작한다고 생각하기
        this.currentPage = currentPage;
        pageSize = 10;
        blockSize = 5;
        rowCount =  count;
        pageCount = rowCount%pageSize != 0 ? rowCount/pageSize + 1 : rowCount/pageSize ;
        blockCount = pageCount%blockSize != 0 ? pageCount/blockSize + 1 : pageCount/blockSize ;
        startRow = currentPage*pageSize;
        endRow = currentPage != (pageCount-1) ? startRow+(pageSize-1) : rowCount-1 ;
        currentBlock = currentPage/blockSize;
        startPage = currentBlock*blockSize;
        endPage = currentBlock != (blockCount-1) ? startPage+(blockSize-1) : pageCount-1;
        preBlock = startPage - blockSize;
        nextBlock = startPage + blockSize;
        existPrev = currentBlock != 0 ;
        existNext = currentBlock+1 != blockCount ;
    }
}
