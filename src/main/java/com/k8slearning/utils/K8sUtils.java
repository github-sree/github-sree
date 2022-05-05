package com.k8slearning.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class K8sUtils {

	@Autowired
	ModelMapper modelMapper;
	
	public <S, T> List<T> mapStream(Stream<S> source, Class<T> targetClass) {
		return source.map(element -> modelMapper.map(element, targetClass)).collect(Collectors.toList());
	}
}
