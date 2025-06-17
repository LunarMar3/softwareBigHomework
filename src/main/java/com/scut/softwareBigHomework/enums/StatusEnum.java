package com.scut.softwareBigHomework.enums;

public class StatusEnum {
    public static final String STATUS_NOT_PROCESS = "未处理";
    public static final String STATUS_PROCESSING = "处理中";
    public static final String STATUS_APPROVING = "审批中";
    public static final String STATUS_CLOSED = "已关闭";
    public static final String STATUS_REJECTED = "已拒绝";
    public static final String STATUS_APPROVED = "已审批";
    public static final String STATUS_FINISHED = "已完成";

    public static String getStatusString(Integer index){
        return switch (index) {
            case 0 -> STATUS_NOT_PROCESS;
            case 1 -> STATUS_PROCESSING;
            case 2 -> STATUS_APPROVING;
            case 3 -> STATUS_CLOSED;
            case 4 -> STATUS_REJECTED;
            case 5 -> STATUS_APPROVED;
            case 6 -> STATUS_FINISHED;
            default -> "未知状态";
        };
    }

}
