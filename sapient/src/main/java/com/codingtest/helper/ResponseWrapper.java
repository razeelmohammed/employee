package com.codingtest.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.codingtest.constant.SystemConstant;

public class ResponseWrapper { 

	    public static ResponseEntity<StandardResponse> getResponseEntityForGet(Object object) {
	        if (object != null) {
	            return getOkStatusResponse(SystemConstant.VOID, object);
	        }
	        return getNoContentResponse();
	    }

	    public static ResponseEntity<StandardResponse> getResponseEntityForDelete(Boolean status) {
	        if (status) {
	            return getOkStatusResponse(SystemConstant.DELETE_MESSAGE, status);
	        }
	        return getUnprocessableEntityResponse();
	    }

	    public static ResponseEntity<StandardResponse> getResponseEntityForUpdate(Boolean status) {
	        if (status) {
	            return getOkStatusResponse(SystemConstant.UPDATE_MESSAGE, status);
	        }
	        return getUnprocessableEntityResponse();
	    }

	    public static ResponseEntity<StandardResponse> getResponseEntityForSave(Boolean status) {
	        if (status) {
	            return getCreatedStatusResponse();
	        }
	        return getUnprocessableEntityResponse();
	    }
	    
	    public static ResponseEntity<StandardResponse> getResponseEntityForSave(Long id) {
	        if (id > 0) {
	            return getCreatedStatusResponse(id);
	        }
	        return getUnprocessableEntityResponse();
	    }

	    public static ResponseEntity<StandardResponse> getNoContentResponse() {
	        return ResponseEntity
	                .status(HttpStatus.OK)
	                .body(new StandardResponse(HttpStatus.NO_CONTENT.value(), SystemConstant.VOID, SystemConstant.VOID));
	    }


	    public static ResponseEntity<StandardResponse> getUnprocessableEntityResponse() {
	        return ResponseEntity
	                .status(HttpStatus.UNPROCESSABLE_ENTITY)
	                .body(new StandardResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), SystemConstant.ERROR, SystemConstant.ERROR));
	    }

	    public static ResponseEntity<StandardResponse> getOkStatusResponse(String message, Object object) {
	        return ResponseEntity
	                .status(HttpStatus.OK)
	                .body(new StandardResponse(HttpStatus.OK.value(), SystemConstant.SUCCESS, message, object));
	    }

	    public static ResponseEntity<StandardResponse> getCreatedStatusResponse() {
	        return ResponseEntity
	                .status(HttpStatus.CREATED)
	                .body(new StandardResponse(HttpStatus.CREATED.value(), SystemConstant.SUCCESS, SystemConstant.SAVE_MESSAGE));
	    }
	    
	    public static ResponseEntity<StandardResponse> getCreatedStatusResponse(Long id) {
	        return ResponseEntity
	                .status(HttpStatus.CREATED)
	                .body(new StandardResponse(HttpStatus.CREATED.value(), SystemConstant.SUCCESS, SystemConstant.SAVE_MESSAGE,id));
	    }

	}
