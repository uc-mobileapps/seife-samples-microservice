package com.uc_mobileapps.examples.cloud.ui.forms;

import com.uc_mobileapps.examples.entities.Trip;
import com.uc_mobileapps.examples.filters.TripFilterVO;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.weebmeister.seife.anno.SeifeBinding;
import com.weebmeister.seife.anno.SeifeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * A view that shows a list of trips with sortable and filterable columns.
 */
@SpringComponent
@Scope(SCOPE_PROTOTYPE)
@CssImport("./styles/shared-styles.css")
@SeifeForm(forClass = Trip.class, generatorOptions = {"grid.gridClass"})
public class TripGrid extends Grid<Trip> {

    /**
     * The recommended way is to name the fields just as in e.g. {@link Trip}, fields are then bound implicitly.
     */
    @SeifeBinding(bindingOptions = {SeifeBinding.DATA_SORTABLE, SeifeBinding.DATA_FILTERABLE})
    private Column<Trip> source;

    /**
     * However if necessary it can be explicitly bound via the name.
     */
    @SeifeBinding(value = "destination", bindingOptions = {SeifeBinding.DATA_SORTABLE, SeifeBinding.DATA_FILTERABLE})
    private Column<Trip> dest;

    /**
     * You can navigate fields with the dot-notation, even if they may contain null and are not mandatory.
     */
    @SeifeBinding(value = "tripStart", bindingOptions = {SeifeBinding.DATA_SORTABLE})
    private Column<Trip> startTime;

	/**
	 * Seife will generate the constraints on the input defined in the {@link Trip} for you.
	 */
	@SeifeBinding(value = "address.street", bindingOptions = {SeifeBinding.DATA_SORTABLE})
	private Column<Trip> street;

	/**
	 * Modern Java date and time api is supported.
	 */
	@SeifeBinding(value = "visitedCustomer", bindingOptions = {SeifeBinding.DATA_SORTABLE})
    private Column<Trip> customerName;

	@Autowired
    public TripGrid() {
		setWidthFull();
		setupFilters();
		setupColumns();
		setupHeaders();
	}

	private String getFieldName(String technicalName) {
		// localized header
		return getTranslation(TripGrid.class.getCanonicalName() + '.' + technicalName);
	}

	/**
	 * Get current filter.
	 * @return filter value object
	 */
	public TripFilterVO getFilter() {
		return filter;
	}

	public void filterSource(String source) {
		// scrolling to an item in a backend backed environment is not trivial,
		// see e.g. https://cookbook.vaadin.com/scroll-to-item-in-tree-grid
		// here a filter is set to make the new item visible
		sourceFilter.setValue(source);
	}

    // @seife automatically generated:

	/** Column key of source. */
	public static final String COLUMN_SOURCE = "source";

	/** Column key of destination. */
	public static final String COLUMN_DESTINATION = "destination";

	/** Column key of tripStart. */
	public static final String COLUMN_TRIP_START = "tripStart";

	/** Column key of address. */
	public static final String COLUMN_ADDRESS = "address";

	/** Column key of visitedCustomer. */
	public static final String COLUMN_VISITED_CUSTOMER = "visitedCustomer";

	/**
	 * Keeps the filtering state of the components
	 */
	private final TripFilterVO filter = new TripFilterVO();
	
	public ConfigurableFilterDataProvider<Trip, String, TripFilterVO> createDataProvider(
			CallbackDataProvider.FetchCallback<Trip, TripFilterVO> fetchCallback,
			CallbackDataProvider.CountCallback<Trip, TripFilterVO> countCallback) {

		ConfigurableFilterDataProvider<Trip, String, TripFilterVO> filteredProvider = DataProvider
				.fromFilteringCallbacks(fetchCallback, countCallback)
				.withConfigurableFilter((filterText, entity) -> filter.withText(filterText));
		
		filteredProvider.setFilter(filter);
		return filteredProvider;
	}

	/**
	 * Binds the columns, can be called e.g. from @PostConstruct.
	 */
	protected final void setupColumns() {
		source = addColumn(entity -> entity.getSource());
		source.setKey(COLUMN_SOURCE);
		source.setId("source");
		dest = addColumn(entity -> entity.getDestination());
		dest.setKey(COLUMN_DESTINATION);
		dest.setId("dest");
		startTime = addColumn(entity -> entity.getTripStart());
		startTime.setKey(COLUMN_TRIP_START);
		startTime.setId("startTime");
		street = addColumn(TemplateRenderer
				.<Trip>of("<span class=\"tertiary-text\">[[item.street]] [[item.houseNumber]]</span>")
				.withProperty("street", trip -> trip.getAddress().getStreet())
				.withProperty("houseNumber", trip -> trip.getAddress().getHouseNumber())
		);
		street.setKey(COLUMN_ADDRESS);
		street.setId("street");
		customerName = addColumn(TemplateRenderer
				.<Trip>of("<i>[[item.firstname]] [[item.lastname]]</i>")
				.withProperty("firstname", trip -> trip.getVisitedCustomer().getFirstName())
				.withProperty("lastname", trip -> trip.getVisitedCustomer().getLastName())
		);
		customerName.setKey(COLUMN_VISITED_CUSTOMER);
		customerName.setId("customerName");
 	}

	/**
	 * Filter control of source in the grid header.
	 */
	private TextField sourceFilter;

	/**
	 * Filter control of dest in the grid header.
	 */
	private TextField destFilter;

	/**
	 * Sets up the filter UI elements. Must be called before setupHeaders.
	 */
	protected final void setupFilters() {
		sourceFilter = new TextField();
		sourceFilter.setClearButtonVisible(true);
		sourceFilter.setValueChangeMode(ValueChangeMode.LAZY);
		sourceFilter.setPlaceholder(getFieldName(COLUMN_SOURCE));
		sourceFilter.addValueChangeListener(e -> {
			String value = e.getValue();
			filter.setSource(value == null ? "" : value);
			getDataProvider().refreshAll();
		});
		destFilter = new TextField();
		destFilter.setClearButtonVisible(true);
		destFilter.setValueChangeMode(ValueChangeMode.LAZY);
		destFilter.setPlaceholder(getFieldName(COLUMN_DESTINATION));
		destFilter.addValueChangeListener(e -> {
			String value = e.getValue();
			filter.setDestination(value == null ? "" : value);
			getDataProvider().refreshAll();
		});
	}

	/**
	 * Sets up the headers, can be called e.g. from @PostConstruct.
	 */
	protected final void setupHeaders() {
		source.setHeader(sourceFilter);
		source.setSortable(true);
		dest.setHeader(destFilter);
		dest.setSortable(true);
		startTime.setHeader(getFieldName(COLUMN_TRIP_START));
		startTime.setSortable(true);
		street.setHeader(getFieldName(COLUMN_ADDRESS));
		street.setSortable(true);
		customerName.setHeader(getFieldName(COLUMN_VISITED_CUSTOMER));
		customerName.setSortable(true);
 	}

	// @seife auto-code end
}
