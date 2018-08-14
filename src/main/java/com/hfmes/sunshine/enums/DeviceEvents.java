package com.hfmes.sunshine.enums;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/8 20:46
 * 设备操作
 */
public enum DeviceEvents {
    BIND_ORDER,                 // 绑定订单
    PRODUCE_NEXT_ORDER,         // 生产下一单
    PRODUCE_CHECK_AND_ACCEPT,   // 生产验收
    PRODUCE_CONTINUE,           // 继续生产     待机 -> 运行
    STOP_AND_NEW_ORDER,         // 中止并新单
    PRODUCE_START,              // 开始生产
    PRODUCE_RECOVERY,           // 恢复生产
    PRODUCE_COMPLETE,           // 生产完成     运行 -> 待机
    PRODUCE_SUSPEND,            // 暂停生产     运行 -> 暂停
    DEVICE_REPORT_REPAIR,       // 设备报修
    DEVICE_REVOKE_REPORT_REPAIR,// 撤销报修
    DEVICE_REPAIR,              // 设备维修
    MOULD_REPAIR,               // 模具维修
    DEVICE_REPAIR_COMPLETE,     // 设备维修完成
    MOULD_REPAIR_COMPLETE,      // 模具维修完成
}
