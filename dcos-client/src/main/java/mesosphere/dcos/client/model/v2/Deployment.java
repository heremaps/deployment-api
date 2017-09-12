package mesosphere.dcos.client.model.v2;

import java.util.Collection;
import java.util.List;
import mesosphere.dcos.client.utils.ModelUtils;

public class Deployment {
	private Collection<String> affectedApps;
	private String id;
	private List<ActionList> steps;
	private Collection<Action> currentActions;
	private String version;
	private Integer currentStep;
	private Integer totalSteps;
	
	public Collection<String> getAffectedApps() {
		return affectedApps;
	}

	public void setAffectedApps(Collection<String> affectedApps) {
		this.affectedApps = affectedApps;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ActionList> getSteps() {
		return steps;
	}

	public void setSteps(List<ActionList> steps) {
		this.steps = steps;
	}

	public Collection<Action> getCurrentActions() {
		return currentActions;
	}

	public void setCurrentActions(Collection<Action> currentActions) {
		this.currentActions = currentActions;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(Integer currentStep) {
		this.currentStep = currentStep;
	}

	public Integer getTotalSteps() {
		return totalSteps;
	}

	public void setTotalSteps(Integer totalSteps) {
		this.totalSteps = totalSteps;
	}

	public class Action {
		private String action;
		private String app;

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public String getApp() {
			return app;
		}

		public void setApp(String app) {
			this.app = app;
		}

		@Override
		public String toString() {
			return ModelUtils.toString(this);
		}
	}
	
	public class ActionList {
		private List<Action> actions;
		
		public List<Action> getActions() {
			return actions;
		}

		public void setActions(List<Action> actions) {
			this.actions = actions;
		}

		@Override
		public String toString() {
			return ModelUtils.toString(this);
		}
	}

	@Override
	public String toString() {
		return ModelUtils.toString(this);
	}
}
