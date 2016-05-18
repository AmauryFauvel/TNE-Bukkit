package com.github.tnerevival.core.shops;

import java.util.UUID;

public class ShareEntry {
	
	private UUID shareOwner;
	private double percent = 0.0;
	
	public ShareEntry(UUID shareOwner) {
		this.shareOwner = shareOwner;
	}
	
	public ShareEntry(UUID shareOwner, double percent) {
		this.shareOwner = shareOwner;
		this.percent = percent;
	}

	public UUID getShareOwner() {
		return shareOwner;
	}

	public void setShareOwner(UUID shareOwner) {
		this.shareOwner = shareOwner;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}
}