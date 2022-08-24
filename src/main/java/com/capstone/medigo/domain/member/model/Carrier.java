package com.capstone.medigo.domain.member.model;

public enum Carrier {
	KT(1),
	SKT(2),
	LG(3);

	private final int carrierNumber;

	Carrier(int carrierNumber) {
		this.carrierNumber = carrierNumber;
	}
}
