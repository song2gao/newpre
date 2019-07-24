package com.cic.domain;

public class PomsCalculateTerminalDevice {

	private String id;
	
	private PomsAssembledTerminalDevice pomsAssembledTerminalDevice;

	public PomsAssembledTerminalDevice getPomsAssembledTerminalDevice() {
		return pomsAssembledTerminalDevice;
	}

	public void setPomsAssembledTerminalDevice(
			PomsAssembledTerminalDevice pomsAssembledTerminalDevice) {
		this.pomsAssembledTerminalDevice = pomsAssembledTerminalDevice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
