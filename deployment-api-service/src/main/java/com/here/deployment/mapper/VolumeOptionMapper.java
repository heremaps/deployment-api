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

import java.util.List;
import java.util.Map;
import com.here.deployment.cloudconfig.model.dcos.VolumeOption;

/**
 * The Interface VolumeOptionMapper.
 */
public interface VolumeOptionMapper {
	
	/**
	 * Convert.
	 *
	 * @param volumeOptions the volume options
	 * @return the map
	 */
	Map<String, String> convert(List<VolumeOption> volumeOptions);
}
