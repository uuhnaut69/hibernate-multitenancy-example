package com.uuhnaut69.core.utils;

public final class TenantContextUtil {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    private TenantContextUtil() {
    }

    public static void setTenantId(String tenant) {
        CONTEXT.set(tenant);
    }

    public static String getTenant() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
