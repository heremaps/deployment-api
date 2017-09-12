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
import com.here.deployment.cloudconfig.model.dcos.VolumeOption;
import com.here.deployment.mapper.VolumeOptionMapper;

/**
 * The Class VolumeOptionMapperImpl.
 */
@Component
public class VolumeOptionMapperImpl implements VolumeOptionMapper {
	
	/* (non-Javadoc)
	 * @see com.here.dcos.deployment.mapper.VolumeOptionMapper#convert(java.util.List)
	 */
	@Override
	public Map<String, String> convert(List<VolumeOption> volumeOptions) {
		
		if (CollectionUtils.isNotEmpty(volumeOptions)) {
			Map<String, String> map = new HashMap<>();
			
			volumeOptions.forEach(v -> {
				map.put(v.getKey(), v.getValue());
			});
			
			return map;
		}
		
		return null;
	}
}
	


