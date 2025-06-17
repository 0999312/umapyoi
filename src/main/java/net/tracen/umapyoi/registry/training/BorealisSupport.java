package net.tracen.umapyoi.registry.training;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;

public class BorealisSupport extends TrainingSupport {

    public BorealisSupport() {
        super();
    }

    @Override
    public Component getDescription(SupportStack stack) {
    	return this.getDescription();
    }
    
//    @Override
//    public boolean applySupport(ItemStack soul, RandomSource rand, SupportStack stack) {
//        var chance = rand.nextFloat();
//        if(chance < UmapyoiConfig.ACUPUNCTUIST_SUPPORT_CHANCE.get()) 
//        	this.applySuccessEvent(soul, rand);
//        else 
//        	UmaStatusUtils.downMotivation(soul);
//        
//        return true;
//    }
//    
//    public void applySuccessEvent(ItemStack soul, RandomSource rand) {
//		switch (AcupuncturistEventTypes.getRandomType(rand)) {
//		case STATUS ->{
//	    	for(int i = 0; i < 5;i++) {
//	            UmaSoulUtils.getMaxProperty(soul)[i] = Math.min(39, UmaSoulUtils.getMaxProperty(soul)[i] + 1);
//				if (UmaSoulUtils.getMaxProperty(soul)[i] > UmaSoulUtils.getProperty(soul)[i]) {
//		            UmaSoulUtils.getProperty(soul)[i] = Math.min(
//		                    UmaSoulUtils.getMaxProperty(soul)[i],
//		                    UmaSoulUtils.getProperty(soul)[i] + 1);
//		        }
//	    	}
//		}
//		
//		case PHYSIQUE ->{
//        	int phy = Math.min(UmaSoulUtils.getPhysique(soul) + 1, 5);
//        	UmaSoulUtils.setPhysique(soul, phy);
//		}
//		
//		case MOTIVATION ->{
//			UmaStatusUtils.addMotivation(soul);
//		}
//		
//		default ->
//		throw new IllegalArgumentException("Unexpected value: " + AcupuncturistEventTypes.getRandomType(rand));
//		}
//	}
    
    public static enum AcupuncturistEventTypes {
		STATUS, PHYSIQUE, MOTIVATION;
		
		public static AcupuncturistEventTypes getRandomType(RandomSource rand) {
			return AcupuncturistEventTypes.values()[rand.nextInt(AcupuncturistEventTypes.values().length)];
		}
	}
}
