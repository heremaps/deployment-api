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

import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.here.deployment.configproperties.LabelTemplateProperties;
import com.here.deployment.mapper.LabelTemplateProvider;
import net.logstash.logback.encoder.org.apache.commons.lang.StringEscapeUtils;

/**
 * The Class LabelTemplateProviderImpl.
 */
@Service
public class LabelTemplateProviderImpl implements LabelTemplateProvider {
	
	/** The Constant TEMPLATE_PLACEHOLDER_PATTERN. */
	static final Pattern TEMPLATE_PLACEHOLDER_PATTERN = Pattern.compile("(?<![0-9+])(\\{[a-zA-Z]+\\})");
	
	/** The label template properties. */
	@Autowired
	LabelTemplateProperties labelTemplateProperties;
	
	/* (non-Javadoc)
	 * @see com.here.dcos.deployment.mapper.LabelTemplateProvider#parse(java.lang.String, java.util.List)
	 */
	@Override
	public String parse(String templateName, List<String> args) throws IllegalArgumentException {
		String template = labelTemplateProperties.get(templateName);
		if (template == null) {
			throw new IllegalArgumentException("Template not found: " + templateName);
		}
		
		if (CollectionUtils.isEmpty(args)) {
			return StringEscapeUtils.unescapeJava(template);
		}

		Matcher matcher = TEMPLATE_PLACEHOLDER_PATTERN.matcher(template);
		StringBuilder stringBuilder = new StringBuilder();
		int pos = 0;
		while(matcher.find()) {
			stringBuilder.append(template, pos, matcher.start());
		    pos = matcher.end();
		    stringBuilder.append("'" + matcher.group(0) + "'");
		}
		
		stringBuilder.append(template, pos, template.length());
		
		String returnString = StringEscapeUtils.unescapeJava(stringBuilder.toString());
		return MessageFormat.format(returnString, (Object[]) args.toArray(new String[args.size()]));
	}
}
