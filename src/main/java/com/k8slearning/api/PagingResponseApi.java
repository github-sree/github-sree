package com.k8slearning.api;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class PagingResponseApi<T> extends ResponseApi {
	private Long totalCount;
	private Integer pageNo;
	private Integer totalPages;
	private Integer pageSize;
	private List<T> rows;

	@Override
	public void clear() {
		setTotalCount(null);
		setPageNo(null);
		setTotalPages(null);
		setPageSize(null);
		setRows(null);
	}
}
