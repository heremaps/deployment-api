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
package com.here.deployment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.here.deployment.cloudconfig.model.dcos.Marathon;
import mesosphere.dcos.client.model.v2.App;

/**
 * The Interface MarathonMapper.
 */
@Mapper(componentModel = "spring", 
uses = { FetchMapper.class, HealthCheckMapper.class, ConstraintMapper.class, SecretMapper.class, 
		LabelMapper.class, ContainerMapper.class, UpgradeStrategyMapper.class })
public interface MarathonMapper {
	
	/**
	 * Convert.
	 *
	 * @param marathon the marathon
	 * @return the app
	 */
	@Mapping(source = "appId", target = "id")
	App convert(Marathon marathon);
}

