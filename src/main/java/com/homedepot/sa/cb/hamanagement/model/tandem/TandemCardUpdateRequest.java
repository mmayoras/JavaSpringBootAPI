package com.homedepot.sa.cb.hamanagement.model.tandem;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by n84qvs on 3/2/17.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonSerialize
@JsonRootName("CardUpdate")
public class TandemCardUpdateRequest extends TandemCardRequest {
}
