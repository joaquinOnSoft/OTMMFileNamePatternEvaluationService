package com.opentext.otmm.sc.jobmodeler.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artesia.asset.AssetIdentifier;
import com.artesia.security.SecuritySession;
import com.opentext.job.context.ListContextWrapper;

public class JobHelper {

	private static Log log = LogFactory.getLog(JobHelper.class);

	public static List<AssetIdentifier> getAssetIds(Map<String, Object> data) {
		return getAssetIds(data, SecurityHelper.getAdminSession());
	}

	public static List<AssetIdentifier> getAssetIds(Map<String, Object> data, SecuritySession session) {
		List<AssetIdentifier> assetIds = new ArrayList<AssetIdentifier>();
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			String key = (String) entry.getKey();
			Object value = entry.getValue();
			if (key.equalsIgnoreCase("assetIds")) {
				assetIds = ((ListContextWrapper) value).getListData();
			}
		}
		
		log.debug("Asset ids: " + assetIds);
		
		return assetIds;
	}
}
