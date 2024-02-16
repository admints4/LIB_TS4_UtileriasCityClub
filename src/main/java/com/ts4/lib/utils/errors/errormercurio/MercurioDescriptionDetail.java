package com.ts4.lib.utils.errors.errormercurio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MercurioDescriptionDetail {
	
	@JsonProperty("ClassName")
    private String className;
	
	@JsonProperty("Message")
    private String message;
	
	@JsonProperty("StackTraceString")
    private String stackTraceString;
	
	@JsonProperty("RemoteStackIndex")
    private long remoteStackIndex;
    
	@JsonProperty("HResult")
	private long hResult;
	
	@JsonProperty("Source")
    private String Source;
}
