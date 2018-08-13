package com.hfmes.sunshine.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hfmes.sunshine.domain.Devc;
import lombok.Data;

import java.util.Date;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/10 15:07
 */
@Data
public class DevcDTO {

    private Integer deviceId;
    private Integer deptId;
    private String title;
    private String type;
    private Date buyTime;
    private String status;
    private String memo;
    private Integer taskId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taskSetDate;
    private String mldStatus;
    private Integer weightTun;
    private String statusStr;
    private String mldStatusStr;

    public DevcDTO() {
    }

    public DevcDTO(Devc devc) {
        this.deviceId = devc.getDeviceId();
        this.deptId = devc.getDeptId();
        this.title = devc.getTitle();
        this.type = devc.getType();
        this.buyTime = devc.getBuyTime();
        this.status = devc.getStatus();
        this.memo = devc.getMemo();
        this.taskId = devc.getTaskId();
        this.taskSetDate = devc.getTaskSetDate();
        this.mldStatus = devc.getMldStatus();

        switch (this.status) {
            case "SD00":
                this.statusStr = "待机";
                break;
            case "SD10":
                this.statusStr = "运行";
                break;
            case "SD20":
                this.statusStr = "故障";
                break;
            case "SD30":
                this.statusStr = "检修";
                break;
            default:
                this.statusStr = "UNKNOWN";
        }

        switch (this.mldStatus) {
            case "SM00":
                this.mldStatusStr = "在架";
                break;
            case "SM10":
                this.mldStatusStr = "领用";
                break;
            case "SM20":
                this.mldStatusStr = "装模";
                break;
            case "SM30":
                this.mldStatusStr = "卸模";
                break;
            case "SM40":
                this.mldStatusStr = "使用";
                break;
            case "SM50":
                this.mldStatusStr = "故障";
                break;
            case "SM60":
                this.mldStatusStr = "检修";
                break;
            default:
                this.mldStatusStr = "UNKNOWN";
        }
    }
}
