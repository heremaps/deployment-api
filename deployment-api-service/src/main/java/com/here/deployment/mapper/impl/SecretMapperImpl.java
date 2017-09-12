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
package com.here.deployment.mapper.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import com.here.deployment.mapper.SecretMapper;

/**
 * The Class SecretMapperImpl.
 */
@Component
public class SecretMapperImpl implements SecretMapper {
	
	/* (non-Javadoc)
	 * @see com.here.dcos.deployment.mapper.SecretMapper#convert(java.util.List)
	 */
	@Override
	public Map<String, mesosphere.dcos.client.model.v2.Secret> convert(List<com.here.deployment.cloudconfig.model.dcos.Secret> secrets) {
		
		if (CollectionUtils.isNotEmpty(secrets)) {
			Map<String, mesosphere.dcos.client.model.v2.Secret> map = new HashMap<>();
			
			secrets.forEach(s -> {
				map.put(s.getName(), new mesosphere.dcos.client.model.v2.Secret(s.getSource()));
			});
			
			return map;
		}
		
		return null;
	}
}
	


