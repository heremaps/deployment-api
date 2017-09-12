package mesosphere.dcos.client.model.v2;

import java.util.List;
import mesosphere.dcos.client.utils.ModelUtils;

public class GetAppsResponse {
	private List<App> apps;

	public List<App> getApps() {
		return apps;
	}

	public void setApps(List<App> apps) {
		this.apps = apps;
	}

	@Override
	public String toString() {
		return ModelUtils.toString(this);
	}

}
