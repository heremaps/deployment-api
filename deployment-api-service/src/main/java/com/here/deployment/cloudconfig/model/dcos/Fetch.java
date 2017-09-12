/*
* Copyright (c) 2017 HERE Europe B.V.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.here.deployment.cloudconfig.model.dcos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class Fetch.
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fetch {
	
	/** The uri. */
	protected String uri;
	
	/** The executable. */
	protected Boolean executable;
	
	/** The extract. */
	protected Boolean extract;
	
	/** The cache. */
	protected Boolean cache;
	
	/** The output file. */
	protected String outputFile;
	
	/**
	 * Gets the uri.
	 *
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}
	
	/**
	 * Sets the uri.
	 *
	 * @param uri the new uri
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	/**
	 * Gets the executable.
	 *
	 * @return the executable
	 */
	public Boolean getExecutable() {
		return executable;
	}
	
	/**
	 * Sets the executable.
	 *
	 * @param executable the new executable
	 */
	public void setExecutable(Boolean executable) {
		this.executable = executable;
	}
	
	/**
	 * Gets the extract.
	 *
	 * @return the extract
	 */
	public Boolean getExtract() {
		return extract;
	}
	
	/**
	 * Sets the extract.
	 *
	 * @param extract the new extract
	 */
	public void setExtract(Boolean extract) {
		this.extract = extract;
	}
	
	/**
	 * Gets the cache.
	 *
	 * @return the cache
	 */
	public Boolean getCache() {
		return cache;
	}
	
	/**
	 * Sets the cache.
	 *
	 * @param cache the new cache
	 */
	public void setCache(Boolean cache) {
		this.cache = cache;
	}
	
	/**
	 * Gets the output file.
	 *
	 * @return the output file
	 */
	public String getOutputFile() {
		return outputFile;
	}
	
	/**
	 * Sets the output file.
	 *
	 * @param outputFile the new output file
	 */
	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}


}
