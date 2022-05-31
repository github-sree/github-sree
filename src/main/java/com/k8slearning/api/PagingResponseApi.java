package com.k8slearning.api;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PagingResponseApi<T> extends ResponseApi {
	private Long totalCount;
	private Integer pageNo;
	private Integer totalPages;
	private Integer pageSize;
	private List<T> rows;
}
