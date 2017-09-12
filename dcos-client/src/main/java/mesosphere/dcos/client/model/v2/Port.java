/*
 * 
 */
package mesosphere.dcos.client.model.v2;

import java.util.HashMap;
import java.util.Map;
import mesosphere.dcos.client.utils.ModelUtils;

public class Port {
	private Integer containerPort;
	private Integer hostPort;
	private Integer servicePort;
	private String protocol;
	private Map<String, String> labels;
	
	public Port() {
		labels = new HashMap<>();
	}
	
	public Integer getContainerPort() {
		return containerPort;
	}

	public void setContainerPort(Integer containerPort) {
		this.containerPort = containerPort;
	}

	public Integer getHostPort() {
		return hostPort;
	}

	public void setHostPort(Integer hostPort) {
		this.hostPort = hostPort;
	}

	public Integer getServicePort() {
		return servicePort;
	}

	public void setServicePort(Integer servicePort) {
		this.servicePort = servicePort;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Map<String, String> getLabels() {
		return labels;
	}
	
	public void addLabel(String key, String value) {
		if (this.labels == null) {
			this.labels = new HashMap<>();
		}		
		labels.put(key, value);
	}

	@Override
	public String toString() {
		return ModelUtils.toString(this);
	}
}