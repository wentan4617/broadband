package com.tm.broadband.model;

import java.io.Serializable;

public class Provision implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer pendingSum;
	private Integer paidSum;
	private Integer pendingWarningSum;
	private Integer orderingPendingSum;
	private Integer orderingPaidSum;
	private Integer rfsSum;
	private Integer usingSum;
	private Integer overflowSum;
	private Integer suspendedSum;
	private Integer waitingForDisconnectSum;
	private Integer disconnectedSum;
	private Integer voidSum;
	private Integer upgradeSum;

	public Integer getPendingSum() {
		return pendingSum;
	}

	public void setPendingSum(Integer pendingSum) {
		this.pendingSum = pendingSum;
	}

	public Integer getPaidSum() {
		return paidSum;
	}

	public void setPaidSum(Integer paidSum) {
		this.paidSum = paidSum;
	}

	public Integer getPendingWarningSum() {
		return pendingWarningSum;
	}

	public void setPendingWarningSum(Integer pendingWarningSum) {
		this.pendingWarningSum = pendingWarningSum;
	}

	public Integer getOrderingPendingSum() {
		return orderingPendingSum;
	}

	public void setOrderingPendingSum(Integer orderingPendingSum) {
		this.orderingPendingSum = orderingPendingSum;
	}

	public Integer getOrderingPaidSum() {
		return orderingPaidSum;
	}

	public void setOrderingPaidSum(Integer orderingPaidSum) {
		this.orderingPaidSum = orderingPaidSum;
	}

	public Integer getRfsSum() {
		return rfsSum;
	}

	public void setRfsSum(Integer rfsSum) {
		this.rfsSum = rfsSum;
	}

	public Integer getUsingSum() {
		return usingSum;
	}

	public void setUsingSum(Integer usingSum) {
		this.usingSum = usingSum;
	}

	public Integer getOverflowSum() {
		return overflowSum;
	}

	public void setOverflowSum(Integer overflowSum) {
		this.overflowSum = overflowSum;
	}

	public Integer getSuspendedSum() {
		return suspendedSum;
	}

	public void setSuspendedSum(Integer suspendedSum) {
		this.suspendedSum = suspendedSum;
	}

	public Integer getWaitingForDisconnectSum() {
		return waitingForDisconnectSum;
	}

	public void setWaitingForDisconnectSum(Integer waitingForDisconnectSum) {
		this.waitingForDisconnectSum = waitingForDisconnectSum;
	}

	public Integer getDisconnectedSum() {
		return disconnectedSum;
	}

	public void setDisconnectedSum(Integer disconnectedSum) {
		this.disconnectedSum = disconnectedSum;
	}

	public Integer getVoidSum() {
		return voidSum;
	}

	public void setVoidSum(Integer voidSum) {
		this.voidSum = voidSum;
	}

	public Integer getUpgradeSum() {
		return upgradeSum;
	}

	public void setUpgradeSum(Integer upgradeSum) {
		this.upgradeSum = upgradeSum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
