package cc.pp.sina.web.resource;

import cc.pp.sina.dao.price.BozhuService;
import cc.pp.sina.dao.price.PriceService;
import cc.pp.sina.domain.bozhus.BozhuPrice;
import cc.pp.sina.domain.bozhus.price.PriceSource;
import cc.pp.sina.utils.json.JsonUtils;
import cc.pp.sina.web.application.CommonInfoApplication;
import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.io.IOException;

/**
 * User: chenwei@pp.cc
 * Date: 14-1-12
 * Time: 上午11:57.
 */
public class UserPriceSourceResource extends ServerResource {

	private int sourceId;
	private PriceService priceService;
	private BozhuPrice bozhuPrice;

	@Override
	public void doInit() {
		long uid = Long.valueOf((String) this.getRequest().getAttributes().get("uid"));
		sourceId = Integer.valueOf((String) this.getRequest().getAttributes().get("sourceId"));
		CommonInfoApplication application = (CommonInfoApplication) getApplication();
		priceService = application.getPriceService();
		BozhuService bozhuService = application.getBozhuService();
		bozhuPrice = bozhuService.get(uid);
		setExisting(bozhuPrice != null);
	}

	@Get("json")
	public String getSource() throws IOException {
		PriceSource result = priceService.getSource(bozhuPrice, sourceId);
		return JsonUtils.getObjectMapper().writer(PriceSource.emptyFilters).writeValueAsString(result);
	}

	@Delete
	public void deleteSource() {
		priceService.deleteSource(bozhuPrice, sourceId);
		setStatus(Status.SUCCESS_NO_CONTENT);
	}

}
