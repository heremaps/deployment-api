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
package com.here.deployment.dcos.service.impl;

import org.springframework.stereotype.Service;
import com.here.deployment.dcos.service.DcosClientService;
import mesosphere.dcos.client.Dcos;
import mesosphere.dcos.client.DcosClient;
import mesosphere.dcos.client.exception.DcosException;

/**
 * The Class DcosClientServiceImpl.
 */
@Service
public class DcosClientServiceImpl implements DcosClientService {

	/* (non-Javadoc)
	 * @see com.here.techops.deployment.dcos.service.DcosClientService#dcosClient(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Dcos dcosClient(String url, String username, String password) throws DcosException {
		return DcosClient.getInstance(url, username, password);
	}
}
