package micdoodle8.mods.galacticraft.core.util;

import net.minecraft.util.IntReferenceHolder;

/**
 * Use this wrapper to resest the last known value. This is needed to resend the data to a new client. Even when the server data has not changed.
 */
public class IntReferenceWrapper extends IntReferenceHolder {

	private final IntReferenceHolder inner;
	
	public IntReferenceWrapper(IntReferenceHolder inner) {
		this.inner = inner;
	}
	
	@Override
	public int get() {
		return this.inner.get();
	}

	@Override
	public void set(int value) {
		this.inner.set(value);
	}

}
