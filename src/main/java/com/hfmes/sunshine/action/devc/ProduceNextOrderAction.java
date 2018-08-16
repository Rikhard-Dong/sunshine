package com.hfmes.sunshine.action.devc;

import com.hfmes.sunshine.action.BaseAction;
import com.hfmes.sunshine.domain.Task;
import com.hfmes.sunshine.enums.DeviceEvents;
import com.hfmes.sunshine.enums.DeviceStatus;
import com.hfmes.sunshine.enums.TaskStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hfmes.sunshine.utils.Constants.ST;


/**
 * @author supreDong@gmail.com
 * @date 2018/8/9 9:20
 * <p>
 * 生产下一单action
 */
@Component
@Slf4j
public class ProduceNextOrderAction extends BaseAction implements Action<DeviceStatus, DeviceEvents> {

    @Override
    @Transactional
    public void execute(StateContext<DeviceStatus, DeviceEvents> context) {
        log.debug("生产下一单...");
        contextLoad(context);
        updateNum();

        Integer workerType = (Integer) context.getMessageHeader("workType");
        if (workerType != null && workerType == 1) {
            // 如果是生产管理者执行下一单, 则需要将工单状态改为验收通过
            curTaskStatus = devc.getTask().getStatus();
            nextTaskStatus = TaskStatus.ST40.toString();
            statusDataLog(ST);
        }
        resetCounts();

        // 更新planDtl的数据
        updatePlanDtl();

        // 获取下一单
        boolean flag = false;
        boolean isGet = false;
        int idx = 0;
        List<Task> tasksTemp = deviceTaskMap.get(devcId);
        for (int i = 0; i < tasksTemp.size(); i++) {
            Task tmp = tasksTemp.get(i);
            if (tmp.getTaskId().equals(task.getTaskId())) {
                flag = true;
                idx = i;
                continue;
            }
            if (flag) {
                if ((devc.getMldDtlId() == null || tmp.getMldDtlId().equals(devc.getMldDtlId())) && StringUtils.equals(tmp.getStatus(), TaskStatus.ST00.toString())) {
                    devc.setTaskId(tmp.getTaskId());
                    devc.setTask(tmp);
                    isGet = true;
                    devcDao.updateTaskId(devc.getDeviceId(), devc.getTaskId());
                    break;
                }
            }
        }
        if (!isGet) {
            for (int i = 0; i < idx; i++) {
                Task tmp = tasksTemp.get(i);
                if (StringUtils.equals(tmp.getStatus(), TaskStatus.ST00.toString()) && (devc.getMldDtlId() == null || tmp.getMldDtlId().equals(devc.getMldDtlId()))) {
                    devc.setTaskId(tmp.getTaskId());
                    devc.setTask(tmp);
                    isGet = true;
                    devcDao.updateTaskId(devc.getDeviceId(), devc.getTaskId());
                    break;
                }
            }
        }
        if (!isGet) {
            // 如果没有下一单, 不允许选择下一单, 所以不会运行到这一步
            if (workerType == null || workerType == 0) {

            }
            devc.setTaskId(0);
            devc.setTask(null);
        }

        devcMap.put(devc.getDeviceId(), devc);

    }
}
