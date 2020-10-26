package com.opentext.otmm.sc.jobmodeler;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artesia.asset.AssetIdentifier;
import com.artesia.entity.TeamsIdentifier;
import com.artesia.metadata.MetadataCollection;
import com.artesia.metadata.MetadataField;
import com.artesia.security.SecuritySession;
import com.opentext.job.JobContext;
import com.opentext.otmm.sc.jobmodeler.helpers.JobHelper;
import com.opentext.otmm.sc.jobmodeler.helpers.MetadataHelper;
import com.opentext.server.activity.decision.EvaluationService;
import com.opentext.server.job.step.JobData;

/**
 * Evaluates a given value. You can specify whether it evaluates an expression, 
 * a variable, or a custom implementation class.
 * A standard Java EvaluationService interface is defined to perform evaluation. 
 * 
 * All the **EvaluationService** implementation classes must implement the 
 * <strong>evaluate()</strong> method and return the evaluation result. 
 * 
 * @author Joaquín Garzón
 */
public class FileNamePatternEvaluationService implements EvaluationService {

	protected static final Log log = LogFactory.getLog(FileNamePatternEvaluationService.class);

	private static final String FIELD_ASSET_NAME = "ARTESIA.FIELD.ASSET NAME";

	// HB_\d{2}_\d{2}_[a-zA-Z0-9]{1,17}_((\d{2})|(##(|\d{2})))_((4c)|(1c))_((DE)|(AT)|(CH)|(CZ)|(FCH)|(ICH)|(NL)|(SE)|(RO)|(LU)|(SK))[.]((tif)|(tiff)|(TIF)|(TIFF)|(jpg)|(JPG)|(jpeg)|(JPEG)|(eps)|(EPS)|(psd)|(PSD))
	//
	// Valid File name examples:
	//
	//   HB_22_10_hola_20_4c_DE.jpg
	//   HB_16_10_hello_##20_1c_AT.jpeg
	
	private static final String FILE_NAME_PATTERN = "HB_\\d{2}_\\d{2}_[a-zA-Z0-9]{1,17}_((\\d{2})|(##(|\\d{2})))_((4c)|(1c))_((DE)|(AT)|(CH)|(CZ)|(FCH)|(ICH)|(NL)|(SE)|(RO)|(LU)|(SK))[.]((tif)|(tiff)|(TIF)|(TIFF)|(jpg)|(JPG)|(jpeg)|(JPEG)|(eps)|(EPS)|(psd)|(PSD))";
	
	public FileNamePatternEvaluationService() {
		log.debug("FileNamePatternEvaluationService instanciated...");
	}
	
	@Override
	public String evaluate(JobData data, JobContext context, SecuritySession session) {
		boolean evaluation = true;
		
		log.debug("Start evaluation...");

		List<AssetIdentifier> assetIds = JobHelper.getAssetIds(context.getData(), session);
		log.debug("Assets ids: " + assetIds);
		
		if(assetIds != null) {
			TeamsIdentifier teamsFieldId = new TeamsIdentifier(FIELD_ASSET_NAME);
			MetadataCollection[] metaCol = MetadataHelper.getMetadataForAssets(assetIds, teamsFieldId);
			
			TeamsIdentifier fieldAssetNameId = new TeamsIdentifier(FIELD_ASSET_NAME);
			MetadataField  field = null;
			String fileName = null;
			
			for(MetadataCollection col: metaCol) {
				field = (MetadataField) col.findElementById(fieldAssetNameId);
				if(field != null) {
					fileName = field.getValue().getStringValue();
					log.debug("evaluating file: " + fileName);
					evaluation = evaluation && fileName.matches(FILE_NAME_PATTERN);
					log.debug("Evaluation result: " + evaluation);
				}
			}
		}
		else {
			log.info("No assets attached to the task");
		}
		
		log.debug("End evaluation... " + evaluation);
		
		return Boolean.toString(evaluation);
	}

}
