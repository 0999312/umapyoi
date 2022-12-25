package net.tracen.umapyoi.registry.training;

import net.tracen.umapyoi.api.UmaStatusUtils.StatusType;
import net.tracen.umapyoi.capability.IUmaCapability;
import net.tracen.umapyoi.registry.umadata.UmaStatus;

public class StatusSupport extends TrainingSupport {
    private final StatusType statusType;
    public StatusSupport(StatusType status){
        super();
        this.statusType = status;
    }
    
    @Override
    public void applySupport(IUmaCapability cap, SupportStack stack) {
        UmaStatus status = cap.getUmaStatus();
        status.property()[statusType.getId()] = Math.min(12, status.property()[statusType.getId()] + stack.getLevel());
    }
    
    
}
