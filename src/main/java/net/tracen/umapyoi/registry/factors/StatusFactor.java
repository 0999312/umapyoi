package net.tracen.umapyoi.registry.factors;

import net.tracen.umapyoi.capability.IUmaCapability;
import net.tracen.umapyoi.registry.umadata.UmaStatus;
import net.tracen.umapyoi.utils.UmaStatusUtils.StatusType;

public class StatusFactor extends UmaFactor {
    
    private final StatusType statusType;
    public StatusFactor(StatusType status){
        super(FactorType.STATUS);
        this.statusType = status;
    }

    @Override
    public void applyFactor(IUmaCapability cap, UmaFactorStack stack) {
        UmaStatus status = cap.getUmaStatus();
        status.property()[statusType.getId()] = Math.min(12, 
                status.property()[statusType.getId()] + stack.getLevel());
    }

}
