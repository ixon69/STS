package kr.or.cmcnu.ixonbatch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonDataset {

	private long id;
	private String dataset_name;
	private String row_key;
	private String column_name;
	private String column_value;

	public CommonDataset(String dataset_name, String row_key, String column_name, String column_value) {
		this.dataset_name = dataset_name;
		this.row_key = row_key;
		this.column_name = column_name;
		this.column_value = column_value;
	}

}
