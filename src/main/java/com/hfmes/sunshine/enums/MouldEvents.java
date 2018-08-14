package com.hfmes.sunshine.enums;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 20:48
 * <p>
 * 模具事件
 */
public enum MouldEvents {
    MOULD_RETURN,               // 模具归还             领用 -> 在架
    MOULD_COLLAR,               // 模具领用             在架 -> 领用
    START_MOULD_FILLING,        // 开始装模,            领用 -> 装模
    COMPLETE_MOULD_FILLING,     // 装模完成             装模 -> 使用
    START_DEMOULDING,           // 开始卸模             使用 -> 卸模
    COMPLETE_DEMOULDING,        // 完成卸模             卸模 -> 领用
    MOULD_REPORT_REPAIR,        // 模具报修             使用 -> 故障
    MOULD_REVOKE_REPORT_REPAIR, // 撤销模具报修          故障 -> 使用
    MOULD_REPAIR,               // 模具维修             故障 -> 模具检修
    DEMOULDING,                 // 卸模/料              模具检修 -> 模具检修
    MOULD_REPAIR_COMPLETE2SM40, // 模具修复到使用状态    模具检修 -> 使用
    MOULD_REPAIR_COMPLETE2SM10, // 模具修复到领用状态    模具检修 -> 领用

}
