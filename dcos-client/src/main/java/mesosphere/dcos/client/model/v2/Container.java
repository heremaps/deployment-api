package mesosphere.dcos.client.model.v2;

import java.util.ArrayList;
import java.util.Collection;
import mesosphere.dcos.client.utils.ModelUtils;

public class Container {
	private String type;
	private Docker docker;
	private Collection<Volume> volumes;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Docker getDocker() {
		return docker;
	}

	public void setDocker(Docker docker) {
		this.docker = docker;
	}

	public Collection<Volume> getVolumes() {
		return volumes;
	}

	public void setVolumes(Collection<Volume> volumes) {
		this.volumes = volumes;
	}
	
	public void addVolume(Volume volume) {
		if (this.volumes == null) {
			this.volumes = new ArrayList<>();		
		}
		this.volumes.add(volume);
	}

	@Override
	public String toString() {
		return ModelUtils.toString(this);
	}
}
