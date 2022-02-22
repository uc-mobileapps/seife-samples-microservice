package com.uc_mobileapps.spring;

import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Transforms vaadin query sort order to spring and hibernate query instances.
 */
public class QueryUtil {

	public static List<Order> convertToSpringSortOrder(Stream<QuerySortOrder> sortOrders) {
		List<Order> sortOrder = sortOrders
				.map(querySortOrder -> (querySortOrder.getDirection() == SortDirection.ASCENDING)
						? Order.asc(querySortOrder.getSorted())
						: Order.desc(querySortOrder.getSorted()))
				.collect(Collectors.toList());
		return sortOrder;
	}
	
	public static Pageable createPageRequest(Query<?, ?> query) {
		Optional<?> filter = query.getFilter();
		List<Order> sortOrder = QueryUtil.convertToSpringSortOrder(query.getSortOrders().stream());

		int page = query.getOffset() / query.getLimit();
		return PageRequest.of(page, query.getLimit(), Sort.by(sortOrder));
	}

}
