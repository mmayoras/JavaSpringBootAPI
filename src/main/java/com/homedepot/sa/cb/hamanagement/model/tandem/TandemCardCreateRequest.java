package com.homedepot.sa.cb.hamanagement.model.tandem;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * Created by PXK2457 on 11/30/2016.
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonSerialize
@JsonRootName("CardRequest")
public class TandemCardCreateRequest extends TandemCardRequest{




}
