package com.ts4.lib.utils.errors.ocapierror;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusDetails {
	private ErrorDetails error;
}
