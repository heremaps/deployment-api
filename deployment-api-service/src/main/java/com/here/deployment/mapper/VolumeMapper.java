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

/**
 * The Interface VolumeMapper.
 */
@Mapper(componentModel = "spring", uses = { VolumeOptionMapper.class })
public interface VolumeMapper {
	
	/**
	 * Convert.
	 *
	 * @param volume the volume
	 * @return the mesosphere.dcos.client.model.v 2 . volume
	 */
	mesosphere.dcos.client.model.v2.Volume convert(com.here.deployment.cloudconfig.model.dcos.Volume volume);
}

