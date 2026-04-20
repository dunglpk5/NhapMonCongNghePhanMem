package com.example.nmcnpm.shared.response;

/**
 * Wrapper chuẩn cho mọi API response trong hệ thống.
 * Frontend luôn nhận cùng một cấu trúc JSON:
 * { "success": true/false, "message": "...", "data": ... }
 */
public class ApiResponse<T> {

    private boolean success;
    private String  message;
    private T       data;

    // ── Constructors ──────────────────────────────────────────────────────

    private ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data    = data;
    }

    // ── Static factories ──────────────────────────────────────────────────

    /** Thành công — có data trả về */
    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    /** Thành công — không cần data */
    public static <T> ApiResponse<T> ok(String message) {
        return new ApiResponse<>(true, message, null);
    }

    /** Thất bại */
    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(false, message, null);
    }

    // ── Getters / Setters ─────────────────────────────────────────────────

    public boolean isSuccess()             { return success; }
    public void    setSuccess(boolean s)   { this.success = s; }

    public String getMessage()             { return message; }
    public void   setMessage(String m)     { this.message = m; }

    public T    getData()                  { return data; }
    public void setData(T data)            { this.data = data; }
}