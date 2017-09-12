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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import com.here.deployment.cloudconfig.model.dcos.Constraint;
import com.here.deployment.mapper.ConstraintMapper;

/**
 * The Class ConstraintMapperImpl.
 */
@Component
public class ConstraintMapperImpl implements ConstraintMapper {
	
	/** The Constant LIST_SIZE. */
	static final int LIST_SIZE = 3;
	
	/** The Constant EMPTY_STRING. */
	static final String EMPTY_STRING = "";
	
	/* (non-Javadoc)
	 * @see com.here.dcos.deployment.mapper.ConstraintMapper#convert(java.util.List)
	 */
	@Override
	public List<List<String>> convert(List<Constraint> constraints) {
		if (CollectionUtils.isNotEmpty(constraints)) {
			List<List<String>> outerList = new ArrayList<List<String>>();
			
			constraints.forEach(c -> {
				List<String> innerList = new ArrayList<String>(LIST_SIZE);
				Stream.<String>of(c.getAttribute(), c.getOperator(), c.getValue()).forEach(s -> {
					if (s == null) {
						innerList.add(EMPTY_STRING);
					} else {
						innerList.add(s);
					}
				});
				outerList.add(innerList);
			});
			
			return outerList;
		}
		
		return null;
	}
}
	


