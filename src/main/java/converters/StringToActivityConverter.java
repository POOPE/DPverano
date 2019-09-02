/*
 * StringToActivityConverter.java
 *
 * Copyright (C) 2019 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Activity;
import repositories.ActivityRepository;

@Component
@Transactional
public class StringToActivityConverter implements Converter<String, Activity> {

	@org.springframework.beans.factory.annotation.Autowired
	ActivityRepository ActivityRepository;


	@Override
	public Activity convert(final String text) {
		Activity result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.ActivityRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
