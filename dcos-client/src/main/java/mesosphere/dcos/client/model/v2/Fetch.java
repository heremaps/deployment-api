package mesosphere.dcos.client.model.v2;

import mesosphere.dcos.client.utils.ModelUtils;

public class Fetch {
	private String uri;
	private Boolean executable;
	private Boolean extract;
	private Boolean cache;
	private String outputFile;

	public Fetch() {
	}

	public Fetch(String uri) {
		this.uri = uri;	
	}
	
	public String getUri() {
		return uri;
	}


	public void setUri(String uri) {
		this.uri = uri;
	}

	public boolean isExecutable() {
		return executable;
	}

	public void setExecutable(boolean executable) {
		this.executable = executable;
	}

	public boolean isExtract() {
		return extract;
	}

	public void setExtract(boolean extract) {
		this.extract = extract;
	}

	public boolean isCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	@Override
	public String toString() {
		return ModelUtils.toString(this);
	}
}
