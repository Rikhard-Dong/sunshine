package com.hfmes.sunshine.utils;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/13 18:30
 * <p>
 * 保存一些常量
 */
public class Constants {
    // 对应DicType状态类型
    public static final Integer SD = 1;
    public static final Integer SM = 2;
    public static final Integer SP = 3;
    public static final Integer ST = 4;

    // 触发类型
    public static final String BTN_EVENT_TYPE = "按钮";


    // 对应的操作
    public static final Integer MOULD_FILLING = 1;                  // opId --> 1 装模/料
    public static final Integer COMPLETE_MOULD_FFILLING = 2;        // opId --> 2 装模完成
    public static final Integer DEMOULDING = 3;                     // opId --> 3 卸模/料
    public static final Integer COMPLETE_DEMOULDING = 4;            // opId --> 4  卸模完成
    public static final Integer MOULD_REPAIR = 5;                   // opId --> 5 模具维修
    public static final Integer DEMOULDING2 = 6;                    // opId --> 6  卸模/料
    public static final Integer MOULD_REPAIR_COMPLETE = 7;          // opId --> 7 模具修复
    public static final Integer MOULD_REPAIR_COMPLETE2 = 8;         // opId --> 8 模具修复
    public static final Integer START_PRODUCE = 9;                  // opId --> 9 开始生产
    public static final Integer CONTINUE_PRODUCE = 10;              // opId --> 10 继续生产
    public static final Integer COMPLETE_PRODUCE = 11;              // opId --> 11 生产完成
    public static final Integer SUSPEND_PRODUCE = 12;               // opId --> 12 暂停生产
    public static final Integer DEVICE_FAULT = 13;                  // opId --> 13 设备故障
    public static final Integer MOULD_FAULT = 14;                   // opId --> 14 模具故障
    public static final Integer REVOKE_DEVICE_REPORT_REPAIR = 15;   // opId --> 15 设备撤销报修
    public static final Integer REVOKE_DEVICE_REPORT_REPAIR2 = 16;  // opId --> 16 模具撤销报修
    public static final Integer REPAIR_DEVICE = 17;                 // opId --> 17 设备维修
    public static final Integer COMPLETE_REPAIR_DEVICE = 18;        // opId --> 18 设备修复
    public static final Integer CHECK_PRODUCE = 19;                 // opId --> 19 生产验收
    public static final Integer CONTINUE_PRODUCE2 = 20;             // opId --> 20 继续生产
    public static final Integer STOP_PRODUCE = 21;                  // opID --> 21 中止生产

    public  static final String MLD = "MLD";            // 模具工
    public  static final String PUNCH = "PUNCH";        // 冲床工
    public  static final String OVERHAUL = "OVERHAUL";  // 检修工
    public  static final String PDCN = "PDCB";          // 生产管理员
    public  static final String UNKNOWN = "UNKNOWN";    // 未知类型


    private Constants() {
    }
}
