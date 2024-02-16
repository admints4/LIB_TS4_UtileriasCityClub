package com.ts4.lib.utils.errors.errormercurio;

import lombok.Data;

@Data
public class MercurioErrors {
	private int statuscode;
	private String description;
	private MercurioDescriptionDetail descriptiondetail;
}
