package com.hfmes.sunshine.enums;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 20:48
 * <p>
 * 模具事件
 */
public enum MouldEvents {
    MOULD_RETURN,               // 模具归还
    MOULD_COLLAR,               // 模具领用
    START_MOULD_FILLING,        // 开始装模
    COMPLETE_MOULD_FILLING,     // 装模完成
    START_DEMOULDING,           // 开始卸模
    COMPLETE_DEMOULDING,        // 完成卸模
    MOULD_REPORT_REPAIR,        // 模具报修
    MOULD_REVOKE_REPORT_REPAIR, // 撤销模具报修
    MOULD_REPAIR,               // 模具维修
    DEMOULDING,                 // 卸模/料
    MOULD_REPAIR_COMPLETE2SM40, // 模具修复到使用状态
    MOULD_REPAIR_COMPLETE2SM10, // 模具修复到领用状态

}
