package com.gtuschool.audit.service;

/**
 * Logging Module Interface.
 * FR-57: Ghi lại lịch sử chỉnh sửa dữ liệu.
 * NFR-06: Ghi nhận 100% lịch sử chỉnh sửa dữ liệu quan trọng.
 *
 * Theo thiết kế trong báo cáo: ILoggingService
 */
public interface ILoggingService {

    /**
     * Ghi log thao tác INSERT
     *
     * @param tableName Tên bảng
     * @param userId    ID người thực hiện
     * @param newData   Dữ liệu mới (JSON)
     */
    void logInsert(String tableName, Integer userId, String newData);

    /**
     * Ghi log thao tác UPDATE
     *
     * @param tableName Tên bảng
     * @param userId    ID người thực hiện
     * @param oldData   Dữ liệu cũ (JSON)
     * @param newData   Dữ liệu mới (JSON)
     */
    void logUpdate(String tableName, Integer userId, String oldData, String newData);

    /**
     * Ghi log thao tác DELETE
     *
     * @param tableName Tên bảng
     * @param userId    ID người thực hiện
     * @param oldData   Dữ liệu bị xóa (JSON)
     */
    void logDelete(String tableName, Integer userId, String oldData);
}
