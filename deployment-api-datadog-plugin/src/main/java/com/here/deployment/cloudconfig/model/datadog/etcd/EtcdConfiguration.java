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
package com.here.deployment.cloudconfig.model.datadog.etcd;

/**
 * The Class EtcdConfiguration.
 */
public class EtcdConfiguration {
	
	/** The etcd. */
	private Etcd etcd;
	
	/**
	 * The Class Etcd.
	 */
	public static class Etcd {
		
		/** The entity id. */
		private String entityId;
		
		/** The payload. */
		private String payload;
		
		/**
		 * Gets the entity id.
		 *
		 * @return the entity id
		 */
		public String getEntityId() {
			return entityId;
		}
		
		/**
		 * Sets the entity id.
		 *
		 * @param entityId the new entity id
		 */
		public void setEntityId(String entityId) {
			this.entityId = entityId;
		}
		
		/**
		 * Gets the payload.
		 *
		 * @return the payload
		 */
		public String getPayload() {
			return payload;
		}
		
		/**
		 * Sets the payload.
		 *
		 * @param payload the new payload
		 */
		public void setPayload(String payload) {
			this.payload = payload;
		}
	}

	/**
	 * Gets the etcd.
	 *
	 * @return the etcd
	 */
	public Etcd getEtcd() {
		return etcd;
	}

	/**
	 * Sets the etcd.
	 *
	 * @param etcd the new etcd
	 */
	public void setEtcd(Etcd etcd) {
		this.etcd = etcd;
	}
}