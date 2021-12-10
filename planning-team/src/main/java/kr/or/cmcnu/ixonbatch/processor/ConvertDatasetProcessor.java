package kr.or.cmcnu.ixonbatch.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import kr.or.cmcnu.ixonbatch.model.CommonDataset;
import kr.or.cmcnu.ixonbatch.util.FNVHash;
import lombok.extern.slf4j.Slf4j;

public class ConvertDatasetProcessor implements ItemProcessor<HashMap, List<CommonDataset>> {

	private String dataset_name = "KPI1";

	@Override
	public List<CommonDataset> process(HashMap hash) throws Exception {
		List<CommonDataset> list = new ArrayList<>();
		String uniqueKey = hash.get("HEADQUARTER") + "\n" + hash.get("DEPARTMENT") + "\n" + hash.get("PART");
		String fnvhash64 = String.format("%16s", Long.toHexString(FNVHash.hash64(uniqueKey))).replace(' ',  '0');
		hash.forEach((key, value) -> {
			String column_name = key.toString();
			String column_value = value.toString();
			list.add(new CommonDataset(dataset_name, fnvhash64, column_name, column_value));
		});
		return list;
	}

}
