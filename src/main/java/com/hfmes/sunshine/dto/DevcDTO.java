package com.hfmes.sunshine.dto;

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
    }
}
