package io.beatbonds.shared;

import org.springframework.stereotype.Component;

@Component
public class SharedData {
	
	private String sharedToken;

	public SharedData() {
		super();
	}

	public SharedData(String sharedToken) {
		super();
		this.sharedToken = sharedToken;
	}

	public String getSharedToken() {
		return sharedToken;
	}

	public void setSharedToken(String sharedToken) {
		this.sharedToken = sharedToken;
	}

	@Override
	public String toString() {
		return "SharedData [sharedToken=" + sharedToken + "]";
	}
}
