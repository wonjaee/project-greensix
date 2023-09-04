package com.coding.cho.common.domain.dto;


import com.coding.cho.event.EventEntity;
import com.coding.cho.event.EventImageEntity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class S3UploadDTO {
	
	private String tempKey;
	private String bucketKey;
	private String orgName;
	private String newName;
	private boolean def;
	private long gno; //fk 상품pk
	
	public S3UploadDTO withGno(long no) {
        this.gno = no;
        return this;
    }
	
	public S3UploadDTO bucketKey(String bucketKey) {
		this.bucketKey=bucketKey;
		return this;
	}
	
	public EventImageEntity toEventImageEntity(long no) {
		return EventImageEntity.builder()
				.newName(newName)
				.url(bucketKey)
				.bucketKey(bucketKey).orgName(orgName).isDef(def).event(EventEntity.builder().no(no).build())
				.build();
	}
}
