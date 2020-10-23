package com.opentext.otmm.sc.jobmodeler.helpers;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artesia.asset.AssetIdentifier;
import com.artesia.asset.metadata.services.AssetMetadataServices;
import com.artesia.asset.services.AssetServices;
import com.artesia.common.exception.BaseTeamsException;
import com.artesia.entity.TeamsIdentifier;
import com.artesia.metadata.MetadataCollection;
import com.artesia.metadata.MetadataField;
import com.artesia.security.SecuritySession;

public class MetadataHelper {

	private static Log log = LogFactory.getLog(MetadataHelper.class);

	public static MetadataCollection[] getMetadataForAssets(List<AssetIdentifier> assetIds, TeamsIdentifier fieldId) {
		MetadataCollection[] collection = null;
		try {
			collection = AssetMetadataServices.getInstance().retrieveMetadataForAssets(
					(AssetIdentifier[]) assetIds.toArray(new AssetIdentifier[assetIds.size()]),
					fieldId.asTeamsIdArray(), null, SecurityHelper.getAdminSession());
		} catch (BaseTeamsException e) {
			log.error(e.getMessage(), e);
		}
		return collection;
	}

	public static void lockAssets(List<AssetIdentifier> assetIds) {
		try {
			AssetServices.getInstance().lockAssets(
					(AssetIdentifier[]) assetIds.toArray(new AssetIdentifier[assetIds.size()]),
					SecurityHelper.getAdminSession());
		} catch (BaseTeamsException e) {
			log.error(e.getMessage(), e);
		}
	}

	public static void unlockAssets(List<AssetIdentifier> assetIds) {
		try {
			AssetServices.getInstance().unlockAssets(
					(AssetIdentifier[]) assetIds.toArray(new AssetIdentifier[assetIds.size()]),
					SecurityHelper.getAdminSession());
		} catch (BaseTeamsException e) {
			log.error(e.getMessage(), e);
		}
	}

	public static void saveMetadata(List<AssetIdentifier> assetIds, MetadataField field) {
		try {
			AssetMetadataServices.getInstance().saveMetadataForAssets(
					(AssetIdentifier[]) assetIds.toArray(new AssetIdentifier[assetIds.size()]),
					new MetadataField[] { field }, SecurityHelper.getAdminSession());
		} catch (BaseTeamsException e) {
			log.error(e.getMessage(), e);
		}
	}

	public static MetadataField getMetadataField(AssetIdentifier assetId, TeamsIdentifier fieldId) {
		return getMetadataField(assetId, fieldId, SecurityHelper.getAdminSession());
	}

	public static MetadataField getMetadataField(AssetIdentifier assetId, TeamsIdentifier fieldId,
			SecuritySession session) {
		try {
			return AssetMetadataServices.getInstance().retrieveMetadataFieldForAsset(assetId, fieldId, session);
		} catch (BaseTeamsException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static void saveMetadata(String fieldId, List<AssetIdentifier> assetIDs,String value) {
		
		TeamsIdentifier teamsFieldId = new TeamsIdentifier(fieldId);
		MetadataCollection[] metaCol = MetadataHelper.getMetadataForAssets(assetIDs, teamsFieldId);

		log.info("Getting metadatafield");
		MetadataField assetStatusField = (MetadataField) metaCol[0].findElementById(teamsFieldId);

		assetStatusField.setValue(value);

		// lock the asset before saving
		log.info("Locking assets");
		MetadataHelper.lockAssets(assetIDs);

		// save the new value
		log.info("Saving asset");
		MetadataHelper.saveMetadata(assetIDs, assetStatusField);

		log.info("Unlocking assets");
		MetadataHelper.unlockAssets(assetIDs);
		
	}
}
