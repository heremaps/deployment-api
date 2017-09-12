package mesosphere.dcos.client.model.v2;

import mesosphere.dcos.client.utils.ModelUtils;

public class Result {
	private String version;
	private String deploymentId;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	@Override
	public String toString() {
		return ModelUtils.toString(this);
	}
}
