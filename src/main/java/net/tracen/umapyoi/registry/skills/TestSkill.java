package net.tracen.umapyoi.registry.skills;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.tracen.umapyoi.Umapyoi;

public class TestSkill extends UmaSkill {

    public TestSkill(Builder builder) {
        super(builder);
    }

    @Override
    public void applySkill(Level level, Player user) {
        Umapyoi.getLogger().info(String.format("%s is a test skill!", this.toString()));
        user.displayClientMessage(new TranslatableComponent("%s is a test skill!", this.toString()), true);
    }
}
