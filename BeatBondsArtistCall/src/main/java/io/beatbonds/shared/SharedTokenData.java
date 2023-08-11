package io.beatbonds.shared;

import org.springframework.stereotype.Component;

@Component
public class SharedTokenData {
	
	private String sharedToken;

	public SharedTokenData() {
		super();
	}

	public SharedTokenData(String sharedToken) {
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
