package com.krishagni.catissueplus.core.administrative.events;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.krishagni.catissueplus.core.administrative.domain.DistributionOrderItem;
import com.krishagni.catissueplus.core.biospecimen.events.SpecimenInfo;

public class DistributionOrderItemDetail implements Serializable {
	private Long id;

	private String orderName;
	
	private SpecimenInfo specimen;
	
	private BigDecimal quantity;

	private BigDecimal cost;

	private String label;

	private StorageLocationSummary holdingLocation;

	private boolean printLabel;
	
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public SpecimenInfo getSpecimen() {
		return specimen;
	}

	public void setSpecimen(SpecimenInfo specimen) {
		this.specimen = specimen;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public StorageLocationSummary getHoldingLocation() {
		return holdingLocation;
	}

	public void setHoldingLocation(StorageLocationSummary holdingLocation) {
		this.holdingLocation = holdingLocation;
	}

	public boolean isPrintLabel() {
		return printLabel;
	}

	public void setPrintLabel(boolean printLabel) {
		this.printLabel = printLabel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static DistributionOrderItemDetail from(DistributionOrderItem orderItem) {
		DistributionOrderItemDetail detail = new DistributionOrderItemDetail();
		detail.setId(orderItem.getId());
		detail.setOrderName(orderItem.getOrder().getName());
		detail.setQuantity(orderItem.getQuantity());
		detail.setSpecimen(SpecimenInfo.from(orderItem.getSpecimen()));
		detail.setCost(orderItem.getCost());
		detail.setLabel(orderItem.getLabel());
		detail.setStatus(orderItem.getStatus().name());
		return detail;
	}
	
	public static List<DistributionOrderItemDetail> from(Collection<DistributionOrderItem> orderItems) {
		return orderItems.stream().map(DistributionOrderItemDetail::from).collect(Collectors.toList());
	}
}
