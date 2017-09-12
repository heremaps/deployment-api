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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.here.deployment.cloudconfig.model.dcos.Label;
import com.here.deployment.mapper.LabelMapper;
import com.here.deployment.mapper.LabelTemplateProvider;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

/**
 * The Class LabelMapperImpl.
 */
@Component
public class LabelMapperImpl implements LabelMapper {
	
	/** The label template provider. */
	@Autowired
	LabelTemplateProvider labelTemplateProvider;
	
	/* (non-Javadoc)
	 * @see com.here.dcos.deployment.mapper.LabelMapper#convert(java.util.List)
	 */
	public Map<String, String> convert(List<Label> labels) {
		
		if (CollectionUtils.isNotEmpty(labels)) {
			Map<String, String> map = new HashMap<>();
			
			labels.forEach(a -> {
				if (StringUtils.isNotEmpty(a.getTemplate())) {
					map.put(a.getKey(), labelTemplateProvider.parse(a.getTemplate(), a.getArgs()));
				} else {
					map.put(a.getKey(), a.getValue());
				}
			});
			
			return map;
		}
		
		return null;
	}
}
	


