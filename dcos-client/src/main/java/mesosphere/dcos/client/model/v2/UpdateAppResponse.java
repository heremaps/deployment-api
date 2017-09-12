package mesosphere.dcos.client.model.v2;

import mesosphere.dcos.client.utils.ModelUtils;

public class UpdateAppResponse {

	private String deploymentId;
	private String version;
	
	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return ModelUtils.toString(this);
	}
}
