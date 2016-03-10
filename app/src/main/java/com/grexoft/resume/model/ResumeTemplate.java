package com.grexoft.resume.model;

import com.grexoft.resume.inappbilling.SkuDetails;

public class ResumeTemplate {	
	
	private String title;
	
	private boolean isFree;
	
	private boolean canShowImage;
	
	public SkuDetails skuDetails;
	
	private int templateImageId;
	
	private boolean isPurchased;

	public ResumeTemplate(String title, boolean isFree, boolean canShowImage, int templateImageId, boolean isPurchased,
			SkuDetails skuDetails) {
		super();
		this.title = title;
		this.isFree = isFree;
		this.canShowImage = canShowImage;
		this.skuDetails = skuDetails;
		this.templateImageId = templateImageId;
		this.isPurchased = isPurchased;
	}

	public boolean isCanShowImage() {
		return canShowImage;
	}

	public void setCanShowImage(boolean canShowImage) {
		this.canShowImage = canShowImage;
	}

	public String getTitle() {
		return title;
	}

	public boolean isFree() {
		return isFree;
	}
	
	public int getTemplateImageId() {
		return templateImageId;
	}
	
	public boolean isPurchased() {
		return isPurchased;
	}
	
	public void setPurchased(boolean isPurchased) {
		this.isPurchased = isPurchased;
	}

}
