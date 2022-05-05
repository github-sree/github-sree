package com.k8slearning.api;

import java.util.List;

import lombok.Data;

@Data
public class PagingResponseApi<T> {
	private Long totalCount;
	private Integer pageNo;
	private Integer totalPages;
	private Integer pageSize;
	private List<T> rows;
}
