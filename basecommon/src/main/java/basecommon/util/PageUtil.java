package basecommon.util;

/**
 * 分页工具类
 *
 * @author 张富华
 * @date 2020/7/1 11:09
 */
public final class PageUtil {

    /**
     * 获取分页信息
     */
    public static void initPageInfo(Page dbParam, Page dto) {
        dbParam.setPage(dto.getPage());
        dbParam.setPageSize(dto.getPageSize());
        dbParam.setOffset(dto.getPageSize() * (dto.getPage() - 1));
    }
}
