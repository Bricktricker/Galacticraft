package micdoodle8.mods.galacticraft.core.client;

import org.lwjgl.glfw.GLFW;

import micdoodle8.mods.galacticraft.api.entity.IEntityNamedContainer;
import micdoodle8.mods.galacticraft.api.prefab.entity.RocketEntity;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStatsClient;
import micdoodle8.mods.galacticraft.core.networking.IgniteRocketPacket;
import micdoodle8.mods.galacticraft.core.networking.NetworkHandler;
import micdoodle8.mods.galacticraft.core.networking.OpenEntityGuiPacket;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Constants.MOD_ID_CORE, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GCKeyHandler {

	public static KeyBinding galaxyMap;
	public static KeyBinding openFuelGui;
	public static KeyBinding toggleAdvGoggles;
	public static KeyBinding spaceKey;
	
	private static boolean fuelGuiKeyWasDown = false;

	public static void initKeybinds() {
		galaxyMap = new KeyBinding("key.galacticraftcore.galaxy_map", GLFW.GLFW_KEY_M, "key.galacticraftcore.category");
		openFuelGui = new KeyBinding("key.galacticraftcore.spaceshipinv", GLFW.GLFW_KEY_F, "key.galacticraftcore.category");
		toggleAdvGoggles = new KeyBinding("key.galacticraftcore.sensortoggle", GLFW.GLFW_KEY_K, "key.galacticraftcore.category");

		ClientRegistry.registerKeyBinding(galaxyMap);
		ClientRegistry.registerKeyBinding(openFuelGui);
		ClientRegistry.registerKeyBinding(toggleAdvGoggles);

		spaceKey = Minecraft.getInstance().gameSettings.keyBindJump;
	}

	@SubscribeEvent
	public static void handleKeys(TickEvent.ClientTickEvent ev) {
		Minecraft mc = Minecraft.getInstance();
		
		if(mc.currentScreen != null) {
			return;
		}
		
		final ClientPlayerEntity player = mc.player;
		if(player == null) {
			return;
		}
		
		if(player.getRidingEntity() instanceof RocketEntity) {
			boolean spacePressed = isKeyDown(spaceKey);
			if(!spacePressed) {
				ClientProxyCore.lastSpacebarDown = false;
			}
			
			if(spacePressed && !ClientProxyCore.lastSpacebarDown) {
				NetworkHandler.INSTANCE.sendToServer(new IgniteRocketPacket());
			}
			
			ClientProxyCore.lastSpacebarDown = spacePressed;
		}
		
		if(player.getRidingEntity() instanceof IEntityNamedContainer) {
			boolean fuelGuiPressed = isKeyDown(openFuelGui);
			if(!fuelGuiPressed) {
				fuelGuiKeyWasDown = false;
			}
			if(fuelGuiPressed && !fuelGuiKeyWasDown) {
				NetworkHandler.INSTANCE.sendToServer(new OpenEntityGuiPacket());
			}
			fuelGuiKeyWasDown = fuelGuiPressed;
		}
		
		if(isKeyDown(toggleAdvGoggles)) {
			GCPlayerStatsClient stats = GCPlayerStatsClient.get(player);
			stats.setUsingAdvancedGoggles(!stats.isUsingAdvancedGoggles());
		}
		
	}

	public static boolean isKeyDown(KeyBinding keybind) {
		if(keybind.isInvalid())
			return false;

		boolean isDown = false;
		switch(keybind.getKey().getType()) {
			case KEYSYM:
				isDown = InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), keybind.getKey().getKeyCode());
				break;
			case MOUSE:
				isDown = GLFW.glfwGetMouseButton(Minecraft.getInstance().getMainWindow().getHandle(), keybind.getKey().getKeyCode()) == GLFW.GLFW_PRESS;
				break;
		}
		return isDown && keybind.getKeyConflictContext().isActive() && keybind.getKeyModifier().isActive(keybind.getKeyConflictContext());
	}

}
