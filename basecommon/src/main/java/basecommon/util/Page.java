package basecommon.util;

import java.io.Serializable;

/**
 * @author 张富华
 * @date 2020/7/1 11:13
 */
public class Page implements Serializable {

    private Long offset;

    private Integer page;

    private Long pageSize;

    public Long getOffset() {
        return offset;
    }

    public Integer getPage() {
        return page;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }
}
