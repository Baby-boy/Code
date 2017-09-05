/*
 * Copyright (c) 2005, 2014 vacoor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.glanway.iclock.entity;

import java.io.Serializable;
import java.util.Date;

import org.ponly.webbase.entity.Auditable;
import org.ponly.webbase.entity.Persistable;

/**
 * @author vacoor
 */
public abstract class BaseEntity implements Persistable<Long>, Auditable<Long, Long>, Serializable {

	private static final long serialVersionUID = -1775772660674482854L;

	protected Long id; // ID

	protected Date batchDate; // 执行日

	protected Long createdBy; // 创建人

	protected Date createdDate; // 创建时间

	protected Long lastModifiedBy; // 最后修改人

	protected Date lastModifiedDate; // 最后修改时间

	protected Long creProId; // 创建程序ID

	protected Long modProId; // 跟新程序ID

	protected String deleted;// 是否删除(0:否, 1:是)

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Date getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}

	@Override
	public Date getCreatedDate() {
		return createdDate;
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@Override
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	@Override
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		} else if (null != other && this.getClass() == other.getClass()) {
			BaseEntity that = (BaseEntity) other;
			Serializable id = this.getId();
			Serializable thatId = that.getId();
			return null != id && null != thatId && (id == thatId || id.equals(thatId));
		} else {
			return false;
		}
	}

	public int hashCode() {
		byte result = 1;
		Long id = this.getId();
		return 31 * result + (id == null ? 0 : id.hashCode());
	}

	protected Object clone() throws CloneNotSupportedException {
		BaseEntity entity = BaseEntity.class.cast(super.clone());
		entity.setId(null);
		return entity;
	}

	public Long getCreProId() {
		return creProId;
	}

	public void setCreProId(Long creProId) {
		this.creProId = creProId;
	}

	public Long getModProId() {
		return modProId;
	}

	public void setModProId(Long modProId) {
		this.modProId = modProId;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

}
