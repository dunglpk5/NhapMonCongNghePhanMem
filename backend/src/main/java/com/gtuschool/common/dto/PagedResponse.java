package com.gtuschool.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response phân trang cho danh sách dữ liệu.
 * FR-14: Hiển thị phân trang (20 bản ghi/trang)
 *
 * @param <T> Kiểu dữ liệu của phần tử trong danh sách
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;
    private boolean first;

    /**
     * Tạo PagedResponse từ Spring Page object
     */
    public static <T> PagedResponse<T> of(List<T> content, int page, int size,
                                            long totalElements, int totalPages) {
        return PagedResponse.<T>builder()
                .content(content)
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .first(page == 0)
                .last(page >= totalPages - 1)
                .build();
    }
}
